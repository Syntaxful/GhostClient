package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.TickEvent;
import ghostclient.gui.ClickGUI;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.lax1dude.eaglercraft.KeyboardConstants;

/**
 * Opens the GhostClient ClickGUI.
 */
public class ClickGUIModule extends Module {

    private boolean openClickGuiNextTick = false;

    public ClickGUIModule() {
        super(Category.Misc, "ClickGUI", "Opens the module GUI.");
        setKeybind(KeyboardConstants.KEY_RSHIFT);
        setEnabled(true); // always active so the keybind works
        setHidden(true); // do not show in the ArrayList HUD
    }

    @EventHandler
    public void onKey(KeyInputEvent event) {
        if (event.getKey() == getKeybind() && event.isPressed() && mc.currentScreen == null) {
            // Defer opening to the next tick so the same key event that opens the GUI
            // does not immediately close it via ClickGUI.keyTyped(RSHIFT).
            openClickGuiNextTick = true;
        }
    }

    @Override
    public void onTick(TickEvent.Post event) {
        if (openClickGuiNextTick) {
            openClickGuiNextTick = false;
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new ClickGUI());
            }
        }
    }

    @Override
    public void onEnable() {
        setEnabled(true);
    }

    @Override
    public void onDisable() {
        setEnabled(true);
    }
}
