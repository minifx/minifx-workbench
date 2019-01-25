#!/usr/bin/env bash
#
# this script assumes to be run from the project dir.

mkdir projectpage
cp -rv build/reports/* projectpage
cp -rv build/docs/* projectpage
cp -rv docs/* projectpage

