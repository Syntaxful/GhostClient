package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Draws lines to nearby players.
 */
public class Tracers extends Module {

    public Tracers() {
        super(Category.Render, "Tracers", "Draw lines to nearby players.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.getRenderManager() == null || mc.player == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != mc.player) {
                RenderUtils.renderTracer(entity.posX, entity.posY + entity.height / 2, entity.posZ, 0xFFFFFFFF);
            }
        }
    }
}
