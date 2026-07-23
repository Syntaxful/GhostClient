package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiDownloadTerrain extends GuiScreen {
	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawRect(0, 0, this.width, this.height, 0xFF000000);
		String title = "GhostClient";
		String subtitle = "made by Syntaxful";
		String status = I18n.format("multiplayer.downloadingTerrain");
		int cx = this.width / 2;
		int cy = this.height / 2;

		this.drawCenteredString(this.fontRendererObj, title, cx, cy - 40, 0xFFFFFFFF);
		this.drawCenteredString(this.fontRendererObj, subtitle, cx, cy - 25, 0xFFAAAAAA);
		this.drawCenteredString(this.fontRendererObj, status, cx, cy + 48, 0xFFCCCCCC);

		long time = System.currentTimeMillis();
		int radius = 18;
		int segments = 24;
		float angle = (time % 2000L) / 2000.0f * 360.0f;
		for (int s = 0; s <= segments; s++) {
			float a = (float) Math.toRadians(angle + s * (360.0f / segments));
			int px = (int) (cx + Math.cos(a) * radius);
			int py = (int) (cy + Math.sin(a) * radius);
			int alpha = (int) (255 * (1.0f - (float) s / segments));
			int color = (alpha << 24) | 0xFFFFFF;
			this.drawRect(px, py, px + 2, py + 2, color);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	public boolean shouldHangupIntegratedServer() {
		return false;
	}
}
