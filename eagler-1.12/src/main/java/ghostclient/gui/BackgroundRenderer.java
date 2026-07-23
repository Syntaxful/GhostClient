package ghostclient.gui;

import java.util.Random;

import ghostclient.util.RenderUtils;
import net.lax1dude.eaglercraft.opengl.GlStateManager;

/**
 * Animated space backgrounds for the ClickGUI.
 * Modes: None, Stars, Warp.
 */
public class BackgroundRenderer {

    private static final Random RANDOM = new Random();
    private static long lastTime = System.currentTimeMillis();
    private static float starOffset = 0f;
    private static float warpOffset = 0f;
    private static int lastWidth = 0;
    private static int lastHeight = 0;
    private static int[] starX;
    private static int[] starY;
    private static int[] starSize;
    private static int[] starSpeed;

    public static void draw(String mode, int width, int height) {
        if (mode == null || "None".equals(mode)) {
            return;
        }
        if (width <= 0 || height <= 0) {
            return;
        }

        long now = System.currentTimeMillis();
        float delta = Math.min((now - lastTime) / 1000f, 0.1f);
        lastTime = now;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        if ("Stars".equals(mode)) {
            drawStars(width, height, delta);
        } else if ("Warp".equals(mode)) {
            drawWarp(width, height, delta);
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void ensureStars(int width, int height) {
        if (starX == null || width != lastWidth || height != lastHeight) {
            lastWidth = width;
            lastHeight = height;
            int count = Math.max(50, width * height / 25000);
            starX = new int[count];
            starY = new int[count];
            starSize = new int[count];
            starSpeed = new int[count];
            RANDOM.setSeed(12345L);
            for (int i = 0; i < count; i++) {
                starX[i] = RANDOM.nextInt(width);
                starY[i] = RANDOM.nextInt(height);
                starSize[i] = 1 + RANDOM.nextInt(2);
                starSpeed[i] = 10 + RANDOM.nextInt(40);
            }
        }
    }

    private static void drawStars(int width, int height, float delta) {
        ensureStars(width, height);
        starOffset = (starOffset + delta * 30f) % height;
        for (int i = 0; i < starX.length; i++) {
            int y = (int) ((starY[i] + starOffset * (starSpeed[i] / 25f)) % height);
            if (y < 0) y += height;
            int alpha = 0xFF;
            if (starSize[i] == 1) {
                alpha = 0xAA;
            }
            int color = (alpha << 24) | 0xFFFFFF;
            RenderUtils.drawRect(starX[i], y, starSize[i], starSize[i], color);
        }
    }

    private static void drawWarp(int width, int height, float delta) {
        warpOffset = (warpOffset + delta * 150f) % 200f;
        int cx = width / 2;
        int cy = height / 2;
        RANDOM.setSeed(42L);
        int rays = 24;
        for (int i = 0; i < rays; i++) {
            double angle = (i / (double) rays) * Math.PI * 2.0;
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            int segments = 6;
            for (int j = 0; j < segments; j++) {
                double t1 = (j * 40 + warpOffset) % 200;
                double t2 = t1 + 20;
                if (t1 > 180) continue;
                int x1 = (int) (cx + dx * t1);
                int y1 = (int) (cy + dy * t1);
                int x2 = (int) (cx + dx * t2);
                int y2 = (int) (cy + dy * t2);
                int alpha = (int) (255 * (1 - t1 / 200.0));
                int color = (alpha << 24) | 0xFFFFFF;
                RenderUtils.drawLine2D(x1, y1, x2, y2, color, 1.5f);
            }
        }
    }
}
