#!/bin/sh
# GhostClient offline HTML build (assumes classes.js and classes.wasm are already built).
# Produces JS and WASM offline download HTML files.

set -e

cd "$(dirname "$0")"

chmod +x MakeOfflineDownload.sh

echo "=== Building offline downloads ==="
./MakeOfflineDownload.sh
(
    cd wasm_gc_teavm
    chmod +x CompileEagRuntimeJS.sh MakeWASMClientBundle.sh
    ./CompileEagRuntimeJS.sh
    ./MakeWASMClientBundle.sh
)

echo "Done. Outputs:"
ls -lh javascript/Eaglercraft_1.12_Offline_en_US.html
ls -lh wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html
