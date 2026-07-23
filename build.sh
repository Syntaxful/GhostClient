#!/bin/sh
# GhostClient one-command build
# Produces a Game/ folder with self-contained HTML clients that work offline or from any host.
# Runs JS and WASM builds in parallel for the fastest possible full build.

set -e

cd "$(dirname "$0")"

echo "=== GhostClient Full Build ==="

# Build JS and WASM in parallel inside the Eaglercraft source tree
cd eagler-1.12
./build-js.sh &
./build-wasm.sh &
wait
cd ..

# Copy everything into the Game/ folder
mkdir -p Game/server

cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_en_US.html Game/GhostClientJS.html
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_International.html Game/GhostClientJS_International.html

if [ -f eagler-1.12/wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html ]; then
  cp eagler-1.12/wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html Game/GhostClientWASM.html
fi

cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_en_US.html Game/GhostClientOffline_US.html
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_International.html Game/GhostClientOffline_International.html

# Optional server-only web variants (for development with a local HTTP server)
# These load external files and cannot be opened directly from file://.
cp eagler-1.12/javascript/classes.js Game/server/
cp eagler-1.12/javascript/assets.epk Game/server/
cp -r eagler-1.12/javascript/lang Game/server/
cp eagler-1.12/javascript/favicon.png Game/server/
sed 's/Eaglercraft 1.12.2/GhostClient 1.12/g' eagler-1.12/javascript/index.html > Game/server/GhostClientJS_Server.html

if [ -d eagler-1.12/wasm_gc_teavm/javascript_dist ]; then
  cp -r eagler-1.12/wasm_gc_teavm/javascript_dist/* Game/server/
  rm -f Game/server/index.html
  sed 's/EaglercraftX 1.12 WASM-GC/GhostClient 1.12 WASM/g' eagler-1.12/wasm_gc_teavm/javascript_dist/index.html > Game/server/GhostClientWASM_Server.html
fi

echo ""
echo "=== Build complete ==="
ls -lh Game/

echo ""
echo "Self-contained clients (open directly, no server needed):"
echo "  Game/GhostClientJS.html"
echo "  Game/GhostClientWASM.html"
echo "  Game/GhostClientOffline_US.html"
echo "  Game/GhostClientOffline_International.html"
echo ""
echo "Optional server-only variants (require a local HTTP server):"
echo "  Game/server/GhostClientJS_Server.html"
echo "  Game/server/GhostClientWASM_Server.html"
