package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Boosts jump distance.
 */
public class LongJump extends Module {

    private final NumberValue boost = new NumberValue("Boost", "Jump boost", 1.5, 1.0, 4.0, 0.1);
    private int jumpTicks = 0;

    public LongJump() {
        super(Category.Movement, "LongJump", "Jump much farther.");
        addSetting(boost);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0.42;
            double b = boost.getValue();
            mc.player.motionX *= b;
            mc.player.motionZ *= b;
            jumpTicks = 10;
        }
    }
}
