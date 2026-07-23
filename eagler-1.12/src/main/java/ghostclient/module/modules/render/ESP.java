package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Highlights nearby entities with boxes.
 */
public class ESP extends Module {

    public ESP() {
        super(Category.Render, "ESP", "See entities through walls.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.getRenderManager() == null || mc.player == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase && entity != mc.player) {
                int color = entity instanceof EntityPlayer ? 0xFFFFFFFF : 0xFFFF0000;
                AxisAlignedBB box = entity.getEntityBoundingBox();
                RenderUtils.drawBox(box, color, false);
            }
        }
    }
}
