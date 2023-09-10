import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {
    private JLabel jlabel;
    private int scene;
    private String ruta;
    private int newWidth;
    private int newHeight;

    public StartWindow() {
        setTitle("Traga-monedas PLUS!!!");
        getContentPane().setBackground(Color.WHITE);
        setSize(460, 380);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        jlabel = new JLabel();
        ruta = "";
        scene = 0;
    }

    public void begin() {
        beginComponents();
    }

    private void beginComponents() {
        switch (scene) {
            case 0:
                ruta = "Images/Inicio.png";
                newWidth = 460;
                newHeight = 300;
                break;
            case 1:
                ruta = "Images/Inicio1.png";
                newWidth = 450;
                newHeight = 335;
                break;
            case 2:
                ruta = "Images/Inicio2.png";
                newWidth = 460;
                newHeight = 280;
                break;
        }
        scene++;

        ImageIcon imageIcon = new ImageIcon(ruta);
        Image image = imageIcon.getImage();

        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        jlabel.setIcon(resizedIcon);
        jlabel.setBounds(0, 0, newWidth, newHeight);
        add(jlabel);

        JButton closeButton = new JButton("Continuar");
        closeButton.setBounds(350, 330, 100, 40); // Posición y tamaño del botón

        closeButton.setFont(new Font("MONOSPACED", Font.BOLD, 15));
        closeButton.setForeground(Color.orange);
        closeButton.setBackground(new Color(23, 159, 12));
        closeButton.setFocusPainted(false);
        Border customBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        closeButton.setBorder(customBorder);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (scene == 3) {
                    dispose();
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.begin();
                    mainWindow.setVisible(true);
                }else {
                    beginComponents();
                }

            }
        });
        add(closeButton);
    }
}
