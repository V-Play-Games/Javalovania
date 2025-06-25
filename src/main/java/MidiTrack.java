import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.util.List;

public record MidiTrack(int channel, List<MidiNote> notes) {
    public void copyTo(Sequence sequence) throws InvalidMidiDataException {
        Track track = sequence.createTrack();
        int resolution = sequence.getResolution();
        for (MidiNote note : notes) {
            track.add(note.toNoteOnEvent(channel, resolution));
            track.add(note.toNoteOffEvent(channel, resolution));
        }
    }
}
