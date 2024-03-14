package usagibot.osu.memreaders.tosu;

import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;

public class TOsuReader implements IMemoryReader {

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
