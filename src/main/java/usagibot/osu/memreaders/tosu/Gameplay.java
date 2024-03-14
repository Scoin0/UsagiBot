package usagibot.osu.memreaders.tosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gameplay {

    @JsonProperty("gamemode")
    public int gameMode;
    @JsonProperty("name")
    public String name;
    @JsonProperty("score")
    public long score;
    @JsonProperty("accuracy")
    public float accuracy;
    @JsonProperty("hp")
    public HP hp;
    @JsonProperty("hits")
    public Hits hits;
    @JsonProperty("pp")
    public PP pp;

    public static class HP {

        @JsonProperty("normal")
        public int normal;
        @JsonProperty("smooth")
        public int smooth;

    }

    public static class Hits {

        @JsonProperty("0")
        public int hit0;
        @JsonProperty("50")
        public int hit50;
        @JsonProperty("100")
        public int hit100;
        @JsonProperty("300")
        public int hit300;
        @JsonProperty("geki")
        public int hitGeki;
        @JsonProperty("katu")
        public int hitKatu;
        @JsonProperty("sliderBreaks")
        public int sliderBreaks;
        @JsonProperty("grade")
        public Grade grade;
        @JsonProperty("unstableRate")
        public float unstableRate;
        // Ignore HitErrorArray

        public static class Grade {

            @JsonProperty("current")
            public String current;
            @JsonProperty("maxThisPlay")
            public String maxThisPlay;

        }
    }

    public static class PP {

        @JsonProperty("current")
        public int current;
        @JsonProperty("fc")
        public int fc;
        @JsonProperty("maxThisPlay")
        public int maxThisPlay;

    }

    // Ignore KeyOverlay
    // Ignore LeaderBoard
    // Ignore Slots
    // Ignore IsReplayHidden

}