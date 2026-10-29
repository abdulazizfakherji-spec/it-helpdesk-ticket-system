
public class Ticket {
    private final int id;
    private String title;
    private String priority;
    private String status;

    public Ticket(int id, String title, String priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = "Open";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket #" + id
                + " | " + title
                + " | Priority: " + priority
                + " | Status: " + status;
    }
}
