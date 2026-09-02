# Slime 26.2 — Mod & Plugin Compatibility

## Confirmed Incompatible Mods

The following NeoForge server-side mods have been tested and **do not currently work with Slime 26.2**:

### Optimization / Performance
- **Lithium** ❌
- **FerriteCore** ❌
- **Most optimization mods** ❌

> **Observed pattern:** Most optimization/performance-focused mods tested so far appear to be incompatible with Slime.

### Gameplay / Content
- **Advanced Netherite** ❌
- **Neo Origins** ❌

### World Generation
- **Moderner Beta** ❌

---

## Confirmed Compatible Mods

The following NeoForge server-side mods have been tested and **work with Slime 26.2**:

- **Chunky (Mod)** ✅
- **Lithostitched** ✅
- **Tectonic** ✅
- **Towns and Towers** ✅
- **Lomka** ✅
- **Retromod** ✅

---

# Confirmed Compatible Plugins

The following Bukkit/Paper plugins have been tested and **work with Slime 26.2**:

- **WorldEdit** ✅
- **LuckPerms** ✅
- **Vault** ✅
- **WorldGuard** ✅
- **Chunky (Plugin)** ✅
- **DiscordSRV (Plugin)** ✅

### Known Plugin Limitation

- **EssentialsX** ⏸️
  - Not currently testable because its latest available version targets **Minecraft 26.1.2**, rather than 26.2.
  - This is **not currently classified as a Slime incompatibility**.

---

# Current Compatibility Summary

| Component | Status |
|---|---|
| Bukkit/Paper plugins | 🟢 Generally strong |
| World-generation mods | 🟢 Generally strong |
| Compatibility/bridge mods | 🟢 Generally strong |
| Optimization mods | 🔴 Major compatibility problem |
| Content/gameplay mods | 🟠 Some incompatibilities |
| Custom player-power mods | 🔴 Incompatible in tested cases |

## Current Known Incompatibility List

**Mods:**
1. Lithium
2. FerriteCore
3. Advanced Netherite
4. Neo Origins
5. Moderner Beta

**Plugins:**
- None confirmed incompatible so far.

**Untestable due to upstream version:**
- EssentialsX (26.1.2 only)

## Important Note

This is a **tested compatibility list, not a definitive list of every incompatible mod**. In particular, the optimization-mod failures suggest there may be a common underlying compatibility issue affecting other low-level optimization mods that have not yet been tested. I will welcome contributions from the community to speed up fixing