# GhostClient Build Guide

## One-command build (recommended)
```bash
./build-all.sh
```
This builds the JS client, WASM client, and offline downloads in one go.

## Individual builds

### JavaScript only
```bash
./build-js.sh
```
Output: `javascript/classes.js`

### WASM only
```bash
./build-wasm.sh
```
Output: `javascript/classes.wasm`

### Offline download only
```bash
./build-offline.sh
```
Output: `javascript/Eaglercraft_1.12_Offline_en_US.html`

## Desktop runtime
```bash
./gradlew runclient
```

## Requirements
- Java 17 recommended for TeaVM/WASM builds
- Java 8+ for desktop runtime
- Gradle wrapper included (`gradlew`)

## Outputs
All web outputs are placed in `javascript/`:
- `classes.js` — JavaScript build
- `classes.wasm` — WASM build
- `assets.epk` — Packed assets
- `Eaglercraft_1.12_Offline_en_US.html` — Offline download
- `index.html` — Online loader
