#!/bin/bash
# publish-jar.sh - Publish a JAR to the local Maven repository

# Usage: ./publish-jar.sh path/to/your.jar groupId artifactId version [packaging]
# Example: ./publish-jar.sh my-library.jar com.example my-library 1.0.0 jar

if [ "$#" -lt 4 ]; then
  echo "Usage: $0 path/to/your.jar groupId artifactId version [packaging]"
  exit 1
fi

JAR_FILE="$1"
GROUP_ID="$2"
ARTIFACT_ID="$3"
VERSION="$4"
PACKAGING="${5:-jar}"

if [ ! -f "$JAR_FILE" ]; then
  echo "Error: File '$JAR_FILE' not found!"
  exit 1
fi

echo "Installing $JAR_FILE into the local Maven repository with coordinates:"
echo "  GroupId:    $GROUP_ID"
echo "  ArtifactId: $ARTIFACT_ID"
echo "  Version:    $VERSION"
echo "  Packaging:  $PACKAGING"

mvn install:install-file \
  -Dfile="$JAR_FILE" \
  -DgroupId="$GROUP_ID" \
  -DartifactId="$ARTIFACT_ID" \
  -Dversion="$VERSION" \
  -Dpackaging="$PACKAGING"

if [ $? -eq 0 ]; then
  echo "Installation successful!"
else
  echo "Installation failed."
  exit 1
fi
