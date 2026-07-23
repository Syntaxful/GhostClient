package ghostclient.event;

import ghostclient.GhostClient;
import ghostclient.module.Module;
import ghostclient.event.TickEvent;
import ghostclient.event.RenderEvent;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.PacketEvent;
import ghostclient.event.Cancellable;

/**
 * Lightweight event bus for GhostClient.
 * Modified for TeaVM WASM-GC compatibility to avoid reflection.
 */
public class EventBus {

    public void register(Object target) {
        // No-op
    }

    public void unregister(Object target) {
        // No-op
    }

    public <T extends Event> T post(T event) {
        if (GhostClient.MODULES == null || GhostClient.MODULES.getAll() == null) {
            return event;
        }

        for (Module m : GhostClient.MODULES.getAll()) {
            if (!m.isEnabled()) continue;
            
            try {
                if (event instanceof TickEvent.Post) {
                    m.onTick((TickEvent.Post) event);
                } else if (event instanceof RenderEvent.Post) {
                    m.onRender((RenderEvent.Post) event);
                } else if (event instanceof KeyInputEvent) {
                    m.onKey((KeyInputEvent) event);
                } else if (event instanceof PacketEvent.Receive) {
                    m.onPacket((PacketEvent.Receive) event);
                } else if (event instanceof PacketEvent.Send) {
                    m.onPacket((PacketEvent.Send) event);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }

            if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
                break;
            }
        }
        return event;
    }
}
