import processing.serial.*; // Import serial library to receive data from ESP8266
Serial myPort;              // Serial port object

// Class to represent a radar detection point (echo)
class EchoPoint {
  PVector pos;  // Position of the point (x, y) in radar space
  int alpha;    // Transparency value (used for fading effect)

  EchoPoint(PVector p) {
    pos = p.copy();  // Copy the position from input
    alpha = 255;     // Fully opaque at start
  }

  void update() {
    alpha -= 2;  // Gradually reduce transparency to fade out (lower = slower fade)
  }

  boolean isVisible() {
    return alpha > 0;  // If alpha is still above 0, keep drawing it
  }

  void display() {
    fill(0, 255, 0, alpha); // Green color with current transparency
    noStroke();             // No border
    ellipse(pos.x, pos.y, 6, 6); // Draw a small circle at the radar location
  }
}

ArrayList<EchoPoint> allPoints = new ArrayList<EchoPoint>(); // List of all echo points (with trail)

float angle = 0;     // Current angle received from ESP8266
float distance = 0;  // Current distance received from ESP8266

void setup() {
  size(600, 600);        // Window size
  smooth();              // Enable anti-aliasing
  myPort = new Serial(this, Serial.list()[0], 115200); // Open serial connection
  myPort.bufferUntil('\n'); // Read serial data line by line
}

void draw() {
  background(0);           // Black background
  translate(width/2, height/2); // Move origin to center
  rotate(-HALF_PI);        // Rotate radar view 90° to the left for alignment

  drawRadarGrid();         // Draw grid and angle lines
  drawSweep();             // Draw radar sweeping line
  drawEchoPoints();        // Draw all detected points
  drawTextOverlay();       // Display text info (angle, distance)
}

// Draws circular radar range rings and angle lines
void drawRadarGrid() {
  stroke(0, 255, 0, 80); // Green lines with some transparency
  noFill();

  for (int r = 100; r <= 250; r += 50) {
    ellipse(0, 0, r * 2, r * 2); // Range rings every 50 pixels
  }

  for (int a = -90; a <= 90; a += 30) {
    float rad = radians(a);
    line(0, 0, 250 * cos(rad), 250 * sin(rad)); // Angle lines every 30°
  }
}

// Draw the radar sweep line
void drawSweep() {
  float sweepAngle = radians(-angle); // Mirror the angle for rotation alignment
  stroke(0, 255, 0, 150); // Green sweeping line
  strokeWeight(2);
  line(0, 0, 250 * cos(sweepAngle), 250 * sin(sweepAngle));
}

// Draw all points and update their fading
void drawEchoPoints() {
  for (int i = allPoints.size() - 1; i >= 0; i--) {
    EchoPoint p = allPoints.get(i);
    p.display();  // Draw the point
    p.update();   // Fade it out slowly
    if (!p.isVisible()) {
      allPoints.remove(i); // Remove if completely transparent
    }
  }
}

// Display angle and distance information as text
void drawTextOverlay() {
  resetMatrix();           // Reset transform to top-left corner
  fill(0, 255, 0);         // Green text
  textAlign(LEFT);
  textSize(14);
  text("Radar View (Simple Mode)", 20, 20);
  text("Angle: " + int(angle) + "°", 20, 40);
  text("Distance: " + nf(distance, 0, 2) + " cm", 20, 60);
}

// Handle incoming serial data from ESP8266
void serialEvent(Serial myPort) {
  String data = myPort.readStringUntil('\n'); // Read one line
  if (data != null) {
    data = trim(data);
    String[] parts = split(data, ","); // Expecting format: angle,distance
    if (parts.length == 2) {
      try {
        angle = float(parts[0]);   // Parse angle
        distance = float(parts[1]); // Parse distance

        float r = constrain(distance, 0, 250); // Limit radius to 250
        float rad = radians(-angle);          // Convert angle for display
        float x = r * cos(rad);               // Convert to x position
        float y = r * sin(rad);               // Convert to y position

        allPoints.add(new EchoPoint(new PVector(x, y))); // Add new radar point
      } catch (Exception e) {
        println("Invalid data: " + data); // Show warning if parsing fails
      }
    }
  }
}
