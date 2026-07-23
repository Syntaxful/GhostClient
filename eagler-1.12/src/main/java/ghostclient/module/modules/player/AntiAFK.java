package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Jumps periodically to avoid AFK kicks.
 */
public class AntiAFK extends Module {

    private int ticks = 0;

    public AntiAFK() {
        super(Category.Player, "AntiAFK", "Jump periodically to avoid AFK kicks.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        ticks++;
        if (ticks >= 600) {
            ticks = 0;
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
    }
}
