package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Sol Client style time changer.
 */
public class TimeChanger extends Module {

    private final NumberValue time = new NumberValue("Time", "World time override", 6000, 0, 24000, 100);

    public TimeChanger() {
        super(Category.Render, "TimeChanger", "Change the client-side world time.");
        addSetting(time);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.world == null) return;
        mc.world.setWorldTime((long) time.getValue().doubleValue());
    }
}
