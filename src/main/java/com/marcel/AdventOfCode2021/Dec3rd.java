package com.marcel.AdventOfCode2021;

import com.marcel.AdventOfCode2021.utils.FileUtils;

import java.util.Arrays;

public class Dec3rd {
    public static void main(String[] args) {
        short[] data = FileUtils.readShortDataIntoArray("inputDec3.txt");
        int cols = 12; // depends on the number of bits in the file
        // used to compute the gamma and epsilon values; cols 'bits' as in the file - used to store the sum representing the number of 1s
        // row 0 -> occurence of 1's at bit pos k; row 1 -> binary of gamma value; row -> binary of epsilon value
        short[][] gammaEpsilon = new short[3][cols];
        int gammaValue = 0;
        int epsilonValue = 0;

        /** ---------------------------------------------------------------------- PART I ---------------------------------------------------------------------- **/
        //iterate each data line, for each bit 'column' increment by 1 for each 1 encountered
        for (short i = 0; i < data.length; i++) {
            for (byte k = 0; k < cols; k++) {
                if (getBit(data[i], k)) gammaEpsilon[0][k]++;
            }
        }

        System.out.println("Number of 1s per column: ");
        for (byte i = 0; i < cols; i++) {
            System.out.print(gammaEpsilon[0][i] + " ");
            //write/overwrite actual bits ([0] gamma, [1] epsilon)
            if (gammaEpsilon[0][i] > (data.length - data.length / 2) || gammaEpsilon[0][i] == (data.length - data.length / 2)) {
                gammaEpsilon[1][i] = 1;
                gammaEpsilon[2][i] = 0;
            } else {
                gammaEpsilon[1][i] = 0;
                gammaEpsilon[2][i] = 1;
            }
        }

        System.out.println("\nGamma Boolean Value ");
        for (byte i = 0; i < cols; i++) {
            System.out.print(gammaEpsilon[1][i] + " ");
            //reconstruct actual integer value by setting individual bits
            gammaValue = gammaValue | (gammaEpsilon[1][i] << i);
        }

        System.out.println("\nGamma Value: " + gammaValue);

        System.out.println("\nEpsilon Boolean Value ");
        for (byte i = 0; i < cols; i++) {
            System.out.print(gammaEpsilon[2][i] + " ");
            //reconstruct actual integer value by setting individual bits
            epsilonValue = epsilonValue | (gammaEpsilon[2][i] << i);
        }

        System.out.println("\nEpsilon Value: " + epsilonValue);

        System.out.println("\n Power Consumption: " + gammaValue * epsilonValue);

        /** ---------------------------------------------------------------------- PART II ---------------------------------------------------------------------- **/
        // I confused myself here initially because I didn't fully understand the requirement
        // I used the original computed most/least common bits in the gammaEpsilon array;
        // yet the requirement is to recompute this for each iteration of remaining numbers
        // the puzzle was tricky though - it worked with the test data
        short lastValue = 0;

        System.out.println("\n ------------ Life support computations --------------------------");
        //oxygenRating
        int oxygenRating = getLastValue(cols, gammaEpsilon, lastValue, 1, data);
        int co2Rating = getLastValue(cols, gammaEpsilon, lastValue, 2, data);

        System.out.println("Oxygen Rating: " + oxygenRating);
        System.out.println("Co2Rating: " + co2Rating);

        System.out.println("Life support rating: " + oxygenRating * co2Rating);


       /* //test sout for first 10 vals
        System.out.println("First 10 vals of the oxygen rating array");
        for (int t = 0; t < 10; t++) {
            System.out.print(newData[t] + " ");
        }
        System.out.println("\nLast value: " + lastValue);*/
    }

    private static short getLastValue(int cols, short[][] gammaEpsilon, short lastValue, int mostCommon, short[] data) {
        short[] oldData = Arrays.copyOfRange(data, 0, data.length);
        short[] newData = new short[oldData.length];
        int newIndex;
        for (byte k = (byte) (cols - 1); k >= 0; k--) {
            if (oldData.length == 1) {
                lastValue = oldData[0];
                break;
            } else if (oldData.length == 2) {
                if (getBit(oldData[0], k)) {
                    lastValue = oldData[0];
                } else {
                    lastValue = oldData[1];
                }
            }

            // recompute most/least common bit at current position -----------
            Arrays.fill(gammaEpsilon[0], (short) 0);
            for (short i = 0; i < oldData.length; i++) {
                for (byte k2 = 0; k2 < cols; k2++) {
                    if (getBit(oldData[i], k2)) gammaEpsilon[0][k2]++;
                }
            }
            for (byte i = 0; i < cols; i++) {
                if (gammaEpsilon[0][i] > (oldData.length - oldData.length / 2) || gammaEpsilon[0][i] == (oldData.length - oldData.length / 2)) {
                    gammaEpsilon[1][i] = 1;
                    gammaEpsilon[2][i] = 0;
                } else {
                    gammaEpsilon[1][i] = 0;
                    gammaEpsilon[2][i] = 1;
                }
            }
            // end of recompute ---------------

            newIndex = 0;

            for (int i = 0; i < oldData.length; i++) {
                if (getBit(oldData[i], k) == (gammaEpsilon[mostCommon == 1 ? 1 : 2][k] == 1)) {
                    newData[newIndex] = oldData[i];
                    newIndex++;
                }
            }
            if (newIndex < 1) {
                System.out.println("oxygen rating looper stopped after k = " + k);
                lastValue = newData[0];
                break;
            }
            Arrays.fill(oldData, (short) 0); //used in debugging for clarity
            oldData = Arrays.copyOfRange(newData, 0, newIndex);

        }
        return lastValue;
    }

    static boolean getBit(short n, short bitPos) {
        return ((n >> bitPos) & 1) == 1;
    }
}
