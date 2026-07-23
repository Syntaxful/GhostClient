package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Customizes held item view model.
 */
public class ViewModel extends Module {

    public final NumberValue fov = new NumberValue("FOV", "Item FOV", 90, 30, 180, 1);

    public ViewModel() {
        super(Category.Render, "ViewModel", "Customize held item view.");
        addSetting(fov);
    }
}
