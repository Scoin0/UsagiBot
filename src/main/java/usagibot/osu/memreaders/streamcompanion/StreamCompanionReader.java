package usagibot.osu.memreaders.streamcompanion;

import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.text.Charsets;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;
import usagibot.osu.memreaders.MemoryReaderConnections;
import usagibot.osu.memreaders.tosu.TOsuModel;

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

    public String getPP(int percentage) {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            StreamCompanionModel model = mapper.readValue(json, StreamCompanionModel.class);
            float a;
            if (percentage == 100) {
                a = model.MOsuSS;
            } else if (percentage == 99) {
                a = model.MOsuNinetyNine;
            } else if (percentage == 98) {
                a = model.MOsuNinetyEight;
            } else if (percentage == 97) {
                a = model.MOsuNinetySeven;
            } else if (percentage == 96) {
                a = model.MOsuNinetySix;
            } else if (percentage == 95) {
                a = model.MOsuNinetyFive;
            } else {
                return null;
            }
            return String.valueOf(a);
        } catch (IOException e) {
            return null;
        }
    }
}