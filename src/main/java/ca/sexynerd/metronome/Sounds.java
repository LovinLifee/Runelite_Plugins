package ca.sexynerd.metronome;

public enum Sounds {

    JINGLE_BELLS("jingle_bells"), HAND_CLAP("hand_clap"), SYMBOL("symbol"), EIGHT_BIT("eight_bit");

    private String fileName;

    Sounds(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return "midi/" + fileName + ".mid";
    }
}
