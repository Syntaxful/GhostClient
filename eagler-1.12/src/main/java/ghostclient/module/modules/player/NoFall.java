package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Prevents fall damage by spoofing on-ground packets.
 */
public class NoFall extends Module {

    public NoFall() {
        super(Category.Player, "NoFall", "Prevents fall damage.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.player.connection == null) return;
        if (mc.player.fallDistance > 2.0F) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }
}
