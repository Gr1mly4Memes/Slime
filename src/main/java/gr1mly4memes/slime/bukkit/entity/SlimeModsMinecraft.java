package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMinecart;

public class SlimeModsMinecraft extends CraftMinecart {

    public SlimeModsMinecraft(CraftServer server, AbstractMinecart entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsMinecraft{" + getType() + '}';
    }
}
