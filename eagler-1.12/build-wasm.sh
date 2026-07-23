#!/bin/sh
# GhostClient WASM-only build
# Produces the self-contained WASM offline HTML.

set -e

cd "$(dirname "$0")"

chmod +x CompileEPK.sh CompileJS.sh MakeOfflineDownload.sh

echo "=== Building WASM client ==="
(
    cd wasm_gc_teavm
    chmod +x CompileEPK.sh
    ./CompileEPK.sh
)
(
    cd wasm_gc_teavm
    chmod +x gradlew
    ./gradlew --parallel --build-cache generateWasmGC
)
(
    cd wasm_gc_teavm
    chmod +x CompileEagRuntimeJS.sh MakeWASMClientBundle.sh
    ./CompileEagRuntimeJS.sh
    ./MakeWASMClientBundle.sh
)

echo "Done. Output:"
ls -lh wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html
