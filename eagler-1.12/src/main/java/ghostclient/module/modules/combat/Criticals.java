package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Sends critical-hit position packets automatically when attacking.
 * Works by sending small Y-offset packets that register as a fall on the server.
 */
public class Criticals extends Module {

    private final ModeValue mode   = new ModeValue("Mode",   "Critical hit style",      "Packet", "Packet", "Jump", "Mini");
    private final ModeValue timing = new ModeValue("Timing", "Attack timing",            "1.9+",   "1.8",   "1.9+");
    private int cooldownTicks = 0;

    public Criticals() {
        super(Category.Combat, "Criticals", "Makes every hit a critical hit.");
        addSetting(mode);
        addSetting(timing);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        boolean attacking = mc.gameSettings.keyBindAttack.isKeyDown() && mc.pointedEntity != null;
        if (!attacking) {
            cooldownTicks = 0;
            return;
        }

        // Respect 1.9 cooldown if enabled
        if ("1.9+".equals(timing.getValue()) && mc.player.getCooledAttackStrength(0.0f) < 1.0f) {
            cooldownTicks = 0;
            return;
        }

        if (cooldownTicks > 0) {
            cooldownTicks--;
            return;
        }
        cooldownTicks = "1.9+".equals(timing.getValue()) ? 10 : 2;

        String m = mode.getValue();
        if ("Packet".equals(m)) {
            // Send micro-jump packets so the server registers a critical
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.05,   mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, true));
        } else if ("Jump".equals(m) && mc.player.onGround) {
            mc.player.jump();
        } else if ("Mini".equals(m)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, true));
        }
    }

    /** Called by KillAura when it performs an attack so criticals apply to aura hits too. */
    public void doCritical() {
        if (mc.player == null) return;
        String m = mode.getValue();
        if ("Packet".equals(m)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.05,   mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, true));
        } else if ("Jump".equals(m) && mc.player.onGround) {
            mc.player.jump();
        } else if ("Mini".equals(m)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,          mc.player.posZ, true));
        }
    }
}
