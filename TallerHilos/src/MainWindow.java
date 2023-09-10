import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame {


    private JPanel panelHora;
    private JPanel panelCreditosMaquina;
    private JLabel jLabelCreditosMaquina;
    private JPanel panelSaldo;
    private JPanel panelApuesta;
    private JPanel panelDetener;
    private JButton jButtonSaldo;
    private JButton jButtonApuesta;
    private JButton jButtonJugar;
    private double valorApuestas = 350;
    private double saldo = 20000;
    private double creditosMaquina = 350000;
    private ToggleButton toggleButton;
    private ToggleButton toggleButtonOne;
    private ToggleButton toggleButtonTwo;
    private JPanel jPanelTFigure;
    private JPanel jPanelTFigureOne;
    private JPanel jPanelTFigureTwo;
    private JLabel valorApuesta;
    private JLabel valorSaldo;


    public MainWindow() {
        setTitle("Traga-monedas PLUS!!!");
        setSize(460, 380);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void begin() {
        beginComponents();
        addComponents();
    }

    private void beginComponents() {
        panelHora = new JPanel();
        panelCreditosMaquina = new JPanel();
        jLabelCreditosMaquina = new JLabel();
        panelSaldo = new JPanel();
        panelApuesta = new JPanel();
        jButtonSaldo = new JButton();
        jButtonApuesta = new JButton();
        jButtonJugar = new JButton();
        panelDetener = new JPanel();
        toggleButton = new ToggleButton();
        toggleButtonOne = new ToggleButton();
        toggleButtonTwo = new ToggleButton();
        jPanelTFigure = new JPanel();
        jPanelTFigureOne = new JPanel();
        jPanelTFigureTwo = new JPanel();
        valorApuesta = new JLabel();
        valorSaldo = new JLabel();
    }

    private void addComponents() {
        addDefaultImage();
        addHora();
        addCreditoMaquina();
        addSaldo();
        addDetener();
        addApuesta();
        addJugar();
        addToggleButton();
    }

    private JLabel ramdonImage() {
        RandomFigure rr = new RandomFigure(null);
        int rnd = (int) (Math.random() * rr.cargarImagenes().size());
        Image scaledImages = rr.cargarImagenes().get(rnd).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImages);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(scaledIcon);
        return jLabel;
    }

    private void addDefaultImage() {

        jPanelTFigure.add(ramdonImage());
        jPanelTFigure.setBounds(45, 90, 100, 120);
        add(jPanelTFigure);


        jPanelTFigureOne.add(ramdonImage());
        jPanelTFigureOne.setBounds(195, 90, 100, 120);
        add(jPanelTFigureOne);

        jPanelTFigureTwo.add(ramdonImage());
        jPanelTFigureTwo.setBounds(345, 90, 100, 120);
        add(jPanelTFigureTwo);

    }

    private void addToggleButton() {

        JPanel jPanel = toggleButton.createComponents();
        jPanel.setBounds(50, 240, 65, 25);
        add(jPanel);


        JPanel jPanelOne = toggleButtonOne.createComponents();
        jPanelOne.setBounds(200, 240, 65, 25);
        add(jPanelOne);


        JPanel jPanelTwo = toggleButtonTwo.createComponents();
        jPanelTwo.setBounds(350, 240, 65, 25);
        add(jPanelTwo);
    }

    private void addDetener() {
        panelDetener.setOpaque(false);
        panelDetener.setBounds(20, 220, 420, 60);
        panelDetener.setLayout(new BorderLayout());
        Border border = BorderFactory.createTitledBorder("Detener");
        panelDetener.setBorder(border);
        add(panelDetener);
    }

    private void iniciarAnimacion() throws InterruptedException {
        toggleButton.setFalse();
        toggleButtonOne.setFalse();
        toggleButtonTwo.setFalse();

        RandomFigure randomFigure = new RandomFigure(toggleButton);
        RandomFigure randomFigureOne = new RandomFigure(toggleButtonOne);
        RandomFigure randomFigureTwo = new RandomFigure(toggleButtonTwo);

        Thread hilo = new Thread(randomFigure);
        Thread hiloOne = new Thread(randomFigureOne);
        Thread hiloTwo = new Thread(randomFigureTwo);

        jPanelTFigure.removeAll();
        jPanelTFigure.add(randomFigure.getLabel());
        jPanelTFigure.setBounds(45, 90, 100, 120);
        add(jPanelTFigure);

        jPanelTFigureOne.removeAll();
        jPanelTFigureOne.add(randomFigureOne.getLabel());
        jPanelTFigureOne.setBounds(195, 90, 100, 120);
        add(jPanelTFigureOne);

        jPanelTFigureTwo.removeAll();
        jPanelTFigureTwo.add(randomFigureTwo.getLabel());
        jPanelTFigureTwo.setBounds(345, 90, 100, 120);
        add(jPanelTFigureTwo);

        hilo.start();
        hiloOne.start();
        hiloTwo.start();

        hilo.join();
        hiloOne.join();
        hiloTwo.join();

        Timer timerDos = new Timer(1000, e -> {
            if (existeArchivoCSV("numeros_imagenes.csv") && leerNumerosDesdeArchivo("numeros_imagenes.csv").size() == 3) {
                ArrayList<Integer> num = new ArrayList<Integer>();
                num.addAll(leerNumerosDesdeArchivo("numeros_imagenes.csv"));
                double valorGanado;
                if (tieneMaximoBonus(num)) {
                    valorGanado = valorApuestas * 10;
                    saldo = saldo + valorGanado;
                    creditosMaquina = creditosMaquina - valorGanado;
                    JOptionPane.showMessageDialog(panelDetener, "¡Felicidades por alcanzar el máximo bono! \n                 GANO $ "+formatoDouble(valorGanado)+"\n","Resumen de juego", JOptionPane.INFORMATION_MESSAGE);


                } else if (tieneTresIguales(num)) {
                    valorGanado = valorApuestas * 3;
                    saldo = saldo + valorGanado;
                    creditosMaquina = creditosMaquina - valorGanado;
                    JOptionPane.showMessageDialog(panelDetener, " ¡Tú puedes lograrlo!\n\n                GANO $ "+formatoDouble(valorGanado)+"\n","Resumen de juego", JOptionPane.INFORMATION_MESSAGE);


                } else if (tieneDosIguales(num)) {
                    valorGanado = valorApuestas * 2;
                    saldo = saldo + valorGanado;
                    creditosMaquina = creditosMaquina - valorGanado;
                    JOptionPane.showMessageDialog(panelDetener, "Tu triunfo está más cerca de lo que crees!!\n\n          GANO $ "+formatoDouble(valorGanado)+"\n","Resumen de juego", JOptionPane.INFORMATION_MESSAGE);



                } else {
                    valorGanado = valorApuestas;
                    saldo = saldo - valorGanado;
                    creditosMaquina = creditosMaquina + valorGanado;
                    JOptionPane.showMessageDialog(panelDetener, "¡La suerte puede cambiar en cualquier momento!\n\n          PERDIO $ -"+formatoDouble(valorGanado)+"\n","Resumen de juego", JOptionPane.INFORMATION_MESSAGE);


                }
                jLabelCreditosMaquina.setText("$ " + formatoDouble(creditosMaquina));
                valorSaldo.setText("$ " + formatoDouble(saldo));

                ((Timer) e.getSource()).stop();
                jButtonJugar.setEnabled(true);
                jButtonApuesta.setEnabled(true);
                jButtonSaldo.setEnabled(true);
            }
        });
        timerDos.start();
    }

    private String formatoDouble(double saldo) {
        DecimalFormat formato = new DecimalFormat("#,###.###");
        String valorFormateado = formato.format(saldo);
        return valorFormateado;
    }

    private static boolean tieneTresIguales(ArrayList<Integer> num) {
        if (num.size() != 3) {
            return false;
        }
        return num.get(0) == num.get(1) && num.get(1) == num.get(2);
    }

    private boolean tieneDosIguales(ArrayList<Integer> num) {
        if (num.size() != 3) {
            return false;
        }

        return num.get(0) == num.get(1) || num.get(0) == num.get(2) || num.get(1) == num.get(2);
    }

    private boolean tieneMaximoBonus(ArrayList<Integer> num) {
        if (num.size() != 3) {
            return false;
        }
        return num.get(0) == 4 && num.get(1) == 4 && num.get(2) == 4;
    }


    private void addJugar() {
        jButtonJugar.setText("Jugar");
        jButtonJugar.setFont(new Font("MONOSPACED", Font.BOLD, 18));
        jButtonJugar.setBounds(345, 310, 80, 40);
        jButtonJugar.setForeground(Color.orange);
        jButtonJugar.setBackground(new Color(23, 159, 12));
        jButtonJugar.setFocusPainted(false);
        Border customBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        jButtonJugar.setBorder(customBorder);
        add(jButtonJugar);
        jButtonJugar.setVisible(true);
        jButtonJugar.addActionListener(e -> {
            deleteArchivo();
            jButtonJugar.setEnabled(false);
            jButtonApuesta.setEnabled(false);
            jButtonSaldo.setEnabled(false);

            double diferencia = saldo - valorApuestas;
            if (diferencia >= 0) {

                new Sonido(2);
                try {
                    iniciarAnimacion();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            } else {

                JOptionPane.showMessageDialog(null, "No hay saldo suficiente");
                jButtonSaldo.setEnabled(true);
                jButtonJugar.setEnabled(true);
                jButtonApuesta.setEnabled(true);
            }


        });
    }


    private void deleteArchivo() {
        File file = new File("numeros_imagenes.csv");
        if (file.exists()) {
            file.delete();
        }
    }

    private boolean existeArchivoCSV(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        return archivo.exists() && archivo.isFile();
    }


    private void addApuesta() {
        panelApuesta.setOpaque(false);
        panelApuesta.setBounds(180, 300, 140, 60);
        panelApuesta.setLayout(new BorderLayout());
        Border borderApuesta = BorderFactory.createTitledBorder("Apuesta");
        panelApuesta.setBorder(borderApuesta);
        valorApuesta.setText("$ " + formatoDouble(valorApuestas));
        panelApuesta.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelApuesta.add(valorApuesta);
        add(panelApuesta);

        jButtonApuesta = new JButton("Cambiar Apuesta");
        jButtonApuesta.setFont(new Font("MONOSPACED", Font.BOLD, 10));
        jButtonApuesta.setBounds(180, 340, 136, 20);

        jButtonApuesta.setForeground(new Color(23, 159, 12));
        jButtonApuesta.setBackground(Color.orange);
        jButtonApuesta.setFocusPainted(false);
        Border customBorderApuesta = BorderFactory.createLineBorder(Color.BLACK, 2);
        jButtonApuesta.setBorder(customBorderApuesta);
        add(jButtonApuesta);
        jButtonApuesta.addActionListener(e -> {
            String val = cambiarValores("Digite el valor de la apuesta", "Apuesta");
            double diferencia;
            if (val != null) {
                diferencia = saldo - Double.parseDouble(val);
                if (diferencia >= 0) {
                    valorApuestas = Double.parseDouble(val);
                    valorApuesta.setText("$ " + formatoDouble(valorApuestas));
                } else {
                    JOptionPane.showMessageDialog(null, "La apuesta no puede ser mayor que el saldo");
                }
            }


        });
    }

    private String cambiarValores(String message, String title) {
        boolean entradaValida = false;
        String input = null;
        while (!entradaValida) {
            input = JOptionPane.showInputDialog(null, message, title, 2);
            if (input == null) {
                break;
            }
            if (input != null && input.matches("^\\d+$")) {
                entradaValida = true;
            } else {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Ingresar solo números enteros positivos.");
            }
        }
        return input;

    }


    private void addSaldo() {
        panelSaldo.setOpaque(false);
        panelSaldo.setBounds(20, 300, 140, 60);
        panelSaldo.setLayout(new BorderLayout());
        Border borderSaldo = BorderFactory.createTitledBorder("Saldo");
        panelSaldo.setBorder(borderSaldo);

        valorSaldo.setText("$ " + formatoDouble(saldo));
        panelSaldo.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSaldo.add(valorSaldo);
        add(panelSaldo);
        jButtonSaldo = new JButton("Cambiar Saldo");
        jButtonSaldo.setFont(new Font("MONOSPACED", Font.BOLD, 10));
        jButtonSaldo.setBounds(20, 340, 136, 20);
        jButtonSaldo.setForeground(new Color(23, 159, 12));
        jButtonSaldo.setBackground(Color.orange);
        jButtonSaldo.setFocusPainted(false);
        Border customBorderSaldo = BorderFactory.createLineBorder(Color.BLACK, 2);
        jButtonSaldo.setBorder(customBorderSaldo);
        jButtonSaldo.setVisible(true);

        jButtonSaldo.addActionListener(e -> {
            String val = cambiarValores("Digite el nuevo saldo", "Saldo");
            double valor = Double.parseDouble(val);
            if (val != null && valor >= valorApuestas) {
                saldo = valor;
                valorSaldo.setText("$ " + formatoDouble(valor));
            } else {
                JOptionPane.showMessageDialog(null, "El saldo no puede ser menor que la apuesta");

            }

        });
        add(jButtonSaldo);
    }

    private void addCreditoMaquina() {
        panelCreditosMaquina.setOpaque(false);
        panelCreditosMaquina.setBounds(240, 20, 200, 60);
        panelCreditosMaquina.setLayout(new BorderLayout());
        Border borderCreditosMaquina = BorderFactory.createTitledBorder("Creditos de Maquina");
        panelCreditosMaquina.setBorder(borderCreditosMaquina);
        add(panelCreditosMaquina);


        jLabelCreditosMaquina.setText("$ " + formatoDouble(creditosMaquina));
        JLabel labelMoneda = new JLabel();
        labelMoneda.setIcon(new ImageIcon(new ImageIcon("Images/dollar.png").getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH)));
        JPanel panelMoneda = new JPanel();
        panelMoneda.setBounds(280, 39, 120, 30);
        panelMoneda.setLayout(new FlowLayout());
        panelMoneda.add(labelMoneda);
        panelMoneda.add(jLabelCreditosMaquina);
        add(panelMoneda);
        setVisible(true);


    }

    private void addHora() {
        JLabel hour = new JLabel();
        panelHora.setBounds(20, 20, 200, 60);
        panelHora.setLayout(new BorderLayout());
        Border borderHora = BorderFactory.createTitledBorder("Tiempo de juego");
        panelHora.setBorder(borderHora);
        panelHora.setLayout(null);
        hour.setBounds(65, 20, 80, 30);
        panelHora.add(hour);
        add(panelHora);
        new Thread(() ->
        {
            int segundo = 0;
            int minuto = 0;
            int hora = 0;
            while (true) {
                hour.setText(String.format("%02d:%02d:%02d", hora, minuto, segundo));
                segundo++;
                if (segundo == 60) {
                    segundo = 0;
                    minuto++;
                    if (minuto == 60) {
                        minuto = 0;
                        hora++;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public ArrayList<Integer> leerNumerosDesdeArchivo(String nombreArchivo) {
        ArrayList<Integer> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    int numero = Integer.parseInt(linea.trim());
                    numeros.add(numero);
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;


    }
}


