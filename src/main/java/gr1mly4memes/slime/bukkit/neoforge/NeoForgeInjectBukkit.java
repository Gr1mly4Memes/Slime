package gr1mly4memes.slime.bukkit.neoforge;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import gr1mly4memes.slime.SlimeLogger;
import net.minecraft.core.Registry;
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
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.dimension.LevelStem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftStatistic;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.util.CraftSpawnCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

public class NeoForgeInjectBukkit {

    public static final boolean DEBUG = Boolean.getBoolean("slime.debug");

    /**
     * Enum-safe name normalisation. Anything that is not {@code [A-Z0-9_]} is folded into an
     * underscore so modded ids such as {@code twilightforest:twilight_oak} become a legal
     * {@code TWILIGHTFOREST_TWILIGHT_OAK} instead of a name containing a colon.
     */
    private static final Pattern NON_ENUM_CHARACTER = Pattern.compile("[^A-Z0-9_]");

    public static BiMap<ResourceKey<LevelStem>, World.Environment> environment =
            HashBiMap.create(ImmutableMap.<ResourceKey<LevelStem>, World.Environment>builder()
                    .put(LevelStem.OVERWORLD, World.Environment.NORMAL)
                    .put(LevelStem.NETHER, World.Environment.NETHER)
                    .put(LevelStem.END, World.Environment.THE_END)
                    .build());

    private static final BiMap<Identifier, Statistic> STATISTICS = HashBiMap.create(CraftStatistic.statistics);

    /**
     * Modded tree growers mapped to their Bukkit {@link TreeType}. Populated by
     * {@link #addEnumTreeType()}; read from the TreeGrower patch when a sapling grows.
     */
    public static Map<String, TreeType> treeTypeByGrowerName = new HashMap<>();

    public static void init() {
        // Material.values() is only ever read for membership tests - build the set once and share it
        // instead of doing an O(n) List#contains per registry entry (was O(n*m) over the whole
        // item + block registries, which is millions of string comparisons on a modded server).
        Set<String> existingMaterials = materialNames();

        addEnumMaterialInItems(existingMaterials);
        addEnumMaterialsInBlocks(existingMaterials);
        addEnumEffectAndPotion();
        addEnumMobEffect();
        addEnumEntity();
        addStatistic();
        loadSpawnCategory();
        addPose();
        addEnumTreeType();
        addEnumEnvironment(MinecraftServer.getServer().registryAccess().lookupOrThrow(Registries.LEVEL_STEM));
        reloadBukkitRegistries();
    }

    private static Set<String> materialNames() {
        Set<String> names = new HashSet<>();
        for (Material material : Material.values()) {
            names.add(material.name());
        }
        return names;
    }

    private static String getMaterialName(Identifier resourceLocation, boolean isMod) {
        return isMod ?
                normalizeName(resourceLocation.toString()) :
                normalizeName(resourceLocation.getPath());
    }

    public static void addEnumMaterialInItems(Set<String> existingMaterials) {
        var registry = BuiltInRegistries.ITEM;
        for (Item item : registry) {
            Identifier resourceLocation = registry.getKey(item);
            boolean isMod = isMods(resourceLocation);
            String materialName = getMaterialName(resourceLocation, isMod);

            if (isMod || !existingMaterials.contains(materialName)) {
                int id = Item.getId(item);

                Material material = Material.addMaterial(materialName, id, false, true, resourceLocation);

                if (material != null) {
                    CraftMagicNumbers.ITEM_MATERIAL.put(item, material);
                    CraftMagicNumbers.MATERIAL_ITEM.put(material, item);
                    debug("Save-ITEM: {0} - {1}", material.name(), material.getKey());
                } else {
                    debug("Failed to add material: {0}", materialName);
                }
            }
        }
    }

    public static void addEnumMaterialsInBlocks(Set<String> existingMaterials) {
        var registry = BuiltInRegistries.BLOCK;
        for (Block block : registry) {
            Identifier resourceLocation = registry.getKey(block);
            boolean isMod = isMods(resourceLocation);
            String materialName = getMaterialName(resourceLocation, isMod);

            if (isMod || !existingMaterials.contains(materialName)) {
                int id = Item.getId(block.asItem());

                Material material = Material.addMaterial(materialName, id, true, false, resourceLocation);
                if (material != null) {
                    CraftMagicNumbers.BLOCK_MATERIAL.put(block, material);
                    CraftMagicNumbers.MATERIAL_BLOCK.put(material, block);
                    debug("Save-BLOCK:{0} - {1}", material.name(), material.getKey());
                } else {
                    debug("Failed to add block material: {0}", materialName);
                }
            }
        }
    }

    public static void addEnumEffectAndPotion() {
        var registry = BuiltInRegistries.POTION;
        for (Potion potion : registry) {
            Identifier resourceLocation = registry.getKey(potion);
            if (resourceLocation == null || !isMods(resourceLocation)) {
                continue;
            }
            String name = normalizeName(resourceLocation.toString());
            // Slime does not ship an enum-extension runtime; a name that already exists as
            // a Bukkit PotionType needs no work at all.
            if (potionTypeExists(name)) {
                debug("Skipping mod potion type: {0}", name);
            }
        }
    }

    private static boolean potionTypeExists(String name) {
        for (PotionType type : PotionType.values()) {
            if (type.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void addEnumMobEffect() {
        var registry = BuiltInRegistries.MOB_EFFECT;
        for (MobEffect effect : registry) {
            Identifier resourceLocation = registry.getKey(effect);
            if (resourceLocation == null || !isMods(resourceLocation)) {
                continue;
            }
            NamespacedKey key = NamespacedKey.fromString(resourceLocation.toString());
            if (key != null) {
                // Touching the registry here forces it to be built before plugins can query it.
                org.bukkit.Registry.MOB_EFFECT.get(key);
                debug("Save-MobEffect:{0}", key);
            }
        }
    }

    public static void addEnumEnvironment(Registry<LevelStem> registry) {
        for (Entry<ResourceKey<LevelStem>, LevelStem> entry : registry.entrySet()) {
            ResourceKey<LevelStem> key = entry.getKey();
            if (environment.get(key) == null) {
                String name = normalizeName(key.identifier().toString());
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod dimension type: {0}", name);
            }
        }
    }

    public static void addEnumEntity() {
        var registry = BuiltInRegistries.ENTITY_TYPE;
        Set<String> entityTypeNames = new HashSet<>();
        for (EntityType type : EntityType.values()) {
            entityTypeNames.add(type.name());
        }
        for (net.minecraft.world.entity.EntityType<?> entity : registry) {
            Identifier resourceLocation = registry.getKey(entity);
            if (resourceLocation == null) continue;
            boolean isMod = isMods(resourceLocation);
            String entityName = getMaterialName(resourceLocation, isMod);
            if (isMod) {
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod entity type: {0}", entityName);
            } else if (!entityTypeNames.contains(entityName)) {
                debug("Skipping minecraft key entity type: {0}", entityName);
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
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod stat type: {0}", name);
            }
        }
        for (Identifier resourceLocation : BuiltInRegistries.CUSTOM_STAT) {
            Statistic statistic = STATISTICS.get(resourceLocation);
            if (statistic == null && isMods(resourceLocation)) {
                String name = normalizeName(resourceLocation.getPath());
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod custom stat: {0}", name);
            }
        }
        CraftStatistic.statistics = STATISTICS;
    }

    private static void loadSpawnCategory() {
        for (MobCategory category : MobCategory.values()) {
            try {
                CraftSpawnCategory.toBukkit(category);
            } catch (Exception e) {
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod spawn category: {0}", category.name());
            }
        }
    }

    private static void addPose() {
        for (Pose pose : Pose.values()) {
            if (pose.ordinal() > 14) {
                // Slime does not ship an enum-extension runtime, so modded entries cannot be added
                debug("Skipping mod pose: {0}", pose.name());
            }
        }
    }

    public static void addEnumTreeType() {
        for (Entry<String, TreeGrower> entry : TreeGrower.getGrowers().entrySet()) {
            String name = entry.getKey();
            if (!name.contains(":")) continue;

            String enumName = normalizeName(name);
            // Slime does not ship an enum-extension runtime: remember the modded grower so the
            // TreeGrower patch can still resolve it instead of falling back to TreeType.CUSTOM.
            treeTypeByGrowerName.put(name, treeTypeFor(enumName));
            debug("Registered mod tree type: {0}", name);
        }
    }

    /**
     * Resolve a modded grower to the closest Bukkit constant, falling back to {@link TreeType#CUSTOM}.
     */
    private static TreeType treeTypeFor(String enumName) {
        for (TreeType type : TreeType.values()) {
            if (type.name().equals(enumName)) {
                return type;
            }
        }
        return TreeType.CUSTOM;
    }

    public static boolean isMods(Identifier resourceLocation) {
        return resourceLocation != null && !resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT);
    }

    public static void reloadBukkitRegistries() {
        for (var field : org.bukkit.Registry.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                if (field.get(null) instanceof org.bukkit.Registry.SimpleRegistry<?> registry) {
                    registry.reload();
                }
            } catch (Exception e) {
                // Previously swallowed silently, which hid a broken registry until plugins started
                // throwing far away from the real cause.
                SlimeLogger.LOGGER.warning("Failed to reload Bukkit registry " + field.getName(), e);
            }
        }
    }

    public static void debug(String message, Object p0) {
        if (DEBUG) System.out.println(format(message, p0));
    }

    public static void debug(String message, Object p0, Object p1) {
        if (DEBUG) System.out.println(format(message, p0, p1));
    }

    /**
     * Minimal {@code {n}} placeholder formatting. The previous implementation chained
     * {@code String#replace("{}", ...)} which silently mis-assigned arguments.
     */
    private static String format(String message, Object... args) {
        String result = message;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return result;
    }

    /**
     * Upper-case and sanitise a registry id into something usable as an enum constant name.
     *
     * <p>{@link Locale#ROOT} is mandatory here: under a Turkish locale the default
     * {@code toUpperCase()} maps {@code i} to {@code İ}, which silently produced a different
     * (and mismatched) material name for every id containing an {@code i}.
     */
    public static String normalizeName(String name) {
        return NON_ENUM_CHARACTER.matcher(name.toUpperCase(Locale.ROOT)).replaceAll("_").replace("__", "_");
    }
}
