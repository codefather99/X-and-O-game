import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;

public class Main {

    static JButton[] buttons = new JButton[9];
    static boolean isXTurn = true;
    static boolean gameOver = false;
    static String gameMode = "";

    static JFrame frame;
    static JLabel statusLabel;
    static JButton playAgainButton;

    static Color backgroundColor = new Color(25, 15, 45);
    static Color panelColor = new Color(40, 25, 65);
    static Color xColor = new Color(70, 150, 255);
    static Color oColor = new Color(255, 80, 80);
    static Color borderColor = new Color(180, 80, 255);

    public static void main(String[] args) {
        frame = new JFrame("Tic-Tac-Toe/X and O");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(backgroundColor);
        frame.setLayout(new BorderLayout());

        showStartScreen();

        frame.setVisible(true);
    }

    static void showStartScreen() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(2, 1, 20, 20));
        startPanel.setBackground(backgroundColor);
        startPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));

        JButton vsComputerButton = new JButton("Play vs Computer");
        JButton vsPlayerButton = new JButton("Play vs Player");

        styleMenuButton(vsComputerButton);
        styleMenuButton(vsPlayerButton);

        vsComputerButton.addActionListener(e -> startGame("computer"));
        vsPlayerButton.addActionListener(e -> startGame("player"));

        startPanel.add(vsComputerButton);
        startPanel.add(vsPlayerButton);

        frame.add(startPanel, BorderLayout.CENTER);
    }

    static void styleMenuButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(panelColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
    }

    static void startGame(String mode) {
        gameMode = mode;

        frame.getContentPane().removeAll();

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 22));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        frame.add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 8, 8));
        boardPanel.setBackground(backgroundColor);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 9; i++) {
            JButton square = new JButton("");
            square.setFont(new Font("Arial", Font.BOLD, 60));
            square.setBackground(panelColor);
            square.setFocusPainted(false);
            square.setBorder(BorderFactory.createLineBorder(borderColor, 3));
            buttons[i] = square;

            square.addActionListener(e -> handleClick(square));

            boardPanel.add(square);
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        playAgainButton = new RoundedButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 18));
        playAgainButton.setBackground(panelColor);
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(e -> resetBoard());
        frame.add(playAgainButton, BorderLayout.SOUTH);

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
            square.setForeground(xColor);
        } else {
            square.setText("O");
            square.setForeground(oColor);
        }

        isXTurn = !isXTurn;

        String winner = GameFlow.checkWin(buttons);
        if (winner != null) {
            gameOver = true;
            statusLabel.setText(winner + " wins!");
            playAgainButton.setVisible(true);
        } else if (GameFlow.isBoardFull(buttons)) {
            gameOver = true;
            statusLabel.setText("It's a draw!");
            playAgainButton.setVisible(true);
        }

        if (!gameOver && gameMode.equals("computer") && !isXTurn) {
            javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
                Computer.makeMove(buttons);
                isXTurn = !isXTurn;

                String computerWinner = GameFlow.checkWin(buttons);
                if (computerWinner != null) {
                    gameOver = true;
                    statusLabel.setText(computerWinner + " wins!");
                    playAgainButton.setVisible(true);
                } else if (GameFlow.isBoardFull(buttons)) {
                    gameOver = true;
                    statusLabel.setText("It's a draw!");
                    playAgainButton.setVisible(true);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    static void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
        }
        isXTurn = true;
        gameOver = false;
        statusLabel.setText(" ");
        playAgainButton.setVisible(false);
    }
}