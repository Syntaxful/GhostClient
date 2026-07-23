package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;

/**
 * Expands and/or renders entity hitboxes.
 */
public class Hitboxes extends Module {

    private final NumberValue size = new NumberValue("Size", "Hitbox expansion in blocks", 1.0, 0.0, 12.0, 0.1);
    private final BooleanValue visible = new BooleanValue("Visible", "Draw hitbox outlines", true);
    private final BooleanValue playersOnly = new BooleanValue("PlayersOnly", "Only show player hitboxes", false);

    public Hitboxes() {
        super(Category.Combat, "Hitboxes", "Show and enlarge entity hitboxes.");
        addSetting(size);
        addSetting(visible);
        addSetting(playersOnly);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // Expansion is applied via mixin/patch in Entity#getEntityBoundingBox.
        // This module stores the requested expansion value for the patch to read.
    }

    public double getExpansion() {
        return isEnabled() ? size.getValue() : 0.0;
    }

    public boolean shouldRender() {
        return isEnabled() && visible.getValue();
    }
}
