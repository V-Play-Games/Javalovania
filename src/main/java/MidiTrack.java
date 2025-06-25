import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Track;
import java.util.List;

public record MidiTrack(int channel, List<MidiNote> notes) {
    public void copyTo(Track midiTrack, int tickRatio) throws InvalidMidiDataException {
        for (MidiNote note : notes) {
            midiTrack.add(note.toMidiEvent(channel, tickRatio));
        }
    }
}
