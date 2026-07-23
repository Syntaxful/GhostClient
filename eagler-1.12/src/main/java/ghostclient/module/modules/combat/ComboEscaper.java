package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Escapes a combo by jumping and strafing when taking rapid hits.
 */
public class ComboEscaper extends Module {

    private final NumberValue hits = new NumberValue("Hits", "Hits in window to trigger escape", 2, 2, 5, 1);
    private final NumberValue window = new NumberValue("Window", "Ticks between hits to count", 30, 5, 60, 1);
    private final NumberValue duration = new NumberValue("Duration", "Escape ticks", 10, 5, 30, 1);

    private int lastHealthTicks = 0;
    private float lastHealth = 0;
    private int comboCount = 0;
    private int escapeTicks = 0;
    private int noHitTicks = 0;

    public ComboEscaper() {
        super(Category.Combat, "ComboEscaper", "Escape combos by jumping/strafing.");
        addSetting(hits);
        addSetting(window);
        addSetting(duration);
    }

    @Override
    public void onEnable() {
        lastHealth = (mc.player != null) ? mc.player.getHealth() : 0;
        comboCount = 0;
        escapeTicks = 0;
        noHitTicks = 0;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        if (escapeTicks > 0) {
            escapeTicks--;
            mc.player.setSprinting(true);
            if (mc.player.onGround) mc.player.jump();
            int dir = (escapeTicks % 16 < 8) ? 1 : -1;
            mc.player.moveStrafing = 0.6f * dir;
            return;
        }

        // Reset strafing when not escaping
        mc.player.moveStrafing = 0;

        float health = mc.player.getHealth();
        if (health < lastHealth) {
            comboCount++;
            noHitTicks = 0;
            if (comboCount >= hits.getInt()) {
                escapeTicks = duration.getInt();
                comboCount = 0;
            }
        } else {
            noHitTicks++;
            if (noHitTicks > window.getInt()) {
                comboCount = 0;
            }
        }
        lastHealth = health;
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.moveStrafing = 0;
    }
}
