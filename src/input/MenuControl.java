package input;

import java.util.Scanner;

public class MenuControl {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean run;
    private static int menuNum;
    // 00 = registerMenu
    // 01 = mainMenu
    // 02 = userMenu
    // 03 = editMenu
    // 04 = chatMenu
    // 05 = groupEditMenu
    // 06 = createPostMenu
    // 07 = homePage

    public static void inputScanner() {
        while (run) {
            String input = scanner.nextLine().trim();

            switch (menuNum) {
                case RegisterMenu.MENU_NUM -> RegisterMenu.input_processor(input);
                case MainMenu.MENU_NUM -> MainMenu.input_processor(input);
                case UserMenu.MENU_NUM -> UserMenu.input_processor(input);
                case EditMenu.MENU_NUM -> EditMenu.input_processor(input);
                case ChatMenu.MENU_NUM -> ChatMenu.input_processor(input);
                case GroupEditMenu.MENU_NUM -> GroupEditMenu.input_processor(input);
                case CreatePostMenu.MENU_NUM -> CreatePostMenu.input_processor(input);
                case HomePage.MENU_NUM -> HomePage.input_processor(input);
            }
        }

    }

    public static void setMenuNum(int menuNum) {
        MenuControl.menuNum = menuNum;
    }

    public static void setRun(boolean run) {
        MenuControl.run = run;
    }
}
