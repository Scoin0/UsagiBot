package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Gameplay {

    @JsonProperty("gameMode")
    public int gameMode;
    @JsonProperty("name")
    public String name;
    @JsonProperty("score")
    public long score;
    @JsonProperty("accuracy")
    public float accuracy;
    @JsonProperty("combo")
    public Combo combo;
    @JsonProperty("hp")
    public HP hp;
    @JsonProperty("hits")
    public Hits hits;

    public class Combo {

        @JsonProperty("current")
        public int current;
        @JsonProperty("max")
        public int max;

    }

    public class HP {

        @JsonProperty("normal")
        public float normal;
        @JsonProperty("smooth")
        public float smooth;

    }

    public class Hits {

        @JsonProperty("300")
        public int hit300;
        @JsonProperty("100")
        public int hit100;
        @JsonProperty("50")
        public int hit50;
        @JsonProperty("0")
        public int hit0;
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

        public class Grade {

            @JsonProperty("current")
            public String current;
            @JsonProperty("maxThisPlay")
            public String maxThisPlay;

        }

    }

}