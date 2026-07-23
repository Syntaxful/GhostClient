package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumHand;

/**
 * Removes the right-click placement delay.
 */
public class FastPlace extends Module {

    private final NumberValue delay = new NumberValue("Delay", "Ticks between placements (0 = instant)", 0, 0, 4, 1);

    public FastPlace() {
        super(Category.World, "FastPlace", "Place blocks faster.");
        addSetting(delay);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        // Reset right click delay via reflection to avoid direct private-field access.
        try {
            java.lang.reflect.Field field = mc.getClass().getDeclaredField("rightClickDelayTimer");
            field.setAccessible(true);
            int current = field.getInt(mc);
            if (current > delay.getInt()) {
                field.setInt(mc, delay.getInt());
            }
        } catch (Exception ignored) {
            // Fallback: spam the use key when the player is holding a block.
            if (mc.gameSettings.keyBindUseItem.isKeyDown() && delay.getInt() <= 0) {
                mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            }
        }
    }
}
