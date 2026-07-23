package ghostclient.module.modules.movement;

import java.util.ArrayList;
import java.util.List;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Chokes movement packets and releases them.
 */
public class Blink extends Module {

    private final List<Packet<?>> packets = new ArrayList<>();

    public Blink() {
        super(Category.Movement, "Blink", "Fake lag by delaying packets.");
    }

    @Override
    public void onEnable() {
        packets.clear();
    }

    @Override
    public void onDisable() {
        for (Packet<?> p : packets) {
            if (mc.player != null && mc.player.connection != null) mc.player.connection.sendPacket(p);
        }
        packets.clear();
    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            packets.add(event.getPacket());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // Optional: auto-release after too many queued packets to avoid kicks
        if (packets.size() > 100) {
            setEnabled(false);
        }
    }
}
