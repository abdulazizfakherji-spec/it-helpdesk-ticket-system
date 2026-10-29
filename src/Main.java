
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static final ArrayList<Ticket> tickets = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Path FILE = Paths.get("tickets.csv");
    private static int nextId = 1;

    public static void main(String[] args) {
        loadTickets();

        while (true) {
            System.out.println("\n=== IT Helpdesk Ticket System ===");
            System.out.println("1. Create ticket");
            System.out.println("2. List tickets");
            System.out.println("3. Find ticket");
            System.out.println("4. Update ticket status");
            System.out.println("5. Delete ticket");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createTicket();
                case "2" -> listTickets();
                case "3" -> findTicket();
                case "4" -> updateStatus();
                case "5" -> deleteTicket();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void createTicket() {
        System.out.print("Problem description: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Description cannot be empty.");
            return;
        }

        System.out.print("Priority (Low/Medium/High): ");
        String priority = scanner.nextLine().trim();

        if (!priority.equalsIgnoreCase("Low")
                && !priority.equalsIgnoreCase("Medium")
                && !priority.equalsIgnoreCase("High")) {
            System.out.println("Invalid priority.");
            return;
        }

        Ticket ticket = new Ticket(nextId++, title, priority);
        tickets.add(ticket);
        saveTickets();
        System.out.println("Ticket created: " + ticket);
    }

    private static void listTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        tickets.forEach(System.out::println);
    }

    private static Ticket getTicketById() {
        System.out.print("Ticket ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            for (Ticket ticket : tickets) {
                if (ticket.getId() == id) {
                    return ticket;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return null;
        }

        System.out.println("Ticket not found.");
        return null;
    }

    private static void findTicket() {
        Ticket ticket = getTicketById();

        if (ticket != null) {
            System.out.println(ticket);
        }
    }

    private static void updateStatus() {
        Ticket ticket = getTicketById();
        if (ticket == null) return;

        System.out.print("New status (Open/In Progress/Closed): ");
        String status = scanner.nextLine().trim();

        if (status.equalsIgnoreCase("Open")
                || status.equalsIgnoreCase("In Progress")
                || status.equalsIgnoreCase("Closed")) {
            ticket.setStatus(status);
            saveTickets();
            System.out.println("Status updated.");
        } else {
            System.out.println("Invalid status.");
        }
    }

    private static void deleteTicket() {
        Ticket ticket = getTicketById();
        if (ticket == null) return;

        tickets.remove(ticket);
        saveTickets();
        System.out.println("Ticket deleted.");
    }

    private static void saveTickets() {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE)) {
            for (Ticket ticket : tickets) {
                writer.write(ticket.getId() + ","
                        + escapeCsv(ticket.getTitle()) + ","
                        + escapeCsv(ticket.getPriority()) + ","
                        + escapeCsv(ticket.getStatus()));
                writer.newLine();
            }
            System.out.println("Tickets saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tickets: " + e.getMessage());
        }
    }

    private static String escapeCsv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static void loadTickets() {
        if (!Files.exists(FILE)) return;

        try {
            for (String line : Files.readAllLines(FILE)) {
                List<String> fields = parseCsvLine(line);
                if (fields.size() != 4) continue;

                try {
                    int id = Integer.parseInt(fields.get(0));
                    Ticket ticket = new Ticket(id, fields.get(1), fields.get(2));
                    ticket.setStatus(fields.get(3));
                    tickets.add(ticket);
                    nextId = Math.max(nextId, id + 1);
                } catch (NumberFormatException ignored) {
                    System.out.println("Skipping invalid ticket ID.");
                }
            }
            System.out.println("Tickets loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading tickets: " + e.getMessage());
        }
    }

    private static List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }

        fields.add(field.toString());
        return fields;
    }
}
