package ghostclient.module.modules.misc;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;

/**
 * Hides your real name in chat and tab.
 */
public class NameProtect extends Module {

    private final ModeValue name = new ModeValue("Name", "Display name", "You", "You", "Player", "Me");

    public NameProtect() {
        super(Category.Misc, "NameProtect", "Hide your real name.");
        addSetting(name);
    }
}
