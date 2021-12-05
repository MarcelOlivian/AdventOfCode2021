package com.marcel.AdventOfCode2021;

import com.marcel.AdventOfCode2021.utils.Bingo;
import com.marcel.AdventOfCode2021.utils.FileUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Dec4th {
    public static void main(String[] args) {
        Bingo bingo = FileUtils.getBingo("testInput.txt");
        if (bingo != null) {
            bingo.initBoards();
            bingo.playGame();
        }
    }
}
