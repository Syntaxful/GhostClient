#!/bin/sh
# Build the JS client and copy the self-contained HTML to Game/.
# No HTTP server is required — open Game/GhostClientJS.html directly in a browser.

set -e

cd "$(dirname "$0")"

cd eagler-1.12
./build-js.sh
cd ..

mkdir -p Game
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_en_US.html Game/GhostClientJS.html
cp eagler-1.12/javascript/Eaglercraft_1.12_Offline_International.html Game/GhostClientJS_International.html

echo ""
echo "=== JS client ready ==="
echo "Open Game/GhostClientJS.html directly in a browser. No server needed."
