package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.databind.ObjectMapper;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;
import usagibot.osu.memreaders.MemoryReaderConnections;
import usagibot.osu.memreaders.gosu.GOsuModel;

import java.io.IOException;

public class GOsuMemoryReader implements IMemoryReader {

    static Beatmap beatmap;

    @Override
    public Beatmap getSong() {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            GOsuModel model = mapper.readValue(json, GOsuModel.class);
            String beatmapId = String.valueOf(model.menu.beatmap.id);
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
            GOsuModel model = mapper.readValue(json, GOsuModel.class);
            String mods = String.valueOf(model.menu.mods.str);
            return mods.replaceAll("^\"|\"$", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}