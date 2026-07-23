package ghostclient.gui;

import java.io.IOException;
import java.util.List;

import ghostclient.GhostClient;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * GhostClient ClickGUI screen.
 */
public class ClickGUI extends GuiScreen {

    private Category selectedCategory = Category.Combat;
    private int categoryX = 10;
    private int categoryY = 40;
    private int categoryWidth = 90;
    private int categoryHeight = 20;
    private int moduleX = 120;
    private int moduleY = 40;
    private int moduleWidth = 160;
    private int moduleHeight = 20;

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Custom GhostClient background for the ClickGUI
        drawGhostClientBackground();

        ScaledResolution sr = new ScaledResolution(mc);
        int w = sr.getScaledWidth();
        int h = sr.getScaledHeight();

        // Header bar (black & white only)
        RenderUtils.drawRect(0, 0, w, 32, 0xFF000000);
        RenderUtils.drawRect(0, 31, w, 1, 0xFFFFFFFF);
        drawString(fontRendererObj, "GhostClient", 10, 10, 0xFFFFFFFF);
        drawString(fontRendererObj, "made by Syntaxful", w - fontRendererObj.getStringWidth("made by Syntaxful") - 10, 10, 0xFFAAAAAA);

        // Categories
        int y = categoryY;
        for (Category cat : Category.values()) {
            boolean active = cat == selectedCategory;
            int bg = active ? 0xFFFFFFFF : 0xFF111111;
            int text = active ? 0xFF000000 : 0xFFFFFFFF;
            RenderUtils.drawRect(categoryX, y, categoryWidth, categoryHeight, bg);
            drawCenteredString(fontRendererObj, cat.getDisplayName(), categoryX + categoryWidth / 2, y + 6, text);
            y += categoryHeight + 4;
        }

        // Modules
        List<Module> modules = GhostClient.MODULES.getByCategory(selectedCategory);
        int mx = moduleX;
        int my = moduleY;
        for (Module module : modules) {
            int bg = module.isEnabled() ? 0xFFFFFFFF : 0xFF111111;
            int text = module.isEnabled() ? 0xFF000000 : 0xFFFFFFFF;
            RenderUtils.drawRect(mx, my, moduleWidth, moduleHeight, bg);
            drawString(fontRendererObj, module.getName(), mx + 6, my + 6, text);
            my += moduleHeight + 4;
        }

        // Tooltip
        String hovered = getHovered(mouseX, mouseY);
        if (hovered != null) {
            drawString(fontRendererObj, hovered, mouseX + 10, mouseY + 10, 0xFFFFFFFF);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // Category click
        int y = categoryY;
        for (Category cat : Category.values()) {
            if (mouseX >= categoryX && mouseX <= categoryX + categoryWidth && mouseY >= y && mouseY <= y + categoryHeight) {
                selectedCategory = cat;
                return;
            }
            y += categoryHeight + 4;
        }

        // Module click
        List<Module> modules = GhostClient.MODULES.getByCategory(selectedCategory);
        int my = moduleY;
        for (Module module : modules) {
            if (mouseX >= moduleX && mouseX <= moduleX + moduleWidth && mouseY >= my && mouseY <= my + moduleHeight) {
                module.toggle();
                return;
            }
            my += moduleHeight + 4;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 54) { // RSHIFT closes too
            mc.displayGuiScreen(null);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private String getHovered(int mouseX, int mouseY) {
        int y = categoryY;
        for (Category cat : Category.values()) {
            if (mouseX >= categoryX && mouseX <= categoryX + categoryWidth && mouseY >= y && mouseY <= y + categoryHeight) {
                return cat.getDisplayName();
            }
            y += categoryHeight + 4;
        }
        List<Module> modules = GhostClient.MODULES.getByCategory(selectedCategory);
        int my = moduleY;
        for (Module module : modules) {
            if (mouseX >= moduleX && mouseX <= moduleX + moduleWidth && mouseY >= my && mouseY <= my + moduleHeight) {
                return module.getDescription();
            }
            my += moduleHeight + 4;
        }
        return null;
    }
}
