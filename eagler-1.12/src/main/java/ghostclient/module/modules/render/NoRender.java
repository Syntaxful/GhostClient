package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;

/**
 * Disables selected render overlays.
 */
public class NoRender extends Module {

    public final BooleanValue fire = new BooleanValue("Fire", "Hide the fire overlay.", true);
    public final BooleanValue portal = new BooleanValue("Portal", "Hide the portal overlay.", true);
    public final BooleanValue pumpkin = new BooleanValue("Pumpkin", "Hide pumpkin overlay.", true);
    public final BooleanValue scoreboard = new BooleanValue("Scoreboard", "Hide the scoreboard.", false);
    public final BooleanValue bossBar = new BooleanValue("BossBar", "Hide boss bars.", false);
    public final BooleanValue titles = new BooleanValue("Titles", "Hide title/subtitle text.", false);

    public NoRender() {
        super(Category.Render, "NoRender", "Disable selected render overlays.");
        addSetting(fire);
        addSetting(portal);
        addSetting(pumpkin);
        addSetting(scoreboard);
        addSetting(bossBar);
        addSetting(titles);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (fire.getValue()) mc.player.extinguish();
        if (portal.getValue()) mc.player.timeInPortal = 0.0F;
        // Pumpkin overlay, scoreboard, boss bars, and titles are handled by the rendering patch.
    }
}
