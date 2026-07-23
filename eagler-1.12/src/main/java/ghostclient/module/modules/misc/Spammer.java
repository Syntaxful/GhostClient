package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Spams chat messages.
 */
public class Spammer extends Module {

    private final NumberValue delay = new NumberValue("Delay", "Seconds between messages", 5, 1, 60, 1);
    private int ticks = 0;

    public Spammer() {
        super(Category.Misc, "Spammer", "Spam chat messages.");
        addSetting(delay);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        ticks++;
        if (ticks >= delay.getInt() * 20) {
            ticks = 0;
            mc.player.sendChatMessage("GhostClient on top!");
        }
    }
}
