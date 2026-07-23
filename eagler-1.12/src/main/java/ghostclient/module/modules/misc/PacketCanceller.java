package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

/**
 * Cancels selected outgoing packets.
 */
public class PacketCanceller extends Module {

    private final ModeValue type = new ModeValue("Type", "Packet type to cancel", "Movement", "Movement", "Chat", "Use");

    public PacketCanceller() {
        super(Category.Misc, "PacketCanceller", "Cancel packets.");
        addSetting(type);
    }

    @EventHandler
    public void onPacket(PacketEvent.Send event) {
        String t = type.getValue();
        if ("Movement".equals(t) && event.getPacket() instanceof CPacketPlayer) {
            event.setCancelled(true);
        } else if ("Chat".equals(t) && event.getPacket() instanceof CPacketChatMessage) {
            event.setCancelled(true);
        } else if ("Use".equals(t) && event.getPacket() instanceof CPacketPlayerTryUseItem) {
            event.setCancelled(true);
        }
    }
}
