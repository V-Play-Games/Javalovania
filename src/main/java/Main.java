import javax.sound.midi.*;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Soundbank soundfont = MidiSystem.getSoundbank(new File("Sonic_Pocket_Adventure_Soundfont_Redux.sf2"));
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        synthesizer.loadAllInstruments(soundfont);

        Sequence newSeq = new Sequence(Sequence.PPQ, 96);
        MegalovaniaNotes.drums.copyTo(newSeq);
        MegalovaniaNotes.melody.copyTo(newSeq);
        MegalovaniaNotes.bass.copyTo(newSeq);

        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
        sequencer.open();
        sequencer.setSequence(newSeq);
        sequencer.start();
        Thread.sleep(50000);
        System.exit(0);
    }
}
