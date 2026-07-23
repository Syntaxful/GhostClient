package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Sends critical packets automatically when attacking.
 */
public class Criticals extends Module {

    public Criticals() {
        super(Category.Combat, "Criticals", "Makes every hit a critical hit.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.onGround) return;
        if (mc.gameSettings.keyBindAttack.isKeyDown() && mc.pointedEntity != null) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.05, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
    }
}
