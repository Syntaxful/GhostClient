package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.MathHelper;

/**
 * Aims the bow at the closest entity.
 */
public class BowAimbot extends Module {

    private final NumberValue range = new NumberValue("Range", "Aim range", 40, 10, 100, 5);

    public BowAimbot() {
        super(Category.Combat, "BowAimbot", "Auto aim bow at entities.");
        addSetting(range);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) return;
        Entity target = findTarget();
        if (target == null) return;

        double dx = target.posX - mc.player.posX;
        double dy = target.posY + target.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        double dz = target.posZ - mc.player.posZ;

        mc.player.rotationYaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        mc.player.rotationPitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
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
}
