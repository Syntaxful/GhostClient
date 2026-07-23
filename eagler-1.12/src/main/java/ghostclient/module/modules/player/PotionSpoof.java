package ghostclient.module.modules.player;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;

/**
 * Spoof potion effects for the client.
 */
public class PotionSpoof extends Module {

    private final ModeValue effect = new ModeValue("Effect", "Potion effect to spoof", "Speed", "Speed", "Jump", "Strength", "NightVision");

    public PotionSpoof() {
        super(Category.Player, "PotionSpoof", "Spoof potion effects.");
        addSetting(effect);
    }
}
