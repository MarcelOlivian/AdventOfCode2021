package com.marcel.AdventOfCode2021.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    private int[][] board;
    int boardNum = 0;

    //detect if any row or column is fully marked
    //marked positions are -1
    public boolean isWinner() {
        int rowWin = 0;
        int columnWin = 0;

        for (int i = 0; i < board.length; i++) {
            rowWin = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == -1) {
                    rowWin++;
                } else {
                    rowWin = -1;
                }
                //check this column too; probably not the most efficient method with 3 loops, but fastest to come to my mind
                columnWin = 0;
                for (int k = 0; k < board.length; k++) {
                    if (board[k][j] == -1) {
                        columnWin++;
                    } else {
                        columnWin = -1;
                    }

                    if (columnWin == -1) break;
                }
                if (columnWin == 5) break;
            }
            if (rowWin == 5 || columnWin == 5) break;
        }

        return rowWin == 5 || columnWin == 5;
    }

    //mark with -1 the position of the called number
    public void markCalledNumber(int calledNumber){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j< 5; j++){
                if(board[i][j] == calledNumber){
                    board[i][j] = -1;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
                stringBuilder.append(Arrays.stream(board[i]).mapToObj(nr -> String.valueOf(nr)).collect(Collectors.joining(" "))).append("\n");
        }
        return stringBuilder.toString();
    }

    public int[][] getBoard() {return board;}

    public void setBoard(int[][] board) {this.board = board;}

    public int getBoardNum() {return boardNum;}

    public void setBoardNum(int boardNum) {this.boardNum = boardNum;}
}
