Contributing to Slime
=====================

1) Keep patches to Minecraft classes together. If you need a lot of things done, you may either add to relevant forge classes or make a new class. Try not to spread out your patches across multiple disjoint lines, as this makes maintenance of your patches difficult.

2) TODO: Test Mods

3) Follow the code style of the class you're working in (braces on newlines & spaces instead of tabs in Forge classes, inline brackets in patches, etc).

## Workflow

1. Fork the repository
2. Check out your fork
3. Make a branch
4. Run `./gradlew setup` from the project root to decompile sources and apply current patches
5. Import project into your IDE (IntelliJ/Eclipse) or Reload Gradle Project
6. Modify the patched Minecraft sources in `projects/slime/src/main/java` as needed. The unmodified sources are available in `projects/base/src/main/java` for your reference. Do not modify these.
7. Test your changes
   - Run the game (Runs are available in the IDE)
     - Runs starting with `base -` run Vanilla without NeoForge or its patches.
     - Runs starting with `neoforge -` run NeoForge.
   - `./gradlew :neoforge:runServer` starts a patched server you can join
8. Run `./gradlew :neoforge:genPatches` to regenerate patch files from the patched sources
9. Build the deliverable with `./gradlew slimeJar` and sanity check `projects/slime/build/libs/`
10. Commit & Push
11. Make PR

> **Note on formatting:** this repository ships `buildSrc/src/main/groovy/neoforge.formatting-conventions.gradle`,
> but it is not applied to any project, so `applyAllFormatting` / `checkFormatting` do **not** exist.
> It cannot simply be switched on either: it forbids `@NotNull`/`@Nonnull`/`@NonNull` and wildcard
> imports, which the bundled Bukkit/Paper/PurPur sources use throughout. Until it is scoped to
> `net/neoforged/**` only, format your changes to match the surrounding file.

Contributor License Agreement
=============================

> **TODO (maintainer):** the clause below is inherited verbatim from NeoForge and assigns copyright
> to *NeoForged*, not to this project. It needs to be rewritten (or replaced with a
> "you license your contribution under this project's licence" inbound=outbound statement) before
> it can be presented to contributors - do not rely on it as-is.

- You grant NeoForged a license to use your code contributed to the primary codebase (everything **not** under patches) in NeoForge, under the LGPLv2.1 license.
- You assign copyright ownership of your contributions to the patches codebase (everything under patches) to NeoForged, where it will be licensed under the LGPLv2.1 license.
