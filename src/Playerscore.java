public class Playerscore {
    private final String name;
    private final int wins;
    private final int losses;
    private final int draws;

    public Playerscore(String name, int wins, int losses, int draws) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }
}