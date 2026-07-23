package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import net.minecraft.block.BlockBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks beds around the player. Works in survival and creative modes.
 */
public class BedAura extends Module {

    private final NumberValue range = new NumberValue("Range", "Bed break range", 5, 1, 7, 1);
    private final BooleanValue survival = new BooleanValue("Survival", "Break beds in survival mode", true);
    private final BooleanValue strict = new BooleanValue("Strict", "Only break beds that are unoccupied", true);

    public BedAura() {
        super(Category.Combat, "BedAura", "Break nearby beds automatically.");
        addSetting(range);
        addSetting(survival);
        addSetting(strict);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        int r = range.getInt();
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() instanceof BlockBed) {
                        if (strict.getValue() && mc.world.getBlockState(pos).getValue(BlockBed.OCCUPIED).booleanValue()) continue;
                        mc.playerController.clickBlock(pos, EnumFacing.UP);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        return;
                    }
                }
            }
        }
    }
}
