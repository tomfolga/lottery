import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class LotteryTicketProcessor {

    private final int numberPickCount;
    private final int totalNumberCount;

    // Please note it's zero base so for number 1 we have list of tickets with this number in index 0
    private final ArrayList<ArrayList<LotteryTicket>> ticketsForEachNumberPicked;
    private final static Comparator<LotteryTicket> ticketComparator = Comparator.comparing(LotteryTicket::getTicketId);

    public LotteryTicketProcessor(int numberPickCount, int totalNumberCount) {
        if (numberPickCount < 1 || numberPickCount >= totalNumberCount) {
            throw new IllegalArgumentException("numberPickCount must be between 1 and " + totalNumberCount);
        }
        this.numberPickCount = numberPickCount;
        this.totalNumberCount = totalNumberCount;
        this.ticketsForEachNumberPicked = new ArrayList<>(totalNumberCount);
        for (int i = 0; i < totalNumberCount; i++) {
            ticketsForEachNumberPicked.add(new ArrayList<>());
        }
    }


    private long ticketSeqId = -1;

    public void startNewGame() {
        ticketSeqId = 0;
    }

    public LotteryResult endGame(Integer... winningNumbers) {
        validateNumbers(winningNumbers);
        for (ArrayList<LotteryTicket> list : ticketsForEachNumberPicked) {
            list.sort(ticketComparator);
        }
        LotteryWinningTicketFinder finder = new LotteryWinningTicketFinder(winningNumbers);
        LotteryResult result = new LotteryResult();
        do {
            List<TicketResult> matches = finder.getMatches();
            matches.forEach(result::addWinning);
        } while (finder.findWinningNumberWithLowestTickedIdAndMoveToNext());
        ticketSeqId = -1;
        return result;
    }


    private void validateNumbers(Integer... numbers) {
        if (ticketSeqId == -1) {
            throw new IllegalStateException("The games has ended or not started yet.");
        }
        if (numbers.length != numberPickCount) {
            throw new IllegalStateException(format("Please select exacly %d numbers", numberPickCount));
        }
        for (int number : numbers) {
            if (number < 1 || number > totalNumberCount) {
                throw new IllegalStateException(format("Selected number %d is out of range. Please select numbers between 1 and %d", number, totalNumberCount));
            }
        }
        HashSet<Integer> uniqueNumbers = new HashSet<>(Arrays.asList(numbers));
        if (uniqueNumbers.size() != numbers.length) {
            throw new IllegalStateException("Duplicated numbers");
        }
    }

    public LotteryTicket issueTicket(Integer... pickedNumbers) {
        if (ticketSeqId == -1) {
            throw new IllegalStateException("Game has not started");
        }
        validateNumbers(pickedNumbers);
        ticketSeqId++;
        LotteryTicket ticket = new LotteryTicket(ticketSeqId, Arrays.asList(pickedNumbers));
        for (int pickedNumber : ticket.getNumbers()) {
            ArrayList<LotteryTicket> lotteryTickets = ticketsForEachNumberPicked.get(pickedNumber - 1);
            lotteryTickets.add(ticket);
        }
        return ticket;
    }

    private class LotteryWinningTicketFinder {

        private final TreeMap<Integer, Integer> ticketIndexForWinningNumber = new TreeMap<>();

        LotteryWinningTicketFinder(Integer... winningNumbers) {
            for (Integer winningNumber : winningNumbers) {
                ticketIndexForWinningNumber.put(winningNumber, 0);
            }
        }

        public List<TicketResult> getMatches() {
            Map<Long, Set<Integer>> result = new HashMap<>();
            for (Map.Entry<Integer, Integer> numberWithIndex : ticketIndexForWinningNumber.entrySet()) {
                ArrayList<LotteryTicket> ticketsWithNumber = ticketsForEachNumberPicked.get(numberWithIndex.getKey() - 1);
                LotteryTicket lotteryTicket = ticketsWithNumber.get(numberWithIndex.getValue());
                Set<Integer> matchedNumers = result.computeIfAbsent(lotteryTicket.getTicketId(), k -> new TreeSet<>());
                matchedNumers.add(numberWithIndex.getKey());
            }
            return result.entrySet().stream().map((e) -> new TicketResult(e.getKey(), e.getValue())).collect(Collectors.toList());
        }


        boolean findWinningNumberWithLowestTickedIdAndMoveToNext() {
            long lowestTicketId = -1;
            int winningNumberToMove = -1;
            for (Map.Entry<Integer, Integer> numberWithIndex : ticketIndexForWinningNumber.entrySet()) {
                ArrayList<LotteryTicket> ticketsWithNumber = ticketsForEachNumberPicked.get(numberWithIndex.getKey() - 1);
                LotteryTicket lotteryTicket = ticketsWithNumber.get(numberWithIndex.getValue());
                if ((numberWithIndex.getValue() < ticketsWithNumber.size() - 1) && (lotteryTicket.getTicketId() < lowestTicketId || lowestTicketId == -1)) {
                    lowestTicketId = lotteryTicket.getTicketId();
                    winningNumberToMove = numberWithIndex.getKey();
                }
            }
            if (winningNumberToMove != -1) {
                ticketIndexForWinningNumber.compute(winningNumberToMove, (Integer k, Integer v) -> v + 1);
                return true;
            } else {
                return false;
            }

        }

        public void printCurrentStep() {
            System.out.println("Current  step");
            for (Map.Entry<Integer, Integer> numberWithIndex : ticketIndexForWinningNumber.entrySet()) {
                ArrayList<LotteryTicket> ticketsWithNumber = ticketsForEachNumberPicked.get(numberWithIndex.getKey() - 1);
                List<String> list = new ArrayList<>();
                for (int j = 0; j < ticketsWithNumber.size(); j++) {
                    String s = ticketsWithNumber.get(j).getTicketId().toString();
                    if (j == (numberWithIndex.getValue())) {
                        list.add("(" + s + ")");
                    } else {
                        list.add(s);
                    }
                }
                System.out.println(numberWithIndex.getKey() + " " + String.join(",", list));
            }
        }

        public void printTickets() {
            for (int i = 0; i < ticketsForEachNumberPicked.size(); i++) {
                ArrayList<LotteryTicket> lotteryTickets = ticketsForEachNumberPicked.get(i);
                if (lotteryTickets.size() > 0) {
                    String collect = lotteryTickets.stream().map(x -> x.getTicketId().toString()).collect(Collectors.joining(","));
                    System.out.println((i + 1) + " " + collect);
                }
            }
        }
    }

}
