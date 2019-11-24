import kotlin.Pair;

import javax.swing.*;
import java.awt.*;


public class StateMachine {

    public static void main(String[] args) {
        JFrame frame = new JFrame("State machine");
        frame.setSize(700, 400);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        TextArea textArea = new TextArea("DO WHILE a < b XOR c > d \ne = 1\nloop");
        Label label = new Label(Error.NO_ERROR.getMessage());
        textArea.setRows(15);
        textArea.setColumns(200);

        frame.add(textArea);
        frame.add(label);
        textArea.addTextListener(e -> {
            String string = ((TextArea) e.getSource()).getText();
            StringBuilder builder = new StringBuilder();

            for (char c : string.toCharArray()) {
                if (c != '\r') builder.append(Character.toLowerCase(c));
            }

            Pair<Error, Integer> result = check(builder.toString());
            String message = result.getFirst().getMessage();
            if (result.getFirst() != Error.NO_ERROR)
              message  += ". Символ " + result.getSecond();
            label.setText(message);
        });

        frame.setVisible(true);
    }

    private static Pair<Error, Integer> check(String string) {
        char[] array = (string + (char) 0).toCharArray();
        MachineAnswer state = States.START;
        int i = 0;
        for (; i < array.length && !(state instanceof Error); i++) {
            state = checkSymbol((States) state, array[i]);
        }
        Error error;
        if (state == States.D3)
            error = Error.NO_ERROR;
        else
            error = (Error) state;
        return new Pair<>(error, i);
    }

    private static MachineAnswer checkSymbol(States state, char symbol) {

        switch (state) {
            case START:
                if (symbol == ' ')
                    return States.START;
                if (symbol == '\n')
                    return States.START;
                if (symbol == 'd')
                    return States.B1;
                return new Error("Ожидались следующие символы: пробел, перенос строки, d");

            case B1:
                if (symbol == 'o')
                    return States.C1;
                return new Error("Ожидался o");

            case C1:
                if (symbol == ' ')
                    return States.F1;
                return new Error("Ожидался пробел");

            case D1:
                if (symbol == 'o')
                    return States.E1;
                return new Error("Ожидался o");

            case E1:
                if (symbol == 'r')
                    return States.M1;
                return new Error("Ожидался r");

            case F1:
                if (symbol == ' ')
                    return States.F1;
                if (symbol == 'w')
                    return States.G1;
                return new Error("Ожидались следующие символы: пробел, w");

            case G1:
                if (symbol == 'h')
                    return States.H1;
                return new Error("Ожидался h");

            case H1:
                if (symbol == 'i')
                    return States.I1;
                return new Error("Ожидался i");

            case I1:
                if (symbol == 'l')
                    return States.K1;
                return new Error("Ожидался l");

            case J1:
                if (symbol == 'n')
                    return States.L1;
                return new Error("Ожидался n");

            case K1:
                if (symbol == 'e')
                    return States.M1;
                return new Error("Ожидался e");

            case L1:
                if (symbol == 'd')
                    return States.M1;
                return new Error("Ожидался d");

            case M1:
                if (symbol == ' ')
                    return States.V1;
                return new Error("Ожидался пробел");

            case N1:
                if (Character.isDigit(symbol))
                    return States.Y1;
                return new Error("Ожидался цифра");

            case O1:
                if (Character.isDigit(symbol))
                    return States.A2;
                return new Error("Ожидался цифра");

            case P1:
                if (Character.isDigit(symbol))
                    return States.B2;
                return new Error("Ожидался цифра");

            case Q1:
                if (symbol == '+')
                    return States.N1;
                if (symbol == '-')
                    return States.N1;
                if (Character.isDigit(symbol))
                    return States.Y1;
                return new Error("Ожидались следующие символы: символ +, -, цифра");

            case R1:
                if (Character.isDigit(symbol))
                    return States.G2;
                return new Error("Ожидался цифра");

            case S1:
                if (Character.isDigit(symbol))
                    return States.I2;
                return new Error("Ожидался цифра");

            case T1:
                if (Character.isDigit(symbol))
                    return States.J2;
                return new Error("Ожидался цифра");

            case U1:
                if (Character.isDigit(symbol))
                    return States.G2;
                if (symbol == '+')
                    return States.R1;
                if (symbol == '-')
                    return States.R1;
                return new Error("Ожидались следующие символы: цифра, символ +, -");

            case V1:
                if (Character.isLetter(symbol))
                    return States.X1;
                if (Character.isDigit(symbol))
                    return States.B2;
                if (symbol == '+')
                    return States.P1;
                if (symbol == ' ')
                    return States.V1;
                if (symbol == '-')
                    return States.P1;
                return new Error("Ожидались следующие символы: буква, цифра, символ +, пробел, -");

            case W1:
                if (symbol == '=')
                    return States.C2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '>')
                    return States.D2;
                if (symbol == '<')
                    return States.E2;
                return new Error("Ожидались следующие символы: =, пробел, >, <");

            case X1:
                if (symbol == '>')
                    return States.D2;
                if (Character.isLetterOrDigit(symbol))
                    return States.X1;
                if (symbol == '<')
                    return States.E2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '=')
                    return States.C2;
                return new Error("Ожидались следующие символы: >, буква или цифра, <, пробел, =");

            case Y1:
                if (symbol == '>')
                    return States.D2;
                if (Character.isDigit(symbol))
                    return States.Y1;
                if (symbol == '<')
                    return States.E2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '=')
                    return States.C2;
                return new Error("Ожидались следующие символы: >, цифра, <, пробел, =");

            case A2:
                if (Character.isDigit(symbol))
                    return States.A2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '=')
                    return States.C2;
                if (symbol == '<')
                    return States.E2;
                if (symbol == '>')
                    return States.D2;
                if (symbol == 'e')
                    return States.Q1;
                return new Error("Ожидались следующие символы: цифра, пробел, =, <, >, e");

            case B2:
                if (symbol == 'e')
                    return States.Q1;
                if (symbol == '>')
                    return States.D2;
                if (Character.isDigit(symbol))
                    return States.B2;
                if (symbol == '<')
                    return States.E2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '=')
                    return States.C2;
                if (symbol == '.')
                    return States.O1;
                return new Error("Ожидались следующие символы: e, >, цифра, <, пробел, =, .");

            case C2:
                if (symbol == '-')
                    return States.T1;
                if (symbol == ' ')
                    return States.C2;
                if (Character.isLetter(symbol))
                    return States.F2;
                if (Character.isDigit(symbol))
                    return States.J2;
                if (symbol == '+')
                    return States.T1;
                return new Error("Ожидались следующие символы: -, пробел, буква, цифра, символ +");

            case D2:
                if (symbol == '=')
                    return States.C2;
                if (symbol == ' ')
                    return States.C2;
                if (symbol == '-')
                    return States.T1;
                if (symbol == '+')
                    return States.T1;
                if (Character.isDigit(symbol))
                    return States.J2;
                if (Character.isLetter(symbol))
                    return States.F2;
                return new Error("Ожидались следующие символы: =, пробел, -, символ +, цифра, буква");

            case E2:
                if (Character.isLetter(symbol))
                    return States.F2;
                if (Character.isDigit(symbol))
                    return States.J2;
                if (symbol == '+')
                    return States.T1;
                if (symbol == '-')
                    return States.T1;
                if (symbol == '=')
                    return States.C2;
                if (symbol == ' ')
                    return States.C2;
                if (symbol == '>')
                    return States.C2;
                return new Error("Ожидались следующие символы: буква, цифра, символ +, -, =, пробел, >");

            case F2:
                if (Character.isLetterOrDigit(symbol))
                    return States.F2;
                if (symbol == ' ')
                    return States.H2;
                if (symbol == '\n')
                    return States.O2;
                return new Error("Ожидались следующие символы: буква или цифра, пробел, перенос строки");

            case G2:
                if (symbol == '\n')
                    return States.O2;
                if (Character.isDigit(symbol))
                    return States.G2;
                if (symbol == ' ')
                    return States.H2;
                return new Error("Ожидались следующие символы: перенос строки, цифра, пробел");

            case H2:
                if (symbol == 'o')
                    return States.E1;
                if (symbol == ' ')
                    return States.H2;
                if (symbol == 'x')
                    return States.D1;
                if (symbol == 'a')
                    return States.J1;
                if (symbol == '\n')
                    return States.O2;
                return new Error("Ожидались следующие символы: o, пробел, x, a, перенос строки");

            case I2:
                if (Character.isDigit(symbol))
                    return States.I2;
                if (symbol == 'e')
                    return States.U1;
                if (symbol == '\n')
                    return States.O2;
                if (symbol == ' ')
                    return States.H2;
                return new Error("Ожидались следующие символы: цифра, e, перенос строки, пробел");

            case J2:
                if (Character.isDigit(symbol))
                    return States.J2;
                if (symbol == '.')
                    return States.S1;
                if (symbol == ' ')
                    return States.H2;
                if (symbol == 'e')
                    return States.U1;
                if (symbol == '\n')
                    return States.O2;
                return new Error("Ожидались следующие символы: цифра, ., пробел, e, перенос строки");

            case K2:
                if (Character.isDigit(symbol))
                    return States.U2;
                return new Error("Ожидался цифра");

            case L2:
                if (Character.isDigit(symbol))
                    return States.V2;
                return new Error("Ожидался цифра");

            case M2:
                if (Character.isDigit(symbol))
                    return States.W2;
                return new Error("Ожидался цифра");

            case N2:
                if (Character.isDigit(symbol))
                    return States.U2;
                if (symbol == '+')
                    return States.K2;
                if (symbol == '-')
                    return States.K2;
                return new Error("Ожидались следующие символы: цифра, символ +, -");

            case O2:
                if (symbol == ' ')
                    return States.O2;
                if (Character.isLetter(symbol))
                    return States.Q2;
                return new Error("Ожидались следующие символы: пробел, буква");

            case P2:
                if (symbol == ' ')
                    return States.P2;
                if (symbol == '=')
                    return States.R2;
                return new Error("Ожидались следующие символы: пробел, =");

            case Q2:
                if (Character.isLetterOrDigit(symbol))
                    return States.Q2;
                if (symbol == ' ')
                    return States.P2;
                if (symbol == '=')
                    return States.R2;
                return new Error("Ожидались следующие символы: буква или цифра, пробел, =");

            case R2:
                if (symbol == ' ')
                    return States.R2;
                if (Character.isDigit(symbol))
                    return States.W2;
                if (symbol == '-')
                    return States.M2;
                if (Character.isLetter(symbol))
                    return States.T2;
                if (symbol == '+')
                    return States.M2;
                return new Error("Ожидались следующие символы: пробел, цифра, -, буква, символ +");

            case S2:
                if (symbol == '+')
                    return States.R2;
                if (symbol == '*')
                    return States.R2;
                if (symbol == ' ')
                    return States.S2;
                if (symbol == '-')
                    return States.R2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '\n')
                    return States.X2;
                return new Error("Ожидались следующие символы: символ +, символ *, пробел, -, /, перенос строки");

            case T2:
                if (Character.isLetterOrDigit(symbol))
                    return States.T2;
                if (symbol == '\n')
                    return States.X2;
                if (symbol == '+')
                    return States.R2;
                if (symbol == ' ')
                    return States.S2;
                if (symbol == '*')
                    return States.R2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '-')
                    return States.R2;
                return new Error("Ожидались следующие символы: буква или цифра, перенос строки, символ +, пробел, символ *, /, -");

            case U2:
                if (symbol == '+')
                    return States.R2;
                if (symbol == '*')
                    return States.R2;
                if (symbol == ' ')
                    return States.S2;
                if (Character.isDigit(symbol))
                    return States.U2;
                if (symbol == '-')
                    return States.R2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '\n')
                    return States.X2;
                return new Error("Ожидались следующие символы: символ +, символ *, пробел, цифра, -, /, перенос строки");

            case V2:
                if (Character.isDigit(symbol))
                    return States.V2;
                if (symbol == '\n')
                    return States.X2;
                if (symbol == ' ')
                    return States.S2;
                if (symbol == '*'||symbol == '+')
                    return States.R2;
                if (symbol == 'e')
                    return States.N2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '-')
                    return States.R2;
                return new Error("Ожидались следующие символы: цифра, перенос строки, пробел, символ *, символ +, e, /, -");

            case W2:
                if (symbol == '*')
                    return States.R2;
                if (symbol == ' ')
                    return States.S2;
                if (Character.isDigit(symbol))
                    return States.W2;
                if (symbol == '+')
                    return States.R2;
                if (symbol == 'e')
                    return States.N2;
                if (symbol == '-')
                    return States.R2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '.')
                    return States.L2;
                if (symbol == '\n')
                    return States.X2;
                return new Error("Ожидались следующие символы: символ *, пробел, цифра, символ +, e, -, /, ., перенос строки");

            case X2:
                if (symbol == 'l')
                    return States.Y2;
                if (symbol == ' ')
                    return States.X2;
                return new Error("Ожидались следующие символы: l, пробел");

            case Y2:
                if (symbol == 'o')
                    return States.A3;
                return new Error("Ожидался o");

            case A3:
                if (symbol == 'o')
                    return States.B3;
                return new Error("Ожидался o");

            case B3:
                if (symbol == 'p')
                    return States.C3;
                return new Error("Ожидался p");

            case C3:
                if (symbol == '\n')
                    return States.C3;
                if (symbol == ' ')
                    return States.C3;
                if (symbol == 0)
                    return States.D3;
                return new Error("Ожидались следующие символы: перенос строки, пробел, конец строки");
        }
        throw new RuntimeException("Что то пошло не так");
    }

    interface MachineAnswer {
    }

    private static class Error implements MachineAnswer {
        static final Error NO_ERROR = new Error("Строка подходит");

        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private enum States implements MachineAnswer {
        START,
        B1,
        C1,
        D1,
        E1,
        F1,
        G1,
        H1,
        I1,
        J1,
        K1,
        L1,
        M1,
        N1,
        O1,
        P1,
        Q1,
        R1,
        S1,
        T1,
        U1,
        V1,
        W1,
        X1,
        Y1,
        A2,
        B2,
        C2,
        D2,
        E2,
        F2,
        G2,
        H2,
        I2,
        J2,
        K2,
        L2,
        M2,
        N2,
        O2,
        P2,
        Q2,
        R2,
        S2,
        T2,
        U2,
        V2,
        W2,
        X2,
        Y2,
        A3,
        B3,
        C3,
        D3,
    }
}
