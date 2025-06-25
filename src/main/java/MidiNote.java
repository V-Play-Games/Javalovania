import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record MidiNote(int command, int note, int velocity, double secPos) {
    public MidiNote(int command, String note, int velocity, double secPos) {
        this(command, noteToNumber(note), velocity, secPos);
    }

    public ShortMessage toMidiMessage(int channel) throws InvalidMidiDataException {
        return new ShortMessage(command, channel, note, velocity);
    }

    public MidiEvent toMidiEvent(int channel, int tickRatio) throws InvalidMidiDataException {
        return new MidiEvent(toMidiMessage(channel), (long) (secPos * tickRatio));
    }

    private static final Pattern NOTE_PATTERN = Pattern.compile("([A-G]#?)(\\d)");

    private static int noteToNumber(String noteName) {
        Matcher matcher = NOTE_PATTERN.matcher(noteName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid note: " + noteName);
        }
        int note = switch (matcher.group(1)) {
            case "C" -> 0;
            case "C#" -> 1;
            case "D" -> 2;
            case "D#" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "F#" -> 6;
            case "G" -> 7;
            case "G#" -> 8;
            case "A" -> 9;
            case "A#" -> 10;
            case "B" -> 11;
            default -> throw new IllegalArgumentException("Invalid note: " + noteName);
        };
        int octave = Integer.parseInt(matcher.group(2));
        return (octave - 1) * 12 + note;
    }
}
