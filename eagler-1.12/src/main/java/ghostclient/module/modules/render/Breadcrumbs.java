package ghostclient.module.modules.render;

import java.util.ArrayList;
import java.util.List;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.util.math.Vec3d;

/**
 * Draws a 3D trail behind the player.
 */
public class Breadcrumbs extends Module {

    private final List<Vec3d> points = new ArrayList<>();

    public Breadcrumbs() {
        super(Category.Render, "Breadcrumbs", "Trail of where you walked.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        Vec3d current = new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (points.isEmpty() || current.distanceTo(points.get(points.size() - 1)) > 0.15) {
            points.add(current);
        }
        if (points.size() > 200) points.remove(0);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (points.size() < 2 || mc.getRenderManager() == null) return;
        RenderUtils.drawPolyline(points, 0xAAFFFFFF, 2.0f);
    }

    @Override
    public void onDisable() {
        points.clear();
    }
}
