import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LotteryTest {

    @Test
    public void canFindOneWinningTicket() {
        Processor p = new Processor(49);
        p.addTicket(new Ticket(1L, 1, 4, 3, 45, 34, 6));
        p.addTicket(new Ticket(2L, 1, 2, 4, 9, 43, 49));
        p.addTicket(new Ticket(3L, 3, 5, 1, 4, 34, 7));
        p.addTicket(new Ticket(4L, 1, 2, 4, 5, 43, 49));
        p.preProcess(1, 2, 43, 4, 5, 49);
        LotteryResult result = p.process();
        assertEquals(List.of(4L),
                result.getWinningTicketIds(6));
        assertEquals(List.of(2L),
                result.getWinningTicketIds(5));
    }

    @Test
    public void multipleWinners() {
        Processor p = new Processor(49);
        p.addTicket(new Ticket(1L, 1, 2, 3, 4, 5, 6));
        p.addTicket(new Ticket(2L, 1, 2, 3, 4, 5, 6));
        p.addTicket(new Ticket(3L, 2, 3, 4, 5, 6, 7));
        p.addTicket(new Ticket(4L, 2, 3, 4, 5, 6, 7));
        p.preProcess(1, 2, 3, 4, 5, 6);
        LotteryResult result = p.process();
        assertEquals(List.of(1L, 2L),
                result.getWinningTicketIds(6));
        assertEquals(List.of(3L, 4L),
                result.getWinningTicketIds(5));
    }


    @Test
    public void testDoubleCounting() {
        Processor p = new Processor(49);
        p.addTicket(new Ticket(1L, 6, 9, 15, 27, 28, 42));
        p.addTicket(new Ticket(2L, 4, 18, 19, 31, 38, 48));
        p.addTicket(new Ticket(3L, 9, 14, 18, 22, 30, 45));
        p.addTicket(new Ticket(4L, 1, 6, 19, 22, 27, 37));
        p.preProcess(6, 9, 15, 27, 28, 42);
        LotteryResult result = p.process();
        assertEquals(List.of(1L), result.getWinningTicketIds(6));
        assertEquals(List.of(4L), result.getWinningTicketIds(2));
        assertEquals(List.of(3L), result.getWinningTicketIds(1));
    }

    @Test
    public void testAllCombinationTicketsCategories() {
        // Test tickets with all combinations of number for a game of (5 balls out of 20) (6 out of 49 would not fit into memory)
        // Each ticket is checked whether it
        Processor p = new Processor(20);
        ArrayList<Ticket> tickets = new ArrayList<>();
        Combinations.generate(p.getTotalNumberCount(), 5, (combinationId, combinations) -> tickets.add(new Ticket(combinationId, combinations)));
        tickets.forEach(p::addTicket);
        p.preProcess(3, 4, 5, 7, 12);
        LotteryResult result = p.process();
        tickets.forEach(ticket -> {
            int category = ticketCategory(ticket, p.getWinningNumbersSet());
            if (category > 0) {
                List<Long> winningTicketForCategory = result.getWinningTicketIds(category);
                assertTrue(String.format("Ticket %s should be in category %d for winningNumbers %s", ticket.toString(), category, p.getWinningNumbersSet()), winningTicketForCategory.contains(ticket.getTicketId()));
            }
        });
    }

    // Calculate ticket winning category independently of the rest of code
    private int ticketCategory(Ticket ticket, Set<Integer> winningNumbers) {
        int category = 0;
        for (Integer winningNumner : winningNumbers) {
            if (ticket.getNumbers().contains(winningNumner)) {
                category++;
            }
        }
        return category;
    }

}
