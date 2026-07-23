import { useMemo, useState } from "react";
import {
  Search,
  Swords,
  Footprints,
  Eye,
  User,
  Globe,
  Settings,
  ChevronDown,
  ChevronRight,
  Crosshair,
  Ghost,
  Monitor,
  Shield,
  Zap,
  MousePointer2,
  Plus,
  Trash2,
  Save,
  Play,
  X,
  Check,
  Palette,
  Type,
  Grid3X3,
  Crown,
  UserCircle,
  ChevronLeft,
  ChevronUp,
  Sun,
  Moon,
  Languages,
  Maximize,
  LogOut,
} from "lucide-react";

const GHOST = {
  black: "#0A0A0F",
  surface: "#141419",
  surface2: "#1E1E26",
  border: "#27272F",
  white: "#F8F8FF",
  muted: "#6B7280",
  accent: "#8B5CF6",
  accentGlow: "#A78BFA",
  danger: "#EF4444",
  success: "#22C55E",
};

type SettingType =
  | { type: "toggle"; value: boolean }
  | { type: "slider"; value: number; min: number; max: number; step?: number }
  | { type: "mode"; value: string; options: string[] }
  | { type: "keybind"; value: string };

type Module = {
  id: string;
  name: string;
  category: string;
  desc: string;
  active: boolean;
  settings?: Record<string, SettingType>;
};

type Server = {
  id: string;
  name: string;
  address: string;
  ping: number;
  online: boolean;
  players: string;
  version: string;
  favorite: boolean;
};

const CATEGORIES = [
  { id: "combat", label: "Combat", icon: Swords, color: "#EF4444" },
  { id: "movement", label: "Movement", icon: Footprints, color: "#3B82F6" },
  { id: "render", label: "Render", icon: Eye, color: "#22C55E" },
  { id: "player", label: "Player", icon: User, color: "#EAB308" },
  { id: "world", label: "World", icon: Globe, color: "#06B6D4" },
  { id: "misc", label: "Misc", icon: Settings, color: "#8B5CF6" },
];

const INITIAL_MODULES: Module[] = [
  {
    id: "kill-aura",
    name: "Kill Aura",
    category: "combat",
    desc: "Attacks nearby entities automatically.",
    active: false,
    settings: {
      Range: { type: "slider", value: 4.5, min: 1, max: 7, step: 0.1 },
      Rotate: { type: "toggle", value: true },
      Mode: { type: "mode", value: "Smart", options: ["Smart", "Instant", "Single"] },
      CPS: { type: "slider", value: 12, min: 1, max: 20, step: 1 },
    },
  },
  {
    id: "criticals",
    name: "Criticals",
    category: "combat",
    desc: "Forces critical hits on every strike.",
    active: false,
    settings: {
      Mode: { type: "mode", value: "Packet", options: ["Packet", "Jump", "Mini"] },
    },
  },
  {
    id: "velocity",
    name: "Velocity",
    category: "combat",
    desc: "Reduces incoming knockback.",
    active: false,
    settings: {
      Horizontal: { type: "slider", value: 0, min: 0, max: 100, step: 1 },
      Vertical: { type: "slider", value: 0, min: 0, max: 100, step: 1 },
    },
  },
  {
    id: "auto-totem",
    name: "Auto Totem",
    category: "combat",
    desc: "Auto-equips totems of undying.",
    active: false,
  },
  {
    id: "triggerbot",
    name: "TriggerBot",
    category: "combat",
    desc: "Attacks when crosshair is on target.",
    active: false,
    settings: {
      CPS: { type: "slider", value: 14, min: 1, max: 20, step: 1 },
      "Hit Delay": { type: "toggle", value: true },
    },
  },
  {
    id: "speed",
    name: "Speed",
    category: "movement",
    desc: "Ground speed modifications.",
    active: false,
    settings: {
      Speed: { type: "slider", value: 1.8, min: 1, max: 4, step: 0.1 },
      Mode: { type: "mode", value: "Strafe", options: ["Strafe", "Vanilla", "Bhop"] },
    },
  },
  {
    id: "flight",
    name: "Flight",
    category: "movement",
    desc: "Creative-style flight in survival.",
    active: false,
    settings: {
      Speed: { type: "slider", value: 2, min: 0.1, max: 5, step: 0.1 },
      Mode: { type: "mode", value: "Vanilla", options: ["Vanilla", "Elytra", "Boost"] },
    },
  },
  {
    id: "jesus",
    name: "Jesus",
    category: "movement",
    desc: "Walk on water.",
    active: false,
  },
  {
    id: "nofall",
    name: "NoFall",
    category: "movement",
    desc: "Prevents fall damage.",
    active: false,
    settings: {
      Mode: { type: "mode", value: "Packet", options: ["Packet", "Spoof", "Bucket"] },
    },
  },
  {
    id: "scaffold",
    name: "Scaffold",
    category: "world",
    desc: "Auto-place blocks under the player.",
    active: false,
    settings: {
      Range: { type: "slider", value: 3, min: 1, max: 6, step: 1 },
      Tower: { type: "toggle", value: true },
      "Safe Walk": { type: "toggle", value: true },
    },
  },
  {
    id: "nuker",
    name: "Nuker",
    category: "world",
    desc: "Breaks blocks in a radius.",
    active: false,
    settings: {
      Radius: { type: "slider", value: 4, min: 1, max: 6, step: 1 },
      Mode: { type: "mode", value: "Normal", options: ["Normal", "Flat", "Silk"] },
    },
  },
  {
    id: "timer",
    name: "Timer",
    category: "world",
    desc: "Speeds up game tick rate.",
    active: false,
    settings: {
      Multiplier: { type: "slider", value: 1.5, min: 0.1, max: 5, step: 0.1 },
    },
  },
  {
    id: "fullbright",
    name: "Fullbright",
    category: "render",
    desc: "Maximum brightness everywhere.",
    active: true,
  },
  {
    id: "xray",
    name: "X-Ray",
    category: "render",
    desc: "Highlights selected blocks.",
    active: false,
    settings: {
      "Show Ores": { type: "toggle", value: true },
      Opacity: { type: "slider", value: 0.4, min: 0, max: 1, step: 0.05 },
    },
  },
  {
    id: "esp",
    name: "ESP",
    category: "render",
    desc: "Entity outlines through walls.",
    active: true,
    settings: {
      Players: { type: "toggle", value: true },
      Mobs: { type: "toggle", value: true },
      Items: { type: "toggle", value: true },
      Mode: { type: "mode", value: "Box", options: ["Box", "Glow", "2D"] },
    },
  },
  {
    id: "freecam",
    name: "Freecam",
    category: "render",
    desc: "Detached camera view.",
    active: false,
    settings: {
      Speed: { type: "slider", value: 1, min: 0.1, max: 3, step: 0.1 },
      "Track Player": { type: "toggle", value: true },
    },
  },
  {
    id: "autotool",
    name: "AutoTool",
    category: "player",
    desc: "Switch to the best tool automatically.",
    active: true,
  },
  {
    id: "autosprint",
    name: "AutoSprint",
    category: "player",
    desc: "Sprint automatically.",
    active: true,
  },
  {
    id: "fastplace",
    name: "FastPlace",
    category: "player",
    desc: "Remove block placement cooldown.",
    active: false,
  },
  {
    id: "clickgui",
    name: "ClickGUI",
    category: "misc",
    desc: "Opens this menu.",
    active: true,
    settings: {
      Keybind: { type: "keybind", value: "RSHIFT" },
    },
  },
  {
    id: "hud-editor",
    name: "HUD Editor",
    category: "misc",
    desc: "Edit HUD elements.",
    active: false,
    settings: {
      Keybind: { type: "keybind", value: "NONE" },
    },
  },
  {
    id: "arraylist",
    name: "ArrayList",
    category: "misc",
    desc: "Active modules list on HUD.",
    active: true,
    settings: {
      "Right Side": { type: "toggle", value: true },
      "Sort Length": { type: "toggle", value: true },
    },
  },
];

const INITIAL_SERVERS: Server[] = [
  {
    id: "1",
    name: "Eagler Lobby",
    address: "wss://eagler.lobby.example",
    ping: 24,
    online: true,
    players: "127/300",
    version: "1.12.2",
    favorite: true,
  },
  {
    id: "2",
    name: "Ghost PvP",
    address: "wss://ghostpvp.example",
    ping: 42,
    online: true,
    players: "64/150",
    version: "1.12.2",
    favorite: false,
  },
  {
    id: "3",
    name: "Anarchy",
    address: "wss://anarchy.example",
    ping: 88,
    online: false,
    players: "0/200",
    version: "1.12.2",
    favorite: true,
  },
];

function GhostButton({
  children,
  onClick,
  variant = "primary",
  size = "md",
  className = "",
  disabled = false,
}: {
  children: React.ReactNode;
  onClick?: () => void;
  variant?: "primary" | "secondary" | "danger" | "ghost" | "accent";
  size?: "sm" | "md" | "lg";
  className?: string;
  disabled?: boolean;
}) {
  const sizeClasses = {
    sm: "px-3 py-1.5 text-xs",
    md: "px-4 py-2 text-sm",
    lg: "px-6 py-2.5 text-sm",
  };

  const variants = {
    primary: {
      background: GHOST.surface,
      border: GHOST.border,
      color: GHOST.white,
      hoverBg: GHOST.surface2,
      glow: "transparent",
    },
    secondary: {
      background: "transparent",
      border: GHOST.border,
      color: GHOST.muted,
      hoverBg: GHOST.surface,
      glow: "transparent",
    },
    danger: {
      background: "rgba(239, 68, 68, 0.12)",
      border: "rgba(239, 68, 68, 0.35)",
      color: "#FCA5A5",
      hoverBg: "rgba(239, 68, 68, 0.22)",
      glow: "rgba(239, 68, 68, 0.25)",
    },
    ghost: {
      background: "transparent",
      border: "transparent",
      color: GHOST.muted,
      hoverBg: "rgba(248, 248, 255, 0.06)",
      glow: "transparent",
    },
    accent: {
      background: GHOST.accent,
      border: GHOST.accent,
      color: GHOST.white,
      hoverBg: GHOST.accentGlow,
      glow: "rgba(139, 92, 246, 0.45)",
    },
  };

  const v = variants[variant];

  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={[
        "relative rounded-lg font-medium transition-all duration-150 ease-out",
        "border focus:outline-none",
        "disabled:opacity-50 disabled:cursor-not-allowed",
        "active:scale-[0.98]",
        sizeClasses[size],
        className,
      ].join(" ")}
      style={{
        background: v.background,
        borderColor: v.border,
        color: v.color,
        boxShadow: disabled ? "none" : `0 0 0 0 ${v.glow}`,
      }}
      onMouseEnter={(e) => {
        if (!disabled) e.currentTarget.style.background = v.hoverBg;
        if (!disabled && v.glow !== "transparent") {
          e.currentTarget.style.boxShadow = `0 0 18px 0 ${v.glow}`;
        }
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.background = v.background;
        e.currentTarget.style.boxShadow = disabled ? "none" : `0 0 0 0 ${v.glow}`;
      }}
    >
      {children}
    </button>
  );
}

function GhostToggle({
  value,
  onChange,
  size = "md",
}: {
  value: boolean;
  onChange: (v: boolean) => void;
  size?: "sm" | "md";
}) {
  const sizes = {
    sm: { w: 32, h: 18, knob: 14, off: 2, on: 16 },
    md: { w: 44, h: 24, knob: 20, off: 2, on: 22 },
  };
  const s = sizes[size];
  return (
    <button
      onClick={() => onChange(!value)}
      className="relative rounded-full transition-colors duration-200 focus:outline-none"
      style={{
        width: s.w,
        height: s.h,
        background: value ? GHOST.accent : GHOST.border,
        boxShadow: value ? `0 0 12px rgba(139, 92, 246, 0.35)` : "none",
      }}
    >
      <span
        className="absolute top-1/2 -translate-y-1/2 rounded-full bg-white shadow transition-all duration-200"
        style={{
          width: s.knob,
          height: s.knob,
          left: value ? s.on : s.off,
        }}
      />
    </button>
  );
}

function GhostSlider({
  value,
  min,
  max,
  step = 0.1,
  onChange,
}: {
  value: number;
  min: number;
  max: number;
  step?: number;
  onChange: (v: number) => void;
}) {
  const pct = ((value - min) / (max - min)) * 100;
  return (
    <div className="flex items-center gap-3 flex-1">
      <div className="relative flex-1 h-1.5 rounded-full" style={{ background: GHOST.border }}>
        <div
          className="absolute top-0 left-0 h-full rounded-full"
          style={{ width: `${pct}%`, background: GHOST.accent }}
        />
        <input
          type="range"
          min={min}
          max={max}
          step={step}
          value={value}
          onChange={(e) => onChange(parseFloat(e.target.value))}
          className="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
        />
        <div
          className="absolute top-1/2 -translate-y-1/2 w-3 h-3 rounded-full border-2 bg-white"
          style={{ left: `calc(${pct}% - 6px)`, borderColor: GHOST.accent }}
        />
      </div>
      <span className="text-xs font-mono w-12 text-right" style={{ color: GHOST.white }}>
        {value.toFixed(typeof step === "number" && step < 1 ? 1 : 0)}
      </span>
    </div>
  );
}

function GhostMode({
  options,
  value,
  onChange,
}: {
  options: string[];
  value: string;
  onChange: (v: string) => void;
}) {
  return (
    <div
      className="inline-flex items-center rounded-lg p-1 gap-1"
      style={{ background: GHOST.surface2, border: `1px solid ${GHOST.border}` }}
    >
      {options.map((opt) => {
        const active = opt === value;
        return (
          <button
            key={opt}
            onClick={() => onChange(opt)}
            className="px-2.5 py-1 rounded-md text-xs font-semibold transition-all"
            style={{
              background: active ? GHOST.accent : "transparent",
              color: active ? GHOST.white : GHOST.muted,
              boxShadow: active ? `0 0 10px rgba(139, 92, 246, 0.3)` : "none",
            }}
          >
            {opt}
          </button>
        );
      })}
    </div>
  );
}

function GhostKeybind({
  value,
  onChange,
}: {
  value: string;
  onChange: (v: string) => void;
}) {
  const [listening, setListening] = useState(false);
  return (
    <button
      onClick={() => setListening(!listening)}
      className="px-3 py-1 rounded-md text-xs font-mono border transition-all"
      style={{
        background: listening ? GHOST.accent : GHOST.surface,
        borderColor: listening ? GHOST.accent : GHOST.border,
        color: GHOST.white,
        boxShadow: listening ? `0 0 12px rgba(139, 92, 246, 0.35)` : "none",
      }}
    >
      {listening ? "PRESS KEY" : value || "NONE"}
    </button>
  );
}

export function ClickGUI() {
  const [tab, setTab] = useState<"modules" | "servers" | "profile" | "settings">("modules");
  const [activeCategory, setActiveCategory] = useState("combat");
  const [search, setSearch] = useState("");
  const [expanded, setExpanded] = useState<string | null>("kill-aura");
  const [modules, setModules] = useState<Module[]>(INITIAL_MODULES);
  const [servers, setServers] = useState<Server[]>(INITIAL_SERVERS);
  const [showAddServer, setShowAddServer] = useState(false);
  const [newServer, setNewServer] = useState({ name: "", address: "" });
  const [profile, setProfile] = useState({
    username: "Syntaxful",
    cape: "ghost-purple",
    skin: "classic",
  });
  const [settings, setSettings] = useState({
    theme: "dark",
    language: "English",
    menuBlur: true,
    arrayList: true,
    animations: true,
    sound: true,
  });

  const activeCount = useMemo(() => modules.filter((m) => m.active).length, [modules]);
  const activeModules = useMemo(
    () => modules.filter((m) => m.active).sort((a, b) => b.name.length - a.name.length),
    [modules],
  );

  const filteredModules = modules.filter((m) => {
    const matchesCategory = m.category === activeCategory;
    const matchesSearch = m.name.toLowerCase().includes(search.toLowerCase());
    return search ? matchesSearch : matchesCategory;
  });

  const toggleModule = (id: string) => {
    setModules((prev) => prev.map((m) => (m.id === id ? { ...m, active: !m.active } : m)));
  };

  const updateModuleSetting = (
    id: string,
    key: string,
    next: Partial<SettingType>,
  ) => {
    setModules((prev) =>
      prev.map((m) => {
        if (m.id !== id) return m;
        const setting = m.settings?.[key];
        if (!setting) return m;
        return {
          ...m,
          settings: {
            ...m.settings,
            [key]: { ...setting, ...next } as SettingType,
          },
        };
      }),
    );
  };

  const addServer = () => {
    if (!newServer.name || !newServer.address) return;
    setServers((prev) => [
      ...prev,
      {
        id: Math.random().toString(36).slice(2, 8),
        name: newServer.name,
        address: newServer.address,
        ping: 0,
        online: true,
        players: "0/0",
        version: "1.12.2",
        favorite: false,
      },
    ]);
    setNewServer({ name: "", address: "" });
    setShowAddServer(false);
  };

  const deleteServer = (id: string) => setServers((prev) => prev.filter((s) => s.id !== id));
  const toggleFavorite = (id: string) =>
    setServers((prev) => prev.map((s) => (s.id === id ? { ...s, favorite: !s.favorite } : s)));

  const capes = [
    { id: "none", label: "None", color: "#6B7280" },
    { id: "ghost-purple", label: "Ghost Purple", color: "#8B5CF6" },
    { id: "ghost-white", label: "Ghost White", color: "#F8F8FF" },
    { id: "crown", label: "Crown", color: "#EAB308" },
  ];

  const skins = ["classic", "slim", "ghost"];

  const tabs = [
    { id: "modules", label: "Modules", icon: Grid3X3 },
    { id: "servers", label: "Servers", icon: Monitor },
    { id: "profile", label: "Profile", icon: UserCircle },
    { id: "settings", label: "Settings", icon: Settings },
  ];

  return (
    <div
      className="relative min-h-screen w-full overflow-hidden font-sans select-none"
      style={{ background: GHOST.black, color: GHOST.white }}
    >
      {/* Background HUD */}
      <div className="absolute inset-0 pointer-events-none">
        <div className="absolute top-4 left-4 text-xs drop-shadow-md">
          <div className="flex items-center gap-2">
            <Crosshair className="w-4 h-4" />
            <span className="font-bold tracking-wider">GhostClient</span>
          </div>
        </div>
        {settings.arrayList && (
          <div className="absolute top-4 right-4 text-right">
            {activeModules.map((m) => (
              <div
                key={m.id}
                className="text-sm font-semibold px-2 py-0.5 rounded-l mb-0.5"
                style={{
                  background: "rgba(139, 92, 246, 0.85)",
                  color: GHOST.white,
                  textShadow: "0 1px 2px rgba(0,0,0,0.8)",
                }}
              >
                {m.name}
              </div>
            ))}
          </div>
        )}
        <div className="absolute bottom-4 left-4 text-xs drop-shadow-md space-y-1">
          <div>240 FPS</div>
          <div>124 / 64 / -302</div>
        </div>
        <div className="absolute bottom-4 right-4 text-right text-xs drop-shadow-md space-y-1">
          <div>6.2 b/s</div>
          <div>12 CPS</div>
        </div>
        <div className="absolute bottom-8 left-1/2 -translate-x-1/2 flex items-end gap-1">
          {Array.from({ length: 9 }).map((_, i) => (
            <div
              key={i}
              className="w-10 h-10 bg-black/40 border"
              style={{ borderColor: i === 0 ? GHOST.white : "rgba(255,255,255,0.2)" }}
            />
          ))}
        </div>
      </div>

      {/* Main Menu Panel */}
      <div className="absolute inset-0 flex items-center justify-center p-8">
        <div
          className="flex w-[1120px] h-[740px] rounded-2xl overflow-hidden"
          style={{
            background: GHOST.black,
            border: `1px solid ${GHOST.border}`,
            boxShadow: "0 24px 80px rgba(0,0,0,0.6), 0 0 0 1px rgba(139,92,246,0.08)",
          }}
        >
          {/* Sidebar */}
          <div
            className="w-60 flex flex-col border-r"
            style={{ background: GHOST.surface, borderColor: GHOST.border }}
          >
            <div className="flex items-center gap-3 px-4 py-5 border-b" style={{ borderColor: GHOST.border }}>
              <div
                className="w-10 h-10 rounded-xl flex items-center justify-center"
                style={{ background: GHOST.black, border: `1px solid ${GHOST.border}` }}
              >
                <Ghost className="w-5 h-5" style={{ color: GHOST.white }} />
              </div>
              <div>
                <div className="text-sm font-bold tracking-wide">GHOST</div>
                <div className="text-[10px] font-semibold -mt-0.5" style={{ color: GHOST.muted }}>
                  CLIENT
                </div>
              </div>
            </div>

            <div className="flex-1 py-3 space-y-1 px-3">
              {tabs.map((t) => {
                const Icon = t.icon;
                const isActive = tab === t.id;
                return (
                  <button
                    key={t.id}
                    onClick={() => setTab(t.id as any)}
                    className="w-full flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm transition-all"
                    style={{
                      background: isActive ? GHOST.accent : "transparent",
                      color: isActive ? GHOST.white : GHOST.muted,
                      boxShadow: isActive ? `0 0 16px rgba(139,92,246,0.35)` : "none",
                    }}
                    onMouseEnter={(e) => {
                      if (!isActive) e.currentTarget.style.background = GHOST.surface2;
                    }}
                    onMouseLeave={(e) => {
                      if (!isActive) e.currentTarget.style.background = "transparent";
                    }}
                  >
                    <Icon className="w-4 h-4" />
                    <span className="font-semibold">{t.label}</span>
                    {t.id === "modules" && (
                      <span
                        className="ml-auto text-[10px] px-1.5 py-0.5 rounded-full font-bold"
                        style={{ background: isActive ? "rgba(255,255,255,0.2)" : GHOST.surface2 }}
                      >
                        {activeCount}
                      </span>
                    )}
                  </button>
                );
              })}
            </div>

            <div className="px-4 py-4 border-t space-y-4" style={{ borderColor: GHOST.border }}>
              <div>
                <div className="text-[10px] uppercase tracking-wider mb-2" style={{ color: GHOST.muted }}>
                  Credits
                </div>
                <div className="text-xs" style={{ color: GHOST.muted }}>
                  made by <span className="font-semibold" style={{ color: GHOST.white }}>Syntaxful</span>
                </div>
              </div>
              <div className="pt-3 border-t" style={{ borderColor: GHOST.border }}>
                <div className="text-[10px] uppercase tracking-wider mb-2" style={{ color: GHOST.muted }}>
                  Keybinds
                </div>
                <div className="space-y-2 text-xs">
                  <div className="flex justify-between" style={{ color: GHOST.muted }}>
                    <span>ClickGUI</span>
                    <span className="font-mono" style={{ color: GHOST.white }}>RSHIFT</span>
                  </div>
                  <div className="flex justify-between" style={{ color: GHOST.muted }}>
                    <span>HUD</span>
                    <span className="font-mono" style={{ color: GHOST.white }}>NONE</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Content Area */}
          <div className="flex-1 flex flex-col min-w-0" style={{ background: GHOST.black }}>
            {/* Header */}
            <div
              className="h-16 border-b flex items-center px-5 justify-between"
              style={{ borderColor: GHOST.border }}
            >
              <div className="flex items-center gap-3">
                <div className="text-lg font-bold tracking-tight">
                  {tab === "modules" && "Module Manager"}
                  {tab === "servers" && "Server List"}
                  {tab === "profile" && "Player Profile"}
                  {tab === "settings" && "Client Settings"}
                </div>
                <div
                  className="text-[10px] px-2 py-0.5 rounded-full border"
                  style={{ borderColor: GHOST.border, color: GHOST.muted }}
                >
                  v1.0.0
                </div>
              </div>
              <div className="flex items-center gap-2">
                {tab === "modules" && (
                  <div className="relative">
                    <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4" style={{ color: GHOST.muted }} />
                    <input
                      type="text"
                      placeholder="Search modules..."
                      value={search}
                      onChange={(e) => setSearch(e.target.value)}
                      className="h-9 pl-9 pr-4 rounded-lg text-sm focus:outline-none"
                      style={{
                        background: GHOST.surface,
                        border: `1px solid ${GHOST.border}`,
                        color: GHOST.white,
                      }}
                    />
                  </div>
                )}
                <GhostButton variant="secondary" size="sm" onClick={() => {}}>
                  <LogOut className="w-3.5 h-3.5 inline mr-1.5" />
                  Close
                </GhostButton>
              </div>
            </div>

            {/* Tab Content */}
            <div className="flex-1 overflow-hidden">
              {tab === "modules" && (
                <div className="flex h-full">
                  {/* Category strip */}
                  <div
                    className="w-44 border-r flex flex-col py-3 px-2 space-y-1"
                    style={{ background: GHOST.surface, borderColor: GHOST.border }}
                  >
                    {CATEGORIES.map((cat) => {
                      const Icon = cat.icon;
                      const isActive = activeCategory === cat.id && !search;
                      return (
                        <button
                          key={cat.id}
                          onClick={() => {
                            setActiveCategory(cat.id);
                            setSearch("");
                          }}
                          className="w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm transition-all"
                          style={{
                            background: isActive ? GHOST.surface2 : "transparent",
                            color: isActive ? cat.color : GHOST.muted,
                            borderLeft: isActive ? `2px solid ${cat.color}` : "2px solid transparent",
                          }}
                          onMouseEnter={(e) => {
                            if (!isActive) e.currentTarget.style.background = "rgba(255,255,255,0.04)";
                          }}
                          onMouseLeave={(e) => {
                            if (!isActive) e.currentTarget.style.background = "transparent";
                          }}
                        >
                          <Icon className="w-4 h-4" />
                          <span className="font-medium">{cat.label}</span>
                          <span className="ml-auto text-[10px]" style={{ color: GHOST.muted }}>
                            {modules.filter((m) => m.category === cat.id).length}
                          </span>
                        </button>
                      );
                    })}
                  </div>

                  {/* Module list */}
                  <div className="flex-1 overflow-y-auto p-4 space-y-2">
                    {filteredModules.map((module) => {
                      const isExpanded = expanded === module.id;
                      const isActive = module.active;
                      return (
                        <div
                          key={module.id}
                          className="rounded-xl border transition-all overflow-hidden"
                          style={{
                            background: GHOST.surface,
                            borderColor: isExpanded ? GHOST.accent : GHOST.border,
                            boxShadow: isExpanded ? `0 0 20px rgba(139,92,246,0.12)` : "none",
                          }}
                        >
                          <div className="flex items-center justify-between px-4 py-3">
                            <div className="flex items-center gap-3 flex-1 min-w-0">
                              <button
                                onClick={() => setExpanded(isExpanded ? null : module.id)}
                                className="transition-colors"
                                style={{ color: GHOST.muted }}
                              >
                                {isExpanded ? (
                                  <ChevronDown className="w-4 h-4" />
                                ) : (
                                  <ChevronRight className="w-4 h-4" />
                                )}
                              </button>
                              <div className="flex-1 min-w-0">
                                <div className="flex items-center gap-2">
                                  <span className="font-semibold text-sm" style={{ color: isActive ? GHOST.white : GHOST.muted }}>
                                    {module.name}
                                  </span>
                                </div>
                                <p className="text-xs truncate" style={{ color: GHOST.muted }}>
                                  {module.desc}
                                </p>
                              </div>
                            </div>
                            <GhostToggle value={isActive} onChange={() => toggleModule(module.id)} />
                          </div>

                          {isExpanded && module.settings && (
                            <div className="px-4 pb-4 pt-1 border-t space-y-3" style={{ borderColor: GHOST.border }}>
                              {Object.entries(module.settings).map(([key, setting]) => (
                                <div key={key} className="flex items-center justify-between gap-4">
                                  <span className="text-sm" style={{ color: GHOST.muted }}>
                                    {key}
                                  </span>
                                  {setting.type === "slider" && (
                                    <GhostSlider
                                      value={setting.value}
                                      min={setting.min}
                                      max={setting.max}
                                      step={setting.step}
                                      onChange={(v) => updateModuleSetting(module.id, key, { value: v })}
                                    />
                                  )}
                                  {setting.type === "toggle" && (
                                    <GhostToggle
                                      size="sm"
                                      value={setting.value}
                                      onChange={(v) => updateModuleSetting(module.id, key, { value: v })}
                                    />
                                  )}
                                  {setting.type === "mode" && (
                                    <GhostMode
                                      options={setting.options}
                                      value={setting.value}
                                      onChange={(v) => updateModuleSetting(module.id, key, { value: v })}
                                    />
                                  )}
                                  {setting.type === "keybind" && (
                                    <GhostKeybind
                                      value={setting.value}
                                      onChange={(v) => updateModuleSetting(module.id, key, { value: v })}
                                    />
                                  )}
                                </div>
                              ))}
                            </div>
                          )}
                        </div>
                      );
                    })}
                  </div>
                </div>
              )}

              {tab === "servers" && (
                <div className="h-full p-5 overflow-y-auto">
                  <div className="flex items-center justify-between mb-4">
                    <div className="text-sm" style={{ color: GHOST.muted }}>
                      Multiplayer servers. No singleplayer support.
                    </div>
                    <GhostButton onClick={() => setShowAddServer(true)} variant="accent" size="sm">
                      <Plus className="w-3.5 h-3.5 inline mr-1.5" />
                      Add Server
                    </GhostButton>
                  </div>

                  {showAddServer && (
                    <div
                      className="mb-4 rounded-xl border p-4"
                      style={{ background: GHOST.surface, borderColor: GHOST.border }}
                    >
                      <div className="grid grid-cols-2 gap-3 mb-3">
                        <input
                          placeholder="Server name"
                          value={newServer.name}
                          onChange={(e) => setNewServer((s) => ({ ...s, name: e.target.value }))}
                          className="h-9 px-3 rounded-lg text-sm focus:outline-none"
                          style={{ background: GHOST.black, border: `1px solid ${GHOST.border}`, color: GHOST.white }}
                        />
                        <input
                          placeholder="wss://address"
                          value={newServer.address}
                          onChange={(e) => setNewServer((s) => ({ ...s, address: e.target.value }))}
                          className="h-9 px-3 rounded-lg text-sm focus:outline-none"
                          style={{ background: GHOST.black, border: `1px solid ${GHOST.border}`, color: GHOST.white }}
                        />
                      </div>
                      <div className="flex gap-2 justify-end">
                        <GhostButton variant="secondary" size="sm" onClick={() => setShowAddServer(false)}>
                          Cancel
                        </GhostButton>
                        <GhostButton variant="accent" size="sm" onClick={addServer}>
                          Save Server
                        </GhostButton>
                      </div>
                    </div>
                  )}

                  <div className="space-y-2">
                    {servers.map((server) => (
                      <div
                        key={server.id}
                        className="flex items-center gap-4 rounded-xl border px-4 py-3"
                        style={{ background: GHOST.surface, borderColor: GHOST.border }}
                      >
                        <div className="w-2 h-2 rounded-full" style={{ background: server.online ? GHOST.success : GHOST.danger }} />
                        <div className="flex-1 min-w-0">
                          <div className="flex items-center gap-2">
                            <span className="font-semibold text-sm">{server.name}</span>
                            {server.favorite && <Crown className="w-3.5 h-3.5" style={{ color: "#EAB308" }} />}
                          </div>
                          <div className="text-xs" style={{ color: GHOST.muted }}>
                            {server.address} · {server.players} · {server.ping}ms
                          </div>
                        </div>
                        <div className="flex items-center gap-2">
                          <button
                            onClick={() => toggleFavorite(server.id)}
                            className="p-2 rounded-lg transition-colors"
                            style={{ color: server.favorite ? "#EAB308" : GHOST.muted }}
                          >
                            <Crown className="w-4 h-4" />
                          </button>
                          <GhostButton variant="primary" size="sm" onClick={() => {}}>
                            <Play className="w-3.5 h-3.5 inline mr-1.5" />
                            Connect
                          </GhostButton>
                          <GhostButton variant="danger" size="sm" onClick={() => deleteServer(server.id)}>
                            <Trash2 className="w-3.5 h-3.5 inline" />
                          </GhostButton>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {tab === "profile" && (
                <div className="h-full p-5 overflow-y-auto">
                  <div className="grid grid-cols-2 gap-5">
                    <div
                      className="rounded-xl border p-5"
                      style={{ background: GHOST.surface, borderColor: GHOST.border }}
                    >
                      <div className="text-sm font-semibold mb-4">Character</div>
                      <div className="flex items-center gap-4 mb-5">
                        <div
                          className="w-20 h-20 rounded-2xl border flex items-center justify-center"
                          style={{ background: GHOST.black, borderColor: GHOST.border }}
                        >
                          <UserCircle className="w-10 h-10" style={{ color: GHOST.muted }} />
                        </div>
                        <div>
                          <div className="text-lg font-bold">{profile.username}</div>
                          <div className="text-xs" style={{ color: GHOST.muted }}>
                            GhostClient user
                          </div>
                        </div>
                      </div>
                      <div className="space-y-3">
                        <div>
                          <label className="text-xs block mb-1.5" style={{ color: GHOST.muted }}>
                            Username
                          </label>
                          <input
                            value={profile.username}
                            onChange={(e) => setProfile((p) => ({ ...p, username: e.target.value }))}
                            className="w-full h-9 px-3 rounded-lg text-sm focus:outline-none"
                            style={{ background: GHOST.black, border: `1px solid ${GHOST.border}`, color: GHOST.white }}
                          />
                        </div>
                        <div>
                          <label className="text-xs block mb-1.5" style={{ color: GHOST.muted }}>
                            Skin Model
                          </label>
                          <div className="flex gap-2">
                            {skins.map((skin) => (
                              <GhostButton
                                key={skin}
                                variant={profile.skin === skin ? "accent" : "secondary"}
                                size="sm"
                                onClick={() => setProfile((p) => ({ ...p, skin }))}
                              >
                                {skin.charAt(0).toUpperCase() + skin.slice(1)}
                              </GhostButton>
                            ))}
                          </div>
                        </div>
                      </div>
                    </div>

                    <div
                      className="rounded-xl border p-5"
                      style={{ background: GHOST.surface, borderColor: GHOST.border }}
                    >
                      <div className="text-sm font-semibold mb-4">Cape & Cosmetics</div>
                      <div className="space-y-3">
                        {capes.map((cape) => (
                          <button
                            key={cape.id}
                            onClick={() => setProfile((p) => ({ ...p, cape: cape.id }))}
                            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-lg border transition-all"
                            style={{
                              background: profile.cape === cape.id ? GHOST.surface2 : GHOST.black,
                              borderColor: profile.cape === cape.id ? cape.color : GHOST.border,
                            }}
                          >
                            <div
                              className="w-5 h-5 rounded border"
                              style={{ background: cape.color, borderColor: "rgba(255,255,255,0.2)" }}
                            />
                            <span className="text-sm font-medium">{cape.label}</span>
                            {profile.cape === cape.id && (
                              <Check className="w-4 h-4 ml-auto" style={{ color: GHOST.success }} />
                            )}
                          </button>
                        ))}
                      </div>
                    </div>
                  </div>
                </div>
              )}

              {tab === "settings" && (
                <div className="h-full p-5 overflow-y-auto">
                  <div className="grid grid-cols-2 gap-5">
                    {[
                      {
                        id: "arrayList",
                        label: "ArrayList HUD",
                        desc: "Show active modules on the right side of the screen.",
                        value: settings.arrayList,
                      },
                      {
                        id: "menuBlur",
                        label: "Menu Blur",
                        desc: "Blur the game world behind GUI menus.",
                        value: settings.menuBlur,
                      },
                      {
                        id: "animations",
                        label: "UI Animations",
                        desc: "Enable smooth transitions and micro-interactions.",
                        value: settings.animations,
                      },
                      {
                        id: "sound",
                        label: "Interface Sounds",
                        desc: "Play subtle audio feedback on clicks and toggles.",
                        value: settings.sound,
                      },
                    ].map((item) => (
                      <div
                        key={item.id}
                        className="flex items-center justify-between rounded-xl border px-4 py-3"
                        style={{ background: GHOST.surface, borderColor: GHOST.border }}
                      >
                        <div>
                          <div className="text-sm font-semibold">{item.label}</div>
                          <div className="text-xs" style={{ color: GHOST.muted }}>
                            {item.desc}
                          </div>
                        </div>
                        <GhostToggle
                          value={item.value}
                          onChange={(v) => setSettings((s) => ({ ...s, [item.id]: v }))}
                        />
                      </div>
                    ))}

                    <div
                      className="rounded-xl border px-4 py-3"
                      style={{ background: GHOST.surface, borderColor: GHOST.border }}
                    >
                      <div className="text-sm font-semibold mb-3">Appearance</div>
                      <div className="flex gap-2 mb-3">
                        <GhostButton
                          variant={settings.theme === "dark" ? "accent" : "secondary"}
                          size="sm"
                          onClick={() => setSettings((s) => ({ ...s, theme: "dark" }))}
                        >
                          <Moon className="w-3.5 h-3.5 inline mr-1.5" />
                          Dark
                        </GhostButton>
                        <GhostButton
                          variant={settings.theme === "light" ? "accent" : "secondary"}
                          size="sm"
                          onClick={() => setSettings((s) => ({ ...s, theme: "light" }))}
                        >
                          <Sun className="w-3.5 h-3.5 inline mr-1.5" />
                          Light
                        </GhostButton>
                      </div>
                      <div className="text-xs" style={{ color: GHOST.muted }}>
                        GhostClient ships in dark mode by default.
                      </div>
                    </div>

                    <div
                      className="rounded-xl border px-4 py-3"
                      style={{ background: GHOST.surface, borderColor: GHOST.border }}
                    >
                      <div className="text-sm font-semibold mb-3">Language</div>
                      <div className="flex gap-2">
                        {["English", "Español", "Русский"].map((lang) => (
                          <GhostButton
                            key={lang}
                            variant={settings.language === lang ? "accent" : "secondary"}
                            size="sm"
                            onClick={() => setSettings((s) => ({ ...s, language: lang }))}
                          >
                            <Languages className="w-3.5 h-3.5 inline mr-1.5" />
                            {lang}
                          </GhostButton>
                        ))}
                      </div>
                    </div>
                  </div>

                  <div className="mt-5 flex gap-2">
                    <GhostButton variant="primary" size="sm" onClick={() => {}}>
                      <Save className="w-3.5 h-3.5 inline mr-1.5" />
                      Save Config
                    </GhostButton>
                    <GhostButton variant="danger" size="sm" onClick={() => {}}>
                      <Trash2 className="w-3.5 h-3.5 inline mr-1.5" />
                      Reset Defaults
                    </GhostButton>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Status Bar */}
      <div
        className="absolute bottom-0 left-0 right-0 h-7 flex items-center justify-between px-4 text-[11px]"
        style={{ background: GHOST.surface, borderTop: `1px solid ${GHOST.border}`, color: GHOST.muted }}
      >
        <div className="flex items-center gap-2">
          <span className="w-2 h-2 rounded-full" style={{ background: GHOST.white }} />
          <span>GhostClient v1.0.0</span>
          <span style={{ color: GHOST.border }}>|</span>
          <span>made by Syntaxful</span>
        </div>
        <div className="flex items-center gap-4">
          <span>240 FPS</span>
          <span>ms: 12</span>
        </div>
      </div>
    </div>
  );
}
