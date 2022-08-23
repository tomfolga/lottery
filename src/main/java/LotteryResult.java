import java.util.*;

public class LotteryResult {

    private long ticketCount;
    private final Map<Integer, List<Long>> listOfTicketIdPerCategory = new TreeMap<>(Comparator.reverseOrder());


    public void addWinner(int category, long ticketId) {
        ticketCount++;
        List<Long> ticketResults = listOfTicketIdPerCategory.computeIfAbsent(category, k -> new ArrayList<>());
        ticketResults.add(ticketId);
    }

    public List<Long> getWinningTicketIds(int category) {
        return listOfTicketIdPerCategory.getOrDefault(category, List.of());
    }
}
