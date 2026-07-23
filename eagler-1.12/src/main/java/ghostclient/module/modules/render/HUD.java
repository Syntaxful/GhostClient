package ghostclient.module.modules.render;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Renders the active module list (ArrayList).
 */
public class HUD extends Module {

    public final BooleanValue rightSide = new BooleanValue("RightSide", "Align list on the right side.", true);
    public final BooleanValue sortLength = new BooleanValue("SortLength", "Sort modules by name length.", true);

    public HUD() {
        super(Category.Render, "ArrayList", "Show the active module list.");
        addSetting(rightSide);
        addSetting(sortLength);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null) return;
        ScaledResolution sr = new ScaledResolution(mc);
        int y = 4;
        int x = rightSide.getValue() ? sr.getScaledWidth() - 4 : 4;
        java.util.List<Module> modules = new java.util.ArrayList<>();
        for (Module module : GhostClient.MODULES.getEnabled()) {
            if (!module.isHidden()) {
                modules.add(module);
            }
        }
        if (sortLength.getValue()) {
            modules.sort((a, b) -> Integer.compare(b.getName().length(), a.getName().length()));
        }
        for (Module module : modules) {
            String text = module.getName();
            int w = mc.fontRendererObj.getStringWidth(text);
            mc.fontRendererObj.drawStringWithShadow(text, rightSide.getValue() ? x - w : x, y, 0xFFFFFFFF);
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }
}
