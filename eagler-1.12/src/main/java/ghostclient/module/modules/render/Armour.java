package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

public class Armour extends Module {

    public Armour() {
        super(Category.Render, "Armour", "Show armor durability.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.fontRendererObj == null) return;
        ScaledResolution sr = new ScaledResolution(mc);
        int y = sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 4;
        for (ItemStack stack : mc.player.inventory.armorInventory) {
            if (stack == null) continue;
            String text = stack.getDisplayName() + " " + (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage();
            mc.fontRendererObj.drawStringWithShadow(text, 4, y, 0xFFFFFFFF);
            y -= mc.fontRendererObj.FONT_HEIGHT + 2;
        }
        ItemStack held = mc.player.getHeldItemMainhand();
        if (held != null) {
            String text = "Held " + (held.getMaxDamage() - held.getItemDamage()) + "/" + held.getMaxDamage();
            mc.fontRendererObj.drawStringWithShadow(text, 4, y, 0xFFFFFFFF);
        }
    }
}