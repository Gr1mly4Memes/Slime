package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.Projectile;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftProjectile;

public class SlimeModsProjectileEntity extends CraftProjectile {

    public SlimeModsProjectileEntity(CraftServer server, Projectile entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsProjectileEntity{" + getType() + '}';
    }
}

