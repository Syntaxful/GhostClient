package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.MathHelper;

/**
 * Aims the bow at the nearest enemy while charging.
 */
public class BowAimbot extends Module {

    public BowAimbot() {
        super(Category.Combat, "BowAimBot", "Aims bow at nearest target.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.getHeldItemMainhand() == null || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) return;
        if (!mc.gameSettings.keyBindUseItem.isKeyDown()) return;

        Entity target = findTarget();
        if (target == null) return;

        double dx = target.posX - mc.player.posX;
        double dy = (target.posY + target.getEyeHeight()) - (mc.player.posY + mc.player.getEyeHeight());
        double dz = target.posZ - mc.player.posZ;
        double distance = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        // Rough projectile-drop compensation
        float pitch = (float) -Math.toDegrees(Math.atan2(dy + 0.05 * distance, distance));
        mc.player.rotationYaw = MathHelper.clamp(yaw, -180, 180);
        mc.player.rotationPitch = MathHelper.clamp(pitch, -90, 45);
    }

    private Entity findTarget() {
        double best = 50.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive() || !(entity instanceof EntityLivingBase)) continue;
            if (!(entity instanceof EntityPlayer)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < best) {
                best = dist;
                target = entity;
            }
        }
        return target;
    }
}
