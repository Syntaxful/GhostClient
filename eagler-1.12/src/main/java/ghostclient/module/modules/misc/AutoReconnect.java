package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Reconnects to the last server after disconnect.
 */
public class AutoReconnect extends Module {

    private final NumberValue delay = new NumberValue("Delay", "Reconnect delay seconds", 5, 1, 30, 1);
    private int ticks = 0;

    public AutoReconnect() {
        super(Category.Misc, "AutoReconnect", "Auto reconnect to server.");
        addSetting(delay);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.getCurrentServerData() == null) return;
        if (mc.world == null && mc.currentScreen instanceof net.minecraft.client.gui.GuiMainMenu) {
            ticks++;
            if (ticks >= delay.getInt() * 20) {
                ticks = 0;
                mc.displayGuiScreen(new net.minecraft.client.multiplayer.GuiConnecting(new net.minecraft.client.gui.GuiMainMenu(), mc, mc.getCurrentServerData().serverIP, 0 /* serverPort missing in 1.8/1.12 ServerData? */));
            }
        }
    }
}
