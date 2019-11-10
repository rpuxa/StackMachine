import java.io.File;
import java.io.FileInputStream;

public class StateMachine {

    private int state;
    private boolean error;

    public static void main(String[] args) {
        try {

            File file = new File("code.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");
            StringBuilder builder = new StringBuilder();

            for (char c : str.toCharArray()) {
                if (c != '\r') builder.append(Character.toLowerCase(c));
            }

            System.out.println("Строка: \n" + str);
            System.out.println(new StateMachine().check(builder.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean check(String string) {
        char[] array = (string + (char) 0).toCharArray();
        int i = 0;
        while (i < array.length) {
            if (state == -1 || error) {
                return false;
            } else {
                char symbol = array[i++];
                checkSymbol(symbol);
            }
        }
        return !error;
    }

    @SuppressWarnings("Duplicates")
    private void checkSymbol(char symbol) {
        switch (state) {

            case 0:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '\n') {
                    return;
                } else if (symbol == 'd') {
                    state = 1;
                } else {
                    error = true;
                }
                return;
            case 1:
                if (symbol == 'o') {
                    state = 2;
                } else {
                    error = true;
                }
                return;
            case 2:
                if (symbol == ' ') {
                    state = 5;
                } else {
                    error = true;
                }
                return;
            case 3:
                if (symbol == 'o') {
                    state = 4;
                } else {
                    error = true;
                }
                return;
            case 4:
                if (symbol == 'r') {
                    state = 12;
                } else {
                    error = true;
                }
                return;
            case 5:
                if (symbol == ' ') {
                    return;
                } else if (symbol == 'w') {
                    state = 6;
                } else {
                    error = true;
                }
                return;
            case 6:
                if (symbol == 'h') {
                    state = 7;
                } else {
                    error = true;
                }
                return;
            case 7:
                if (symbol == 'i') {
                    state = 8;
                } else {
                    error = true;
                }
                return;
            case 8:
                if (symbol == 'l') {
                    state = 10;
                } else {
                    error = true;
                }
                return;
            case 9:
                if (symbol == 'n') {
                    state = 11;
                } else {
                    error = true;
                }
                return;
            case 10:
                if (symbol == 'e') {
                    state = 12;
                } else {
                    error = true;
                }
                return;
            case 11:
                if (symbol == 'd') {
                    state = 12;
                } else {
                    error = true;
                }
                return;
            case 12:
                if (symbol == ' ') {
                    state = 21;
                } else {
                    error = true;
                }
                return;
            case 13:
                if (Character.isDigit(symbol)) {
                    state = 24;
                } else {
                    error = true;
                }
                return;
            case 14:
                if (Character.isDigit(symbol)) {
                    state = 25;
                } else {
                    error = true;
                }
                return;
            case 15:
                if (Character.isDigit(symbol)) {
                    state = 26;
                } else {
                    error = true;
                }
                return;
            case 16:
                if (symbol == '+') {
                    state = 13;
                } else if (symbol == '-') {
                    state = 13;
                } else if (Character.isDigit(symbol)) {
                    state = 24;
                } else {
                    error = true;
                }
                return;
            case 17:
                if (Character.isDigit(symbol)) {
                    state = 31;
                } else {
                    error = true;
                }
                return;
            case 18:
                if (Character.isDigit(symbol)) {
                    state = 33;
                } else {
                    error = true;
                }
                return;
            case 19:
                if (Character.isDigit(symbol)) {
                    state = 34;
                } else {
                    error = true;
                }
                return;
            case 20:
                if (Character.isDigit(symbol)) {
                    state = 31;
                } else if (symbol == '+') {
                    state = 17;
                } else if (symbol == '-') {
                    state = 17;
                } else {
                    error = true;
                }
                return;
            case 21:
                if (Character.isLetter(symbol)) {
                    state = 23;
                } else if (Character.isDigit(symbol)) {
                    state = 26;
                } else if (symbol == '+') {
                    state = 15;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == '-') {
                    state = 15;
                } else {
                    error = true;
                }
                return;
            case 22:
                if (symbol == '=') {
                    state = 27;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == '>') {
                    state = 28;
                } else if (symbol == '<') {
                    state = 29;
                } else {
                    error = true;
                }
                return;
            case 23:
                if (symbol == '>') {
                    state = 28;
                } else if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '<') {
                    state = 29;
                } else if (symbol == ' ') {
                    state = 22;
                } else if (symbol == '=') {
                    state = 27;
                } else {
                    error = true;
                }
                return;
            case 24:
                if (symbol == '>') {
                    state = 28;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '<') {
                    state = 29;
                } else if (symbol == ' ') {
                    state = 22;
                } else if (symbol == '=') {
                    state = 27;
                } else {
                    error = true;
                }
                return;
            case 25:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 22;
                } else if (symbol == '=') {
                    state = 27;
                } else if (symbol == '<') {
                    state = 29;
                } else if (symbol == '>') {
                    state = 28;
                } else if (symbol == 'e') {
                    state = 16;
                } else {
                    error = true;
                }
                return;
            case 26:
                if (symbol == 'e') {
                    state = 16;
                } else if (symbol == '>') {
                    state = 28;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '<') {
                    state = 29;
                } else if (symbol == ' ') {
                    state = 22;
                } else if (symbol == '=') {
                    state = 27;
                } else if (symbol == '.') {
                    state = 14;
                } else {
                    error = true;
                }
                return;
            case 27:
                if (symbol == '-') {
                    state = 19;
                } else if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 30;
                } else if (Character.isDigit(symbol)) {
                    state = 34;
                } else if (symbol == '+') {
                    state = 19;
                } else {
                    error = true;
                }
                return;
            case 28:
                if (symbol == '=') {
                    state = 27;
                } else if (symbol == ' ') {
                    state = 27;
                } else if (symbol == '-') {
                    state = 19;
                } else if (symbol == '+') {
                    state = 19;
                } else if (Character.isDigit(symbol)) {
                    state = 34;
                } else if (Character.isLetter(symbol)) {
                    state = 30;
                } else {
                    error = true;
                }
                return;
            case 29:
                if (Character.isLetter(symbol)) {
                    state = 30;
                } else if (Character.isDigit(symbol)) {
                    state = 34;
                } else if (symbol == '+') {
                    state = 19;
                } else if (symbol == '-') {
                    state = 19;
                } else if (symbol == '=') {
                    state = 27;
                } else if (symbol == ' ') {
                    state = 27;
                } else if (symbol == '>') {
                    state = 27;
                } else {
                    error = true;
                }
                return;
            case 30:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 32;
                } else if (symbol == '\n') {
                    state = 39;
                } else {
                    error = true;
                }
                return;
            case 31:
                if (symbol == '\n') {
                    state = 39;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 32;
                } else {
                    error = true;
                }
                return;
            case 32:
                if (symbol == 'o') {
                    state = 4;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == 'x') {
                    state = 3;
                } else if (symbol == 'a') {
                    state = 9;
                } else if (symbol == '\n') {
                    state = 39;
                } else {
                    error = true;
                }
                return;
            case 33:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == 'e') {
                    state = 20;
                } else if (symbol == '\n') {
                    state = 39;
                } else if (symbol == ' ') {
                    state = 32;
                } else {
                    error = true;
                }
                return;
            case 34:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '.') {
                    state = 18;
                } else if (symbol == ' ') {
                    state = 32;
                } else if (symbol == 'e') {
                    state = 20;
                } else if (symbol == '\n') {
                    state = 39;
                } else {
                    error = true;
                }
                return;
            case 35:
                if (Character.isDigit(symbol)) {
                    state = 45;
                } else {
                    error = true;
                }
                return;
            case 36:
                if (Character.isDigit(symbol)) {
                    state = 46;
                } else {
                    error = true;
                }
                return;
            case 37:
                if (Character.isDigit(symbol)) {
                    state = 47;
                } else {
                    error = true;
                }
                return;
            case 38:
                if (Character.isDigit(symbol)) {
                    state = 45;
                } else if (symbol == '+') {
                    state = 35;
                } else if (symbol == '-') {
                    state = 35;
                } else {
                    error = true;
                }
                return;
            case 39:
                if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 41;
                } else {
                    error = true;
                }
                return;
            case 40:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '=') {
                    state = 42;
                } else {
                    error = true;
                }
                return;
            case 41:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 40;
                } else if (symbol == '=') {
                    state = 42;
                } else {
                    error = true;
                }
                return;
            case 42:
                if (symbol == ' ') {
                    return;
                } else if (Character.isDigit(symbol)) {
                    state = 47;
                } else if (symbol == '-') {
                    state = 37;
                } else if (Character.isLetter(symbol)) {
                    state = 44;
                } else if (symbol == '+') {
                    state = 37;
                } else {
                    error = true;
                }
                return;
            case 43:
                if (symbol == '+') {
                    state = 42;
                } else if (symbol == '*') {
                    state = 42;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == '-') {
                    state = 42;
                } else if (symbol == '/') {
                    state = 42;
                } else if (symbol == '\n') {
                    state = 48;
                } else {
                    error = true;
                }
                return;
            case 44:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '\n') {
                    state = 48;
                } else if (symbol == '+') {
                    state = 42;
                } else if (symbol == ' ') {
                    state = 43;
                } else if (symbol == '*') {
                    state = 42;
                } else if (symbol == '/') {
                    state = 42;
                } else if (symbol == '-') {
                    state = 42;
                } else {
                    error = true;
                }
                return;
            case 45:
                if (symbol == '+') {
                    state = 42;
                } else if (symbol == '*') {
                    state = 42;
                } else if (symbol == ' ') {
                    state = 43;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '-') {
                    state = 42;
                } else if (symbol == '/') {
                    state = 42;
                } else if (symbol == '\n') {
                    state = 48;
                } else {
                    error = true;
                }
                return;
            case 46:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '\n') {
                    state = 48;
                } else if (symbol == ' ') {
                    state = 43;
                } else if (symbol == '*') {
                    state = 42;
                } else if (symbol == '+') {
                    state = 42;
                } else if (symbol == 'e') {
                    state = 38;
                } else if (symbol == '/') {
                    state = 42;
                } else if (symbol == '-') {
                    state = 42;
                } else {
                    error = true;
                }
                return;
            case 47:
                if (symbol == '*') {
                    state = 42;
                } else if (symbol == ' ') {
                    state = 43;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '+') {
                    state = 42;
                } else if (symbol == 'e') {
                    state = 38;
                } else if (symbol == '-') {
                    state = 42;
                } else if (symbol == '/') {
                    state = 42;
                } else if (symbol == '.') {
                    state = 36;
                } else if (symbol == '\n') {
                    state = 48;
                } else {
                    error = true;
                }
                return;
            case 48:
                if (symbol == 'l') {
                    state = 49;
                } else if (symbol == ' ') {
                    return;
                } else {
                    error = true;
                }
                return;
            case 49:
                if (symbol == 'o') {
                    state = 50;
                } else {
                    error = true;
                }
                return;
            case 50:
                if (symbol == 'o') {
                    state = 51;
                } else {
                    error = true;
                }
                return;
            case 51:
                if (symbol == 'p') {
                    state = 52;
                } else {
                    error = true;
                }
                return;
            case 52:
                if (symbol == '\n') {
                    return;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == 0) {
                    state = 53;
                } else {
                    error = true;
                }
                return;
            case -1:
                return;
        }


        error = true;
    }
}
