package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAbstractSkeleton;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

public class SlimeModsSkeleton extends CraftAbstractSkeleton {
    public SlimeModsSkeleton(CraftServer server, AbstractSkeleton entity) {
        super(server, entity);
    }

    public @NotNull Skeleton.SkeletonType getSkeletonType() {
        return Skeleton.SkeletonType.NORMAL;
    }

    @Override
    public String toString() {
        return "SlimeModsSkeleton{" + getType() + '}';
    }
}
