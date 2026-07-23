package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.ItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

/**
 * Automatically switches to the best sword in hotbar when attacking.
 */
public class AutoSword extends Module {

    public AutoSword() {
        super(Category.Combat, "AutoSword", "Switch to best sword when attacking.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (!mc.gameSettings.keyBindAttack.isKeyDown() || mc.pointedEntity == null) return;

        int bestSlot = -1;
        float bestDamage = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (ItemUtil.isEmpty(stack) || !(stack.getItem() instanceof ItemSword)) continue;
            // ItemSword damage is roughly 4 + material. We approximate by attackDamage attribute.
            float damage = 4.0f + ((ItemSword) stack.getItem()).getDamageVsEntity();
            if (damage > bestDamage) {
                bestDamage = damage;
                bestSlot = i;
            }
        }
        if (bestSlot != -1 && mc.player.inventory.currentItem != bestSlot) {
            mc.player.inventory.currentItem = bestSlot;
        }
    }
}
