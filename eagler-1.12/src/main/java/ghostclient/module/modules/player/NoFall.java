package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
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
    public void onPacketSend(PacketEvent.Send event) {
        if (mc.player == null) return;
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if (mc.player.fallDistance > 2.0F) {
                setOnGround(packet, true);
            }
        }
    }

    private void setOnGround(CPacketPlayer packet, boolean onGround) {
        try {
            java.lang.reflect.Field f = CPacketPlayer.class.getDeclaredField("onGround");
            f.setAccessible(true);
            f.setBoolean(packet, onGround);
        } catch (Exception e) {
            // Fallback: the packet handler patch will read this state.
        }
    }
}
