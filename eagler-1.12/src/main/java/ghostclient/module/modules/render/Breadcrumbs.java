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
        // TODO: draw 3D line strip through points via RenderUtils
    }

    @Override
    public void onDisable() {
        points.clear();
    }
}
