package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Mines blocks faster by boosting breaking progress.
 */
public class SpeedMine extends Module {

    private final NumberValue multiplier = new NumberValue("Multiplier", "Mining speed multiplier", 2.0, 1.0, 5.0, 0.1);

    public SpeedMine() {
        super(Category.Player, "SpeedMine", "Mine blocks faster.");
        addSetting(multiplier);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.playerController == null) return;
        mc.playerController.setBlockHitDelay(0);
        float cur = mc.playerController.getCurBlockDamageMP();
        mc.playerController.setCurBlockDamageMP(Math.min(1.0f, cur + multiplier.getFloat() * 0.05f));
    }

    public float getMultiplier() {
        return isEnabled() ? multiplier.getFloat() : 1.0f;
    }
}
