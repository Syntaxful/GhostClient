package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Automatically eats food when hungry.
 */
public class AutoEat extends Module {

    private final NumberValue hunger = new NumberValue("Hunger", "Eat when hunger below", 15, 1, 20, 1);

    public AutoEat() {
        super(Category.Player, "AutoEat", "Auto eat when hungry.");
        addSetting(hunger);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.getFoodStats().getFoodLevel() > hunger.getInt()) return;
        int foodSlot = findFoodSlot();
        if (foodSlot != -1) {
            int old = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = foodSlot;
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            mc.player.inventory.currentItem = old;
        }
    }

    private int findFoodSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!(stack == null || stack.func_190926_b()) && stack.getItem() instanceof ItemFood) return i;
        }
        return -1;
    }
}
