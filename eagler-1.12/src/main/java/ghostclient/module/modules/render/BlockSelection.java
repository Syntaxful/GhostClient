package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import ghostclient.util.RenderUtils;
import net.minecraft.util.math.BlockPos;

/**
 * Customizes the block selection outline (width, color, fill).
 */
public class BlockSelection extends Module {

    private final NumberValue width = new NumberValue("Width",  "Outline width",    2.0, 0.5, 5.0, 0.5);
    private final BooleanValue fill  = new BooleanValue("Fill",   "Fill the box",     false);

    public BlockSelection() {
        super(Category.Render, "BlockSelection", "Custom block selection outline.");
        addSetting(width);
        addSetting(fill);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null || mc.world == null) return;
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        net.minecraft.util.math.AxisAlignedBB box = mc.world.getBlockState(pos).getBoundingBox(mc.world, pos).offset(pos.getX(), pos.getY(), pos.getZ());
        RenderUtils.drawBox(box, 0xFFFFFFFF, fill.getValue());
    }

    private int hsbToRgb(float h, float s, float v) {
        int r = 0, g = 0, b = 0;
        if (s == 0) {
            r = g = b = (int) (v * 255);
        } else {
            float hh = (h - (int) h) * 6f;
            float f = hh - (int) hh;
            float p = v * (1f - s);
            float q = v * (1f - s * f);
            float t = v * (1f - s * (1f - f));
            switch ((int) hh) {
                case 0: r = (int)(v * 255); g = (int)(t * 255); b = (int)(p * 255); break;
                case 1: r = (int)(q * 255); g = (int)(v * 255); b = (int)(p * 255); break;
                case 2: r = (int)(p * 255); g = (int)(v * 255); b = (int)(t * 255); break;
                case 3: r = (int)(p * 255); g = (int)(q * 255); b = (int)(v * 255); break;
                case 4: r = (int)(t * 255); g = (int)(p * 255); b = (int)(v * 255); break;
                case 5: r = (int)(v * 255); g = (int)(p * 255); b = (int)(q * 255); break;
            }
        }
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
