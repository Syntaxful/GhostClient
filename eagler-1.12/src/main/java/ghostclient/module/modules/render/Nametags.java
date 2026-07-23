package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Renders larger nametags above players with health/distance/armor info.
 */
public class Nametags extends Module {

    private final BooleanValue health = new BooleanValue("Health", "Show player health", true);
    private final BooleanValue distance = new BooleanValue("Distance", "Show distance", true);
    private final BooleanValue armor = new BooleanValue("Armor", "Show armor items", false);

    public Nametags() {
        super(Category.Render, "Nametags", "Draw nametags above players.");
        addSetting(health);
        addSetting(distance);
        addSetting(armor);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.player == null || mc.fontRendererObj == null || mc.getRenderManager() == null) return;
        ScaledResolution sr = new ScaledResolution(mc);

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player) continue;

            // Very simple 2D projection: ignore camera rotation for stability, just offset by relative position.
            double dx = player.posX - mc.getRenderManager().viewerPosX;
            double dy = player.posY + player.height + 0.5 - mc.getRenderManager().viewerPosY;
            double dz = player.posZ - mc.getRenderManager().viewerPosZ;
            double dist = Math.sqrt(dx * dx + dz * dz);
            if (dist > 64.0) continue;

            // Project roughly to screen center with simple perspective scaling.
            double fovScale = 90.0 / mc.gameSettings.fovSetting;
            double scale = Math.max(0.5, 8.0 / dist) * fovScale;
            int screenX = sr.getScaledWidth() / 2 + (int) (dx * scale * 20.0);
            int screenY = sr.getScaledHeight() / 2 - (int) (dy * scale * 20.0);

            StringBuilder text = new StringBuilder(player.getName());
            if (health.getValue()) text.append(" ").append((int) player.getHealth()).append("HP");
            if (distance.getValue()) text.append(" ").append((int) mc.player.getDistanceToEntity(player)).append("m");

            String s = text.toString();
            int w = mc.fontRendererObj.getStringWidth(s);
            int x = screenX - w / 2;
            int y = screenY - mc.fontRendererObj.FONT_HEIGHT / 2;
            mc.fontRendererObj.drawStringWithShadow(s, x, y, 0xFFFFFFFF);
        }
    }
}
