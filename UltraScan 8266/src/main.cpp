#include <Servo.h>  // Include Servo library

// Define pins for ultrasonic sensor and servo motor
#define TRIG_PIN D5      // Trigger pin of HC-SR04
#define ECHO_PIN D6      // Echo pin of HC-SR04
#define SERVO_PIN D2     // PWM pin connected to servo motor (GPIO4)

Servo myServo;  // Create servo object

void setup() {
  Serial.begin(115200);  // Start serial communication at 115200 baud
  pinMode(TRIG_PIN, OUTPUT); // Set trigger pin as output
  pinMode(ECHO_PIN, INPUT);  // Set echo pin as input

  // Attach servo to SERVO_PIN with safe pulse width range for full sweep
  myServo.attach(SERVO_PIN, 600, 2400); // Approx. 0° to 180° (custom range)
}

// Function to measure distance using HC-SR04
float readDistanceCM() {
  digitalWrite(TRIG_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);

  // Read pulse duration in microseconds
  long duration = pulseIn(ECHO_PIN, HIGH, 30000); // Timeout after 30 ms
  if (duration == 0) return -1; // No echo received
  return (duration / 2.0) / 29.1; // Convert to centimeters
}

void loop() {
  // Sweep from left (-90°) to right (+90°)
  for (int angle = 0; angle <= 180; angle += 2) {
    myServo.write(angle);  // Move servo to specified angle
    delay(30);             // Wait for servo to move

    float distance = readDistanceCM(); // Measure distance
    int realAngle = angle - 90;        // Convert angle to range: -90 to +90

    // Send data in CSV format: angle,distance
    Serial.print(realAngle);
    Serial.print(",");
    Serial.println(distance);

    delay(50);  // Small delay before next reading
  }

  // Sweep back from right (+90°) to left (-90°)
  for (int angle = 180; angle >= 0; angle -= 2) {
    myServo.write(angle);
    delay(30);

    float distance = readDistanceCM();
    int realAngle = angle - 90;

    Serial.print(realAngle);
    Serial.print(",");
    Serial.println(distance);

    delay(50);
  }
}
