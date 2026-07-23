package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Sol Client style motion blur.
 */
public class MotionBlur extends Module {

    public final NumberValue amount = new NumberValue("Amount", "Blur amount", 0.5, 0.0, 1.0, 0.05);

    public MotionBlur() {
        super(Category.Render, "MotionBlur", "Motion blur when turning.");
        addSetting(amount);
    }
}
