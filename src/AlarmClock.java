import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

/**
 * @auther abdlatif-nabgha
 **/
public class AlarmClock implements Runnable{
    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner scanner;
    AlarmClock(LocalTime alarmTime, String filePath, Scanner scanner) {
        this.alarmTime = alarmTime;
        this.filePath = filePath;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        // LocalTime now = LocalTime.now();
        while (LocalTime.now().isBefore(alarmTime)){
            try {
                Thread.sleep(1000);

                LocalTime now = LocalTime.now();

                System.out.printf("\r%02d:%02d:%02d",
                                    now.getHour(),
                                    now.getMinute(),
                                    now.getSecond()
                );
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        System.out.println("\n*** ALARM NOISES ***");
        //Toolkit.getDefaultToolkit().beep();
        playSound(filePath);

    }
    private void playSound(String filePath) {
        File audioFile = new File(filePath);
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Press enter to stop the alarm");
            scanner.nextLine();
            clip.stop();
            scanner.close();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio file not supported");
        } catch (IOException e) {
            System.err.println("Error opening file");
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
