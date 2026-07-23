#!/bin/sh
# Build the offline download variants and copy them to Game/.
# No HTTP server is required — open any HTML file directly in a browser.

set -e

cd "$(dirname "$0")"

cd eagler-1.12
./build-js.sh &
./build-wasm.sh &
wait
./build-offline.sh
cd ..

mkdir -p Game
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_en_US.html Game/GhostClientJS.html
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_International.html Game/GhostClientJS_International.html
cp eagler-1.12/wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html Game/GhostClientWASM.html

# Legacy offline-named copies for compatibility
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_en_US.html Game/GhostClientOffline_US.html
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_International.html Game/GhostClientOffline_International.html

echo ""
echo "=== Offline downloads ready ==="
echo "Open Game/GhostClientJS.html, Game/GhostClientWASM.html, or the Offline_ variants directly. No server needed."
