package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

/**
 * Attacks when looking at an entity.
 */
public class TriggerBot extends Module {

    private final NumberValue interval = new NumberValue("Interval", "Ticks between attacks (1 = every tick)", 1, 1, 20, 1);
    private int ticks = 0;

    public TriggerBot() {
        super(Category.Combat, "TriggerBot", "Attack when crosshair is on an entity.");
        addSetting(interval);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) return;
        if (mc.objectMouseOver.entityHit instanceof EntityLivingBase && ((EntityLivingBase) mc.objectMouseOver.entityHit).isEntityAlive()) {
            ticks++;
            if (ticks >= interval.getInt()) {
                ticks = 0;
                mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
