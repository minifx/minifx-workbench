#!/usr/bin/env bash
#
# this script assumes to be run from the project dir.

mkdir artifacts
cp -r reports/* artifacts
cp -r docs/* artifacts
cp templates/index.md artifacts

