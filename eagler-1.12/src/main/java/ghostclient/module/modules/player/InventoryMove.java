package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;

/**
 * Move while inventory is open.
 */
public class InventoryMove extends Module {

    public InventoryMove() {
        super(Category.Player, "InventoryMove", "Walk while inventory is open.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.currentScreen instanceof GuiContainer) {
            mc.player.movementInput.updatePlayerMoveState();
        }
    }
}
