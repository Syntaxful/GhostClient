package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import ghostclient.util.ItemUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

/**
 * Automatically moves a totem of undying to the offhand at low health.
 *
 * Container slot mapping for ContainerPlayer (1.12):
 *   0       = crafting result
 *   1-4     = crafting grid
 *   5-8     = armor (head=5, chest=6, legs=7, feet=8)
 *   9-35    = main inventory (matches player inventory slots 9-35)
 *   36-44   = hotbar (player inventory slots 0-8)
 *   45      = offhand
 */
public class AutoTotem extends Module {

    private final NumberValue health = new NumberValue("Health Threshold", "Equip totem when HP <= this", 14.0, 1.0, 20.0, 1.0);
    private int cooldown = 0;

    public AutoTotem() {
        super(Category.Combat, "AutoTotem", "Auto move totem to offhand at low health.");
        addSetting(health);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (cooldown > 0) { cooldown--; return; }
        if (mc.player.getHealth() > health.getValue()) return;

        // Already have a totem in offhand — nothing to do
        ItemStack offhand = mc.player.getHeldItemOffhand();
        if (!ItemUtil.isEmpty(offhand) && offhand.getItem() == Items.TOTEM_OF_UNDYING) return;

        // Scan hotbar (inv slots 0-8) and main inventory (inv slots 9-35)
        for (int invSlot = 0; invSlot < 36; invSlot++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(invSlot);
            if (ItemUtil.isEmpty(stack) || stack.getItem() != Items.TOTEM_OF_UNDYING) continue;

            // Convert player inventory slot → container slot
            int containerSlot = (invSlot < 9) ? (invSlot + 36) : invSlot;
            int windowId = mc.player.inventoryContainer.windowId;

            // Pick up totem
            mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);
            // Place in offhand slot
            mc.playerController.windowClick(windowId, 45, 0, ClickType.PICKUP, mc.player);
            // If something was in offhand, drop it back to the source slot
            mc.playerController.windowClick(windowId, containerSlot, 0, ClickType.PICKUP, mc.player);

            cooldown = 10; // brief cooldown to avoid double-swap
            return;
        }
    }
}
