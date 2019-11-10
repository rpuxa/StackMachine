import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;


public class StateMachine {

    public static void main(String[] args) {
        JFrame frame = new JFrame("State machine");
        frame.setSize(700, 400);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        TextArea textArea = new TextArea("DO WHILE a < b XOR c > d \ne = 1\nloop");
        Label label = new Label("Подходит");
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

            label.setText(check(builder.toString()) ? "Подходит" : "Не подходит");
        });

        frame.setVisible(true);
    }

    private static boolean check(String string) {
        char[] array = (string + (char) 0).toCharArray();
        States state = States.START;
        for (int i = 0; i < array.length && state != States.FINISH && state != States.ERROR; i++) {
            state = checkSymbol(state, array[i]);
        }
        return state != States.ERROR;
    }

    private static States checkSymbol(States state, char symbol) {

        switch (state) {
            case START:
                if (symbol == ' ')
                    return States.START;
                if (symbol == '\n')
                    return States.START;
                if (symbol == 'd')
                    return States.B1;
                break;

            case B1:
                if (symbol == 'o')
                    return States.C1;
                break;

            case C1:
                if (symbol == ' ')
                    return States.F1;
                break;

            case D1:
                if (symbol == 'o')
                    return States.E1;
                break;

            case E1:
                if (symbol == 'r')
                    return States.M1;
                break;

            case F1:
                if (symbol == ' ')
                    return States.F1;
                if (symbol == 'w')
                    return States.G1;
                break;

            case G1:
                if (symbol == 'h')
                    return States.H1;
                break;

            case H1:
                if (symbol == 'i')
                    return States.I1;
                break;

            case I1:
                if (symbol == 'l')
                    return States.K1;
                break;

            case J1:
                if (symbol == 'n')
                    return States.L1;
                break;

            case K1:
                if (symbol == 'e')
                    return States.M1;
                break;

            case L1:
                if (symbol == 'd')
                    return States.M1;
                break;

            case M1:
                if (symbol == ' ')
                    return States.V1;
                break;

            case N1:
                if (Character.isDigit(symbol))
                    return States.Y1;
                break;

            case O1:
                if (Character.isDigit(symbol))
                    return States.A2;
                break;

            case P1:
                if (Character.isDigit(symbol))
                    return States.B2;
                break;

            case Q1:
                if (symbol == '+')
                    return States.N1;
                if (symbol == '-')
                    return States.N1;
                if (Character.isDigit(symbol))
                    return States.Y1;
                break;

            case R1:
                if (Character.isDigit(symbol))
                    return States.G2;
                break;

            case S1:
                if (Character.isDigit(symbol))
                    return States.I2;
                break;

            case T1:
                if (Character.isDigit(symbol))
                    return States.J2;
                break;

            case U1:
                if (Character.isDigit(symbol))
                    return States.G2;
                if (symbol == '+')
                    return States.R1;
                if (symbol == '-')
                    return States.R1;
                break;

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
                break;

            case W1:
                if (symbol == '=')
                    return States.C2;
                if (symbol == ' ')
                    return States.W1;
                if (symbol == '>')
                    return States.D2;
                if (symbol == '<')
                    return States.E2;
                break;

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
                break;

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
                break;

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
                break;

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
                break;

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
                break;

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
                break;

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
                break;

            case F2:
                if (Character.isLetterOrDigit(symbol))
                    return States.F2;
                if (symbol == ' ')
                    return States.H2;
                if (symbol == '\n')
                    return States.O2;
                break;

            case G2:
                if (symbol == '\n')
                    return States.O2;
                if (Character.isDigit(symbol))
                    return States.G2;
                if (symbol == ' ')
                    return States.H2;
                break;

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
                break;

            case I2:
                if (Character.isDigit(symbol))
                    return States.I2;
                if (symbol == 'e')
                    return States.U1;
                if (symbol == '\n')
                    return States.O2;
                if (symbol == ' ')
                    return States.H2;
                break;

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
                break;

            case K2:
                if (Character.isDigit(symbol))
                    return States.U2;
                break;

            case L2:
                if (Character.isDigit(symbol))
                    return States.V2;
                break;

            case M2:
                if (Character.isDigit(symbol))
                    return States.W2;
                break;

            case N2:
                if (Character.isDigit(symbol))
                    return States.U2;
                if (symbol == '+')
                    return States.K2;
                if (symbol == '-')
                    return States.K2;
                break;

            case O2:
                if (symbol == ' ')
                    return States.O2;
                if (Character.isLetter(symbol))
                    return States.Q2;
                break;

            case P2:
                if (symbol == ' ')
                    return States.P2;
                if (symbol == '=')
                    return States.R2;
                break;

            case Q2:
                if (Character.isLetterOrDigit(symbol))
                    return States.Q2;
                if (symbol == ' ')
                    return States.P2;
                if (symbol == '=')
                    return States.R2;
                break;

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
                break;

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
                break;

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
                break;

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
                break;

            case V2:
                if (Character.isDigit(symbol))
                    return States.V2;
                if (symbol == '\n')
                    return States.X2;
                if (symbol == ' ')
                    return States.S2;
                if (symbol == '*')
                    return States.R2;
                if (symbol == '+')
                    return States.R2;
                if (symbol == 'e')
                    return States.N2;
                if (symbol == '/')
                    return States.R2;
                if (symbol == '-')
                    return States.R2;
                break;

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
                break;

            case X2:
                if (symbol == 'l')
                    return States.Y2;
                if (symbol == ' ')
                    return States.X2;
                break;

            case Y2:
                if (symbol == 'o')
                    return States.A3;
                break;

            case A3:
                if (symbol == 'o')
                    return States.B3;
                break;

            case B3:
                if (symbol == 'p')
                    return States.C3;
                break;

            case C3:
                if (symbol == '\n')
                    return States.C3;
                if (symbol == ' ')
                    return States.C3;
                if (symbol == 0)
                    return States.D3;
                break;
        }
        return States.ERROR;
    }

    private enum States {
        START,
        ERROR,
        FINISH,
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
