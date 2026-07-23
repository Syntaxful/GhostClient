package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Speed up ridden entities.
 */
public class EntitySpeed extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Entity speed", 2.0, 0.5, 5.0, 0.1);

    public EntitySpeed() {
        super(Category.Movement, "EntitySpeed", "Speed up mounts and boats.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.player.getRidingEntity() == null) return;
        net.minecraft.entity.Entity e = mc.player.getRidingEntity();
        e.motionX *= speed.getValue();
        e.motionZ *= speed.getValue();
    }
}
