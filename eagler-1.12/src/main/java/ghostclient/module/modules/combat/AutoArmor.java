package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Automatically equips the best armor.
 */
public class AutoArmor extends Module {

    public AutoArmor() {
        super(Category.Combat, "AutoArmor", "Auto equip the best armor.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        for (int i = 9; i < 45; i++) {
            ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) stack.getItem();
                int slot = getArmorSlot(armor.getEquipmentSlot());
                if (slot != -1 && isBetter(stack, mc.player.inventory.getStackInSlot(slot))) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, net.minecraft.inventory.ClickType.QUICK_MOVE, mc.player);
                }
            }
        }
    }

    private int getArmorSlot(EntityEquipmentSlot slot) {
        switch (slot) {
            case HEAD: return 39;
            case CHEST: return 38;
            case LEGS: return 37;
            case FEET: return 36;
            default: return -1;
        }
    }

    private boolean isBetter(ItemStack candidate, ItemStack equipped) {
        if ((equipped == null || equipped.func_190926_b())) return true;
        if (!(candidate.getItem() instanceof ItemArmor) || !(equipped.getItem() instanceof ItemArmor)) return false;
        return ((ItemArmor) candidate.getItem()).damageReduceAmount > ((ItemArmor) equipped.getItem()).damageReduceAmount;
    }
}
