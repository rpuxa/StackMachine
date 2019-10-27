import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

public class StackMachine {

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
            System.out.println(new StackMachine().check(builder.toString()));
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

    private void checkSymbol(char symbol) {
        switch (state) {


            case 0:
                if (symbol == 'd') {
                    state = 3;
                } else if (symbol == ' ') {
                    state = 1;
                } else if (symbol == '\n') {
                    state = 2;
                } else {
                    error = true;
                }
                return;
            case 1:
                if (symbol == ' ') {
                    return;
                } else if (symbol == 'd') {
                    state = 3;
                } else if (symbol == '\n') {
                    state = 2;
                } else {
                    error = true;
                }
                return;
            case 2:
                if (symbol == '\n') {
                    return;
                } else if (symbol == ' ') {
                    state = 1;
                } else if (symbol == 'd') {
                    state = 3;
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
                if (symbol == ' ') {
                    state = 5;
                } else {
                    error = true;
                }
                return;
            case 5:
                if (symbol == ' ') {
                    state = 6;
                } else if (symbol == 'w') {
                    state = 7;
                } else {
                    error = true;
                }
                return;
            case 6:
                if (symbol == ' ') {
                    return;
                } else if (symbol == 'w') {
                    state = 7;
                } else {
                    error = true;
                }
                return;
            case 7:
                if (symbol == 'h') {
                    state = 8;
                } else {
                    error = true;
                }
                return;
            case 8:
                if (symbol == 'i') {
                    state = 9;
                } else {
                    error = true;
                }
                return;
            case 9:
                if (symbol == 'l') {
                    state = 10;
                } else {
                    error = true;
                }
                return;
            case 10:
                if (symbol == 'e') {
                    state = 11;
                } else {
                    error = true;
                }
                return;
            case 11:
                if (symbol == ' ') {
                    state = 12;
                } else {
                    error = true;
                }
                return;
            case 12:
                if (symbol == '-') {
                    state = 13;
                } else if (symbol == ' ') {
                    state = 15;
                } else if (Character.isLetter(symbol)) {
                    state = 14;
                } else if (symbol == '+') {
                    state = 17;
                } else if (Character.isDigit(symbol)) {
                    state = 16;
                } else {
                    error = true;
                }
                return;
            case 13:
                if (Character.isDigit(symbol)) {
                    state = 18;
                } else {
                    error = true;
                }
                return;
            case 14:
                if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == ' ') {
                    state = 19;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 22;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 15:
                if (symbol == '-') {
                    state = 13;
                } else if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 14;
                } else if (Character.isDigit(symbol)) {
                    state = 16;
                } else if (symbol == '+') {
                    state = 17;
                } else {
                    error = true;
                }
                return;
            case 16:
                if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '.') {
                    state = 25;
                } else if (Character.isDigit(symbol)) {
                    state = 24;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 17:
                if (Character.isDigit(symbol)) {
                    state = 26;
                } else {
                    error = true;
                }
                return;
            case 18:
                if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '.') {
                    state = 25;
                } else if (Character.isDigit(symbol)) {
                    state = 24;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 19:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 20:
                if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else if (symbol == '=') {
                    state = 31;
                } else if (symbol == '+') {
                    state = 32;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '-') {
                    state = 27;
                } else if (symbol == '>') {
                    state = 33;
                } else {
                    error = true;
                }
                return;
            case 21:
                if (symbol == '+') {
                    state = 32;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '-') {
                    state = 27;
                } else if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else {
                    error = true;
                }
                return;
            case 22:
                if (symbol == ' ') {
                    state = 19;
                } else if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 23:
                if (symbol == '+') {
                    state = 32;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '-') {
                    state = 27;
                } else if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else if (symbol == '=') {
                    state = 34;
                } else {
                    error = true;
                }
                return;
            case 24:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '.') {
                    state = 25;
                } else if (symbol == '<') {
                    state = 20;
                } else if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '>') {
                    state = 23;
                } else {
                    error = true;
                }
                return;
            case 25:
                if (Character.isDigit(symbol)) {
                    state = 35;
                } else {
                    error = true;
                }
                return;
            case 26:
                if (symbol == '.') {
                    state = 25;
                } else if (Character.isDigit(symbol)) {
                    state = 24;
                } else if (symbol == '<') {
                    state = 20;
                } else if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else {
                    error = true;
                }
                return;
            case 27:
                if (Character.isDigit(symbol)) {
                    state = 36;
                } else {
                    error = true;
                }
                return;
            case 28:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 39;
                } else {
                    error = true;
                }
                return;
            case 29:
                if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    return;
                } else if (symbol == '+') {
                    state = 32;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '-') {
                    state = 27;
                } else {
                    error = true;
                }
                return;
            case 30:
                if (Character.isDigit(symbol)) {
                    state = 40;
                } else if (symbol == '.') {
                    state = 41;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '\n') {
                    state = 38;
                } else {
                    error = true;
                }
                return;
            case 31:
                if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else if (symbol == '+') {
                    state = 32;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '-') {
                    state = 27;
                } else {
                    error = true;
                }
                return;
            case 32:
                if (Character.isDigit(symbol)) {
                    state = 42;
                } else {
                    error = true;
                }
                return;
            case 33:
                if (symbol == '-') {
                    state = 27;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '+') {
                    state = 32;
                } else if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else {
                    error = true;
                }
                return;
            case 34:
                if (Character.isLetter(symbol)) {
                    state = 28;
                } else if (symbol == ' ') {
                    state = 29;
                } else if (symbol == '-') {
                    state = 27;
                } else if (Character.isDigit(symbol)) {
                    state = 30;
                } else if (symbol == '+') {
                    state = 32;
                } else {
                    error = true;
                }
                return;
            case 35:
                if (Character.isDigit(symbol)) {
                    state = 43;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 36:
                if (Character.isDigit(symbol)) {
                    state = 40;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '.') {
                    state = 41;
                } else if (symbol == '\n') {
                    state = 38;
                } else {
                    error = true;
                }
                return;
            case 37:
                if (symbol == ' ') {
                    state = 44;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (symbol == 'x') {
                    state = 47;
                } else if (symbol == 'a') {
                    state = 46;
                } else if (symbol == 'o') {
                    state = 45;
                } else {
                    error = true;
                }
                return;
            case 38:
                if (symbol == ' ') {
                    state = 49;
                } else if (Character.isLetter(symbol)) {
                    state = 48;
                } else {
                    error = true;
                }
                return;
            case 39:
                if (symbol == ' ') {
                    state = 37;
                } else if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '\n') {
                    state = 38;
                } else {
                    error = true;
                }
                return;
            case 40:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '.') {
                    state = 41;
                } else if (symbol == '\n') {
                    state = 38;
                } else {
                    error = true;
                }
                return;
            case 41:
                if (Character.isDigit(symbol)) {
                    state = 50;
                } else {
                    error = true;
                }
                return;
            case 42:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == '.') {
                    state = 41;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isDigit(symbol)) {
                    state = 40;
                } else {
                    error = true;
                }
                return;
            case 43:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '>') {
                    state = 23;
                } else if (symbol == '=') {
                    state = 21;
                } else if (symbol == ' ') {
                    state = 19;
                } else if (symbol == '<') {
                    state = 20;
                } else {
                    error = true;
                }
                return;
            case 44:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (symbol == 'x') {
                    state = 47;
                } else if (symbol == 'a') {
                    state = 46;
                } else if (symbol == 'o') {
                    state = 45;
                } else {
                    error = true;
                }
                return;
            case 45:
                if (symbol == 'r') {
                    state = 51;
                } else {
                    error = true;
                }
                return;
            case 46:
                if (symbol == 'n') {
                    state = 52;
                } else {
                    error = true;
                }
                return;
            case 47:
                if (symbol == 'o') {
                    state = 53;
                } else {
                    error = true;
                }
                return;
            case 48:
                if (Character.isLetterOrDigit(symbol)) {
                    state = 56;
                } else if (symbol == '=') {
                    state = 55;
                } else if (symbol == ' ') {
                    state = 54;
                } else {
                    error = true;
                }
                return;
            case 49:
                if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 48;
                } else {
                    error = true;
                }
                return;
            case 50:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isDigit(symbol)) {
                    state = 57;
                } else {
                    error = true;
                }
                return;
            case 51:
            case 60:
            case 59:
                if (symbol == ' ') {
                    state = 58;
                } else {
                    error = true;
                }
                return;
            case 52:
                if (symbol == 'd') {
                    state = 59;
                } else {
                    error = true;
                }
                return;
            case 53:
                if (symbol == 'r') {
                    state = 60;
                } else {
                    error = true;
                }
                return;
            case 54:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '=') {
                    state = 55;
                } else {
                    error = true;
                }
                return;
            case 55:
                if (symbol == ' ') {
                    state = 63;
                } else if (Character.isDigit(symbol)) {
                    state = 64;
                } else if (symbol == '+') {
                    state = 65;
                } else if (symbol == '-') {
                    state = 61;
                } else if (Character.isLetter(symbol)) {
                    state = 62;
                } else {
                    error = true;
                }
                return;
            case 56:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '=') {
                    state = 55;
                } else if (symbol == ' ') {
                    state = 54;
                } else {
                    error = true;
                }
                return;
            case 57:
            case 126:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '\n') {
                    state = 38;
                } else {
                    error = true;
                }
                return;
            case 58:
                if (Character.isLetter(symbol)) {
                    state = 67;
                } else if (symbol == '+') {
                    state = 70;
                } else if (Character.isDigit(symbol)) {
                    state = 69;
                } else if (symbol == '-') {
                    state = 66;
                } else if (symbol == ' ') {
                    state = 68;
                } else {
                    error = true;
                }
                return;
            case 61:
                if (Character.isDigit(symbol)) {
                    state = 71;
                } else {
                    error = true;
                }
                return;
            case 62:
                if (symbol == '+') {
                    state = 77;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '/') {
                    state = 78;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 75;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 63:
                if (symbol == ' ') {
                    return;
                } else if (Character.isDigit(symbol)) {
                    state = 64;
                } else if (Character.isLetter(symbol)) {
                    state = 62;
                } else if (symbol == '-') {
                    state = 61;
                } else if (symbol == '+') {
                    state = 65;
                } else {
                    error = true;
                }
                return;
            case 64:
                if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '.') {
                    state = 80;
                } else if (Character.isDigit(symbol)) {
                    state = 79;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 65:
                if (Character.isDigit(symbol)) {
                    state = 81;
                } else {
                    error = true;
                }
                return;
            case 66:
                if (Character.isDigit(symbol)) {
                    state = 82;
                } else {
                    error = true;
                }
                return;
            case 67:
                if (symbol == ' ') {
                    state = 83;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '<') {
                    state = 84;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 86;
                } else {
                    error = true;
                }
                return;
            case 68:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '-') {
                    state = 66;
                } else if (Character.isLetter(symbol)) {
                    state = 67;
                } else if (symbol == '+') {
                    state = 70;
                } else if (Character.isDigit(symbol)) {
                    state = 69;
                } else {
                    error = true;
                }
                return;
            case 69:
                if (symbol == ' ') {
                    state = 83;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == '.') {
                    state = 89;
                } else if (Character.isDigit(symbol)) {
                    state = 88;
                } else if (symbol == '<') {
                    state = 84;
                } else {
                    error = true;
                }
                return;
            case 70:
                if (Character.isDigit(symbol)) {
                    state = 90;
                } else {
                    error = true;
                }
                return;
            case 71:
                if (symbol == '/') {
                    state = 78;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '.') {
                    state = 80;
                } else if (Character.isDigit(symbol)) {
                    state = 79;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 72:
                if (Character.isDigit(symbol)) {
                    state = 94;
                } else if (symbol == ' ') {
                    state = 93;
                } else if (Character.isLetter(symbol)) {
                    state = 92;
                } else if (symbol == '+') {
                    state = 95;
                } else if (symbol == '-') {
                    state = 91;
                } else {
                    error = true;
                }
                return;
            case 73:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 74:
                if (symbol == 'l') {
                    state = 97;
                } else if (symbol == ' ') {
                    state = 96;
                } else {
                    error = true;
                }
                return;
            case 75:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '-') {
                    state = 72;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '/') {
                    state = 78;
                } else {
                    error = true;
                }
                return;
            case 76:
                if (symbol == '-') {
                    state = 91;
                } else if (symbol == '+') {
                    state = 95;
                } else if (Character.isLetter(symbol)) {
                    state = 92;
                } else if (symbol == ' ') {
                    state = 93;
                } else if (Character.isDigit(symbol)) {
                    state = 94;
                } else {
                    error = true;
                }
                return;
            case 77:
                if (symbol == '-') {
                    state = 91;
                } else if (symbol == '+') {
                    state = 95;
                } else if (Character.isLetter(symbol)) {
                    state = 92;
                } else if (Character.isDigit(symbol)) {
                    state = 94;
                } else if (symbol == ' ') {
                    state = 93;
                } else {
                    error = true;
                }
                return;
            case 78:
                if (symbol == '+') {
                    state = 95;
                } else if (symbol == '-') {
                    state = 91;
                } else if (Character.isDigit(symbol)) {
                    state = 94;
                } else if (symbol == ' ') {
                    state = 93;
                } else if (Character.isLetter(symbol)) {
                    state = 92;
                } else {
                    error = true;
                }
                return;
            case 79:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '.') {
                    state = 80;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 80:
                if (Character.isDigit(symbol)) {
                    state = 98;
                } else {
                    error = true;
                }
                return;
            case 81:
                if (symbol == '-') {
                    state = 72;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '.') {
                    state = 80;
                } else if (Character.isDigit(symbol)) {
                    state = 79;
                } else {
                    error = true;
                }
                return;
            case 82:
                if (symbol == ' ') {
                    state = 83;
                } else if (Character.isDigit(symbol)) {
                    state = 88;
                } else if (symbol == '.') {
                    state = 89;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '<') {
                    state = 84;
                } else {
                    error = true;
                }
                return;
            case 83:
                if (symbol == ' ') {
                    return;
                } else if (symbol == '<') {
                    state = 84;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '>') {
                    state = 87;
                } else {
                    error = true;
                }
                return;
            case 84:
                if (symbol == '>') {
                    state = 105;
                } else if (symbol == '-') {
                    state = 99;
                } else if (symbol == '+') {
                    state = 104;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else if (symbol == '=') {
                    state = 103;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (symbol == ' ') {
                    state = 101;
                } else {
                    error = true;
                }
                return;
            case 85:
                if (symbol == '+') {
                    state = 104;
                } else if (symbol == '-') {
                    state = 99;
                } else if (symbol == ' ') {
                    state = 101;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else {
                    error = true;
                }
                return;
            case 86:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '<') {
                    state = 84;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == ' ') {
                    state = 83;
                } else {
                    error = true;
                }
                return;
            case 87:
                if (symbol == '-') {
                    state = 99;
                } else if (symbol == '=') {
                    state = 106;
                } else if (symbol == '+') {
                    state = 104;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (symbol == ' ') {
                    state = 101;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else {
                    error = true;
                }
                return;
            case 88:
                if (symbol == '.') {
                    state = 89;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '<') {
                    state = 84;
                } else if (symbol == ' ') {
                    state = 83;
                } else {
                    error = true;
                }
                return;
            case 89:
                if (Character.isDigit(symbol)) {
                    state = 107;
                } else {
                    error = true;
                }
                return;
            case 90:
                if (symbol == ' ') {
                    state = 83;
                } else if (symbol == '<') {
                    state = 84;
                } else if (Character.isDigit(symbol)) {
                    state = 88;
                } else if (symbol == '.') {
                    state = 89;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == '=') {
                    state = 85;
                } else {
                    error = true;
                }
                return;
            case 91:
                if (Character.isDigit(symbol)) {
                    state = 108;
                } else {
                    error = true;
                }
                return;
            case 92:
                if (symbol == '-') {
                    state = 72;
                } else if (symbol == '*') {
                    state = 76;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 109;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else {
                    error = true;
                }
                return;
            case 93:
                if (Character.isDigit(symbol)) {
                    state = 94;
                } else if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 92;
                } else if (symbol == '+') {
                    state = 95;
                } else if (symbol == '-') {
                    state = 91;
                } else {
                    error = true;
                }
                return;
            case 94:
                if (Character.isDigit(symbol)) {
                    state = 110;
                } else if (symbol == '-') {
                    state = 72;
                } else if (symbol == '.') {
                    state = 111;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '*') {
                    state = 76;
                } else {
                    error = true;
                }
                return;
            case 95:
                if (Character.isDigit(symbol)) {
                    state = 112;
                } else {
                    error = true;
                }
                return;
            case 96:
                if (symbol == ' ') {
                    return;
                } else if (symbol == 'l') {
                    state = 97;
                } else {
                    error = true;
                }
                return;
            case 97:
                if (symbol == 'o') {
                    state = 113;
                } else {
                    error = true;
                }
                return;
            case 98:
                if (Character.isDigit(symbol)) {
                    state = 114;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 99:
                if (Character.isDigit(symbol)) {
                    state = 115;
                } else {
                    error = true;
                }
                return;
            case 100:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isLetterOrDigit(symbol)) {
                    state = 116;
                } else {
                    error = true;
                }
                return;
            case 101:
                if (symbol == ' ') {
                    return;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else if (symbol == '+') {
                    state = 104;
                } else if (symbol == '-') {
                    state = 99;
                } else {
                    error = true;
                }
                return;
            case 102:
                if (symbol == ' ') {
                    state = 37;
                } else if (Character.isDigit(symbol)) {
                    state = 117;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (symbol == '.') {
                    state = 118;
                } else {
                    error = true;
                }
                return;
            case 103:
                if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (symbol == ' ') {
                    state = 101;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else if (symbol == '-') {
                    state = 99;
                } else if (symbol == '+') {
                    state = 104;
                } else {
                    error = true;
                }
                return;
            case 104:
                if (Character.isDigit(symbol)) {
                    state = 119;
                } else {
                    error = true;
                }
                return;
            case 105:
                if (symbol == '-') {
                    state = 99;
                } else if (symbol == '+') {
                    state = 104;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (symbol == ' ') {
                    state = 101;
                } else {
                    error = true;
                }
                return;
            case 106:
                if (symbol == '-') {
                    state = 99;
                } else if (symbol == '+') {
                    state = 104;
                } else if (Character.isDigit(symbol)) {
                    state = 102;
                } else if (Character.isLetter(symbol)) {
                    state = 100;
                } else if (symbol == ' ') {
                    state = 101;
                } else {
                    error = true;
                }
                return;
            case 107:
                if (symbol == '<') {
                    state = 84;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '>') {
                    state = 87;
                } else if (symbol == ' ') {
                    state = 83;
                } else if (Character.isDigit(symbol)) {
                    state = 120;
                } else {
                    error = true;
                }
                return;
            case 108:
                if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (Character.isDigit(symbol)) {
                    state = 110;
                } else if (symbol == '-') {
                    state = 72;
                } else if (symbol == '.') {
                    state = 111;
                } else {
                    error = true;
                }
                return;
            case 109:
                if (symbol == '+') {
                    state = 77;
                } else if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 110:
                if (symbol == '-') {
                    state = 72;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '.') {
                    state = 111;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else {
                    error = true;
                }
                return;
            case 111:
                if (Character.isDigit(symbol)) {
                    state = 121;
                } else {
                    error = true;
                }
                return;
            case 112:
                if (symbol == '.') {
                    state = 111;
                } else if (Character.isDigit(symbol)) {
                    state = 110;
                } else if (symbol == '-') {
                    state = 72;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else {
                    error = true;
                }
                return;
            case 113:
                if (symbol == 'o') {
                    state = 122;
                } else {
                    error = true;
                }
                return;
            case 114:
                if (symbol == '*') {
                    state = 76;
                } else if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '+') {
                    state = 77;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 115:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isDigit(symbol)) {
                    state = 117;
                } else if (symbol == '.') {
                    state = 118;
                } else {
                    error = true;
                }
                return;
            case 116:
                if (Character.isLetterOrDigit(symbol)) {
                    return;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else {
                    error = true;
                }
                return;
            case 117:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (symbol == '.') {
                    state = 118;
                } else {
                    error = true;
                }
                return;
            case 118:
                if (Character.isDigit(symbol)) {
                    state = 123;
                } else {
                    error = true;
                }
                return;
            case 119:
                if (symbol == '\n') {
                    state = 38;
                } else if (symbol == ' ') {
                    state = 37;
                } else if (Character.isDigit(symbol)) {
                    state = 117;
                } else if (symbol == '.') {
                    state = 118;
                } else {
                    error = true;
                }
                return;
            case 120:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == ' ') {
                    state = 83;
                } else if (symbol == '<') {
                    state = 84;
                } else if (symbol == '=') {
                    state = 85;
                } else if (symbol == '>') {
                    state = 87;
                } else {
                    error = true;
                }
                return;
            case 121:
                if (symbol == '+') {
                    state = 77;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '/') {
                    state = 78;
                } else if (Character.isDigit(symbol)) {
                    state = 124;
                } else if (symbol == '-') {
                    state = 72;
                } else {
                    error = true;
                }
                return;
            case 122:
                if (symbol == 'p') {
                    state = 125;
                } else {
                    error = true;
                }
                return;
            case 123:
                if (symbol == ' ') {
                    state = 37;
                } else if (symbol == '\n') {
                    state = 38;
                } else if (Character.isDigit(symbol)) {
                    state = 126;
                } else {
                    error = true;
                }
                return;
            case 124:
                if (Character.isDigit(symbol)) {
                    return;
                } else if (symbol == '-') {
                    state = 72;
                } else if (symbol == ' ') {
                    state = 73;
                } else if (symbol == '\n') {
                    state = 74;
                } else if (symbol == '/') {
                    state = 78;
                } else if (symbol == '*') {
                    state = 76;
                } else if (symbol == '+') {
                    state = 77;
                } else {
                    error = true;
                }
                return;
            case 125:
                if (symbol == '\n') {
                    state = 128;
                } else if (symbol == ' ') {
                    state = 127;
                } else if (symbol == 0) {
                    state = 129;
                } else {
                    error = true;
                }
                return;
            case 127:
                if (symbol == ' ') {
                    return;
                } else if (symbol == 0) {
                    state = 129;
                } else if (symbol == '\n') {
                    state = 128;
                } else {
                    error = true;
                }
                return;
            case 128:
                if (symbol == '\n') {
                    return;
                } else if (symbol == ' ') {
                    state = 127;
                } else if (symbol == 0) {
                    state = 129;
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
