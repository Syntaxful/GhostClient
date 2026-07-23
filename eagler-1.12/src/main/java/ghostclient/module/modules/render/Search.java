package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import ghostclient.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Highlights configured ore blocks within a small radius.
 */
public class Search extends Module {

    private final ModeValue block = new ModeValue("Block", "Block to search", "Diamond", "Diamond", "Emerald", "Gold", "Iron", "Lapis", "Redstone", "Coal");

    public Search() {
        super(Category.Render, "Search", "Highlight specific blocks.");
        addSetting(block);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.player == null || mc.getRenderManager() == null) return;

        Block target = getTargetBlock();
        if (target == null) return;
        int color = getTargetColor();

        int range = 12;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() == target) {
                        AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                        RenderUtils.drawBox(box, color, true);
                    }
                }
            }
        }
    }

    private Block getTargetBlock() {
        String name = block.getValue();
        if ("Emerald".equals(name)) return Blocks.EMERALD_ORE;
        if ("Gold".equals(name)) return Blocks.GOLD_ORE;
        if ("Iron".equals(name)) return Blocks.IRON_ORE;
        if ("Lapis".equals(name)) return Blocks.LAPIS_ORE;
        if ("Redstone".equals(name)) return Blocks.REDSTONE_ORE;
        if ("Coal".equals(name)) return Blocks.COAL_ORE;
        return Blocks.DIAMOND_ORE;
    }

    private int getTargetColor() {
        String name = block.getValue();
        if ("Emerald".equals(name)) return 0xFF00FF00;
        if ("Gold".equals(name)) return 0xFFFFD700;
        if ("Iron".equals(name)) return 0xFFFFD6D6;
        if ("Lapis".equals(name)) return 0xFF0000FF;
        if ("Redstone".equals(name)) return 0xFFFF0000;
        if ("Coal".equals(name)) return 0xFF222222;
        return 0xFF00FFFF;
    }
}
