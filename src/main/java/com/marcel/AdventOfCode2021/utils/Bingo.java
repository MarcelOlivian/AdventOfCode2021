package com.marcel.AdventOfCode2021.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bingo {

    private List<Short> calledNumbers = new ArrayList<>();
    private int[][][] boards;
    private List<Board> gameBoards = new ArrayList<>();


    public List<Short> getCalledNumbers() {
        return calledNumbers;
    }

    public void setCalledNumbers(List<Short> calledNumbers) {
        this.calledNumbers = calledNumbers;
    }

    public int[][][] getBoards() {
        return boards;
    }

    public void setBoards(int[][][] boards) {
        this.boards = boards;
    }

    public void initBoards() {
        for (int b = 0; b < boards.length; b++) {
            Board board = new Board();
            int[][] currentBoard = new int[5][5];
            for (int r = 0; r < boards[b].length; r++) {
                currentBoard[r] = Arrays.copyOfRange(boards[b][r], 0, boards[b][r].length);
            }
            board.setBoard(currentBoard);
            board.setBoardNum(b);
            gameBoards.add(board);
        }
    }

    public void playGame(){
        System.out.println("Start Bingo! Number of rounds: " + calledNumbers.size());
        for(int round = 0; round < calledNumbers.size(); round++){
            System.out.println("\n --------------------------| ROUND " + round + " |--------------------------");
            System.out.println("Called number: " + calledNumbers.get(round));
            int winnerBoard = markBoards(calledNumbers.get(round), round);
            if(winnerBoard > -1){
                System.out.println("\n --------------------------| WE HAVE A WINNER! " + " |--------------------------");
                System.out.println(" Board #" + winnerBoard + " wins the game! \n\n");
                break;
            }
            printBoards();
        }
        printBoards();
    }

    //go through each board and mark the called number, then check for bingo
    //return winning board number
    public int markBoards(int calledNumber, int round){
        for(Board board: gameBoards){
            board.markCalledNumber(calledNumber);
            if(round > 3 && board.isWinner()) {
                return board.getBoardNum();
            }
        }
        return -1;
    }

    public void printBoards() {
        for (Board board : gameBoards) {
            System.out.println("Board #" + board.getBoardNum());
            System.out.println(board);
        }
    }
}
