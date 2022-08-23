import java.util.ArrayList;
import java.util.List;

public class Combinations {

    interface Callback {

        void apply(long combinationNumber, int[] combinations);
    }

    public static void generate(int n, int r, Callback callback) {
        long combinationNumber = 1;
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i + 1;
        }

        while (combination[r - 1] < n) {
            callback.apply(combinationNumber, combination);
            combinationNumber++;
            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

    }
}
