package com.clay.c_thread_safety_synchronization.syn_methods.phaser;

public class Main {
    public static void main(String[] args) {
        int numParticipants = 5; // Total number of participants
        ConferenceRegistrationSystem registrationSystem = new ConferenceRegistrationSystem(numParticipants);

        // Create threads for participants
        for (int i = 1; i <= numParticipants; i++) {
            final String participantName = "Participant " + i;
            Thread thread = new Thread(() -> registrationSystem.registerParticipant(participantName));
            thread.start();
        }

        // Start the conference when all participants have arrived
        registrationSystem.startConference();
    }
}
