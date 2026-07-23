package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import ghostclient.util.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;

/**
 * Increases attack and block reach with an adjustable range and on-screen indicator.
 */
public class Reach extends Module {

    private final NumberValue reach = new NumberValue("Reach",  "Reach distance (blocks)", 3.5, 1.0, 12.0, 0.1);
    private final BooleanValue blocks = new BooleanValue("Blocks", "Extend block reach", true);
    private final BooleanValue entities = new BooleanValue("Entities", "Extend entity reach", true);
    private final BooleanValue indicator = new BooleanValue("Indicator", "Show when target is within reach", true);

    public Reach() {
        super(Category.Player, "Reach", "Increase reach distance.");
        addSetting(reach);
        addSetting(blocks);
        addSetting(entities);
        addSetting(indicator);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // The patched server reach logic reads getReach().
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (!indicator.getValue() || mc.fontRendererObj == null || mc.player == null) return;
        boolean inRange = false;
        Entity target = mc.pointedEntity;
        if (entities.getValue() && target != null && mc.player != null && mc.player.getDistanceToEntity(target) <= reach.getValue()) {
            inRange = true;
        }
        if (!inRange && blocks.getValue() && mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null) {
            double dist = mc.player.getPositionVector().distanceTo(
                new net.minecraft.util.math.Vec3d(mc.objectMouseOver.getBlockPos().getX() + 0.5,
                                                  mc.objectMouseOver.getBlockPos().getY() + 0.5,
                                                  mc.objectMouseOver.getBlockPos().getZ() + 0.5));
            inRange = dist <= reach.getValue();
        }

        ScaledResolution sr = new ScaledResolution(mc);
        String text = inRange ? "REACH: READY" : "REACH: " + String.format("%.1f", reach.getValue()) + " blocks";
        int color = inRange ? 0xFF55FF55 : 0xFFFFFFFF;
        int x = sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(text) / 2;
        int y = sr.getScaledHeight() / 2 + 20;
        mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
    }

    public double getReach() {
        return isEnabled() ? reach.getValue() : 3.0;
    }

    public boolean extendBlocks()     { return isEnabled() && blocks.getValue(); }
    public boolean extendEntities()   { return isEnabled() && entities.getValue(); }
    public boolean showIndicator()    { return isEnabled() && indicator.getValue(); }
}
