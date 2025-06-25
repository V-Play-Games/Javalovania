import javax.sound.midi.*;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Soundbank soundfont = MidiSystem.getSoundbank(new File("Sonic_Pocket_Adventure_Soundfont_Redux.sf2")); // Load your soundfont file
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        synthesizer.loadAllInstruments(soundfont);

        Sequence newSeq = new Sequence(Sequence.PPQ, 96, 3);
        MegalovaniaNotes.track1.copyTo(newSeq.getTracks()[0], 96);
        MegalovaniaNotes.track2.copyTo(newSeq.getTracks()[1], 96);
        MegalovaniaNotes.track3.copyTo(newSeq.getTracks()[2], 96);

        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
        sequencer.open();
        sequencer.setSequence(newSeq);
        sequencer.start();
    }
}
