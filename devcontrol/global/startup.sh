#!/bin/bash

set -eu

# @file devcontrol/global/startup.sh
# @brief devcontrol startup script and functions
echo "basedocker (c) 2020 Ayuda Digital"
echo
cp -n platform/config.ini.dist platform/config.ini || true
