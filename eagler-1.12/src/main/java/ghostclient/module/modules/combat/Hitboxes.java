package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import ghostclient.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Expands entity hitboxes server-side and renders them through walls.
 * The expansion value is read by a patch in Entity#getEntityBoundingBox.
 */
public class Hitboxes extends Module {

    private final NumberValue size = new NumberValue("Size", "Hitbox expansion in blocks", 1.0, 0.0, 6.0, 0.1);
    private final BooleanValue visible = new BooleanValue("Visible", "Draw hitbox outlines", true);
    private final BooleanValue playersOnly = new BooleanValue("PlayersOnly", "Only show player hitboxes", false);

    public Hitboxes() {
        super(Category.Combat, "Hitboxes", "Show and enlarge entity hitboxes.");
        addSetting(size);
        addSetting(visible);
        addSetting(playersOnly);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // Expansion is read by the patched entity bounding box getter.
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (!visible.getValue() || mc.world == null || mc.getRenderManager() == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player) continue;
            if (!entity.isEntityAlive()) continue;
            if (!(entity instanceof EntityLivingBase)) continue;
            if (playersOnly.getValue() && !(entity instanceof EntityPlayer)) continue;
            AxisAlignedBB box = entity.getEntityBoundingBox();
            RenderUtils.drawBox(box, 0xAAFFFFFF, true);
        }
    }

    public double getExpansion() {
        return isEnabled() ? size.getValue() : 0.0;
    }
}
