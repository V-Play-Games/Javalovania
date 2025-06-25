import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record MidiNote(int note, int velocity, double secPos, double duration) {
    public static final int NOTE_OFF = 0x80;  // 128
    public static final int NOTE_ON = 0x90;  // 144

    public MidiNote(String note, int velocity, double secPos, double duration) {
        this(noteToNumber(note), velocity, secPos, duration);
    }

    public MidiEvent toNoteOnEvent(int channel, int tickRatio) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(NOTE_ON, channel, note, velocity), (long) (secPos * tickRatio));
    }

    public MidiEvent toNoteOffEvent(int channel, int tickRatio) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(NOTE_OFF, channel, note, velocity), (long) ((secPos + duration) * tickRatio));
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

    public static String getNoteName(int noteNum) {
        int octave = (noteNum / 12) + 1;
        String note = "C C#D D#E F F#G G#A A#B ".substring((noteNum % 12) * 2).substring(0, 2).trim();

        return "%3s".formatted(note + octave);
    }

    public String toString() {
        return "Note %3s, Velocity %02d, at %02.02f s%n for %02.02fs"
                .formatted(getNoteName(note), velocity, secPos, duration);
    }
}
