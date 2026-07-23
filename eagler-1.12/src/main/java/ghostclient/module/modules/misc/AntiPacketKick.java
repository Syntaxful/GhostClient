package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Prevents being kicked by invalid packets.
 */
public class AntiPacketKick extends Module {

    public AntiPacketKick() {
        super(Category.Misc, "AntiPacketKick", "Prevent kicks from invalid packets.");
    }

    @EventHandler
    public void onPacket(PacketEvent.Receive event) {
        // TODO: catch and cancel malformed packets
    }
}
