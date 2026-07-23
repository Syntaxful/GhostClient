package ghostclient.gui;

import java.util.Random;

import ghostclient.util.RenderUtils;
import net.lax1dude.eaglercraft.opengl.GlStateManager;

/**
 * Animated space backgrounds for the ClickGUI.
 * Modes: None, Stars, Warp, Nebula, Galaxy, Constellations, Void, Snow.
 */
public class BackgroundRenderer {

    private static final Random RANDOM = new Random();
    private static long lastTime = System.currentTimeMillis();
    private static float starOffset = 0f;
    private static float warpOffset = 0f;
    private static float snowOffset = 0f;
    private static float nebulaOffset = 0f;
    private static int lastWidth = 0;
    private static int lastHeight = 0;
    private static int[] starX;
    private static int[] starY;
    private static int[] starSize;
    private static int[] starSpeed;
    private static int[] snowX;
    private static int[] snowY;
    private static int[] snowSpeed;
    private static int[] constellationX;
    private static int[] constellationY;

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
        } else if ("Nebula".equals(mode)) {
            drawNebula(width, height, delta);
        } else if ("Galaxy".equals(mode)) {
            drawGalaxy(width, height, delta);
        } else if ("Constellations".equals(mode)) {
            drawConstellations(width, height, delta);
        } else if ("Void".equals(mode)) {
            drawVoid(width, height, delta);
        } else if ("Snow".equals(mode)) {
            drawSnow(width, height, delta);
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
            int count = Math.max(80, width * height / 15000);
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

    private static void ensureConstellations(int width, int height) {
        ensureStars(width, height);
        if (constellationX == null || constellationX.length != 60) {
            constellationX = new int[60];
            constellationY = new int[60];
            RANDOM.setSeed(999L);
            for (int i = 0; i < 60; i++) {
                constellationX[i] = RANDOM.nextInt(width);
                constellationY[i] = RANDOM.nextInt(height);
            }
        }
    }

    private static void ensureSnow(int width, int height) {
        if (snowX == null || width != lastWidth || height != lastHeight) {
            lastWidth = width;
            lastHeight = height;
            int count = Math.max(80, width * height / 15000);
            snowX = new int[count];
            snowY = new int[count];
            snowSpeed = new int[count];
            RANDOM.setSeed(77777L);
            for (int i = 0; i < count; i++) {
                snowX[i] = RANDOM.nextInt(width);
                snowY[i] = RANDOM.nextInt(height);
                snowSpeed[i] = 20 + RANDOM.nextInt(60);
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

    private static void drawNebula(int width, int height, float delta) {
        nebulaOffset = (nebulaOffset + delta * 8f) % 360f;
        int cx = width / 2;
        int cy = height / 2;
        for (int r = 0; r < 6; r++) {
            int radius = 80 + r * 70;
            int alpha = (int) (60 - r * 8);
            int color = (alpha << 24) | 0xFFFFFF;
            int x = (int) (cx + Math.cos(Math.toRadians(nebulaOffset + r * 60)) * radius * 0.3);
            int y = (int) (cy + Math.sin(Math.toRadians(nebulaOffset + r * 60)) * radius * 0.3);
            RenderUtils.drawCircle(x, y, radius, color, 40);
        }
        drawStars(width, height, delta);
    }

    private static void drawGalaxy(int width, int height, float delta) {
        int cx = width / 2;
        int cy = height / 2;
        int arms = 4;
        int points = 40;
        float time = (System.currentTimeMillis() % 20000) / 20000f;
        for (int a = 0; a < arms; a++) {
            double baseAngle = (a / (double) arms) * Math.PI * 2.0 + time * Math.PI * 2.0;
            for (int p = 0; p < points; p++) {
                double t = p / (double) points;
                double radius = t * Math.max(width, height) * 0.55;
                double angle = baseAngle + t * Math.PI * 1.5;
                int x = (int) (cx + Math.cos(angle) * radius);
                int y = (int) (cy + Math.sin(angle) * radius);
                if (x < 0 || x >= width || y < 0 || y >= height) continue;
                int alpha = (int) (180 * (1 - t));
                int color = (alpha << 24) | 0xFFFFFF;
                RenderUtils.drawRect(x, y, 2, 2, color);
            }
        }
    }

    private static void drawConstellations(int width, int height, float delta) {
        ensureConstellations(width, height);
        int threshold = 120;
        int lineColor = 0x33FFFFFF;
        for (int i = 0; i < constellationX.length; i++) {
            for (int j = i + 1; j < constellationX.length; j++) {
                int dx = constellationX[i] - constellationX[j];
                int dy = constellationY[i] - constellationY[j];
                int dist = (int) Math.sqrt(dx * dx + dy * dy);
                if (dist < threshold) {
                    RenderUtils.drawLine2D(constellationX[i], constellationY[i], constellationX[j], constellationY[j], lineColor, 1.0f);
                }
            }
            RenderUtils.drawRect(constellationX[i] - 1, constellationY[i] - 1, 2, 2, 0xFFFFFFFF);
        }
    }

    private static void drawVoid(int width, int height, float delta) {
        int cx = width / 2;
        int cy = height / 2;
        for (int i = 0; i < 20; i++) {
            int r = 30 + i * 25;
            int alpha = (int) (30 - i * 1.2);
            if (alpha < 0) alpha = 0;
            int color = (alpha << 24) | 0xFFFFFF;
            RenderUtils.drawCircle(cx, cy, r, color, 32);
        }
    }

    private static void drawSnow(int width, int height, float delta) {
        ensureSnow(width, height);
        snowOffset = (snowOffset + delta * 40f) % height;
        for (int i = 0; i < snowX.length; i++) {
            int y = (int) ((snowY[i] + snowOffset * (snowSpeed[i] / 30f)) % height);
            if (y < 0) y += height;
            int alpha = 0xCC;
            int color = (alpha << 24) | 0xFFFFFF;
            RenderUtils.drawRect(snowX[i], y, 2, 2, color);
        }
    }
}
