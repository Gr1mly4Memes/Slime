package gr1mly4memes.slime.bukkit.block;

import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import org.bukkit.craftbukkit.block.CraftBlock;

public class SlimeBlockSnapshot extends CraftBlock {

    private final BlockState blockState;

    public SlimeBlockSnapshot(BlockSnapshot blockSnapshot, boolean current) {
        super(blockSnapshot.getLevel(), blockSnapshot.getPos());
        this.blockState = current ? blockSnapshot.getCurrentState() : blockSnapshot.getState();
    }

    public static SlimeBlockSnapshot fromBlockSnapshot(BlockSnapshot blockSnapshot, boolean current) {
        return new SlimeBlockSnapshot(blockSnapshot, current);
    }

    @Override
    public BlockState getBlockState() {
        return blockState;
    }
}