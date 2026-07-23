package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

/**
 * Automatically attacks nearby entities.
 */
public class KillAura extends Module {

    private final NumberValue range = new NumberValue("Range", "Attack range", 4.5, 1.0, 6.0, 0.1);
    private final NumberValue aps = new NumberValue("APS", "Attacks per second", 10, 1, 20, 1);
    private int ticksSinceAttack = 0;

    public KillAura() {
        super(Category.Combat, "KillAura", "Automatically attacks nearby entities.");
        addSetting(range);
        addSetting(aps);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        ticksSinceAttack++;
        int interval = 20 / aps.getInt();
        if (interval < 1) interval = 1;
        if (ticksSinceAttack < interval) return;

        Entity target = findTarget();
        if (target == null) return;

        ticksSinceAttack = 0;
        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    private Entity findTarget() {
        double bestRange = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player) continue;
            if (!entity.isEntityAlive()) continue;
            if (!(entity instanceof EntityLivingBase)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist > range.getValue()) continue;
            if (dist < bestRange) {
                bestRange = dist;
                target = entity;
            }
        }
        return target;
    }
}
