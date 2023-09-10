import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleButton {

    int posX;
    int posY;
    int width;
    int height;
    boolean state;
    private JPanel panelSeleccion;
    private JToggleButton toggleButton;


    public ToggleButton() {
        this.posX = 2;
        this.posY = 2;
        width = 60;
        height = 20;
        panelSeleccion = new JPanel();
        toggleButton = new JToggleButton();
    }

    public JPanel createComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        toggleButton = new JToggleButton();
        toggleButton.setOpaque(false);
        toggleButton.setBorder(new LineBorder(Color.BLACK));
        toggleButton.setContentAreaFilled(false);
        toggleButton.setBounds(posX - 2, posY - 2, width + 4, height + 4);


        panelSeleccion.setBackground(new Color(61, 133, 198));
        panelSeleccion.setBounds(posX, posY, width - 20, height);


        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    panelSeleccion.setBounds(posX + 40, posY, width - 40, height);
                    panelSeleccion.setBackground(new Color(255, 153, 0));
                    state = true;
                } else {
                    panelSeleccion.setBounds(posX, posY, width - 20, height);
                    panelSeleccion.setBackground(new Color(61, 133, 198));
                }
            }
        });

        panel.add(toggleButton);
        panel.add(panelSeleccion);
        panel.setVisible(true);
        return panel;
    }

    public boolean isState() {
        return state;
    }

    public void setFalse() {
        this.state = false;
        panelSeleccion.setBounds(posX, posY, width - 20, height);
        panelSeleccion.setBackground(new Color(61, 133, 198));
        toggleButton.setSelected(false);

    }
}