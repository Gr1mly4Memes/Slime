package gr1mly4memes.slime.bukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

public class SlimeModsEntity extends CraftEntity {

    public SlimeModsEntity(CraftServer server, net.minecraft.world.entity.Entity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "SlimeModsEntity{" + this.getType() + '}';
    }
}
