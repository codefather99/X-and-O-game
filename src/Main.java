import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.util.List;

public class Main {

    static JButton[] buttons = new JButton[9];
    static boolean isXTurn = true;
    static boolean gameOver = false;
    static String gameMode = "";
    static Player playerX = new Player("X", "X");
    static Player playerO = new Player("O", "O");
    static int XScore = 0;
    static int OScore = 0;
    static int scoreDraws = 0;

    static JFrame frame;
    static JLabel statusLabel;
    static JButton playAgainButton;

    static Color backgroundColor = new Color(25, 15, 45);
    static Color panelColor = new Color(40, 25, 65);
    static Color xColor = new Color(70, 150, 255);
    static Color oColor = new Color(255, 80, 80);
    static Color borderColor = new Color(180, 80, 255);

    public static void main(String[] args) {
        Database.initialize();

        frame = new JFrame("Tic-Tac-Toe/X and O");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(backgroundColor);
        frame.setLayout(new BorderLayout());

        showStartScreen();

        frame.setVisible(true);
    }

    static void showStartScreen() {
        frame.getContentPane().removeAll();

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3, 1, 20, 20));
        startPanel.setBackground(backgroundColor);
        startPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));

        JButton vsComputerButton = new JButton("Play vs Computer");
        JButton vsPlayerButton = new JButton("Play vs Player");
        JButton leaderboardButton = new JButton("Leaderboard");

        styleMenuButton(vsComputerButton);
        styleMenuButton(vsPlayerButton);
        styleMenuButton(leaderboardButton);

        vsComputerButton.addActionListener(e -> startGame("computer"));
        vsPlayerButton.addActionListener(e -> showPlayerSetupScreen());
        leaderboardButton.addActionListener(e -> showLeaderboardScreen());

        startPanel.add(vsComputerButton);
        startPanel.add(vsPlayerButton);
        startPanel.add(leaderboardButton);

        frame.add(startPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    static void showPlayerSetupScreen(){

        frame.getContentPane().removeAll();

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(4, 1, 10, 10));
        startPanel.setBackground(backgroundColor);
        startPanel.setBorder(BorderFactory.createEmptyBorder(80,40,80,40));

        JLabel jlab = new JLabel("Enter the names to be used for players X and O below: ");
        JTextField inputplayerXName = new JTextField();
        JTextField inputplayerOName = new JTextField();

        JButton startPVP = new JButton("Start PvP game");

        startPanel.add(jlab);
        startPanel.add(inputplayerXName);
        startPanel.add(inputplayerOName);
        startPanel.add(startPVP);

        playerX.setName(inputplayerXName.getText());
        playerO.setName(inputplayerOName.getText());

        startPVP.addActionListener(e -> {
            playerX.setName(inputplayerXName.getText());
            playerO.setName(inputplayerOName.getText());
            startGame("player");
        });

        frame.add(startPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    static void showLeaderboardScreen() {
        frame.getContentPane().removeAll();

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BorderLayout());
        leaderboardPanel.setBackground(backgroundColor);
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        leaderboardPanel.add(title, BorderLayout.NORTH);

        List<Playerscore> scores = Scoredao.getLeaderboard();

        String[] columns = {"Player", "Wins", "Losses", "Draws"};
        String[][] rows = new String[scores.size()][4];
        for (int i = 0; i < scores.size(); i++) {
            Playerscore ps = scores.get(i);
            rows[i][0] = ps.getName();
            rows[i][1] = String.valueOf(ps.getWins());
            rows[i][2] = String.valueOf(ps.getLosses());
            rows[i][3] = String.valueOf(ps.getDraws());
        }

        JTable table = new JTable(rows, columns);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleMenuButton(backButton);
        backButton.addActionListener(e -> showStartScreen());
        leaderboardPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(leaderboardPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
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

        statusLabel = new JLabel(playerX.getName() + "'s turn");
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

        if (gameMode.equals("player")){
            //player vs player mode logic
            if (isXTurn) {
                square.setText("X");
                square.setForeground(xColor);
            } else {
                square.setText("O");
                square.setForeground(oColor);
            }

            isXTurn = !isXTurn;
            statusLabel.setText((isXTurn ? playerX.getName() : playerO.getName()) + "'s turn");

        } else {
            //player vs AI mode logic
            square.setText("X");
            square.setForeground(xColor);

            if (GameFlow.checkWin(buttons) == null && !GameFlow.isBoardFull(buttons)) {
                Computer.makeMove(buttons);
            }
        }

        String winner = GameFlow.checkWin(buttons);

        if (winner != null) {
            gameOver = true;

            if (gameMode.equals("player")) {
                Player winningPlayer = winner.equals(playerX.getMark()) ? playerX : playerO;
                Player losingPlayer = winner.equals(playerX.getMark()) ? playerO : playerX;
                statusLabel.setText(winningPlayer.getName() + " wins!");
                Scoredao.recordWin(winningPlayer.getName(), losingPlayer.getName());
            } else {
                String winnerLabel = winner.equals("X") ? playerX.getName() : "Computer";
                String loserLabel = winner.equals("X") ? "Computer" : playerX.getName();
                statusLabel.setText(winnerLabel + " wins!");
                Scoredao.recordWin(winnerLabel, loserLabel);
            }

            playAgainButton.setVisible(true);
        } else if (GameFlow.isBoardFull(buttons)) {
            gameOver = true;
            statusLabel.setText("It's a draw!");

            if (gameMode.equals("player")) {
                Scoredao.recordDraw(playerX.getName(), playerO.getName());
            } else {
                Scoredao.recordDraw(playerX.getName(), "Computer");
            }

            playAgainButton.setVisible(true);
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