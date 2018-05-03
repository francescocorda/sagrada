package it.polimi.ingsw;

import java.util.ArrayList;

public class PatternDeck {

    private ArrayList<PatternCard> patterns = new ArrayList<PatternCard>();

    PatternCard kaleidoscopic_dream = new PatternCard("Kaleidoscopic Dream", 1);
    PatternCard aurorae_magnificus = new PatternCard("Aurorae Magnificus", 2);
    PatternCard sun_catcher = new PatternCard("Sun Catcher", 3);
    PatternCard virtus = new PatternCard("Virtus", 4);
    PatternCard via_lux = new PatternCard("Via Lux", 5);
    PatternCard bellesguard = new PatternCard("Bellesguard", 6);
    PatternCard firmitas = new PatternCard("Firmitas", 7);
    PatternCard aurora_sagradis = new PatternCard("Aurora Sagradis", 8);
    PatternCard shadow_thief = new PatternCard("Shadow Thief", 9);
    PatternCard symphony_of_light = new PatternCard("Symphony Of Light", 10);
    PatternCard industria = new PatternCard("Industria", 11);
    PatternCard batllo = new PatternCard("Batllo", 12);
    PatternCard gravitas = new PatternCard("Gravitas", 13);
    PatternCard lux_astram = new PatternCard("Lux Astram", 14);
    PatternCard firelight = new PatternCard("Firelight", 15);
    PatternCard fractal_drops = new PatternCard("Fractal Drops", 16);
    PatternCard chromatic_splendor = new PatternCard("Chromatic Splendor", 17);
    PatternCard luz_celestial = new PatternCard("Luz Celestial", 18);
    PatternCard water_of_life = new PatternCard("Water Of Life", 19);
    PatternCard lux_mundi = new PatternCard("Lux Mundi", 20);
    PatternCard suns_glory = new PatternCard("Sun's Glory", 21);
    PatternCard ripples_of_light = new PatternCard("Ripples Of Light", 22);
    PatternCard comitas = new PatternCard("Comitas", 23);
    PatternCard fulgor_del_cielo = new PatternCard("Fulgor Del Cielo", 24);

    public void createPatternDeck() {


        //kaleidoscopic_dream
        kaleidoscopic_dream.setDifficulty(4);
        kaleidoscopic_dream.setRestriction(1,1, Restriction.ANSI_YELLOW);
        kaleidoscopic_dream.setRestriction(1,2, Restriction.ANSI_BLUE);
        kaleidoscopic_dream.setRestriction(1,5, Restriction.ONE);
        kaleidoscopic_dream.setRestriction(2,1, Restriction.ANSI_GREEN);
        kaleidoscopic_dream.setRestriction(2,3, Restriction.FIVE);
        kaleidoscopic_dream.setRestriction(2,5, Restriction.FOUR);
        kaleidoscopic_dream.setRestriction(3,1, Restriction.THREE);
        kaleidoscopic_dream.setRestriction(3,3, Restriction.ANSI_RED);
        kaleidoscopic_dream.setRestriction(3,5, Restriction.ANSI_GREEN);
        kaleidoscopic_dream.setRestriction(4,1, Restriction.TWO);
        kaleidoscopic_dream.setRestriction(4,4, Restriction.ANSI_BLUE);
        kaleidoscopic_dream.setRestriction(4,5, Restriction.ANSI_YELLOW);
        patterns.add(kaleidoscopic_dream);

        //aurorae_magnificus
        aurorae_magnificus.setDifficulty(5);
        aurorae_magnificus.setRestriction(1,1, Restriction.FIVE);
        aurorae_magnificus.setRestriction(1,2, Restriction.ANSI_GREEN);
        aurorae_magnificus.setRestriction(1,3, Restriction.ANSI_BLUE);
        aurorae_magnificus.setRestriction(1,4, Restriction.ANSI_PURPLE);
        aurorae_magnificus.setRestriction(1,5, Restriction.TWO);
        aurorae_magnificus.setRestriction(2,1, Restriction.ANSI_PURPLE);
        aurorae_magnificus.setRestriction(2,5, Restriction.ANSI_YELLOW);
        aurorae_magnificus.setRestriction(3,1, Restriction.ANSI_YELLOW);
        aurorae_magnificus.setRestriction(3,3, Restriction.SIX);
        aurorae_magnificus.setRestriction(3,5, Restriction.ANSI_PURPLE);
        aurorae_magnificus.setRestriction(4,1, Restriction.ONE);
        aurorae_magnificus.setRestriction(4,4, Restriction.ANSI_GREEN);
        aurorae_magnificus.setRestriction(4,5, Restriction.FOUR);
        patterns.add(aurorae_magnificus);

        //sun_catcher
        sun_catcher.setDifficulty(3);
        sun_catcher.setRestriction(1,2, Restriction.ANSI_BLUE);
        sun_catcher.setRestriction(1,3, Restriction.TWO);
        sun_catcher.setRestriction(1,5, Restriction.ANSI_YELLOW);
        sun_catcher.setRestriction(2,2, Restriction.FOUR);
        sun_catcher.setRestriction(2,4, Restriction.ANSI_RED);
        sun_catcher.setRestriction(3,3, Restriction.FIVE);
        sun_catcher.setRestriction(3,4, Restriction.ANSI_YELLOW);
        sun_catcher.setRestriction(4,1, Restriction.ANSI_GREEN);
        sun_catcher.setRestriction(4,2, Restriction.THREE);
        sun_catcher.setRestriction(4,5, Restriction.ANSI_PURPLE);
        patterns.add(sun_catcher);

        //virtus
        virtus.setDifficulty(5);
        virtus.setRestriction(1,1, Restriction.FOUR);
        virtus.setRestriction(1,3, Restriction.TWO);
        virtus.setRestriction(1,4, Restriction.FIVE);
        virtus.setRestriction(1,5, Restriction.ANSI_GREEN);
        virtus.setRestriction(2,3, Restriction.SIX);
        virtus.setRestriction(2,4, Restriction.ANSI_GREEN);
        virtus.setRestriction(2,5, Restriction.TWO);
        virtus.setRestriction(3,2, Restriction.THREE);
        virtus.setRestriction(3,3, Restriction.ANSI_GREEN);
        virtus.setRestriction(3,4, Restriction.FOUR);
        virtus.setRestriction(4,1, Restriction.FIVE);
        virtus.setRestriction(4,2, Restriction.ANSI_GREEN);
        virtus.setRestriction(4,3, Restriction.ONE);
        patterns.add(virtus);

        //via_lux
        via_lux.setDifficulty(4);
        via_lux.setRestriction(1,1, Restriction.ANSI_YELLOW);
        via_lux.setRestriction(1,3, Restriction.SIX);
        via_lux.setRestriction(2,2, Restriction.ONE);
        via_lux.setRestriction(2,3, Restriction.FIVE);
        via_lux.setRestriction(2,5, Restriction.TWO);
        via_lux.setRestriction(3,1, Restriction.THREE);
        via_lux.setRestriction(3,2, Restriction.ANSI_YELLOW);
        via_lux.setRestriction(3,3, Restriction.ANSI_RED);
        via_lux.setRestriction(3,4, Restriction.ANSI_PURPLE);
        via_lux.setRestriction(4,3, Restriction.FOUR);
        via_lux.setRestriction(4,4, Restriction.THREE);
        via_lux.setRestriction(4,5, Restriction.ANSI_RED);
        patterns.add(via_lux);

        //bellesguard
        bellesguard.setDifficulty(3);
        bellesguard.setRestriction(1,1, Restriction.ANSI_BLUE);
        bellesguard.setRestriction(1,2, Restriction.SIX);
        bellesguard.setRestriction(1,5, Restriction.ANSI_YELLOW);
        bellesguard.setRestriction(2,2, Restriction.THREE);
        bellesguard.setRestriction(2,3, Restriction.ANSI_BLUE);
        bellesguard.setRestriction(3,2, Restriction.FIVE);
        bellesguard.setRestriction(3,3, Restriction.SIX);
        bellesguard.setRestriction(3,4, Restriction.TWO);
        bellesguard.setRestriction(4,2, Restriction.FOUR);
        bellesguard.setRestriction(4,4, Restriction.ONE);
        bellesguard.setRestriction(4,5, Restriction.ANSI_GREEN);
        patterns.add(bellesguard);

        //firmitas
        firmitas.setDifficulty(5);
        firmitas.setRestriction(1,1, Restriction.ANSI_PURPLE);
        firmitas.setRestriction(1,2, Restriction.SIX);
        firmitas.setRestriction(1,5, Restriction.THREE);
        firmitas.setRestriction(2,1, Restriction.FIVE);
        firmitas.setRestriction(2,2, Restriction.ANSI_PURPLE);
        firmitas.setRestriction(2,3, Restriction.THREE);
        firmitas.setRestriction(3,2, Restriction.TWO);
        firmitas.setRestriction(3,3, Restriction.ANSI_PURPLE);
        firmitas.setRestriction(3,4, Restriction.ONE);
        firmitas.setRestriction(4,2, Restriction.ONE);
        firmitas.setRestriction(4,3, Restriction.FIVE);
        firmitas.setRestriction(4,4, Restriction.ANSI_PURPLE);
        firmitas.setRestriction(4,5, Restriction.FOUR);
        patterns.add(firmitas);

        //aurora_sagradis
        aurora_sagradis.setDifficulty(4);
        aurora_sagradis.setRestriction(1,1, Restriction.ANSI_RED);
        aurora_sagradis.setRestriction(1,3, Restriction.ANSI_BLUE);
        aurora_sagradis.setRestriction(1,5, Restriction.ANSI_YELLOW);
        aurora_sagradis.setRestriction(2,1, Restriction.FOUR);
        aurora_sagradis.setRestriction(2,2, Restriction.ANSI_PURPLE);
        aurora_sagradis.setRestriction(2,3, Restriction.THREE);
        aurora_sagradis.setRestriction(2,4, Restriction.ANSI_GREEN);
        aurora_sagradis.setRestriction(2,5, Restriction.TWO);
        aurora_sagradis.setRestriction(3,2, Restriction.ONE);
        aurora_sagradis.setRestriction(3,4, Restriction.FIVE);
        aurora_sagradis.setRestriction(4,3, Restriction.SIX);
        patterns.add(aurora_sagradis);

        //shadow_thief
        shadow_thief.setDifficulty(5);
        shadow_thief.setRestriction(1,1, Restriction.SIX);
        shadow_thief.setRestriction(1,2, Restriction.ANSI_PURPLE);
        shadow_thief.setRestriction(1,5, Restriction.FIVE);
        shadow_thief.setRestriction(2,1, Restriction.FIVE);
        shadow_thief.setRestriction(2,3, Restriction.ANSI_PURPLE);
        shadow_thief.setRestriction(3,1, Restriction.ANSI_RED);
        shadow_thief.setRestriction(3,2, Restriction.SIX);
        shadow_thief.setRestriction(3,4, Restriction.ANSI_PURPLE);
        shadow_thief.setRestriction(4,1, Restriction.ANSI_YELLOW);
        shadow_thief.setRestriction(4,2, Restriction.ANSI_RED);
        shadow_thief.setRestriction(4,3, Restriction.FIVE);
        shadow_thief.setRestriction(4,4, Restriction.FOUR);
        shadow_thief.setRestriction(4,5, Restriction.THREE);
        patterns.add(shadow_thief);

        //symphony_of_light
        symphony_of_light.setDifficulty(6);
        symphony_of_light.setRestriction(1,1, Restriction.TWO);
        symphony_of_light.setRestriction(1,3, Restriction.FIVE);
        symphony_of_light.setRestriction(1,5, Restriction.ONE);
        symphony_of_light.setRestriction(2,1, Restriction.ANSI_YELLOW);
        symphony_of_light.setRestriction(2,2, Restriction.SIX);
        symphony_of_light.setRestriction(2,3, Restriction.ANSI_PURPLE);
        symphony_of_light.setRestriction(2,4, Restriction.TWO);
        symphony_of_light.setRestriction(2,5, Restriction.ANSI_RED);
        symphony_of_light.setRestriction(3,2, Restriction.ANSI_BLUE);
        symphony_of_light.setRestriction(3,3, Restriction.FOUR);
        symphony_of_light.setRestriction(3,4, Restriction.ANSI_GREEN);
        symphony_of_light.setRestriction(4,2, Restriction.THREE);
        symphony_of_light.setRestriction(4,4, Restriction.FIVE);
        patterns.add(symphony_of_light);

        //industria
        industria.setDifficulty(5);
        industria.setRestriction(1,1, Restriction.ONE);
        industria.setRestriction(1,2, Restriction.ANSI_RED);
        industria.setRestriction(1,3, Restriction.THREE);
        industria.setRestriction(1,5, Restriction.SIX);
        industria.setRestriction(2,1, Restriction.FIVE);
        industria.setRestriction(2,2, Restriction.FOUR);
        industria.setRestriction(2,3, Restriction.ANSI_RED);
        industria.setRestriction(2,4, Restriction.TWO);
        industria.setRestriction(3,3, Restriction.FIVE);
        industria.setRestriction(3,4, Restriction.ANSI_RED);
        industria.setRestriction(3,5, Restriction.ONE);
        industria.setRestriction(4,4, Restriction.THREE);
        industria.setRestriction(4,5, Restriction.ANSI_RED);
        patterns.add(industria);

        //batllo
        batllo.setDifficulty(5);
        batllo.setRestriction(1,3, Restriction.SIX);
        batllo.setRestriction(2,2, Restriction.FIVE);
        batllo.setRestriction(2,3, Restriction.ANSI_BLUE);
        batllo.setRestriction(2,4, Restriction.FOUR);
        batllo.setRestriction(3,1, Restriction.THREE);
        batllo.setRestriction(3,2, Restriction.ANSI_GREEN);
        batllo.setRestriction(3,3, Restriction.ANSI_YELLOW);
        batllo.setRestriction(3,4, Restriction.ANSI_PURPLE);
        batllo.setRestriction(3,5, Restriction.TWO);
        batllo.setRestriction(4,1, Restriction.ONE);
        batllo.setRestriction(4,2, Restriction.FOUR);
        batllo.setRestriction(4,3, Restriction.ANSI_RED);
        batllo.setRestriction(4,4, Restriction.FIVE);
        batllo.setRestriction(4,5, Restriction.THREE);
        patterns.add(batllo);

        //gravitas
        gravitas.setDifficulty(5);
        gravitas.setRestriction(1,1, Restriction.ONE);
        gravitas.setRestriction(1,3, Restriction.THREE);
        gravitas.setRestriction(1,4, Restriction.ANSI_BLUE);
        gravitas.setRestriction(2,2, Restriction.TWO);
        gravitas.setRestriction(2,3, Restriction.ANSI_BLUE);
        gravitas.setRestriction(3,1, Restriction.SIX);
        gravitas.setRestriction(3,2, Restriction.ANSI_BLUE);
        gravitas.setRestriction(3,4, Restriction.FOUR);
        gravitas.setRestriction(4,1, Restriction.ANSI_BLUE);
        gravitas.setRestriction(4,2, Restriction.FIVE);
        gravitas.setRestriction(4,3, Restriction.TWO);
        gravitas.setRestriction(4,5, Restriction.ONE);
        patterns.add(gravitas);

        //lux_astram
        lux_astram.setDifficulty(5);
        lux_astram.setRestriction(1,2, Restriction.ONE);
        lux_astram.setRestriction(1,3, Restriction.ANSI_GREEN);
        lux_astram.setRestriction(1,4, Restriction.ANSI_PURPLE);
        lux_astram.setRestriction(1,5, Restriction.FOUR);
        lux_astram.setRestriction(2,1, Restriction.SIX);
        lux_astram.setRestriction(2,2, Restriction.ANSI_PURPLE);
        lux_astram.setRestriction(2,3, Restriction.TWO);
        lux_astram.setRestriction(2,4, Restriction.FIVE);
        lux_astram.setRestriction(2,5, Restriction.ANSI_GREEN);
        lux_astram.setRestriction(3,1, Restriction.ONE);
        lux_astram.setRestriction(3,2, Restriction.ANSI_GREEN);
        lux_astram.setRestriction(3,3, Restriction.FIVE);
        lux_astram.setRestriction(3,4, Restriction.THREE);
        lux_astram.setRestriction(3,5, Restriction.ANSI_PURPLE);
        patterns.add(lux_astram);

        //firelight
        firelight.setDifficulty(5);
        firelight.setRestriction(1,1, Restriction.THREE);
        firelight.setRestriction(1,2, Restriction.FOUR);
        firelight.setRestriction(1,3, Restriction.ONE);
        firelight.setRestriction(1,4, Restriction.FIVE);
        firelight.setRestriction(2,2, Restriction.SIX);
        firelight.setRestriction(2,3, Restriction.TWO);
        firelight.setRestriction(2,5, Restriction.ANSI_YELLOW);
        firelight.setRestriction(3,4, Restriction.ANSI_YELLOW);
        firelight.setRestriction(3,5, Restriction.ANSI_RED);
        firelight.setRestriction(4,1, Restriction.FIVE);
        firelight.setRestriction(4,3, Restriction.ANSI_YELLOW);
        firelight.setRestriction(4,4, Restriction.ANSI_RED);
        firelight.setRestriction(4,5, Restriction.SIX);
        patterns.add(firelight);

        //fractal_drops
        fractal_drops.setDifficulty(3);
        fractal_drops.setRestriction(1,2, Restriction.FOUR);
        fractal_drops.setRestriction(1,4, Restriction.ANSI_YELLOW);
        fractal_drops.setRestriction(1,5, Restriction.SIX);
        fractal_drops.setRestriction(2,1, Restriction.ANSI_RED);
        fractal_drops.setRestriction(2,3, Restriction.TWO);
        fractal_drops.setRestriction(3,3, Restriction.ANSI_RED);
        fractal_drops.setRestriction(3,4, Restriction.ANSI_PURPLE);
        fractal_drops.setRestriction(3,5, Restriction.ONE);
        fractal_drops.setRestriction(4,1, Restriction.ANSI_BLUE);
        fractal_drops.setRestriction(4,2, Restriction.ANSI_YELLOW);
        patterns.add(fractal_drops);

        //chromatic_splendor
        chromatic_splendor.setDifficulty(4);
        chromatic_splendor.setRestriction(1,3, Restriction.ANSI_GREEN);
        chromatic_splendor.setRestriction(2,1, Restriction.TWO);
        chromatic_splendor.setRestriction(2,2, Restriction.ANSI_YELLOW);
        chromatic_splendor.setRestriction(2,3, Restriction.FIVE);
        chromatic_splendor.setRestriction(2,4, Restriction.ANSI_BLUE);
        chromatic_splendor.setRestriction(2,5, Restriction.ONE);
        chromatic_splendor.setRestriction(3,2, Restriction.ANSI_RED);
        chromatic_splendor.setRestriction(3,3, Restriction.THREE);
        chromatic_splendor.setRestriction(3,4, Restriction.ANSI_PURPLE);
        chromatic_splendor.setRestriction(4,1, Restriction.ONE);
        chromatic_splendor.setRestriction(4,3, Restriction.SIX);
        chromatic_splendor.setRestriction(4,5, Restriction.FOUR);
        patterns.add(chromatic_splendor);

        //luz_celestial
        luz_celestial.setDifficulty(3);
        luz_celestial.setRestriction(1,3, Restriction.ANSI_RED);
        luz_celestial.setRestriction(1,4, Restriction.FIVE);
        luz_celestial.setRestriction(2,1, Restriction.ANSI_PURPLE);
        luz_celestial.setRestriction(2,2, Restriction.FOUR);
        luz_celestial.setRestriction(2,4, Restriction.ANSI_GREEN);
        luz_celestial.setRestriction(2,5, Restriction.THREE);
        luz_celestial.setRestriction(3,1, Restriction.SIX);
        luz_celestial.setRestriction(3,4, Restriction.ANSI_BLUE);
        luz_celestial.setRestriction(4,2, Restriction.ANSI_YELLOW);
        luz_celestial.setRestriction(4,3, Restriction.TWO);
        patterns.add(luz_celestial);

        //water_of_life
        water_of_life.setDifficulty(6);
        water_of_life.setRestriction(1,1, Restriction.SIX);
        water_of_life.setRestriction(1,2, Restriction.ANSI_BLUE);
        water_of_life.setRestriction(1,5, Restriction.ONE);
        water_of_life.setRestriction(2,2, Restriction.FIVE);
        water_of_life.setRestriction(2,3, Restriction.ANSI_BLUE);
        water_of_life.setRestriction(3,1, Restriction.FOUR);
        water_of_life.setRestriction(3,2, Restriction.ANSI_RED);
        water_of_life.setRestriction(3,3, Restriction.TWO);
        water_of_life.setRestriction(3,4, Restriction.ANSI_BLUE);
        water_of_life.setRestriction(4,1, Restriction.ANSI_GREEN);
        water_of_life.setRestriction(4,2, Restriction.SIX);
        water_of_life.setRestriction(4,3, Restriction.ANSI_YELLOW);
        water_of_life.setRestriction(4,4, Restriction.THREE);
        water_of_life.setRestriction(4,5, Restriction.ANSI_PURPLE);
        patterns.add(water_of_life);

        //lux_mundi
        lux_mundi.setDifficulty(6);
        lux_mundi.setRestriction(1,3, Restriction.ONE);
        lux_mundi.setRestriction(2,1, Restriction.ONE);
        lux_mundi.setRestriction(2,2, Restriction.ANSI_GREEN);
        lux_mundi.setRestriction(2,3, Restriction.THREE);
        lux_mundi.setRestriction(2,4, Restriction.ANSI_BLUE);
        lux_mundi.setRestriction(2,5, Restriction.TWO);
        lux_mundi.setRestriction(3,1, Restriction.ANSI_BLUE);
        lux_mundi.setRestriction(3,2, Restriction.FIVE);
        lux_mundi.setRestriction(3,3, Restriction.FOUR);
        lux_mundi.setRestriction(3,4, Restriction.SIX);
        lux_mundi.setRestriction(3,5, Restriction.ANSI_GREEN);
        lux_mundi.setRestriction(4,2, Restriction.ANSI_BLUE);
        lux_mundi.setRestriction(4,3, Restriction.FIVE);
        lux_mundi.setRestriction(4,4, Restriction.ANSI_GREEN);
        patterns.add(lux_mundi);

        //suns_glory
        suns_glory.setDifficulty(6);
        suns_glory.setRestriction(1,1, Restriction.ONE);
        suns_glory.setRestriction(1,2, Restriction.ANSI_PURPLE);
        suns_glory.setRestriction(1,3, Restriction.ANSI_YELLOW);
        suns_glory.setRestriction(1,5, Restriction.FOUR);
        suns_glory.setRestriction(2,1, Restriction.ANSI_PURPLE);
        suns_glory.setRestriction(2,2, Restriction.ANSI_YELLOW);
        suns_glory.setRestriction(2,5, Restriction.SIX);
        suns_glory.setRestriction(3,1, Restriction.ANSI_YELLOW);
        suns_glory.setRestriction(3,4, Restriction.FIVE);
        suns_glory.setRestriction(3,5, Restriction.THREE);
        suns_glory.setRestriction(4,2, Restriction.FIVE);
        suns_glory.setRestriction(4,3, Restriction.FOUR);
        suns_glory.setRestriction(4,4, Restriction.TWO);
        suns_glory.setRestriction(4,5, Restriction.ONE);
        patterns.add(suns_glory);

        //ripples_of_light
        ripples_of_light.setDifficulty(5);
        ripples_of_light.setRestriction(1,4, Restriction.ANSI_RED);
        ripples_of_light.setRestriction(1,5, Restriction.FIVE);
        ripples_of_light.setRestriction(2,3, Restriction.ANSI_PURPLE);
        ripples_of_light.setRestriction(2,4, Restriction.FOUR);
        ripples_of_light.setRestriction(2,5, Restriction.ANSI_BLUE);
        ripples_of_light.setRestriction(3,2, Restriction.ANSI_BLUE);
        ripples_of_light.setRestriction(3,3, Restriction.THREE);
        ripples_of_light.setRestriction(3,4, Restriction.ANSI_YELLOW);
        ripples_of_light.setRestriction(3,5, Restriction.SIX);
        ripples_of_light.setRestriction(4,1, Restriction.ANSI_YELLOW);
        ripples_of_light.setRestriction(4,2, Restriction.TWO);
        ripples_of_light.setRestriction(4,3, Restriction.ANSI_GREEN);
        ripples_of_light.setRestriction(4,4, Restriction.ONE);
        ripples_of_light.setRestriction(4,5, Restriction.ANSI_RED);
        patterns.add(ripples_of_light);

        //comitas
        comitas.setDifficulty(5);
        comitas.setRestriction(1,1, Restriction.ANSI_YELLOW);
        comitas.setRestriction(1,3, Restriction.TWO);
        comitas.setRestriction(1,5, Restriction.SIX);
        comitas.setRestriction(2,2, Restriction.FOUR);
        comitas.setRestriction(2,4, Restriction.FIVE);
        comitas.setRestriction(2,5, Restriction.ANSI_YELLOW);
        comitas.setRestriction(3,4, Restriction.ANSI_YELLOW);
        comitas.setRestriction(3,5, Restriction.FIVE);
        comitas.setRestriction(4,1, Restriction.ONE);
        comitas.setRestriction(4,2, Restriction.TWO);
        comitas.setRestriction(4,3, Restriction.ANSI_YELLOW);
        comitas.setRestriction(4,4, Restriction.THREE);
        patterns.add(comitas);

        //fulgor_del_cielo
        fulgor_del_cielo.setDifficulty(5);
        fulgor_del_cielo.setRestriction(1,2, Restriction.ANSI_BLUE);
        fulgor_del_cielo.setRestriction(1,3, Restriction.ANSI_RED);
        fulgor_del_cielo.setRestriction(2,2, Restriction.FOUR);
        fulgor_del_cielo.setRestriction(2,3, Restriction.FIVE);
        fulgor_del_cielo.setRestriction(2,5, Restriction.ANSI_BLUE);
        fulgor_del_cielo.setRestriction(3,1, Restriction.ANSI_BLUE);
        fulgor_del_cielo.setRestriction(3,2, Restriction.TWO);
        fulgor_del_cielo.setRestriction(3,4, Restriction.ANSI_RED);
        fulgor_del_cielo.setRestriction(3,5, Restriction.FIVE);
        fulgor_del_cielo.setRestriction(4,1, Restriction.SIX);
        fulgor_del_cielo.setRestriction(4,2, Restriction.ANSI_RED);
        fulgor_del_cielo.setRestriction(4,3, Restriction.THREE);
        fulgor_del_cielo.setRestriction(4,4, Restriction.ONE);
        patterns.add(fulgor_del_cielo);
    }


    public void printPatternDeck() {
        for (int k=0; k<24; k++) {
            patterns.get(k).dump();
        }
    }
}



