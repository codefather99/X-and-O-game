import javax.swing.JButton;

public class GameFlow {

    static int[][] winningLines = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    static String checkWin(JButton[] buttons) {
        for (int[] line : winningLines) {
            int a = line[0];
            int b = line[1];
            int c = line[2];

            String textA = buttons[a].getText();
            String textB = buttons[b].getText();
            String textC = buttons[c].getText();

            if (!textA.isEmpty() && textA.equals(textB) && textA.equals(textC)) {
                return textA;
            }
        }

        return null;
    }

    static boolean isBoardFull(JButton[] buttons) {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}