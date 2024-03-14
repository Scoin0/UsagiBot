package usagibot.osu.memreaders.rosu;

import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;

public class ROsuReader implements IMemoryReader {

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
