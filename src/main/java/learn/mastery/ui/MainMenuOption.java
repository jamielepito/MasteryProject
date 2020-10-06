package learn.mastery.ui;


public enum MainMenuOption {

    EXIT(0,"Exit"),
    MAKE_RESERVATION(1, "Make a Reservation"),
    EDIT_RESERVATION(2, "Edit a Reservation"),
    CANCEL_RESERVATION(3,"Cancel a Reservation");

    private int value;
    private String message;

    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }
    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
