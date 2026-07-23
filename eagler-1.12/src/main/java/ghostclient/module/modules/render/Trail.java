package ghostclient.module.modules.render;

import java.util.ArrayList;
import java.util.List;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.util.math.Vec3d;

/**
 * Leaves a particle-style trail behind the player.
 */
public class Trail extends Module {

    private final List<Vec3d> trail = new ArrayList<>();

    public Trail() {
        super(Category.Render, "Trail", "Particle trail behind the player.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        trail.add(new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ));
        if (trail.size() > 100) trail.remove(0);
    }

    @Override
    public void onDisable() {
        trail.clear();
    }
}
