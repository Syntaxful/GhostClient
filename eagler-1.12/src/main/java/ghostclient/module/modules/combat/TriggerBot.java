package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

/**
 * Attacks when looking at an entity.
 */
public class TriggerBot extends Module {

    private int ticks = 0;

    public TriggerBot() {
        super(Category.Combat, "TriggerBot", "Attack when crosshair is on an entity.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) return;
        if (mc.objectMouseOver.entityHit instanceof EntityLivingBase && ((EntityLivingBase) mc.objectMouseOver.entityHit).isEntityAlive()) {
            ticks++;
            if (ticks >= 12) {
                ticks = 0;
                mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
