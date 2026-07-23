package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import ghostclient.util.ItemUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Renders armor and held item durability with real item icons.
 */
public class Armour extends Module {

    private final BooleanValue showIcons = new BooleanValue("ShowIcons", "Render actual item icons", true);
    private final BooleanValue showDurability = new BooleanValue("ShowDurability", "Show durability text", true);
    private final ModeValue position = new ModeValue("Position", "HUD position", "Bottom", "Bottom", "Top", "Left");

    public Armour() {
        super(Category.Render, "Armour", "Show armor durability and icons.");
        addSetting(showIcons);
        addSetting(showDurability);
        addSetting(position);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.fontRendererObj == null || mc.getRenderItem() == null) return;
        ScaledResolution sr = new ScaledResolution(mc);

        NonNullList<ItemStack> armor = mc.player.inventory.armorInventory;
        ItemStack held = mc.player.getHeldItemMainhand();
        int total = armor.size() + (held != null && !ItemUtil.isEmpty(held) ? 1 : 0);
        if (total == 0) return;

        String pos = position.getValue();
        int startX, startY;
        if ("Top".equals(pos)) {
            startX = sr.getScaledWidth() / 2 - 40;
            startY = 4;
        } else if ("Left".equals(pos)) {
            startX = 4;
            startY = sr.getScaledHeight() / 2 - (total * 9);
        } else {
            startX = 4;
            startY = sr.getScaledHeight() - (total * 18) - 4;
        }

        int y = startY;
        for (int i = 0; i < armor.size(); i++) {
            ItemStack stack = armor.get(i);
            if (ItemUtil.isEmpty(stack)) continue;
            renderItem(stack, startX, y);
            y += 18;
        }
        if (held != null && !ItemUtil.isEmpty(held)) {
            renderItem(held, startX, y);
        }
    }

    private void renderItem(ItemStack stack, int x, int y) {
        if (showIcons.getValue()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        }
        if (showDurability.getValue()) {
            int dur = stack.getMaxDamage() - stack.getItemDamage();
            int max = stack.getMaxDamage();
            if (max > 0) {
                String text = dur + "/" + max;
                int pct = (dur * 100 / max);
                int color = pct > 50 ? 0xFF55FF55 : (pct > 25 ? 0xFFFFFF55 : 0xFFFF5555);
                mc.fontRendererObj.drawStringWithShadow(text, x + (showIcons.getValue() ? 20 : 0), y + 4, color);
            }
        }
    }
}
