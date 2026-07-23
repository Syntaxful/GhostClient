package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.network.play.client.CPacketAnimation;

/**
 * Prevents the server from seeing your arm swing while mining.
 */
public class SilentMine extends Module {

    public SilentMine() {
        super(Category.Player, "SilentMine", "Mine without sending arm swings.");
    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketAnimation) {
            event.setCancelled(true);
        }
    }
}
