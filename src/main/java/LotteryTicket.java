import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LotteryTicket {

    Long ticketId;
    private final Set<Integer> numbers = new HashSet<>();


    public Set<Integer> getNumbers() {
        return numbers;
    }

    public Long getTicketId() {
        return ticketId;
    }


    public LotteryTicket(long ticketId, List<Integer> numbers) {
        this.numbers.addAll(numbers);
        this.ticketId = ticketId;
    }

}
