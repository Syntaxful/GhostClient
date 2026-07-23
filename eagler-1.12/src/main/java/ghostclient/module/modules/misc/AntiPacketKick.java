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
        // Catch exceptions during packet processing to prevent disconnects.
        // The actual protection is applied by the packet handler wrapping logic.
        try {
            event.getPacket();
        } catch (Exception e) {
            event.setCancelled(true);
        }
    }
}
