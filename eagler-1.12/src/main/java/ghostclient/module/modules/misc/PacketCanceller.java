package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;

/**
 * Cancels selected packets.
 */
public class PacketCanceller extends Module {

    private final ModeValue type = new ModeValue("Type", "Packet type to cancel", "Movement", "Movement", "Chat", "Use");

    public PacketCanceller() {
        super(Category.Misc, "PacketCanceller", "Cancel packets.");
        addSetting(type);
    }

    @EventHandler
    public void onPacket(PacketEvent.Send event) {
        // TODO: cancel based on packet type
    }
}
