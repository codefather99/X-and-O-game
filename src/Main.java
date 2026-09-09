import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Font;

public class Main {

    static JButton[] buttons = new JButton[9];
    static boolean isXTurn = true;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic-Tac-Toe/X and O");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            JButton square = new JButton("");
            square.setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i] = square;

            square.addActionListener(e -> handleClick(square));

            boardPanel.add(square);
        }

        frame.add(boardPanel);
        frame.setVisible(true);
    }

    static void handleClick(JButton square) {
        if (!square.getText().isEmpty()) {
            return;
        }

        if (isXTurn) {
            square.setText("X");
        } else {
            square.setText("O");
        }

        isXTurn = !isXTurn;
    }
}