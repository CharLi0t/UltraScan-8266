# 🔭 ESP8266 Ultrasonic Radar Scanner

A real-time radar scanner built using **ESP8266**, **HC-SR04 ultrasonic sensor**, and **SG90 servo motor**, with a **Processing GUI** to visualize object detection in a sweeping radar style.

---
<p align="center">
  <img src="https://github.com/CharLi0t/UltraScan-8266/blob/c6e8f9a524d53164bd9bf7c57d5cd9638b9c8baf/UltraScan%208266/img/ultra_scan_8266_project.jpg" height="380" margin-right:10px;/>
  <img src="https://github.com/CharLi0t/UltraScan-8266/raw/34fa69b2e86cf5d078ea8e25c4a5d02b0921de27/UltraScan%208266/img/screenshot_1.png" height="380"/>
</p>

---

## 📦 Features

- 🔁 180° servo-based radar sweep
- 📏 Real-time distance detection with HC-SR04
- 🧭 Angle adjustment from -90° to +90°
- 🖼️ Visual radar GUI in Processing (sweep + echo trails)
- 🟢 Object detection shown as green dots with fading effect
- 🎯 Optional: measure and visualize object width (multi-object supported)

---

## 🧰 Hardware Required

- [x] ESP8266 (NodeMCU / Amica)
- [x] HC-SR04 Ultrasonic Sensor
- [x] SG90 Servo Motor
- [x] USB Cable / Power Bank
- [x] Jumper Wires

### Pin Connections (ESP8266)

| Component     | ESP8266 Pin |
|---------------|-------------|
| HC-SR04 TRIG  | D5          |
| HC-SR04 ECHO  | D6          |
| Servo Signal  | D2 (GPIO4)  |
| GND           | GND         |
| VCC (Both)    | 5V          |

> ⚠️ Make sure GND from servo, sensor, and ESP8266 are connected together.

---

## 🚀 How It Works

1. Servo motor sweeps from 0° to 180° (mapped to -90° to +90°)
2. At each angle, HC-SR04 measures the distance
3. ESP8266 sends `angle,distance` via Serial (USB)
4. Processing app reads serial data and renders radar GUI

---

## 🧠 Example Serial Output

```text
-90,42.3
-88,40.1
-86,39.6
...
```

---

## 🖥️ Radar GUI (Processing)

Tech: Java-based Processing
Open the processing_radar.pde file in Processing IDE and run it.
The radar will show:
- Sweep line
- Green dots = detected objects
- Fading trail effect
- Object size (optional feature)

## 📸 Preview
<p align="center">
  <img src="https://github.com/CharLi0t/UltraScan-8266/raw/34fa69b2e86cf5d078ea8e25c4a5d02b0921de27/UltraScan%208266/img/screenshot_1.png" width="30%" margin-right:10px;/>
  <img src="https://github.com/CharLi0t/UltraScan-8266/raw/c6e8f9a524d53164bd9bf7c57d5cd9638b9c8baf/UltraScan%208266/img/screenshot_2.png" width="30%" margin-right:10px;/>
  <img src="https://github.com/CharLi0t/UltraScan-8266/blob/5c408736228ff54ff4075797ccb04fa63b965d23/UltraScan%208266/img/screenshot_3.png" width="30%"/>
</p>

---

## 🧪 Optional Ideas

- 🔔 Add buzzer alert on close objects
- 🌈 Color code based on distance
- 🌐 Send data to a web dashboard (ESP8266 + WiFi)
- 📦 Record and replay scans (data logging)

---

## 📄 License
MIT License
Feel free to use, modify, and share!

---

## 🙌 Credits

Created by [Your Name Here]
Inspired by Arduino + Processing radar projects from the maker community.
