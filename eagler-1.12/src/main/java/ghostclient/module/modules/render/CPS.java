package ghostclient.module.modules.render;

import java.util.ArrayDeque;
import java.util.Deque;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Shows clicks per second (left and right).
 */
public class CPS extends Module {

    private final Deque<Integer> leftClicks = new ArrayDeque<>();
    private final Deque<Integer> rightClicks = new ArrayDeque<>();
    private boolean leftWasDown = false;
    private boolean rightWasDown = false;
    private int tickCounter = 0;

    public CPS() {
        super(Category.Render, "CPS", "Show clicks per second.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.gameSettings == null) return;
        tickCounter++;
        boolean leftDown = mc.gameSettings.keyBindAttack.isKeyDown();
        boolean rightDown = mc.gameSettings.keyBindUseItem.isKeyDown();
        if (leftDown && !leftWasDown) leftClicks.addLast(tickCounter);
        if (rightDown && !rightWasDown) rightClicks.addLast(tickCounter);
        leftWasDown = leftDown;
        rightWasDown = rightDown;

        while (!leftClicks.isEmpty() && tickCounter - leftClicks.peekFirst() > 20) leftClicks.removeFirst();
        while (!rightClicks.isEmpty() && tickCounter - rightClicks.peekFirst() > 20) rightClicks.removeFirst();
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null || mc.gameSettings == null) return;

        ScaledResolution sr = new ScaledResolution(mc);
        String text = "CPS: L " + leftClicks.size() + " R " + rightClicks.size();
        mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(text) - 4, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 4, 0xFFFFFFFF);
    }
}
