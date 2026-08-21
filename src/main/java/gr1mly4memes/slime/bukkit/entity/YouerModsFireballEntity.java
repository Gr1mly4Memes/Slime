package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftFireball;

public class YouerModsFireballEntity extends CraftFireball {

    public YouerModsFireballEntity(CraftServer server, Fireball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "YouerModsFireballEntity{" + getType() + '}';
    }
}
