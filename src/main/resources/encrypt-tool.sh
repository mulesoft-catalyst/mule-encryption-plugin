#!/usr/bin/env bash

MULE_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../.." && pwd )"
PLUGIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Mule Home: $MULE_HOME"

java -cp "$MULE_HOME/conf":"$MULE_HOME/lib/mule/*":"$MULE_HOME/lib/boot/*":"$MULE_HOME/lib/opt/*":"$PLUGIN_DIR/lib/*" com.mulesoft.services.encryption.EncryptTool $*