package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Jump higher than normal. Triggers once per jump press.
 */
public class HighJump extends Module {

    private final NumberValue height = new NumberValue("Height", "Jump height multiplier", 2.0, 1.1, 3.0, 0.1);
    private boolean wasOnGround = true;
    private boolean jumpApplied = false;

    public HighJump() {
        super(Category.Movement, "HighJump", "Jump higher than normal.");
        addSetting(height);
    }

    @Override
    public void onEnable() {
        wasOnGround = true;
        jumpApplied = false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        boolean onGround = mc.player.onGround;
        boolean jumpHeld = mc.gameSettings.keyBindJump.isKeyDown();

        if (onGround && jumpHeld && wasOnGround && !jumpApplied) {
            mc.player.motionY = 0.42 * height.getValue();
            jumpApplied = true;
        }

        if (!jumpHeld) jumpApplied = false;
        wasOnGround = onGround;
    }
}
