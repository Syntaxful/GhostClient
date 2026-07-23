package net.minecraft.client.gui;

import java.io.IOException;

import net.minecraft.client.resources.I18n;

public class GuiScreenFirstLoad extends GuiScreen {

	private final GuiScreen mainMenu;

	public GuiScreenFirstLoad(GuiScreen mainMenu) {
		this.mainMenu = mainMenu;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 40, 200, 20,
				I18n.format("gui.continue")));
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawGhostClientBackground();

		int cx = this.width / 2;
		int cy = this.height / 2;
		drawCenteredString(this.fontRendererObj, "GhostClient", cx, cy - 30, 0xFFFFFFFF);
		drawCenteredString(this.fontRendererObj, "made by Syntaxful", cx, cy - 10, 0xFFAAAAAA);
		drawCenteredString(this.fontRendererObj, "Welcome to the client.", cx, cy + 10, 0xFFCCCCCC);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(this.mainMenu);
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
