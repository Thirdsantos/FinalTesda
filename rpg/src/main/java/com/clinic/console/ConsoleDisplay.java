package com.clinic.console;

import java.util.Scanner;

public class ConsoleDisplay {
    private Scanner scanner = new Scanner(System.in);

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("Unable to clear console: " + e.getMessage());
        }
    }

    public void displayWelcome() {
        String clinicName = "Ampo's Clinic";
        String tagline = "Your Health, Our Priority";
        int width = 50;
        String border = repeatChar('*', width);

        clearConsole();
        safeTypewrite(border);
        safeTypewrite(centerText(clinicName, width));
        safeTypewrite(centerText(tagline, width));
        safeTypewrite(border);
        System.out.println();

        safeSleep(300);

        safeTypewriteWithBlink("Welcome to Ampo's Clinic!", 3);

        System.out.println();
        safeSleep(300);
     }

    private static String repeatChar(char ch, int count) {
        return String.valueOf(ch).repeat(count);
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return repeatChar(' ', padding) + text + repeatChar(' ', padding);
    }

    private void safeTypewrite(String text) {
        try {
            for (char ch : text.toCharArray()) {
                System.out.print(ch);
                Thread.sleep(25);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void safeTypewriteWithBlink(String text, int blinks) {
        try {
            for (int i = 0; i < blinks; i++) {
                System.out.print("\r" + text + " ");
                Thread.sleep(300);
                System.out.print("\r" + text + "_");
                Thread.sleep(300);
            }
            System.out.print("\r" + text + " ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void showLoadingBar() {
        clearConsole();
        final int totalBars = 30;
        final int loadingTime = 50;
    
        System.out.println("Loading, please wait...");
    
        System.out.print("[");

        for (int i = 0; i < totalBars; i++) {
            System.out.print(" ");
        }
        System.out.print("] 0%");

        System.out.print("\r[");

        for (int i = 0; i <= totalBars; i++) {
            try {
                Thread.sleep(loadingTime); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                return; 
            }

            System.out.print("█");

            int percentage = (i * 100) / totalBars;

            System.out.print("\r[");

            for (int j = 0; j < i; j++) {
                System.out.print("█");
            }
            for (int j = i; j < totalBars; j++) {
                System.out.print(" ");
            }
            System.out.print("] " + percentage + "%");
        }
        System.out.println("\n✔ Loading Complete!");
        clearConsole();
    }
}
