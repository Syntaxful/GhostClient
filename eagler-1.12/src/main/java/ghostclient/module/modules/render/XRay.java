package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Reduces opacity of non-ore blocks to reveal ores.
 */
public class XRay extends Module {

    public XRay() {
        super(Category.Render, "XRay", "See ores through the world.");
    }

    @Override
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
    }
}
