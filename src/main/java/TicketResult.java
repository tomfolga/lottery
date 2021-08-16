import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

class TicketResult {
    private final Long ticketId;
    private final TreeSet<Integer> allMatchedNumbers = new TreeSet<>();

    public TicketResult(Long ticketId, Collection<Integer> allMatchedNumbers) {
        this.ticketId = ticketId;
        this.allMatchedNumbers.addAll(allMatchedNumbers);

    }

    public int matchCount() {
        return allMatchedNumbers.size();
    }

    public Long getTicketId() {
        return ticketId;
    }

    public TreeSet<Integer> getAllMatchedNumbers() {
        return allMatchedNumbers;
    }

    public static TicketResult of(Long ticketId, Integer... list) {
        return new TicketResult(ticketId, Arrays.asList(list));
    }

     @Override
    public String toString() {
        return "TicketResult{" +
                "ticketId=" + ticketId +
                ", allMatchedNumbers=" + allMatchedNumbers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketResult that = (TicketResult) o;
        return Objects.equals(ticketId, that.ticketId) && Objects.equals(allMatchedNumbers, that.allMatchedNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, allMatchedNumbers);
    }
}
