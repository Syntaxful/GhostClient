package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * Smoothly aims at the closest entity.
 */
public class AimAssist extends Module {

    private final NumberValue range = new NumberValue("Range", "Aim range", 4.0, 1.0, 8.0, 0.1);
    private final NumberValue speed = new NumberValue("Speed", "Aim speed", 0.5, 0.1, 1.0, 0.05);

    public AimAssist() {
        super(Category.Combat, "AimAssist", "Smoothly aim at nearby entities.");
        addSetting(range);
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        Entity target = findTarget();
        if (target == null) return;

        double dx = target.posX - mc.player.posX;
        double dy = target.posY + target.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        double dz = target.posZ - mc.player.posZ;

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        mc.player.rotationYaw = MathHelper.clamp(lerpAngle(mc.player.rotationYaw, yaw, speed.getFloat()), -180, 180);
        mc.player.rotationPitch = MathHelper.clamp(lerp(mc.player.rotationPitch, pitch, speed.getFloat()), -90, 90);
    }

    private Entity findTarget() {
        double best = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive() || !(entity instanceof EntityLivingBase)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < best) {
                best = dist;
                target = entity;
            }
        }
        return target;
    }

    private float lerp(float start, float end, float factor) {
        return start + (end - start) * factor;
    }

    private float lerpAngle(float start, float end, float factor) {
        float diff = ((end - start + 540) % 360) - 180;
        return start + diff * factor;
    }
}
