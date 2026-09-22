package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftProjectile;

public class SlimeModsThrowableEntity extends CraftProjectile {
    public SlimeModsThrowableEntity(CraftServer server, ThrowableProjectile entity) {
        super(server, entity);
    }

    @Override
    public ThrowableProjectile getHandle() {
        return (ThrowableProjectile) entity;
    }

    @Override
    public String toString() {
        return "SlimeModsThrowableEntity{" + getType() + '}';
    }
}
