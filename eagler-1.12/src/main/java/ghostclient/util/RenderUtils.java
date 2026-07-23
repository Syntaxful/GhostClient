package ghostclient.util;

import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.lax1dude.eaglercraft.opengl.RealOpenGLEnums;
import net.minecraft.client.renderer.Tessellator;
import net.lax1dude.eaglercraft.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * GhostClient rendering utility.
 */
public class RenderUtils {
    public static void renderTracer(double x, double y, double z, int color) {}

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void glColor(int color) {
        float a = ((color >> 24) & 0xFF) / 255.0F;
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        GlStateManager.color(r, g, b, a);
    }

    public static void drawRect(int x, int y, int w, int h, int color) {
        glColor(color);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x, y + h, 0).endVertex();
        buffer.pos(x + w, y + h, 0).endVertex();
        buffer.pos(x + w, y, 0).endVertex();
        buffer.pos(x, y, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawBorderedRect(int x, int y, int w, int h, int color, int borderColor, int borderWidth) {
        drawRect(x - borderWidth, y - borderWidth, w + borderWidth * 2, h + borderWidth * 2, borderColor);
        drawRect(x, y, w, h, color);
    }

    public static void drawLine2D(double x1, double y1, double x2, double y2, int color, float width) {
        glColor(color);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        net.lax1dude.eaglercraft.opengl.EaglercraftGPU.glLineWidth(width);
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(1, DefaultVertexFormats.POSITION);
        buffer.pos(x1, y1, 0).endVertex();
        buffer.pos(x2, y2, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawCircle(int cx, int cy, double radius, int color, int segments) {
        glColor(color);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(6, DefaultVertexFormats.POSITION);
        buffer.pos(cx, cy, 0).endVertex();
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            double x = cx + Math.cos(angle) * radius;
            double y = cy + Math.sin(angle) * radius;
            buffer.pos(x, y, 0).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawBox(AxisAlignedBB box, int color, boolean fill) {
        glColor(color);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();

        if (fill) {
            // Fill only the front face (simpler, works in TeaVM without complex geometry)
            buffer.begin(7, DefaultVertexFormats.POSITION);
            buffer.pos(box.minX, box.minY, box.minZ).endVertex();
            buffer.pos(box.maxX, box.minY, box.minZ).endVertex();
            buffer.pos(box.maxX, box.maxY, box.minZ).endVertex();
            buffer.pos(box.minX, box.maxY, box.minZ).endVertex();
            tessellator.draw();
        }

        // Always draw outline
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(box.minX, box.minY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).endVertex();
        tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static Vec3d getRenderPos(double x, double y, double z) {
        double renderX = x - mc.getRenderManager().viewerPosX;
        double renderY = y - mc.getRenderManager().viewerPosY;
        double renderZ = z - mc.getRenderManager().viewerPosZ;
        return new Vec3d(renderX, renderY, renderZ);
    }

    public static void drawPolyline(List<Vec3d> points, int color, float width) {
        if (points.size() < 2) return;
        glColor(color);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        net.lax1dude.eaglercraft.opengl.EaglercraftGPU.glLineWidth(width);
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        for (Vec3d p : points) {
            Vec3d r = getRenderPos(p.xCoord, p.yCoord, p.zCoord);
            buffer.pos(r.xCoord, r.yCoord, r.zCoord).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void prepare2D() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
                RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
    }

    public static void end2D() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
