import javax.swing.JButton;
import java.util.Random;


public class Computer {

    static Random random = new Random();

    static void makeMove(JButton[] buttons) {
        int move = findBestMove(buttons);
        if (move != -1) {
            buttons[move].setText("O");
            buttons[move].setForeground(Main.oColor);
        }
    }

    static int findBestMove(JButton[] buttons) {
        // 1. Can I win this turn?
        int winMove = findWinningMove(buttons, "O");
        if (winMove != -1) return winMove;

        // 2. Can I block the player from winning?
        int blockMove = findWinningMove(buttons, "X");
        if (blockMove != -1) return blockMove;

        // 3. Take the center if it's free
        if (buttons[4].getText().isEmpty()) return 4;

        // 4. Take a random free corner
        int[] corners = {0, 2, 6, 8};
        shuffle(corners);
        for (int i : corners) {
            if (buttons[i].getText().isEmpty()) return i;
        }

        // 5. Take any free square
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) return i;
        }

        return -1; // board full, shouldn't happen if called correctly
    }

    static int findWinningMove(JButton[] buttons, String mark) {
        for (int[] line : GameFlow.winningLines) {
            String a = buttons[line[0]].getText();
            String b = buttons[line[1]].getText();
            String c = buttons[line[2]].getText();

            int emptyCount = 0;
            int emptyIndex = -1;
            int markCount = 0;

            String[] cells = {a, b, c};
            for (int i = 0; i < 3; i++) {
                if (cells[i].isEmpty()) {
                    emptyCount++;
                    emptyIndex = line[i];
                } else if (cells[i].equals(mark)) {
                    markCount++;
                }
            }

            if (markCount == 2 && emptyCount == 1) {
                return emptyIndex;
            }
        }
        return -1;
    }

    static void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}