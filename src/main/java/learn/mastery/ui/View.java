package learn.mastery.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);

    public MainMenuOption displayMainMenuAndSelect() {
        MainMenuOption[] values = MainMenuOption.values();
        for (MainMenuOption option : values) {
            System.out.printf("%s. %s%n", option.getValue(), option.getMessage());
        }
        int menuIndex = readInt("Select [0-4]: ", 0, values.length);
        return values[menuIndex];
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
}
