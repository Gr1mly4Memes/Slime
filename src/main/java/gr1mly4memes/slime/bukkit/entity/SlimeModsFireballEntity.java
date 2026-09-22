package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftFireball;

public class SlimeModsFireballEntity extends CraftFireball {

    public SlimeModsFireballEntity(CraftServer server, Fireball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsFireballEntity{" + getType() + '}';
    }
}
