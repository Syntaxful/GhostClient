package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes the hurt camera shake.
 */
public class NoHurtCam extends Module {

    public NoHurtCam() {
        super(Category.Render, "NoHurtCam", "Disables the hurt camera shake.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.hurtTime > 0) {
            mc.player.hurtTime = 0;
            mc.player.hurtResistantTime = 0;
        }
    }

    public boolean shouldCancelHurtCam() {
        return isEnabled();
    }
}
