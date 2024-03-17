package usagibot.osu.memreaders.streamcompanion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Follows the JSON schema (As of March 10th, 2024)
 * URL:         https://piotrekol.github.io/StreamCompanion/development/SC/api.html
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamCompanionModel {

    // Ignore BackgroundImageLocation
    // Ignore BakcgroundImageFileName
    @JsonProperty("osu_SSPP")
    public float OsuSS;
    @JsonProperty("osu_99_9PP")
    public float OsuNinetyNinePointNine;
    @JsonProperty("osu_99PP")
    public float OsuNinetyNine;
    @JsonProperty("osu_98PP")
    public float OsuNinetyEight;
    @JsonProperty("osu_97PP")
    public float OsuNinetySeven;
    @JsonProperty("osu_96PP")
    public float OsuNinetySix;
    @JsonProperty("osu_95PP")
    public float OsuNinetyFive;
    @JsonProperty("osu_90PP")
    public float OsuNinety;
    @JsonProperty("osu_mSSPP")
    public float MOsuSS;
    @JsonProperty("osu_m99_9PP")
    public float MOsuNinetyNinePointNine;
    @JsonProperty("osu_m99PP")
    public float MOsuNinetyNine;
    @JsonProperty("osu_m98PP")
    public float MOsuNinetyEight;
    @JsonProperty("osu_m97PP")
    public float MOsuNinetySeven;
    @JsonProperty("osu_m96PP")
    public float MOsuNinetySix;
    @JsonProperty("osu_m95PP")
    public float MOsuNinetyFive;
    @JsonProperty("osu_m90PP")
    public float MOsuNinety;
    // Ignore Mania
    // Ignore OsuIsRunning
    // Ignore Map Strains
    // Ignore Skin
    // Ignore Skin Path
    @JsonProperty("firstHitObjectTime")
    public int firstHitObjectTime;
    // Ignore Map Breaks
    // Ignore Map Timing Points
    @JsonProperty("rankedStatus")
    public int rankedStatus;
    @JsonProperty("status")
    public int status;
    @JsonProperty("rawStatus")
    public int rawStatus;
    @JsonProperty("mapid")
    public int mapId;
    @JsonProperty("mapsetId")
    public int mapsetId;
    @JsonProperty("username")
    public String username;
    @JsonProperty("acc")
    public float accuracy;
    @JsonProperty("katsu")
    public int hitKatsu;
    @JsonProperty("geki")
    public int hitGeki;
    @JsonProperty("c300")
    public int hit300;
    @JsonProperty("c100")
    public int hit100;
    @JsonProperty("c50")
    public int hit50;
    @JsonProperty("miss")
    public int hitMiss;
    @JsonProperty("grade")
    public int grade;
    // Ignore Map Position
    // Ignore Time
    // Ignore Time Left
    @JsonProperty("combo")
    public int combo;
    @JsonProperty("comboLeft")
    public int comboLeft;
    @JsonProperty("score")
    public long score;
    @JsonProperty("currentMaxCombo")
    public int currentMaxCombo;
    @JsonProperty("playerHp")
    public float playerHp;
    @JsonProperty("ppIfMapEndsNow")
    public float ppIfMapEndsNow;
    // Ignore Aim PP If Map Ends Now
    // Ignore Speed PP If Map Ends Now
    // Ignore Accuracy PP If Map Ends Now
    // Ignore Strain PP If Map Ends Now
    // Ignore PP If RestFced
    // Ignore No Choke
    @JsonProperty("simulatedPp")
    public float simulatedPP;
    @JsonProperty("unstableRate")
    public float unstableRate;
    @JsonProperty("convertedUnstableRate")
    public float convertedUnstableRate;
    // Ignore Hit Errors
    // Ignore Local Time ISO
    // Ignore Local Time
    @JsonProperty("sliderBreaks")
    public int sliderBreaks;
    // Ignore Live Star Rating
    // Ignore Is Break Time
    @JsonProperty("currentBpm")
    public float currentBPM;
    // Ignore Leader Board Players
    // Ignore Leader Board Main Player
    // Ignore Key Overlay
    // Kill me
    // Ignore In-Game Interface Is Enabled
    // Ignore Song Selection Ranking Type
    // Ignore Song Selection Total Scores
    // Ignore Song Selection Scores
    // Ignore Song Selection Main Player Score
    @JsonProperty("plays")
    public int plays;
    // Ignore Retries
    // Ignore Osu File Location
    @JsonProperty("titleRoman")
    public String titleRoman;
    @JsonProperty("artistRoman")
    public String aristRoman;
    @JsonProperty("titleUnicode")
    public String titleUnicode;
    @JsonProperty("artistUnicode")
    public String aristUnicode;
    @JsonProperty("mapArtistTitle")
    public String mapArtistTitle;
    @JsonProperty("mapArtistTitleUnicode")
    public String mapArtistTitleUnicode;
    @JsonProperty("mapDiff")
    public String mapDifficulty;
    @JsonProperty("creator")
    public String mapCreator;
    @JsonProperty("diffName")
    public String difficultyName;
    // Ignore HashCode
    // Ignore Osu File Name
    @JsonProperty("maxBpm")
    public float maxBPM;
    @JsonProperty("minBpm")
    public float minBPM;
    @JsonProperty("bpm")
    public float bpm;
    @JsonProperty("mainBpm")
    public float mainBPM;
    // Ignore Tags
    @JsonProperty("circles")
    public int circles;
    @JsonProperty("sliders")
    public int sliders;
    @JsonProperty("spinners")
    public int spinners;
    @JsonProperty("ar")
    public float ar;
    @JsonProperty("cs")
    public float cs;
    @JsonProperty("hp")
    public float hp;
    @JsonProperty("od")
    public float od;
    @JsonProperty("sv")
    public float sv;
    @JsonProperty("starsNomod")
    public float starsNoMod;
    @JsonProperty("drainingtime")
    public long drainingTime;
    @JsonProperty("totaltime")
    public long totalTime;
    @JsonProperty("previewtime")
    public long previewTime;
    @JsonProperty("dl")
    public String downloadLink;
    // Ignore Thread Id
    // Ignore Sl?
    @JsonProperty("mode")
    public int mode;
    @JsonProperty("source")
    public String source;
    // Ignore Directory
    // Ignore LB
    @JsonProperty("gameMode")
    public String gameMode;
    @JsonProperty("maxCombo")
    public int maxCombo;
    @JsonProperty("mods")
    public String mods;
    @JsonProperty("modsEnum")
    public long modsEnum;
    @JsonProperty("mAR")
    public float mAR;
    @JsonProperty("mCS")
    public float mCS;
    @JsonProperty("mOD")
    public float mOD;
    @JsonProperty("mHP")
    public float mHP;
    @JsonProperty("mStars")
    public float mStars;
    @JsonProperty("mBpm")
    public float mBPM;
    @JsonProperty("mMaxBpm")
    public float mMaxBPM;
    @JsonProperty("mMinBpm")
    public float mMinBPM;
    @JsonProperty("mMainBpm")
    public float MMainBPm;

}