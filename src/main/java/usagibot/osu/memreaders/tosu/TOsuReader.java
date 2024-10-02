package usagibot.osu.memreaders.tosu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.ApiStatus;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.IMemoryReader;
import usagibot.osu.memreaders.MemoryReaderConnections;
import usagibot.osu.memreaders.gosu.GOsuModel;

import java.io.IOException;

public class TOsuReader implements IMemoryReader {

    static Beatmap beatmap;

    @Override
    public Beatmap getSong() {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            TOsuModel model = mapper.readValue(json, TOsuModel.class);
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
            TOsuModel model = mapper.readValue(json, TOsuModel.class);
            String mods = String.valueOf(model.menu.mods.str);
            if (mods == "") { // Fix for tosu
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
            TOsuModel model = mapper.readValue(json, TOsuModel.class);
            return String.valueOf(model.settings.folders.skin);
        } catch (IOException e) {
            return "Something went wrong. Cannot get skin...";
        }
    }

    @Override
    public String getPP(int percentage) {
        try {
            String json = MemoryReaderConnections.fetchJsonData(MemoryReaderConnections.webHookPath);
            ObjectMapper mapper = new ObjectMapper();
            TOsuModel model = mapper.readValue(json, TOsuModel.class);
            float a;
            if (percentage == 100) {
                a = model.menu.pp.Perfect;
            } else if (percentage == 99) {
                a = model.menu.pp.ninetyNine;
            } else if (percentage == 98) {
                a = model.menu.pp.ninetyEight;
            } else if (percentage == 97) {
                a = model.menu.pp.ninetySeven;
            } else if (percentage == 96) {
                a = model.menu.pp.ninetySix;
            } else if (percentage == 95) {
                a = model.menu.pp.ninetyFive;
            } else {
                return null;
            }
            return String.valueOf(a);
        } catch (IOException e) {
            return null;
        }
    }
}