package usagibot.osu.pp;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.BeatmapAttributes;
import usagibot.osu.api.Mods;

@Slf4j
public class OsuScore {

    private float totalValue;
    private float effectiveMissCount;
    private float aimValue;
    private float accuracyValue;
    private float speedValue;
    private float flashlightValue;

    private int num50;
    private int num100;
    private int num300;
    private int numMiss;
    private int maxCombo;
    private int mods;

    public OsuScore(int m_maxCombo, int m_num300, int m_num100, int m_num50, int m_numMiss, int m_mods) {
       super();
       this.maxCombo = m_maxCombo;
       this.num300 = m_num300;
       this.num100 = m_num100;
       this.num50 = m_num50;
       this.numMiss = m_numMiss;
       this.mods = m_mods;
    }

    public float readPP(Beatmap beatmap) {
        computeEffectiveMissCount(beatmap);

        computeAimValue(beatmap);
        computeSpeedValue(beatmap);
        computeAccuracyValue(beatmap);
        computeFlashlightValue(beatmap);

        computeTotalValue(beatmap);
        return totalValue();
    }

    public float totalValue() {
        log.info("Total Value: " + totalValue);
        log.info("Total Hits: " + totalHits());
        return totalValue;
    }

    public float accuracy() {
        if (totalHits() == 0)
            return 0;
        log.info("Accuracy: " + clamp((float) (num50 * 50 + num100 * 100 + num300 * 300) / (totalHits() * 300), 0.0f, 1.0f));
        return clamp((float) (num50 * 50 + num100 * 100 + num300 * 300) / (totalHits() * 300), 0.0f, 1.0f);
    }

    private int totalHits() {
        return num50 + num100 + num300 + numMiss;
    }

    private int totalSuccessfulHits() {
        return num50 + num100 + num300;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private void computeEffectiveMissCount(Beatmap beatmap) {
        float comboBasedMissCount = 0.0f;
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);
        float beatmapMaxCombo = map.getAttributes().getMax_combo();

        if (beatmap.getCount_sliders() > 0) {
            float fullComboThreshold = beatmapMaxCombo - 0.1f * beatmap.getCount_sliders();
            if (maxCombo < fullComboThreshold) {
                comboBasedMissCount = fullComboThreshold / Math.max(1, maxCombo);
            }
            comboBasedMissCount = Math.min(comboBasedMissCount, (float)(num100 + num50 + numMiss));
            effectiveMissCount = Math.max((float)numMiss, comboBasedMissCount);
        }
        log.info("Effective Miss Count: " + effectiveMissCount);
    }

    private void computeTotalValue(Beatmap beatmap) {
        if (Mods.Relax.is(mods) || Mods.Relax2.is(mods) || Mods.Autoplay.is(mods)) {
            totalValue = 0;
            return;
        }

        float multiplier = 1.14f;

        if (Mods.NoFail.is(mods))
            multiplier *= Math.max(0.9f, 1.0f - 0.02f * effectiveMissCount);

        int numTotalHits = totalHits();

        if (Mods.SpunOut.is(mods))
            multiplier *= 1.0f - Math.pow(beatmap.getCount_spinners() / (float) numTotalHits, 0.85f);

        totalValue = (float) (Math.pow(
                Math.pow(aimValue, 1.1f) +
                        Math.pow(speedValue, 1.1f) +
                        Math.pow(accuracyValue, 1.1f) +
                        Math.pow(flashlightValue, 1.1),
                1.0f / 1.1f) * multiplier);
        log.info("Multiplier: " + multiplier);
        log.info("Total Value (In Method): " + totalValue);
    }

    private void computeAimValue(Beatmap beatmap) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);

        aimValue = (float) (Math.pow(5.0 * Math.max(1.0, map.getAttributes().getAim_difficulty() / 0.0675) - 4.0, 3.0) / 100000.0);

        int numTotalHits = totalHits();

        float lengthBonus = (float) (0.95f + 0.4f * Math.min(1.0f, (float) numTotalHits / 2000.0f) +
                        (numTotalHits > 2000 ? Math.log10((float) numTotalHits / 2000.0f) * 0.5f : 0.0f));

        aimValue *= lengthBonus;

        if (effectiveMissCount > 0)
            aimValue *= 0.97f * Math.pow(1.0 - Math.pow(effectiveMissCount / (float) numTotalHits, 0.775), effectiveMissCount);

        float approachRate = map.getAttributes().getApproach_rate();
        float approachRateFactor = 0.0f;

        if (approachRate > 10.33f)
            approachRateFactor = 0.3f * (approachRate - 10.33f);
        else if (approachRate < 8.0f)
            approachRateFactor = 0.05f * (8.0f - approachRate);

        aimValue *= 1.0f + approachRateFactor * lengthBonus;

        if (Mods.Hidden.is(mods))
            aimValue *= 1.0f + 0.04f * (12.0f - approachRate);

        float estimateDifficultSliders = beatmap.getCount_sliders() * 0.15f;

        if (beatmap.getCount_sliders() > 0) {
            float _maxCombo = map.getAttributes().getMax_combo();
            float estimateSliderEndsDropped = Math.min(Math.max(Math.min((float) (num100 + num50 + numMiss), _maxCombo - maxCombo), 0.0f), estimateDifficultSliders);
            float sliderFactor = map.getAttributes().getSlider_factor();
            float sliderNerfFactor = (float) ((1.0f - sliderFactor) * Math.pow(1.0 - estimateSliderEndsDropped / estimateDifficultSliders, 3) + sliderFactor);
            aimValue *= sliderNerfFactor;
        }

        aimValue *= accuracy();
        aimValue *= 0.98f + (Math.pow(map.getAttributes().getOverall_difficulty(), 2) / 2500);
        log.info("Aim Value: " + aimValue);
    }

    private void computeSpeedValue(Beatmap beatmap) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);
        float speedNoteCount = map.getAttributes().getSpeedNoteCount();

        speedValue = (float) (Math.pow(5.0 * Math.max(1.0, map.getAttributes().getSpeed_difficulty() / 0.0675) - 4.0, 3.0) / 100000.0);

        int numTotalHits = totalHits();

        float lengthBonus = (float) (0.95f + 0.4f * Math.min(1.0f, (float) numTotalHits / 2000.0f) +
                        (numTotalHits > 2000 ? Math.log10((float) numTotalHits / 2000.0f) * 0.5f : 0.0f));

        speedValue *= lengthBonus;

        if (effectiveMissCount > 0)
            speedValue *= 0.97f * Math.pow(1.0 - Math.pow(effectiveMissCount / (float)numTotalHits, 0.775), Math.pow(effectiveMissCount, 0.875));

        speedValue *= getComboScalingFactor(beatmap);

        float approachRate = map.getAttributes().getApproach_rate();
        float approachRateFactor = 0.0f;

        if (approachRate > 10.33f)
            approachRateFactor = 0.3f * (approachRate - 10.33f);

        speedValue *= 1.0f + approachRateFactor * lengthBonus;

        if (Mods.Hidden.is(mods))
            speedValue *= 1.0f + 0.4f * (12.0f - approachRate);

        float relevantTotalDiff = (float) numTotalHits - speedNoteCount;
        float relevantCountGreat = Math.max(0.0f, num300 - relevantTotalDiff);
        float relevantCountOk = Math.max(0.0f, num100 - Math.max(0.0f, relevantTotalDiff - num300));
        float relevantCountMeh = Math.max(0.0f, num50 - Math.max(0.0f, relevantTotalDiff - num300 - num100));
        float relevantAccuracy = (speedNoteCount == 0.0f) ? 0.0f : (relevantCountGreat * 6.0f + relevantCountOk * 2.0f + relevantCountMeh) / (speedNoteCount * 6.0f);

        speedValue *= (0.95f + Math.pow(map.getAttributes().getOverall_difficulty(), 2) / 750) * Math.pow((accuracy() + relevantAccuracy) / 2.0f, (14.5f - Math.max(map.getAttributes().getOverall_difficulty(), 8.0f)) / 2);
        speedValue *= Math.pow(0.99, (num50 < numTotalHits / 500.0) ? 0.0 : num50 - numTotalHits / 500.0);
        log.info("Speed Value: " + speedValue);
    }

    private void computeAccuracyValue(Beatmap beatmap) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);

        float betterAccuracyPercentage;
        int numHitObjectsWithAccuracy;

        if (Mods.ScoreV2.is(mods)) {
            numHitObjectsWithAccuracy = totalHits();
            betterAccuracyPercentage = accuracy();
        } else {
            numHitObjectsWithAccuracy = beatmap.getCount_circles();
            if (numHitObjectsWithAccuracy > 0) {
                betterAccuracyPercentage = ((float) (num300 - (totalHits() - numHitObjectsWithAccuracy)) * 6 + num100 * 2 + num50) / (numHitObjectsWithAccuracy * 6);
                if (betterAccuracyPercentage < 0) {
                    betterAccuracyPercentage = 0;
                }
            } else {
                betterAccuracyPercentage = 0;
            }
        }

        accuracyValue = (float) (Math.pow(1.52163, map.getAttributes().getOverall_difficulty()) * Math.pow(betterAccuracyPercentage, 24) * 2.83);

        accuracyValue *= Math.min(1.15, (float) Math.pow(numHitObjectsWithAccuracy / 1000.0, 0.3));

        if (Mods.Hidden.is(mods))
            accuracyValue *= 1.08f;

        if (Mods.Flashlight.is(mods))
            accuracyValue *= 1.02f;
        log.info("Accuracy Value: " + accuracyValue);
    }

    private void computeFlashlightValue(Beatmap beatmap) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);

        flashlightValue = 0.0f;

        if (!Mods.Flashlight.is(mods))
            return;

        flashlightValue = (float) (Math.pow(map.getAttributes().getFlashlight_difficulty(), 2.0f) * 25.0f);

        int numTotalHits = totalHits();

        if (effectiveMissCount > 0)
            flashlightValue *= 0.97f * Math.pow(1 - Math.pow(effectiveMissCount / (float) numTotalHits, 0.775f), Math.pow(effectiveMissCount, 0.875f));

        flashlightValue *= getComboScalingFactor(beatmap);

        flashlightValue *= 0.7f + 0.1f * Math.min(1.0f, (float) numTotalHits / 200.0f) +
                (numTotalHits > 200 ? 0.2f * Math.min(1.0f, ((float) numTotalHits - 200) / 200.0f) : 0.0f);

        flashlightValue *= 0.5f + accuracy() / 2.0f;
        flashlightValue *= 0.98f + Math.pow(map.getAttributes().getOverall_difficulty(), 2.0f) / 2500.0f;
        log.info("FlashLight Value: " + flashlightValue);
    }

    private float getComboScalingFactor(Beatmap beatmap) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes((String.valueOf(beatmap.getId())), beatmap.getMode(), mods);

        float _maxCombo = map.getAttributes().getMax_combo();

        if (_maxCombo > 0) {
            log.info("Get Combo Scaling Factor: " + (float) Math.min((float) Math.pow(_maxCombo, 0.8) / Math.pow(maxCombo, 0.8), 1.0f));
            return (float) Math.min((float) Math.pow(_maxCombo, 0.8) / Math.pow(maxCombo, 0.8), 1.0f);
        }
        return 1.0f;
    }
}