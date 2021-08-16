import org.junit.Assert;
import org.junit.Test;

public class LotteryEdgeCasesTest {

    @Test
    public void invalidNumberPickCountTest() {
        try {
            LotteryTicketProcessor p = new LotteryTicketProcessor(6, 5);
            Assert.fail("Expected IllegalArgumentException");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void notEnoughtNumbersOnTicketTest() {
        try {
            LotteryTicketProcessor p = new LotteryTicketProcessor(6, 59);
            p.issueTicket(1, 2);
            Assert.fail("Expected IllegalArgumentException");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void tooManyNumbersOnTicketTest() {
        try {
            LotteryTicketProcessor p = new LotteryTicketProcessor(6, 59);
            p.issueTicket(1, 2, 3, 4, 5, 6, 7);
            Assert.fail("Expected IllegalArgumentException");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void duplicatedNumbersOnTicketTest() {
        try {
            LotteryTicketProcessor p = new LotteryTicketProcessor(6, 59);
            p.issueTicket(1, 2, 3, 4, 6, 6);
            Assert.fail("Expected IllegalArgumentException");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void gameHasToStartTest() {
        try {
            LotteryTicketProcessor p = new LotteryTicketProcessor(6, 59);
            p.issueTicket(1, 2, 3, 4, 5,  6);
            Assert.fail("Expected IllegalArgumentException");
        } catch (RuntimeException e) {

        }
    }
}
