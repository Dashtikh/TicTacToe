import java.util.Scanner;

public class Main {
    public static TTT ttt = new TTT();

    public static void main(String[] args) {
        play();


    }

    public static boolean win() {
        int[][] win_state = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int i = 0; i < 8; i++) {
            boolean win = true;
            int fr = win_state[i][0] / 3, fc = win_state[i][0] % 3;
            for (int j = 0; j < 3; j++) {
                int row = win_state[i][j] / 3, column = win_state[i][j] % 3;
                if (ttt.getGrid()[fr][fc] == ' ' || ttt.getGrid()[fr][fc] != ttt.getGrid()[row][column]) {
                    win = false;

                }

            }
            if (win) return true;

        }
        return false;

    }

    public static boolean tie() {
        if (win()) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ttt.getGrid()[i][j] == ' ') {
                    return false;
                }

            }

        }
        return true;

    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (i != 0) {
                System.out.print("---+---+---\n");
            }
            for (int j = 0; j < 3; j++) {
                if (j != 0) {
                    System.out.print("|");
                }
                System.out.print(' ');
                if (board[i][j] == ' ') {
                    System.out.print(3 * i + j + 1);
                } else System.out.print(board[i][j]);

                System.out.print(' ');

            }
            System.out.print(' ');
            System.out.print("\n");
        }
    }

    public static void play() {
        char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        ttt.setGrid(board);
        while (true) {
            System.out.print("choose a symbol, X or O: ");
            Scanner scanner = new Scanner(System.in);
            ttt.setPlayer(scanner.next().charAt(0));
            if (ttt.getPlayer() == 'X' || ttt.getPlayer() == 'O')
                break;
        }
        if (ttt.getPlayer() == 'X')
            ttt.setComputer('O');
        else ttt.setComputer('X');
        if (ttt.getPlayer()=='O'){
            moveCpu();
        }
        printBoard(ttt.getGrid());
        while (true) {
            movePlayer();
            printBoard(ttt.getGrid());
            if (win()) {
                System.out.println("Player won!");
                return;
            } else if (tie()) {
                System.out.println("Tie");
                return;
            }
            System.out.println("cpu turn");
            moveCpu();
            printBoard(ttt.getGrid());
            if (win()) {
                System.out.println("Cpu won!");
                return;
            } else if (tie()) {
                System.out.println("Tie");
                return;
            }
        }
    }

    public static void movePlayer() {
        char[][] board = ttt.getGrid();
        while (true) {
            System.out.print("choose a place from 1 to 9: ");
            int location;
            Scanner scanner = new Scanner(System.in);
            location = scanner.nextInt();
            int row = (location - 1) / 3, column = (location - 1) % 3;
            if (location >= 1 && location <= 9 && ttt.getGrid()[row][column] == ' ') {
                board[row][column] = ttt.getPlayer();
                ttt.setGrid(board);
                break;
            }

        }

    }

    public static void moveCpu() {
        char[][] board = ttt.getGrid();
        Move bestMove = minimax(true);
        board[bestMove.getRow()][bestMove.getColumn()]= ttt.getComputer();
        ttt.setGrid(board);
    }

    public static Move minimax(boolean maximizing_player) {
        char[][] board = ttt.getGrid();
        Move best_move = new Move();
        if (win()) {
            if (maximizing_player) {
                best_move.setScore(-1);
            } else {
                best_move.setScore(1);
            }
            return best_move;
        } else if (tie()) {
            best_move.setScore(0);
            return best_move;
        }

        best_move.setScore(maximizing_player ? -2 : 2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ttt.getGrid()[i][j] == ' ') {
                    board[i][j] = maximizing_player ? ttt.getComputer() : ttt.getPlayer();
                    Move boardstate = minimax(!maximizing_player);

                    if (maximizing_player) {
                        if (boardstate.getScore() > best_move.getScore()) {
                            best_move.setScore(boardstate.getScore());
                            best_move.setRow(i);
                            best_move.setColumn(j);
                        }
                    } else {
                        if (boardstate.getScore() < best_move.getScore()) {
                            best_move.setScore(boardstate.getScore());
                            best_move.setRow(i);
                            best_move.setColumn(j);
                        }
                    }
                    board[i][j] = ' ';
                    ttt.setGrid(board);
                }
            }
        }
        return best_move;
    }
}
