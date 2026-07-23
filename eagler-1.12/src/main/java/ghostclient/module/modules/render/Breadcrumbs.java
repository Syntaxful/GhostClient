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
 * Draws a trail behind the player.
 */
public class Breadcrumbs extends Module {

    private final List<Vec3d> points = new ArrayList<>();

    public Breadcrumbs() {
        super(Category.Render, "Breadcrumbs", "Trail of where you walked.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        points.add(new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ));
        if (points.size() > 200) points.remove(0);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (points.size() < 2 || mc.getRenderManager() == null) return;
        RenderUtils.prepare2D();
        for (int i = 1; i < points.size(); i++) {
            Vec3d a = RenderUtils.getRenderPos(points.get(i - 1).xCoord, points.get(i - 1).yCoord, points.get(i - 1).zCoord);
            Vec3d b = RenderUtils.getRenderPos(points.get(i).xCoord, points.get(i).yCoord, points.get(i).zCoord);
            // Project 3D points to 2D screen; this is a simplified representation.
            RenderUtils.drawLine2D(a.xCoord, a.yCoord, b.xCoord, b.yCoord, 0xAAFFFFFF, 2.0f);
        }
        RenderUtils.end2D();
    }

    @Override
    public void onDisable() {
        points.clear();
    }
}
