package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMonster;

public class SlimeModsMonster extends CraftMonster {

    public SlimeModsMonster(CraftServer server, Monster entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsMonster{" + getType() + '}';
    }
}
