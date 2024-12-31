package com.clinic.jdbc;

import java.sql.*;
import java.util.Scanner;

import com.clinic.console.Menu;
import com.clinic.console.ConsoleDisplay;

public class CheckStatus {
    private static final String URL = "jdbc:mysql://localhost:3306/tesda_clinic_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    ConsoleDisplay display = new ConsoleDisplay();

    private final Menu menu; 
    private final Scanner scanner = new Scanner(System.in);

   
    public CheckStatus(Menu menu) {
        this.menu = menu;
    }

    public void checkStatus() {
        display.clearConsole();
        System.out.println("Please make sure that the Name you enter is correct.");
        boolean continueChecking = true;

        while (continueChecking) {
            System.out.print("Please enter the name of the patient: ");
            String name = scanner.nextLine();
            if (displayPatientDetails(name.toLowerCase())) {
                continueChecking = false;
            } else {
                display.clearConsole();
                System.out.println("No patient found with that name.");
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Try Again");
                System.out.println("2. Exit");
                System.out.println("3. Menu");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        continue;
                    case 2:
                        display.clearConsole();
                        System.out.println("Thank you for using our clinic services!");
                        continueChecking = false;
                        break;
                    case 3:
                        System.out.println("Returning to the main menu...");
                        menu.selector();
                        continueChecking = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private boolean displayPatientDetails(String name) {
        String query = "SELECT * FROM appointment WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    display.clearConsole();
                    System.out.println("Appointment Status: ");
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Contact Number: " + resultSet.getString("contact_number"));
                    System.out.println("Doctor: " + resultSet.getString("doctor"));
                    System.out.println("Booking Date: " + resultSet.getString("booking_date"));
                    return true;
                } else {
                    System.out.println("No patient found with that name.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
