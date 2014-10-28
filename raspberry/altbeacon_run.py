#!/usr/bin/env python3

import subprocess
import time

print("Starting sensor")

while True:
    try:
        subprocess.call("./altbeacon_transmit")
        time.sleep(1)
    except Exception as e:
        print(e)
        break

print("Closing sensor")
