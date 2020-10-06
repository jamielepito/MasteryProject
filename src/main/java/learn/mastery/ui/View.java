package learn.mastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);

    public MainMenuOption displayMainMenuAndSelect() {
        MainMenuOption[] values = MainMenuOption.values();
        for (MainMenuOption option : values) {
            System.out.printf("%s. %s%n", option.getValue(), option.getMessage());
        }
        String message = String.format("Select [0-%s]: ", values.length-1);
        int menuIndex = readInt(message, 0, values.length-1);
        return values[menuIndex];
    }

    public String getHostEmail() {
        return readRequiredString("Host Email Address: ");

    }

    public void printHeader(String message) {
        System.out.printf("%n%s%n", message);
        System.out.println("=".repeat(message.length()));
    }

    private int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean isInteger = false;
        do {
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isInteger = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number! Try again.");
            }
        } while (!isInteger);
        return result;
    }

    private String readRequiredString(String prompt) {
        String result = null;
        do{
            result = readString(prompt).trim();
            if (result.length() == 0){
                System.out.println("You need to enter a value");
            }
        } while(result.length() == 0);
        return result;
    }

    // read user input from console after printing prompt
    private String readString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private LocalDate readDate(String prompt) {
        String input = readRequiredString(prompt);
        while (true){
            try{
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (DateTimeParseException ex){
                System.out.println("Enter a date in format MM/dd/yyyy: ");
            }
        }
    }
}
