package usagibot.osu.memreaders.rosu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.Mods;
import usagibot.osu.memreaders.IMemoryReader;
import usagibot.osu.memreaders.MemoryReaderConnections;

import java.io.IOException;

@Slf4j
public class ROsuReader implements IMemoryReader {

    static Beatmap beatmap;

    @Override
    public Beatmap getSong() {
        try {
            String json = MemoryReaderConnections.fetchJsonDataWebSocket(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            ROsuModel model = mapper.readValue(json, ROsuModel.class);
            String beatmapId = String.valueOf(model.beatmap.mapId);
            return beatmap = UsagiBot.getClient().getBeatmap(beatmapId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getMods() {
        try {
            String json = MemoryReaderConnections.fetchJsonDataWebSocket(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            ROsuModel model = mapper.readValue(json, ROsuModel.class);
            String mods = String.valueOf(model.mods);
            if (mods.contains("0")) { // Fix for rosu-memory
                return "NM";
            } else {
                mods = (Mods.toShortNamesContinuous(Mods.getMods(model.mods)));
            }
            return mods.replaceAll("^\"|\"$", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}