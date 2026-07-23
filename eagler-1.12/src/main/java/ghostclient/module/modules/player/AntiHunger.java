package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Reduces hunger loss by spoofing on-ground.
 */
public class AntiHunger extends Module {

    public AntiHunger() {
        super(Category.Player, "AntiHunger", "Reduce hunger loss.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.player.connection == null) return;
        if (!mc.player.isSprinting() && mc.player.motionY < 0 && !mc.player.onGround) {
            mc.player.connection.sendPacket(new net.minecraft.network.play.client.CPacketPlayer(true));
        }
    }
}
