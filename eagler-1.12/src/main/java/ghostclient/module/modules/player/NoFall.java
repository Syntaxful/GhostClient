package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Prevents fall damage by spoofing on-ground packets before landing.
 */
public class NoFall extends Module {

    public NoFall() {
        super(Category.Player, "NoFall", "Prevents fall damage.");
    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (mc.player == null) return;
        if (mc.player.fallDistance <= 2.0f) return;

        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if (mc.player.onGround) {
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
            // Patch fallback will handle the packet's onGround state if reflection fails
        }
    }
}
