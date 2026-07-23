package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically jumps once you've landed 2-3 hits in a combo.
 */
public class AutoComboJump extends Module {

    private final NumberValue hits   = new NumberValue("Hits", "Hits to trigger combo jump", 2, 2, 5, 1);
    private final NumberValue window = new NumberValue("Window", "Ticks between hits to count", 30, 5, 60, 1);
    private final NumberValue chance = new NumberValue("Chance", "Jump chance %", 100, 0, 100, 5);

    private int hitCount = 0;
    private int noHitTicks = 0;
    private float lastHealth = 0;

    public AutoComboJump() {
        super(Category.Combat, "AutoComboJump", "Auto jump once a combo starts.");
        addSetting(hits);
        addSetting(window);
        addSetting(chance);
    }

    @Override
    public void onEnable() {
        hitCount = 0;
        noHitTicks = 0;
        lastHealth = (mc.player != null) ? mc.player.getHealth() : 0;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        float health = mc.player.getHealth();

        // Count self-damage-taken as a hit (you are in combat)
        if (health < lastHealth) {
            hitCount++;
            noHitTicks = 0;
            if (hitCount >= hits.getInt() && mc.player.onGround && Math.random() * 100 < chance.getValue()) {
                mc.player.jump();
                hitCount = 0;
            }
        } else {
            noHitTicks++;
            if (noHitTicks > window.getInt()) {
                hitCount = 0;
            }
        }
        lastHealth = health;
    }
}
