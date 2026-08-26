package gr1mly4memes.slime.bukkit.neoforge;

import com.google.common.base.Preconditions;
import gr1mly4memes.slime.bukkit.entity.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.camel.CamelHusk;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.animal.equine.*;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.animal.fish.*;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilus;
import net.minecraft.world.entity.animal.panda.Panda;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.animal.squid.GlowSquid;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.monster.cubemob.MagmaCube;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.skeleton.*;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.zombie.*;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.arrow.SpectralArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.AbstractWindCharge;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.BreezeWindCharge;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.entity.projectile.throwableitemprojectile.*;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.minecart.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Mgazul
 * @date 2026/4/11 23:51
 */
public class EntityClassLookup {

    private static final Map<Class<? extends Entity>, BiFunction<CraftServer, Entity, CraftEntity>> ENTITY_LOOKUP_MAP = new HashMap<>();

    static {
        registerEntity(net.minecraft.world.entity.player.Player.class, (server, entity) -> new CraftHumanEntity(server, (net.minecraft.world.entity.player.Player) entity));
        registerEntity(ServerPlayer.class, (server, entity) -> new CraftPlayer(server, (ServerPlayer) entity));
        registerEntity(ElderGuardian.class, (server, entity) -> new CraftElderGuardian(server, (ElderGuardian) entity));
        registerEntity(WitherSkeleton.class, (server, entity) -> new CraftWitherSkeleton(server, (WitherSkeleton) entity));
        registerEntity(Stray.class, (server, entity) -> new CraftStray(server, (Stray) entity));
        registerEntity(Husk.class, (server, entity) -> new CraftHusk(server, (Husk) entity));
        registerEntity(ZombieVillager.class, (server, entity) -> new CraftVillagerZombie(server, (ZombieVillager) entity));
        registerEntity(SkeletonHorse.class, (server, entity) -> new CraftSkeletonHorse(server, (SkeletonHorse) entity));
        registerEntity(ZombieHorse.class, (server, entity) -> new CraftZombieHorse(server, (ZombieHorse) entity));
        registerEntity(ArmorStand.class, (server, entity) -> new CraftArmorStand(server, (ArmorStand) entity));
        registerEntity(Donkey.class, (server, entity) -> new CraftDonkey(server, (Donkey) entity));
        registerEntity(Mule.class, (server, entity) -> new CraftMule(server, (Mule) entity));
        registerEntity(Evoker.class, (server, entity) -> new CraftEvoker(server, (Evoker) entity));
        registerEntity(Vex.class, (server, entity) -> new CraftVex(server, (Vex) entity));
        registerEntity(Vindicator.class, (server, entity) -> new CraftVindicator(server, (Vindicator) entity));
        registerEntity(Illusioner.class, (server, entity) -> new CraftIllusioner(server, (Illusioner) entity));
        registerEntity(Creeper.class, (server, entity) -> new CraftCreeper(server, (Creeper) entity));
        registerEntity(Skeleton.class, (server, entity) -> new CraftSkeleton(server, (Skeleton) entity));
        registerEntity(Spider.class, (server, entity) -> new CraftSpider(server, (Spider) entity));
        registerEntity(Giant.class, (server, entity) -> new CraftGiant(server, (Giant) entity));
        registerEntity(Zombie.class, (server, entity) -> new CraftZombie(server, (Zombie) entity));
        registerEntity(Slime.class, (server, entity) -> new CraftSlime(server, (Slime) entity));
        registerEntity(Ghast.class, (server, entity) -> new CraftGhast(server, (Ghast) entity));
        registerEntity(ZombifiedPiglin.class, (server, entity) -> new CraftPigZombie(server, (ZombifiedPiglin) entity));
        registerEntity(EnderMan.class, (server, entity) -> new CraftEnderman(server, (EnderMan) entity));
        registerEntity(CaveSpider.class, (server, entity) -> new CraftCaveSpider(server, (CaveSpider) entity));
        registerEntity(Silverfish.class, (server, entity) -> new CraftSilverfish(server, (Silverfish) entity));
        registerEntity(Blaze.class, (server, entity) -> new CraftBlaze(server, (Blaze) entity));
        registerEntity(MagmaCube.class, (server, entity) -> new CraftMagmaCube(server, (MagmaCube) entity));
        registerEntity(WitherBoss.class, (server, entity) -> new CraftWither(server, (WitherBoss) entity));
        registerEntity(Bat.class, (server, entity) -> new CraftBat(server, (Bat) entity));
        registerEntity(Witch.class, (server, entity) -> new CraftWitch(server, (Witch) entity));
        registerEntity(Endermite.class, (server, entity) -> new CraftEndermite(server, (Endermite) entity));
        registerEntity(Guardian.class, (server, entity) -> new CraftGuardian(server, (Guardian) entity));
        registerEntity(Shulker.class, (server, entity) -> new CraftShulker(server, (Shulker) entity));
        registerEntity(Pig.class, (server, entity) -> new CraftPig(server, (Pig) entity));
        registerEntity(Sheep.class, (server, entity) -> new CraftSheep(server, (Sheep) entity));
        registerEntity(Cow.class, (server, entity) -> new CraftCow(server, (Cow) entity));
        registerEntity(Chicken.class, (server, entity) -> new CraftChicken(server, (Chicken) entity));
        registerEntity(Squid.class, (server, entity) -> new CraftSquid(server, (Squid) entity));
        registerEntity(Wolf.class, (server, entity) -> new CraftWolf(server, (Wolf) entity));
        registerEntity(MushroomCow.class, (server, entity) -> new CraftMushroomCow(server, (MushroomCow) entity));
        registerEntity(SnowGolem.class, (server, entity) -> new CraftSnowman(server, (SnowGolem) entity));
        registerEntity(Ocelot.class, (server, entity) -> new CraftOcelot(server, (Ocelot) entity));
        registerEntity(IronGolem.class, (server, entity) -> new CraftIronGolem(server, (IronGolem) entity));
        registerEntity(Horse.class, (server, entity) -> new CraftHorse(server, (Horse) entity));
        registerEntity(Rabbit.class, (server, entity) -> new CraftRabbit(server, (Rabbit) entity));
        registerEntity(PolarBear.class, (server, entity) -> new CraftPolarBear(server, (PolarBear) entity));
        registerEntity(Llama.class, (server, entity) -> new CraftLlama(server, (Llama) entity));
        registerEntity(Parrot.class, (server, entity) -> new CraftParrot(server, (Parrot) entity));
        registerEntity(Villager.class, (server, entity) -> new CraftVillager(server, (Villager) entity));
        registerEntity(Turtle.class, (server, entity) -> new CraftTurtle(server, (Turtle) entity));
        registerEntity(Phantom.class, (server, entity) -> new CraftPhantom(server, (Phantom) entity));
        registerEntity(Cod.class, (server, entity) -> new CraftCod(server, (Cod) entity));
        registerEntity(Salmon.class, (server, entity) -> new CraftSalmon(server, (Salmon) entity));
        registerEntity(Pufferfish.class, (server, entity) -> new CraftPufferFish(server, (Pufferfish) entity));
        registerEntity(TropicalFish.class, (server, entity) -> new CraftTropicalFish(server, (TropicalFish) entity));
        registerEntity(Drowned.class, (server, entity) -> new CraftDrowned(server, (Drowned) entity));
        registerEntity(Dolphin.class, (server, entity) -> new CraftDolphin(server, (Dolphin) entity));
        registerEntity(Cat.class, (server, entity) -> new CraftCat(server, (Cat) entity));
        registerEntity(Panda.class, (server, entity) -> new CraftPanda(server, (Panda) entity));
        registerEntity(Pillager.class, (server, entity) -> new CraftPillager(server, (Pillager) entity));
        registerEntity(Ravager.class, (server, entity) -> new CraftRavager(server, (Ravager) entity));
        registerEntity(TraderLlama.class, (server, entity) -> new CraftTraderLlama(server, (TraderLlama) entity));
        registerEntity(WanderingTrader.class, (server, entity) -> new CraftWanderingTrader(server, (WanderingTrader) entity));
        registerEntity(Fox.class, (server, entity) -> new CraftFox(server, (Fox) entity));
        registerEntity(Bee.class, (server, entity) -> new CraftBee(server, (Bee) entity));
        registerEntity(Hoglin.class, (server, entity) -> new CraftHoglin(server, (Hoglin) entity));
        registerEntity(Piglin.class, (server, entity) -> new CraftPiglin(server, (Piglin) entity));
        registerEntity(Strider.class, (server, entity) -> new CraftStrider(server, (Strider) entity));
        registerEntity(Zoglin.class, (server, entity) -> new CraftZoglin(server, (Zoglin) entity));
        registerEntity(PiglinBrute.class, (server, entity) -> new CraftPiglinBrute(server, (PiglinBrute) entity));
        registerEntity(Axolotl.class, (server, entity) -> new CraftAxolotl(server, (Axolotl) entity));
        registerEntity(GlowSquid.class, (server, entity) -> new CraftGlowSquid(server, (GlowSquid) entity));
        registerEntity(Goat.class, (server, entity) -> new CraftGoat(server, (Goat) entity));
        registerEntity(Allay.class, (server, entity) -> new CraftAllay(server, (Allay) entity));
        registerEntity(Frog.class, (server, entity) -> new CraftFrog(server, (Frog) entity));
        registerEntity(Tadpole.class, (server, entity) -> new CraftTadpole(server, (Tadpole) entity));
        registerEntity(Warden.class, (server, entity) -> new CraftWarden(server, (Warden) entity));
        registerEntity(Camel.class, (server, entity) -> new CraftCamel(server, (Camel) entity));
        registerEntity(Sniffer.class, (server, entity) -> new CraftSniffer(server, (Sniffer) entity));
        registerEntity(Breeze.class, (server, entity) -> new CraftBreeze(server, (Breeze) entity));
        registerEntity(Creaking.class, (server, entity) -> new CraftCreaking(server, (Creaking) entity));
        registerEntity(CopperGolem.class, (server, entity) -> new CraftCopperGolem(server, (CopperGolem) entity));
        registerEntity(HappyGhast.class, (server, entity) -> new CraftHappyGhast(server, (HappyGhast) entity));
        registerEntity(Mannequin.class, (server, entity) -> new CraftMannequin(server, (Mannequin) entity));
        registerEntity(CamelHusk.class, (server, entity) -> new CraftCamelHusk(server, (CamelHusk) entity));
        registerEntity(Parched.class, (server, entity) -> new CraftParched(server, (Parched) entity));
        registerEntity(ZombieNautilus.class, (server, entity) -> new CraftZombieNautilus(server, (ZombieNautilus) entity));
        registerEntity(EnderDragon.class, (server, entity) -> new CraftEnderDragon(server, (EnderDragon) entity));
        registerEntity(LargeFireball.class, (server, entity) -> new CraftLargeFireball(server, (LargeFireball) entity));
        registerEntity(SmallFireball.class, (server, entity) -> new CraftSmallFireball(server, (SmallFireball) entity));
        registerEntity(WitherSkull.class, (server, entity) -> new CraftWitherSkull(server, (WitherSkull) entity));
        registerEntity(DragonFireball.class, (server, entity) -> new CraftDragonFireball(server, (DragonFireball) entity));
        registerEntity(WindCharge.class, (server, entity) -> new CraftWindCharge(server, (WindCharge) entity));
        registerEntity(Painting.class, (server, entity) -> new CraftPainting(server, (Painting) entity));
        registerEntity(ItemFrame.class, (server, entity) -> new CraftItemFrame(server, (ItemFrame) entity));
        registerEntity(GlowItemFrame.class, (server, entity) -> new CraftGlowItemFrame(server, (GlowItemFrame) entity));
        registerEntity(Arrow.class, (server, entity) -> new CraftArrow(server, (Arrow) entity));
        registerEntity(ThrownEnderpearl.class, (server, entity) -> new CraftEnderPearl(server, (ThrownEnderpearl) entity));
        registerEntity(ThrownExperienceBottle.class, (server, entity) -> new CraftThrownExpBottle(server, (ThrownExperienceBottle) entity));
        registerEntity(SpectralArrow.class, (server, entity) -> new CraftSpectralArrow(server, (SpectralArrow) entity));
        registerEntity(EndCrystal.class, (server, entity) -> new CraftEnderCrystal(server, (EndCrystal) entity));
        registerEntity(ThrownTrident.class, (server, entity) -> new CraftTrident(server, (ThrownTrident) entity));
        registerEntity(LightningBolt.class, (server, entity) -> new CraftLightningStrike(server, (LightningBolt) entity));
        registerEntity(ShulkerBullet.class, (server, entity) -> new CraftShulkerBullet(server, (ShulkerBullet) entity));
        registerEntity(LlamaSpit.class, (server, entity) -> new CraftLlamaSpit(server, (LlamaSpit) entity));
        registerEntity(Marker.class, (server, entity) -> new CraftMarker(server, (Marker) entity));
        registerEntity(Display.BlockDisplay.class, (server, entity) -> new CraftBlockDisplay(server, (Display.BlockDisplay) entity));
        registerEntity(Interaction.class, (server, entity) -> new CraftInteraction(server, (Interaction) entity));
        registerEntity(Display.ItemDisplay.class, (server, entity) -> new CraftItemDisplay(server, (Display.ItemDisplay) entity));
        registerEntity(Display.TextDisplay.class, (server, entity) -> new CraftTextDisplay(server, (Display.TextDisplay) entity));
        registerEntity(ItemEntity.class, (server, entity) -> new CraftItem(server, (ItemEntity) entity));
        registerEntity(ExperienceOrb.class, (server, entity) -> new CraftExperienceOrb(server, (ExperienceOrb) entity));
        registerEntity(AreaEffectCloud.class, (server, entity) -> new CraftAreaEffectCloud(server, (AreaEffectCloud) entity));
        registerEntity(ThrownEgg.class, (server, entity) -> new CraftEgg(server, (ThrownEgg) entity));
        registerEntity(LeashFenceKnotEntity.class, (server, entity) -> new CraftLeash(server, (LeashFenceKnotEntity) entity));
        registerEntity(Snowball.class, (server, entity) -> new CraftSnowball(server, (Snowball) entity));
        registerEntity(EyeOfEnder.class, (server, entity) -> new CraftEnderSignal(server, (EyeOfEnder) entity));
        registerEntity(PrimedTnt.class, (server, entity) -> new CraftTNTPrimed(server, (PrimedTnt) entity));
        registerEntity(FallingBlockEntity.class, (server, entity) -> new CraftFallingBlock(server, (FallingBlockEntity) entity));
        registerEntity(FireworkRocketEntity.class, (server, entity) -> new CraftFirework(server, (FireworkRocketEntity) entity));
        registerEntity(EvokerFangs.class, (server, entity) -> new CraftEvokerFangs(server, (EvokerFangs) entity));
        registerEntity(MinecartCommandBlock.class, (server, entity) -> new CraftMinecartCommand(server, (MinecartCommandBlock) entity));
        registerEntity(Minecart.class, (server, entity) -> new CraftMinecartRideable(server, (Minecart) entity));
        registerEntity(MinecartChest.class, (server, entity) -> new CraftMinecartChest(server, (MinecartChest) entity));
        registerEntity(MinecartFurnace.class, (server, entity) -> new CraftMinecartFurnace(server, (MinecartFurnace) entity));
        registerEntity(MinecartTNT.class, (server, entity) -> new CraftMinecartTNT(server, (MinecartTNT) entity));
        registerEntity(MinecartHopper.class, (server, entity) -> new CraftMinecartHopper(server, (MinecartHopper) entity));
        registerEntity(MinecartSpawner.class, (server, entity) -> new CraftMinecartMobSpawner(server, (MinecartSpawner) entity));
        registerEntity(FishingHook.class, (server, entity) -> new CraftFishHook(server, (FishingHook) entity));
        registerEntity(Bogged.class, (server, entity) -> new CraftBogged(server, (Bogged) entity));
        registerEntity(OminousItemSpawner.class, (server, entity) -> new CraftOminousItemSpawner(server, (OminousItemSpawner) entity));
        registerEntity(Armadillo.class, (server, entity) -> new CraftArmadillo(server, (Armadillo) entity));
        registerEntity(BreezeWindCharge.class, (server, entity) -> new CraftBreezeWindCharge(server, (BreezeWindCharge) entity));
        registerEntity(AbstractMinecartContainer.class, (server, entity) -> new YouerModsMinecartContainer(server, (AbstractMinecartContainer) entity));
        registerEntity(AbstractFish.class, (server, entity) -> new CraftFish(server, (AbstractFish) entity));
        registerEntity(LivingEntity.class, (server, entity) -> new CraftLivingEntity(server, (LivingEntity) entity));
        registerEntity(Projectile.class, (server, entity) -> new YouerModsProjectileEntity(server, (Projectile) entity));
        registerEntity(VehicleEntity.class, (server, entity) -> new YouerModsVehicle(server, (VehicleEntity) entity));
        registerEntity(AbstractWindCharge.class, (server, entity) -> new YouerModsWindCharge(server, (AbstractWindCharge) entity));
        registerEntity(ThrowableItemProjectile.class, (server, entity) -> new YouerModsThrowableProjectile(server, (ThrowableItemProjectile) entity));
        registerEntity(HangingEntity.class, (server, entity) -> new CraftHanging(server, (HangingEntity) entity));
        registerEntity(Display.class, (server, entity) -> new CraftDisplay(server, (Display) entity));
        registerEntity(AgeableMob.class, (server, entity) -> new CraftAgeable(server, (AgeableMob) entity));
        registerEntity(PathfinderMob.class, (server, entity) -> new CraftCreature(server, (PathfinderMob) entity));
        registerEntity(Monster.class, (server, entity) -> new CraftMonster(server, (Monster) entity));
        registerEntity(Mob.class, (server, entity) -> new YouerModsMob(server, (Mob) entity));
        registerEntity(Entity.class, (server, entity) -> new YouerModsEntity(server, entity));
    }

    private static void registerEntity(Class<? extends Entity> clazz, BiFunction<CraftServer, Entity, CraftEntity> factory) {
        ENTITY_LOOKUP_MAP.put(clazz, factory);
    }

    public static <T extends Entity> CraftEntity getEntity(CraftServer server, T entity) {
        Preconditions.checkArgument(entity != null, "Unknown entity");

        if (entity instanceof EnderDragonPart complexPart) {
            if (complexPart.parentMob instanceof EnderDragon) {
                return new CraftEnderDragonPart(server, complexPart);
            } else {
                return new CraftComplexPart(server, complexPart);
            }
        }

        Class<?> entityClass = entity.getClass();

        while (entityClass != null) {
            BiFunction<CraftServer, Entity, CraftEntity> factory = ENTITY_LOOKUP_MAP.get(entityClass);
            if (factory != null) {
                return factory.apply(server, entity);
            }
            entityClass = entityClass.getSuperclass();
        }

        throw new AssertionError("Unknown entity " + (entity == null ? null : entity.getClass()));
    }
}

