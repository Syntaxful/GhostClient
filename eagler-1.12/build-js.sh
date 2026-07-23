#!/bin/sh
# GhostClient JS-only build
# Produces javascript/classes.js and the self-contained offline HTML.

set -e

cd "$(dirname "$0")"

chmod +x CompileEPK.sh CompileJS.sh MakeOfflineDownload.sh

echo "=== Building JS client ==="
./CompileEPK.sh
./CompileJS.sh
./MakeOfflineDownload.sh

echo "Done. Output:"
ls -lh javascript/classes.js javascript/Eaglercraft_1.12_Offline_en_US.html
