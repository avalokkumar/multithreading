package com.clay.f_thread_pools.scheduled_thread_pool;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * In this example, we have an EmailReminderScheduler class that utilizes ExecutorService with newScheduledThreadPool(int corePoolSize) to schedule email reminders for users at specific times. The scheduleReminder() method takes the recipient's email and the desired reminder time as parameters. It creates a Runnable task that represents sending the reminder email and schedules it for execution using the executor's schedule() method. The delay for each reminder is calculated based on the current time and the reminder time provided.
 *
 * The sendReminderEmail() method simulates the logic to send the reminder email to the recipient. You can replace the placeholder implementation with your actual email sending logic.
 *
 * The calculateDelay() method calculates the delay between the current time and the reminder time in seconds, ensuring a non-negative delay value.
 *
 * In the main() method, we create an instance of EmailReminderScheduler and schedule email reminders for different recipients and times using the scheduleReminder() method. The reminders are scheduled to be sent after specific durations from the current time.
 *
 * Finally, we call executor.shutdown() to gracefully shut down the executor service after all tasks have been completed.
 *
 * The newScheduledThreadPool() allows us to schedule tasks for execution at specific times or after specific delays. It provides a convenient way to handle time-based operations and automate recurring tasks, such as sending reminders, generating reports at regular intervals, or running periodic background tasks.
 *
 */
public class EmailReminderScheduler {
    private final ScheduledExecutorService executor;

    public EmailReminderScheduler() {
        executor = Executors.newScheduledThreadPool(2);
    }

    public void scheduleReminder(String recipient, LocalDateTime reminderTime) {
        Runnable reminderTask = () -> sendReminderEmail(recipient);

        long delay = calculateDelay(reminderTime);

        executor.schedule(reminderTask, delay, TimeUnit.SECONDS);
    }

    private void sendReminderEmail(String recipient) {
        System.out.println("Sending reminder email to: " + recipient +
                " at " + LocalDateTime.now() + " by " + Thread.currentThread().getName());

        // Logic to send the reminder email
        // ...
    }

    private long calculateDelay(LocalDateTime reminderTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, reminderTime);
        long delaySeconds = duration.getSeconds();
        return Math.max(delaySeconds, 0);
    }

    public static void main(String[] args) {
        EmailReminderScheduler scheduler = new EmailReminderScheduler();

        // Schedule email reminders for different recipients and times
        scheduler.scheduleReminder("user1@example.com", LocalDateTime.now().plusSeconds(10));
        scheduler.scheduleReminder("user2@example.com", LocalDateTime.now().plusMinutes(2));
        scheduler.scheduleReminder("user3@example.com", LocalDateTime.now().plusHours(1));

        // Shutdown the executor service after all tasks have been completed
        scheduler.executor.shutdown();
    }
}
