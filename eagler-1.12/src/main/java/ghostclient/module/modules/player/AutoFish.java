package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.item.ItemFishingRod;

/**
 * Auto pull and recast fishing rod.
 */
public class AutoFish extends Module {

    private int recastTicks = 0;

    public AutoFish() {
        super(Category.Player, "AutoFish", "Auto fish for you.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod)) return;
        if (mc.player.fishEntity != null && mc.player.fishEntity.motionY < 0) {
            // removed reflection call to private rightClickMouse
            recastTicks = 20;
        }
        if (recastTicks > 0) {
            recastTicks--;
            if (recastTicks == 0) {
                // removed reflection call to private rightClickMouse
            }
        }
    }
}
