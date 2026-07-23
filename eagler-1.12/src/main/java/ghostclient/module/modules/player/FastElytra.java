package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Swaps chestplate with elytra on keypress.
 */
public class FastElytra extends Module {

    public FastElytra() {
        super(Category.Player, "FastElytra", "Swap chestplate with elytra.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.gameSettings.keyBindJump.isKeyDown()) return;
        ItemStack chest = mc.player.inventory.armorItemInSlot(2);
        if (chest.getItem() == Items.ELYTRA) return;
        for (int i = 0; i < 45; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.ELYTRA) {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                return;
            }
        }
    }
}
