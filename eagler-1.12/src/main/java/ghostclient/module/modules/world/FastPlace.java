package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes the right-click placement delay.
 */
public class FastPlace extends Module {

    public FastPlace() {
        super(Category.World, "FastPlace", "Place blocks faster.");
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.gameSettings != null) mc.setRightClickDelayTimer(0);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.setRightClickDelayTimer(0);
        }
    }
}
