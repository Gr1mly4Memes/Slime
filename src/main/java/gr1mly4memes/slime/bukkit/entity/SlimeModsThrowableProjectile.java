package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftThrowableProjectile;

public class SlimeModsThrowableProjectile extends CraftThrowableProjectile {

    public SlimeModsThrowableProjectile(CraftServer server, ThrowableItemProjectile entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsThrowableProjectile{" + getType() + '}';
    }
}
