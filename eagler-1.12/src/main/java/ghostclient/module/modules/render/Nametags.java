package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Renders larger nametags above players.
 */
public class Nametags extends Module {

    public Nametags() {
        super(Category.Render, "Nametags", "Draw big nametags above players.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.player == null || mc.getRenderManager() == null) return;
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player) continue;
            // TODO: draw custom scaled nametag via GhostClient renderer
        }
    }
}
