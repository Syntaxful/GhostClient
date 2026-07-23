package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Sends critical packets automatically when attacking.
 */
public class Criticals extends Module {

    private final ModeValue mode = new ModeValue("Mode", "Critical hit style", "Packet", "Packet", "Jump", "Mini");
    private final ModeValue timing = new ModeValue("Timing", "1.8 or 1.9+ attack system", "1.9+", "1.8", "1.9+");

    public Criticals() {
        super(Category.Combat, "Criticals", "Makes every hit a critical hit.");
        addSetting(mode);
        addSetting(timing);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.onGround) return;
        if (!mc.gameSettings.keyBindAttack.isKeyDown() || mc.pointedEntity == null) return;
        if ("1.9+".equals(timing.getValue()) && mc.player.getCooledAttackStrength(0.0f) < 1.0f) return;

        String m = mode.getValue();
        if ("Packet".equals(m)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.05, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        } else if ("Jump".equals(m)) {
            mc.player.jump();
        } else if ("Mini".equals(m)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
    }
}
