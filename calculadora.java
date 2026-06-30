import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class calculadora {

    int ancho = 360;
    int alto = 540;

    Color grisClaro = new Color(212, 212, 210);
    Color grisOscuro = new Color(80, 80, 80);
    Color negro = new Color(28, 28, 28);
    Color naranja = new Color(255, 149, 0);

    String[] botones = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    String[] operadores = {"÷", "×", "-", "+", "="};
    String[] especiales = {"AC", "+/-", "%"};

    JFrame ventana = new JFrame("Calculadora");
    JLabel pantalla = new JLabel();

    JPanel panelPantalla = new JPanel();
    JPanel panelBotones = new JPanel();

    String primerNumero = "0";
    String segundoNumero = null;
    String operador = null;

    public calculadora() {

        ventana.setSize(ancho, alto);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        pantalla.setBackground(negro);
        pantalla.setForeground(Color.WHITE);
        pantalla.setFont(new Font("Arial", Font.PLAIN, 80));
        pantalla.setHorizontalAlignment(JLabel.RIGHT);
        pantalla.setText("0");
        pantalla.setOpaque(true);

        panelPantalla.setLayout(new BorderLayout());
        panelPantalla.add(pantalla);
        ventana.add(panelPantalla, BorderLayout.NORTH);

        panelBotones.setLayout(new GridLayout(5, 4));
        panelBotones.setBackground(negro);
        ventana.add(panelBotones);

        for (int i = 0; i < botones.length; i++) {

            JButton boton = new JButton(botones[i]);

            boton.setFont(new Font("Arial", Font.PLAIN, 30));
            boton.setFocusable(false);
            boton.setBorder(new LineBorder(negro));

            if (Arrays.asList(especiales).contains(botones[i])) {
                boton.setBackground(grisClaro);
                boton.setForeground(negro);
            } else if (Arrays.asList(operadores).contains(botones[i])) {
                boton.setBackground(naranja);
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(grisOscuro);
                boton.setForeground(Color.WHITE);
            }

            panelBotones.add(boton);

            boton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    JButton botonPresionado = (JButton) e.getSource();
                    String valor = botonPresionado.getText();

                    if (Arrays.asList(operadores).contains(valor)) {

                        if (valor.equals("=")) {

                            if (operador != null) {

                                segundoNumero = pantalla.getText();

                                double num1 = Double.parseDouble(primerNumero);
                                double num2 = Double.parseDouble(segundoNumero);

                                switch (operador) {

                                    case "+":
                                        pantalla.setText(quitarDecimal(num1 + num2));
                                        break;

                                    case "-":
                                        pantalla.setText(quitarDecimal(num1 - num2));
                                        break;

                                    case "×":
                                        pantalla.setText(quitarDecimal(num1 * num2));
                                        break;

                                    case "÷":
                                        pantalla.setText(quitarDecimal(num1 / num2));
                                        break;
                                }

                                limpiarTodo();
                            }

                        } else {

                            if (operador == null) {
                                primerNumero = pantalla.getText();
                                pantalla.setText("0");
                            }

                            operador = valor;
                        }

                    } else if (Arrays.asList(especiales).contains(valor)) {

                        if (valor.equals("AC")) {

                            limpiarTodo();
                            pantalla.setText("0");

                        } else if (valor.equals("+/-")) {

                            double numero = Double.parseDouble(pantalla.getText());
                            numero *= -1;
                            pantalla.setText(quitarDecimal(numero));

                        } else if (valor.equals("%")) {

                            double numero = Double.parseDouble(pantalla.getText());
                            numero /= 100;
                            pantalla.setText(quitarDecimal(numero));
                        }

                    } else {

                        // Si es un punto decimal
                        if (valor.equals(".")) {

                            if (!pantalla.getText().contains(".")) {
                                pantalla.setText(pantalla.getText() + ".");
                            }

                        }
                        // Si es un número
                        else {

                            if (pantalla.getText().equals("0")) {
                                pantalla.setText(valor);
                            } else {
                                pantalla.setText(pantalla.getText() + valor);
                            }
                        }
                    }
                }
            });
        }

        ventana.setVisible(true);
    }

    public void limpiarTodo() {
        primerNumero = "0";
        segundoNumero = null;
        operador = null;
    }

    public String quitarDecimal(double numero) {

        if (numero % 1 == 0) {
            return String.valueOf((int) numero);
        }

        return String.valueOf(numero);
    }

    public static void main(String[] args) {
        new calculadora();
    }
}