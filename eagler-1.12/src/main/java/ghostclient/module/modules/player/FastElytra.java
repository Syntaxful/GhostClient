package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.ItemUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

/**
 * Swaps chestplate with elytra when enabled/toggled.
 */
public class FastElytra extends Module {

    private boolean swappedOnEnable = false;

    public FastElytra() {
        super(Category.Player, "FastElytra", "Swap chestplate with elytra.");
    }

    @Override
    public void onEnable() {
        swappedOnEnable = false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (swappedOnEnable) return;

        ItemStack chest = mc.player.inventory.armorItemInSlot(2);
        if (chest.getItem() == Items.ELYTRA) {
            swappedOnEnable = true; // already wearing elytra
            return;
        }

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!ItemUtil.isEmpty(stack) && stack.getItem() == Items.ELYTRA) {
                int containerSlot = (i < 9) ? (i + 36) : i;
                int windowId = mc.player.inventoryContainer.windowId;
                mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(windowId, 6, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
                swappedOnEnable = true;
                return;
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        ItemStack chest = mc.player.inventory.armorItemInSlot(2);
        if (chest.getItem() != Items.ELYTRA) return;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!ItemUtil.isEmpty(stack) && stack.getItem() == Items.ELYTRA) continue;
            int containerSlot = (i < 9) ? (i + 36) : i;
            int windowId = mc.player.inventoryContainer.windowId;
            mc.playerController.windowClick(windowId, 6, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
            return;
        }
    }
}
