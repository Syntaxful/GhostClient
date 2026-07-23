package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically jumps while hitting to keep combos going.
 */
public class AutoComboJump extends Module {

    private final NumberValue interval = new NumberValue("Interval", "Ticks between jumps", 12, 3, 40, 1);
    private final NumberValue chance = new NumberValue("Chance", "Jump chance %", 80, 0, 100, 5);
    private int ticks = 0;

    public AutoComboJump() {
        super(Category.Combat, "AutoComboJump", "Auto jump during combat combos.");
        addSetting(interval);
        addSetting(chance);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.gameSettings.keyBindAttack.isKeyDown() && mc.player.onGround) {
            ticks++;
            if (ticks >= interval.getInt()) {
                ticks = 0;
                if (Math.random() * 100 < chance.getValue()) {
                    mc.player.jump();
                }
            }
        } else {
            ticks = 0;
        }
    }
}
