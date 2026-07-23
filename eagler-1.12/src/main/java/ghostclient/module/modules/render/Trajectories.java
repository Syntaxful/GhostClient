package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.util.math.Vec3d;

/**
 * Predicts projectile trajectories.
 */
public class Trajectories extends Module {

    public Trajectories() {
        super(Category.Render, "Trajectories", "Show where projectiles will land.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.player.getHeldItemMainhand() == null) return;
        if (isProjectile(mc.player.getHeldItemMainhand().getItem())) {
            Vec3d pos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
            Vec3d motion = new Vec3d(-Math.sin(Math.toRadians(mc.player.rotationYaw)) * Math.cos(Math.toRadians(mc.player.rotationPitch)),
                    -Math.sin(Math.toRadians(mc.player.rotationPitch)),
                    Math.cos(Math.toRadians(mc.player.rotationYaw)) * Math.cos(Math.toRadians(mc.player.rotationPitch)));
            motion = motion.scale(1.5);
            RenderUtils.prepare2D();
            for (int i = 0; i < 40; i++) {
                Vec3d render = RenderUtils.getRenderPos(pos.xCoord, pos.yCoord, pos.zCoord);
                RenderUtils.drawRect((int) render.xCoord - 1, (int) render.yCoord - 1, 2, 2, 0xAAFFFFFF);
                pos = pos.add(motion);
                motion = motion.scale(0.99).subtract(0, 0.03, 0);
            }
            RenderUtils.end2D();
        }
    }

    private boolean isProjectile(net.minecraft.item.Item item) {
        return item instanceof ItemBow || item instanceof ItemEnderPearl || item instanceof ItemPotion || item instanceof ItemExpBottle;
    }
}
