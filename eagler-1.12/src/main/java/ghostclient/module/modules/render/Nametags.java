package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.util.RenderUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

/**
 * Renders larger nametags above players.
 */
public class Nametags extends Module {

    private final BooleanValue health = new BooleanValue("Health", "Show player health", true);
    private final BooleanValue distance = new BooleanValue("Distance", "Show distance", true);
    private final BooleanValue armor = new BooleanValue("Armor", "Show armor items", true);

    public Nametags() {
        super(Category.Render, "Nametags", "Draw big nametags above players.");
        addSetting(health);
        addSetting(distance);
        addSetting(armor);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.player == null || mc.getRenderManager() == null) return;
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player) continue;
            Vec3d pos = RenderUtils.getRenderPos(player.posX, player.posY + player.height + 0.5, player.posZ);
            // Project to screen; simplified as 2D point for the patch to scale.
            StringBuilder text = new StringBuilder(player.getName());
            if (health.getValue()) text.append(" ").append((int) player.getHealth()).append("HP");
            if (distance.getValue()) text.append(" ").append((int) mc.player.getDistanceToEntity(player)).append("m");
            // The actual scaled rendering is best done by a patch; this module exposes the intended text.
        }
    }
}
