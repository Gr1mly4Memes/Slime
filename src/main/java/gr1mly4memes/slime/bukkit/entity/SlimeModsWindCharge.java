package gr1mly4memes.slime.bukkit.entity;

import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.AbstractWindCharge;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAbstractWindCharge;

public class SlimeModsWindCharge extends CraftAbstractWindCharge {

    public SlimeModsWindCharge(CraftServer server, AbstractWindCharge entity) {
        super(server, entity);
    }
}
