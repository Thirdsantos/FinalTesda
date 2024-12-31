package com.clinic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.clinic.console.ConsoleDisplay;


public class Appointment {
    private static final String URL = "jdbc:mysql://localhost:3306/tesda_clinic_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection() throws Exception {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    ConsoleDisplay display = new ConsoleDisplay();
    Users users = new Users();
    Scanner scanner = new Scanner(System.in);

    public void appoint() {
        ConsoleDisplay.clearConsole();
        System.out.println("Hello welcome to the appointment booking system!");
        System.out.println("Please make sure that your information is correct!");
        System.out.print("Please Enter your Full Name (Eg. John Doe): ");
        String fullName = scanner.nextLine().toLowerCase(); 
        users.setName(fullName);
        System.out.print("Please Enter your Contact Number: ");
        String contactNumber = scanner.nextLine();
        users.setContactNumber(contactNumber);
        users.setDoctor("Dr. Ramirez");

        String date;
        boolean isValidDate = false;

        while (!isValidDate) {
            System.out.print("Please enter your desired date (YYYY-MM-DD): ");
            date = scanner.nextLine();

            if (isDateValid(date)) {
                users.setDate(date);
                isValidDate = true;
            } else {
                System.out.println("Invalid date! Please ensure the date is in the format YYYY-MM-DD, and a valid calendar date.");
            }
        }
        display.showLoadingBar();
        display.clearConsole();

        addUser(users, fullName);
    }

    private boolean isDateValid(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

            if (parsedDate.getYear() < 2025) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void addUser(Users users, String name) {
        String query = "INSERT INTO appointment (name, contact_number, doctor, booking_date) VALUES (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, users.getName()); 
            statement.setString(2, users.getContactNumber());
            statement.setString(3, users.getDoctor());
            statement.setString(4, users.getDate());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Appointment successfully booked for " + name + " on " + users.getDate() + " with " + users.getDoctor() + ".");
            } else {
                System.out.println("An error occurred!");
            }

        } catch (Exception ex) {
            System.out.println("There was an error: ");
            System.out.println("There could be a duplicate entry for this appointment.");
            System.out.println("Please try again.");
        }
    }
}