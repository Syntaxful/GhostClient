package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;

/**
 * Boosts jump distance.
 */
public class LongJump extends Module {

    private final NumberValue boost = new NumberValue("Boost", "Horizontal jump boost", 1.5, 1.0, 4.0, 0.1);
    private final NumberValue height = new NumberValue("Height", "Jump height", 0.42, 0.2, 0.8, 0.01);
    private final BooleanValue autoDisable = new BooleanValue("AutoDisable", "Disable after landing", true);
    private int jumpTicks = 0;

    public LongJump() {
        super(Category.Movement, "LongJump", "Jump much farther.");
        addSetting(boost);
        addSetting(height);
        addSetting(autoDisable);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = height.getValue();
            double b = boost.getValue();
            mc.player.motionX *= b;
            mc.player.motionZ *= b;
            jumpTicks = 10;
        } else if (mc.player.onGround && jumpTicks > 0) {
            if (autoDisable.getValue()) {
                setEnabled(false);
            }
            jumpTicks = 0;
        } else if (jumpTicks > 0) {
            jumpTicks--;
            double airBoost = 1.0 + (boost.getValue() - 1.0) * 0.05;
            mc.player.motionX *= airBoost;
            mc.player.motionZ *= airBoost;
        }
    }
}
