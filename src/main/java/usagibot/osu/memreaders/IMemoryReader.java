package usagibot.osu.memreaders;

import usagibot.osu.api.Beatmap;

import java.io.IOException;

public interface IMemoryReader {
    Beatmap getSong();
    String getMods();
    String getSkin();
}