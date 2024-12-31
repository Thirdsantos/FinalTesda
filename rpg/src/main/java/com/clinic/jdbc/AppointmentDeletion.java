package com.clinic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.clinic.console.*;

public class AppointmentDeletion {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tesda_clinic_db";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    ConsoleDisplay display = new ConsoleDisplay();

    private final Menu menu;
    private final Scanner scanner = new Scanner(System.in);

    public AppointmentDeletion(Menu menu) {
        this.menu = menu;
    }

    public void deleteAppointment() {
        boolean isDeleting = true;
       

        while (isDeleting) {
            display.clearConsole();
            System.out.println("Please ensure the name you enter is correct.");
            System.out.print("Enter the patient's name to delete the appointment: ");
            String patientName = scanner.nextLine();

            if (doesAppointmentExist(patientName.toLowerCase())) {
                confirmAndDelete(patientName.toLowerCase());
                isDeleting = false;
            } else {
                display.clearConsole();
                System.out.println("No appointment found with that name.");
                showRetryMenu();
            }
        }
    }

    private boolean doesAppointmentExist(String name) {
        String query = "SELECT 1 FROM appointment WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void confirmAndDelete(String name) {
        while (true) {
            System.out.print("Are you sure you want to delete the appointment for " + name + "? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(confirmation)) {
                executeDeleteQuery(name);
                break;
            } else if ("no".equals(confirmation)) {
                System.out.println("Deletion canceled.");
                showRetryMenu();
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private void executeDeleteQuery(String name) {
        String query = "DELETE FROM appointment WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Appointment successfully deleted for " + name + ".");
            } else {
                System.out.println("An error occurred during deletion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showRetryMenu() {
        
        System.out.println("What would you like to do next?");
        System.out.println("1. Try Again");
        System.out.println("2. Exit");
        System.out.println("3. Menu");
        System.out.print("Enter your choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    break;
                case 2:
                    System.out.println("Thank you for using our clinic services!");
                    System.exit(0); 
                    break;
                case 3:
                    System.out.println("Returning to the main menu...");
                    menu.selector();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    showRetryMenu(); 
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            showRetryMenu();
        }
    }
}
