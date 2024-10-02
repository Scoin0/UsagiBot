package usagibot.osu.memreaders.streamcompanion;

import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.text.Charsets;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;
import usagibot.osu.memreaders.MemoryReaderConnections;
import java.io.IOException;

@Slf4j
public class StreamCompanionReader implements IMemoryReader {

    static Beatmap beatmap;

    @Override
    public Beatmap getSong() {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            StreamCompanionModel model = mapper.readValue(json, StreamCompanionModel.class);
            String beatmapId = String.valueOf(model.mapId);
            return beatmap = UsagiBot.getClient().getBeatmap(beatmapId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getMods() {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            StreamCompanionModel model = mapper.readValue(json, StreamCompanionModel.class);
            String mods = String.valueOf(model.mods);
            if (mods.contains("None")) { // Fix for StreamCompanion
                return "NM";
            }
            return mods.replaceAll("^\"|\"$", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getSkin() {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            StreamCompanionModel model = mapper.readValue(json, StreamCompanionModel.class);
            return String.valueOf(model.skin);
        } catch (IOException e) {
            e.printStackTrace();
            return "Something went wrong. Cannot get skin...";
        }
    }
}