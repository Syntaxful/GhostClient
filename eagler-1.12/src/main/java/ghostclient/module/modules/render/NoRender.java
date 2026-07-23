package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;

public class NoRender extends Module {

    public final BooleanValue fire = new BooleanValue("Fire", "Hide the fire overlay.", true);
    public final BooleanValue portal = new BooleanValue("Portal", "Hide the portal overlay.", true);
    public final BooleanValue pumpkin = new BooleanValue("Pumpkin", "Hide pumpkin overlay.", true);

    public NoRender() {
        super(Category.Render, "NoRender", "Disable selected render overlays.");
        addSetting(fire);
        addSetting(portal);
        addSetting(pumpkin);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (fire.getValue()) mc.player.extinguish();
        if (portal.getValue()) mc.player.timeInPortal = 0.0F;
        // Pumpkin overlay is handled by GuiIngame rendering; setting kept for registration/UI.
    }
}