# Slime — Code, Performance & Identity Audit

Audit of `Gr1mly4Memes/Slime` at `26.2` (NeoForge `26.2.0.88`, Minecraft `26.2`), a fork of
[MohistMC/Youer][youer] — a NeoForge hybrid server implementing the Bukkit/Spigot/Paper/PurPur
plugin APIs.

Everything in **✅ Fixed** below is already applied on this branch. Items marked **⚠️ Recommended**
are real problems I deliberately did *not* change, either because they need a design decision from
you or because they cannot be verified in this sandbox.

> **Verification caveat:** this sandbox has no JDK and no outbound network, so nothing could be
> compiled or run. Every change here is static analysis and has been written to be conservative —
> but please run `./gradlew setup && ./gradlew slimeJar` before trusting any of it.

---

## 1. Critical correctness bugs

### 1.1 ✅ `SHA256.as(InputStream)` never hashed anything

`slimelauncher/.../util/SHA256.java`

```java
return String.format("%032x", new BigInteger(1,
    new DigestInputStream(is, MessageDigest.getInstance("SHA-256")).getMessageDigest().digest()))
    .toLowerCase();
```

`getMessageDigest()` returns the `MessageDigest` **itself**; calling `digest()` on it finalises the
hash over whatever was written so far. The stream is never read, so this always returned
`e3b0c442…b855` — the SHA-256 of *zero bytes* — for every input.

Knock-on effects, all of which were live:

* `LibrariesDownloadQueue.scanFromJar()` recorded that same bogus digest for every bundled library.
* `needDownload()` compared the **real** on-disk digest against the bogus one → never equal →
  **every library in the jar was re-extracted from the Slime jar on every single boot**, plus a
  full `syncCommands()`-free but very I/O-heavy copy pass.
* `Action.copyFileFromJar()` compared `SHA256.as(file)` with `SHA256.as(is)` → never equal → the
  universal jar and the binary patch were re-extracted every boot, and `clearOld` wiped the whole
  `libraries/net/neoforged/neoforge/` directory each time.

Fixed by actually draining the stream through the `DigestInputStream`, streaming files through a
64 KiB buffer instead of `Files.readAllBytes`, and replacing `BigInteger` + `String.format` +
`toLowerCase()` with `HexFormat` (also removes a Turkish-locale `toLowerCase()` hazard).

### 1.2 ✅ `Files.createDirectories(file.toPath())` created a directory where the file should be

`slimelauncher/.../action/Action.java` (`copyFileFromJar`)

```java
Files.createDirectories(file.toPath());   // creates .../client.lzma as a *directory*
Files.copy(is, file.toPath(), REPLACE_EXISTING);   // always fails, IOException silently ignored
```

Extraction silently failed whenever the parent directory did not already exist. Fixed to create
`file.getParentFile()`, and the swallowed `IOException` now reports.

### 1.3 ✅ Unbounded recursion in `LibrariesDownloadQueue.progressBar()`

```java
if (!fail.isEmpty()) {
    progressBar();   // recurses forever for a deterministic failure
}
```

A library missing from the jar (or a read-only `libraries/` directory) turned into a
`StackOverflowError` instead of an error message. Replaced with a bounded retry loop that reports
the entries it could not extract.

### 1.4 ✅ `normalizeName()` in `NeoForgeInjectBukkit` was a no-op with a locale bug

```java
return name.toUpperCase().replace("[^A-Z0-9_", "_").replace("__", "_");
```

Two separate defects:

* `String.replace(CharSequence, CharSequence)` is **literal**, not regex — the intended
  `replaceAll("[^A-Z0-9_]", "_")` (note the also-missing closing `]`) did nothing. Modded ids came
  out as `TWILIGHTFOREST:TWILIGHT_OAK` and were then injected as **enum constant names containing a
  colon** via `Material.addEnumValue()`'s reflection hack.
* `toUpperCase()` without `Locale.ROOT` maps `i` → `İ` under a Turkish locale, silently producing a
  *different* material name for every id containing an `i`.

Now uses a precompiled `Pattern` with `Locale.ROOT`.

### 1.5 ✅ `ThreadUtils.executeOnMainThread` never woke the server thread

```java
if ("waiting for tasks".equals(LockSupport.getBlocker(serverThread))) {
    LockSupport.unpark(serverThread);
}
```

The server parks with `LockSupport.parkNanos("waiting for tick or tasks", toWait)`
(`patches/net/minecraft/server/MinecraftServer.java.patch`), so the string never matched and the
unpark **never fired**. Queued work sat idle until the next tick deadline or unrelated I/O. Used on
the player-configuration path (`ServerConfigurationPacketListenerImpl`), i.e. extra login latency.

### 1.6 ✅ `OSUtil.getOS()` returned `null` on unrecognised platforms

`DataParser.parseLaunchArgs()` immediately calls `os.isWindows()` → `NullPointerException` on any
OS that matches none of the branches. Added an `UNKNOWN` constant, made the cache `volatile`, and
tested macOS before the generic `nix/nux` branch.

### 1.7 ✅ EULA prompt: Windows-only, leaked a handle, crashed on EOF

`Action.start()` only prompted when `OSUtil.getOS().isWindows()`, so a Linux server booted straight
into Mojang's terse refusal. The loop also allocated a new `Scanner` per iteration, never closed
it, and threw `NoSuchElementException` when stdin was closed (Docker, systemd, CI).
`MojangEulaUtil.writeInfos()` wrote with the platform default charset but `hasAcceptedEULA()` read
back as UTF-8, and `Files.readAllLines` threw `MalformedInputException` on a non-UTF-8 `eula.txt`.

### 1.8 ✅ `PlayerEventDispatcher.onItemTossEvent` — unguarded cast + re-entrancy

```java
ServerPlayer player = (ServerPlayer) event.getPlayer();
```

Any modded `FakePlayer` threw `ClassCastException` from inside the NeoForge event bus. Worse, the
cancellation path calls `player.drop(...)`, which fires another `ItemTossEvent` — a plugin that
cancels every drop would recurse until the stack overflowed. Now guarded with `instanceof` plus a
`ThreadLocal` re-entrancy flag.

### 1.9 ✅ Removed: three event dispatchers that would double-fire Bukkit events

`gr1mly4memes/slime/eventhandler/` (`EventDispatcherRegistry`, `PlayerEventDispatcher`,
`ItemEventDispatcher`) was **never referenced anywhere** — `EventDispatcherRegistry.init()` had no
caller, so none of the listeners was ever registered on the NeoForge bus.

That looked like a bug, and the obvious "fix" (call `init()`) would have been a serious one, because
CraftBukkit **already** fires all three events from the Minecraft patches:

| Dispatcher | Event it fires | Already fired by |
|---|---|---|
| `PlayerEventDispatcher.onItemTossEvent` | `PlayerDropItemEvent` | `patches/.../world/entity/LivingEntity.java.patch` |
| `ItemEventDispatcher.onItemExpireEvent` | `ItemDespawnEvent` | `patches/.../world/entity/item/ItemEntity.java.patch` (two places) |
| `PlayerEventDispatcher.onEnterSleepEvent` | `PlayerBedEnterEvent` | CraftBukkit's own bed path |

Registering them would have fired `PlayerDropItemEvent` twice for every item drop — a classic
source of double-counting in protection, anti-dupe and logging plugins.

Rather than leave a trap for the next person to "fix", the three classes were deleted. If you want
them back, the git history is intact; the takeaway is that they need deduplication, not
registration.

### 1.10 ✅ `BukkitPermissionsHandler` was never registered

The class existed but nothing registered it, so NeoForge mod permission nodes always resolved
through their own default resolver and never saw the server's permission plugin. It is now
registered under `slime:permission` (`SlimePermissionBridge`, called from the `CraftServer`
constructor). This is **opt-in** — NeoForge's `permissionHandler` config still defaults to
`neoforge:default_handler`; set `permissionHandler="slime:permission"` to route mod permissions
through Bukkit/LuckPerms. Its identifier also still said `"youer"`.

### 1.11 ✅ `CraftFakePlayer` was never wired up

`CraftFakePlayer` (safe `isOp()`, no-op `setOp()` so modded fake players are never written to
`ops.json`) was dead. Registered in `EntityClassLookup` for
`net.neoforged.neoforge.common.util.FakePlayer`, ahead of the `ServerPlayer` entry — the lookup
walks the concrete class first, so ordering matters and is now commented.

### 1.11a ✅ Launcher `main` was package-private

`slimelauncher/.../Main.java` declared `static void main(String[])` with no access modifier, while
the server jar's manifest points `Main-Class` at it. The JVM launcher resolves the entry point with
`Class#getMethod("main", String[].class)`, which only finds **public** methods, so
`java -jar slime-26.2-server.jar` would fail with *"Main method not found"* before anything ran
(JLS 12.1.4 requires `public static void main(String[])`).

Made `public`. This is safe either way: `public static void main` is always valid, so if some
launcher path was tolerating the package-private form, nothing changes.

### 1.12 ⚠️ Recommended — `MinecraftServer.globalEntityCache` can leak entities

`patches/net/minecraft/server/MinecraftServer.java.patch` adds a `ConcurrentHashMap<UUID, Entity>`
populated from `ServerLevel` (`addEntityToGlobalCache`) and drained in exactly one place
(`removeEntityFromGlobalCache`). Any entity removed through a path that does not route through that
call is retained for the lifetime of the server. `clearEntityCacheForLevel` is O(n) over the whole
cache on level unload.

Worth auditing against upstream Youer issue [#549][youer-549], which is the same class of bug in a
different subsystem (see §5) — **Slime does not have that particular crash**, because its
`PersistentEntitySectionManager` patch contains no `synchronized` block at all.

---

## 2. Performance

### 2.1 ✅ TPS history: `CopyOnWriteArrayList` on a hot path

`gr1mly4memes/slime/util/tps/TPSCalculator.java`

* `doTick()` called `tpsHistory.remove(0)` **and** `add()` on a `CopyOnWriteArrayList` — two full
  array copies **per tick, per level**.
* `getAverageTPS()` streamed and unboxed every element. `getMostAccurateTPS()` calls it, and
  `TPSUtil.rawTT20()` → `getMostAccurateTPS()` runs for **every block-break tick, every accelerated
  block-entity tick, every item pickup and every portal transfer**. That was thousands of
  allocations per tick under load.

Replaced with a fixed `double[]` ring buffer and a plain summation loop — zero allocation, zero
boxing.

### 2.2 ✅ Mod/material registration was O(n·m) at startup

`NeoForgeInjectBukkit.addEnumMaterialInItems()` and `addEnumMaterialsInBlocks()` built a
`List<String>` of every `Material.name()` and then called `List#contains` once per registry entry:

```java
List<String> materials = new ArrayList<>(Arrays.stream(Material.values()).map(Enum::name).toList());
for (Item item : registry) {
    ...
    if (isMod || !materials.contains(materialName)) {   // O(n) per item
```

With ~1,200 materials × ~2,000 registry entries (and far more on a modpack) that is millions of
string comparisons on the critical startup path. Now a single shared `HashSet`, built once.
`addEnumEntity()` had the identical shape and was fixed too.

### 2.3 ✅ The launcher hashed the entire jar on every boot

`scanFromJar()` opened a stream for every `.jar`/`.zip`/`.txt`/`.lzma` entry under
`META-INF/libraries` and hashed it — hundreds of MB of I/O per start — and then leaked every one of
those streams.

Replaced with a **build-time manifest**: `projects/slime/build.gradle` now generates
`META-INF/libraries.txt` (`path|sha256|size`, the format `Libraries.from()` already parsed) and
`LibrariesDownloadQueue` reads it. The jar scan remains as a fallback for jars built without it,
but now closes its streams. Verification also short-circuits on `file.length()` before hashing.

### 2.4 ✅ Entity-type resolution cached

`EntityClassLookup.getEntity()` walked the superclass chain with a `HashMap` lookup per level for
**every** `CraftEntity` creation. Now memoised in a `ClassValue`, which is lock-free on read and —
unlike a static `HashMap` cache — does not pin mod/plugin classloaders for the life of the server.

### 2.4a ✅ Missed-tick count now computed once per tick

`applicableMissedTicks()` did `(int) Math.floor(allMissedTicks)` on every call, and lag compensation
calls it **once per living entity, per block entity and per item entity per tick** — plus a second
time inside the `ItemEntity` branch, and once per iteration of the two acceleration loops.

The value is constant for the whole tick (`allMissedTicks` only changes in `doTick()`), so it is now
cached in an `int` refreshed once per tick, and hoisted into a local in the `LevelChunk`,
`LivingEntity` and `ItemEntity` patches. `clearMissedTicks()` still uses the live value so the
fractional remainder is preserved.

### 2.5 ✅ Modded tree types now actually resolve

`treeTypeByGrowerName` was read by the `TreeGrower` patch but never populated, so every modded tree
fell through to `TreeType.CUSTOM`. `addEnumTreeType()` now fills it.

### 2.5a ⚠️ Recommended — `potion-effect-acceleration` does nothing

`SlimeConfig.potionEffectAcceleration` is a real, documented config option, and
`patches/.../world/entity/LivingEntity.java.patch` adds the matching `lagCompensation()` helper —
but **nothing ever calls it**. Every other acceleration option is wired:

| Option | Hook |
|---|---|
| `block-entity-acceleration` | `LevelChunk` patch, called |
| `block-breaking-acceleration` | `BlockBehaviour` patch, called |
| `eating-acceleration` | `Item` patch, called |
| `fluid-acceleration` | `LavaFluid` / `WaterFluid` patches, called |
| `pickup-acceleration` | `ItemEntity` patch, called |
| `portal-acceleration` | `PortalProcessor` patch, called |
| **`potion-effect-acceleration`** | **`LivingEntity` patch, never called** |

I did **not** wire it up, because unlike the others it re-runs `tickEffects()` on every living
entity on every lagging tick — that fires effect expiry, particle and attribute-recalc side effects
N extra times, which is a gameplay-visible change rather than a pure timing fix. Either hook it into
`LivingEntity` where `tickEffects()` is normally invoked (accepting the extra work), or drop the
option and the dead helper so the config stops advertising something it does not do.

### 2.6 ⚠️ Recommended — declare `slime.debug` handling consistently

`NeoForgeInjectBukkit.DEBUG` and `Main.DEBUG` both read the `slime.debug` system property, but
`Main.DEBUG` is read once at class-init while the launcher sets
`log4j2.configurationFile` before that. Minor, but debug output is currently inconsistent between
the launcher and the server.

### 2.7 ⚠️ Recommended — two Gradle repositories that resolve nothing

`settings.gradle` declares `https://maven.mohistmc.com/` and `https://mohistmc.github.io/maven/`,
but no dependency in the repo uses the `com.mohistmc` group — and
`buildSrc/.../installer/LibraryCollector.java` explicitly *removes* that host from the installer's
repository list. Gradle probes every declared repository for every artifact, so these slow down
resolution and add two third-party failure points.

I left them in place because I cannot run a build to prove nothing resolves from them. To verify:

```bash
./gradlew :neoforge:dependencies --configuration libraries | grep -i mohist
```

If that is empty, delete both lines — a small build-speed and independence win.

---

## 3. Other fixes applied

| Area | Change |
|---|---|
| `ExceptionHandler` | `INSTANCE` is now `volatile` and the handler map is frozen before publication (it was read from arbitrary threads); `printStackTrace(System.out)`/`printStackTrace()` now go to the Log4j logger instead of raw stdout/stderr; `Math.floorMod` guards the sample-ring index against `AtomicInteger` overflow; string-concat log calls converted to parameterised ones |
| `SlimeCommand` | Usage message said `/nitor [...]` (leftover from another project) — now `/slime [...]` |
| `GlobalConfigManager` | Log message said "Failure to load **leaves** config" (Leaf fork leftover); routing now uses `SlimeLogger` consistently |
| `ConfigVerify` | Removed double-brace `ArrayList` initialisation (anonymous inner class per verifier, holding an implicit outer reference) |
| `MojangEulaUtil` | Try-with-resources, explicit UTF-8, `MalformedInputException` fallback |
| `FileUtils` | `JarFile` was leaked whenever the entry was absent; `FileWriter` used the platform charset; a missing resource NPE'd inside the try-block instead of returning an empty list |
| `DataParser` | `.getFirst()` on a missing resource threw a bare `NoSuchElementException` — now an actionable `IllegalStateException` |
| `Libraries.from()` | `ArrayIndexOutOfBoundsException` on malformed manifest lines — now returns `null` and the caller skips the line |
| `Action.install()` | The null-version check ran *after* filesystem writes; `System.exit(0)` (success!) → `System.exit(1)`; `unmute()` moved into a `finally` so a failing install task can't silence the rest of the run |
| `Action.run()` | Unconditional "Loading …" println is now debug-only; the `URLClassLoader` is closed in a `finally` |
| `Action.initInstallerLib()` | The installertools version **and** its SHA-256 were hardcoded in Java, silently diverging from `installertools_version` in `gradle.properties`. Now resolved from the generated manifest, with the old constant as fallback |
| Dead code | Removed `SlimeConfig.createWorldSections` (declared, assigned at shutdown, never read), `NeoForgeInjectBukkit`'s `environment0`, `spawnCategoryMap`, `CategoryspawnMap`, `profession`, `addEnumMaterialsInBlockEntityType()`, `addEnumParticle()`; `gr1mly4memes...CallbackExecutor` (unreferenced, and its `ArrayDeque` is not thread-safe despite implementing `Executor`); `SlimeBlockSnapshot` (unreferenced). See §1.9 for the event dispatchers |
| `BukkitPermissionsHandler` | `getPermission`/`getOfflinePermission` did an unchecked `(T) (Object)` cast; now `node.getType().typeToken().cast(...)`, which is checked |

---

## 4. Separating Slime from Youer

Youer upstream is still on `1.21.1` [2](https://github.com/MohistMC/Youer); Slime is on `26.2`, so
the codebases have already diverged a long way. What remained was the *identity* in the source.

**Applied:**

* Renamed all 20 `YouerMods*` entity classes to `SlimeMods*` (`git mv`, so history is preserved),
  and updated `EntityClassLookup`.
* Rewrote every `// Youer start/end` and `// Youer - …` marker in `patches/` to `// Slime`. Only
  `+` (added) lines were touched — context lines are untouched so the patches still apply.
* `Material.java`, `CraftServer.java`, `META-INF/accesstransformer.cfg` markers → `Slime`.
* `SlimeModsTameableEntity.toString()` returned `"YouerCustomTameableAnimal{…}"`.
* Reworded the ten "not available without Mohist" comments to describe Slime's own behaviour.
* `ItemEventDispatcher` / `BukkitPermissionsHandler` still carried `Copyright (C) MohistMC.` headers.
* Removed an orphaned `// Youer end` marker in `patches/.../SpawnUtil.java.patch` (no matching start).
* Rewrote `docs/README.md` to describe Slime on its own terms, and **added a root `README.md`** —
  the repository had none, so GitHub's landing page was empty.
* `docs/CONTRIBUTING.md` still said "Contributing to **NeoForge**" and told contributors to add
  `neoforged/NeoForge` as upstream. Rewritten for Slime.

**Kept on purpose:** `Youer` in `docs/CREDITS.md` and in the credits section of the READMEs. Slime
derived from Youer, which is LGPL-licensed — removing attribution would be a licence problem, not a
rebranding win.

**⚠️ Recommended — legal, needs your decision:**

* `docs/CONTRIBUTING.md` still contains NeoForge's **Contributor License Agreement**, which
  "assign[s] copyright ownership of your contributions … to **NeoForged**". I added a `TODO`
  banner above it rather than rewriting a legal clause. Replace it with an inbound=outbound
  statement ("you licence your contribution under this project's licence") or remove it.
* `.github/ISSUE_TEMPLATE/*.md` and `.github/stale.yml` are NeoForge boilerplate.
* `LICENCE/` — worth confirming the tree matches what you intend to release Slime under.

---

## 5. Build & CI

### 5.1 ✅ `:tests:` — a project that does not exist

`settings.gradle` includes only `:base`, `:neoforge`, `slimelauncher` and `:coremods`, but
`check-local-changes.yml` ran `./gradlew :neoforge:runData :tests:runData` and `test-prs.yml` ran
`:tests:runGameTestServer` / `:tests:runUnitTests`. Both workflows were guaranteed failures.
Removed the `:tests:` steps.

### 5.2 ✅ `checkFormatting` does not exist

`buildSrc` ships `neoforge.formatting-conventions.gradle`, but **no project applies it**, so
`checkFormatting`, `applyAllFormatting`, `licenseCheck` and `immaculateCheck` are not registered
tasks. `build-prs.yml` ran `./gradlew assemble checkFormatting`, and `check-local-changes.yml` ran
`generatePackageInfos` (also defined only in that unapplied plugin).

Note that simply switching the plugin on is **not** the fix: it forbids `@NotNull`/`@Nonnull`/
`@NonNull` and wildcard imports, which **882** and **174** files under `src/main/java` respectively
use, and it rewrites `org.jetbrains.annotations.Nullable` → `org.jspecify.annotations.Nullable`
(**416** files). The bundled Bukkit/Paper/PurPur sources were never written to NeoForge's style.
The real fix is scoping the immaculate workflow to `net/neoforged/**` only.

### 5.3 ✅ `assemble` never built the deliverable

`build-prs.yml` ran `assemble`, which does not produce the runnable `slime-*-server.jar`. Now runs
`slimeJar`, and drops `checkJarCompatibility` — that task diffs against `net.neoforged:neoforge`
published on Maven, i.e. upstream NeoForge rather than the previous Slime release, so it can only
ever produce false positives here. Also deleted `publish-jcc.yml` and `publish-prs.yml`, which
depend on NeoForged's private GitHub App credentials.

### 5.4 ✅ `build.yml` published a release on every push *and* every pull request

`on: [push, pull_request]` combined with an unconditional `ncipollo/release-action` step meant
every PR build tried to create/update the `26.2-release` GitHub release. Restricted to pushes on
`26.*` branches plus `workflow_dispatch`; the release step now skips PRs. The upload glob
`build/libs/*.jar` (which uploaded the universal jar, installer and userdev bundle alongside the
server jar) is now `slime-*-server.jar`. Upgraded `actions/setup-java@v3` → `v4`.

### 5.5 ✅ `release.yml` could never run

It was NeoForge's generated workflow, gated on `if: github.repository == 'neoforged/NeoForge'`.
Replaced with a tag-triggered (`v*`) Slime release workflow.

### 5.6 ⚠️ Recommended — version pinning

`minecraft_jar=823e2250d24b3ddac457a60c92a6a941943fcd6a` in `gradle.properties` is a Mojang object
hash with no comment explaining which version it belongs to. Add a comment or derive it, otherwise
the next MC bump silently downloads the wrong `server.jar`.

---

## 6. Online research

**The hybrid-server landscape.** Slime sits in the NeoForge-hybrid niche alongside Youer,
MohistNeo and Banner. Independent comparisons consistently place hybrids below pure Paper for
performance and stability, and note that hybrids are "best-effort, not guaranteed" for individual
mod/plugin combinations [3](https://xgamingserver.com/blog/what-is-mohist/). That framing is worth
keeping in the README: it sets expectations and pre-empts a class of bug reports.

**Upstream Youer is on 1.21.1** [2](https://github.com/MohistMC/Youer) while Slime targets 26.2, so
there is essentially nothing left to cherry-pick — maintenance divergence is now permanent and the
rebranding in §4 is the honest reflection of that.

**Youer issue #549** [youer-549] is the most instructive open bug in the family:
`PersistentEntitySectionManager.processUnloads()` iterated a fastutil `LongOpenHashSet` while the
chunk-system thread removed entries, producing a whole-process crash. The reporter's key insight is
that Youer's fix — `synchronized (chunksToUnload)` around the read — is **useless**, because *no
write path ever takes that lock*; bytecode analysis showed exactly one `monitorenter` in the whole
class. The recommended fix is a snapshot loop (`for (long pos : set.toLongArray())`) rather than a
lock.

Two takeaways for Slime:

1. Slime does **not** carry that patch and is not exposed to that crash — verified: Slime's
   `PersistentEntitySectionManager.java.patch` has no `synchronized` block, and nothing references
   `chunksToUnload`.
2. It is a good template for auditing Slime's own cross-thread state, most notably
   `MinecraftServer.globalEntityCache` (§1.9).

**HybridFix** [5](https://modrinth.com/mod/hybridfix) is a third-party mod that patches around
Forge+Bukkit hybrid bugs (event bridging, explosion handling, FakePlayer support). Several of the
things it fixes are exactly the categories where Slime's own glue code is thin — worth reading as a
checklist of what plugin authors hit in practice.

---

## 7. Verification checklist

```bash
./gradlew setup                 # decompile + apply patches (watch for patch application errors)
./gradlew :neoforge:compileJava # compile the patched sources
./gradlew slimeJar              # build the deliverable
unzip -l projects/slime/build/libs/slime-26.2-server.jar | grep libraries.txt   # manifest present
java -jar projects/slime/build/libs/slime-26.2-server.jar   # first boot
java -jar projects/slime/build/libs/slime-26.2-server.jar   # second boot: should skip extraction
```

The second boot is the important one: before this change every library was re-extracted on every
start, so a fast second boot is the clearest signal that §1.1 is really fixed.

[youer]: https://github.com/MohistMC/Youer
[youer-549]: https://github.com/MohistMC/Youer/issues/549
