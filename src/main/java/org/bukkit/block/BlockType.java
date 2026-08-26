package org.bukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.block.data.type.CalibratedSculkSensor;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.CopperGolemStatue;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.CreakingHeart;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.block.data.type.HangingSign;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.block.data.type.PotentSulfur;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.block.data.type.Shelf;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.Skull;
import org.bukkit.block.data.type.TestBlock;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.Vault;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Represents a block type.
 */
@NullMarked
public interface BlockType extends Keyed, Translatable, net.kyori.adventure.translation.Translatable, io.papermc.paper.world.flag.FeatureDependant { // Paper - add translatable & feature flag API

    /**
     * Typed represents a subtype of {@link BlockType}s that have a known block
     * data type at compile time.
     *
     * @param <B> the generic type of the block data that represents the block
     * type.
     */
    interface Typed<B extends BlockData> extends BlockType {

        /**
         * Gets the BlockData class of this BlockType
         *
         * @return the BlockData class of this BlockType
         */
        @Override
        Class<B> getBlockDataClass();

        /**
         * Creates a new {@link BlockData} instance for this block type, with
         * all properties initialized to unspecified defaults.
         *
         * @param consumer consumer to run on new instance before returning
         * @return new data instance
         */
        B createBlockData(@Nullable Consumer<? super B> consumer);

        /**
         * Creates a new {@link BlockData} instance for this block type, with all
         * properties initialized to unspecified defaults.
         *
         * @return new data instance
         */
        @Override
        B createBlockData();

        /**
         * Creates a collection of {@link BlockData} instances for this block type, with all
         * possible combinations of properties values.
         *
         * @return new block data collection
         */
        @Override
        @Unmodifiable Collection<B> createBlockDataStates();

        /**
         * Creates a new {@link BlockData} instance for this block type, with all
         * properties initialized to unspecified defaults, except for those provided
         * in data.
         *
         * @param data data string
         * @return new data instance
         * @throws IllegalArgumentException if the specified data is not valid
         */
        B createBlockData(@Nullable String data);
    }

    //<editor-fold desc="BlockTypes" defaultstate="collapsed">
    // Start generate - BlockType
    Typed<Switch> ACACIA_BUTTON = getBlockType("acacia_button");

    Typed<Door> ACACIA_DOOR = getBlockType("acacia_door");

    Typed<Fence> ACACIA_FENCE = getBlockType("acacia_fence");

    Typed<Gate> ACACIA_FENCE_GATE = getBlockType("acacia_fence_gate");

    Typed<HangingSign> ACACIA_HANGING_SIGN = getBlockType("acacia_hanging_sign");

    Typed<Leaves> ACACIA_LEAVES = getBlockType("acacia_leaves");

    Typed<Orientable> ACACIA_LOG = getBlockType("acacia_log");

    Typed<BlockData> ACACIA_PLANKS = getBlockType("acacia_planks");

    Typed<Powerable> ACACIA_PRESSURE_PLATE = getBlockType("acacia_pressure_plate");

    Typed<Sapling> ACACIA_SAPLING = getBlockType("acacia_sapling");

    Typed<Shelf> ACACIA_SHELF = getBlockType("acacia_shelf");

    Typed<Sign> ACACIA_SIGN = getBlockType("acacia_sign");

    Typed<Slab> ACACIA_SLAB = getBlockType("acacia_slab");

    Typed<Stairs> ACACIA_STAIRS = getBlockType("acacia_stairs");

    Typed<TrapDoor> ACACIA_TRAPDOOR = getBlockType("acacia_trapdoor");

    Typed<WallHangingSign> ACACIA_WALL_HANGING_SIGN = getBlockType("acacia_wall_hanging_sign");

    Typed<WallSign> ACACIA_WALL_SIGN = getBlockType("acacia_wall_sign");

    Typed<Orientable> ACACIA_WOOD = getBlockType("acacia_wood");

    Typed<RedstoneRail> ACTIVATOR_RAIL = getBlockType("activator_rail");

    Typed<BlockData> AIR = getBlockType("air");

    Typed<BlockData> ALLIUM = getBlockType("allium");

    Typed<BlockData> AMETHYST_BLOCK = getBlockType("amethyst_block");

    Typed<AmethystCluster> AMETHYST_CLUSTER = getBlockType("amethyst_cluster");

    Typed<BlockData> ANCIENT_DEBRIS = getBlockType("ancient_debris");

    Typed<BlockData> ANDESITE = getBlockType("andesite");

    Typed<Slab> ANDESITE_SLAB = getBlockType("andesite_slab");

    Typed<Stairs> ANDESITE_STAIRS = getBlockType("andesite_stairs");

    Typed<Wall> ANDESITE_WALL = getBlockType("andesite_wall");

    Typed<Directional> ANVIL = getBlockType("anvil");

    Typed<Directional> ATTACHED_MELON_STEM = getBlockType("attached_melon_stem");

    Typed<Directional> ATTACHED_PUMPKIN_STEM = getBlockType("attached_pumpkin_stem");

    Typed<BlockData> AZALEA = getBlockType("azalea");

    Typed<Leaves> AZALEA_LEAVES = getBlockType("azalea_leaves");

    Typed<BlockData> AZURE_BLUET = getBlockType("azure_bluet");

    Typed<Bamboo> BAMBOO = getBlockType("bamboo");

    Typed<Orientable> BAMBOO_BLOCK = getBlockType("bamboo_block");

    Typed<Switch> BAMBOO_BUTTON = getBlockType("bamboo_button");

    Typed<Door> BAMBOO_DOOR = getBlockType("bamboo_door");

    Typed<Fence> BAMBOO_FENCE = getBlockType("bamboo_fence");

    Typed<Gate> BAMBOO_FENCE_GATE = getBlockType("bamboo_fence_gate");

    Typed<HangingSign> BAMBOO_HANGING_SIGN = getBlockType("bamboo_hanging_sign");

    Typed<BlockData> BAMBOO_MOSAIC = getBlockType("bamboo_mosaic");

    Typed<Slab> BAMBOO_MOSAIC_SLAB = getBlockType("bamboo_mosaic_slab");

    Typed<Stairs> BAMBOO_MOSAIC_STAIRS = getBlockType("bamboo_mosaic_stairs");

    Typed<BlockData> BAMBOO_PLANKS = getBlockType("bamboo_planks");

    Typed<Powerable> BAMBOO_PRESSURE_PLATE = getBlockType("bamboo_pressure_plate");

    Typed<BlockData> BAMBOO_SAPLING = getBlockType("bamboo_sapling");

    Typed<Shelf> BAMBOO_SHELF = getBlockType("bamboo_shelf");

    Typed<Sign> BAMBOO_SIGN = getBlockType("bamboo_sign");

    Typed<Slab> BAMBOO_SLAB = getBlockType("bamboo_slab");

    Typed<Stairs> BAMBOO_STAIRS = getBlockType("bamboo_stairs");

    Typed<TrapDoor> BAMBOO_TRAPDOOR = getBlockType("bamboo_trapdoor");

    Typed<WallHangingSign> BAMBOO_WALL_HANGING_SIGN = getBlockType("bamboo_wall_hanging_sign");

    Typed<WallSign> BAMBOO_WALL_SIGN = getBlockType("bamboo_wall_sign");

    Typed<Barrel> BARREL = getBlockType("barrel");

    Typed<Waterlogged> BARRIER = getBlockType("barrier");

    Typed<Orientable> BASALT = getBlockType("basalt");

    Typed<BlockData> BEACON = getBlockType("beacon");

    Typed<BlockData> BEDROCK = getBlockType("bedrock");

    Typed<Beehive> BEE_NEST = getBlockType("bee_nest");

    Typed<Beehive> BEEHIVE = getBlockType("beehive");

    Typed<Ageable> BEETROOTS = getBlockType("beetroots");

    Typed<Bell> BELL = getBlockType("bell");

    Typed<BigDripleaf> BIG_DRIPLEAF = getBlockType("big_dripleaf");

    Typed<Dripleaf> BIG_DRIPLEAF_STEM = getBlockType("big_dripleaf_stem");

    Typed<Switch> BIRCH_BUTTON = getBlockType("birch_button");

    Typed<Door> BIRCH_DOOR = getBlockType("birch_door");

    Typed<Fence> BIRCH_FENCE = getBlockType("birch_fence");

    Typed<Gate> BIRCH_FENCE_GATE = getBlockType("birch_fence_gate");

    Typed<HangingSign> BIRCH_HANGING_SIGN = getBlockType("birch_hanging_sign");

    Typed<Leaves> BIRCH_LEAVES = getBlockType("birch_leaves");

    Typed<Orientable> BIRCH_LOG = getBlockType("birch_log");

    Typed<BlockData> BIRCH_PLANKS = getBlockType("birch_planks");

    Typed<Powerable> BIRCH_PRESSURE_PLATE = getBlockType("birch_pressure_plate");

    Typed<Sapling> BIRCH_SAPLING = getBlockType("birch_sapling");

    Typed<Shelf> BIRCH_SHELF = getBlockType("birch_shelf");

    Typed<Sign> BIRCH_SIGN = getBlockType("birch_sign");

    Typed<Slab> BIRCH_SLAB = getBlockType("birch_slab");

    Typed<Stairs> BIRCH_STAIRS = getBlockType("birch_stairs");

    Typed<TrapDoor> BIRCH_TRAPDOOR = getBlockType("birch_trapdoor");

    Typed<WallHangingSign> BIRCH_WALL_HANGING_SIGN = getBlockType("birch_wall_hanging_sign");

    Typed<WallSign> BIRCH_WALL_SIGN = getBlockType("birch_wall_sign");

    Typed<Orientable> BIRCH_WOOD = getBlockType("birch_wood");

    Typed<Rotatable> BLACK_BANNER = getBlockType("black_banner");

    Typed<Bed> BLACK_BED = getBlockType("black_bed");

    Typed<Candle> BLACK_CANDLE = getBlockType("black_candle");

    Typed<Lightable> BLACK_CANDLE_CAKE = getBlockType("black_candle_cake");

    Typed<BlockData> BLACK_CARPET = getBlockType("black_carpet");

    Typed<BlockData> BLACK_CONCRETE = getBlockType("black_concrete");

    Typed<BlockData> BLACK_CONCRETE_POWDER = getBlockType("black_concrete_powder");

    Typed<Directional> BLACK_GLAZED_TERRACOTTA = getBlockType("black_glazed_terracotta");

    Typed<Directional> BLACK_SHULKER_BOX = getBlockType("black_shulker_box");

    Typed<BlockData> BLACK_STAINED_GLASS = getBlockType("black_stained_glass");

    Typed<GlassPane> BLACK_STAINED_GLASS_PANE = getBlockType("black_stained_glass_pane");

    Typed<BlockData> BLACK_TERRACOTTA = getBlockType("black_terracotta");

    Typed<Directional> BLACK_WALL_BANNER = getBlockType("black_wall_banner");

    Typed<BlockData> BLACK_WOOL = getBlockType("black_wool");

    Typed<BlockData> BLACKSTONE = getBlockType("blackstone");

    Typed<Slab> BLACKSTONE_SLAB = getBlockType("blackstone_slab");

    Typed<Stairs> BLACKSTONE_STAIRS = getBlockType("blackstone_stairs");

    Typed<Wall> BLACKSTONE_WALL = getBlockType("blackstone_wall");

    Typed<Furnace> BLAST_FURNACE = getBlockType("blast_furnace");

    Typed<Rotatable> BLUE_BANNER = getBlockType("blue_banner");

    Typed<Bed> BLUE_BED = getBlockType("blue_bed");

    Typed<Candle> BLUE_CANDLE = getBlockType("blue_candle");

    Typed<Lightable> BLUE_CANDLE_CAKE = getBlockType("blue_candle_cake");

    Typed<BlockData> BLUE_CARPET = getBlockType("blue_carpet");

    Typed<BlockData> BLUE_CONCRETE = getBlockType("blue_concrete");

    Typed<BlockData> BLUE_CONCRETE_POWDER = getBlockType("blue_concrete_powder");

    Typed<Directional> BLUE_GLAZED_TERRACOTTA = getBlockType("blue_glazed_terracotta");

    Typed<BlockData> BLUE_ICE = getBlockType("blue_ice");

    Typed<BlockData> BLUE_ORCHID = getBlockType("blue_orchid");

    Typed<Directional> BLUE_SHULKER_BOX = getBlockType("blue_shulker_box");

    Typed<BlockData> BLUE_STAINED_GLASS = getBlockType("blue_stained_glass");

    Typed<GlassPane> BLUE_STAINED_GLASS_PANE = getBlockType("blue_stained_glass_pane");

    Typed<BlockData> BLUE_TERRACOTTA = getBlockType("blue_terracotta");

    Typed<Directional> BLUE_WALL_BANNER = getBlockType("blue_wall_banner");

    Typed<BlockData> BLUE_WOOL = getBlockType("blue_wool");

    Typed<Orientable> BONE_BLOCK = getBlockType("bone_block");

    Typed<BlockData> BOOKSHELF = getBlockType("bookshelf");

    Typed<Waterlogged> BRAIN_CORAL = getBlockType("brain_coral");

    Typed<BlockData> BRAIN_CORAL_BLOCK = getBlockType("brain_coral_block");

    Typed<Waterlogged> BRAIN_CORAL_FAN = getBlockType("brain_coral_fan");

    Typed<CoralWallFan> BRAIN_CORAL_WALL_FAN = getBlockType("brain_coral_wall_fan");

    Typed<BrewingStand> BREWING_STAND = getBlockType("brewing_stand");

    Typed<Slab> BRICK_SLAB = getBlockType("brick_slab");

    Typed<Stairs> BRICK_STAIRS = getBlockType("brick_stairs");

    Typed<Wall> BRICK_WALL = getBlockType("brick_wall");

    Typed<BlockData> BRICKS = getBlockType("bricks");

    Typed<Rotatable> BROWN_BANNER = getBlockType("brown_banner");

    Typed<Bed> BROWN_BED = getBlockType("brown_bed");

    Typed<Candle> BROWN_CANDLE = getBlockType("brown_candle");

    Typed<Lightable> BROWN_CANDLE_CAKE = getBlockType("brown_candle_cake");

    Typed<BlockData> BROWN_CARPET = getBlockType("brown_carpet");

    Typed<BlockData> BROWN_CONCRETE = getBlockType("brown_concrete");

    Typed<BlockData> BROWN_CONCRETE_POWDER = getBlockType("brown_concrete_powder");

    Typed<Directional> BROWN_GLAZED_TERRACOTTA = getBlockType("brown_glazed_terracotta");

    Typed<BlockData> BROWN_MUSHROOM = getBlockType("brown_mushroom");

    Typed<MultipleFacing> BROWN_MUSHROOM_BLOCK = getBlockType("brown_mushroom_block");

    Typed<Directional> BROWN_SHULKER_BOX = getBlockType("brown_shulker_box");

    Typed<BlockData> BROWN_STAINED_GLASS = getBlockType("brown_stained_glass");

    Typed<GlassPane> BROWN_STAINED_GLASS_PANE = getBlockType("brown_stained_glass_pane");

    Typed<BlockData> BROWN_TERRACOTTA = getBlockType("brown_terracotta");

    Typed<Directional> BROWN_WALL_BANNER = getBlockType("brown_wall_banner");

    Typed<BlockData> BROWN_WOOL = getBlockType("brown_wool");

    Typed<BubbleColumn> BUBBLE_COLUMN = getBlockType("bubble_column");

    Typed<Waterlogged> BUBBLE_CORAL = getBlockType("bubble_coral");

    Typed<BlockData> BUBBLE_CORAL_BLOCK = getBlockType("bubble_coral_block");

    Typed<Waterlogged> BUBBLE_CORAL_FAN = getBlockType("bubble_coral_fan");

    Typed<CoralWallFan> BUBBLE_CORAL_WALL_FAN = getBlockType("bubble_coral_wall_fan");

    Typed<BlockData> BUDDING_AMETHYST = getBlockType("budding_amethyst");

    Typed<BlockData> BUSH = getBlockType("bush");

    Typed<Ageable> CACTUS = getBlockType("cactus");

    Typed<BlockData> CACTUS_FLOWER = getBlockType("cactus_flower");

    Typed<Cake> CAKE = getBlockType("cake");

    Typed<BlockData> CALCITE = getBlockType("calcite");

    Typed<CalibratedSculkSensor> CALIBRATED_SCULK_SENSOR = getBlockType("calibrated_sculk_sensor");

    Typed<Campfire> CAMPFIRE = getBlockType("campfire");

    Typed<Candle> CANDLE = getBlockType("candle");

    Typed<Lightable> CANDLE_CAKE = getBlockType("candle_cake");

    Typed<Ageable> CARROTS = getBlockType("carrots");

    Typed<BlockData> CARTOGRAPHY_TABLE = getBlockType("cartography_table");

    Typed<Directional> CARVED_PUMPKIN = getBlockType("carved_pumpkin");

    Typed<BlockData> CAULDRON = getBlockType("cauldron");

    Typed<BlockData> CAVE_AIR = getBlockType("cave_air");

    Typed<CaveVines> CAVE_VINES = getBlockType("cave_vines");

    Typed<CaveVinesPlant> CAVE_VINES_PLANT = getBlockType("cave_vines_plant");

    Typed<CommandBlock> CHAIN_COMMAND_BLOCK = getBlockType("chain_command_block");

    Typed<Switch> CHERRY_BUTTON = getBlockType("cherry_button");

    Typed<Door> CHERRY_DOOR = getBlockType("cherry_door");

    Typed<Fence> CHERRY_FENCE = getBlockType("cherry_fence");

    Typed<Gate> CHERRY_FENCE_GATE = getBlockType("cherry_fence_gate");

    Typed<HangingSign> CHERRY_HANGING_SIGN = getBlockType("cherry_hanging_sign");

    Typed<Leaves> CHERRY_LEAVES = getBlockType("cherry_leaves");

    Typed<Orientable> CHERRY_LOG = getBlockType("cherry_log");

    Typed<BlockData> CHERRY_PLANKS = getBlockType("cherry_planks");

    Typed<Powerable> CHERRY_PRESSURE_PLATE = getBlockType("cherry_pressure_plate");

    Typed<Sapling> CHERRY_SAPLING = getBlockType("cherry_sapling");

    Typed<Shelf> CHERRY_SHELF = getBlockType("cherry_shelf");

    Typed<Sign> CHERRY_SIGN = getBlockType("cherry_sign");

    Typed<Slab> CHERRY_SLAB = getBlockType("cherry_slab");

    Typed<Stairs> CHERRY_STAIRS = getBlockType("cherry_stairs");

    Typed<TrapDoor> CHERRY_TRAPDOOR = getBlockType("cherry_trapdoor");

    Typed<WallHangingSign> CHERRY_WALL_HANGING_SIGN = getBlockType("cherry_wall_hanging_sign");

    Typed<WallSign> CHERRY_WALL_SIGN = getBlockType("cherry_wall_sign");

    Typed<Orientable> CHERRY_WOOD = getBlockType("cherry_wood");

    Typed<Chest> CHEST = getBlockType("chest");

    Typed<Directional> CHIPPED_ANVIL = getBlockType("chipped_anvil");

    Typed<ChiseledBookshelf> CHISELED_BOOKSHELF = getBlockType("chiseled_bookshelf");

    Typed<BlockData> CHISELED_CINNABAR = getBlockType("chiseled_cinnabar");

    Typed<BlockData> CHISELED_COPPER = getBlockType("chiseled_copper");

    Typed<BlockData> CHISELED_DEEPSLATE = getBlockType("chiseled_deepslate");

    Typed<BlockData> CHISELED_NETHER_BRICKS = getBlockType("chiseled_nether_bricks");

    Typed<BlockData> CHISELED_POLISHED_BLACKSTONE = getBlockType("chiseled_polished_blackstone");

    Typed<BlockData> CHISELED_QUARTZ_BLOCK = getBlockType("chiseled_quartz_block");

    Typed<BlockData> CHISELED_RED_SANDSTONE = getBlockType("chiseled_red_sandstone");

    Typed<BlockData> CHISELED_RESIN_BRICKS = getBlockType("chiseled_resin_bricks");

    Typed<BlockData> CHISELED_SANDSTONE = getBlockType("chiseled_sandstone");

    Typed<BlockData> CHISELED_STONE_BRICKS = getBlockType("chiseled_stone_bricks");

    Typed<BlockData> CHISELED_SULFUR = getBlockType("chiseled_sulfur");

    Typed<BlockData> CHISELED_TUFF = getBlockType("chiseled_tuff");

    Typed<BlockData> CHISELED_TUFF_BRICKS = getBlockType("chiseled_tuff_bricks");

    Typed<Ageable> CHORUS_FLOWER = getBlockType("chorus_flower");

    Typed<MultipleFacing> CHORUS_PLANT = getBlockType("chorus_plant");

    Typed<BlockData> CINNABAR = getBlockType("cinnabar");

    Typed<Slab> CINNABAR_BRICK_SLAB = getBlockType("cinnabar_brick_slab");

    Typed<Stairs> CINNABAR_BRICK_STAIRS = getBlockType("cinnabar_brick_stairs");

    Typed<Wall> CINNABAR_BRICK_WALL = getBlockType("cinnabar_brick_wall");

    Typed<BlockData> CINNABAR_BRICKS = getBlockType("cinnabar_bricks");

    Typed<Slab> CINNABAR_SLAB = getBlockType("cinnabar_slab");

    Typed<Stairs> CINNABAR_STAIRS = getBlockType("cinnabar_stairs");

    Typed<Wall> CINNABAR_WALL = getBlockType("cinnabar_wall");

    Typed<BlockData> CLAY = getBlockType("clay");

    Typed<BlockData> CLOSED_EYEBLOSSOM = getBlockType("closed_eyeblossom");

    Typed<BlockData> COAL_BLOCK = getBlockType("coal_block");

    Typed<BlockData> COAL_ORE = getBlockType("coal_ore");

    Typed<BlockData> COARSE_DIRT = getBlockType("coarse_dirt");

    Typed<BlockData> COBBLED_DEEPSLATE = getBlockType("cobbled_deepslate");

    Typed<Slab> COBBLED_DEEPSLATE_SLAB = getBlockType("cobbled_deepslate_slab");

    Typed<Stairs> COBBLED_DEEPSLATE_STAIRS = getBlockType("cobbled_deepslate_stairs");

    Typed<Wall> COBBLED_DEEPSLATE_WALL = getBlockType("cobbled_deepslate_wall");

    Typed<BlockData> COBBLESTONE = getBlockType("cobblestone");

    Typed<Slab> COBBLESTONE_SLAB = getBlockType("cobblestone_slab");

    Typed<Stairs> COBBLESTONE_STAIRS = getBlockType("cobblestone_stairs");

    Typed<Wall> COBBLESTONE_WALL = getBlockType("cobblestone_wall");

    Typed<BlockData> COBWEB = getBlockType("cobweb");

    Typed<Cocoa> COCOA = getBlockType("cocoa");

    Typed<CommandBlock> COMMAND_BLOCK = getBlockType("command_block");

    Typed<Comparator> COMPARATOR = getBlockType("comparator");

    Typed<Levelled> COMPOSTER = getBlockType("composter");

    Typed<Waterlogged> CONDUIT = getBlockType("conduit");

    Typed<Fence> COPPER_BARS = getBlockType("copper_bars");

    Typed<BlockData> COPPER_BLOCK = getBlockType("copper_block");

    Typed<CopperBulb> COPPER_BULB = getBlockType("copper_bulb");

    Typed<Chain> COPPER_CHAIN = getBlockType("copper_chain");

    Typed<Chest> COPPER_CHEST = getBlockType("copper_chest");

    Typed<Door> COPPER_DOOR = getBlockType("copper_door");

    Typed<CopperGolemStatue> COPPER_GOLEM_STATUE = getBlockType("copper_golem_statue");

    Typed<Waterlogged> COPPER_GRATE = getBlockType("copper_grate");

    Typed<Lantern> COPPER_LANTERN = getBlockType("copper_lantern");

    Typed<BlockData> COPPER_ORE = getBlockType("copper_ore");

    Typed<BlockData> COPPER_TORCH = getBlockType("copper_torch");

    Typed<TrapDoor> COPPER_TRAPDOOR = getBlockType("copper_trapdoor");

    Typed<Directional> COPPER_WALL_TORCH = getBlockType("copper_wall_torch");

    Typed<BlockData> CORNFLOWER = getBlockType("cornflower");

    Typed<BlockData> CRACKED_DEEPSLATE_BRICKS = getBlockType("cracked_deepslate_bricks");

    Typed<BlockData> CRACKED_DEEPSLATE_TILES = getBlockType("cracked_deepslate_tiles");

    Typed<BlockData> CRACKED_NETHER_BRICKS = getBlockType("cracked_nether_bricks");

    Typed<BlockData> CRACKED_POLISHED_BLACKSTONE_BRICKS = getBlockType("cracked_polished_blackstone_bricks");

    Typed<BlockData> CRACKED_STONE_BRICKS = getBlockType("cracked_stone_bricks");

    Typed<Crafter> CRAFTER = getBlockType("crafter");

    Typed<BlockData> CRAFTING_TABLE = getBlockType("crafting_table");

    Typed<CreakingHeart> CREAKING_HEART = getBlockType("creaking_heart");

    Typed<Skull> CREEPER_HEAD = getBlockType("creeper_head");

    Typed<WallSkull> CREEPER_WALL_HEAD = getBlockType("creeper_wall_head");

    Typed<Switch> CRIMSON_BUTTON = getBlockType("crimson_button");

    Typed<Door> CRIMSON_DOOR = getBlockType("crimson_door");

    Typed<Fence> CRIMSON_FENCE = getBlockType("crimson_fence");

    Typed<Gate> CRIMSON_FENCE_GATE = getBlockType("crimson_fence_gate");

    Typed<BlockData> CRIMSON_FUNGUS = getBlockType("crimson_fungus");

    Typed<HangingSign> CRIMSON_HANGING_SIGN = getBlockType("crimson_hanging_sign");

    Typed<Orientable> CRIMSON_HYPHAE = getBlockType("crimson_hyphae");

    Typed<BlockData> CRIMSON_NYLIUM = getBlockType("crimson_nylium");

    Typed<BlockData> CRIMSON_PLANKS = getBlockType("crimson_planks");

    Typed<Powerable> CRIMSON_PRESSURE_PLATE = getBlockType("crimson_pressure_plate");

    Typed<BlockData> CRIMSON_ROOTS = getBlockType("crimson_roots");

    Typed<Shelf> CRIMSON_SHELF = getBlockType("crimson_shelf");

    Typed<Sign> CRIMSON_SIGN = getBlockType("crimson_sign");

    Typed<Slab> CRIMSON_SLAB = getBlockType("crimson_slab");

    Typed<Stairs> CRIMSON_STAIRS = getBlockType("crimson_stairs");

    Typed<Orientable> CRIMSON_STEM = getBlockType("crimson_stem");

    Typed<TrapDoor> CRIMSON_TRAPDOOR = getBlockType("crimson_trapdoor");

    Typed<WallHangingSign> CRIMSON_WALL_HANGING_SIGN = getBlockType("crimson_wall_hanging_sign");

    Typed<WallSign> CRIMSON_WALL_SIGN = getBlockType("crimson_wall_sign");

    Typed<BlockData> CRYING_OBSIDIAN = getBlockType("crying_obsidian");

    Typed<BlockData> CUT_COPPER = getBlockType("cut_copper");

    Typed<Slab> CUT_COPPER_SLAB = getBlockType("cut_copper_slab");

    Typed<Stairs> CUT_COPPER_STAIRS = getBlockType("cut_copper_stairs");

    Typed<BlockData> CUT_RED_SANDSTONE = getBlockType("cut_red_sandstone");

    Typed<Slab> CUT_RED_SANDSTONE_SLAB = getBlockType("cut_red_sandstone_slab");

    Typed<BlockData> CUT_SANDSTONE = getBlockType("cut_sandstone");

    Typed<Slab> CUT_SANDSTONE_SLAB = getBlockType("cut_sandstone_slab");

    Typed<Rotatable> CYAN_BANNER = getBlockType("cyan_banner");

    Typed<Bed> CYAN_BED = getBlockType("cyan_bed");

    Typed<Candle> CYAN_CANDLE = getBlockType("cyan_candle");

    Typed<Lightable> CYAN_CANDLE_CAKE = getBlockType("cyan_candle_cake");

    Typed<BlockData> CYAN_CARPET = getBlockType("cyan_carpet");

    Typed<BlockData> CYAN_CONCRETE = getBlockType("cyan_concrete");

    Typed<BlockData> CYAN_CONCRETE_POWDER = getBlockType("cyan_concrete_powder");

    Typed<Directional> CYAN_GLAZED_TERRACOTTA = getBlockType("cyan_glazed_terracotta");

    Typed<Directional> CYAN_SHULKER_BOX = getBlockType("cyan_shulker_box");

    Typed<BlockData> CYAN_STAINED_GLASS = getBlockType("cyan_stained_glass");

    Typed<GlassPane> CYAN_STAINED_GLASS_PANE = getBlockType("cyan_stained_glass_pane");

    Typed<BlockData> CYAN_TERRACOTTA = getBlockType("cyan_terracotta");

    Typed<Directional> CYAN_WALL_BANNER = getBlockType("cyan_wall_banner");

    Typed<BlockData> CYAN_WOOL = getBlockType("cyan_wool");

    Typed<Directional> DAMAGED_ANVIL = getBlockType("damaged_anvil");

    Typed<BlockData> DANDELION = getBlockType("dandelion");

    Typed<Switch> DARK_OAK_BUTTON = getBlockType("dark_oak_button");

    Typed<Door> DARK_OAK_DOOR = getBlockType("dark_oak_door");

    Typed<Fence> DARK_OAK_FENCE = getBlockType("dark_oak_fence");

    Typed<Gate> DARK_OAK_FENCE_GATE = getBlockType("dark_oak_fence_gate");

    Typed<HangingSign> DARK_OAK_HANGING_SIGN = getBlockType("dark_oak_hanging_sign");

    Typed<Leaves> DARK_OAK_LEAVES = getBlockType("dark_oak_leaves");

    Typed<Orientable> DARK_OAK_LOG = getBlockType("dark_oak_log");

    Typed<BlockData> DARK_OAK_PLANKS = getBlockType("dark_oak_planks");

    Typed<Powerable> DARK_OAK_PRESSURE_PLATE = getBlockType("dark_oak_pressure_plate");

    Typed<Sapling> DARK_OAK_SAPLING = getBlockType("dark_oak_sapling");

    Typed<Shelf> DARK_OAK_SHELF = getBlockType("dark_oak_shelf");

    Typed<Sign> DARK_OAK_SIGN = getBlockType("dark_oak_sign");

    Typed<Slab> DARK_OAK_SLAB = getBlockType("dark_oak_slab");

    Typed<Stairs> DARK_OAK_STAIRS = getBlockType("dark_oak_stairs");

    Typed<TrapDoor> DARK_OAK_TRAPDOOR = getBlockType("dark_oak_trapdoor");

    Typed<WallHangingSign> DARK_OAK_WALL_HANGING_SIGN = getBlockType("dark_oak_wall_hanging_sign");

    Typed<WallSign> DARK_OAK_WALL_SIGN = getBlockType("dark_oak_wall_sign");

    Typed<Orientable> DARK_OAK_WOOD = getBlockType("dark_oak_wood");

    Typed<BlockData> DARK_PRISMARINE = getBlockType("dark_prismarine");

    Typed<Slab> DARK_PRISMARINE_SLAB = getBlockType("dark_prismarine_slab");

    Typed<Stairs> DARK_PRISMARINE_STAIRS = getBlockType("dark_prismarine_stairs");

    Typed<DaylightDetector> DAYLIGHT_DETECTOR = getBlockType("daylight_detector");

    Typed<Waterlogged> DEAD_BRAIN_CORAL = getBlockType("dead_brain_coral");

    Typed<BlockData> DEAD_BRAIN_CORAL_BLOCK = getBlockType("dead_brain_coral_block");

    Typed<Waterlogged> DEAD_BRAIN_CORAL_FAN = getBlockType("dead_brain_coral_fan");

    Typed<CoralWallFan> DEAD_BRAIN_CORAL_WALL_FAN = getBlockType("dead_brain_coral_wall_fan");

    Typed<Waterlogged> DEAD_BUBBLE_CORAL = getBlockType("dead_bubble_coral");

    Typed<BlockData> DEAD_BUBBLE_CORAL_BLOCK = getBlockType("dead_bubble_coral_block");

    Typed<Waterlogged> DEAD_BUBBLE_CORAL_FAN = getBlockType("dead_bubble_coral_fan");

    Typed<CoralWallFan> DEAD_BUBBLE_CORAL_WALL_FAN = getBlockType("dead_bubble_coral_wall_fan");

    Typed<BlockData> DEAD_BUSH = getBlockType("dead_bush");

    Typed<Waterlogged> DEAD_FIRE_CORAL = getBlockType("dead_fire_coral");

    Typed<BlockData> DEAD_FIRE_CORAL_BLOCK = getBlockType("dead_fire_coral_block");

    Typed<Waterlogged> DEAD_FIRE_CORAL_FAN = getBlockType("dead_fire_coral_fan");

    Typed<CoralWallFan> DEAD_FIRE_CORAL_WALL_FAN = getBlockType("dead_fire_coral_wall_fan");

    Typed<Waterlogged> DEAD_HORN_CORAL = getBlockType("dead_horn_coral");

    Typed<BlockData> DEAD_HORN_CORAL_BLOCK = getBlockType("dead_horn_coral_block");

    Typed<Waterlogged> DEAD_HORN_CORAL_FAN = getBlockType("dead_horn_coral_fan");

    Typed<CoralWallFan> DEAD_HORN_CORAL_WALL_FAN = getBlockType("dead_horn_coral_wall_fan");

    Typed<Waterlogged> DEAD_TUBE_CORAL = getBlockType("dead_tube_coral");

    Typed<BlockData> DEAD_TUBE_CORAL_BLOCK = getBlockType("dead_tube_coral_block");

    Typed<Waterlogged> DEAD_TUBE_CORAL_FAN = getBlockType("dead_tube_coral_fan");

    Typed<CoralWallFan> DEAD_TUBE_CORAL_WALL_FAN = getBlockType("dead_tube_coral_wall_fan");

    Typed<DecoratedPot> DECORATED_POT = getBlockType("decorated_pot");

    Typed<Orientable> DEEPSLATE = getBlockType("deepslate");

    Typed<Slab> DEEPSLATE_BRICK_SLAB = getBlockType("deepslate_brick_slab");

    Typed<Stairs> DEEPSLATE_BRICK_STAIRS = getBlockType("deepslate_brick_stairs");

    Typed<Wall> DEEPSLATE_BRICK_WALL = getBlockType("deepslate_brick_wall");

    Typed<BlockData> DEEPSLATE_BRICKS = getBlockType("deepslate_bricks");

    Typed<BlockData> DEEPSLATE_COAL_ORE = getBlockType("deepslate_coal_ore");

    Typed<BlockData> DEEPSLATE_COPPER_ORE = getBlockType("deepslate_copper_ore");

    Typed<BlockData> DEEPSLATE_DIAMOND_ORE = getBlockType("deepslate_diamond_ore");

    Typed<BlockData> DEEPSLATE_EMERALD_ORE = getBlockType("deepslate_emerald_ore");

    Typed<BlockData> DEEPSLATE_GOLD_ORE = getBlockType("deepslate_gold_ore");

    Typed<BlockData> DEEPSLATE_IRON_ORE = getBlockType("deepslate_iron_ore");

    Typed<BlockData> DEEPSLATE_LAPIS_ORE = getBlockType("deepslate_lapis_ore");

    Typed<Lightable> DEEPSLATE_REDSTONE_ORE = getBlockType("deepslate_redstone_ore");

    Typed<Slab> DEEPSLATE_TILE_SLAB = getBlockType("deepslate_tile_slab");

    Typed<Stairs> DEEPSLATE_TILE_STAIRS = getBlockType("deepslate_tile_stairs");

    Typed<Wall> DEEPSLATE_TILE_WALL = getBlockType("deepslate_tile_wall");

    Typed<BlockData> DEEPSLATE_TILES = getBlockType("deepslate_tiles");

    Typed<RedstoneRail> DETECTOR_RAIL = getBlockType("detector_rail");

    Typed<BlockData> DIAMOND_BLOCK = getBlockType("diamond_block");

    Typed<BlockData> DIAMOND_ORE = getBlockType("diamond_ore");

    Typed<BlockData> DIORITE = getBlockType("diorite");

    Typed<Slab> DIORITE_SLAB = getBlockType("diorite_slab");

    Typed<Stairs> DIORITE_STAIRS = getBlockType("diorite_stairs");

    Typed<Wall> DIORITE_WALL = getBlockType("diorite_wall");

    Typed<BlockData> DIRT = getBlockType("dirt");

    Typed<BlockData> DIRT_PATH = getBlockType("dirt_path");

    Typed<Dispenser> DISPENSER = getBlockType("dispenser");

    Typed<BlockData> DRAGON_EGG = getBlockType("dragon_egg");

    Typed<Skull> DRAGON_HEAD = getBlockType("dragon_head");

    Typed<WallSkull> DRAGON_WALL_HEAD = getBlockType("dragon_wall_head");

    Typed<DriedGhast> DRIED_GHAST = getBlockType("dried_ghast");

    Typed<BlockData> DRIED_KELP_BLOCK = getBlockType("dried_kelp_block");

    Typed<BlockData> DRIPSTONE_BLOCK = getBlockType("dripstone_block");

    Typed<Dispenser> DROPPER = getBlockType("dropper");

    Typed<BlockData> EMERALD_BLOCK = getBlockType("emerald_block");

    Typed<BlockData> EMERALD_ORE = getBlockType("emerald_ore");

    Typed<BlockData> ENCHANTING_TABLE = getBlockType("enchanting_table");

    Typed<BlockData> END_GATEWAY = getBlockType("end_gateway");

    Typed<BlockData> END_PORTAL = getBlockType("end_portal");

    Typed<EndPortalFrame> END_PORTAL_FRAME = getBlockType("end_portal_frame");

    Typed<Directional> END_ROD = getBlockType("end_rod");

    Typed<BlockData> END_STONE = getBlockType("end_stone");

    Typed<Slab> END_STONE_BRICK_SLAB = getBlockType("end_stone_brick_slab");

    Typed<Stairs> END_STONE_BRICK_STAIRS = getBlockType("end_stone_brick_stairs");

    Typed<Wall> END_STONE_BRICK_WALL = getBlockType("end_stone_brick_wall");

    Typed<BlockData> END_STONE_BRICKS = getBlockType("end_stone_bricks");

    Typed<EnderChest> ENDER_CHEST = getBlockType("ender_chest");

    Typed<BlockData> EXPOSED_CHISELED_COPPER = getBlockType("exposed_chiseled_copper");

    Typed<BlockData> EXPOSED_COPPER = getBlockType("exposed_copper");

    Typed<Fence> EXPOSED_COPPER_BARS = getBlockType("exposed_copper_bars");

    Typed<CopperBulb> EXPOSED_COPPER_BULB = getBlockType("exposed_copper_bulb");

    Typed<Chain> EXPOSED_COPPER_CHAIN = getBlockType("exposed_copper_chain");

    Typed<Chest> EXPOSED_COPPER_CHEST = getBlockType("exposed_copper_chest");

    Typed<Door> EXPOSED_COPPER_DOOR = getBlockType("exposed_copper_door");

    Typed<CopperGolemStatue> EXPOSED_COPPER_GOLEM_STATUE = getBlockType("exposed_copper_golem_statue");

    Typed<Waterlogged> EXPOSED_COPPER_GRATE = getBlockType("exposed_copper_grate");

    Typed<Lantern> EXPOSED_COPPER_LANTERN = getBlockType("exposed_copper_lantern");

    Typed<TrapDoor> EXPOSED_COPPER_TRAPDOOR = getBlockType("exposed_copper_trapdoor");

    Typed<BlockData> EXPOSED_CUT_COPPER = getBlockType("exposed_cut_copper");

    Typed<Slab> EXPOSED_CUT_COPPER_SLAB = getBlockType("exposed_cut_copper_slab");

    Typed<Stairs> EXPOSED_CUT_COPPER_STAIRS = getBlockType("exposed_cut_copper_stairs");

    Typed<LightningRod> EXPOSED_LIGHTNING_ROD = getBlockType("exposed_lightning_rod");

    Typed<Farmland> FARMLAND = getBlockType("farmland");

    Typed<BlockData> FERN = getBlockType("fern");

    Typed<Fire> FIRE = getBlockType("fire");

    Typed<Waterlogged> FIRE_CORAL = getBlockType("fire_coral");

    Typed<BlockData> FIRE_CORAL_BLOCK = getBlockType("fire_coral_block");

    Typed<Waterlogged> FIRE_CORAL_FAN = getBlockType("fire_coral_fan");

    Typed<CoralWallFan> FIRE_CORAL_WALL_FAN = getBlockType("fire_coral_wall_fan");

    Typed<BlockData> FIREFLY_BUSH = getBlockType("firefly_bush");

    Typed<BlockData> FLETCHING_TABLE = getBlockType("fletching_table");

    Typed<BlockData> FLOWER_POT = getBlockType("flower_pot");

    Typed<BlockData> FLOWERING_AZALEA = getBlockType("flowering_azalea");

    Typed<Leaves> FLOWERING_AZALEA_LEAVES = getBlockType("flowering_azalea_leaves");

    Typed<BlockData> FROGSPAWN = getBlockType("frogspawn");

    Typed<Ageable> FROSTED_ICE = getBlockType("frosted_ice");

    Typed<Furnace> FURNACE = getBlockType("furnace");

    Typed<BlockData> GILDED_BLACKSTONE = getBlockType("gilded_blackstone");

    Typed<BlockData> GLASS = getBlockType("glass");

    Typed<Fence> GLASS_PANE = getBlockType("glass_pane");

    Typed<GlowLichen> GLOW_LICHEN = getBlockType("glow_lichen");

    Typed<BlockData> GLOWSTONE = getBlockType("glowstone");

    Typed<BlockData> GOLD_BLOCK = getBlockType("gold_block");

    Typed<BlockData> GOLD_ORE = getBlockType("gold_ore");

    Typed<BlockData> GOLDEN_DANDELION = getBlockType("golden_dandelion");

    Typed<BlockData> GRANITE = getBlockType("granite");

    Typed<Slab> GRANITE_SLAB = getBlockType("granite_slab");

    Typed<Stairs> GRANITE_STAIRS = getBlockType("granite_stairs");

    Typed<Wall> GRANITE_WALL = getBlockType("granite_wall");

    Typed<Snowable> GRASS_BLOCK = getBlockType("grass_block");

    Typed<BlockData> GRAVEL = getBlockType("gravel");

    Typed<Rotatable> GRAY_BANNER = getBlockType("gray_banner");

    Typed<Bed> GRAY_BED = getBlockType("gray_bed");

    Typed<Candle> GRAY_CANDLE = getBlockType("gray_candle");

    Typed<Lightable> GRAY_CANDLE_CAKE = getBlockType("gray_candle_cake");

    Typed<BlockData> GRAY_CARPET = getBlockType("gray_carpet");

    Typed<BlockData> GRAY_CONCRETE = getBlockType("gray_concrete");

    Typed<BlockData> GRAY_CONCRETE_POWDER = getBlockType("gray_concrete_powder");

    Typed<Directional> GRAY_GLAZED_TERRACOTTA = getBlockType("gray_glazed_terracotta");

    Typed<Directional> GRAY_SHULKER_BOX = getBlockType("gray_shulker_box");

    Typed<BlockData> GRAY_STAINED_GLASS = getBlockType("gray_stained_glass");

    Typed<GlassPane> GRAY_STAINED_GLASS_PANE = getBlockType("gray_stained_glass_pane");

    Typed<BlockData> GRAY_TERRACOTTA = getBlockType("gray_terracotta");

    Typed<Directional> GRAY_WALL_BANNER = getBlockType("gray_wall_banner");

    Typed<BlockData> GRAY_WOOL = getBlockType("gray_wool");

    Typed<Rotatable> GREEN_BANNER = getBlockType("green_banner");

    Typed<Bed> GREEN_BED = getBlockType("green_bed");

    Typed<Candle> GREEN_CANDLE = getBlockType("green_candle");

    Typed<Lightable> GREEN_CANDLE_CAKE = getBlockType("green_candle_cake");

    Typed<BlockData> GREEN_CARPET = getBlockType("green_carpet");

    Typed<BlockData> GREEN_CONCRETE = getBlockType("green_concrete");

    Typed<BlockData> GREEN_CONCRETE_POWDER = getBlockType("green_concrete_powder");

    Typed<Directional> GREEN_GLAZED_TERRACOTTA = getBlockType("green_glazed_terracotta");

    Typed<Directional> GREEN_SHULKER_BOX = getBlockType("green_shulker_box");

    Typed<BlockData> GREEN_STAINED_GLASS = getBlockType("green_stained_glass");

    Typed<GlassPane> GREEN_STAINED_GLASS_PANE = getBlockType("green_stained_glass_pane");

    Typed<BlockData> GREEN_TERRACOTTA = getBlockType("green_terracotta");

    Typed<Directional> GREEN_WALL_BANNER = getBlockType("green_wall_banner");

    Typed<BlockData> GREEN_WOOL = getBlockType("green_wool");

    Typed<Grindstone> GRINDSTONE = getBlockType("grindstone");

    Typed<Waterlogged> HANGING_ROOTS = getBlockType("hanging_roots");

    Typed<Orientable> HAY_BLOCK = getBlockType("hay_block");

    Typed<Waterlogged> HEAVY_CORE = getBlockType("heavy_core");

    Typed<AnaloguePowerable> HEAVY_WEIGHTED_PRESSURE_PLATE = getBlockType("heavy_weighted_pressure_plate");

    Typed<BlockData> HONEY_BLOCK = getBlockType("honey_block");

    Typed<BlockData> HONEYCOMB_BLOCK = getBlockType("honeycomb_block");

    Typed<Hopper> HOPPER = getBlockType("hopper");

    Typed<Waterlogged> HORN_CORAL = getBlockType("horn_coral");

    Typed<BlockData> HORN_CORAL_BLOCK = getBlockType("horn_coral_block");

    Typed<Waterlogged> HORN_CORAL_FAN = getBlockType("horn_coral_fan");

    Typed<CoralWallFan> HORN_CORAL_WALL_FAN = getBlockType("horn_coral_wall_fan");

    Typed<BlockData> ICE = getBlockType("ice");

    Typed<BlockData> INFESTED_CHISELED_STONE_BRICKS = getBlockType("infested_chiseled_stone_bricks");

    Typed<BlockData> INFESTED_COBBLESTONE = getBlockType("infested_cobblestone");

    Typed<BlockData> INFESTED_CRACKED_STONE_BRICKS = getBlockType("infested_cracked_stone_bricks");

    Typed<Orientable> INFESTED_DEEPSLATE = getBlockType("infested_deepslate");

    Typed<BlockData> INFESTED_MOSSY_STONE_BRICKS = getBlockType("infested_mossy_stone_bricks");

    Typed<BlockData> INFESTED_STONE = getBlockType("infested_stone");

    Typed<BlockData> INFESTED_STONE_BRICKS = getBlockType("infested_stone_bricks");

    Typed<Fence> IRON_BARS = getBlockType("iron_bars");

    Typed<BlockData> IRON_BLOCK = getBlockType("iron_block");

    Typed<Chain> IRON_CHAIN = getBlockType("iron_chain");

    Typed<Door> IRON_DOOR = getBlockType("iron_door");

    Typed<BlockData> IRON_ORE = getBlockType("iron_ore");

    Typed<TrapDoor> IRON_TRAPDOOR = getBlockType("iron_trapdoor");

    Typed<Directional> JACK_O_LANTERN = getBlockType("jack_o_lantern");

    Typed<Jigsaw> JIGSAW = getBlockType("jigsaw");

    Typed<Jukebox> JUKEBOX = getBlockType("jukebox");

    Typed<Switch> JUNGLE_BUTTON = getBlockType("jungle_button");

    Typed<Door> JUNGLE_DOOR = getBlockType("jungle_door");

    Typed<Fence> JUNGLE_FENCE = getBlockType("jungle_fence");

    Typed<Gate> JUNGLE_FENCE_GATE = getBlockType("jungle_fence_gate");

    Typed<HangingSign> JUNGLE_HANGING_SIGN = getBlockType("jungle_hanging_sign");

    Typed<Leaves> JUNGLE_LEAVES = getBlockType("jungle_leaves");

    Typed<Orientable> JUNGLE_LOG = getBlockType("jungle_log");

    Typed<BlockData> JUNGLE_PLANKS = getBlockType("jungle_planks");

    Typed<Powerable> JUNGLE_PRESSURE_PLATE = getBlockType("jungle_pressure_plate");

    Typed<Sapling> JUNGLE_SAPLING = getBlockType("jungle_sapling");

    Typed<Shelf> JUNGLE_SHELF = getBlockType("jungle_shelf");

    Typed<Sign> JUNGLE_SIGN = getBlockType("jungle_sign");

    Typed<Slab> JUNGLE_SLAB = getBlockType("jungle_slab");

    Typed<Stairs> JUNGLE_STAIRS = getBlockType("jungle_stairs");

    Typed<TrapDoor> JUNGLE_TRAPDOOR = getBlockType("jungle_trapdoor");

    Typed<WallHangingSign> JUNGLE_WALL_HANGING_SIGN = getBlockType("jungle_wall_hanging_sign");

    Typed<WallSign> JUNGLE_WALL_SIGN = getBlockType("jungle_wall_sign");

    Typed<Orientable> JUNGLE_WOOD = getBlockType("jungle_wood");

    Typed<Ageable> KELP = getBlockType("kelp");

    Typed<BlockData> KELP_PLANT = getBlockType("kelp_plant");

    Typed<Ladder> LADDER = getBlockType("ladder");

    Typed<Lantern> LANTERN = getBlockType("lantern");

    Typed<BlockData> LAPIS_BLOCK = getBlockType("lapis_block");

    Typed<BlockData> LAPIS_ORE = getBlockType("lapis_ore");

    Typed<AmethystCluster> LARGE_AMETHYST_BUD = getBlockType("large_amethyst_bud");

    Typed<Bisected> LARGE_FERN = getBlockType("large_fern");

    Typed<Levelled> LAVA = getBlockType("lava");

    Typed<BlockData> LAVA_CAULDRON = getBlockType("lava_cauldron");

    Typed<LeafLitter> LEAF_LITTER = getBlockType("leaf_litter");

    Typed<Lectern> LECTERN = getBlockType("lectern");

    Typed<Switch> LEVER = getBlockType("lever");

    Typed<Light> LIGHT = getBlockType("light");

    Typed<Rotatable> LIGHT_BLUE_BANNER = getBlockType("light_blue_banner");

    Typed<Bed> LIGHT_BLUE_BED = getBlockType("light_blue_bed");

    Typed<Candle> LIGHT_BLUE_CANDLE = getBlockType("light_blue_candle");

    Typed<Lightable> LIGHT_BLUE_CANDLE_CAKE = getBlockType("light_blue_candle_cake");

    Typed<BlockData> LIGHT_BLUE_CARPET = getBlockType("light_blue_carpet");

    Typed<BlockData> LIGHT_BLUE_CONCRETE = getBlockType("light_blue_concrete");

    Typed<BlockData> LIGHT_BLUE_CONCRETE_POWDER = getBlockType("light_blue_concrete_powder");

    Typed<Directional> LIGHT_BLUE_GLAZED_TERRACOTTA = getBlockType("light_blue_glazed_terracotta");

    Typed<Directional> LIGHT_BLUE_SHULKER_BOX = getBlockType("light_blue_shulker_box");

    Typed<BlockData> LIGHT_BLUE_STAINED_GLASS = getBlockType("light_blue_stained_glass");

    Typed<GlassPane> LIGHT_BLUE_STAINED_GLASS_PANE = getBlockType("light_blue_stained_glass_pane");

    Typed<BlockData> LIGHT_BLUE_TERRACOTTA = getBlockType("light_blue_terracotta");

    Typed<Directional> LIGHT_BLUE_WALL_BANNER = getBlockType("light_blue_wall_banner");

    Typed<BlockData> LIGHT_BLUE_WOOL = getBlockType("light_blue_wool");

    Typed<Rotatable> LIGHT_GRAY_BANNER = getBlockType("light_gray_banner");

    Typed<Bed> LIGHT_GRAY_BED = getBlockType("light_gray_bed");

    Typed<Candle> LIGHT_GRAY_CANDLE = getBlockType("light_gray_candle");

    Typed<Lightable> LIGHT_GRAY_CANDLE_CAKE = getBlockType("light_gray_candle_cake");

    Typed<BlockData> LIGHT_GRAY_CARPET = getBlockType("light_gray_carpet");

    Typed<BlockData> LIGHT_GRAY_CONCRETE = getBlockType("light_gray_concrete");

    Typed<BlockData> LIGHT_GRAY_CONCRETE_POWDER = getBlockType("light_gray_concrete_powder");

    Typed<Directional> LIGHT_GRAY_GLAZED_TERRACOTTA = getBlockType("light_gray_glazed_terracotta");

    Typed<Directional> LIGHT_GRAY_SHULKER_BOX = getBlockType("light_gray_shulker_box");

    Typed<BlockData> LIGHT_GRAY_STAINED_GLASS = getBlockType("light_gray_stained_glass");

    Typed<GlassPane> LIGHT_GRAY_STAINED_GLASS_PANE = getBlockType("light_gray_stained_glass_pane");

    Typed<BlockData> LIGHT_GRAY_TERRACOTTA = getBlockType("light_gray_terracotta");

    Typed<Directional> LIGHT_GRAY_WALL_BANNER = getBlockType("light_gray_wall_banner");

    Typed<BlockData> LIGHT_GRAY_WOOL = getBlockType("light_gray_wool");

    Typed<AnaloguePowerable> LIGHT_WEIGHTED_PRESSURE_PLATE = getBlockType("light_weighted_pressure_plate");

    Typed<LightningRod> LIGHTNING_ROD = getBlockType("lightning_rod");

    Typed<Bisected> LILAC = getBlockType("lilac");

    Typed<BlockData> LILY_OF_THE_VALLEY = getBlockType("lily_of_the_valley");

    Typed<BlockData> LILY_PAD = getBlockType("lily_pad");

    Typed<Rotatable> LIME_BANNER = getBlockType("lime_banner");

    Typed<Bed> LIME_BED = getBlockType("lime_bed");

    Typed<Candle> LIME_CANDLE = getBlockType("lime_candle");

    Typed<Lightable> LIME_CANDLE_CAKE = getBlockType("lime_candle_cake");

    Typed<BlockData> LIME_CARPET = getBlockType("lime_carpet");

    Typed<BlockData> LIME_CONCRETE = getBlockType("lime_concrete");

    Typed<BlockData> LIME_CONCRETE_POWDER = getBlockType("lime_concrete_powder");

    Typed<Directional> LIME_GLAZED_TERRACOTTA = getBlockType("lime_glazed_terracotta");

    Typed<Directional> LIME_SHULKER_BOX = getBlockType("lime_shulker_box");

    Typed<BlockData> LIME_STAINED_GLASS = getBlockType("lime_stained_glass");

    Typed<GlassPane> LIME_STAINED_GLASS_PANE = getBlockType("lime_stained_glass_pane");

    Typed<BlockData> LIME_TERRACOTTA = getBlockType("lime_terracotta");

    Typed<Directional> LIME_WALL_BANNER = getBlockType("lime_wall_banner");

    Typed<BlockData> LIME_WOOL = getBlockType("lime_wool");

    Typed<BlockData> LODESTONE = getBlockType("lodestone");

    Typed<Directional> LOOM = getBlockType("loom");

    Typed<Rotatable> MAGENTA_BANNER = getBlockType("magenta_banner");

    Typed<Bed> MAGENTA_BED = getBlockType("magenta_bed");

    Typed<Candle> MAGENTA_CANDLE = getBlockType("magenta_candle");

    Typed<Lightable> MAGENTA_CANDLE_CAKE = getBlockType("magenta_candle_cake");

    Typed<BlockData> MAGENTA_CARPET = getBlockType("magenta_carpet");

    Typed<BlockData> MAGENTA_CONCRETE = getBlockType("magenta_concrete");

    Typed<BlockData> MAGENTA_CONCRETE_POWDER = getBlockType("magenta_concrete_powder");

    Typed<Directional> MAGENTA_GLAZED_TERRACOTTA = getBlockType("magenta_glazed_terracotta");

    Typed<Directional> MAGENTA_SHULKER_BOX = getBlockType("magenta_shulker_box");

    Typed<BlockData> MAGENTA_STAINED_GLASS = getBlockType("magenta_stained_glass");

    Typed<GlassPane> MAGENTA_STAINED_GLASS_PANE = getBlockType("magenta_stained_glass_pane");

    Typed<BlockData> MAGENTA_TERRACOTTA = getBlockType("magenta_terracotta");

    Typed<Directional> MAGENTA_WALL_BANNER = getBlockType("magenta_wall_banner");

    Typed<BlockData> MAGENTA_WOOL = getBlockType("magenta_wool");

    Typed<BlockData> MAGMA_BLOCK = getBlockType("magma_block");

    Typed<Switch> MANGROVE_BUTTON = getBlockType("mangrove_button");

    Typed<Door> MANGROVE_DOOR = getBlockType("mangrove_door");

    Typed<Fence> MANGROVE_FENCE = getBlockType("mangrove_fence");

    Typed<Gate> MANGROVE_FENCE_GATE = getBlockType("mangrove_fence_gate");

    Typed<HangingSign> MANGROVE_HANGING_SIGN = getBlockType("mangrove_hanging_sign");

    Typed<Leaves> MANGROVE_LEAVES = getBlockType("mangrove_leaves");

    Typed<Orientable> MANGROVE_LOG = getBlockType("mangrove_log");

    Typed<BlockData> MANGROVE_PLANKS = getBlockType("mangrove_planks");

    Typed<Powerable> MANGROVE_PRESSURE_PLATE = getBlockType("mangrove_pressure_plate");

    Typed<MangrovePropagule> MANGROVE_PROPAGULE = getBlockType("mangrove_propagule");

    Typed<Waterlogged> MANGROVE_ROOTS = getBlockType("mangrove_roots");

    Typed<Shelf> MANGROVE_SHELF = getBlockType("mangrove_shelf");

    Typed<Sign> MANGROVE_SIGN = getBlockType("mangrove_sign");

    Typed<Slab> MANGROVE_SLAB = getBlockType("mangrove_slab");

    Typed<Stairs> MANGROVE_STAIRS = getBlockType("mangrove_stairs");

    Typed<TrapDoor> MANGROVE_TRAPDOOR = getBlockType("mangrove_trapdoor");

    Typed<WallHangingSign> MANGROVE_WALL_HANGING_SIGN = getBlockType("mangrove_wall_hanging_sign");

    Typed<WallSign> MANGROVE_WALL_SIGN = getBlockType("mangrove_wall_sign");

    Typed<Orientable> MANGROVE_WOOD = getBlockType("mangrove_wood");

    Typed<AmethystCluster> MEDIUM_AMETHYST_BUD = getBlockType("medium_amethyst_bud");

    Typed<BlockData> MELON = getBlockType("melon");

    Typed<Ageable> MELON_STEM = getBlockType("melon_stem");

    Typed<BlockData> MOSS_BLOCK = getBlockType("moss_block");

    Typed<BlockData> MOSS_CARPET = getBlockType("moss_carpet");

    Typed<BlockData> MOSSY_COBBLESTONE = getBlockType("mossy_cobblestone");

    Typed<Slab> MOSSY_COBBLESTONE_SLAB = getBlockType("mossy_cobblestone_slab");

    Typed<Stairs> MOSSY_COBBLESTONE_STAIRS = getBlockType("mossy_cobblestone_stairs");

    Typed<Wall> MOSSY_COBBLESTONE_WALL = getBlockType("mossy_cobblestone_wall");

    Typed<Slab> MOSSY_STONE_BRICK_SLAB = getBlockType("mossy_stone_brick_slab");

    Typed<Stairs> MOSSY_STONE_BRICK_STAIRS = getBlockType("mossy_stone_brick_stairs");

    Typed<Wall> MOSSY_STONE_BRICK_WALL = getBlockType("mossy_stone_brick_wall");

    Typed<BlockData> MOSSY_STONE_BRICKS = getBlockType("mossy_stone_bricks");

    Typed<TechnicalPiston> MOVING_PISTON = getBlockType("moving_piston");

    Typed<BlockData> MUD = getBlockType("mud");

    Typed<Slab> MUD_BRICK_SLAB = getBlockType("mud_brick_slab");

    Typed<Stairs> MUD_BRICK_STAIRS = getBlockType("mud_brick_stairs");

    Typed<Wall> MUD_BRICK_WALL = getBlockType("mud_brick_wall");

    Typed<BlockData> MUD_BRICKS = getBlockType("mud_bricks");

    Typed<Orientable> MUDDY_MANGROVE_ROOTS = getBlockType("muddy_mangrove_roots");

    Typed<MultipleFacing> MUSHROOM_STEM = getBlockType("mushroom_stem");

    Typed<Snowable> MYCELIUM = getBlockType("mycelium");

    Typed<Fence> NETHER_BRICK_FENCE = getBlockType("nether_brick_fence");

    Typed<Slab> NETHER_BRICK_SLAB = getBlockType("nether_brick_slab");

    Typed<Stairs> NETHER_BRICK_STAIRS = getBlockType("nether_brick_stairs");

    Typed<Wall> NETHER_BRICK_WALL = getBlockType("nether_brick_wall");

    Typed<BlockData> NETHER_BRICKS = getBlockType("nether_bricks");

    Typed<BlockData> NETHER_GOLD_ORE = getBlockType("nether_gold_ore");

    Typed<Orientable> NETHER_PORTAL = getBlockType("nether_portal");

    Typed<BlockData> NETHER_QUARTZ_ORE = getBlockType("nether_quartz_ore");

    Typed<BlockData> NETHER_SPROUTS = getBlockType("nether_sprouts");

    Typed<Ageable> NETHER_WART = getBlockType("nether_wart");

    Typed<BlockData> NETHER_WART_BLOCK = getBlockType("nether_wart_block");

    Typed<BlockData> NETHERITE_BLOCK = getBlockType("netherite_block");

    Typed<BlockData> NETHERRACK = getBlockType("netherrack");

    Typed<NoteBlock> NOTE_BLOCK = getBlockType("note_block");

    Typed<Switch> OAK_BUTTON = getBlockType("oak_button");

    Typed<Door> OAK_DOOR = getBlockType("oak_door");

    Typed<Fence> OAK_FENCE = getBlockType("oak_fence");

    Typed<Gate> OAK_FENCE_GATE = getBlockType("oak_fence_gate");

    Typed<HangingSign> OAK_HANGING_SIGN = getBlockType("oak_hanging_sign");

    Typed<Leaves> OAK_LEAVES = getBlockType("oak_leaves");

    Typed<Orientable> OAK_LOG = getBlockType("oak_log");

    Typed<BlockData> OAK_PLANKS = getBlockType("oak_planks");

    Typed<Powerable> OAK_PRESSURE_PLATE = getBlockType("oak_pressure_plate");

    Typed<Sapling> OAK_SAPLING = getBlockType("oak_sapling");

    Typed<Shelf> OAK_SHELF = getBlockType("oak_shelf");

    Typed<Sign> OAK_SIGN = getBlockType("oak_sign");

    Typed<Slab> OAK_SLAB = getBlockType("oak_slab");

    Typed<Stairs> OAK_STAIRS = getBlockType("oak_stairs");

    Typed<TrapDoor> OAK_TRAPDOOR = getBlockType("oak_trapdoor");

    Typed<WallHangingSign> OAK_WALL_HANGING_SIGN = getBlockType("oak_wall_hanging_sign");

    Typed<WallSign> OAK_WALL_SIGN = getBlockType("oak_wall_sign");

    Typed<Orientable> OAK_WOOD = getBlockType("oak_wood");

    Typed<Observer> OBSERVER = getBlockType("observer");

    Typed<BlockData> OBSIDIAN = getBlockType("obsidian");

    Typed<Orientable> OCHRE_FROGLIGHT = getBlockType("ochre_froglight");

    Typed<BlockData> OPEN_EYEBLOSSOM = getBlockType("open_eyeblossom");

    Typed<Rotatable> ORANGE_BANNER = getBlockType("orange_banner");

    Typed<Bed> ORANGE_BED = getBlockType("orange_bed");

    Typed<Candle> ORANGE_CANDLE = getBlockType("orange_candle");

    Typed<Lightable> ORANGE_CANDLE_CAKE = getBlockType("orange_candle_cake");

    Typed<BlockData> ORANGE_CARPET = getBlockType("orange_carpet");

    Typed<BlockData> ORANGE_CONCRETE = getBlockType("orange_concrete");

    Typed<BlockData> ORANGE_CONCRETE_POWDER = getBlockType("orange_concrete_powder");

    Typed<Directional> ORANGE_GLAZED_TERRACOTTA = getBlockType("orange_glazed_terracotta");

    Typed<Directional> ORANGE_SHULKER_BOX = getBlockType("orange_shulker_box");

    Typed<BlockData> ORANGE_STAINED_GLASS = getBlockType("orange_stained_glass");

    Typed<GlassPane> ORANGE_STAINED_GLASS_PANE = getBlockType("orange_stained_glass_pane");

    Typed<BlockData> ORANGE_TERRACOTTA = getBlockType("orange_terracotta");

    Typed<BlockData> ORANGE_TULIP = getBlockType("orange_tulip");

    Typed<Directional> ORANGE_WALL_BANNER = getBlockType("orange_wall_banner");

    Typed<BlockData> ORANGE_WOOL = getBlockType("orange_wool");

    Typed<BlockData> OXEYE_DAISY = getBlockType("oxeye_daisy");

    Typed<BlockData> OXIDIZED_CHISELED_COPPER = getBlockType("oxidized_chiseled_copper");

    Typed<BlockData> OXIDIZED_COPPER = getBlockType("oxidized_copper");

    Typed<Fence> OXIDIZED_COPPER_BARS = getBlockType("oxidized_copper_bars");

    Typed<CopperBulb> OXIDIZED_COPPER_BULB = getBlockType("oxidized_copper_bulb");

    Typed<Chain> OXIDIZED_COPPER_CHAIN = getBlockType("oxidized_copper_chain");

    Typed<Chest> OXIDIZED_COPPER_CHEST = getBlockType("oxidized_copper_chest");

    Typed<Door> OXIDIZED_COPPER_DOOR = getBlockType("oxidized_copper_door");

    Typed<CopperGolemStatue> OXIDIZED_COPPER_GOLEM_STATUE = getBlockType("oxidized_copper_golem_statue");

    Typed<Waterlogged> OXIDIZED_COPPER_GRATE = getBlockType("oxidized_copper_grate");

    Typed<Lantern> OXIDIZED_COPPER_LANTERN = getBlockType("oxidized_copper_lantern");

    Typed<TrapDoor> OXIDIZED_COPPER_TRAPDOOR = getBlockType("oxidized_copper_trapdoor");

    Typed<BlockData> OXIDIZED_CUT_COPPER = getBlockType("oxidized_cut_copper");

    Typed<Slab> OXIDIZED_CUT_COPPER_SLAB = getBlockType("oxidized_cut_copper_slab");

    Typed<Stairs> OXIDIZED_CUT_COPPER_STAIRS = getBlockType("oxidized_cut_copper_stairs");

    Typed<LightningRod> OXIDIZED_LIGHTNING_ROD = getBlockType("oxidized_lightning_rod");

    Typed<BlockData> PACKED_ICE = getBlockType("packed_ice");

    Typed<BlockData> PACKED_MUD = getBlockType("packed_mud");

    Typed<HangingMoss> PALE_HANGING_MOSS = getBlockType("pale_hanging_moss");

    Typed<BlockData> PALE_MOSS_BLOCK = getBlockType("pale_moss_block");

    Typed<MossyCarpet> PALE_MOSS_CARPET = getBlockType("pale_moss_carpet");

    Typed<Switch> PALE_OAK_BUTTON = getBlockType("pale_oak_button");

    Typed<Door> PALE_OAK_DOOR = getBlockType("pale_oak_door");

    Typed<Fence> PALE_OAK_FENCE = getBlockType("pale_oak_fence");

    Typed<Gate> PALE_OAK_FENCE_GATE = getBlockType("pale_oak_fence_gate");

    Typed<HangingSign> PALE_OAK_HANGING_SIGN = getBlockType("pale_oak_hanging_sign");

    Typed<Leaves> PALE_OAK_LEAVES = getBlockType("pale_oak_leaves");

    Typed<Orientable> PALE_OAK_LOG = getBlockType("pale_oak_log");

    Typed<BlockData> PALE_OAK_PLANKS = getBlockType("pale_oak_planks");

    Typed<Powerable> PALE_OAK_PRESSURE_PLATE = getBlockType("pale_oak_pressure_plate");

    Typed<Sapling> PALE_OAK_SAPLING = getBlockType("pale_oak_sapling");

    Typed<Shelf> PALE_OAK_SHELF = getBlockType("pale_oak_shelf");

    Typed<Sign> PALE_OAK_SIGN = getBlockType("pale_oak_sign");

    Typed<Slab> PALE_OAK_SLAB = getBlockType("pale_oak_slab");

    Typed<Stairs> PALE_OAK_STAIRS = getBlockType("pale_oak_stairs");

    Typed<TrapDoor> PALE_OAK_TRAPDOOR = getBlockType("pale_oak_trapdoor");

    Typed<WallHangingSign> PALE_OAK_WALL_HANGING_SIGN = getBlockType("pale_oak_wall_hanging_sign");

    Typed<WallSign> PALE_OAK_WALL_SIGN = getBlockType("pale_oak_wall_sign");

    Typed<Orientable> PALE_OAK_WOOD = getBlockType("pale_oak_wood");

    Typed<Orientable> PEARLESCENT_FROGLIGHT = getBlockType("pearlescent_froglight");

    Typed<Bisected> PEONY = getBlockType("peony");

    Typed<Slab> PETRIFIED_OAK_SLAB = getBlockType("petrified_oak_slab");

    Typed<Skull> PIGLIN_HEAD = getBlockType("piglin_head");

    Typed<WallSkull> PIGLIN_WALL_HEAD = getBlockType("piglin_wall_head");

    Typed<Rotatable> PINK_BANNER = getBlockType("pink_banner");

    Typed<Bed> PINK_BED = getBlockType("pink_bed");

    Typed<Candle> PINK_CANDLE = getBlockType("pink_candle");

    Typed<Lightable> PINK_CANDLE_CAKE = getBlockType("pink_candle_cake");

    Typed<BlockData> PINK_CARPET = getBlockType("pink_carpet");

    Typed<BlockData> PINK_CONCRETE = getBlockType("pink_concrete");

    Typed<BlockData> PINK_CONCRETE_POWDER = getBlockType("pink_concrete_powder");

    Typed<Directional> PINK_GLAZED_TERRACOTTA = getBlockType("pink_glazed_terracotta");

    Typed<FlowerBed> PINK_PETALS = getBlockType("pink_petals");

    Typed<Directional> PINK_SHULKER_BOX = getBlockType("pink_shulker_box");

    Typed<BlockData> PINK_STAINED_GLASS = getBlockType("pink_stained_glass");

    Typed<GlassPane> PINK_STAINED_GLASS_PANE = getBlockType("pink_stained_glass_pane");

    Typed<BlockData> PINK_TERRACOTTA = getBlockType("pink_terracotta");

    Typed<BlockData> PINK_TULIP = getBlockType("pink_tulip");

    Typed<Directional> PINK_WALL_BANNER = getBlockType("pink_wall_banner");

    Typed<BlockData> PINK_WOOL = getBlockType("pink_wool");

    Typed<Piston> PISTON = getBlockType("piston");

    Typed<PistonHead> PISTON_HEAD = getBlockType("piston_head");

    Typed<PitcherCrop> PITCHER_CROP = getBlockType("pitcher_crop");

    Typed<Bisected> PITCHER_PLANT = getBlockType("pitcher_plant");

    Typed<Skull> PLAYER_HEAD = getBlockType("player_head");

    Typed<WallSkull> PLAYER_WALL_HEAD = getBlockType("player_wall_head");

    Typed<Snowable> PODZOL = getBlockType("podzol");

    Typed<Speleothem> POINTED_DRIPSTONE = getBlockType("pointed_dripstone");

    Typed<BlockData> POLISHED_ANDESITE = getBlockType("polished_andesite");

    Typed<Slab> POLISHED_ANDESITE_SLAB = getBlockType("polished_andesite_slab");

    Typed<Stairs> POLISHED_ANDESITE_STAIRS = getBlockType("polished_andesite_stairs");

    Typed<Orientable> POLISHED_BASALT = getBlockType("polished_basalt");

    Typed<BlockData> POLISHED_BLACKSTONE = getBlockType("polished_blackstone");

    Typed<Slab> POLISHED_BLACKSTONE_BRICK_SLAB = getBlockType("polished_blackstone_brick_slab");

    Typed<Stairs> POLISHED_BLACKSTONE_BRICK_STAIRS = getBlockType("polished_blackstone_brick_stairs");

    Typed<Wall> POLISHED_BLACKSTONE_BRICK_WALL = getBlockType("polished_blackstone_brick_wall");

    Typed<BlockData> POLISHED_BLACKSTONE_BRICKS = getBlockType("polished_blackstone_bricks");

    Typed<Switch> POLISHED_BLACKSTONE_BUTTON = getBlockType("polished_blackstone_button");

    Typed<Powerable> POLISHED_BLACKSTONE_PRESSURE_PLATE = getBlockType("polished_blackstone_pressure_plate");

    Typed<Slab> POLISHED_BLACKSTONE_SLAB = getBlockType("polished_blackstone_slab");

    Typed<Stairs> POLISHED_BLACKSTONE_STAIRS = getBlockType("polished_blackstone_stairs");

    Typed<Wall> POLISHED_BLACKSTONE_WALL = getBlockType("polished_blackstone_wall");

    Typed<BlockData> POLISHED_CINNABAR = getBlockType("polished_cinnabar");

    Typed<Slab> POLISHED_CINNABAR_SLAB = getBlockType("polished_cinnabar_slab");

    Typed<Stairs> POLISHED_CINNABAR_STAIRS = getBlockType("polished_cinnabar_stairs");

    Typed<Wall> POLISHED_CINNABAR_WALL = getBlockType("polished_cinnabar_wall");

    Typed<BlockData> POLISHED_DEEPSLATE = getBlockType("polished_deepslate");

    Typed<Slab> POLISHED_DEEPSLATE_SLAB = getBlockType("polished_deepslate_slab");

    Typed<Stairs> POLISHED_DEEPSLATE_STAIRS = getBlockType("polished_deepslate_stairs");

    Typed<Wall> POLISHED_DEEPSLATE_WALL = getBlockType("polished_deepslate_wall");

    Typed<BlockData> POLISHED_DIORITE = getBlockType("polished_diorite");

    Typed<Slab> POLISHED_DIORITE_SLAB = getBlockType("polished_diorite_slab");

    Typed<Stairs> POLISHED_DIORITE_STAIRS = getBlockType("polished_diorite_stairs");

    Typed<BlockData> POLISHED_GRANITE = getBlockType("polished_granite");

    Typed<Slab> POLISHED_GRANITE_SLAB = getBlockType("polished_granite_slab");

    Typed<Stairs> POLISHED_GRANITE_STAIRS = getBlockType("polished_granite_stairs");

    Typed<BlockData> POLISHED_SULFUR = getBlockType("polished_sulfur");

    Typed<Slab> POLISHED_SULFUR_SLAB = getBlockType("polished_sulfur_slab");

    Typed<Stairs> POLISHED_SULFUR_STAIRS = getBlockType("polished_sulfur_stairs");

    Typed<Wall> POLISHED_SULFUR_WALL = getBlockType("polished_sulfur_wall");

    Typed<BlockData> POLISHED_TUFF = getBlockType("polished_tuff");

    Typed<Slab> POLISHED_TUFF_SLAB = getBlockType("polished_tuff_slab");

    Typed<Stairs> POLISHED_TUFF_STAIRS = getBlockType("polished_tuff_stairs");

    Typed<Wall> POLISHED_TUFF_WALL = getBlockType("polished_tuff_wall");

    Typed<BlockData> POPPY = getBlockType("poppy");

    Typed<Ageable> POTATOES = getBlockType("potatoes");

    Typed<PotentSulfur> POTENT_SULFUR = getBlockType("potent_sulfur");

    Typed<BlockData> POTTED_ACACIA_SAPLING = getBlockType("potted_acacia_sapling");

    Typed<BlockData> POTTED_ALLIUM = getBlockType("potted_allium");

    Typed<BlockData> POTTED_AZALEA_BUSH = getBlockType("potted_azalea_bush");

    Typed<BlockData> POTTED_AZURE_BLUET = getBlockType("potted_azure_bluet");

    Typed<BlockData> POTTED_BAMBOO = getBlockType("potted_bamboo");

    Typed<BlockData> POTTED_BIRCH_SAPLING = getBlockType("potted_birch_sapling");

    Typed<BlockData> POTTED_BLUE_ORCHID = getBlockType("potted_blue_orchid");

    Typed<BlockData> POTTED_BROWN_MUSHROOM = getBlockType("potted_brown_mushroom");

    Typed<BlockData> POTTED_CACTUS = getBlockType("potted_cactus");

    Typed<BlockData> POTTED_CHERRY_SAPLING = getBlockType("potted_cherry_sapling");

    Typed<BlockData> POTTED_CLOSED_EYEBLOSSOM = getBlockType("potted_closed_eyeblossom");

    Typed<BlockData> POTTED_CORNFLOWER = getBlockType("potted_cornflower");

    Typed<BlockData> POTTED_CRIMSON_FUNGUS = getBlockType("potted_crimson_fungus");

    Typed<BlockData> POTTED_CRIMSON_ROOTS = getBlockType("potted_crimson_roots");

    Typed<BlockData> POTTED_DANDELION = getBlockType("potted_dandelion");

    Typed<BlockData> POTTED_DARK_OAK_SAPLING = getBlockType("potted_dark_oak_sapling");

    Typed<BlockData> POTTED_DEAD_BUSH = getBlockType("potted_dead_bush");

    Typed<BlockData> POTTED_FERN = getBlockType("potted_fern");

    Typed<BlockData> POTTED_FLOWERING_AZALEA_BUSH = getBlockType("potted_flowering_azalea_bush");

    Typed<BlockData> POTTED_GOLDEN_DANDELION = getBlockType("potted_golden_dandelion");

    Typed<BlockData> POTTED_JUNGLE_SAPLING = getBlockType("potted_jungle_sapling");

    Typed<BlockData> POTTED_LILY_OF_THE_VALLEY = getBlockType("potted_lily_of_the_valley");

    Typed<BlockData> POTTED_MANGROVE_PROPAGULE = getBlockType("potted_mangrove_propagule");

    Typed<BlockData> POTTED_OAK_SAPLING = getBlockType("potted_oak_sapling");

    Typed<BlockData> POTTED_OPEN_EYEBLOSSOM = getBlockType("potted_open_eyeblossom");

    Typed<BlockData> POTTED_ORANGE_TULIP = getBlockType("potted_orange_tulip");

    Typed<BlockData> POTTED_OXEYE_DAISY = getBlockType("potted_oxeye_daisy");

    Typed<BlockData> POTTED_PALE_OAK_SAPLING = getBlockType("potted_pale_oak_sapling");

    Typed<BlockData> POTTED_PINK_TULIP = getBlockType("potted_pink_tulip");

    Typed<BlockData> POTTED_POPPY = getBlockType("potted_poppy");

    Typed<BlockData> POTTED_RED_MUSHROOM = getBlockType("potted_red_mushroom");

    Typed<BlockData> POTTED_RED_TULIP = getBlockType("potted_red_tulip");

    Typed<BlockData> POTTED_SPRUCE_SAPLING = getBlockType("potted_spruce_sapling");

    Typed<BlockData> POTTED_TORCHFLOWER = getBlockType("potted_torchflower");

    Typed<BlockData> POTTED_WARPED_FUNGUS = getBlockType("potted_warped_fungus");

    Typed<BlockData> POTTED_WARPED_ROOTS = getBlockType("potted_warped_roots");

    Typed<BlockData> POTTED_WHITE_TULIP = getBlockType("potted_white_tulip");

    Typed<BlockData> POTTED_WITHER_ROSE = getBlockType("potted_wither_rose");

    Typed<BlockData> POWDER_SNOW = getBlockType("powder_snow");

    Typed<Levelled> POWDER_SNOW_CAULDRON = getBlockType("powder_snow_cauldron");

    Typed<RedstoneRail> POWERED_RAIL = getBlockType("powered_rail");

    Typed<BlockData> PRISMARINE = getBlockType("prismarine");

    Typed<Slab> PRISMARINE_BRICK_SLAB = getBlockType("prismarine_brick_slab");

    Typed<Stairs> PRISMARINE_BRICK_STAIRS = getBlockType("prismarine_brick_stairs");

    Typed<BlockData> PRISMARINE_BRICKS = getBlockType("prismarine_bricks");

    Typed<Slab> PRISMARINE_SLAB = getBlockType("prismarine_slab");

    Typed<Stairs> PRISMARINE_STAIRS = getBlockType("prismarine_stairs");

    Typed<Wall> PRISMARINE_WALL = getBlockType("prismarine_wall");

    Typed<BlockData> PUMPKIN = getBlockType("pumpkin");

    Typed<Ageable> PUMPKIN_STEM = getBlockType("pumpkin_stem");

    Typed<Rotatable> PURPLE_BANNER = getBlockType("purple_banner");

    Typed<Bed> PURPLE_BED = getBlockType("purple_bed");

    Typed<Candle> PURPLE_CANDLE = getBlockType("purple_candle");

    Typed<Lightable> PURPLE_CANDLE_CAKE = getBlockType("purple_candle_cake");

    Typed<BlockData> PURPLE_CARPET = getBlockType("purple_carpet");

    Typed<BlockData> PURPLE_CONCRETE = getBlockType("purple_concrete");

    Typed<BlockData> PURPLE_CONCRETE_POWDER = getBlockType("purple_concrete_powder");

    Typed<Directional> PURPLE_GLAZED_TERRACOTTA = getBlockType("purple_glazed_terracotta");

    Typed<Directional> PURPLE_SHULKER_BOX = getBlockType("purple_shulker_box");

    Typed<BlockData> PURPLE_STAINED_GLASS = getBlockType("purple_stained_glass");

    Typed<GlassPane> PURPLE_STAINED_GLASS_PANE = getBlockType("purple_stained_glass_pane");

    Typed<BlockData> PURPLE_TERRACOTTA = getBlockType("purple_terracotta");

    Typed<Directional> PURPLE_WALL_BANNER = getBlockType("purple_wall_banner");

    Typed<BlockData> PURPLE_WOOL = getBlockType("purple_wool");

    Typed<BlockData> PURPUR_BLOCK = getBlockType("purpur_block");

    Typed<Orientable> PURPUR_PILLAR = getBlockType("purpur_pillar");

    Typed<Slab> PURPUR_SLAB = getBlockType("purpur_slab");

    Typed<Stairs> PURPUR_STAIRS = getBlockType("purpur_stairs");

    Typed<BlockData> QUARTZ_BLOCK = getBlockType("quartz_block");

    Typed<BlockData> QUARTZ_BRICKS = getBlockType("quartz_bricks");

    Typed<Orientable> QUARTZ_PILLAR = getBlockType("quartz_pillar");

    Typed<Slab> QUARTZ_SLAB = getBlockType("quartz_slab");

    Typed<Stairs> QUARTZ_STAIRS = getBlockType("quartz_stairs");

    Typed<Rail> RAIL = getBlockType("rail");

    Typed<BlockData> RAW_COPPER_BLOCK = getBlockType("raw_copper_block");

    Typed<BlockData> RAW_GOLD_BLOCK = getBlockType("raw_gold_block");

    Typed<BlockData> RAW_IRON_BLOCK = getBlockType("raw_iron_block");

    Typed<Rotatable> RED_BANNER = getBlockType("red_banner");

    Typed<Bed> RED_BED = getBlockType("red_bed");

    Typed<Candle> RED_CANDLE = getBlockType("red_candle");

    Typed<Lightable> RED_CANDLE_CAKE = getBlockType("red_candle_cake");

    Typed<BlockData> RED_CARPET = getBlockType("red_carpet");

    Typed<BlockData> RED_CONCRETE = getBlockType("red_concrete");

    Typed<BlockData> RED_CONCRETE_POWDER = getBlockType("red_concrete_powder");

    Typed<Directional> RED_GLAZED_TERRACOTTA = getBlockType("red_glazed_terracotta");

    Typed<BlockData> RED_MUSHROOM = getBlockType("red_mushroom");

    Typed<MultipleFacing> RED_MUSHROOM_BLOCK = getBlockType("red_mushroom_block");

    Typed<Slab> RED_NETHER_BRICK_SLAB = getBlockType("red_nether_brick_slab");

    Typed<Stairs> RED_NETHER_BRICK_STAIRS = getBlockType("red_nether_brick_stairs");

    Typed<Wall> RED_NETHER_BRICK_WALL = getBlockType("red_nether_brick_wall");

    Typed<BlockData> RED_NETHER_BRICKS = getBlockType("red_nether_bricks");

    Typed<BlockData> RED_SAND = getBlockType("red_sand");

    Typed<BlockData> RED_SANDSTONE = getBlockType("red_sandstone");

    Typed<Slab> RED_SANDSTONE_SLAB = getBlockType("red_sandstone_slab");

    Typed<Stairs> RED_SANDSTONE_STAIRS = getBlockType("red_sandstone_stairs");

    Typed<Wall> RED_SANDSTONE_WALL = getBlockType("red_sandstone_wall");

    Typed<Directional> RED_SHULKER_BOX = getBlockType("red_shulker_box");

    Typed<BlockData> RED_STAINED_GLASS = getBlockType("red_stained_glass");

    Typed<GlassPane> RED_STAINED_GLASS_PANE = getBlockType("red_stained_glass_pane");

    Typed<BlockData> RED_TERRACOTTA = getBlockType("red_terracotta");

    Typed<BlockData> RED_TULIP = getBlockType("red_tulip");

    Typed<Directional> RED_WALL_BANNER = getBlockType("red_wall_banner");

    Typed<BlockData> RED_WOOL = getBlockType("red_wool");

    Typed<BlockData> REDSTONE_BLOCK = getBlockType("redstone_block");

    Typed<Lightable> REDSTONE_LAMP = getBlockType("redstone_lamp");

    Typed<Lightable> REDSTONE_ORE = getBlockType("redstone_ore");

    Typed<Lightable> REDSTONE_TORCH = getBlockType("redstone_torch");

    Typed<RedstoneWallTorch> REDSTONE_WALL_TORCH = getBlockType("redstone_wall_torch");

    Typed<RedstoneWire> REDSTONE_WIRE = getBlockType("redstone_wire");

    Typed<BlockData> REINFORCED_DEEPSLATE = getBlockType("reinforced_deepslate");

    Typed<Repeater> REPEATER = getBlockType("repeater");

    Typed<CommandBlock> REPEATING_COMMAND_BLOCK = getBlockType("repeating_command_block");

    Typed<BlockData> RESIN_BLOCK = getBlockType("resin_block");

    Typed<Slab> RESIN_BRICK_SLAB = getBlockType("resin_brick_slab");

    Typed<Stairs> RESIN_BRICK_STAIRS = getBlockType("resin_brick_stairs");

    Typed<Wall> RESIN_BRICK_WALL = getBlockType("resin_brick_wall");

    Typed<BlockData> RESIN_BRICKS = getBlockType("resin_bricks");

    Typed<ResinClump> RESIN_CLUMP = getBlockType("resin_clump");

    Typed<RespawnAnchor> RESPAWN_ANCHOR = getBlockType("respawn_anchor");

    Typed<BlockData> ROOTED_DIRT = getBlockType("rooted_dirt");

    Typed<Bisected> ROSE_BUSH = getBlockType("rose_bush");

    Typed<BlockData> SAND = getBlockType("sand");

    Typed<BlockData> SANDSTONE = getBlockType("sandstone");

    Typed<Slab> SANDSTONE_SLAB = getBlockType("sandstone_slab");

    Typed<Stairs> SANDSTONE_STAIRS = getBlockType("sandstone_stairs");

    Typed<Wall> SANDSTONE_WALL = getBlockType("sandstone_wall");

    Typed<Scaffolding> SCAFFOLDING = getBlockType("scaffolding");

    Typed<BlockData> SCULK = getBlockType("sculk");

    Typed<SculkCatalyst> SCULK_CATALYST = getBlockType("sculk_catalyst");

    Typed<SculkSensor> SCULK_SENSOR = getBlockType("sculk_sensor");

    Typed<SculkShrieker> SCULK_SHRIEKER = getBlockType("sculk_shrieker");

    Typed<SculkVein> SCULK_VEIN = getBlockType("sculk_vein");

    Typed<BlockData> SEA_LANTERN = getBlockType("sea_lantern");

    Typed<SeaPickle> SEA_PICKLE = getBlockType("sea_pickle");

    Typed<BlockData> SEAGRASS = getBlockType("seagrass");

    Typed<BlockData> SHORT_DRY_GRASS = getBlockType("short_dry_grass");

    Typed<BlockData> SHORT_GRASS = getBlockType("short_grass");

    Typed<BlockData> SHROOMLIGHT = getBlockType("shroomlight");

    Typed<Directional> SHULKER_BOX = getBlockType("shulker_box");

    Typed<Skull> SKELETON_SKULL = getBlockType("skeleton_skull");

    Typed<WallSkull> SKELETON_WALL_SKULL = getBlockType("skeleton_wall_skull");

    Typed<BlockData> SLIME_BLOCK = getBlockType("slime_block");

    Typed<AmethystCluster> SMALL_AMETHYST_BUD = getBlockType("small_amethyst_bud");

    Typed<SmallDripleaf> SMALL_DRIPLEAF = getBlockType("small_dripleaf");

    Typed<BlockData> SMITHING_TABLE = getBlockType("smithing_table");

    Typed<Furnace> SMOKER = getBlockType("smoker");

    Typed<BlockData> SMOOTH_BASALT = getBlockType("smooth_basalt");

    Typed<BlockData> SMOOTH_QUARTZ = getBlockType("smooth_quartz");

    Typed<Slab> SMOOTH_QUARTZ_SLAB = getBlockType("smooth_quartz_slab");

    Typed<Stairs> SMOOTH_QUARTZ_STAIRS = getBlockType("smooth_quartz_stairs");

    Typed<BlockData> SMOOTH_RED_SANDSTONE = getBlockType("smooth_red_sandstone");

    Typed<Slab> SMOOTH_RED_SANDSTONE_SLAB = getBlockType("smooth_red_sandstone_slab");

    Typed<Stairs> SMOOTH_RED_SANDSTONE_STAIRS = getBlockType("smooth_red_sandstone_stairs");

    Typed<BlockData> SMOOTH_SANDSTONE = getBlockType("smooth_sandstone");

    Typed<Slab> SMOOTH_SANDSTONE_SLAB = getBlockType("smooth_sandstone_slab");

    Typed<Stairs> SMOOTH_SANDSTONE_STAIRS = getBlockType("smooth_sandstone_stairs");

    Typed<BlockData> SMOOTH_STONE = getBlockType("smooth_stone");

    Typed<Slab> SMOOTH_STONE_SLAB = getBlockType("smooth_stone_slab");

    Typed<Hatchable> SNIFFER_EGG = getBlockType("sniffer_egg");

    Typed<Snow> SNOW = getBlockType("snow");

    Typed<BlockData> SNOW_BLOCK = getBlockType("snow_block");

    Typed<Campfire> SOUL_CAMPFIRE = getBlockType("soul_campfire");

    Typed<BlockData> SOUL_FIRE = getBlockType("soul_fire");

    Typed<Lantern> SOUL_LANTERN = getBlockType("soul_lantern");

    Typed<BlockData> SOUL_SAND = getBlockType("soul_sand");

    Typed<BlockData> SOUL_SOIL = getBlockType("soul_soil");

    Typed<BlockData> SOUL_TORCH = getBlockType("soul_torch");

    Typed<Directional> SOUL_WALL_TORCH = getBlockType("soul_wall_torch");

    Typed<BlockData> SPAWNER = getBlockType("spawner");

    Typed<BlockData> SPONGE = getBlockType("sponge");

    Typed<BlockData> SPORE_BLOSSOM = getBlockType("spore_blossom");

    Typed<Switch> SPRUCE_BUTTON = getBlockType("spruce_button");

    Typed<Door> SPRUCE_DOOR = getBlockType("spruce_door");

    Typed<Fence> SPRUCE_FENCE = getBlockType("spruce_fence");

    Typed<Gate> SPRUCE_FENCE_GATE = getBlockType("spruce_fence_gate");

    Typed<HangingSign> SPRUCE_HANGING_SIGN = getBlockType("spruce_hanging_sign");

    Typed<Leaves> SPRUCE_LEAVES = getBlockType("spruce_leaves");

    Typed<Orientable> SPRUCE_LOG = getBlockType("spruce_log");

    Typed<BlockData> SPRUCE_PLANKS = getBlockType("spruce_planks");

    Typed<Powerable> SPRUCE_PRESSURE_PLATE = getBlockType("spruce_pressure_plate");

    Typed<Sapling> SPRUCE_SAPLING = getBlockType("spruce_sapling");

    Typed<Shelf> SPRUCE_SHELF = getBlockType("spruce_shelf");

    Typed<Sign> SPRUCE_SIGN = getBlockType("spruce_sign");

    Typed<Slab> SPRUCE_SLAB = getBlockType("spruce_slab");

    Typed<Stairs> SPRUCE_STAIRS = getBlockType("spruce_stairs");

    Typed<TrapDoor> SPRUCE_TRAPDOOR = getBlockType("spruce_trapdoor");

    Typed<WallHangingSign> SPRUCE_WALL_HANGING_SIGN = getBlockType("spruce_wall_hanging_sign");

    Typed<WallSign> SPRUCE_WALL_SIGN = getBlockType("spruce_wall_sign");

    Typed<Orientable> SPRUCE_WOOD = getBlockType("spruce_wood");

    Typed<Piston> STICKY_PISTON = getBlockType("sticky_piston");

    Typed<BlockData> STONE = getBlockType("stone");

    Typed<Slab> STONE_BRICK_SLAB = getBlockType("stone_brick_slab");

    Typed<Stairs> STONE_BRICK_STAIRS = getBlockType("stone_brick_stairs");

    Typed<Wall> STONE_BRICK_WALL = getBlockType("stone_brick_wall");

    Typed<BlockData> STONE_BRICKS = getBlockType("stone_bricks");

    Typed<Switch> STONE_BUTTON = getBlockType("stone_button");

    Typed<Powerable> STONE_PRESSURE_PLATE = getBlockType("stone_pressure_plate");

    Typed<Slab> STONE_SLAB = getBlockType("stone_slab");

    Typed<Stairs> STONE_STAIRS = getBlockType("stone_stairs");

    Typed<Directional> STONECUTTER = getBlockType("stonecutter");

    Typed<Orientable> STRIPPED_ACACIA_LOG = getBlockType("stripped_acacia_log");

    Typed<Orientable> STRIPPED_ACACIA_WOOD = getBlockType("stripped_acacia_wood");

    Typed<Orientable> STRIPPED_BAMBOO_BLOCK = getBlockType("stripped_bamboo_block");

    Typed<Orientable> STRIPPED_BIRCH_LOG = getBlockType("stripped_birch_log");

    Typed<Orientable> STRIPPED_BIRCH_WOOD = getBlockType("stripped_birch_wood");

    Typed<Orientable> STRIPPED_CHERRY_LOG = getBlockType("stripped_cherry_log");

    Typed<Orientable> STRIPPED_CHERRY_WOOD = getBlockType("stripped_cherry_wood");

    Typed<Orientable> STRIPPED_CRIMSON_HYPHAE = getBlockType("stripped_crimson_hyphae");

    Typed<Orientable> STRIPPED_CRIMSON_STEM = getBlockType("stripped_crimson_stem");

    Typed<Orientable> STRIPPED_DARK_OAK_LOG = getBlockType("stripped_dark_oak_log");

    Typed<Orientable> STRIPPED_DARK_OAK_WOOD = getBlockType("stripped_dark_oak_wood");

    Typed<Orientable> STRIPPED_JUNGLE_LOG = getBlockType("stripped_jungle_log");

    Typed<Orientable> STRIPPED_JUNGLE_WOOD = getBlockType("stripped_jungle_wood");

    Typed<Orientable> STRIPPED_MANGROVE_LOG = getBlockType("stripped_mangrove_log");

    Typed<Orientable> STRIPPED_MANGROVE_WOOD = getBlockType("stripped_mangrove_wood");

    Typed<Orientable> STRIPPED_OAK_LOG = getBlockType("stripped_oak_log");

    Typed<Orientable> STRIPPED_OAK_WOOD = getBlockType("stripped_oak_wood");

    Typed<Orientable> STRIPPED_PALE_OAK_LOG = getBlockType("stripped_pale_oak_log");

    Typed<Orientable> STRIPPED_PALE_OAK_WOOD = getBlockType("stripped_pale_oak_wood");

    Typed<Orientable> STRIPPED_SPRUCE_LOG = getBlockType("stripped_spruce_log");

    Typed<Orientable> STRIPPED_SPRUCE_WOOD = getBlockType("stripped_spruce_wood");

    Typed<Orientable> STRIPPED_WARPED_HYPHAE = getBlockType("stripped_warped_hyphae");

    Typed<Orientable> STRIPPED_WARPED_STEM = getBlockType("stripped_warped_stem");

    Typed<StructureBlock> STRUCTURE_BLOCK = getBlockType("structure_block");

    Typed<BlockData> STRUCTURE_VOID = getBlockType("structure_void");

    Typed<Ageable> SUGAR_CANE = getBlockType("sugar_cane");

    Typed<BlockData> SULFUR = getBlockType("sulfur");

    Typed<Slab> SULFUR_BRICK_SLAB = getBlockType("sulfur_brick_slab");

    Typed<Stairs> SULFUR_BRICK_STAIRS = getBlockType("sulfur_brick_stairs");

    Typed<Wall> SULFUR_BRICK_WALL = getBlockType("sulfur_brick_wall");

    Typed<BlockData> SULFUR_BRICKS = getBlockType("sulfur_bricks");

    Typed<Slab> SULFUR_SLAB = getBlockType("sulfur_slab");

    Typed<Speleothem> SULFUR_SPIKE = getBlockType("sulfur_spike");

    Typed<Stairs> SULFUR_STAIRS = getBlockType("sulfur_stairs");

    Typed<Wall> SULFUR_WALL = getBlockType("sulfur_wall");

    Typed<Bisected> SUNFLOWER = getBlockType("sunflower");

    Typed<Brushable> SUSPICIOUS_GRAVEL = getBlockType("suspicious_gravel");

    Typed<Brushable> SUSPICIOUS_SAND = getBlockType("suspicious_sand");

    Typed<Ageable> SWEET_BERRY_BUSH = getBlockType("sweet_berry_bush");

    Typed<BlockData> TALL_DRY_GRASS = getBlockType("tall_dry_grass");

    Typed<Bisected> TALL_GRASS = getBlockType("tall_grass");

    Typed<Bisected> TALL_SEAGRASS = getBlockType("tall_seagrass");

    Typed<AnaloguePowerable> TARGET = getBlockType("target");

    Typed<BlockData> TERRACOTTA = getBlockType("terracotta");

    Typed<TestBlock> TEST_BLOCK = getBlockType("test_block");

    Typed<BlockData> TEST_INSTANCE_BLOCK = getBlockType("test_instance_block");

    Typed<BlockData> TINTED_GLASS = getBlockType("tinted_glass");

    Typed<TNT> TNT = getBlockType("tnt");

    Typed<BlockData> TORCH = getBlockType("torch");

    Typed<BlockData> TORCHFLOWER = getBlockType("torchflower");

    Typed<Ageable> TORCHFLOWER_CROP = getBlockType("torchflower_crop");

    Typed<Chest> TRAPPED_CHEST = getBlockType("trapped_chest");

    Typed<TrialSpawner> TRIAL_SPAWNER = getBlockType("trial_spawner");

    Typed<Tripwire> TRIPWIRE = getBlockType("tripwire");

    Typed<TripwireHook> TRIPWIRE_HOOK = getBlockType("tripwire_hook");

    Typed<Waterlogged> TUBE_CORAL = getBlockType("tube_coral");

    Typed<BlockData> TUBE_CORAL_BLOCK = getBlockType("tube_coral_block");

    Typed<Waterlogged> TUBE_CORAL_FAN = getBlockType("tube_coral_fan");

    Typed<CoralWallFan> TUBE_CORAL_WALL_FAN = getBlockType("tube_coral_wall_fan");

    Typed<BlockData> TUFF = getBlockType("tuff");

    Typed<Slab> TUFF_BRICK_SLAB = getBlockType("tuff_brick_slab");

    Typed<Stairs> TUFF_BRICK_STAIRS = getBlockType("tuff_brick_stairs");

    Typed<Wall> TUFF_BRICK_WALL = getBlockType("tuff_brick_wall");

    Typed<BlockData> TUFF_BRICKS = getBlockType("tuff_bricks");

    Typed<Slab> TUFF_SLAB = getBlockType("tuff_slab");

    Typed<Stairs> TUFF_STAIRS = getBlockType("tuff_stairs");

    Typed<Wall> TUFF_WALL = getBlockType("tuff_wall");

    Typed<TurtleEgg> TURTLE_EGG = getBlockType("turtle_egg");

    Typed<Ageable> TWISTING_VINES = getBlockType("twisting_vines");

    Typed<BlockData> TWISTING_VINES_PLANT = getBlockType("twisting_vines_plant");

    Typed<Vault> VAULT = getBlockType("vault");

    Typed<Orientable> VERDANT_FROGLIGHT = getBlockType("verdant_froglight");

    Typed<MultipleFacing> VINE = getBlockType("vine");

    Typed<BlockData> VOID_AIR = getBlockType("void_air");

    Typed<Directional> WALL_TORCH = getBlockType("wall_torch");

    Typed<Switch> WARPED_BUTTON = getBlockType("warped_button");

    Typed<Door> WARPED_DOOR = getBlockType("warped_door");

    Typed<Fence> WARPED_FENCE = getBlockType("warped_fence");

    Typed<Gate> WARPED_FENCE_GATE = getBlockType("warped_fence_gate");

    Typed<BlockData> WARPED_FUNGUS = getBlockType("warped_fungus");

    Typed<HangingSign> WARPED_HANGING_SIGN = getBlockType("warped_hanging_sign");

    Typed<Orientable> WARPED_HYPHAE = getBlockType("warped_hyphae");

    Typed<BlockData> WARPED_NYLIUM = getBlockType("warped_nylium");

    Typed<BlockData> WARPED_PLANKS = getBlockType("warped_planks");

    Typed<Powerable> WARPED_PRESSURE_PLATE = getBlockType("warped_pressure_plate");

    Typed<BlockData> WARPED_ROOTS = getBlockType("warped_roots");

    Typed<Shelf> WARPED_SHELF = getBlockType("warped_shelf");

    Typed<Sign> WARPED_SIGN = getBlockType("warped_sign");

    Typed<Slab> WARPED_SLAB = getBlockType("warped_slab");

    Typed<Stairs> WARPED_STAIRS = getBlockType("warped_stairs");

    Typed<Orientable> WARPED_STEM = getBlockType("warped_stem");

    Typed<TrapDoor> WARPED_TRAPDOOR = getBlockType("warped_trapdoor");

    Typed<WallHangingSign> WARPED_WALL_HANGING_SIGN = getBlockType("warped_wall_hanging_sign");

    Typed<WallSign> WARPED_WALL_SIGN = getBlockType("warped_wall_sign");

    Typed<BlockData> WARPED_WART_BLOCK = getBlockType("warped_wart_block");

    Typed<Levelled> WATER = getBlockType("water");

    Typed<Levelled> WATER_CAULDRON = getBlockType("water_cauldron");

    Typed<BlockData> WAXED_CHISELED_COPPER = getBlockType("waxed_chiseled_copper");

    Typed<Fence> WAXED_COPPER_BARS = getBlockType("waxed_copper_bars");

    Typed<BlockData> WAXED_COPPER_BLOCK = getBlockType("waxed_copper_block");

    Typed<CopperBulb> WAXED_COPPER_BULB = getBlockType("waxed_copper_bulb");

    Typed<Chain> WAXED_COPPER_CHAIN = getBlockType("waxed_copper_chain");

    Typed<Chest> WAXED_COPPER_CHEST = getBlockType("waxed_copper_chest");

    Typed<Door> WAXED_COPPER_DOOR = getBlockType("waxed_copper_door");

    Typed<CopperGolemStatue> WAXED_COPPER_GOLEM_STATUE = getBlockType("waxed_copper_golem_statue");

    Typed<Waterlogged> WAXED_COPPER_GRATE = getBlockType("waxed_copper_grate");

    Typed<Lantern> WAXED_COPPER_LANTERN = getBlockType("waxed_copper_lantern");

    Typed<TrapDoor> WAXED_COPPER_TRAPDOOR = getBlockType("waxed_copper_trapdoor");

    Typed<BlockData> WAXED_CUT_COPPER = getBlockType("waxed_cut_copper");

    Typed<Slab> WAXED_CUT_COPPER_SLAB = getBlockType("waxed_cut_copper_slab");

    Typed<Stairs> WAXED_CUT_COPPER_STAIRS = getBlockType("waxed_cut_copper_stairs");

    Typed<BlockData> WAXED_EXPOSED_CHISELED_COPPER = getBlockType("waxed_exposed_chiseled_copper");

    Typed<BlockData> WAXED_EXPOSED_COPPER = getBlockType("waxed_exposed_copper");

    Typed<Fence> WAXED_EXPOSED_COPPER_BARS = getBlockType("waxed_exposed_copper_bars");

    Typed<CopperBulb> WAXED_EXPOSED_COPPER_BULB = getBlockType("waxed_exposed_copper_bulb");

    Typed<Chain> WAXED_EXPOSED_COPPER_CHAIN = getBlockType("waxed_exposed_copper_chain");

    Typed<Chest> WAXED_EXPOSED_COPPER_CHEST = getBlockType("waxed_exposed_copper_chest");

    Typed<Door> WAXED_EXPOSED_COPPER_DOOR = getBlockType("waxed_exposed_copper_door");

    Typed<CopperGolemStatue> WAXED_EXPOSED_COPPER_GOLEM_STATUE = getBlockType("waxed_exposed_copper_golem_statue");

    Typed<Waterlogged> WAXED_EXPOSED_COPPER_GRATE = getBlockType("waxed_exposed_copper_grate");

    Typed<Lantern> WAXED_EXPOSED_COPPER_LANTERN = getBlockType("waxed_exposed_copper_lantern");

    Typed<TrapDoor> WAXED_EXPOSED_COPPER_TRAPDOOR = getBlockType("waxed_exposed_copper_trapdoor");

    Typed<BlockData> WAXED_EXPOSED_CUT_COPPER = getBlockType("waxed_exposed_cut_copper");

    Typed<Slab> WAXED_EXPOSED_CUT_COPPER_SLAB = getBlockType("waxed_exposed_cut_copper_slab");

    Typed<Stairs> WAXED_EXPOSED_CUT_COPPER_STAIRS = getBlockType("waxed_exposed_cut_copper_stairs");

    Typed<LightningRod> WAXED_EXPOSED_LIGHTNING_ROD = getBlockType("waxed_exposed_lightning_rod");

    Typed<LightningRod> WAXED_LIGHTNING_ROD = getBlockType("waxed_lightning_rod");

    Typed<BlockData> WAXED_OXIDIZED_CHISELED_COPPER = getBlockType("waxed_oxidized_chiseled_copper");

    Typed<BlockData> WAXED_OXIDIZED_COPPER = getBlockType("waxed_oxidized_copper");

    Typed<Fence> WAXED_OXIDIZED_COPPER_BARS = getBlockType("waxed_oxidized_copper_bars");

    Typed<CopperBulb> WAXED_OXIDIZED_COPPER_BULB = getBlockType("waxed_oxidized_copper_bulb");

    Typed<Chain> WAXED_OXIDIZED_COPPER_CHAIN = getBlockType("waxed_oxidized_copper_chain");

    Typed<Chest> WAXED_OXIDIZED_COPPER_CHEST = getBlockType("waxed_oxidized_copper_chest");

    Typed<Door> WAXED_OXIDIZED_COPPER_DOOR = getBlockType("waxed_oxidized_copper_door");

    Typed<CopperGolemStatue> WAXED_OXIDIZED_COPPER_GOLEM_STATUE = getBlockType("waxed_oxidized_copper_golem_statue");

    Typed<Waterlogged> WAXED_OXIDIZED_COPPER_GRATE = getBlockType("waxed_oxidized_copper_grate");

    Typed<Lantern> WAXED_OXIDIZED_COPPER_LANTERN = getBlockType("waxed_oxidized_copper_lantern");

    Typed<TrapDoor> WAXED_OXIDIZED_COPPER_TRAPDOOR = getBlockType("waxed_oxidized_copper_trapdoor");

    Typed<BlockData> WAXED_OXIDIZED_CUT_COPPER = getBlockType("waxed_oxidized_cut_copper");

    Typed<Slab> WAXED_OXIDIZED_CUT_COPPER_SLAB = getBlockType("waxed_oxidized_cut_copper_slab");

    Typed<Stairs> WAXED_OXIDIZED_CUT_COPPER_STAIRS = getBlockType("waxed_oxidized_cut_copper_stairs");

    Typed<LightningRod> WAXED_OXIDIZED_LIGHTNING_ROD = getBlockType("waxed_oxidized_lightning_rod");

    Typed<BlockData> WAXED_WEATHERED_CHISELED_COPPER = getBlockType("waxed_weathered_chiseled_copper");

    Typed<BlockData> WAXED_WEATHERED_COPPER = getBlockType("waxed_weathered_copper");

    Typed<Fence> WAXED_WEATHERED_COPPER_BARS = getBlockType("waxed_weathered_copper_bars");

    Typed<CopperBulb> WAXED_WEATHERED_COPPER_BULB = getBlockType("waxed_weathered_copper_bulb");

    Typed<Chain> WAXED_WEATHERED_COPPER_CHAIN = getBlockType("waxed_weathered_copper_chain");

    Typed<Chest> WAXED_WEATHERED_COPPER_CHEST = getBlockType("waxed_weathered_copper_chest");

    Typed<Door> WAXED_WEATHERED_COPPER_DOOR = getBlockType("waxed_weathered_copper_door");

    Typed<CopperGolemStatue> WAXED_WEATHERED_COPPER_GOLEM_STATUE = getBlockType("waxed_weathered_copper_golem_statue");

    Typed<Waterlogged> WAXED_WEATHERED_COPPER_GRATE = getBlockType("waxed_weathered_copper_grate");

    Typed<Lantern> WAXED_WEATHERED_COPPER_LANTERN = getBlockType("waxed_weathered_copper_lantern");

    Typed<TrapDoor> WAXED_WEATHERED_COPPER_TRAPDOOR = getBlockType("waxed_weathered_copper_trapdoor");

    Typed<BlockData> WAXED_WEATHERED_CUT_COPPER = getBlockType("waxed_weathered_cut_copper");

    Typed<Slab> WAXED_WEATHERED_CUT_COPPER_SLAB = getBlockType("waxed_weathered_cut_copper_slab");

    Typed<Stairs> WAXED_WEATHERED_CUT_COPPER_STAIRS = getBlockType("waxed_weathered_cut_copper_stairs");

    Typed<LightningRod> WAXED_WEATHERED_LIGHTNING_ROD = getBlockType("waxed_weathered_lightning_rod");

    Typed<BlockData> WEATHERED_CHISELED_COPPER = getBlockType("weathered_chiseled_copper");

    Typed<BlockData> WEATHERED_COPPER = getBlockType("weathered_copper");

    Typed<Fence> WEATHERED_COPPER_BARS = getBlockType("weathered_copper_bars");

    Typed<CopperBulb> WEATHERED_COPPER_BULB = getBlockType("weathered_copper_bulb");

    Typed<Chain> WEATHERED_COPPER_CHAIN = getBlockType("weathered_copper_chain");

    Typed<Chest> WEATHERED_COPPER_CHEST = getBlockType("weathered_copper_chest");

    Typed<Door> WEATHERED_COPPER_DOOR = getBlockType("weathered_copper_door");

    Typed<CopperGolemStatue> WEATHERED_COPPER_GOLEM_STATUE = getBlockType("weathered_copper_golem_statue");

    Typed<Waterlogged> WEATHERED_COPPER_GRATE = getBlockType("weathered_copper_grate");

    Typed<Lantern> WEATHERED_COPPER_LANTERN = getBlockType("weathered_copper_lantern");

    Typed<TrapDoor> WEATHERED_COPPER_TRAPDOOR = getBlockType("weathered_copper_trapdoor");

    Typed<BlockData> WEATHERED_CUT_COPPER = getBlockType("weathered_cut_copper");

    Typed<Slab> WEATHERED_CUT_COPPER_SLAB = getBlockType("weathered_cut_copper_slab");

    Typed<Stairs> WEATHERED_CUT_COPPER_STAIRS = getBlockType("weathered_cut_copper_stairs");

    Typed<LightningRod> WEATHERED_LIGHTNING_ROD = getBlockType("weathered_lightning_rod");

    Typed<Ageable> WEEPING_VINES = getBlockType("weeping_vines");

    Typed<BlockData> WEEPING_VINES_PLANT = getBlockType("weeping_vines_plant");

    Typed<BlockData> WET_SPONGE = getBlockType("wet_sponge");

    Typed<Ageable> WHEAT = getBlockType("wheat");

    Typed<Rotatable> WHITE_BANNER = getBlockType("white_banner");

    Typed<Bed> WHITE_BED = getBlockType("white_bed");

    Typed<Candle> WHITE_CANDLE = getBlockType("white_candle");

    Typed<Lightable> WHITE_CANDLE_CAKE = getBlockType("white_candle_cake");

    Typed<BlockData> WHITE_CARPET = getBlockType("white_carpet");

    Typed<BlockData> WHITE_CONCRETE = getBlockType("white_concrete");

    Typed<BlockData> WHITE_CONCRETE_POWDER = getBlockType("white_concrete_powder");

    Typed<Directional> WHITE_GLAZED_TERRACOTTA = getBlockType("white_glazed_terracotta");

    Typed<Directional> WHITE_SHULKER_BOX = getBlockType("white_shulker_box");

    Typed<BlockData> WHITE_STAINED_GLASS = getBlockType("white_stained_glass");

    Typed<GlassPane> WHITE_STAINED_GLASS_PANE = getBlockType("white_stained_glass_pane");

    Typed<BlockData> WHITE_TERRACOTTA = getBlockType("white_terracotta");

    Typed<BlockData> WHITE_TULIP = getBlockType("white_tulip");

    Typed<Directional> WHITE_WALL_BANNER = getBlockType("white_wall_banner");

    Typed<BlockData> WHITE_WOOL = getBlockType("white_wool");

    Typed<FlowerBed> WILDFLOWERS = getBlockType("wildflowers");

    Typed<BlockData> WITHER_ROSE = getBlockType("wither_rose");

    Typed<Skull> WITHER_SKELETON_SKULL = getBlockType("wither_skeleton_skull");

    Typed<WallSkull> WITHER_SKELETON_WALL_SKULL = getBlockType("wither_skeleton_wall_skull");

    Typed<Rotatable> YELLOW_BANNER = getBlockType("yellow_banner");

    Typed<Bed> YELLOW_BED = getBlockType("yellow_bed");

    Typed<Candle> YELLOW_CANDLE = getBlockType("yellow_candle");

    Typed<Lightable> YELLOW_CANDLE_CAKE = getBlockType("yellow_candle_cake");

    Typed<BlockData> YELLOW_CARPET = getBlockType("yellow_carpet");

    Typed<BlockData> YELLOW_CONCRETE = getBlockType("yellow_concrete");

    Typed<BlockData> YELLOW_CONCRETE_POWDER = getBlockType("yellow_concrete_powder");

    Typed<Directional> YELLOW_GLAZED_TERRACOTTA = getBlockType("yellow_glazed_terracotta");

    Typed<Directional> YELLOW_SHULKER_BOX = getBlockType("yellow_shulker_box");

    Typed<BlockData> YELLOW_STAINED_GLASS = getBlockType("yellow_stained_glass");

    Typed<GlassPane> YELLOW_STAINED_GLASS_PANE = getBlockType("yellow_stained_glass_pane");

    Typed<BlockData> YELLOW_TERRACOTTA = getBlockType("yellow_terracotta");

    Typed<Directional> YELLOW_WALL_BANNER = getBlockType("yellow_wall_banner");

    Typed<BlockData> YELLOW_WOOL = getBlockType("yellow_wool");

    Typed<Skull> ZOMBIE_HEAD = getBlockType("zombie_head");

    Typed<WallSkull> ZOMBIE_WALL_HEAD = getBlockType("zombie_wall_head");
    // End generate - BlockType
    //</editor-fold>

    @SuppressWarnings("unchecked")
    private static <B extends BlockType> B getBlockType(@KeyPattern.Value final String key) {
        // Cast instead of using BlockType#typed, since block type can be a mock during testing and would return null
        return (B) Registry.BLOCK.getOrThrow(Key.key(Key.MINECRAFT_NAMESPACE, key));
    }

    /**
     * Yields this block type as a typed version of itself with a plain {@link BlockData} representing it.
     *
     * @return the typed block type.
     */
    Typed<BlockData> typed();

    /**
     * Yields this block type as a typed version of itself with a specific {@link BlockData} representing it.
     *
     * @param blockDataType the class type of the {@link BlockData} to type this {@link BlockType} with.
     * @param <B>          the generic type of the block data to type this block type with.
     * @return the typed block type.
     */
    <B extends BlockData> Typed<B> typed(Class<B> blockDataType);

    /**
     * Returns true if this BlockType has a corresponding {@link ItemType}.
     *
     * @return true if there is a corresponding ItemType, otherwise false
     * @see #getItemType()
     */
    boolean hasItemType();

    /**
     * Returns the corresponding {@link ItemType} for the given BlockType.
     * <p>
     * If there is no corresponding {@link ItemType} an error will be thrown.
     * <p>This is <b>NOT</b> the same as the {@link ItemType} with the same key,
     * but instead is the item associated with this block if this block
     * can be represented with an item.</p>
     *
     * @return the corresponding ItemType
     * @see #hasItemType()
     * @see BlockData#getPlacementMaterial()
     */
    ItemType getItemType();

    /**
     * Gets the BlockData class of this BlockType
     *
     * @return the BlockData class of this BlockType
     */
    Class<? extends BlockData> getBlockDataClass();

    /**
     * Creates a new {@link BlockData} instance for this block type, with all
     * properties initialized to unspecified defaults.
     *
     * @return new data instance
     */
    BlockData createBlockData();

    /**
     * Creates a collection of {@link BlockData} instances for this block type, with all
     * possible combinations of properties values.
     *
     * @return new block data collection
     */
    @Unmodifiable Collection<? extends BlockData> createBlockDataStates();

    /**
     * Creates a new {@link BlockData} instance for this block type, with all
     * properties initialized to unspecified defaults, except for those provided
     * in data.
     *
     * @param data data string
     * @return new data instance
     * @throws IllegalArgumentException if the specified data is not valid
     */
    BlockData createBlockData(@Nullable String data);

    /**
     * Check if the block type is solid (can be built upon)
     *
     * @return True if this block type is solid
     */
    boolean isSolid();

    /**
     * Check if the block type can catch fire
     *
     * @return True if this block type can catch fire
     */
    boolean isFlammable();

    /**
     * Check if the block type can burn away
     *
     * @return True if this block type can burn away
     */
    boolean isBurnable();

    /**
     * Check if the block type occludes light in the lighting engine.
     * <p>
     * Generally speaking, most full blocks will occlude light. Non-full blocks are
     * not occluding (e.g. anvils, chests, tall grass, stairs, etc.), nor are specific
     * full blocks such as barriers or spawners which block light despite their texture.
     * <p>
     * An occluding block will have the following effects:
     * <ul>
     *   <li>Chests cannot be opened if an occluding block is above it.
     *   <li>Mobs cannot spawn inside of occluding blocks.
     *   <li>Only occluding blocks can be "powered" ({@link Block#isBlockPowered()}).
     * </ul>
     * This list may be inconclusive. For a full list of the side effects of an occluding
     * block, see the <a href="https://minecraft.wiki/w/Opacity">Minecraft Wiki</a>.
     *
     * @return True if this block type occludes light
     */
    boolean isOccluding();

    /**
     * @return True if this block type is affected by gravity.
     */
    boolean hasGravity();

    /**
     * Checks if this block type can be interacted with.
     * <p>
     * Interactable block types include those with functionality when they are
     * interacted with by a player such as chests, furnaces, etc.
     * <p>
     * Some blocks such as piston heads and stairs are considered interactable
     * though may not perform any additional functionality.
     * <p>
     * Note that the interactability of some block types may be dependant on their
     * state as well. This method will return true if there is at least one
     * state in which additional interact handling is performed for the
     * block type.
     *
     * @deprecated This method is not comprehensive and does not accurately reflect what block types are
     * interactable. Many "interactions" are defined on the item not block, and many are conditional on some other world state
     * checks being true.
     *
     * @return true if this block type can be interacted with.
     */
    @Deprecated // Paper
    boolean isInteractable();

    /**
     * Obtains the block's hardness level (also known as "strength").
     * <br>
     * This number is used to calculate the time required to break each block.
     *
     * @return the hardness of that block type.
     */
    float getHardness();

    /**
     * Obtains the blast resistance value (also known as block "durability").
     * <br>
     * This value is used in explosions to calculate whether a block should be
     * broken or not.
     *
     * @return the blast resistance of that block type.
     */
    float getBlastResistance();

    /**
     * Returns a value that represents how 'slippery' the block is.
     * <p>
     * Blocks with higher slipperiness, like {@link BlockType#ICE} can be slid on
     * further by the player and other entities.
     * <p>
     * Most blocks have a default slipperiness of {@code 0.6f}.
     *
     * @return the slipperiness of this block
     */
    float getSlipperiness();

    /**
     * Check if the block type is an air block.
     *
     * @return True if this block type is an air block.
     */
    boolean isAir();

    /**
     * Gets if the BlockType is enabled by the features in a world.
     *
     * @param world the world to check
     * @return true if this BlockType can be used in this World.
     * @deprecated use {@link io.papermc.paper.world.flag.FeatureFlagSetHolder#isEnabled(io.papermc.paper.world.flag.FeatureDependant)}
     */
    @Deprecated(forRemoval = true, since = "1.21.1") // Paper
    boolean isEnabledByFeature(World world);

    /**
     * Tries to convert this BlockType into a Material
     *
     * @return the converted Material or null
     * @deprecated only for internal use
     */
    @Nullable
    @Deprecated(since = "1.20.6")
    Material asMaterial();

    /**
     * @deprecated use {@link #translationKey()} and {@link net.kyori.adventure.text.Component#translatable(net.kyori.adventure.translation.Translatable)}
     */
    @Deprecated(forRemoval = true)
    @Override
    String getTranslationKey();

    /**
     * Checks if this block type has collision.
     * <p>
     * @return false if this block never has collision, true if it <b>might</b> have collision
     */
    boolean hasCollision();
}
