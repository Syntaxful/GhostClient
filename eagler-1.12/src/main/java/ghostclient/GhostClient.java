package ghostclient;

import ghostclient.command.CommandManager;
import ghostclient.config.ConfigManager;
import ghostclient.event.EventBus;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.RenderEvent;
import ghostclient.event.TickEvent;
import ghostclient.module.Module;
import ghostclient.module.ModuleManager;
import ghostclient.module.modules.combat.AimAssist;
import ghostclient.module.modules.combat.AutoLog;
import ghostclient.module.modules.combat.AutoArmor;
import ghostclient.module.modules.combat.AutoSpleef;
import ghostclient.module.modules.combat.AutoClicker;
import ghostclient.module.modules.combat.AutoSword;
import ghostclient.module.modules.combat.AutoTotem;
import ghostclient.module.modules.combat.BedAura;
import ghostclient.module.modules.combat.BowAimbot;
import ghostclient.module.modules.combat.Criticals;
import ghostclient.module.modules.combat.Hitboxes;
import ghostclient.module.modules.combat.KillAura;
import ghostclient.module.modules.combat.Offhand;
import ghostclient.module.modules.combat.Surround;
import ghostclient.module.modules.combat.TriggerBot;
import ghostclient.module.modules.combat.Velocity;
import ghostclient.module.modules.combat.WTap;
import ghostclient.module.modules.misc.AntiPacketKick;
import ghostclient.module.modules.misc.NoAnimations;
import ghostclient.module.modules.misc.AutoCraft;
import ghostclient.module.modules.misc.AutoRespawn;
import ghostclient.module.modules.misc.BetterChat;
import ghostclient.module.modules.misc.FakePlayer;
import ghostclient.module.modules.misc.NameProtect;
import ghostclient.module.modules.misc.PacketCanceller;
import ghostclient.module.modules.misc.Panic;
import ghostclient.module.modules.misc.Spammer;
import ghostclient.module.modules.misc.WindowTitle;
import ghostclient.module.modules.movement.AirJump;
import ghostclient.module.modules.movement.AntiLevitation;
import ghostclient.module.modules.movement.AutoWalk;
import ghostclient.module.modules.movement.Blink;
import ghostclient.module.modules.movement.BoatFly;
import ghostclient.module.modules.movement.ElytraFly;
import ghostclient.module.modules.movement.EntitySpeed;
import ghostclient.module.modules.movement.FastClimb;
import ghostclient.module.modules.movement.Fly;
import ghostclient.module.modules.movement.HighJump;
import ghostclient.module.modules.movement.IceSpeed;
import ghostclient.module.modules.movement.WaterWalker;
import ghostclient.module.modules.movement.LongJump;
import ghostclient.module.modules.movement.NoSlow;
import ghostclient.module.modules.movement.Parkour;
import ghostclient.module.modules.movement.SafeWalk;
import ghostclient.module.modules.movement.Sneak;
import ghostclient.module.modules.movement.Speed;
import ghostclient.module.modules.movement.Sprint;
import ghostclient.module.modules.movement.Step;
import ghostclient.module.modules.movement.Timer;
import ghostclient.module.modules.player.AntiAFK;
import ghostclient.module.modules.player.AntiHunger;
import ghostclient.module.modules.player.AutoEat;
import ghostclient.module.modules.player.AutoFish;
import ghostclient.module.modules.player.AutoTool;
import ghostclient.module.modules.player.FastElytra;
import ghostclient.module.modules.player.NoFall;
import ghostclient.module.modules.player.Reach;
import ghostclient.module.modules.player.SilentMine;
import ghostclient.module.modules.player.SpeedMine;
import ghostclient.module.modules.render.AntiBlind;
import ghostclient.module.modules.render.Armour;
import ghostclient.module.modules.render.BlockSelection;
import ghostclient.module.modules.render.CPS;
import ghostclient.module.modules.render.Breadcrumbs;
import ghostclient.module.modules.render.CameraClip;
import ghostclient.module.modules.render.ChestESP;
import ghostclient.module.modules.render.Coordinates;
import ghostclient.module.modules.render.ESP;
import ghostclient.module.modules.render.FPS;
import ghostclient.module.modules.render.FovModifier;
import ghostclient.module.modules.render.Freecam;
import ghostclient.module.modules.render.Keystrokes;
import ghostclient.module.modules.render.NoFog;
import ghostclient.module.modules.render.Fullbright;
import ghostclient.module.modules.render.HUD;
import ghostclient.module.modules.render.HoleESP;
import ghostclient.module.modules.render.LowFire;
import ghostclient.module.modules.render.MotionBlur;
import ghostclient.module.modules.render.Nametags;
import ghostclient.module.modules.render.NoHurtCam;
import ghostclient.module.modules.render.NoRender;
import ghostclient.module.modules.render.PotionEffects;
import ghostclient.module.modules.render.NoWeather;
import ghostclient.module.modules.render.Search;
import ghostclient.module.modules.render.Speedometer;
import ghostclient.module.modules.render.StorageESP;
import ghostclient.module.modules.render.TimeChanger;
import ghostclient.module.modules.render.ToggleSprint;
import ghostclient.module.modules.render.Tracers;
import ghostclient.module.modules.render.Trail;
import ghostclient.module.modules.render.Trajectories;
import ghostclient.module.modules.render.UnfocusedCPU;
import ghostclient.module.modules.render.ViewModel;
import ghostclient.module.modules.render.VoidESP;
import ghostclient.module.modules.render.Waypoints;
import ghostclient.module.modules.render.XRay;
import ghostclient.module.modules.render.Zoom;
import ghostclient.module.modules.world.FastBreak;
import ghostclient.module.modules.world.FastPlace;
import ghostclient.module.modules.world.Nuker;
import ghostclient.module.modules.world.Scaffold;
import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.Minecraft;

/**
 * GhostClient main class.
 */
public class GhostClient {

    public static final String NAME = "GhostClient";
    public static final String VERSION = "1.0.0";
    public static final String CREDIT = "made by Syntaxful";

    public static final EventBus EVENT_BUS = new EventBus();
    public static final ModuleManager MODULES = new ModuleManager();

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        System.out.println("[" + NAME + "] " + CREDIT + " - " + VERSION);

        registerModules();
        CommandManager.init();
        ConfigManager.load();
        Module hud = MODULES.getByName("ArrayList");
        if (hud != null) hud.setEnabled(true);

        // Ensure ClickGUI is always bound to RSHIFT and enabled so the keybind never silently breaks
        Module clickGui = MODULES.getByName("ClickGUI");
        if (clickGui != null) {
            clickGui.setEnabled(true);
            clickGui.setKeybind(KeyboardConstants.KEY_RSHIFT);
        }
    }

    private static void registerModules() {
        // Combat (16)
        MODULES.register(new AimAssist());
        MODULES.register(new AutoLog());
        MODULES.register(new AutoArmor());
        MODULES.register(new AutoSpleef());
        MODULES.register(new AutoClicker());
        MODULES.register(new AutoSword());
        MODULES.register(new AutoTotem());
        MODULES.register(new BedAura());
        MODULES.register(new BowAimbot());
        MODULES.register(new Criticals());
        MODULES.register(new Hitboxes());
        MODULES.register(new KillAura());
        MODULES.register(new Offhand());
        MODULES.register(new Surround());
        MODULES.register(new TriggerBot());
        MODULES.register(new Velocity());
        MODULES.register(new WTap());

        // Movement (20)
        MODULES.register(new AirJump());
        MODULES.register(new AntiLevitation());
        MODULES.register(new AutoWalk());
        MODULES.register(new Blink());
        MODULES.register(new BoatFly());
        MODULES.register(new ElytraFly());
        MODULES.register(new EntitySpeed());
        MODULES.register(new FastClimb());
        MODULES.register(new Fly());
        MODULES.register(new HighJump());
        MODULES.register(new IceSpeed());
        MODULES.register(new WaterWalker());
        MODULES.register(new LongJump());
        MODULES.register(new NoSlow());
        MODULES.register(new Parkour());
        MODULES.register(new SafeWalk());
        MODULES.register(new Sneak());
        MODULES.register(new Speed());
        MODULES.register(new Sprint());
        MODULES.register(new Step());
        MODULES.register(new Timer());

        // Player (10)
        MODULES.register(new AntiAFK());
        MODULES.register(new AntiHunger());
        MODULES.register(new AutoEat());
        MODULES.register(new AutoFish());
        MODULES.register(new AutoTool());
        MODULES.register(new FastElytra());
        MODULES.register(new NoFall());
        MODULES.register(new Reach());
        MODULES.register(new SilentMine());
        MODULES.register(new SpeedMine());

        // World (4)
        MODULES.register(new FastBreak());
        MODULES.register(new FastPlace());
        MODULES.register(new Nuker());
        MODULES.register(new Scaffold());

        // Render (31)
        MODULES.register(new BlockSelection());
        MODULES.register(new Breadcrumbs());
        MODULES.register(new CameraClip());
        MODULES.register(new ChestESP());
        MODULES.register(new Coordinates());
        MODULES.register(new ESP());
        MODULES.register(new FPS());
        MODULES.register(new FovModifier());
        MODULES.register(new Speedometer());
        MODULES.register(new CPS());
        MODULES.register(new NoFog());
        MODULES.register(new AntiBlind());
        MODULES.register(new NoRender());
        MODULES.register(new PotionEffects());
        MODULES.register(new Armour());
        MODULES.register(new Keystrokes());
        MODULES.register(new Freecam());
        MODULES.register(new Fullbright());
        MODULES.register(new HUD());
        MODULES.register(new HoleESP());
        MODULES.register(new LowFire());
        MODULES.register(new MotionBlur());
        MODULES.register(new Nametags());
        MODULES.register(new NoHurtCam());
        MODULES.register(new NoWeather());
        MODULES.register(new Search());
        MODULES.register(new StorageESP());
        MODULES.register(new TimeChanger());
        MODULES.register(new ToggleSprint());
        MODULES.register(new Tracers());
        MODULES.register(new Trail());
        MODULES.register(new Trajectories());
        MODULES.register(new UnfocusedCPU());
        MODULES.register(new ViewModel());
        MODULES.register(new VoidESP());
        MODULES.register(new Waypoints());
        MODULES.register(new XRay());
        MODULES.register(new Zoom());

        // Misc (11)
        MODULES.register(new ghostclient.module.modules.misc.ClickGUIModule());
        MODULES.register(new AntiPacketKick());
        MODULES.register(new AutoCraft());
        MODULES.register(new NoAnimations());
        MODULES.register(new AutoRespawn());
        MODULES.register(new BetterChat());
        MODULES.register(new FakePlayer());
        MODULES.register(new NameProtect());
        MODULES.register(new PacketCanceller());
        MODULES.register(new Panic());
        MODULES.register(new Spammer());
        MODULES.register(new WindowTitle());
    }

    public static void onTickPre() {
        EVENT_BUS.post(new TickEvent.Pre());
    }

    public static void onTickPost() {
        EVENT_BUS.post(new TickEvent.Post());
    }

    public static void onRenderPre(float partialTicks) {
        EVENT_BUS.post(new RenderEvent.Pre(partialTicks));
    }

    public static void onRenderPost(float partialTicks) {
        EVENT_BUS.post(new RenderEvent.Post(partialTicks));
    }

    public static void onKeyInput(int key, boolean pressed) {
        KeyInputEvent event = new KeyInputEvent(key, pressed);
        EVENT_BUS.post(event);

        if (event.isCancelled()) {
            return;
        }

        if (pressed && Minecraft.getMinecraft().currentScreen == null) {
            for (Module module : MODULES.getAll()) {
                if (module.getKeybind() == key) {
                    // ClickGUI handles its own keybind; don't toggle it
                    if ("ClickGUI".equals(module.getName())) {
                        continue;
                    }
                    module.toggle();
                }
            }
        }
    }
}
