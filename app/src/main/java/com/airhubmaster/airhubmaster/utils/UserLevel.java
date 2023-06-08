package com.airhubmaster.airhubmaster.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLevel {
    Map<Integer, List<Integer>> staffBoostMap = new HashMap<>();
    Map<Integer, Integer> requiredExpMap = new HashMap<>();
    public UserLevel(){
        staffBoostMap.put(1, Arrays.asList(100, 0, 0, 0, 0, 0));
        staffBoostMap.put(2, Arrays.asList(99, 1, 0, 0, 0, 0));
        staffBoostMap.put(3, Arrays.asList(97, 3, 0, 0, 0, 0));
        staffBoostMap.put(4, Arrays.asList(95, 5, 0, 0, 0, 0));
        staffBoostMap.put(5, Arrays.asList(90, 9, 1, 0, 0, 0));
        staffBoostMap.put(6, Arrays.asList(89, 10, 1, 0, 0, 0));
        staffBoostMap.put(7, Arrays.asList(87, 12, 1, 0, 0, 0));
        staffBoostMap.put(8, Arrays.asList(86, 13, 1, 0, 0, 0));
        staffBoostMap.put(9, Arrays.asList(86, 13, 1, 0, 0, 0));
        staffBoostMap.put(10, Arrays.asList(77, 20, 3, 0, 0, 0));

        staffBoostMap.put(11, Arrays.asList(72, 25, 3, 0, 0, 0));
        staffBoostMap.put(12, Arrays.asList(72, 25, 3, 0, 0, 0));
        staffBoostMap.put(13, Arrays.asList(72, 25, 3, 0, 0, 0));
        staffBoostMap.put(14, Arrays.asList(72, 25, 3, 0, 0, 0));
        staffBoostMap.put(15, Arrays.asList(60, 35, 5, 0, 0, 0));
        staffBoostMap.put(16, Arrays.asList(58, 37, 5, 0, 0, 0));
        staffBoostMap.put(17, Arrays.asList(56, 39, 5, 0, 0, 0));
        staffBoostMap.put(18, Arrays.asList(54, 41, 5, 0, 0, 0));
        staffBoostMap.put(19, Arrays.asList(52, 43, 5, 0, 0, 0));
        staffBoostMap.put(20, Arrays.asList(42, 43, 12, 3, 0, 0));

        staffBoostMap.put(21, Arrays.asList(40, 45, 12, 3, 0, 0));
        staffBoostMap.put(22, Arrays.asList(39, 46, 12, 3, 0, 0));
        staffBoostMap.put(23, Arrays.asList(37, 48, 12, 3, 0, 0));
        staffBoostMap.put(24, Arrays.asList(35, 50, 12, 3, 0, 0));
        staffBoostMap.put(25, Arrays.asList(32, 33, 25, 10, 0, 0));
        staffBoostMap.put(26, Arrays.asList(30, 35, 25, 10, 0, 0));
        staffBoostMap.put(27, Arrays.asList(28, 37, 25, 10, 0, 0));
        staffBoostMap.put(28, Arrays.asList(27, 38, 25, 10, 0, 0));
        staffBoostMap.put(29, Arrays.asList(26, 39, 25, 10, 0, 0));
        staffBoostMap.put(30, Arrays.asList(15, 25, 40, 15, 5, 0));

        staffBoostMap.put(31, Arrays.asList(12, 20, 48, 15, 5, 0));
        staffBoostMap.put(32, Arrays.asList(11, 20, 47, 17, 5, 0));
        staffBoostMap.put(33, Arrays.asList(10, 20, 45, 20, 5, 0));
        staffBoostMap.put(34, Arrays.asList(8, 20, 40, 27, 5, 0));
        staffBoostMap.put(35, Arrays.asList(7, 15, 30, 35, 7, 0));
        staffBoostMap.put(36, Arrays.asList(7, 15, 28, 43, 7, 0));
        staffBoostMap.put(37, Arrays.asList(7, 15, 26, 45, 7, 0));
        staffBoostMap.put(38, Arrays.asList(7, 15, 24, 45, 9, 0));
        staffBoostMap.put(39, Arrays.asList(7, 15, 22, 42, 14, 0));
        staffBoostMap.put(40, Arrays.asList(5, 15, 20, 39, 20, 1));

        staffBoostMap.put(41, Arrays.asList(4, 10, 17, 37, 27, 5));
        staffBoostMap.put(42, Arrays.asList(3, 10, 11, 29, 40, 7));
        staffBoostMap.put(43, Arrays.asList(2, 8, 9, 25, 38, 18));
        staffBoostMap.put(44, Arrays.asList(1, 7, 8, 20, 35, 29));
        staffBoostMap.put(45, Arrays.asList(0, 5, 5, 10, 25, 55));
        staffBoostMap.put(46, Arrays.asList(0, 2, 3, 8, 20, 67));
        staffBoostMap.put(47, Arrays.asList(0, 1, 2, 5, 15, 77));
        staffBoostMap.put(48, Arrays.asList(0, 0, 1, 5, 15, 79));
        staffBoostMap.put(49, Arrays.asList(0, 0, 0, 3, 15, 82));
        staffBoostMap.put(50, Arrays.asList(0, 0, 0, 2, 15, 83));

        //====

        int k = 1000;
        int m = 1000000;

        requiredExpMap.put(1, 0);
        requiredExpMap.put(2, 25);
        requiredExpMap.put(3, 50);
        requiredExpMap.put(4, 125);
        requiredExpMap.put(5, 225);
        requiredExpMap.put(6, 400);
        requiredExpMap.put(7, 500);
        requiredExpMap.put(8, 650);
        requiredExpMap.put(9, 800);
        requiredExpMap.put(10, k);

        requiredExpMap.put(11, 2 * k + 250);
        requiredExpMap.put(12, 3 * k + 500);
        requiredExpMap.put(13, 5 * k + 750);
        requiredExpMap.put(14, 8 * k);
        requiredExpMap.put(15, 10 * k);
        requiredExpMap.put(16, 12 * k);
        requiredExpMap.put(17, 15 * k);
        requiredExpMap.put(18, 18 * k);
        requiredExpMap.put(19, 21 * k);
        requiredExpMap.put(20, 25 * k);

        requiredExpMap.put(21, 32 * k);
        requiredExpMap.put(22, 45 * k);
        requiredExpMap.put(23, 75 * k);
        requiredExpMap.put(24, 125 * k);
        requiredExpMap.put(25, 175 * k);
        requiredExpMap.put(26, 200 * k);
        requiredExpMap.put(27, 235 * k);
        requiredExpMap.put(28, 275 * k);
        requiredExpMap.put(29, 320 * k);
        requiredExpMap.put(30, 400 * k);

        requiredExpMap.put(31, 600 * k);
        requiredExpMap.put(32, 900 * k);
        requiredExpMap.put(33, m + 400 * k);
        requiredExpMap.put(34, 2 * m + 500 * k);
        requiredExpMap.put(35, 4 * m);
        requiredExpMap.put(36, 4 * m + 750 * k);
        requiredExpMap.put(37, 6 * m);
        requiredExpMap.put(38, 7 * m);
        requiredExpMap.put(39, 8 * m);
        requiredExpMap.put(40, 10 * m);

        requiredExpMap.put(41, 15 * m);
        requiredExpMap.put(42, 25 * m);
        requiredExpMap.put(43, 45 * m);
        requiredExpMap.put(44, 70 * m);
        requiredExpMap.put(45, 100 * m);
        requiredExpMap.put(46, 120 * m);
        requiredExpMap.put(47, 150 * m);
        requiredExpMap.put(48, 170 *m);
        requiredExpMap.put(49, 200 * m);
        requiredExpMap.put(50, 250 * m);
    }
}
