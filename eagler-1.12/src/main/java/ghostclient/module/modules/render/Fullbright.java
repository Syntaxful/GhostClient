package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Maximizes gamma for full brightness.
 */
public class Fullbright extends Module {

    private float savedGamma;

    public Fullbright() {
        super(Category.Render, "Fullbright", "See everything at full brightness.");
    }

    @Override
    public void onEnable() {
        savedGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100.0F;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = savedGamma;
    }
}
