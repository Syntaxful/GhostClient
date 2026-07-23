package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Reduces knockback taken.
 */
public class Velocity extends Module {

    private final NumberValue horizontal = new NumberValue("Horizontal", "Horizontal velocity %", 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", "Vertical velocity %", 0, 0, 100, 1);

    public Velocity() {
        super(Category.Combat, "Velocity", "Reduce knockback.");
        addSetting(horizontal);
        addSetting(vertical);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.hurtTime > 0 && mc.player.hurtResistantTime > 0) {
            mc.player.motionX *= horizontal.getValue() / 100.0;
            mc.player.motionZ *= horizontal.getValue() / 100.0;
            mc.player.motionY *= vertical.getValue() / 100.0;
        }
    }
}
