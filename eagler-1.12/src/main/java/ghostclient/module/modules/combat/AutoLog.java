package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically disconnects when health drops below a threshold.
 */
public class AutoLog extends Module {

    private final NumberValue health = new NumberValue("Health", "Disconnect when health is at or below this value.", 6.0, 1.0, 19.0, 0.5);

    public AutoLog() {
        super(Category.Combat, "AutoLog", "Disconnect when health is low.");
        addSetting(health);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.player.connection == null) return;
        if (mc.player.getHealth() <= health.getFloat()) {
            if (mc.world != null) {
                mc.world.sendQuittingDisconnectingPacket();
            }
            mc.loadWorld(null);
            setEnabled(false);
        }
    }
}
