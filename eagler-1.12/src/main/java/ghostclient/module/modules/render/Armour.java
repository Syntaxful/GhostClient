package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Renders armor and held item durability with actual item icons.
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
        if (mc.player == null || mc.fontRendererObj == null) return;
        ScaledResolution sr = new ScaledResolution(mc);

        NonNullList<ItemStack> armor = mc.player.inventory.armorInventory;
        ItemStack held = mc.player.getHeldItemMainhand();
        int total = armor.size() + (held != null ? 1 : 0);

        String pos = position.getValue();
        int startX = 4;
        int startY = sr.getScaledHeight() - (total * 18) - 4;
        if ("Top".equals(pos)) {
            startX = sr.getScaledWidth() / 2 - 40;
            startY = 4;
        } else if ("Left".equals(pos)) {
            startX = 4;
            startY = sr.getScaledHeight() / 2 - (total * 9);
        }

        for (int i = 0; i < armor.size(); i++) {
            ItemStack stack = armor.get(i);
            if (stack == null) continue;
            int x = startX;
            int y = startY + i * 18;
            if (showIcons.getValue()) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
            }
            if (showDurability.getValue()) {
                int dur = stack.getMaxDamage() - stack.getItemDamage();
                int max = stack.getMaxDamage();
                String text = dur + "/" + max;
                int pct = max == 0 ? 100 : (dur * 100 / max);
                int color = pct > 50 ? 0xFF55FF55 : (pct > 25 ? 0xFFFFFF55 : 0xFFFF5555);
                mc.fontRendererObj.drawStringWithShadow(text, x + (showIcons.getValue() ? 20 : 0), y + 4, color);
            }
        }
        if (held != null) {
            int y = startY + armor.size() * 18;
            if (showIcons.getValue()) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(held, startX, y);
            }
            if (showDurability.getValue()) {
                int dur = held.getMaxDamage() - held.getItemDamage();
                int max = held.getMaxDamage();
                String text = dur + "/" + max;
                int pct = max == 0 ? 100 : (dur * 100 / max);
                int color = pct > 50 ? 0xFF55FF55 : (pct > 25 ? 0xFFFFFF55 : 0xFFFF5555);
                mc.fontRendererObj.drawStringWithShadow(text, startX + (showIcons.getValue() ? 20 : 0), y + 4, color);
            }
        }
    }
}
