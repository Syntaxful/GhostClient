package ghostclient.module.modules.combat;

import ghostclient.GhostClient;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

/**
 * Automatically attacks nearby entities. Supports 1.8 spam and 1.9+ timed attack modes.
 * Also notifies WTap / STap / AutoComboJump on successful attacks.
 */
public class KillAura extends Module {

    private final NumberValue range = new NumberValue("Range", "Attack range", 4.5, 1.0, 12.0, 0.1);
    private final NumberValue cps = new NumberValue("CPS", "Attacks per second (spam mode)", 12, 1, 20, 1);
    private final NumberValue fov = new NumberValue("FOV", "Attack field of view", 180, 1, 180, 1);
    private final BooleanValue playersOnly = new BooleanValue("PlayersOnly", "Only attack players", true);
    private final ModeValue attackMode = new ModeValue("Attack Mode", "1.8 spam or 1.9+ timed", "Spam", "Spam", "Timed");
    private final BooleanValue hitDelay = new BooleanValue("Hit Delay", "Respect attack cooldown in timed mode", true);
    private int ticksSinceAttack = 0;

    public KillAura() {
        super(Category.Combat, "KillAura", "Automatically attacks nearby entities.");
        addSetting(range);
        addSetting(cps);
        addSetting(fov);
        addSetting(playersOnly);
        addSetting(attackMode);
        addSetting(hitDelay);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        Entity target = findTarget();
        if (target == null) return;

        boolean timed = "Timed".equals(attackMode.getValue());
        if (timed) {
            if (hitDelay.getValue() && mc.player.getCooledAttackStrength(0.0f) < 1.0f) return;
            if (ticksSinceAttack > 0) {
                ticksSinceAttack--;
                return;
            }
            ticksSinceAttack = 10;
        } else {
            ticksSinceAttack++;
            int interval = 20 / cps.getInt();
            if (interval < 1) interval = 1;
            if (ticksSinceAttack < interval) return;
            ticksSinceAttack = 0;
        }

        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(EnumHand.MAIN_HAND);

        notifyCombatHelpers();
    }

    private Entity findTarget() {
        double bestRange = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player) continue;
            if (!entity.isEntityAlive()) continue;
            if (!(entity instanceof EntityLivingBase)) continue;
            if (playersOnly.getValue() && !(entity instanceof EntityPlayer)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist > range.getValue()) continue;
            if (!isInFOV(entity)) continue;
            if (dist < bestRange) {
                bestRange = dist;
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
        while (angle > 180) angle -= 360;
        return Math.abs(angle) <= fov.getValue() / 2.0;
    }

    private void notifyCombatHelpers() {
        try {
            AutoWtap wtap = (AutoWtap) GhostClient.MODULES.getByName("WTap");
            if (wtap != null) wtap.onAttack();
        } catch (Exception ignored) {}
        try {
            STap stap = (STap) GhostClient.MODULES.getByName("STap");
            if (stap != null) stap.onAttack();
        } catch (Exception ignored) {}
    }
}
