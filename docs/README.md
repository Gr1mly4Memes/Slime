<div align="center">

# Slime

Slime is a NeoForge hybrid server implementing the Paper, PurPur, Spigot and Bukkit plugin APIs,
so NeoForge mods and the Bukkit plugin ecosystem can run on the same server.

**Slime is still in an EXPERIMENTAL phase and may cause instability!**

Join my small Discord: https://discord.gg/vzy3DwcbUT
</div>

## ⚙️ Features

- **NeoForge + Bukkit in one jar** - NeoForge mods alongside Paper/PurPur/Spigot/Bukkit plugins
- **Almost Fully Compatible** - Works with PurPur, Paper, Spigot, and Bukkit plugins
- **Performance Optimized** - Paper (Moonrise chunk system), Pufferfish, Leaf and PurPur optimizations,
  plus Slime's own lag compensation, which re-runs time-based gameplay logic proportionally to the
  number of ticks the server actually missed
- **Target version** - Minecraft 26.2 / NeoForge 26.2

## 🚀 Building

```bash
./gradlew setup          # decompile Minecraft and apply patches
./gradlew slimeJar       # build the runnable server jar
```

The jar is written to `projects/slime/build/libs/`. Run it with:

```bash
java -jar slime-26.2-server.jar
```

## ⚡ Performance Mods & Plugins

This project includes Paper optimizations out of the box. Adding external performance optimization
mods or plugins is unnecessary and may break unexpected server behavior.
See [Compatibility](COMPATIBILTY.md) for details.

## 📜 Credits

Slime originated as a fork of [Youer](https://github.com/MohistMC/Youer) and has since been
reworked as an independent project. It includes patches and features from many open-source
projects - see [CREDITS.md](CREDITS.md) for the full list.

## 📌 Development Notice

Slime is under active early development. Contributions are welcome - see
[CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.
