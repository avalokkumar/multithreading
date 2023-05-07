package com.clay.b_thread_basics.priority_scheduling_examples;

/**
 *  Consider an autonomous driving system that requires multiple threads to manage different tasks such as sensor
 *  data acquisition, decision making, and control. In this system, the priority and scheduling of the threads can have a significant impact on the system's performance and safety.
 *
 * The sensor data acquisition thread may need to run at a higher priority and more frequently compared to the decision-making thread since the sensor data needs to be continuously collected and processed to make real-time decisions. On the other hand, the control thread may have a lower priority since its output does not directly affect the system's immediate safety.
 *
 * Additionally, the scheduling of the threads can also impact the system's performance. For example, if the decision-making thread is scheduled to run too frequently, it may waste computational resources and slow down the system's overall response time.
 *
 * By setting the thread priorities and scheduling appropriately, the autonomous driving system can ensure that critical tasks are performed with the necessary urgency while minimizing wasted resources.
 */
class SensorDataAcquisitionThread extends Thread {
    private volatile boolean running = true; // Volatile boolean flag to indicate if the thread should keep running

    public void stopRunning() {
        running = false; // Method to stop the thread by setting the running flag to false
    }

    public void run() {
        while (running) { // While the running flag is true
            System.out.println("Collect and process sensor data");
            setPriority(Thread.MAX_PRIORITY); // Set the thread priority to the maximum
            yield(); // Give an opportunity for other threads to execute
        }
    }
}

class DecisionMakingThread extends Thread {
    private volatile boolean running = true; // Volatile boolean flag to indicate if the thread should keep running

    public void stopRunning() {
        running = false; // Method to stop the thread by setting the running flag to false
    }

    public void run() {
        while (running) { // While the running flag is true
            System.out.println("Make decisions based on sensor data");
            setPriority(Thread.NORM_PRIORITY); // Set the thread priority to the normal
            yield(); // Give an opportunity for other threads to execute
        }
    }
}

class ControlThread extends Thread {
    private volatile boolean running = false; // Volatile boolean flag to indicate if the thread should keep running

    public void stopRunning() {
        running = false; // Method to stop the thread by setting the running flag to false
    }

    public void run() {
        while (running) { // While the running flag is true
            System.out.println("Control the vehicle based on decisions");
            setPriority(Thread.MIN_PRIORITY); // Set the thread priority to the minimum
            yield(); // Give an opportunity for other threads to execute
        }
    }
}

public class AutonomousDrivingSystem {
    public static void main(String[] args) {
        SensorDataAcquisitionThread sensorThread = new SensorDataAcquisitionThread(); // Create a new thread for sensor data acquisition
        DecisionMakingThread decisionThread = new DecisionMakingThread(); // Create a new thread for decision making
        ControlThread controlThread = new ControlThread(); // Create a new thread for vehicle control

        sensorThread.start(); // Start the sensor data acquisition thread
        decisionThread.start(); // Start the decision making thread
        controlThread.start(); // Start the control thread

        // Let the threads run for a certain duration
        try {
            Thread.sleep(5000); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the threads by calling the stopRunning method
        sensorThread.stopRunning();
        decisionThread.stopRunning();
        controlThread.stopRunning();
    }
}