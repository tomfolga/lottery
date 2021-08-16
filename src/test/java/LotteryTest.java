import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LotteryTest {

    @Test
    public void canFindOneWinningTicket() {
        LotteryTicketProcessor p = new LotteryTicketProcessor(6, 49);

        p.startNewGame();
        p.issueTicket(1, 4, 3, 45, 34, 6);
        p.issueTicket(1, 2, 4, 9, 43, 49);
        p.issueTicket(3, 5, 1, 4, 34, 7);
        p.issueTicket(1, 2, 4, 5, 43, 49);

        LotteryResult result = p.endGame(1, 2, 43, 4, 5, 49);


        assertEquals(List.of(
                        TicketResult.of(4L, 1, 2, 4, 5, 43, 49)),
                result.getWinningTickets(6));
        assertEquals(List.of(
                        TicketResult.of(2L, 1, 2, 4, 43, 49)),
                result.getWinningTickets(5));


    }

    @Test
    public void multipleWinners() {
        LotteryTicketProcessor p = new LotteryTicketProcessor(6, 49);

        p.startNewGame();
        p.issueTicket(1, 2, 3, 4, 5, 6);
        p.issueTicket(1, 2, 3, 4, 5, 6);
        p.issueTicket(2, 3, 4, 5, 6, 7);
        p.issueTicket(2, 3, 4, 5, 6, 7);

        LotteryResult result = p.endGame(1, 2, 3, 4, 5, 6);


        assertEquals(List.of(
                        TicketResult.of(1L, 1, 2, 3, 4, 5, 6),
                        TicketResult.of(2L, 1, 2, 3, 4, 5, 6)),
                result.getWinningTickets(6));
        assertEquals(List.of(
                        TicketResult.of(3L, 2, 3, 4, 5, 6),
                        TicketResult.of(4L, 2, 3, 4, 5, 6)),
                result.getWinningTickets(5));


    }

}
