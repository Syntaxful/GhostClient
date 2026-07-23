package ghostclient.module.modules.render;

import java.util.ArrayDeque;
import java.util.Deque;

import ghostclient.event.EventHandler;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Shows clicks per second (left and right).
 */
public class CPS extends Module {

    private final Deque<Long> leftClicks = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();
    private boolean leftWasDown = false;
    private boolean rightWasDown = false;

    public CPS() {
        super(Category.Render, "CPS", "Show clicks per second.");
    }

    @EventHandler
    public void onTick(KeyInputEvent event) {
        // Use key state to detect actual mouse clicks, not raw key repeats
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null || mc.gameSettings == null) return;

        boolean leftDown = mc.gameSettings.keyBindAttack.isKeyDown();
        boolean rightDown = mc.gameSettings.keyBindUseItem.isKeyDown();
        long now = System.currentTimeMillis();
        if (leftDown && !leftWasDown) leftClicks.addLast(now);
        if (rightDown && !rightWasDown) rightClicks.addLast(now);
        leftWasDown = leftDown;
        rightWasDown = rightDown;

        while (!leftClicks.isEmpty() && now - leftClicks.peekFirst() > 1000L) leftClicks.removeFirst();
        while (!rightClicks.isEmpty() && now - rightClicks.peekFirst() > 1000L) rightClicks.removeFirst();

        ScaledResolution sr = new ScaledResolution(mc);
        String text = "CPS: L " + leftClicks.size() + " R " + rightClicks.size();
        mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(text) - 4, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 4, 0xFFFFFFFF);
    }
}
