import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EmergencyAlert {
    private double heartRate;
    private double bloodPressure;
    s
    private static final double CRITICAL_HEART_RATE = 120.0;
    private static final double CRITICAL_BLOOD_PRESSURE = 180.0;

    public EmergencyAlert(double heartRate, double bloodPressure) {
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
    }

    public void checkAndTriggerAlert() {
        try {
            if (heartRate > CRITICAL_HEART_RATE || bloodPressure > CRITICAL_BLOOD_PRESSURE) {
                System.out.println("Critical vitals detected! Triggering emergency alert.");
                String message = "Emergency Alert: Patient vitals are abnormal. HR: "
                        + heartRate + ", BP: " + bloodPressure;
                NotificationService.sendAlert(message);
            } else {
                System.out.println("Vitals are within normal ranges.");
            }
        } catch (Exception e) {
            System.err.println("Error triggering emergency alert: " + e.getMessage());
        }
    }
}

class NotificationService {
    public static void sendAlert(String message) {
        List<Notifiable> channels = new ArrayList<>();
        channels.add(new EmailNotification("doctor@example.com"));
        channels.add(new SMSNotification("+1234567890"));

        for (Notifiable channel : channels) {
            channel.notifyUser(message);
        }
    }
}

class PanicButton {
    public void pressButton() {
        System.out.println("Panic button activated by the patient!");
        EmergencyAlert alert = new EmergencyAlert(130.0, 190.0);
        alert.checkAndTriggerAlert();
    }
}

class ChatServer {
    private List<String> messages;

    public ChatServer() {
        messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
        System.out.println("New message on ChatServer: " + message);
    }

    public List<String> getMessages() {
        return messages;
    }
}

class ChatClient {
    private String username;
    private ChatServer server;

    public ChatClient(String username, ChatServer server) {
        this.username = username;
        this.server = server;
    }

    public void sendMessage(String message) {
        String formattedMessage = username + ": " + message;
        server.addMessage(formattedMessage);
    }

    public void receiveMessages() {
        System.out.println(username + " is retrieving messages:");
        for(String msg : server.getMessages()){
            System.out.println(msg);
        }
    }
}

class VideoCall {
    private String meetingLink;

    public VideoCall(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public void startCall() {
        System.out.println("Starting video call. Please join the meeting using this link: " + meetingLink);
    }
}

interface Notifiable {
    void notifyUser(String message);
}

class EmailNotification implements Notifiable {
    private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    @Override
    public void notifyUser(String message) {
        System.out.println("Sending Email to " + email + ": " + message);
    }
}

class SMSNotification implements Notifiable {
    private String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void notifyUser(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

class ReminderService {

    public void sendAppointmentReminder(String appointmentDetails) {
        String message = "Reminder: " + appointmentDetails;
        Notifiable email = new EmailNotification("patient@example.com");
        Notifiable sms = new SMSNotification("+0987654321");

        email.notifyUser(message);
        sms.notifyUser(message);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EmergencyAlert emergencyAlert = new EmergencyAlert(110.0, 170.0);
        PanicButton panicButton = new PanicButton();

        ChatServer chatServer = new ChatServer();
        ChatClient doctor = new ChatClient("Dr. Smith", chatServer);
        ChatClient patient = new ChatClient("Patient John", chatServer);

        // Create a ReminderService for sending notifications.
        ReminderService reminderService = new ReminderService();

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Remote Health Monitoring System ===");
            System.out.println("Select an option:");
            System.out.println("1. Check Vitals and Trigger Emergency Alert");
            System.out.println("2. Press Panic Button");
            System.out.println("3. Chat between Doctor and Patient");
            System.out.println("4. Start a Video Call");
            System.out.println("5. Send Appointment Reminder");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    System.out.println("\n[Emergency Alert] Checking vitals...");
                    emergencyAlert.checkAndTriggerAlert();
                    break;
                case 2:
                    System.out.println("\n[Panic Button] Activating panic button...");
                    panicButton.pressButton();
                    break;
                case 3:
                    System.out.println("\n[Chat Module] Enter a message from the patient:");
                    String patientMessage = scanner.nextLine();
                    patient.sendMessage(patientMessage);

                    System.out.println("Enter a message from the doctor:");
                    String doctorMessage = scanner.nextLine();
                    doctor.sendMessage(doctorMessage);

                    System.out.println("\nCurrent Chat Messages:");
                    for (String msg : chatServer.getMessages()) {
                        System.out.println(msg);
                    }
                    break;
                case 4:
                    System.out.println("\n[Video Call] Enter a video call meeting link (e.g., https://meet.example.com/abc123):");
                    String meetingLink = scanner.nextLine();
                    VideoCall videoCall = new VideoCall(meetingLink);
                    videoCall.startCall();
                    break;
                case 5:
                    System.out.println("\n[Reminder Service] Enter appointment details (e.g., 'Your appointment is scheduled on 22-Apr-2025 at 10:00 AM.'):");
                    String appointmentDetails = scanner.nextLine();
                    reminderService.sendAppointmentReminder(appointmentDetails);
                    break;
                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Please choose a valid option.");
            }
            System.out.println("------------------------------------------");
        }

    }
}