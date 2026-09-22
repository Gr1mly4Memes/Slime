# Slime

A NeoForge hybrid Minecraft server: NeoForge **mods** and Paper/PurPur/Spigot/Bukkit **plugins**
running together on Minecraft **26.2**.

> **Slime is experimental.** Expect instability on production servers.

- 📖 **[Documentation & credits](docs/README.md)**
- 🤝 **[Contributing](docs/CONTRIBUTING.md)**
- 🧩 **[Mod & plugin compatibility](docs/COMPATIBILTY.md)**

## Quick start

```bash
./gradlew setup      # decompile Minecraft and apply patches
./gradlew slimeJar   # build the runnable server jar
```

The runnable jar is written to `projects/slime/build/libs/`:

```bash
java -jar slime-26.2-server.jar
```

## What Slime is

| Layer | Provides |
| --- | --- |
| [NeoForge](https://github.com/neoforged/NeoForge) | Mod loading and the modding API |
| [Paper](https://github.com/PaperMC/Paper) | Chunk system and server optimizations |
| [PurPur](https://github.com/PurpurMC/Purpur) / [Pufferfish](https://github.com/pufferfish-gg/Pufferfish) / [Leaf](https://github.com/Winds-Studio/Leaf) | Additional configurable behavior and optimizations |
| Bukkit / CraftBukkit / Spigot | Plugin API |
| Slime | The glue between all of the above, plus lag compensation |

Slime originated as a fork of [Youer](https://github.com/MohistMC/Youer) and is now developed as an
independent project. Full attribution is in [docs/CREDITS.md](docs/CREDITS.md).

## License

See [README-LICENSE.md](README-LICENSE.md) and the [LICENCE](LICENCE) directory.
