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
        if (mc.player == null) return;
        try {
            java.lang.reflect.Field f = mc.playerController.getClass().getDeclaredField("blockHitDelay");
            f.setAccessible(true);
            f.setInt(mc.playerController, 0);

            java.lang.reflect.Field curBlock = mc.playerController.getClass().getDeclaredField("curBlockDamageMP");
            curBlock.setAccessible(true);
            float cur = curBlock.getFloat(mc.playerController);
            curBlock.setFloat(mc.playerController, Math.min(1.0f, cur + multiplier.getFloat() * 0.05f));
        } catch (Exception ignored) {
            // Reflection may not work in Eagler; rely on a patch reading getMultiplier().
        }
    }

    public float getMultiplier() {
        return isEnabled() ? multiplier.getFloat() : 1.0f;
    }
}
