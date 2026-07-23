#!/bin/sh
# Build the WASM client and copy the self-contained HTML to Game/.
# No HTTP server is required — open Game/GhostClientWASM.html directly in a browser.

set -e

cd "$(dirname "$0")"

cd eagler-1.12
./build-wasm.sh
cd ..

mkdir -p Game
cp eagler-1.12/wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html Game/GhostClientWASM.html

echo ""
echo "=== WASM client ready ==="
echo "Open Game/GhostClientWASM.html directly in a browser. No server needed."
