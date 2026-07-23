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

    private final NumberValue health = new NumberValue("Health", "Disconnect below this HP", 6.0, 1.0, 20.0, 1.0);

    public AutoLog() {
        super(Category.Combat, "AutoLog", "Disconnect at low health.");
        addSetting(health);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.getHealth() <= health.getValue() && mc.getConnection() != null) {
            mc.getConnection().onDisconnect(null);
            setEnabled(false);
        }
    }
}
