package net.minecraft.client;

import net.lax1dude.eaglercraft.opengl.WorldRenderer;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.lax1dude.eaglercraft.opengl.RealOpenGLEnums;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements IProgressUpdate {
	private String message = "";

	/** A reference to the Minecraft object. */
	private final Minecraft mc;

	/**
	 * The text currently displayed (i.e. the argument to the last call to printText
	 * or displayString)
	 */
	private String currentlyDisplayedText = "";

	/** The system's time represented in milliseconds. */
	private long systemTime = Minecraft.getSystemTime();

	/** True if the loading ended with a success */
	private boolean loadingSuccess;

	public LoadingScreenRenderer(Minecraft mcIn) {
		this.mc = mcIn;
	}

	/**
	 * this string, followed by "working..." and then the "% complete" are the 3
	 * lines shown. This resets progress to 0, and the WorkingString to
	 * "working...".
	 */
	public void resetProgressAndMessage(String message) {
		this.loadingSuccess = false;
		this.displayString(message);
	}

	/**
	 * Shows the 'Saving level' string.
	 */
	public void displaySavingString(String message) {
		this.loadingSuccess = true;
		this.displayString(message);
	}

	private void displayString(String message) {
		this.currentlyDisplayedText = message;

		if (!this.mc.running) {
			if (!this.loadingSuccess) {
				throw new MinecraftError();
			}
		} else {
			GlStateManager.clear(256);
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();

			GlStateManager.ortho(0.0D, mc.scaledResolution.getScaledWidth_double(),
					mc.scaledResolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);

			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.0F, 0.0F, -200.0F);
		}
	}

	/**
	 * Displays a string on the loading screen supposed to indicate what is being
	 * done currently.
	 */
	public void displayLoadingString(String message) {
		if (!this.mc.running) {
			if (!this.loadingSuccess) {
				throw new MinecraftError();
			}
		} else {
			this.systemTime = 0L;
			this.message = message;
			this.setLoadingProgress(-1);
			this.systemTime = 0L;
		}
	}

	/**
	 * Updates the progress bar on the loading screen to the specified amount.
	 * GhostClient: black screen, white branding and a rotating loading circle.
	 */
	public void setLoadingProgress(int progress) {
		if (!this.mc.running) {
			if (!this.loadingSuccess) {
				throw new MinecraftError();
			}
		} else {
			long i = Minecraft.getSystemTime();

			if (i - this.systemTime >= 100L) {
				this.systemTime = i;
				ScaledResolution scaledresolution = mc.scaledResolution;
				int k = scaledresolution.getScaledWidth();
				int l = scaledresolution.getScaledHeight();

				GlStateManager.clear(256);
				GlStateManager.matrixMode(5889);
				GlStateManager.loadIdentity();
				GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(),
						scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
				GlStateManager.matrixMode(5888);
				GlStateManager.loadIdentity();
				GlStateManager.translate(0.0F, 0.0F, -200.0F);
				GlStateManager.clear(16640);

				// Black background
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer bufferbuilder = tessellator.getBuffer();
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos(0.0D, (double) l, 0.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos((double) k, (double) l, 0.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos((double) k, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
				tessellator.draw();

				String title = "GhostClient";
				String subtitle = "made by Syntaxful";
				String status = this.currentlyDisplayedText.isEmpty() ? "Loading..." : this.currentlyDisplayedText;
				int cx = k / 2;
				int cy = l / 2;

				this.mc.fontRendererObj.drawStringWithShadow(title,
						(float) (cx - this.mc.fontRendererObj.getStringWidth(title) / 2),
						(float) (cy - 40), 0xFFFFFFFF);
				this.mc.fontRendererObj.drawStringWithShadow(subtitle,
						(float) (cx - this.mc.fontRendererObj.getStringWidth(subtitle) / 2),
						(float) (cy - 25), 0xFFAAAAAA);
				this.mc.fontRendererObj.drawStringWithShadow(status,
						(float) (cx - this.mc.fontRendererObj.getStringWidth(status) / 2),
						(float) (cy + 48), 0xFFCCCCCC);

				// Rotating loading circle
				int radius = 18;
				int segments = 24;
				float angle = (i % 2000L) / 2000.0F * 360.0F;
				GlStateManager.disableTexture2D();
				bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos((double) cx, (double) cy, 0.0D).color(255, 255, 255, 64).endVertex();
				for (int s = 0; s <= segments; s++) {
					float a = (float) Math.toRadians(angle + s * (360.0F / segments));
					int px = (int) (cx + Math.cos(a) * radius);
					int py = (int) (cy + Math.sin(a) * radius);
					int alpha = (int) (255 * (1.0F - (float) s / segments));
					bufferbuilder.pos((double) px, (double) py, 0.0D).color(255, 255, 255, alpha).endVertex();
				}
				tessellator.draw();
				GlStateManager.enableTexture2D();

				this.mc.updateDisplay();
			}
		}
	}

	public void setDoneWorking() {
	}

	public void eaglerShow(String line1, String line2) {
		if (!this.mc.running) {
			if (!this.loadingSuccess) {
				throw new MinecraftError();
			}
		} else {
			this.systemTime = 0L;
			this.currentlyDisplayedText = line1;
			this.message = line2;
			this.setLoadingProgress(-1);
			this.systemTime = 0L;
		}
	}
}
