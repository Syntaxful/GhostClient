package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

/**
 * Strong aim assist — snaps crosshair toward the nearest target.
 * Strength 100 = instant snap every tick. Increase range for larger hitbox coverage.
 */
public class AimAssist extends Module {

    private final NumberValue range    = new NumberValue("Range",    "Target range (blocks)",        8.0,  1.0, 12.0,  0.5);
    private final NumberValue strength = new NumberValue("Strength", "Snap strength (1-100)",       95.0,  1.0, 100.0,  1.0);
    private final NumberValue fov      = new NumberValue("FOV",      "Aim FOV (degrees)",          180.0,  1.0, 180.0,  1.0);
    private final BooleanValue playersOnly = new BooleanValue("PlayersOnly", "Only target players", true);
    private final ModeValue mode = new ModeValue("Mode", "Aim style", "Silent", "Silent", "Legit", "Smooth");

    public AimAssist() {
        super(Category.Combat, "AimAssist", "Strongly aim at nearby entities.");
        addSetting(range);
        addSetting(strength);
        addSetting(fov);
        addSetting(playersOnly);
        addSetting(mode);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        Entity target = findTarget();
        if (target == null) return;

        double dx = target.posX - mc.player.posX;
        double dy = (target.posY + target.getEyeHeight() * 0.8) - (mc.player.posY + mc.player.getEyeHeight());
        double dz = target.posZ - mc.player.posZ;

        float targetYaw   = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        // Factor: Silent snaps fully at strength 100, Legit is much softer
        float factor = strength.getFloat() / 100.0f;
        if ("Legit".equals(mode.getValue())) {
            factor *= 0.08f;
        } else if ("Smooth".equals(mode.getValue())) {
            factor *= 0.20f;
        }
        factor = Math.min(factor, 1.0f);

        mc.player.rotationYaw   = lerpAngle(mc.player.rotationYaw, targetYaw, factor);
        mc.player.rotationPitch = MathHelper.clamp(lerp(mc.player.rotationPitch, targetPitch, factor), -90.0f, 90.0f);
    }

    private Entity findTarget() {
        double bestDist = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player) continue;
            if (!entity.isEntityAlive()) continue;
            if (!(entity instanceof EntityLivingBase)) continue;
            if (playersOnly.getValue() && !(entity instanceof EntityPlayer)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist > range.getValue()) continue;
            if (!isInFOV(entity)) continue;
            if (dist < bestDist) {
                bestDist = dist;
                target = entity;
            }
        }
        return target;
    }

    private boolean isInFOV(Entity entity) {
        double dx = entity.posX - mc.player.posX;
        double dz = entity.posZ - mc.player.posZ;
        double angle = Math.toDegrees(Math.atan2(dz, dx)) - mc.player.rotationYaw;
        while (angle < -180) angle += 360;
        while (angle >  180) angle -= 360;
        return Math.abs(angle) <= fov.getValue() / 2.0;
    }

    private float lerp(float a, float b, float t)      { return a + (b - a) * t; }
    private float lerpAngle(float a, float b, float t) {
        float diff = ((b - a + 540) % 360) - 180;
        return a + diff * t;
    }
}
