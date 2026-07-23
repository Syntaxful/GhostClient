package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Manages the offhand slot automatically.
 */
public class Offhand extends Module {

    private final ModeValue item = new ModeValue("Item", "Offhand item", "Totem", "Totem", "Crystal", "Gapple", "Shield");

    public Offhand() {
        super(Category.Combat, "Offhand", "Auto manage offhand item.");
        addSetting(item);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        ItemStack offhand = mc.player.getHeldItemOffhand();
        String mode = item.getValue();
        if (mode.equals("Totem") && offhand.getItem() != Items.TOTEM_OF_UNDYING) moveItem(Items.TOTEM_OF_UNDYING);
        if (mode.equals("Gapple") && offhand.getItem() != Items.GOLDEN_APPLE) moveItem(Items.GOLDEN_APPLE);
        if (mode.equals("Shield") && offhand.getItem() != Items.SHIELD) moveItem(Items.SHIELD);
    }

    private void moveItem(net.minecraft.item.Item target) {
        for (int i = 0; i < 45; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == target) {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, net.minecraft.inventory.ClickType.PICKUP, mc.player);
                return;
            }
        }
    }
}
