import kotlin.text.StringsKt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class StateMachine {

    public static void main(String[] args) {
        JFrame frame = new JFrame("State machine");
        frame.setSize(700, 450);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JTextArea textArea = new JTextArea("DO WHILE a < b XOR c > d \ne = 1\nloop");
        JLabel label = new JLabel();
        JButton gotoError = new JButton("Перейти к ошибке");

        textArea.setRows(15);
        textArea.setColumns(200);
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        gotoError.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);


        frame.add(textArea);
        frame.add(Box.createVerticalStrut(20));
        frame.add(label);
        frame.add(Box.createVerticalStrut(20));
        frame.add(gotoError);
        frame.add(Box.createVerticalStrut(5));

        gotoError.setVisible(false);

        final StateMachine[] machine = {null};

        gotoError.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int symbolIndex = machine[0].error.symbolIndex;
                textArea.requestFocus();
                textArea.setCaretPosition(symbolIndex);
            }
        });

        Runnable onKey = () -> {
            String string = textArea.getText();
            StringBuilder builder = new StringBuilder();

            for (char c : string.toCharArray()) {
                if (c != '\r') builder.append(Character.toLowerCase(c));
            }

            machine[0] = check(builder.toString());
            StringBuilder message = new StringBuilder("<html>");
            message.append(machine[0].error.message);

            if (machine[0].error == Error.NO_ERROR) {
                message.append("<br/>Индентификаторы: ");
                boolean first = true;
                for (String id : machine[0].ids) {
                    if (!first) {
                        message.append(", ");
                    }
                    message.append(id);
                    first = false;
                }
                if (machine[0].ids.isEmpty())
                    message.append("отсутствуют");

                message.append("<br/>Константы: ");
                first = true;
                for (String constant : machine[0].constants) {
                    if (!first) {
                        message.append(", ");
                    }
                    message.append(constant);
                    first = false;
                }

                if (machine[0].constants.isEmpty())
                    message.append("отсутствуют");
            }

            message.append("</html>");

            label.setText(message.toString());
            gotoError.setVisible(machine[0].error != Error.NO_ERROR);
        };

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                onKey.run();
            }
        });

        onKey.run();
        frame.setVisible(true);
    }

    Error error = Error.NO_ERROR;
    List<String> constants = new ArrayList<>();
    List<String> ids = new ArrayList<>();


    private StringBuilder toCheck = new StringBuilder();
    private States state = States.START;
    private char symbol;

    private static StateMachine check(String string) {
        StateMachine machine = new StateMachine();
        char[] array = (string + (char) 0).toCharArray();
        int i = 0;
        for (; i < array.length && machine.error == Error.NO_ERROR; i++) {
            machine.symbol = array[i];
            machine.checkSymbol();
        }

        machine.error.symbolIndex = i - 1;
        return machine;
    }

    private void checkSymbol() {

        switch (state) {
            case START:
                if (symbol == ' ' || symbol == '\n') {
                    state = States.START;
                } else if (symbol == 'd') {
                    state = States.B1;
                } else {
                    error = new Error("Ожидались следующие символы: пробел, перенос строки, d");
                }
                break;

            case B1:
                if (symbol == 'o') {
                    state = States.C1;
                } else {
                    error = new Error("Ожидались следующие символы: o");
                }
                break;

            case C1:
                if (symbol == ' ') {
                    state = States.F1;
                } else {
                    error = new Error("Ожидались следующие символы: пробел");
                }
                break;

            case D1:
                if (symbol == 'o') {
                    state = States.E1;
                } else {
                    error = new Error("Ожидались следующие символы: o");
                }
                break;

            case E1:
                if (symbol == 'r') {
                    state = States.M1;
                } else {
                    error = new Error("Ожидались следующие символы: r");
                }
                break;

            case F1:
                if (symbol == ' ') {
                    state = States.F1;
                } else if (symbol == 'w') {
                    state = States.G1;
                } else {
                    error = new Error("Ожидались следующие символы: пробел, w");
                }
                break;

            case G1:
                if (symbol == 'h') {
                    state = States.H1;
                } else {
                    error = new Error("Ожидались следующие символы: h");
                }
                break;

            case H1:
                if (symbol == 'i') {
                    state = States.I1;
                } else {
                    error = new Error("Ожидались следующие символы: i");
                }
                break;

            case I1:
                if (symbol == 'l') {
                    state = States.K1;
                } else {
                    error = new Error("Ожидались следующие символы: l");
                }
                break;

            case J1:
                if (symbol == 'n') {
                    state = States.L1;
                } else {
                    error = new Error("Ожидались следующие символы: n");
                }
                break;

            case K1:
                if (symbol == 'e') {
                    state = States.M1;
                } else {
                    error = new Error("Ожидались следующие символы: e");
                }
                break;

            case L1:
                if (symbol == 'd') {
                    state = States.M1;
                } else {
                    error = new Error("Ожидались следующие символы: d");
                }
                break;

            case M1:
                if (symbol == ' ') {
                    state = States.V1;
                } else {
                    error = new Error("Ожидались следующие символы: пробел");
                }
                break;

            case N1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.Y1;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case O1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.A2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case P1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.B2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case Q1:
                if (symbol == '+' || symbol == '-') {
                    addSymbol();
                    state = States.N1;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.Y1;
                } else {
                    error = new Error("Ожидались следующие символы: +, -, цифра");
                }
                break;

            case R1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.G2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case S1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.I2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case T1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.J2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case U1:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.G2;
                } else if (symbol == '+' || symbol == '-') {
                    addSymbol();
                    state = States.R1;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, +, -");
                }
                break;

            case V1:
                if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.X1;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.B2;
                } else if (symbol == '+' || symbol == '-') {
                    addSymbol();
                    state = States.P1;
                } else if (symbol == ' ') {
                    state = States.V1;
                } else {
                    error = new Error("Ожидались следующие символы: буква, цифра, +, пробел, -");
                }
                break;

            case W1:
                if (symbol == '=') {
                    state = States.C2;
                } else if (symbol == ' ') {
                    state = States.W1;
                } else if (symbol == '>') {
                    state = States.D2;
                } else if (symbol == '<') {
                    state = States.E2;
                } else {
                    error = new Error("Ожидались следующие символы: =, пробел, >, <");
                }
                break;

            case X1:
                if (symbol == '>') {
                    checkId();
                    state = States.D2;
                } else if (Character.isLetterOrDigit(symbol)) {
                    addSymbol();
                    state = States.X1;
                } else if (symbol == '<') {
                    checkId();
                    state = States.E2;
                } else if (symbol == ' ') {
                    checkId();
                    state = States.W1;
                } else if (symbol == '=') {
                    checkId();
                    state = States.C2;
                } else {
                    error = new Error("Ожидались следующие символы: >, буква или цифра, <, пробел, =");
                }
                break;

            case Y1:
                if (symbol == '>') {
                    checkConstant();
                    state = States.D2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.Y1;
                } else if (symbol == '<') {
                    checkConstant();
                    state = States.E2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.W1;
                } else if (symbol == '=') {
                    checkConstant();
                    state = States.C2;
                } else {
                    error = new Error("Ожидались следующие символы: >, цифра, <, пробел, =");
                }
                break;

            case A2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.A2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.W1;
                } else if (symbol == '=') {
                    checkConstant();
                    state = States.C2;
                } else if (symbol == '<') {
                    checkConstant();
                    state = States.E2;
                } else if (symbol == '>') {
                    checkConstant();
                    state = States.D2;
                } else if (symbol == 'e') {
                    addSymbol();
                    state = States.Q1;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, пробел, =, <, >, e");
                }
                break;

            case B2:
                if (symbol == 'e') {
                    addSymbol();
                    state = States.Q1;
                } else if (symbol == '>') {
                    checkConstant();
                    state = States.D2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.B2;
                } else if (symbol == '<') {
                    checkConstant();
                    state = States.E2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.W1;
                } else if (symbol == '=') {
                    checkConstant();
                    state = States.C2;
                } else if (symbol == '.') {
                    addSymbol();
                    state = States.O1;
                } else {
                    error = new Error("Ожидались следующие символы: e, >, цифра, <, пробел, =, .");
                }
                break;

            case C2:
                if (symbol == '-' || symbol == '+') {
                    addSymbol();
                    state = States.T1;
                } else if (symbol == ' ') {
                    state = States.C2;
                } else if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.F2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.J2;
                } else {
                    error = new Error("Ожидались следующие символы: -, пробел, буква, цифра, +");
                }
                break;

            case D2:
                if (symbol == '=' || symbol == ' ') {
                    state = States.C2;
                } else if (symbol == '-' || symbol == '+') {
                    addSymbol();
                    state = States.T1;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.J2;
                } else if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.F2;
                } else {
                    error = new Error("Ожидались следующие символы: =, пробел, -, +, цифра, буква");
                }
                break;

            case E2:
                if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.F2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.J2;
                } else if (symbol == '+' || symbol == '-') {
                    addSymbol();
                    state = States.T1;
                } else if (symbol == '=' || symbol == ' ' || symbol == '>') {
                    state = States.C2;
                } else {
                    error = new Error("Ожидались следующие символы: буква, цифра, +, -, =, пробел, >");
                }
                break;

            case F2:
                if (Character.isLetterOrDigit(symbol)) {
                    addSymbol();
                    state = States.F2;
                } else if (symbol == ' ') {
                    checkId();
                    state = States.H2;
                } else if (symbol == '\n') {
                    checkId();
                    state = States.O2;
                } else {
                    error = new Error("Ожидались следующие символы: буква или цифра, пробел, перенос строки");
                }
                break;

            case G2:
                if (symbol == '\n') {
                    checkConstant();
                    state = States.O2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.G2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.H2;
                } else {
                    error = new Error("Ожидались следующие символы: перенос строки, цифра, пробел");
                }
                break;

            case H2:
                if (symbol == 'o') {
                    state = States.E1;
                } else if (symbol == ' ') {
                    state = States.H2;
                } else if (symbol == 'x') {
                    state = States.D1;
                } else if (symbol == 'a') {
                    state = States.J1;
                } else if (symbol == '\n') {
                    state = States.O2;
                } else {
                    error = new Error("Ожидались следующие символы: o, пробел, x, a, перенос строки");
                }
                break;

            case I2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.I2;
                } else if (symbol == 'e') {
                    addSymbol();
                    state = States.U1;
                } else if (symbol == '\n') {
                    checkConstant();
                    state = States.O2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.H2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, e, перенос строки, пробел");
                }
                break;

            case J2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.J2;
                } else if (symbol == '.') {
                    addSymbol();
                    state = States.S1;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.H2;
                } else if (symbol == 'e') {
                    addSymbol();
                    state = States.U1;
                } else if (symbol == '\n') {
                    checkConstant();
                    state = States.O2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, ., пробел, e, перенос строки");
                }
                break;

            case K2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.U2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case L2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.V2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case M2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.W2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра");
                }
                break;

            case N2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.U2;
                } else if (symbol == '+' || symbol == '-') {
                    addSymbol();
                    state = States.K2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, +, -");
                }
                break;

            case O2:
                if (symbol == ' ') {
                    state = States.O2;
                } else if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.Q2;
                } else {
                    error = new Error("Ожидались следующие символы: пробел, буква");
                }
                break;

            case P2:
                if (symbol == ' ') {
                    state = States.P2;
                } else if (symbol == '=') {
                    state = States.R2;
                } else {
                    error = new Error("Ожидались следующие символы: пробел, =");
                }
                break;

            case Q2:
                if (Character.isLetterOrDigit(symbol)) {
                    addSymbol();
                    state = States.Q2;
                } else if (symbol == ' ') {
                    checkId();
                    state = States.P2;
                } else if (symbol == '=') {
                    checkId();
                    state = States.R2;
                } else {
                    error = new Error("Ожидались следующие символы: буква или цифра, пробел, =");
                }
                break;

            case R2:
                if (symbol == ' ') {
                    state = States.R2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.W2;
                } else if (symbol == '-' || symbol == '+') {
                    addSymbol();
                    state = States.M2;
                } else if (Character.isLetter(symbol)) {
                    addSymbol();
                    state = States.T2;
                } else {
                    error = new Error("Ожидались следующие символы: пробел, цифра, -, буква, +");
                }
                break;

            case S2:
                if (symbol == '+' || symbol == '*' || symbol == '-' || symbol == '/') {
                    state = States.R2;
                } else if (symbol == ' ') {
                    state = States.S2;
                } else if (symbol == '\n') {
                    state = States.X2;
                } else {
                    error = new Error("Ожидались следующие символы: +, *, пробел, -, /, перенос строки");
                }
                break;

            case T2:
                if (Character.isLetterOrDigit(symbol)) {
                    addSymbol();
                    state = States.T2;
                } else if (symbol == '\n') {
                    checkId();
                    state = States.X2;
                } else if (symbol == '+' || symbol == '*' || symbol == '/' || symbol == '-') {
                    checkId();
                    state = States.R2;
                } else if (symbol == ' ') {
                    checkId();
                    state = States.S2;
                } else {
                    error = new Error("Ожидались следующие символы: буква или цифра, перенос строки, +, пробел, *, /, -");
                }
                break;

            case U2:
                if (symbol == '+' || symbol == '*' || symbol == '-' || symbol == '/') {
                    checkConstant();
                    state = States.R2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.S2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.U2;
                } else if (symbol == '\n') {
                    checkConstant();
                    state = States.X2;
                } else {
                    error = new Error("Ожидались следующие символы: +, *, пробел, цифра, -, /, перенос строки");
                }
                break;

            case V2:
                if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.V2;
                } else if (symbol == '\n') {
                    checkConstant();
                    state = States.X2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.S2;
                } else if (symbol == '*' || symbol == '+' || symbol == '/' || symbol == '-') {
                    checkConstant();
                    state = States.R2;
                } else if (symbol == 'e') {
                    addSymbol();
                    state = States.N2;
                } else {
                    error = new Error("Ожидались следующие символы: цифра, перенос строки, пробел, *, +, e, /, -");
                }
                break;

            case W2:
                if (symbol == '*' || symbol == '+' || symbol == '-' || symbol == '/') {
                    checkConstant();
                    state = States.R2;
                } else if (symbol == ' ') {
                    checkConstant();
                    state = States.S2;
                } else if (Character.isDigit(symbol)) {
                    addSymbol();
                    state = States.W2;
                } else if (symbol == 'e') {
                    addSymbol();
                    state = States.N2;
                } else if (symbol == '.') {
                    addSymbol();
                    state = States.L2;
                } else if (symbol == '\n') {
                    checkConstant();
                    state = States.X2;
                } else {
                    error = new Error("Ожидались следующие символы: *, пробел, цифра, +, e, -, /, ., перенос строки");
                }
                break;

            case X2:
                if (symbol == 'l') {
                    state = States.Y2;
                } else if (symbol == ' ') {
                    state = States.X2;
                } else {
                    error = new Error("Ожидались следующие символы: l, пробел");
                }
                break;

            case Y2:
                if (symbol == 'o') {
                    state = States.A3;
                } else {
                    error = new Error("Ожидались следующие символы: o");
                }
                break;

            case A3:
                if (symbol == 'o') {
                    state = States.B3;
                } else {
                    error = new Error("Ожидались следующие символы: o");
                }
                break;

            case B3:
                if (symbol == 'p') {
                    state = States.C3;
                } else {
                    error = new Error("Ожидались следующие символы: p");
                }
                break;

            case C3:
                if (symbol == '\n' || symbol == ' ') {
                    state = States.C3;
                } else if (symbol == 0) {
                    state = States.D3;
                } else {
                    error = new Error("Ожидались следующие символы: перенос строки, пробел, конец строки");
                }
                break;
        }

    }

    private void addSymbol() {
        toCheck.append(symbol);
    }


    private static final String[] KEY_WORDS = {
            "do", "while", "loop", "and", "or", "xor"
    };

    private void checkId() {
        String string = toCheck.toString();
        if (string.length() > 8) {
            error = new Error("Индентификатор " + string + " содержит больше 8 символов");
            return;
        }
        for (String keyWord : KEY_WORDS) {
            if (keyWord.equals(string)) {
                error = new Error("Некорректное название индентификатора");
                return;
            }
        }
        ids.add(string);
        StringsKt.clear(toCheck);
    }

    private void checkConstant() {
        String string = toCheck.toString();
        try {
            int i = Integer.parseInt(string);
            if (i < -32768 || i > 32767) {
                error = new Error("Целое число должно быть в диапазоне -32768 – 32767");
                return;
            }
        } catch (NumberFormatException ignored) {
        }
        constants.add(string);
        StringsKt.clear(toCheck);
    }

    private static class Error {
        static final Error NO_ERROR = new Error("Строка подходит");

        String message;
        int symbolIndex;

        Error(String message) {
            this.message = message;
        }
    }

    private enum States {
        START, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1,
        W1, X1, Y1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2, N2, O2, P2, Q2, R2, S2, T2,
        U2, V2, W2, X2, Y2, A3, B3, C3, D3,
    }
}
