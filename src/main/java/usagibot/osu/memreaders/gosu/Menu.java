package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Menu {

    @JsonProperty("state")
    public int state;
    // Ignore SkinFolder
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class BM {

        @JsonProperty("time")
        public Time time;
        @JsonProperty("id")
        public int id;
        @JsonProperty("set")
        public int beatmapSetId;
        // Ignore HashCode
        @JsonProperty("rankedStatus")
        public int rankedStatus;
        @JsonProperty("metadata")
        public MetaData metaData;
        @JsonProperty("stats")
        public Stats stats;
        // Ignore Path

        public class Time {

            @JsonProperty("firstObj")
            public int firstObject;
            @JsonProperty("current")
            public int current;
            @JsonProperty("full")
            public int full;
            @JsonProperty("mp3")
            public int mp3;

        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public class MetaData {

            @JsonProperty("artist")
            public String artist;
            @JsonProperty("title")
            public String title;
            @JsonProperty("mapper")
            public String mapper;
            @JsonProperty("difficulty")
            public String difficulty;

        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Stats {

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
            public BPM bpm;
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

            public class BPM {

                @JsonProperty("min")
                public float min;
                @JsonProperty("max")
                public float max;

            }

        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Mods {

        @JsonProperty("num")
        public long num;
        @JsonProperty("str")
        public String str;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PP {

        @JsonProperty("100")
        public int perfect;
        @JsonProperty("99")
        public int ninetyNine;
        @JsonProperty("98")
        public int ninetyEight;
        @JsonProperty("97")
        public int ninetySeven;
        @JsonProperty("96")
        public int ninetySix;
        @JsonProperty("95")
        public int ninetyFive;
        // Ignore Strains

    }

}