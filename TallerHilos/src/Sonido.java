import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sonido {

    private AudioInputStream audioInputStream;
    private Clip clip;

    public Sonido(int opc) {
        String nombreSonido = "";
        switch (opc) {
            case 1:
                nombreSonido = "Sonido/Bell.wav";
                break;
            case 2:
                nombreSonido = "Sonido/Run.wav";
                break;
        }
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        }
    }
    


}
