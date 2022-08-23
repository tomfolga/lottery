import java.util.*;

public class Ticket {

    private final long ticketId;
    private final Set<Integer> numbers = new HashSet<>();

    public Set<Integer> getNumbers() {
        return numbers;
    }

    public Long getTicketId() {
        return ticketId;
    }


    public Ticket(long ticketId, int ... nums) {
        for (int num : nums) {
            numbers.add(num);
        }
        this.ticketId = ticketId;
    }

}
