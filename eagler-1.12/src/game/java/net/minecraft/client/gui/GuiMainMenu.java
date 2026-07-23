package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

import ghostclient.GhostClient;
import ghostclient.util.RenderUtils;
import net.lax1dude.eaglercraft.PauseMenuCustomizeState;
import net.lax1dude.eaglercraft.sp.SingleplayerServerController;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.HString;
import net.lax1dude.eaglercraft.Keyboard;
import net.lax1dude.eaglercraft.Mouse;
import net.lax1dude.eaglercraft.ServerQueryDispatch;
import net.lax1dude.eaglercraft.minecraft.GuiButtonWithStupidIcons;
import net.lax1dude.eaglercraft.notifications.GuiButtonNotifBell;
import net.lax1dude.eaglercraft.notifications.GuiScreenNotifications;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SessionUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

	private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
	private static final Random RANDOM = new Random();
	private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
	private String splashText = "missingno";
	private GuiButton buttonResetDemo;

	/** The Object utilized for the main menu's 3D background shader */
	private String openGLWarning1;
	private String openGLWarning2;
	private String openGLWarningLink;

	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;

	private GuiButton realmsButton;
	private GuiScreen modUpdateNotification;

	private GuiScreen achievementsScreen = null;

	public GuiMainMenu() {
		openGLWarning2 = "";
		openGLWarning1 = "";
		openGLWarningLink = "";

		SplashTextGeneratorThread th = new SplashTextGeneratorThread("Menu Splash Thread");
		th.setDaemon(true);
		th.start();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
			this.splashText = "Merry X-mas!";
		} else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
			this.splashText = "Happy new year!";
		} else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
			this.splashText = "OOoooOOOoooo! Spooky!";
		}
	}

	class SplashTextGeneratorThread extends Thread {
		public SplashTextGeneratorThread(String name) {
			super(name);
		}

		public void run() {
			BufferedReader bufferedreader = null;
			try {
				List<String> list = Lists.<String>newArrayList();
				bufferedreader = new BufferedReader(
						new InputStreamReader(mc.getResourceManager().getResource(SPLASH_TEXTS).getInputStream(),
							StandardCharsets.UTF_8));
				String str;

				while ((str = bufferedreader.readLine()) != null) {
					str = str.trim();
					if (!str.isEmpty()) {
						list.add(str);
					}
				}

				if (!list.isEmpty()) {
					GuiMainMenu.this.splashText = list.get(RANDOM.nextInt(list.size()));
				}
			} catch (IOException e) {
				;
			} finally {
				if (bufferedreader != null) {
					try {
						bufferedreader.close();
					} catch (IOException e) {
						;
					}
				}
			}
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	public boolean shouldHangupIntegratedServer() {
		return true;
	}

	public void updateScreen() {
		if (achievementsScreen != null) {
			mc.displayGuiScreen(achievementsScreen);
			achievementsScreen = null;
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.mc.gameSettings.enableServerRegistry = false;
		this.openGLWarning2 = "";

		int centerX = this.width / 2;
		int centerY = this.height / 2 + 10;
		int buttonWidth = 200;
		int buttonHeight = 20;
		int gap = 4;

		this.buttonList.add(new GuiButton(1, centerX - buttonWidth / 2, centerY, buttonWidth, buttonHeight,
				I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, centerX - buttonWidth / 2, centerY + buttonHeight + gap, buttonWidth,
				buttonHeight, I18n.format("menu.multiplayer")));
		this.buttonList.add(new GuiButton(6, centerX - buttonWidth / 2, centerY + (buttonHeight + gap) * 2, buttonWidth,
				buttonHeight, I18n.format("fml.menu.mods")));
		this.buttonList.add(new GuiButton(0, centerX - buttonWidth / 2, centerY + (buttonHeight + gap) * 3, buttonWidth,
				buttonHeight, I18n.format("menu.options")));
		this.buttonList.add(new GuiButton(4, centerX - buttonWidth / 2, centerY + (buttonHeight + gap) * 4, buttonWidth,
				buttonHeight, I18n.format("menu.quit")));

		this.buttonList.add(new GuiButtonWithStupidIcons(20, this.width - 24, 4, 20, 20, 0));
		this.buttonList.add(new GuiButtonNotifBell(21, this.width - 48, 4, 20, 20));
	}

	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		// handled in initGui
	}

	private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
		this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_,
				I18n.format("menu.resetdemo")));
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

		if (worldinfo == null) {
			this.buttonResetDemo.enabled = false;
		}
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 1) {
			if (this.mc.gameSettings.hudFps) {
				this.mc.displayGuiScreen(new GuiWorldSelection(this));
			} else {
				this.mc.displayGuiScreen(new GuiWorldSelection(this));
			}
		}

		if (button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (button.id == 4) {
			this.mc.shutdown();
		}

		if (button.id == 6) {
			this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
		}

		if (button.id == 11) {
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", new WorldSettings(new Random().nextLong(),
				GameType.SURVIVAL, false, true, WorldType.DEFAULT), false);
		}

		if (button.id == 12) {
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

			if (worldinfo != null) {
				GuiYesNo guiyesno = new GuiYesNo(this,
						I18n.format("selectWorld.deleteQuestion"),
						"'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"),
						12);
				this.mc.displayGuiScreen(guiyesno);
			}
		}

		if (button.id == 20) {
			this.mc.displayGuiScreen(new GuiScreenNotifications(this));
		}

		if (button.id == 21) {
			this.mc.displayGuiScreen(new GuiScreenNotifications(this));
		}
	}

	public void confirmClicked(boolean result, int id) {
		if (result && id == 12) {
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.flushCache();
			isaveformat.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		}
		if (id == 12) {
			this.mc.displayGuiScreen(this);
		}
	}

	private void switchToRealms() {
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(this);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawGhostClientBackground();

		int centerX = this.width / 2;
		int logoY = this.height / 2 - 80;

		// GhostClient logo (large text with a subtle shadow)
		String title = "GhostClient";
		String version = "1.12";
		String author = "made by Syntaxful";
		this.drawCenteredString(this.fontRendererObj, title, centerX, logoY, 0xFFFFFFFF);
		this.drawCenteredString(this.fontRendererObj, version, centerX + 80, logoY + 6, 0xFF888888);
		this.drawCenteredString(this.fontRendererObj, author, centerX, logoY + 16, 0xFFAAAAAA);

		// Splash text
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) (centerX + 90), (float) (logoY + 8), 0.0F);
		GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
		float f = 1.8F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
		f = f * 100.0F / (float) (this.fontRendererObj.getStringWidth(this.splashText) + 32);
		GlStateManager.scale(f, f, f);
		this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, 0xFFFFFFFF);
		GlStateManager.popMatrix();

		// Watermarks
		this.drawString(this.fontRendererObj, "GhostClient", 6, this.height - 12, 0xFF555555);
		this.drawString(this.fontRendererObj, "Minecraft 1.12.2", this.width - this.fontRendererObj.getStringWidth("Minecraft 1.12.2") - 6, this.height - 12, 0xFF555555);

		if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
			drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1,
					0x55200000);
			this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, 0xFFFFFF00);
			this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2,
					this.buttonList.get(0).yPosition - 12, 0xFFFFFFFF);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
			if (mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u
					&& mouseY <= this.field_92019_w) {
				if (!this.openGLWarningLink.isEmpty()) {
					EagRuntime.openLink(this.openGLWarningLink);
				}
			}
		}
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		this.mc.gameSettings.enableServerRegistry = true;
	}
}
