package org.example;

import java.util.*;
import java.util.concurrent.Executor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        List<Thread> threadList = new ArrayList<>();
        long startTs = System.currentTimeMillis(); // start time
        int count = 0;
        for (String text : texts) {
            Thread thread = new Thread(() -> {
                int maxSize = 0;
                for (int d = 0; d < text.length(); d++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (d >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = d; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - d) {
                            maxSize = j - d;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

