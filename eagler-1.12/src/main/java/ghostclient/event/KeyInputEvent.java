package ghostclient.event;

import ghostclient.event.Cancellable;

/**
 * Fired when a keyboard key is pressed or released.
 */
public class KeyInputEvent extends Event implements Cancellable {

    private final int key;
    private final boolean pressed;
    private boolean cancelled = false;

    public KeyInputEvent(int key, boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }

    public int getKey() {
        return key;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
