package learn.mastery.ui;

import learn.mastery.domain.Result;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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

    public void viewReservations(List<Reservation> reservations){

        // TODO: Take care of this in service
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (Reservation res : reservations){
            System.out.printf("%s: %s - %s Guest Id: %s Total: $%.2f%n",
                    res.getResId(),
                    res.getStartDate(),
                    res.getEndDate(),
                    res.getGuestId(),
                    res.getTotal());
        }
    }

    public String getEmail(String person) {
        return readRequiredString(String.format("%s Email Address: ", person));

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

    public LocalDate readDate(String prompt) {

        Boolean isDate = false;
        do{
            String input = readRequiredString(prompt);
            try{
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("M/d/yyyy"));
                //isDate = true;
            } catch (DateTimeParseException ex) {
                System.out.println("Enter a date in format MM/dd/yyyy: ");
            }
        } while(!isDate);
        return null;
    }

    public String summary(LocalDate startDate, LocalDate endDate, BigDecimal total){
        printHeader("Summary:");
        System.out.printf("Start: %s%nEnd: %s%nTotal: $%.2f",
                startDate,
                endDate,
                total);

       return readRequiredString("Is this okay? [y/n]: ");
    }

    public void displayResult(Result result) {
        if (result.isSuccess()) {
            printHeader("Success!");
        } else {
            printHeader("Error:");
            for (String err : result.getErrorMessages()) {
                System.out.println(err);
            }
        }
    }
}
