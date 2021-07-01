public class TTT {
    private char player;
    private char computer;
    private char grid[][] = new char[3][3];

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
    }

    public char getComputer() {
        return computer;
    }

    public void setComputer(char computer) {
        this.computer = computer;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }


}
