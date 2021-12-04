package com.marcel.AdventOfCode2021;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Dec1st {
    public static void main(String[] args)  {
        //File inputFile = new File(Dec1st.class.getClassLoader().getResource("inputDec1.txt").toURI());
        //Scanner fileScanner = new Scanner(inputFile);

        int[] data = FileUtils.readIntegerDataIntoArray("inputDec1.txt");
        int arrayLength = data.length;
        int increaseTimes = 0;
        int windowIncreaseTimes = 0;

        int lastWindow = data[0] + data[1] + data[2];

        for (int i = 0; i < arrayLength; i++) {
            //1st puzzle
           /* if (i > 0 && data[i] > data[i - 1]) {
                increaseTimes++;
                System.out.println(i + 1 + ": " + data[i] + " INCREASED");
            } else {
                System.out.println(i + 1 + ": " + data[i] + " DECREASED");
            }*/

            //2nd puzzle, sliding window of 3 values
            if (i <= (arrayLength - 3)) {
                int currentWindow = data[i] + data[i + 1] + data[i + 2];
                if (currentWindow > lastWindow){
                    System.out.println("Window " + i + "(index of " + data[i] + "): " + currentWindow + " -> INCREASED");
                    windowIncreaseTimes++;
                } else if (currentWindow == lastWindow) {
                    System.out.println("Window " + i + "(index of " + data[i] + "): " + currentWindow + " -> NO CHANGE");
                } else {
                    System.out.println("Window " + i + "(index of " + data[i] + "): " + currentWindow + " -> DECREASED");
                }
                lastWindow = currentWindow;
            }

        }

        System.out.println("\n Nr. of increase times: " + increaseTimes);
        System.out.println("\n Nr. of window increase times: " + windowIncreaseTimes);

    }
}
