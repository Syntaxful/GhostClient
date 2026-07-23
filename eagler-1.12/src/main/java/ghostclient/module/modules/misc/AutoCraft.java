package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import ghostclient.util.ItemUtil;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Automatically crafts the selected item when ingredients are available.
 * Uses the actual item registry IDs so it checks real inventory contents.
 */
public class AutoCraft extends Module {

    private final ModeValue recipe = new ModeValue("Recipe", "Item to craft",
        "Sticks", "Sticks", "Planks", "Crafting Table", "Chest", "Furnace", "Torch", "Diamond Pickaxe", "Diamond Sword");
    private int cooldown = 0;

    public AutoCraft() {
        super(Category.Misc, "AutoCraft", "Auto craft selected items when ingredients are available.");
        addSetting(recipe);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        if (cooldown-- > 0) return;
        if (mc.player.openContainer == null || mc.player.openContainer == mc.player.inventoryContainer) return;

        String target = recipe.getValue();

        // Ingredient counts required
        int planksNeeded = 0, sticksNeeded = 0, diamondsNeeded = 0, ironNeeded = 0, cobbleNeeded = 0, coalNeeded = 0;
        String outputItem = "";

        if ("Sticks".equals(target)) { planksNeeded = 2; outputItem = "stick"; }
        else if ("Planks".equals(target)) { /* planks from logs handled below */ outputItem = "planks"; }
        else if ("Crafting Table".equals(target)) { planksNeeded = 4; outputItem = "crafting_table"; }
        else if ("Chest".equals(target)) { planksNeeded = 8; outputItem = "chest"; }
        else if ("Furnace".equals(target)) { cobbleNeeded = 8; outputItem = "furnace"; }
        else if ("Torch".equals(target)) { coalNeeded = 1; sticksNeeded = 1; outputItem = "torch"; }
        else if ("Diamond Pickaxe".equals(target)) { diamondsNeeded = 3; sticksNeeded = 2; outputItem = "diamond_pickaxe"; }
        else if ("Diamond Sword".equals(target)) { diamondsNeeded = 2; sticksNeeded = 1; outputItem = "diamond_sword"; }
        else return;

        if (!hasIngredients(planksNeeded, sticksNeeded, diamondsNeeded, ironNeeded, cobbleNeeded, coalNeeded)) return;

        // Try to click the output slot (slot 0) in a crafting table / inventory crafting GUI
        try {
            mc.playerController.windowClick(mc.player.openContainer.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player);
            cooldown = 5;
        } catch (Exception ignored) {}
    }

    private boolean hasIngredients(int planks, int sticks, int diamonds, int iron, int cobble, int coal) {
        int p = 0, s = 0, d = 0, in = 0, c = 0, co = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (ItemUtil.isEmpty(stack)) continue;
            Item item = stack.getItem();
            String name = Item.REGISTRY.getNameForObject(item).toString();
            if (name.contains("planks")) p += ItemUtil.getCount(stack);
            else if (name.contains("stick")) s += ItemUtil.getCount(stack);
            else if (name.contains("diamond")) d += ItemUtil.getCount(stack);
            else if (name.contains("iron_ingot")) in += ItemUtil.getCount(stack);
            else if (name.contains("cobblestone")) c += ItemUtil.getCount(stack);
            else if (name.contains("coal")) co += ItemUtil.getCount(stack);
            else if (name.contains("log")) p += ItemUtil.getCount(stack) * 4;
        }
        return p >= planks && s >= sticks && d >= diamonds && in >= iron && c >= cobble && co >= coal;
    }

    public String getTargetRecipe() {
        return recipe.getValue();
    }
}
