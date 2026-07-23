package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically jumps/escapes when low on health or on fire to break combos.
 */
public class ComboEscaper extends Module {

    private final NumberValue health = new NumberValue("Health", "Escape when health <= this", 8.0, 1.0, 20.0, 0.5);
    private final NumberValue cooldown = new NumberValue("Cooldown", "Ticks between escapes", 20, 5, 100, 5);
    private int ticks = 0;

    public ComboEscaper() {
        super(Category.Combat, "ComboEscaper", "Jump to escape combos when low.");
        addSetting(health);
        addSetting(cooldown);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (ticks > 0) {
            ticks--;
            return;
        }
        boolean low = mc.player.getHealth() <= health.getValue();
        boolean onFire = mc.player.isBurning();
        if (low || onFire) {
            mc.player.jump();
            if (mc.player.moveStrafing != 0) {
                mc.player.motionX *= 1.1;
                mc.player.motionZ *= 1.1;
            }
            ticks = cooldown.getInt();
        }
    }
}
