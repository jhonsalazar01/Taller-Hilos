import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class RandomFigure implements Runnable {

    private int numeroImg;
    private ArrayList<ImageIcon> images;
    private JLabel label;
    private int imageSize = 90;
    private int lapso = 1;
    private boolean state;
    private ToggleButton toggleButton;


    public RandomFigure(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
        images = new ArrayList<ImageIcon>();
        label = new JLabel();
    }

    public ArrayList<ImageIcon> cargarImagenes() {
        images.add(new ImageIcon("Images/aubergine.png"));
        images.add(new ImageIcon("Images/banana.png"));
        images.add(new ImageIcon("Images/carrots.png"));
        images.add(new ImageIcon("Images/cherries.png"));
        images.add(new ImageIcon("Images/dollar.png"));
        images.add(new ImageIcon("Images/lemon.png"));
        images.add(new ImageIcon("Images/orange.png"));
        images.add(new ImageIcon("Images/peach.png"));
        images.add(new ImageIcon("Images/potato.png"));
        images.add(new ImageIcon("Images/tomato.png"));
        return images;
    }

    @Override
    public void run() {
        ArrayList<ImageIcon> images = cargarImagenes();
        Timer timer = new Timer(lapso, e -> {
            if (toggleButton.state) {
                new Sonido(1);
                try {
                    guardarNumeroImagen();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                ((Timer) e.getSource()).stop();
                state = true;
            }
            if (!state) {

                int rnd = (int) (Math.random() * images.size());
                numeroImg = rnd;
                Image scaledImage = images.get(rnd).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                label.setIcon(scaledIcon);
                if (lapso < 500) {
                    lapso += 10;
                    ((Timer) e.getSource()).setDelay(lapso);
                }

                if (lapso > 500) {
                    new Sonido(1);
                    state = true;
                    try {
                        guardarNumeroImagen();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.setRepeats(true);
        timer.start();
    }


    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public JLabel getLabel() {
        return label;
    }

    public int getNumeroImg() {
        return numeroImg;
    }

    public void setNumeroImg(int numeroImg) {
        this.numeroImg = numeroImg;
    }

    public void guardarNumeroImagen() throws IOException {
        File file = new File("numeros_imagenes.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(this.getNumeroImg()));
        bufferedWriter.newLine();
        bufferedWriter.close();
    }
}



