#!/bin/bash

# Directory containing the Koin source code you want to repackage.
KOIN_SRC_DIR="/Users/arnaud/workspace/koin-projects/koin/projects"
TARGET_PACKAGE="io.kotzilla.sdk.koin"
TARGET_PACKAGE_PATH=$(echo $TARGET_PACKAGE | tr '.' '/')

# 1. Replace package declarations and import statements in all Kotlin files.
find "$KOIN_SRC_DIR" -type f -name "*.kt" -print0 | while IFS= read -r -d '' file; do
    # Use sed to replace 'org.koin' with your target package.
    sed -i.bak "s/org\.koin/${TARGET_PACKAGE//./\\.}/g" "$file"
    # Optionally remove backup files
    rm "$file.bak"
done

# 2. Move the directory structure:
#    All files under .../org/koin/ should be moved to .../io/your/sdk/koin/
if [ -d "$KOIN_SRC_DIR/org/koin" ]; then
    mkdir -p "$KOIN_SRC_DIR/$TARGET_PACKAGE_PATH"
    mv "$KOIN_SRC_DIR/org/koin/"* "$KOIN_SRC_DIR/$TARGET_PACKAGE_PATH/"
    # Optionally, remove the now-empty original directories.
    rm -rf "$KOIN_SRC_DIR/org"
fi

echo "Repackaging complete. Please verify the changes and rebuild the project."
