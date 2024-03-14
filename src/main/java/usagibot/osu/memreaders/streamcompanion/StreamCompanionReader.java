package usagibot.osu.memreaders.streamcompanion;

import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;

public class StreamCompanionReader implements IMemoryReader {

    static Beatmap beatmap;

    @Override
    public Beatmap getSong() {
        return null;
    }

    @Override
    public String getMods() {
        return null;
    }
}
