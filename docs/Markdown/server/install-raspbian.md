## Install and configure Raspbian on Raspberry pi

#### Install Raspbian on SD Card

1. Download and extract Raspbian image from

`https://www.raspberrypi.org/downloads/raspbian/`

2. Insert SD Card and check location of device with

`lsblk`

3. Copy OS image to SD card

`sudo dd bs=4M if=2018-11-13-raspbian-stretch-lite.img of=/dev/mmcblk0 conv=fsync status=progress`

#### Configure Raspbian on Raspberry pi

1. Insert SD Card into Raspberry pi and connect it to monitor, mouse, keyboard and power on.

2. Provide default credentials

`username: pi`

`password: password`

4. Configure raspberry pi by running a following command

`sudo raspi-config`

5. Choose below options:

- `Update`

- `Network Options -> Hostname -> Change hostname to "adampi"`

- `Network Options -> Wifi`
    
- `Boot Options -> Wait for network at boot`
    
- `Interfacing Options -> SSH`

6. Update software package by running following commands

`sudo apt-get update`

`sudo apt-get dist-upgrade`

`sudo apt autoremove`

7. Check your ip address

`sudo ip addr show`

8. Update router port forwarding

`80 -> 80`
`443 -> 443`
`8443 -> 8443`

9. Setup automatic updates

`sudo su`

`crontab -e`

`0 9 * * 6 apt-get update && apt-get dist-upgrade -y`
