package ghostclient.event;

/**
 * Fired during rendering.
 */
public class RenderEvent extends Event {

    public final float partialTicks;

    public RenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public static class Pre extends RenderEvent {
        public Pre(float partialTicks) {
            super(partialTicks);
        }
    }

    public static class Post extends RenderEvent {
        public Post(float partialTicks) {
            super(partialTicks);
        }
    }
}
