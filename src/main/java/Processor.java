import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {


    private final int totalNumberCount;

    private final Set<Integer> winningNumbersSet = new HashSet<>();

    private final Map<Integer, Bucket> buckets;

    public int getTotalNumberCount() {
        return totalNumberCount;
    }

    public Set<Integer> getWinningNumbersSet() {
        return winningNumbersSet;
    }

    public Processor(int totalNumberCount) {
        this.totalNumberCount = totalNumberCount;
        this.buckets = new HashMap<>();
        for (int i = 1; i <= totalNumberCount; i++) {
            buckets.put(i, new Bucket());
        }
    }

    void addTicket(Ticket ticket) {
        for (Integer number : ticket.getNumbers()) {
            buckets.get(number).addTicket(ticket);
        }
    }


    void preProcess(int... winningNumbers) {
        winningNumbersSet.clear();
        for (int num : winningNumbers) {
            winningNumbersSet.add(num);
        }
        if (winningNumbers.length != winningNumbersSet.size()) {
            throw new IllegalArgumentException("Winning numbers must be unique");
        }
        for (int number = 1; number <= totalNumberCount; number++) {
            if (winningNumbersSet.contains(number)) {
                buckets.get(number).preProcess();
            } else {
                buckets.remove(number);
            }

        }
    }


    LotteryResult process() {
        LotteryResult lotteryResult = new LotteryResult();
        long lowestTicketId;
        while ((lowestTicketId = findLowestTicketId()) != -1) {
            int category = 0;
            for (Bucket bucket : buckets.values()) {
                if (bucket.getCurrentTicketId() == lowestTicketId) {
                    category++;
                }
            }
            lotteryResult.addWinner(category, lowestTicketId);
            for (Bucket bucket : buckets.values()) {
                bucket.increment(lowestTicketId);
            }
        }
        return lotteryResult;
    }

    private long findLowestTicketId() {
        long lowestTicketId = Long.MAX_VALUE;
        for (Bucket bucket : buckets.values()) {
            long currentTicketId = bucket.getCurrentTicketId();
            if (currentTicketId != -1) {
                if (currentTicketId < lowestTicketId) {
                    lowestTicketId = currentTicketId;
                }
            }
        }
        if (lowestTicketId == Long.MAX_VALUE) {
            return -1;
        } else {
            return lowestTicketId;
        }
    }

}
