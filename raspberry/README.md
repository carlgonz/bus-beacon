BusBeacon
==========

A iBeacon (AltBeacon) based solution, to help blind people in city mobilization
tasks.

This is the sensor module, based on a Raspberry Pi.

## Setup

1. Copy altbeacon_run.py to $HOME directory
2. Copy altbeacon/altbeacon_recive script tp $HOME directory
3. Copy altbeacon/altbeacon_transmit script tp $HOME directory

To send files to a Raspberry Pi use csp command

    csp origin_file pi@<device_ip>:destine_file
    

Run altbeacon_run.py script to start beacon function.

    python3 altbeacon_run.py
    
To init becon function at start-up do the following

    mkdir logs
    chmod +x altbeacon_run.py
    sudo crontab -e
   
Add the following line to cron

    @reboot python3 /home/pi/altbeacon_run.py >/home/pi/logs/cronlog 2>&1
