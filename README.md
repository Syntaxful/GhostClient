# GhostClient 1.12

A performance-first, utility, and combat client for **Eaglercraft 1.12.2** — the GhostClient 1.12 release.

Built directly on top of the Eaglercraft 1.12.2 source.

**Maintained by Syntaxful.**

<img width="224" height="224" alt="a6b06022-f3d6-446b-9e96-f98b85455b20" src="https://github.com/user-attachments/assets/a3d4dc0b-89de-4d74-8976-d724bd22c563" />

---

## What you get

- Custom ClickGUI with a matching dark background, HUD Editor, and module system
- Combat, utility, movement, and performance modules
- Server list with add / favorite / connect — no singleplayer
- Profile tab with username, skin, and cape settings
- One-command build to produce distributable HTML files

---

## Build the client (HTML output)

GhostClient is Eaglercraft 1.12.2 Java source compiled to JavaScript via TeaVM. You need a local Java environment.

### Requirements

- Java 17 or 21
- Gradle 8.x (the Gradle wrapper is included)

### Quick build (one command)

```bash
./build.sh
```

This builds everything in parallel and creates a `Game/` folder with self-contained HTML files:

- `Game/GhostClientJS.html` — web client (JavaScript build)
- `Game/GhostClientWASM.html` — web client (WASM build)
- `Game/GhostClientOffline_US.html` — offline single-file client (US English)
- `Game/GhostClientOffline_International.html` — offline single-file client (International)

All of these are **single-file, self-contained clients**. You can open them directly in a browser from your desktop. No local HTTP server is required. To share the client, zip the `Game/` folder and give it to someone else.

### Separate builds

Build only what you need, when you need it. Each command produces a usable HTML file in `Game/`.

```bash
# JavaScript client only
./build-js.sh

# WASM client only (modern browsers, usually better performance)
./build-wasm.sh

# All offline-download variants
./build-offline.sh
```

### Manual Gradle steps

If you prefer to run Gradle directly:

```bash
cd eagler-1.12
./gradlew generateJavascript          # JS client
./gradlew generateWasmGC              # WASM client (inside wasm_gc_teavm/)
```

---

## Build the mockup preview only

The ClickGUI mockup is in `artifacts/mockup-sandbox/` and runs in this workspace.

```bash
# Install dependencies
pnpm install

# Run the ClickGUI preview
pnpm --filter @workspace/mockup-sandbox run dev
```

The preview opens the interactive mockup in the browser.

---

## Project files

| File | Purpose |
|------|---------|
| `README.md` | This file — build and usage instructions |
| `todo.md` | Development checklist and remaining work |
| `CONTRIBUTING.md` | Solo project contribution policy |
| `build.sh` | Full build: JS + WASM + Game/ folder |
| `build-js.sh` | JS-only build |
| `build-wasm.sh` | WASM-only build |
| `build-offline.sh` | All offline download variants |
| `artifacts/mockup-sandbox/.../ClickGUI.tsx` | ClickGUI mockup with custom controls |
| `eagler-1.12/` | Eaglercraft 1.12.2 source + GhostClient package |
| `screenshots/` | UI mockup screenshots |

---

## Features

- **Performance / FPS** — FPS Boost, No Animations, Fast Render, Fullbright, No Fog, No Weather, Entity Culling, Menu Blur, Dynamic FOV, Smooth Lighting toggle
