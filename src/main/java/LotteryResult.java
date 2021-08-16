import java.util.*;

public class LotteryResult {

    private final Map<Integer, List<TicketResult>> winningsByNumberOfMatchedNumbers = new TreeMap<>(Comparator.reverseOrder());

    public LotteryResult() {
    }

    public void addWinning(TicketResult ticketResult) {
        int matchedNumbersCount = ticketResult.matchCount();
        // Don't add matched result if we found a match with more numbers for this ticket
        for (Map.Entry<Integer, List<TicketResult>> results : winningsByNumberOfMatchedNumbers.entrySet()) {
            if (results.getValue().stream().anyMatch(t -> t.getTicketId().equals(ticketResult.getTicketId()) && t.matchCount() > ticketResult.matchCount())) {
                return;
            }
        }
        List<TicketResult> ticketResults = winningsByNumberOfMatchedNumbers.computeIfAbsent(matchedNumbersCount, k -> new ArrayList<>());
        ticketResults.add(ticketResult);
        // Remove any previous match with fewer matches
        for (Map.Entry<Integer, List<TicketResult>> results : winningsByNumberOfMatchedNumbers.entrySet()) {
            results.getValue().removeIf(t -> t.getTicketId().equals(ticketResult.getTicketId()) && t.matchCount() < ticketResult.matchCount());
        }


    }

    public List<TicketResult> getWinningTickets(int numberOfMatches) {
        return winningsByNumberOfMatchedNumbers.getOrDefault(numberOfMatches, List.of());
    }

    @Override
    public String toString() {
        return "LotteryResult{" +
                "winningsByNumberOfMatchedNumbers=" + winningsByNumberOfMatchedNumbers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryResult that = (LotteryResult) o;
        return Objects.equals(winningsByNumberOfMatchedNumbers, that.winningsByNumberOfMatchedNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winningsByNumberOfMatchedNumbers);
    }
}
