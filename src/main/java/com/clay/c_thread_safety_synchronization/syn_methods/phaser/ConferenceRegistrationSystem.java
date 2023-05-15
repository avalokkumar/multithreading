package com.clay.c_thread_safety_synchronization.syn_methods.phaser;

import java.util.concurrent.Phaser;

/**
 * In this example, the ConferenceRegistrationSystem class represents a system for registering participants for a conference. It uses a Phaser to coordinate the registration process.
 *
 * The constructor takes an argument numParticipants, which represents the total number of participants in the conference. It initializes the Phaser with an initial party count of 1 to register the main thread.
 *
 * The registerParticipant method simulates the registration process for each participant. It first registers the participant with the phaser by calling register(). After simulating the registration time, it calls arriveAndDeregister() to indicate that the participant has completed the registration and can proceed.
 *
 * The startConference method is called when all participants have arrived and completed registration. It signals the start of the conference by calling arriveAndDeregister() on the phaser to deregister the main thread.
 *
 */
public class ConferenceRegistrationSystem {
    private Phaser phaser;

    public ConferenceRegistrationSystem(int numParticipants) {
        this.phaser = new Phaser(1); // Register the main thread
    }

    public void registerParticipant(String participantName) {
        System.out.println("Participant " + participantName + " has arrived for registration.");
        phaser.register(); // Register the participant

        // Simulate participant registration time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Participant " + participantName + " has completed the registration.");

        phaser.arriveAndDeregister(); // Deregister the participant
    }

    public void startConference() {
        System.out.println("All participants have arrived. Starting the conference.");
        phaser.arriveAndDeregister(); // Deregister the main thread
    }
}
