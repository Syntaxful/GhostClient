package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Customizes the block selection box.
 */
public class BlockSelection extends Module {

    private final NumberValue width = new NumberValue("Width", "Outline width", 2, 0.5, 5, 0.5);

    public BlockSelection() {
        super(Category.Render, "BlockSelection", "Custom block selection outline.");
        addSetting(width);
    }
}
