#!/bin/sh
# GhostClient full build (JS + WASM in parallel, then offline downloads).
# Produces all self-contained HTML files.
# JS and WASM Gradle builds are parallelized to cut total build time roughly in half.

set -e

cd "$(dirname "$0")"

FAILED=0

fail() {
    echo "$1"
    FAILED=1
}

echo "=== GhostClient Full Build ==="

# Build JS and WASM in parallel
./build-js.sh &
JS_PID=$!
./build-wasm.sh &
WASM_PID=$!
wait $JS_PID || fail "JS build failed"
wait $WASM_PID || fail "WASM build failed"
[ $FAILED -eq 0 ] || exit 1

# Offline downloads are already produced by build-js.sh and build-wasm.sh,
# but make sure they're fresh with a final pass.
./build-offline.sh

echo "=== Build complete ==="
ls -lh javascript/classes.js
ls -lh wasm_gc_teavm/javascript/classes.wasm
ls -lh javascript/Eaglercraft_1.12_Offline_en_US.html
ls -lh wasm_gc_teavm/javascript_dist/Eaglercraft_1.12.2_WASM_Offline_Download.html
