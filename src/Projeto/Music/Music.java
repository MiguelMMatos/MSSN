package Projeto.Music;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private String path = System.getProperty("user.dir") + "/src/Projeto/sound/music.wav";
    private Clip audioClip;
    private FloatControl volume;
    private String path2 = System.getProperty("user.dir") + "/src/Projeto/sound/waterDrop.wav";
    private Clip audioClip2;
    private FloatControl volume2;

    public Music(){
        PlayMusicBackground();
    }

    private void PlayMusicBackground(){
        playAudio(createAudio(),audioClip,true,0.8f);
    }
    public void PlayMusicClick(){
        playAudio(createAudio2(),audioClip2,false,0.4f);
    }
    private File createAudio(){
        return new File(path);
    }
    private File createAudio2(){
        return new File(path2);
    }

    private void volumeRange(float desiredVolume){
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * desiredVolume) + volume.getMinimum();
        volume.setValue(gain);
    }
    private void playAudio(File audioFile,Clip audioClipIn,boolean loop, float volumeRange){
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClipIn = (Clip) AudioSystem.getLine(info);
            audioClipIn.open(audioStream);
            if(loop) {
                audioClipIn.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                audioClipIn.start();
            }
            //Adjust Volume
            volume = (FloatControl) audioClipIn.getControl(FloatControl.Type.MASTER_GAIN);
            volumeRange(volumeRange);
            //volumeRange(0.5f);
            audioClipIn.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
