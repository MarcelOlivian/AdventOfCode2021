package com.marcel.AdventOfCode2021.utils;

import org.javatuples.Pair;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//probably should've used a classic loop with reader.readline() approach instead of relying on ugly stream processing with side effects (the atomic variables)
//but hey, it's a free world and this is not production code, it's for fun. So chaotic fun shall we have!
public class FileUtils {
    public static Stream<String> getLinesFromFile(String resourceUri) {
        try {
            System.out.println("File path: " + ClassLoader.getSystemResource(resourceUri).toURI().getPath());
            return Files.lines(Paths.get(ClassLoader.getSystemResource(resourceUri).toURI()), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }
        return null;
    }

    //for 01.12 we had an input file with integers per line
    public static int[] readIntegerDataIntoArray(String resourceUri) {
        try (Stream<String> inFileStream = getLinesFromFile(resourceUri)) {
            AtomicInteger lineNumber = new AtomicInteger(0);
            int[] data = new int[3000]; //tbd how to init this array
            inFileStream.forEach(l -> {
                data[lineNumber.get()] = Integer.parseInt(l);
                lineNumber.getAndIncrement();
            });
            return Arrays.copyOfRange(data, 0, lineNumber.get());
        } catch (Exception ex) {
            System.out.println("Couldn't process data from file: " + ex.getCause());
        }
        return new int[0];
    }

    // for 02.12 we had an input file with commands per line like "forward 9", "down 2" etc.
    public static List<Pair<String, Integer>> readDataIntoCommandList(String resourceUri) {
        List<Pair<String, Integer>> commandList = new ArrayList<>();
        try (Stream<String> inFileStream = getLinesFromFile(resourceUri)) {
            inFileStream.forEach(l -> {
                String[] line = l.split("\\s");
                commandList.add(new Pair<>(line[0], Integer.valueOf(line[1])));
            });
            return commandList;
        } catch (Exception ex) {
            System.out.println("Couldn't process command list from file: " + ex.getCause());
        }
        return null;
    }

    //for 03.12 we had a 12bit array per line; read them as shorts and bit-manipulate them later for the puzzle requirement
    public static short[] readShortDataIntoArray(String resourceUri) {
        try (Stream<String> inFileStream = getLinesFromFile(resourceUri)) {
            AtomicInteger lineNumber = new AtomicInteger(0);
            short[] data = new short[3000]; //tbd how to init this array
            inFileStream.forEach(l -> {
                data[lineNumber.get()] = Short.valueOf(l, 2);
                lineNumber.getAndIncrement();
            });
            return Arrays.copyOfRange(data, 0, lineNumber.get());
        } catch (Exception ex) {
            System.out.println("Couldn't process data from file: " + ex.getCause());
        }
        return new short[0];
    }

    //for 04.12 we had a line with random numbers, then a lot of 5x5 matrices of numbers ("boards)

    public static Bingo getBingo(String resourceUri) {
        try (Stream<String> inFileStream = getLinesFromFile(resourceUri)) {
            AtomicInteger lineNumber = new AtomicInteger(0);
            AtomicInteger boardNumber = new AtomicInteger(0);
            AtomicInteger boardRow = new AtomicInteger(0);

            Bingo bingo = new Bingo();
            int[][][] boards = new int[200][5][5]; //enough for max 200 boards for now

            inFileStream.forEach(l -> {
                if (lineNumber.get() == 0) {
                    bingo.setCalledNumbers(Arrays.stream(l.split(",")).map(n -> Short.parseShort(n)).collect(Collectors.toList()));
                } else {
                    if(l.isBlank()){
                        if(lineNumber.get() != 1) {
                            boardNumber.getAndIncrement();
                            boardRow.set(0);
                        }
                    } else {
                        int[] row = Arrays.stream(l.trim().split(" ")).filter(s -> !s.isBlank()).mapToInt(n -> Integer.parseInt(n.trim())).toArray();
                        //System.out.println("Current row: " + Arrays.toString(row));
                        boards[boardNumber.get()][boardRow.get()] = row;
                        boardRow.getAndIncrement();
                    }
                }
                lineNumber.getAndIncrement();
            });
            bingo.setBoards(Arrays.copyOfRange(boards, 0, boardNumber.get() + 1));
            return bingo;
        } catch (Exception ex) {
            System.out.println("Couldn't process bingo data from file: " + ex.getCause());
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        short[] data = readShortDataIntoArray("inputDec3.txt");
        for (int i = 0; i < data.length; i++) {
            System.out.println(i + ": " + data[i] + " " + Integer.toBinaryString(data[i]));
        }
    }
}
