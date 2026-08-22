package gr1mly4memes.slime.bukkit.block;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.craftbukkit.block.CraftBlock;

public class SlimeBlockSnapshot extends CraftBlock {

    private final BlockState blockState;

    public SlimeBlockSnapshot(net.neoforged.neoforge.common.util.BlockSnapshot blockSnapshot, boolean current) {
        super(blockSnapshot.getLevel(), blockSnapshot.getPos());
        this.blockState = current ? blockSnapshot.getCurrentState() : blockSnapshot.getState();
    }

    public static SlimeBlockSnapshot fromBlockSnapshot(net.neoforged.neoforge.common.util.BlockSnapshot blockSnapshot, boolean current) {
        return new SlimeBlockSnapshot(blockSnapshot, current);
    }

    @Override
    public BlockState getNMS() {
        return blockState;
    }
}
