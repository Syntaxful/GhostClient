package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.MathHelper;

/**
 * Aims the bow at the closest entity, with target prediction.
 */
public class BowAimbot extends Module {

    private final NumberValue range = new NumberValue("Range", "Aim range", 60, 10, 120, 5);
    private final BooleanValue prediction = new BooleanValue("Prediction", "Lead moving targets", true);
    private final BooleanValue playersOnly = new BooleanValue("PlayersOnly", "Only aim at players", false);

    public BowAimbot() {
        super(Category.Combat, "BowAimbot", "Auto aim bow at entities.");
        addSetting(range);
        addSetting(prediction);
        addSetting(playersOnly);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null || mc.player.getHeldItemMainhand() == null || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) return;
        Entity target = findTarget();
        if (target == null) return;

        double tx = target.posX;
        double ty = target.posY + target.getEyeHeight();
        double tz = target.posZ;

        if (prediction.getValue()) {
            double velocityFactor = 0.5;
            tx += target.motionX * 3.0 * velocityFactor;
            tz += target.motionZ * 3.0 * velocityFactor;
        }

        double dx = tx - mc.player.posX;
        double dy = ty - (mc.player.posY + mc.player.getEyeHeight());
        double dz = tz - mc.player.posZ;
        double dist = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, dist));
        pitch += 2.0f; // slight arc compensation

        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
    }

    private Entity findTarget() {
        double best = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive() || !(entity instanceof EntityLivingBase)) continue;
            if (playersOnly.getValue() && !(entity instanceof net.minecraft.entity.player.EntityPlayer)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < best) {
                best = dist;
                target = entity;
            }
        }
        return target;
    }
}
