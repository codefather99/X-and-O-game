import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.Font;

public class Main {

    static JButton[] buttons = new JButton[9];
    static boolean isXTurn = true;
    static boolean gameOver = false;
    static String gameMode = "";

    static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Tic-Tac-Toe/X and O");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showStartScreen();

        frame.setVisible(true);
    }

    static void showStartScreen() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(2, 1));

        JButton vsComputerButton = new JButton("Play vs Computer");
        JButton vsPlayerButton = new JButton("Play vs Player");

        vsComputerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        vsPlayerButton.setFont(new Font("Arial", Font.PLAIN, 20));

        vsComputerButton.addActionListener(e -> startGame("computer"));
        vsPlayerButton.addActionListener(e -> startGame("player"));

        startPanel.add(vsComputerButton);
        startPanel.add(vsPlayerButton);

        frame.add(startPanel);
    }

    static void startGame(String mode) {
        gameMode = mode;

        frame.getContentPane().removeAll();

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

        frame.revalidate();
        frame.repaint();
    }

    static void handleClick(JButton square) {
        if (gameOver) {
            return;
        }

        if (!square.getText().isEmpty()) {
            return;
        }

        if (isXTurn) {
            square.setText("X");
        } else {
            square.setText("O");
        }

        isXTurn = !isXTurn;

        String winner = GameFlow.checkWin(buttons);
        if (winner != null) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, winner + " wins!");
            resetBoard();
        } else if (GameFlow.isBoardFull(buttons)) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "It's a draw!");
            resetBoard();
        }
    }

    static void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
        }
        isXTurn = true;
        gameOver = false;
    }
}