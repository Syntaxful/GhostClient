# GhostClient 1.12 — Development TODO

> A performance-first, utility, and combat client for **Eaglercraft 1.12.2**.
> Maintained by Syntaxful.
>
> **Transfer status:** Transferred to Syntaxful and branded as **GhostClient 1.12** for Eaglercraft 1.12.2.
> All PlxeXyzz references replaced. Module source classes are present; build verification pending.

## Transfer & 1.12 Branding Checklist
- [x] Transfer ownership from PlxeXyzz to Syntaxful
- [x] Replace all PlxeXyzz references with Syntaxful
- [x] Keep GhostClient project name and identity
- [x] Label as GhostClient 1.12 for Eaglercraft 1.12.2
- [x] Update README.md and todo.md with new ownership and 1.12 branding
- [x] Push updated repo to GitHub
- [x] Delete old PlxeXyzz/GhostClient repo

## Legend
- `[ ]` — not started
- `[~]` — in progress
- `[x]` — done
- `[!]` — blocked / needs Java/TeaVM environment

---

## Phase 0 — Foundation & Source Patches
- [x] Define brand identity, color palette, and UI rules
- [x] Create project plan and architecture
- [x] Build ClickGUI mockup with custom modules, toggles, sliders, and modes
- [x] Create server list, player profile, and settings mockups
- [ ] Set up Java/TeaVM build environment (Eclipse / IntelliJ / Gradle)
- [x] Create `src/main/java/ghostclient/` package structure
- [x] Implement lightweight event bus (`EventBus`, `Event`, `@EventHandler`)
- [x] Define core events: `TickEvent`, `Render2DEvent`, `Render3DEvent`, `PacketEvent`, `PlayerMoveEvent`, `AttackEntityEvent`, `KeyInputEvent`
- [x] Patch `Minecraft.java` with `// GHOSTCLIENT HOOK` initialization and tick calls
- [ ] Patch `EntityRenderer.java` for 2D/3D render events and FOV changes
- [x] Patch `GuiIngame.java` for HUD render and overlay hiding
- [ ] Patch `EntityPlayerSP.java` for movement hooks
- [ ] Patch `NetHandlerPlayClient.java` for packet events
- [ ] Patch `PlayerControllerMP.java` for attack/block events
- [ ] Patch `WorldClient.java` for world tick events
- [x] Patch `GuiMainMenu.java` and `GuiIngameMenu.java` for GhostClient branding
- [x] Implement `Module`, `ModuleManager`, `Category`, and typed settings system
- [x] Implement JSON `ConfigManager` for save/load
- [x] Implement command system (`.toggle`, `.bind`, `.help`, `.config`, `.panic`)

## Phase 1 — Core Performance & UI
- [x] `FPS` module (dynamic render distance, particle reduction)
- [x] `NoAnimations` module
- [ ] `FastRender` module
- [x] `DynamicFOV` module
- [x] `Fullbright` module
- [x] `NoFog` module
- [x] `NoWeather` module
- [ ] `SmoothLighting` toggle module
- [ ] `EntityCulling` module
- [ ] `MenuBlur` module
- [x] `MotionBlur` module (default off)
- [x] `ClickGUI` module with keybind
- [ ] `HUDEditor` module with keybind
- [x] `ArrayList` HUD module (toggleable, right-side, length-sorted)
- [x] `FPS` counter HUD element
- [x] `Coordinates` HUD element
- [x] `Speedometer` HUD element
- [x] `CPS` HUD element

## Phase 2 — Utility Modules
- [ ] `Freelook` module
- [x] `Zoom` module
- [x] `PotionEffects` HUD element
- [x] `Armour` HUD element
- [ ] `TabList` improvements
- [x] `Chat` improvements
- [ ] `ItemPhysics` module
- [x] `Keystrokes` HUD element
- [ ] `Crosshair` editor module
- [x] `Timers` HUD element
- [x] `AutoTool` module
- [x] `AutoSprint` module
- [x] `AutoRespawn` module
- [x] `AutoArmor` module
- [x] `AntiBlind` module
- [x] `NoRender` module (fire, portal, overlay hiding)
- [x] `Freecam` module
- [x] `XRay` module with ore selection
- [x] `Search` module
- [x] `Trajectories` module
- [x] `Waypoints` module
- [ ] `LightOverlay` module

## Phase 3 — Combat Modules
- [x] `KillAura` module
- [x] `TriggerBot` module
- [x] `AimAssist` module
- [x] `AutoLog` module
- [ ] `Aimbot` module
- [x] `Criticals` module
- [x] `Velocity` module
- [x] `AutoTotem` module
- [ ] `AutoLog` module
- [x] `AutoWeapon` module
- [x] `Hitboxes` module
- [x] `Reach` module
- [x] `Surround` module
- [ ] `SelfTrap` module
- [x] `BowAimbot` module

## Phase 4 — World & Movement Modules
- [x] `Scaffold` module
- [x] `SafeWalk` module
- [x] `AutoWalk` module
- [x] `Parkour` module
- [x] `Speed` module
- [x] `Flight` module
- [x] `Jesus` module
- [x] `Step` module
- [ ] `Spider` module
- [x] `NoFall` module
- [x] `Nuker` module
- [x] `Timer` module
- [x] `FastPlace` module
- [x] `AutoFish` module
- [ ] `AutoFarm` module

## Phase 5 — Polish & Branding
- [ ] Replace title screen logo with GhostClient ghost logo
- [x] Add "made by Syntaxful" watermark on main menu
- [ ] Custom hotbar / texture pack style (black/white/purple)
- [ ] Final ClickGUI polish and animations
- [ ] Config profiles (PvP, Safe, Utility, Default)
- [ ] Safe Profile preset (disables risky anti-cheat modules)
- [x] One-command build scripts (`build.sh` → `Game/` folder with self-contained `GhostClientJS.html`, `GhostClientWASM.html`, `GhostClientOffline_US.html`, `GhostClientOffline_International.html`)
- [ ] Desktop window icon set (16/32/64/128/256/512) — only 16/32 currently present in desktopRuntime/

## Phase 6 — Documentation & Release
- [x] Write `README.md` with build instructions and feature list
- [x] Write `CONTRIBUTING.md` (solo project — owner only)
- [ ] Set up CI/CD for web build (optional)
- [ ] Test on multiple Eagler servers
- [ ] Tag first release `v1.0.0`

---

## UI/UX Checklist
- [x] Custom buttons with hover states, glow, and transitions
- [x] Custom toggle switches
- [x] Custom sliders with ghost accent
- [x] Custom mode selector pills
- [x] Server list with add/remove/favorite/connect
- [x] Player profile with username, skin model, and cape selection
- [x] Settings panel with theme/language/HUD toggles
- [x] Remove singleplayer references
- [x] Dark/black/white/purple color palette
- [x] No emojis in UI
- [ ] Final HUD Editor drag-and-drop implementation
- [x] Animated menu transitions

## Project State
- **Transfer & rebrand:** Complete. GhostClient 1.12 for Eaglercraft 1.12.2, owned by Syntaxful.
- **Core client package:** Complete. Event bus, module system, settings, and 75+ module classes exist.
- **Build system:** Complete. `./build.sh` runs successfully and produces self-contained HTML files in `Game/`.
- **Self-contained HTML:** Complete. `GhostClientJS.html`, `GhostClientJS_International.html`, and `GhostClientWASM.html` work offline or from any host without an HTTP server. Server-only variants are also provided in `Game/server/`.
- **Vanilla hooks:** Partial. `Minecraft.java` and `GuiIngame.java` are patched. `EntityRenderer`, `EntityPlayerSP`, `NetHandlerPlayClient`, `PlayerControllerMP`, `WorldClient`, `GuiMainMenu`, and `GuiIngameMenu` still need `// GHOSTCLIENT HOOK` patches.
- **Persistence:** Complete. JSON `ConfigManager` saves/loads modules, keybinds, and settings via `EagRuntime` storage.
- **Commands:** Complete. `.toggle`, `.bind`, `.help`, `.config`, and `.panic` commands are registered and dispatched from chat.
- **Asset / UI polish:** Partial. Mockups and ClickGUI exist. Full HUD Editor, animated menu transitions, and full icon set are missing.

## Next Steps (Priority Order)
1. Patch remaining vanilla classes (`EntityRenderer`, `EntityPlayerSP`, `NetHandlerPlayClient`, `PlayerControllerMP`, `WorldClient`) with `// GHOSTCLIENT HOOK` calls.
2. Add `GuiMainMenu` and `GuiIngameMenu` branding patches for the GhostClient 1.12 watermark.
3. Implement `ConfigManager` JSON save/load.
4. Implement command system (`.toggle`, `.bind`, `.help`, `.config`).
5. Add remaining modules: `NoAnimations`, `FastRender`, `NoFog`, `EntityCulling`, `MenuBlur`, `HUDEditor`, `ArrayList`, `Speedometer`, `CPS`, `Freelook`, `PotionEffects`, `Armour`, `TabList`, `ItemPhysics`, `Keystrokes`, `Crosshair`, `AntiBlind`, `NoRender`, `LightOverlay`, `Aimbot`, `AutoLog`, `SelfTrap`, `Spider`, `AutoFarm`.

## Module Inventory
Registered modules by category (current source):
- **Combat** — 16: AimAssist, AutoArmor, AutoCity, AutoClicker, AutoSword, AutoTotem, BedAura, BowAimbot, Criticals, Hitboxes, KillAura, Offhand, Surround, TriggerBot, Velocity, WTap
- **Movement** — 20: AirJump, AntiLevitation, AutoWalk, Blink, BoatFly, ElytraFly, EntitySpeed, FastClimb, Fly, HighJump, IceSpeed, Jesus, LongJump, NoSlow, Parkour, SafeWalk, Sneak, Speed, Sprint, Step, Timer
- **Player** — 14: AntiAFK, AntiHunger, AutoEat, AutoFish, AutoTool, ChestSwap, FastUse, InventoryMove, NoFall, NoRotate, PotionSpoof, Reach, SilentMine, SpeedMine
- **World** — 4: FastBreak, FastPlace, Nuker, Scaffold
- **Render** — 32: BlockSelection, BossStack, Breadcrumbs, CameraClip, ChestESP, Coordinates, ESP, FPS, FovModifier, Freecam, Fullbright, HUD, HoleESP, LowFire, MotionBlur, Nametags, NoHurtCam, NoWeather, Search, StorageESP, TimeChanger, ToggleSprint, Tracers, Trail, Trajectories, UnfocusedCPU, ViewModel, VoidESP, Waypoints, XRay, Zoom
- **Misc** — 13: ClickGUIModule, AntiPacketKick, AutoCraft, AutoReconnect, AutoRespawn, BetterChat, FakePlayer, NameProtect, Notebot, PacketCanceller, Panic, Spammer, WindowTitle
- **Total registered:** 99 modules

## Hook Reference
Existing `// GHOSTCLIENT HOOK` calls in vanilla source:
- `Minecraft.java`: `init()`, `onTickPre()`, `onTickPost()`, `onKeyInput(key, pressed)`
- `GuiIngame.java`: `onRenderPre(partialTicks)`, `onRenderPost(partialTicks)`

Still needed:
- `EntityRenderer.java`: `onRender3D(partialTicks)` (world-space overlay), `getFOVModifier` hook for DynamicFOV
- `EntityPlayerSP.java`: `onMove`, `onUpdateWalkingPlayer` (movement / MotionUpdateEvent)
- `NetHandlerPlayClient.java`: `onPacketReceive(packet)`, `onPacketSend(packet)` (PacketEvent)
- `PlayerControllerMP.java`: `attackEntity`, `clickBlock` hooks (AttackEntityEvent)
- `WorldClient.java`: `onWorldTick` (world tick events)
- `GuiMainMenu.java` / `GuiIngameMenu.java`: draw watermark and title-screen branding

## Detailed Implementation Notes for Missing Core Systems

### ConfigManager
- Save location: `eagler-1.12/config/` or `localStorage` for web builds.
- Store module enabled states, keybinds, and per-module setting values.
- JSON schema suggestion:
  ```json
  {
    "version": "1.0.0",
    "enabled": ["HUD", "Sprint"],
    "keybinds": {"ClickGUI": 54},
    "settings": {"KillAura": {"Range": 4.5}}
  }
  ```
- Load on `GhostClient.init()` and save on module toggle / setting change.

### Command System
- Prefix `.`, parsed in chat input or console.
- Minimum commands: `.toggle <module>`, `.bind <module> <key>`, `.help`, `.config save/load`, `.panic`.
- Add a `Command` base class and `CommandManager` registered in `GhostClient.init()`.

### Missing Patch Details
- `EntityRenderer.java`: Post `RenderEvent.3D` around `renderWorldPass` and override `getFOVModifier` for DynamicFOV.
- `EntityPlayerSP.java`: Post `MotionUpdateEvent` at the start of `onUpdateWalkingPlayer`; call movement-module hooks.
- `NetHandlerPlayClient.java`: Post `PacketEvent.Incoming` / `PacketEvent.Outgoing` around `processPacket` / `sendPacket`.
- `PlayerControllerMP.java`: Post `AttackEntityEvent` in `attackEntity` and `PlayerInteractEvent` in `clickBlock` / `onPlayerDamageBlock`.
- `WorldClient.java`: Post `WorldTickEvent` at the end of `updateEntities`.

## Build Verification Checklist
- [x] `./build.sh` runs without errors in a local Java/TeaVM environment.
- [x] `Game/GhostClientJS.html` launches and displays the main menu (self-contained, no HTTP server required).
- [x] `Game/GhostClientWASM.html` launches and displays the main menu (self-contained, no HTTP server required).
- [x] Offline clients (`GhostClientOffline_US.html`, `GhostClientOffline_International.html`) launch offline.
- [x] ClickGUI opens with the configured keybind and shows all modules.
- [ ] At least one module toggles on/off and shows visible behavior (e.g., Fullbright, Sprint).
- [ ] No crashes in browser console or server logs.

## Where Things Live
| What | Path |
|------|------|
| Client entry point | `eagler-1.12/src/main/java/ghostclient/GhostClient.java` |
| Module registration | `eagler-1.12/src/main/java/ghostclient/GhostClient.java` (registerModules) |
| Module base classes | `eagler-1.12/src/main/java/ghostclient/module/` |
| Combat modules | `eagler-1.12/src/main/java/ghostclient/module/modules/combat/` |
| Movement modules | `eagler-1.12/src/main/java/ghostclient/module/modules/movement/` |
| Player modules | `eagler-1.12/src/main/java/ghostclient/module/modules/player/` |
| Render modules | `eagler-1.12/src/main/java/ghostclient/module/modules/render/` |
| Utility / misc modules | `eagler-1.12/src/main/java/ghostclient/module/modules/misc/` |
| World modules | `eagler-1.12/src/main/java/ghostclient/module/modules/world/` |
| Event bus | `eagler-1.12/src/main/java/ghostclient/event/` |
| Settings / values | `eagler-1.12/src/main/java/ghostclient/setting/` |
| ClickGUI | `eagler-1.12/src/main/java/ghostclient/gui/ClickGUI.java` |
| Vanilla patches | `eagler-1.12/src/game/java/net/minecraft/client/` |
| ClickGUI mockup | `artifacts/mockup-sandbox/src/components/mockups/ghostclient-clickgui/ClickGUI.tsx` |
| Build script | `build.sh` |
| Eaglercraft source | `eagler-1.12/` |

## Quick Reference
- **Build client:** `./build.sh`
- **Serve output:** `npx serve Game` or `python3 -m http.server 8080 --directory Game`
- **Run mockup preview:** `pnpm install && pnpm --filter @workspace/mockup-sandbox run dev`
- **Regenerate API client (if OpenAPI changes):** `pnpm --filter @workspace/api-spec run codegen`
- **Default ClickGUI keybind:** RSHIFT (set in `ClickGUIModule.java`)

## Missing Module Backlog
Implementation hints for the modules still pending:
- **NoAnimations** — Disable `EntityLivingBase` limb swing / bobbing and reduce block animation updates.
- **FastRender** — Lower render quality: disable VBOs, fast clouds, minimal particles, reduce chunk updates.
- **NoFog** — Force `FogRenderer` to skip all fog density calculations (override to 1.0 visibility).
- **EntityCulling** — Skip rendering entities outside the camera frustum or occluded by blocks.
- **MenuBlur** — Post-process the background framebuffer with a Gaussian blur when `GuiScreen` is open.
- **HUDEditor** — Draggable screen that lets the user reposition HUD elements; save positions in config.
- **Speedometer** — HUD element showing `player.motionXZ` blocks per second (BPS).
- **CPS** — Track left/right click counts in a rolling 1-second window and display on HUD.
- **Freelook** — Detach camera rotation from player rotation while holding a keybind; restore on release.
- **PotionEffects** — HUD element listing active `PotionEffect`s with duration and amplifier.
- **Armour** — HUD element showing durability of worn armor pieces and equipped item.
- **ItemPhysics** — Replace flat item rendering with a rotating 3D physics entity (override `RenderEntityItem`).
- **Keystrokes** — HUD element displaying W/A/S/D, L/R mouse, space, and sprint keys with press feedback.
- **AntiBlind** — Cancel the blindness and nausea potion shaders in `EntityRenderer`.
- **NoRender** — Hide fire, portal, pumpkin, and boss overlays in `GuiIngame`/`EntityRenderer`.
- **LightOverlay** — Render a grid on blocks showing light level 0–7 (spawn-danger indicator).
- **Aimbot** — Simpler than AimAssist: snap crosshair to nearest target head/body on attack.
- **AutoLog** — Monitor `player.health`; disconnect if below a threshold and no totem in offhand.
- **SelfTrap** — Place obsidian around the player feet/head when enabled (watch for block placement range).
- **Spider** — Allow climbing up vertical walls by setting `isCollidedHorizontally` climb behavior.

## Module Authoring Guide
To add a new module:
1. Create a class in `eagler-1.12/src/main/java/ghostclient/module/modules/<category>/`.
2. Extend `ghostclient.module.Module` and call `super(Category, "Name", "Description")` in the constructor.
3. Add settings with `addSetting(new NumberValue(...))`, `BooleanValue`, or `ModeValue`.
4. Implement behavior using one or more of these lifecycle methods:
   - `@EventHandler public void onTick(TickEvent.Post event)` — runs every tick.
   - `@Override public void onEnable()` / `onDisable()` — setup / cleanup when toggled.
   - `public void onRender2D(...)` or `public void onRender3D(...)` — called via render events.
5. Register the module in `GhostClient.registerModules()` with `MODULES.register(new YourModule())`.
6. If the module needs a new vanilla hook (e.g., packet receive), add a `// GHOSTCLIENT HOOK` call in the appropriate vanilla class and handle it in `GhostClient.java`.

### Minimal Module Template
```java
package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

public class ExampleModule extends Module {
    public ExampleModule() {
        super(Category.Render, "ExampleModule", "Description of what this module does.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        // Module logic here.
    }
}
```

### Settings Types Reference
- `BooleanValue(name, description, default)` — on/off toggle.
  - `value.getValue()` returns `Boolean`; `value.toggle()` flips the state.
- `NumberValue(name, description, default, min, max, step)` — slider with min/max/step.
  - `value.getValue()` returns `Double`; `value.getInt()` and `value.getFloat()` cast for convenience.
- `ModeValue(name, description, default, mode1, mode2, ...)` — cyclic dropdown.
  - `value.getValue()` returns the current mode string; `value.getModes()` lists all options; `value.cycle()` advances to the next mode.

## Testing Status
- **Build:** Verified. `./build.sh` completes successfully and produces `Game/` outputs.
- **HTML self-containment:** Verified. `Game/GhostClientJS.html`, `Game/GhostClientJS_International.html`, and `Game/GhostClientWASM.html` have no external `src`/`href` references and no `file://` alert blocks.
- **In-game / runtime:** Pending. Requires a browser + Eagler server to verify the main menu loads, ClickGUI opens, and modules toggle correctly. This cannot be validated in the local development environment alone.
- **Recommended test steps:**
  1. Open `Game/GhostClientJS.html` (or `Game/GhostClientWASM.html`) in a browser.
  2. Verify the title screen shows "GhostClient 1.12" branding.
  3. Press `RSHIFT` to open the ClickGUI.
  4. Enable `Fullbright` or `Sprint` and verify behavior.
  5. Connect to an Eaglercraft 1.12.2 server and verify no console errors.

## Top 5 Quick Wins
If you want to close gaps fast, these modules are small and self-contained:
1. **NoFog** — one-line override of `FogRenderer` density.
2. **AntiBlind** — cancel the blindness/nausea shader in `EntityRenderer`.
3. **CPS** — track clicks in a 1-second rolling window and render on HUD.
4. **Speedometer** — display `Math.hypot(motionX, motionZ)` as BPS on HUD.
5. **NoRender** — hide specific overlays in `GuiIngame` / `EntityRenderer`.

## Changelog / Milestones
- **Transfer milestone** — Ownership moved from PlxeXyzz to Syntaxful; all references updated; repo renamed to `Syntaxful/GhostClient`.
- **1.12 branding milestone** — Project labeled as GhostClient 1.12 for Eaglercraft 1.12.2; README and repo metadata updated.
- **Documentation milestone** — todo.md expanded with transfer checklist, project state, next steps, module inventory, hook reference, implementation notes, build verification checklist, missing module backlog, authoring guide, and settings reference.
- **Pending:** ConfigManager, command system, remaining vanilla patches, and ~20 missing modules.

## New GhostClient UI Tasks
- [x] Add `GuiScreen.drawGhostClientBackground()` helper for dark themed menus
- [x] Apply custom GhostClient background to Multiplayer, Options, Controls, Sounds, Chat, and Resource Packs screens
- [x] Apply custom GhostClient background to the in-game pause menu
- [x] Implement custom GhostClient button styling in `GuiButton` (dark surface, purple hover, white text)
- [x] Fix ClickGUI not opening by keeping `ClickGUIModule` enabled and skipping it in the global keybind toggle loop
- [x] Add "GhostClient 1.12" and "made by Syntaxful" branding to all custom menu screens
- [ ] Add animated menu transitions (slide/fade) between GhostClient screens
- [ ] Replace vanilla title-screen logo with a custom GhostClient ghost logo
- [ ] Create a dedicated GhostClient settings panel from the ClickGUI mockup
- [ ] Add config profiles (PvP, Safe, Utility, Default) to the ClickGUI

## Notes
- All Java source edits must be verified in a real Java/TeaVM environment.
- The web/pnpm workspace is for planning, mockups, and asset management only.
- Keep every patch marked with `// GHOSTCLIENT HOOK` for maintainability.
- Build output is produced in `Game/` via `./build.sh`.
