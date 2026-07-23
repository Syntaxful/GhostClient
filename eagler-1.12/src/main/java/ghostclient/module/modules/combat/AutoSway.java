package ghostclient.module.modules.combat;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically strafes (A/D) around the target while attacking.
 * Compatible with WTap and STap.
 */
public class AutoSway extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Strafe speed multiplier", 1.0, 0.1, 3.0, 0.1);
    private final NumberValue range = new NumberValue("Range", "Only sway when target within range", 4.0, 1.0, 8.0, 0.1);
    private int dir = 1;
    private int ticks = 0;

    public AutoSway() {
        super(Category.Combat, "AutoSway", "Auto strafe around targets.");
        addSetting(speed);
        addSetting(range);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        // Only sway if a combat module is active or the player is swinging.
        Module killAura = GhostClient.MODULES.getByName("KillAura");
        Module wtap = GhostClient.MODULES.getByName("WTap");
        Module stap = GhostClient.MODULES.getByName("STap");
        boolean combatActive = (killAura != null && killAura.isEnabled())
                || (wtap != null && wtap.isEnabled())
                || (stap != null && stap.isEnabled())
                || mc.gameSettings.keyBindAttack.isKeyDown();
        if (!combatActive) return;
        if (mc.player.getRidingEntity() != null) return;

        // Simple distance check to nearest living entity
        double best = range.getValue() + 1.0;
        for (net.minecraft.entity.Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive() || !(entity instanceof net.minecraft.entity.EntityLivingBase)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < best) best = dist;
        }
        if (best > range.getValue()) return;

        ticks++;
        if (ticks % 8 == 0) dir = -dir;
        mc.player.moveStrafing = dir * speed.getFloat();
        mc.player.setSprinting(true);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.moveStrafing = 0.0f;
    }
}
