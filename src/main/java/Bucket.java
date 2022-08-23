import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bucket {

    private final List<Long> ticketIds = new ArrayList<>();
    private int currentIndex;

    public void addTicket(Ticket ticket) {
        ticketIds.add(ticket.getTicketId());
    }

    void preProcess() {
        currentIndex = 0;
        Collections.sort(ticketIds);
    }

    // used during processing
    long getCurrentTicketId() {
        if (currentIndex < ticketIds.size()) {
            return ticketIds.get(currentIndex);
        } else {
            return -1;
        }
    }

    void increment(long lowestTicketId) {
        if (getCurrentTicketId() == lowestTicketId) {
            currentIndex++;
        }
    }

}
