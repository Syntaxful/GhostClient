package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

public class NoAnimations extends Module {

    public NoAnimations() {
        super(Category.Misc, "NoAnimations", "Disable limb swing/bobbing animations.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.player.limbSwing = 0.0F;
        mc.player.limbSwingAmount = 0.0F;
    }
}