package gr1mly4memes.slime.bukkit.neoforge;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.dimension.LevelStem;
import org.bukkit.*;
import org.bukkit.craftbukkit.CraftStatistic;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.util.CraftSpawnCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;

public class NeoForgeInjectBukkit {

    public static final boolean DEBUG = Boolean.getBoolean("slime.debug");
    public static BiMap<ResourceKey<LevelStem>, World.Environment> environment =
            HashBiMap.create(ImmutableMap.<ResourceKey<LevelStem>, World.Environment>builder()
                    .put(LevelStem.OVERWORLD, World.Environment.NORMAL)
                    .put(LevelStem.NETHER, World.Environment.NETHER)
                    .put(LevelStem.END, World.Environment.THE_END)
                    .build());

    public static BiMap<World.Environment, ResourceKey<LevelStem>> environment0 =
            HashBiMap.create(ImmutableMap.<World.Environment, ResourceKey<LevelStem>>builder()
                    .put(World.Environment.NORMAL, LevelStem.OVERWORLD)
                    .put(World.Environment.NETHER, LevelStem.NETHER)
                    .put(World.Environment.THE_END, LevelStem.END)
                    .build());

    public static Map<Villager.Profession, Identifier> profession = new HashMap<>();
    private static final BiMap<Identifier, Statistic> STATISTICS = HashBiMap.create(CraftStatistic.statistics);
    public static Map<MobCategory, SpawnCategory> spawnCategoryMap = new HashMap<>();
    public static Map<SpawnCategory, MobCategory> CategoryspawnMap = new HashMap<>();
    public static Map<String, TreeType> treeTypeByGrowerName = new HashMap<>();


    public static void init() {
        addEnumMaterialInItems();
        addEnumEffectAndPotion();
        addEnumMobEffect();
        addEnumMaterialsInBlocks();
        addEnumEntity();
        addStatistic();
        loadSpawnCategory();
        addPose();
        addEnumTreeType();
        addEnumEnvironment(MinecraftServer.getServer().registryAccess().lookupOrThrow(Registries.LEVEL_STEM));
        reloadBukkitRegistries();
    }

    private static String getMaterialName(Identifier resourceLocation, boolean isMod) {
        return isMod ?
                normalizeName(resourceLocation.toString()) :
                normalizeName(resourceLocation.getPath());
    }

    public static void addEnumMaterialInItems() {
        var registry = BuiltInRegistries.ITEM;
        List<String> materials = new ArrayList<>(Arrays.stream(Material.values())
                .map(Enum::name)
                .toList());
        for (Item item : registry) {
            Identifier resourceLocation = registry.getKey(item);
            boolean isMod = isMods(resourceLocation);
            String materialName = getMaterialName(resourceLocation, isMod);

            if (isMod || !materials.contains(materialName)) {
                int id = Item.getId(item);

                Material material = Material.addMaterial(materialName, id, false, true, resourceLocation);

                if (material != null) {
                    CraftMagicNumbers.ITEM_MATERIAL.put(item, material);
                    CraftMagicNumbers.MATERIAL_ITEM.put(material, item);
                    debug("Save-ITEM: {} - {}", material.name(), material.getKey());
                } else {
                    debug("Failed to add material: {}", materialName);
                }
            }
        }
        materials.clear();
    }

    public static void addEnumMaterialsInBlocks() {
        var registry = BuiltInRegistries.BLOCK;
        List<String> materials = new ArrayList<>(Arrays.stream(Material.values())
                .map(Enum::name)
                .toList());
        for (Block block : registry) {
            Identifier resourceLocation = registry.getKey(block);
            boolean isMod = isMods(resourceLocation);
            String materialName = getMaterialName(resourceLocation, isMod);

            // 检查是否需要添加材料
            if (isMod || !materials.contains(materialName)) {
                int id = Item.getId(block.asItem());
                Item item = Item.byId(id);

                Material material = Material.addMaterial(materialName, id, true, false, resourceLocation);
                if (material != null) {
                    CraftMagicNumbers.BLOCK_MATERIAL.put(block, material);
                    CraftMagicNumbers.MATERIAL_BLOCK.put(material, block);
                    debug("Save-BLOCK:{} - {}", material.name(), material.getKey());
                } else {
                    debug("Failed to add block material: {}", materialName);
                }
            }
        }
        materials.clear();
    }

    public static void addEnumMaterialsInBlockEntityType() {
        var registry = BuiltInRegistries.BLOCK_ENTITY_TYPE;
        for (BlockEntityType<?> entityType : registry) {
            Identifier resourceLocation = registry.getKey(entityType);
            if (isMods(resourceLocation)) {
                String materialName = normalizeName(resourceLocation.toString());
                debug("Discover entity blocks:{} - {}", entityType, materialName);
            }
        }
    }

    public static void addEnumEffectAndPotion() {
        var registry = BuiltInRegistries.POTION;
        for (Potion potion : registry) {
            Identifier resourceLocation = registry.getKey(potion);
            if (resourceLocation != null) {
                String name = normalizeName(resourceLocation.toString());
                if (isMods(resourceLocation)) {
                    try {
                        PotionType.valueOf(name);
                    } catch (Exception e) {
                        // Dynamic enum addition not available without Mohist
                        debug("Skipping mod potion type: {}", name);
                    }
                }
            }
        }
    }

    public static void addEnumMobEffect() {
        var registry = BuiltInRegistries.MOB_EFFECT;
        for (MobEffect effect : registry) {
            Identifier resourceLocation = registry.getKey(effect);
            if (resourceLocation != null && isMods(resourceLocation)) {
                NamespacedKey key = NamespacedKey.fromString(resourceLocation.toString());
                if (key != null) {
                    org.bukkit.Registry.MOB_EFFECT.get(key);
                    debug("Save-MobEffect:{}", key);
                }
            }
        }
    }
    public static void addEnumParticle() {
        var registry = BuiltInRegistries.PARTICLE_TYPE;
        for (ParticleType<?> particleType : registry) {
            Identifier resourceLocation = registry.getKey(particleType);
            String name = normalizeName(resourceLocation.toString());
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod particle type: {}", name);
            }
        }
    }


    public static void addEnumEnvironment(Registry<LevelStem> registry) {
        int i = World.Environment.values().length;
        for (Entry<ResourceKey<LevelStem>, LevelStem> entry : registry.entrySet()) {
            ResourceKey<LevelStem> key = entry.getKey();
            World.Environment environment1 = environment.get(key);
            if (environment1 == null) {
                String name = normalizeName(key.identifier().toString());
                int id = i - 1;
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod dimension type: {}", name);
                i++;
            }
        }
    }

    public static void addEnumEntity() {
        var registry = BuiltInRegistries.ENTITY_TYPE;
        List<String> entityTypeNames = Arrays.stream(EntityType.values())
                .map(Enum::name)
                .toList();
        for (net.minecraft.world.entity.EntityType<?> entity : registry) {
            Identifier resourceLocation = registry.getKey(entity);
            if (resourceLocation == null) continue;
            boolean isMod = isMods(resourceLocation);
            String entityName = getMaterialName(resourceLocation, isMod);
            if (isMod) {
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod entity type: {}", entityName);
            } else {
                if (!entityTypeNames.contains(entityName)) {
                    // Dynamic enum addition not available without Mohist
                    debug("Skipping minecraft key entity type: {}", entityName);
                }
            }
        }
    }

    public static void addStatistic() {
        var registry = BuiltInRegistries.STAT_TYPE;
        for (StatType<?> statType : registry) {
            if (statType == Stats.CUSTOM) continue;
            var resourceLocation = registry.getKey(statType);
            Statistic statistic = STATISTICS.get(resourceLocation);
            if (statistic == null && isMods(resourceLocation)) {
                String name = normalizeName(resourceLocation.getPath());
                Statistic.Type type;
                if (statType.getRegistry() == BuiltInRegistries.ENTITY_TYPE) {
                    type = Statistic.Type.ENTITY;
                } else if (statType.getRegistry() == BuiltInRegistries.BLOCK) {
                    type = Statistic.Type.BLOCK;
                } else if (statType.getRegistry() == BuiltInRegistries.ITEM) {
                    type = Statistic.Type.ITEM;
                } else {
                    type = Statistic.Type.UNTYPED;
                }
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod stat type: {}", name);
            }
        }
        for (Identifier resourceLocation : BuiltInRegistries.CUSTOM_STAT) {
            Statistic statistic = STATISTICS.get(resourceLocation);
            if (statistic == null && isMods(resourceLocation)) {
                String name = normalizeName(resourceLocation.getPath());
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod custom stat: {}", name);
            }
        }
        CraftStatistic.statistics = STATISTICS;
    }

    private static void loadSpawnCategory() {
        for (MobCategory category : MobCategory.values()) {
            try {
                CraftSpawnCategory.toBukkit(category);
            } catch (Exception e) {
                String name = category.name();
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod spawn category: {}", name);
            }
        }
    }

    private static void addPose() {
        for (Pose pose : Pose.values()) {
            if (pose.ordinal() > 14) {
                // Dynamic enum addition not available without Mohist
                debug("Skipping mod pose: {}", pose.name());
            }
        }
    }

    public static void addEnumTreeType() {
        for (Entry<String, TreeGrower> entry : TreeGrower.getGrowers().entrySet()) {
            String name = entry.getKey();
            if (!name.contains(":")) continue;

            String enumName = normalizeName(name);
            // Dynamic enum addition not available without Mohist
            debug("Skipping mod tree type: {}", name);
        }
    }

    public static boolean isMods(Identifier resourceLocation) {
        return resourceLocation != null && !resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT);
    }

    public static void reloadBukkitRegistries() {
        try {
            for (var field : org.bukkit.Registry.class.getFields()) {
                if (Modifier.isStatic(field.getModifiers()) && field.get(null) instanceof org.bukkit.Registry.SimpleRegistry<?> registry) {
                    registry.reload();
                }
            }
        } catch (Throwable ignored) {
        }
    }

    public static void debug(String message, Object p0) {
        if (DEBUG) System.out.println(message.replace("{}", String.valueOf(p0)));
    }

    public static void debug(String message, Object p0, Object p1) {
        if (DEBUG) System.out.println(message.replace("{}", String.valueOf(p0)).replace("{}", String.valueOf(p1)));
    }

    private static String normalizeName(String name) {
        return name.toUpperCase().replace("[^A-Z0-9_", "_").replace("__", "_");
    }
}
