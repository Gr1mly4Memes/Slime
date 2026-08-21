package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.TamableAnimal;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftTameableAnimal;

public class YouerModsTameableEntity extends CraftTameableAnimal {

    public YouerModsTameableEntity(CraftServer server, TamableAnimal entity) {
        super(server, entity);
    }


    @Override
    public TamableAnimal getHandle() {
        return (TamableAnimal) entity;
    }

    @Override
    public String toString() {
        return "YouerCustomTameableAnimal{" + getType() + '}';
    }
}
