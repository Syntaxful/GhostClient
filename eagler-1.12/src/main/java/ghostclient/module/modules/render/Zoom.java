package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Zoom in with the camera.
 */
public class Zoom extends Module {

    private final NumberValue amount = new NumberValue("Amount", "Zoom amount", 4.0, 1.0, 10.0, 0.5);
    private float savedFov;

    public Zoom() {
        super(Category.Render, "Zoom", "Hold key to zoom.");
        addSetting(amount);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.gameSettings == null) return;
        mc.gameSettings.fovSetting = savedFov / amount.getFloat();
    }

    @Override
    public void onEnable() {
        if (mc.gameSettings == null) return;
        savedFov = mc.gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        if (mc.gameSettings == null) return;
        mc.gameSettings.fovSetting = savedFov;
    }
}
