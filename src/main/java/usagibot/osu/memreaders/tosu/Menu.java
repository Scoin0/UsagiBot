package usagibot.osu.memreaders.tosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Menu {

    // Ignore MainMenu

    @JsonProperty("state")
    public int state;
    @JsonProperty("gameMode")
    public int gameMode;
    @JsonProperty("isChatEnabled")
    public boolean isChatEnabled;
    @JsonProperty("bm")
    public BM beatmap;
    @JsonProperty("mods")
    public Mods mods;
    @JsonProperty("pp")
    public PP pp;

    public static class BM {

        @JsonProperty("time")
        public Time time;
        @JsonProperty("id")
        public int id;
        @JsonProperty("set")
        public int set;
        // Ignore HashCode
        @JsonProperty("rankedStatus")
        public int rankedStatus;
        @JsonProperty("metadata")
        public MetaData metaData;
        @JsonProperty("stats")
        public Stats stats;

        public static class Time {

            @JsonProperty("firstObj")
            public int firstObject;
            @JsonProperty("current")
            public int current;
            @JsonProperty("full")
            public int full;
            @JsonProperty("mp3")
            public int mp3;

        }

        public static class MetaData {

            @JsonProperty("artist")
            public String artist;
            @JsonProperty("artistOriginal")
            public String artistOriginal;
            @JsonProperty("title")
            public String title;
            @JsonProperty("titleOriginal")
            public String titleOriginal;
            @JsonProperty("mapper")
            public String mapper;
            @JsonProperty("difficulty")
            public String difficulty;

        }

        public static class Stats {

            @JsonProperty("AR")
            public float ar;
            @JsonProperty("CS")
            public float cs;
            @JsonProperty("OD")
            public float od;
            @JsonProperty("HP")
            public float hp;
            @JsonProperty("SR")
            public float sr;
            @JsonProperty("BPM")
            public BPM BPM;
            @JsonProperty("circles")
            public int circles;
            @JsonProperty("sliders")
            public int sliders;
            @JsonProperty("spinners")
            public int spinners;
            @JsonProperty("holds")
            public int holds;
            @JsonProperty("maxCombo")
            public int maxCombo;
            @JsonProperty("fullSR")
            public float fullSR;
            @JsonProperty("memoryAR")
            public float memoryAR;
            @JsonProperty("memoryCS")
            public float memoryCS;
            @JsonProperty("memoryOD")
            public float memoryOD;
            @JsonProperty("memoryHP")
            public float memoryHP;

            public static class BPM {

                @JsonProperty("common")
                public int common;
                @JsonProperty("min")
                public int min;
                @JsonProperty("max")
                public int max;

            }

            // Ignore Path
        }

    }

    public static class Mods {

        @JsonProperty("num")
        public long num;
        @JsonProperty("str")
        public String str;

    }

}