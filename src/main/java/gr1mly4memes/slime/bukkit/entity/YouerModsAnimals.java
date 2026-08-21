package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAnimals;

public class YouerModsAnimals extends CraftAnimals {

    public YouerModsAnimals(CraftServer server, Animal entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "YouerModsAnimals{" + getType() + '}';
    }
}
