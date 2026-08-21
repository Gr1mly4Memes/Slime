package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMonster;

public class YouerModsMonster extends CraftMonster {

    public YouerModsMonster(CraftServer server, Monster entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "YouerModsMonster{" + getType() + '}';
    }
}
