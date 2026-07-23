package ghostclient.event;

import net.minecraft.util.math.Vec3d;

/**
 * Fired around player movement updates.
 */
public class MotionUpdateEvent extends Event {

    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;

    public MotionUpdateEvent(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public static class Pre extends MotionUpdateEvent {
        public Pre(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(x, y, z, yaw, pitch, onGround);
        }
    }

    public static class Post extends MotionUpdateEvent {
        public Post(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(x, y, z, yaw, pitch, onGround);
        }
    }
}
