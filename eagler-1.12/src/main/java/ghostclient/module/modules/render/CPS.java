package ghostclient.module.modules.render;

import java.util.ArrayDeque;
import java.util.Deque;

import ghostclient.event.EventHandler;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

public class CPS extends Module {

    private final Deque<Long> leftClicks = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();

    public CPS() {
        super(Category.Render, "CPS", "Show clicks per second.");
    }

    @EventHandler
    public void onKey(KeyInputEvent event) {
        if (!event.isPressed()) return;
        if (event.getKey() == mc.gameSettings.keyBindAttack.getKeyCode()) leftClicks.addLast(System.currentTimeMillis());
        if (event.getKey() == mc.gameSettings.keyBindUseItem.getKeyCode()) rightClicks.addLast(System.currentTimeMillis());
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null) return;
        long now = System.currentTimeMillis();
        while (!leftClicks.isEmpty() && now - leftClicks.peekFirst() > 1000L) leftClicks.removeFirst();
        while (!rightClicks.isEmpty() && now - rightClicks.peekFirst() > 1000L) rightClicks.removeFirst();
        ScaledResolution sr = new ScaledResolution(mc);
        String text = "CPS: L " + leftClicks.size() + " R " + rightClicks.size();
        mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(text) - 4, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 4, 0xFFFFFFFF);
    }
}