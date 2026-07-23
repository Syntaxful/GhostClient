package ghostclient.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import ghostclient.GhostClient;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import ghostclient.setting.NumberValue;
import ghostclient.setting.Value;
import ghostclient.util.RenderUtils;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * GhostClient ClickGUI screen.
 *
 * Black-and-white, space-themed module manager with:
 * - category tabs, search, expandable settings
 * - visual keybind selector keyboard
 * - remembers last selected category
 */
public class ClickGUI extends GuiScreen {

    private static Category selectedCategory = Category.Combat;
    private String searchQuery = "";
    private Module expandedModule = null;
    private Module bindingModule = null;
    private boolean draggingSlider = false;
    private Value<?> draggingValue = null;

    private int panelX, panelY, panelW, panelH;
    private int headerH = 32;
    private int tabH = 28;
    private int moduleRowH = 24;
    private int settingH = 22;

    private final int COL_BG = 0xFF000000;
    private final int COL_PANEL = 0xFF0E0E0E;
    private final int COL_PANEL_LIGHT = 0xFF181818;
    private final int COL_BORDER = 0xFF2A2A2A;
    private final int COL_WHITE = 0xFFFFFFFF;
    private final int COL_GRAY = 0xFFAAAAAA;
    private final int COL_DARK_GRAY = 0xFF666666;
    private final int COL_ACTIVE = 0xFFFFFFFF;
    private final int COL_INACTIVE = 0xFF555555;

    private static final String[][] KEYBOARD_ROWS = {
        { "ESC", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" },
        { "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "BACK" },
        { "TAB", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" },
        { "CAPS", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "ENTER" },
        { "SHIFT", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "SHIFT" },
        { "CTRL", "META", "ALT", "SPACE", "ALT", "FN", "CTRL" }
    };

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        int sw = sr.getScaledWidth();
        int sh = sr.getScaledHeight();
        panelW = Math.min(900, sw - 40);
        panelH = Math.min(620, sh - 60);
        panelX = (sw - panelW) / 2;
        panelY = (sh - panelH) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // In-game GUI: no full-screen background, only the panel draws its own dark surface
        // (see panel background drawn below)

        drawPanel(mouseX, mouseY, partialTicks);

        if (bindingModule != null) {
            drawKeybindSelector(mouseX, mouseY, partialTicks);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawPanel(int mouseX, int mouseY, float partialTicks) {
        // Panel background with subtle border
        RenderUtils.drawRect(panelX, panelY, panelW, panelH, COL_PANEL);
        RenderUtils.drawBorderedRect(panelX + 1, panelY + 1, panelW - 2, panelH - 2, COL_PANEL, COL_BORDER, 1);

        // Header
        RenderUtils.drawRect(panelX, panelY, panelW, headerH, COL_PANEL_LIGHT);
        drawString(fontRendererObj, "GhostClient", panelX + 12, panelY + 10, COL_WHITE);
        drawString(fontRendererObj, "made by Syntaxful", panelX + panelW - 110, panelY + 10, COL_GRAY);

        // Search bar
        int searchW = 140;
        int searchX = panelX + panelW - searchW - 16;
        int searchY = panelY + 5;
        RenderUtils.drawRect(searchX, searchY, searchW, 18, COL_BG);
        RenderUtils.drawBorderedRect(searchX + 1, searchY + 1, searchW - 2, 16, COL_BG, COL_BORDER, 1);
        String searchText = searchQuery.isEmpty() ? "Search..." : searchQuery;
        int searchColor = searchQuery.isEmpty() ? COL_DARK_GRAY : COL_WHITE;
        drawString(fontRendererObj, searchText, searchX + 6, searchY + 5, searchColor);

        // Category tabs
        int tabX = panelX + 12;
        int tabY = panelY + headerH;
        Category[] cats = Category.values();
        int tabW = (panelW - 24) / cats.length;
        for (int i = 0; i < cats.length; i++) {
            Category cat = cats[i];
            boolean active = cat == selectedCategory;
            int tx = tabX + i * tabW;
            int bg = active ? COL_WHITE : COL_PANEL_LIGHT;
            int text = active ? COL_BG : COL_WHITE;
            RenderUtils.drawRect(tx, tabY, tabW - 2, tabH, bg);
            if (active) {
                RenderUtils.drawRect(tx, tabY + tabH - 2, tabW - 2, 2, COL_WHITE);
            }
            String name = cat.getDisplayName();
            int tw = fontRendererObj.getStringWidth(name);
            drawCenteredString(fontRendererObj, name, tx + (tabW - 2) / 2, tabY + 7, text);
        }

        // Module list
        int listX = panelX + 12;
        int listY = panelY + headerH + tabH + 8;
        int listW = (panelW - 32) / 2;
        int listH = panelH - (headerH + tabH + 20);
        RenderUtils.drawRect(listX, listY, listW, listH, COL_BG);
        RenderUtils.drawBorderedRect(listX + 1, listY + 1, listW - 2, listH - 2, COL_BG, COL_BORDER, 1);

        List<Module> modules = getVisibleModules();
        int my = listY + 6;
        for (Module module : modules) {
            boolean expanded = module == expandedModule;
            boolean enabled = module.isEnabled();
            int rowColor = expanded ? COL_PANEL_LIGHT : (enabled ? COL_PANEL_LIGHT : COL_BG);
            RenderUtils.drawRect(listX + 4, my, listW - 8, moduleRowH, rowColor);
            if (expanded) {
                RenderUtils.drawRect(listX + 4, my, 3, moduleRowH, COL_WHITE);
            }
            int textColor = enabled ? COL_WHITE : COL_GRAY;
            drawString(fontRendererObj, module.getName(), listX + 14, my + 7, textColor);

            // Toggle pill
            int toggleX = listX + listW - 36;
            int toggleY = my + 5;
            int toggleW = 26;
            int toggleH = 12;
            int toggleBg = enabled ? COL_WHITE : COL_INACTIVE;
            RenderUtils.drawRect(toggleX, toggleY, toggleW, toggleH, toggleBg);
            int knobX = enabled ? toggleX + toggleW - 11 : toggleX + 2;
            RenderUtils.drawRect(knobX, toggleY + 1, 9, toggleH - 2, COL_BG);

            my += moduleRowH + 1;
        }

        // Settings panel (right side)
        int setX = listX + listW + 8;
        int setY = listY;
        int setW = listW;
        int setH = listH;
        RenderUtils.drawRect(setX, setY, setW, setH, COL_BG);
        RenderUtils.drawBorderedRect(setX + 1, setY + 1, setW - 2, setH - 2, COL_BG, COL_BORDER, 1);

        if (expandedModule != null) {
            drawModuleSettings(expandedModule, setX, setY, setW, mouseX, mouseY);
        } else {
            String hint = "Select a module to edit settings";
            int hw = fontRendererObj.getStringWidth(hint);
            drawCenteredString(fontRendererObj, hint, setX + setW / 2, setY + setH / 2 - 4, COL_DARK_GRAY);
        }
    }

    private void drawModuleSettings(Module module, int x, int y, int w, int mouseX, int mouseY) {
        int titleH = 22;
        RenderUtils.drawRect(x, y, w, titleH, COL_PANEL_LIGHT);
        drawString(fontRendererObj, module.getName(), x + 10, y + 6, COL_WHITE);
        String desc = module.getDescription();
        if (desc != null && !desc.isEmpty()) {
            String clipped = fontRendererObj.trimStringToWidth(desc, w - 20);
            drawString(fontRendererObj, clipped, x + 10, y + 20, COL_GRAY);
        }

        int sy = y + 38;
        List<Value<?>> settings = module.getSettings();
        for (Value<?> value : settings) {
            drawSetting(value, x + 8, sy, w - 16, mouseX, mouseY);
            sy += settingH + 4;
        }

        // Keybind row
        drawKeybindRow(module, x + 8, sy, w - 16, mouseX, mouseY);
    }

    private void drawSetting(Value<?> value, int x, int y, int w, int mouseX, int mouseY) {
        drawString(fontRendererObj, value.getName(), x, y + 5, COL_GRAY);
        int cx = x + w - 90;
        int cw = 90;

        if (value instanceof BooleanValue) {
            BooleanValue bv = (BooleanValue) value;
            boolean v = bv.getValue();
            int bx = x + w - 34;
            int bw = 26;
            int bh = 12;
            int bg = v ? COL_WHITE : COL_INACTIVE;
            RenderUtils.drawRect(bx, y + 3, bw, bh, bg);
            int kx = v ? bx + bw - 11 : bx + 2;
            RenderUtils.drawRect(kx, y + 4, 9, bh - 2, COL_BG);
        } else if (value instanceof NumberValue) {
            NumberValue nv = (NumberValue) value;
            double pct = (nv.getValue() - nv.getMin()) / (nv.getMax() - nv.getMin());
            int sw = 70;
            int sx = x + w - sw - 4;
            RenderUtils.drawRect(sx, y + 8, sw, 4, COL_INACTIVE);
            RenderUtils.drawRect(sx, y + 8, (int) (sw * pct), 4, COL_WHITE);
            RenderUtils.drawRect(sx + (int) (sw * pct) - 2, y + 6, 4, 8, COL_WHITE);
            String num = formatNumber(nv.getValue(), nv.getStep());
            drawString(fontRendererObj, num, sx + sw + 4, y + 5, COL_WHITE);
        } else if (value instanceof ModeValue) {
            ModeValue mv = (ModeValue) value;
            String mode = mv.getValue();
            int mw = fontRendererObj.getStringWidth(mode) + 12;
            int mx = x + w - mw - 4;
            RenderUtils.drawRect(mx, y + 2, mw, 16, COL_PANEL_LIGHT);
            RenderUtils.drawBorderedRect(mx + 1, y + 3, mw - 2, 14, COL_PANEL_LIGHT, COL_BORDER, 1);
            drawCenteredString(fontRendererObj, mode, mx + mw / 2, y + 6, COL_WHITE);
        }
    }

    private void drawKeybindRow(Module module, int x, int y, int w, int mouseX, int mouseY) {
        drawString(fontRendererObj, "Keybind", x, y + 5, COL_GRAY);
        String label = module.getKeybind() == 0 ? "NONE" : Keyboard.getKeyName(module.getKeybind());
        int kw = Math.max(60, fontRendererObj.getStringWidth(label) + 16);
        int kx = x + w - kw - 4;
        int bg = bindingModule == module ? COL_WHITE : COL_PANEL_LIGHT;
        int fg = bindingModule == module ? COL_BG : COL_WHITE;
        RenderUtils.drawRect(kx, y + 2, kw, 16, bg);
        drawCenteredString(fontRendererObj, label, kx + kw / 2, y + 6, fg);
    }

    private void drawKeybindSelector(int mouseX, int mouseY, float partialTicks) {
        // Dim overlay
        RenderUtils.drawRect(0, 0, this.width, this.height, 0xCC000000);

        int kbW = 560;
        int kbH = 260;
        int kbX = (this.width - kbW) / 2;
        int kbY = (this.height - kbH) / 2;
        RenderUtils.drawRect(kbX, kbY, kbW, kbH, COL_PANEL);
        RenderUtils.drawBorderedRect(kbX + 1, kbY + 1, kbW - 2, kbH - 2, COL_PANEL, COL_BORDER, 1);

        drawCenteredString(fontRendererObj, "Press a key or click one below", kbX + kbW / 2, kbY + 10, COL_WHITE);
        drawCenteredString(fontRendererObj, "ESC = unbind, BACKSPACE = none", kbX + kbW / 2, kbY + 22, COL_GRAY);

        int keyH = 22;
        int keyGap = 4;
        int startY = kbY + 46;
        for (int row = 0; row < KEYBOARD_ROWS.length; row++) {
            String[] keys = KEYBOARD_ROWS[row];
            int totalGap = (keys.length - 1) * keyGap;
            int keyW = (kbW - 32 - totalGap) / keys.length;
            int kx = kbX + 16;
            for (String key : keys) {
                int actualW = keyW;
                if (key.equals("SPACE")) actualW = keyW * 4 + keyGap * 3;
                if (key.equals("BACK") || key.equals("ENTER") || key.equals("SHIFT")) actualW = keyW + 14;
                boolean hover = mouseX >= kx && mouseX <= kx + actualW && mouseY >= startY && mouseY <= startY + keyH;
                int bg = hover ? COL_WHITE : COL_PANEL_LIGHT;
                int fg = hover ? COL_BG : COL_WHITE;
                RenderUtils.drawRect(kx, startY, actualW, keyH, bg);
                drawCenteredString(fontRendererObj, key, kx + actualW / 2, startY + 6, fg);
                kx += actualW + keyGap;
            }
            startY += keyH + keyGap;
        }
    }

    private List<Module> getVisibleModules() {
        List<Module> all = GhostClient.MODULES.getByCategory(selectedCategory);
        if (searchQuery.isEmpty()) return all;
        List<Module> filtered = new ArrayList<>();
        String q = searchQuery.toLowerCase();
        for (Module m : GhostClient.MODULES.getAll()) {
            if (m.getName().toLowerCase().contains(q)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    private String formatNumber(double value, double step) {
        if (step >= 1.0) return String.valueOf((int) value);
        return String.format("%.1f", value);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (bindingModule != null) {
            handleKeyboardClick(mouseX, mouseY);
            return;
        }

        int searchW = 140;
        int searchX = panelX + panelW - searchW - 16;
        int searchY = panelY + 5;
        if (mouseX >= searchX && mouseX <= searchX + searchW && mouseY >= searchY && mouseY <= searchY + 18) {
            searchQuery = "";
            return;
        }

        // Category tabs
        int tabX = panelX + 12;
        int tabY = panelY + headerH;
        Category[] cats = Category.values();
        int tabW = (panelW - 24) / cats.length;
        for (int i = 0; i < cats.length; i++) {
            int tx = tabX + i * tabW;
            if (mouseX >= tx && mouseX <= tx + tabW - 2 && mouseY >= tabY && mouseY <= tabY + tabH) {
                selectedCategory = cats[i];
                expandedModule = null;
                return;
            }
        }

        // Module list
        int listX = panelX + 12;
        int listY = panelY + headerH + tabH + 8;
        int listW = (panelW - 32) / 2;
        int listH = panelH - (headerH + tabH + 20);
        List<Module> modules = getVisibleModules();
        int my = listY + 6;
        for (Module module : modules) {
            if (mouseX >= listX + 4 && mouseX <= listX + listW - 4 && mouseY >= my && mouseY <= my + moduleRowH) {
                int toggleX = listX + listW - 36;
                if (mouseX >= toggleX && mouseX <= toggleX + 26) {
                    module.toggle();
                } else {
                    expandedModule = (expandedModule == module) ? null : module;
                }
                return;
            }
            my += moduleRowH + 1;
        }

        // Settings panel clicks
        if (expandedModule != null) {
            int setX = listX + listW + 8;
            int setY = listY;
            int setW = listW;
            handleSettingClick(expandedModule, setX, setY, setW, mouseX, mouseY);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void handleSettingClick(Module module, int setX, int setY, int setW, int mouseX, int mouseY) {
        int sy = setY + 38;
        List<Value<?>> settings = module.getSettings();
        for (Value<?> value : settings) {
            int x = setX + 8;
            int w = setW - 16;
            if (value instanceof BooleanValue) {
                int bx = x + w - 34;
                if (mouseX >= bx && mouseX <= bx + 26 && mouseY >= sy + 3 && mouseY <= sy + 15) {
                    ((BooleanValue) value).toggle();
                    return;
                }
            } else if (value instanceof NumberValue) {
                NumberValue nv = (NumberValue) value;
                int sw = 70;
                int sx = x + w - sw - 4;
                if (mouseX >= sx && mouseX <= sx + sw && mouseY >= sy && mouseY <= sy + 16) {
                    double pct = (double) (mouseX - sx) / sw;
                    double newVal = nv.getMin() + pct * (nv.getMax() - nv.getMin());
                    nv.setValue(newVal);
                    draggingSlider = true;
                    draggingValue = nv;
                    return;
                }
            } else if (value instanceof ModeValue) {
                ModeValue mv = (ModeValue) value;
                String mode = mv.getValue();
                int mw = fontRendererObj.getStringWidth(mode) + 12;
                int mx = x + w - mw - 4;
                if (mouseX >= mx && mouseX <= mx + mw && mouseY >= sy + 2 && mouseY <= sy + 18) {
                    mv.cycle();
                    return;
                }
            }
            sy += settingH + 4;
        }

        // Keybind row
        int x = setX + 8;
        int w = setW - 16;
        String label = module.getKeybind() == 0 ? "NONE" : Keyboard.getKeyName(module.getKeybind());
        int kw = Math.max(60, fontRendererObj.getStringWidth(label) + 16);
        int kx = x + w - kw - 4;
        if (mouseX >= kx && mouseX <= kx + kw && mouseY >= sy + 2 && mouseY <= sy + 18) {
            bindingModule = module;
            return;
        }
    }

    private void handleKeyboardClick(int mouseX, int mouseY) {
        int kbW = 560;
        int kbH = 260;
        int kbX = (this.width - kbW) / 2;
        int kbY = (this.height - kbH) / 2;

        int keyH = 22;
        int keyGap = 4;
        int startY = kbY + 46;
        for (int row = 0; row < KEYBOARD_ROWS.length; row++) {
            String[] keys = KEYBOARD_ROWS[row];
            int totalGap = (keys.length - 1) * keyGap;
            int keyW = (kbW - 32 - totalGap) / keys.length;
            int kx = kbX + 16;
            for (String key : keys) {
                int actualW = keyW;
                if (key.equals("SPACE")) actualW = keyW * 4 + keyGap * 3;
                if (key.equals("BACK") || key.equals("ENTER") || key.equals("SHIFT")) actualW = keyW + 14;
                if (mouseX >= kx && mouseX <= kx + actualW && mouseY >= startY && mouseY <= startY + keyH) {
                    int code = keyCodeFromLabel(key);
                    if (bindingModule != null) {
                        bindingModule.setKeybind(code);
                    }
                    bindingModule = null;
                    return;
                }
                kx += actualW + keyGap;
            }
            startY += keyH + keyGap;
        }
    }

    private int keyCodeFromLabel(String label) {
        switch (label) {
            case "ESC": return Keyboard.KEY_ESCAPE;
            case "BACK": return Keyboard.KEY_BACK;
            case "TAB": return Keyboard.KEY_TAB;
            case "ENTER": return Keyboard.KEY_RETURN;
            case "CAPS": return Keyboard.KEY_CAPITAL;
            case "SHIFT": return Keyboard.KEY_LSHIFT;
            case "CTRL": return Keyboard.KEY_LCONTROL;
            case "META": return Keyboard.KEY_LMETA;
            case "ALT": return Keyboard.KEY_LMENU;
            case "SPACE": return Keyboard.KEY_SPACE;
            case "FN": return 0;
            default: {
                if (label.startsWith("F")) {
                    try {
                        int n = Integer.parseInt(label.substring(1));
                        return Keyboard.KEY_F1 + n - 1;
                    } catch (Exception ignored) {}
                }
                char c = label.charAt(0);
                return Keyboard.getKeyIndex(String.valueOf(c));
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingSlider && draggingValue instanceof NumberValue) {
            NumberValue nv = (NumberValue) draggingValue;
            int x = panelX + 12 + (panelW - 32) / 2 + 8 + 8;
            int w = (panelW - 32) / 2 - 16;
            int sx = x + w - 70 - 4;
            double pct = (double) (mouseX - sx) / 70.0;
            pct = Math.max(0.0, Math.min(1.0, pct));
            double newVal = nv.getMin() + pct * (nv.getMax() - nv.getMin());
            nv.setValue(newVal);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingSlider = false;
        draggingValue = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (bindingModule != null) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                bindingModule.setKeybind(0);
            } else if (keyCode == Keyboard.KEY_BACK) {
                bindingModule.setKeybind(0);
            } else {
                bindingModule.setKeybind(keyCode);
            }
            bindingModule = null;
            return;
        }
        if (keyCode == Keyboard.KEY_RSHIFT || keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
