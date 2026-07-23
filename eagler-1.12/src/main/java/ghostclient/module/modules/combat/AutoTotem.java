package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Automatically moves a totem to the offhand.
 */
public class AutoTotem extends Module {

    public AutoTotem() {
        super(Category.Combat, "AutoTotem", "Auto move totem to offhand.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        ItemStack offhand = mc.player.getHeldItemOffhand();
        if (offhand.getItem() == Items.TOTEM_OF_UNDYING) return;
        for (int i = 0; i < 45; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                return;
            }
        }
    }
}
