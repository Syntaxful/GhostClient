package ghostclient.module.modules.render;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import ghostclient.util.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Renders the active module list (ArrayList).
 */
public class HUD extends Module {

    public final BooleanValue rightSide = new BooleanValue("RightSide", "Align list on the right side.", true);
    public final BooleanValue sortLength = new BooleanValue("SortLength", "Sort modules by name length.", true);
    public final BooleanValue showKeybinds = new BooleanValue("ShowKeybinds", "Show keybinds next to names", true);
    public final BooleanValue categoryColors = new BooleanValue("CategoryColors", "Color modules by category", true);
    public final BooleanValue background = new BooleanValue("Background", "Draw a dark background behind the list", true);
    public final ModeValue mode = new ModeValue("Mode", "ArrayList style", "Default", "Default", "Compact", "Clean");

    public HUD() {
        super(Category.Render, "ArrayList", "Show the active module list.");
        addSetting(rightSide);
        addSetting(sortLength);
        addSetting(showKeybinds);
        addSetting(categoryColors);
        addSetting(background);
        addSetting(mode);
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
        int maxW = 0;
        for (Module module : modules) {
            String text = getDisplayText(module);
            int w = mc.fontRendererObj.getStringWidth(text);
            if (w > maxW) maxW = w;
        }
        if (background.getValue() && !modules.isEmpty()) {
            int bgX = rightSide.getValue() ? x - maxW - 4 : x - 2;
            int bgY = 2;
            int bgW = maxW + 6;
            int bgH = modules.size() * (mc.fontRendererObj.FONT_HEIGHT + 2) + 2;
            RenderUtils.drawRect(bgX, bgY, bgW, bgH, 0x55000000);
        }
        for (Module module : modules) {
            String text = getDisplayText(module);
            int w = mc.fontRendererObj.getStringWidth(text);
            int color = getColor(module);
            mc.fontRendererObj.drawStringWithShadow(text, rightSide.getValue() ? x - w : x, y, color);
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }

    private String getDisplayText(Module module) {
        String name = module.getName();
        if (showKeybinds.getValue() && module.getKeybind() != 0) {
            name = name + " " + net.lax1dude.eaglercraft.Keyboard.getKeyName(module.getKeybind());
        }
        if ("Compact".equals(mode.getValue())) {
            return name.toLowerCase();
        }
        return name;
    }

    private int getColor(Module module) {
        if (!categoryColors.getValue()) return 0xFFFFFFFF;
        switch (module.getCategory()) {
            case Combat: return 0xFFFFAAAA;
            case Movement: return 0xFFAAFFAA;
            case Player: return 0xFFAAFFFF;
            case Render: return 0xFFFFAAFF;
            case World: return 0xFFFFFFAA;
            case Misc: return 0xFFAAAAFF;
            default: return 0xFFFFFFFF;
        }
    }
}
