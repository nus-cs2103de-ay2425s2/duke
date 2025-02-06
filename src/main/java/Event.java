class Event extends Task {
    String from, to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getType() {
        return "E"; // "E" for Event
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + getStatus() + " (from: " + from + " to: " + to + ")";
    }
}
