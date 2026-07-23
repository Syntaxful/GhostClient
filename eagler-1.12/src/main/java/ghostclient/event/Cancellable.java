package ghostclient.event;

/**
 * Marker interface for events that can be cancelled.
 */
public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
