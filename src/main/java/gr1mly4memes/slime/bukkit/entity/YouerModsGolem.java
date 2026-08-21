package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.animal.golem.AbstractGolem;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftGolem;

public class YouerModsGolem extends CraftGolem {

    public YouerModsGolem(CraftServer server, AbstractGolem entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "YouerModsGolem{" + getType() + '}';
    }
}
