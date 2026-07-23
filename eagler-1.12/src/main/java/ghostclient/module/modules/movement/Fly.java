package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Creative-style flight in survival.
 */
public class Fly extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Flight speed", 0.5, 0.1, 2.0, 0.1);

    public Fly() {
        super(Category.Movement, "Fly", "Fly in survival mode.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed(speed.getFloat());
        mc.player.motionY = 0;
        if (mc.gameSettings.keyBindJump.isKeyDown()) mc.player.motionY = speed.getValue();
        if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.motionY = -speed.getValue();
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.capabilities.isFlying = false;
    }
}
