package com.clinic;
import com.clinic.console.ConsoleDisplay;
import com.clinic.console.Menu;

public class App {
    public static void main( String[] args ){
        
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        Menu menu = new Menu();


        consoleDisplay.displayWelcome();
        menu.selector();

    }
}
