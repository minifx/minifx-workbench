#!/usr/bin/env bash
#
# this script assumes to be run from the project dir.

mkdir artifacts
cp -rv build/reports/* artifacts
cp -rv build/docs/* artifacts
cp -v templates/index.md artifacts

