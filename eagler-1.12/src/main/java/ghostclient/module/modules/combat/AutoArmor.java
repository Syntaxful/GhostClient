package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.ItemUtil;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Automatically equips the best armor available in the inventory.
 */
public class AutoArmor extends Module {

    private int cooldown = 0;

    public AutoArmor() {
        super(Category.Combat, "AutoArmor", "Auto equip best armor.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || cooldown-- > 0) return;

        int windowId = mc.player.inventoryContainer.windowId;

        for (EntityEquipmentSlot slot : new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET}) {
            int bestSlot = -1;
            int bestTier = -1;

            // Armor slot mapping in ContainerPlayer: HEAD=5, CHEST=6, LEGS=7, FEET=8
            int armorContainerSlot = 5 + slot.getIndex();

            ItemStack equipped = mc.player.getItemStackFromSlot(slot);
            int currentTier = getArmorTier(equipped);

            for (int i = 0; i < 36; i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (ItemUtil.isEmpty(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
                ItemArmor armor = (ItemArmor) stack.getItem();
                if (armor.getEquipmentSlot() != slot) continue;
                int tier = getArmorTier(stack);
                if (tier > bestTier) {
                    bestTier = tier;
                    bestSlot = i;
                }
            }

            if (bestSlot != -1 && bestTier > currentTier) {
                int containerSlot = (bestSlot < 9) ? (bestSlot + 36) : bestSlot;
                mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(windowId, armorContainerSlot, 0, ClickType.PICKUP, mc.player);
                // If there was an item in the armor slot, the second click swapped it to the cursor.
                // Click the source slot again to return any swapped item.
                if (currentTier > 0) {
                    mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
                }
                cooldown = 5;
                return;
            }
        }
    }

    private int getArmorTier(ItemStack stack) {
        if (ItemUtil.isEmpty(stack) || !(stack.getItem() instanceof ItemArmor)) return 0;
        ItemArmor armor = (ItemArmor) stack.getItem();
        String name = armor.getArmorMaterial().getName();
        if (name.contains("diamond")) return 4;
        if (name.contains("iron")) return 3;
        if (name.contains("chain")) return 2;
        if (name.contains("gold")) return 1;
        return 1;
    }
}
