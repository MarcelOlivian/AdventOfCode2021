package com.marcel.AdventOfCode2021;

import com.marcel.AdventOfCode2021.utils.FileUtils;
import org.javatuples.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Dec2nd {
    public static void main(String[] args) {
        List<Pair<String, Integer>> commandList = FileUtils.readDataIntoCommandList("inputDec2.txt");
        /*AtomicInteger incrementor = new AtomicInteger(1);
        commandList.forEach(c -> {
            System.out.println(incrementor.get() + " " + c.getValue0() + " " + c.getValue1());
            incrementor.getAndIncrement();
        });*/

        AtomicInteger horizontalPos = new AtomicInteger(0);
        AtomicInteger depth = new AtomicInteger(0);
        AtomicInteger aim = new AtomicInteger(0);

        if (commandList != null) {
            commandList.forEach(c -> {
                switch(c.getValue0()) {
                    case "forward":
                        horizontalPos.addAndGet(c.getValue1());
                        depth.addAndGet(aim.get() * c.getValue1());
                        break;
                    case "up":
                        aim.addAndGet(-c.getValue1());
                        break;
                    case "down":
                        aim.addAndGet(c.getValue1());
                        break;
                }
            });
        }

        System.out.println("Horizontal position: " + horizontalPos.get() + "\nDepth: " + depth.get() + "\nMultiplication result: " + horizontalPos.get()*depth.get());
    }
}
