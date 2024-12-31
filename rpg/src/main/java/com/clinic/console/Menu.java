package com.clinic.console;

import java.util.Scanner;

import com.clinic.jdbc.Appointment;
import com.clinic.jdbc.AppointmentDeletion;
import com.clinic.jdbc.CheckStatus;

public class Menu {
    private final ConsoleDisplay display = new ConsoleDisplay();
    private final Scanner scanner = new Scanner(System.in);
    private final Appointment appoint = new Appointment();
    private final AppointmentDeletion delete;
    private final CheckStatus check;

    public Menu() {
        this.delete = new AppointmentDeletion(this); 
        this.check = new CheckStatus(this);          
    }

    public void selector() {
        boolean notTrue = false;

        while (!notTrue) {
            System.out.println("Here are the available services:");
            System.out.println("1. Book an Appointment");
            System.out.println("2. Check your Appointment Status");
            System.out.println("3. Cancel your Appointment");
            System.out.println("4. Exit");
            System.out.print("Choose the number of the service you want: ");
            int choice = scanner.nextInt();

            if (choice > 0 && choice < 4) {
                selectService(choice);

                boolean validInput = false;
                while (!validInput) {
                    System.out.println(" ");
                    System.out.println("What would you like to do next?");
                    System.out.println("1. Menu");
                    System.out.println("2. Exit");
                    System.out.print("Choose the number of the action you want: ");
                    int nextChoice = scanner.nextInt();

                    if (nextChoice == 1) {
                        validInput = true;
                        display.clearConsole();
                    } else if (nextChoice == 2) {
                        notTrue = true;
                        validInput = true;
                        display.clearConsole();
                        System.out.println("Thank you for using our clinic services!");
                    } else {
                        display.clearConsole();
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (choice == 4) {
                notTrue = true;
                display.clearConsole();
                System.out.println("Thank you for using our clinic services!");
            } else {
                display.clearConsole();
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void selectService(int input) {
        switch (input) {
            case 1:
                appoint.appoint();
                break;
            case 2:
                check.checkStatus();
                break;
            case 3:
                delete.deleteAppointment();
                break;
        }
    }
}
