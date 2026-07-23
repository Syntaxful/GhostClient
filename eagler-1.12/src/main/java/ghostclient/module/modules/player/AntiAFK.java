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
        ticks++;
        if (ticks >= 600 && mc.player != null) {
            ticks = 0;
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
    }
}
