package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatternDeck {

    private ArrayList<PatternCard> patternDeck;

    private PatternCard kaleidoscopic_dream;
    private PatternCard aurorae_magnificus;
    private PatternCard sun_catcher;
    private PatternCard virtus;
    private PatternCard via_lux;
    private PatternCard bellesguard;
    private PatternCard firmitas;
    private PatternCard aurora_sagradis;
    private PatternCard shadow_thief;
    private PatternCard symphony_of_light;
    private PatternCard industria;
    private PatternCard batllo;
    private PatternCard gravitas;
    private PatternCard lux_astram;
    private PatternCard firelight;
    private PatternCard fractal_drops;
    private PatternCard chromatic_splendor;
    private PatternCard luz_celestial;
    private PatternCard water_of_life;
    private PatternCard lux_mundi;
    private PatternCard suns_glory;
    private PatternCard ripples_of_light;
    private PatternCard comitas;
    private PatternCard fulgor_del_cielo;

    Logger logger = Logger.getLogger(PatternDeck.class.getName());

    //public void createPatternDeck() {

    public PatternDeck() {
        patternDeck = new ArrayList<PatternCard>();

        kaleidoscopic_dream = new PatternCard("Kaleidoscopic Dream", 1);
        aurorae_magnificus = new PatternCard("Aurorae Magnificus", 2);
        sun_catcher = new PatternCard("Sun Catcher", 3);
        virtus = new PatternCard("Virtus", 4);
        via_lux = new PatternCard("Via Lux", 5);
        bellesguard = new PatternCard("Bellesguard", 6);
        firmitas = new PatternCard("Firmitas", 7);
        aurora_sagradis = new PatternCard("Aurora Sagradis", 8);
        shadow_thief = new PatternCard("Shadow Thief", 9);
        symphony_of_light = new PatternCard("Symphony Of Light", 10);
        industria = new PatternCard("Industria", 11);
        batllo = new PatternCard("Batllo", 12);
        gravitas = new PatternCard("Gravitas", 13);
        lux_astram = new PatternCard("Lux Astram", 14);
        firelight = new PatternCard("Firelight", 15);
        fractal_drops = new PatternCard("Fractal Drops", 16);
        chromatic_splendor = new PatternCard("Chromatic Splendor", 17);
        luz_celestial = new PatternCard("Luz Celestial", 18);
        water_of_life = new PatternCard("Water Of Life", 19);
        lux_mundi = new PatternCard("Lux Mundi", 20);
        suns_glory = new PatternCard("Sun's Glory", 21);
        ripples_of_light = new PatternCard("Ripples Of Light", 22);
        comitas = new PatternCard("Comitas", 23);
        fulgor_del_cielo = new PatternCard("Fulgor Del Cielo", 24);

        //kaleidoscopic_dream
        try {
            kaleidoscopic_dream.setDifficulty(4);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(kaleidoscopic_dream);

        //aurorae_magnificus
        try {
            aurorae_magnificus.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(aurorae_magnificus);

        //sun_catcher
        try {
            sun_catcher.setDifficulty(3);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(sun_catcher);

        //virtus
        try {
            virtus.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(virtus);

        //via_lux
        try {
            via_lux.setDifficulty(4);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(via_lux);

        //bellesguard
        try {
            bellesguard.setDifficulty(3);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(bellesguard);

        //firmitas
        try {
            firmitas.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(firmitas);

        //aurora_sagradis
        try {
            aurora_sagradis.setDifficulty(4);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(aurora_sagradis);

        //shadow_thief
        try {
            shadow_thief.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(shadow_thief);

        //symphony_of_light
        try {
            symphony_of_light.setDifficulty(6);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(symphony_of_light);

        //industria
        try {
            industria.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(industria);

        //batllo
        try {
            batllo.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(batllo);

        //gravitas
        try {
            gravitas.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(gravitas);

        //lux_astram
        try {
            lux_astram.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(lux_astram);

        //firelight
        try {
            firelight.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(firelight);

        //fractal_drops
        try {
            fractal_drops.setDifficulty(3);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(fractal_drops);

        //chromatic_splendor
        try {
            chromatic_splendor.setDifficulty(4);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(chromatic_splendor);

        //luz_celestial
        try {
            luz_celestial.setDifficulty(3);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(luz_celestial);

        //water_of_life
        try {
            water_of_life.setDifficulty(6);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(water_of_life);

        //lux_mundi
        try {
            lux_mundi.setDifficulty(6);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(lux_mundi);

        //suns_glory
        try {
            suns_glory.setDifficulty(6);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(suns_glory);

        //ripples_of_light
        try {
            ripples_of_light.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(ripples_of_light);

        //comitas
        try {
            comitas.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(comitas);

        //fulgor_del_cielo
        try {
            fulgor_del_cielo.setDifficulty(5);
        } catch (NotValidInputException e) {
            logger.log(Level.SEVERE, "context", e);
        }
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
        patternDeck.add(fulgor_del_cielo);
    }

    public ArrayList<PatternCard> getPatternDeck(){
        return this.patternDeck;
    }

    public PatternCard getPatternCard(int id) throws NotValidInputException {   //l'ho aggiunta per avere un controllo sul parametro
        if (id<0 || id>=patternDeck.size()){                                 // ma non so se può servire, ho visto che per ora
            throw new NotValidInputException();                                    //avete usato la getPatternDeck().get(index).
        }                                                                          //Nel caso non serva toglietela pure
        return this.patternDeck.get(id-1);
    }

    public PatternCard removePatternCard(int index) {
        if(index<0 || index >patternDeck.size()) throw new IndexOutOfBoundsException();
        return patternDeck.remove(index);
    }

    public int size(){
        return patternDeck.size();
    }


    public void dump() {
        for (PatternCard c: patternDeck) {
            c.dump();
            System.out.println("\n");
        }
    }

    @Override
    public String toString() {
        String string= "";
        for (PatternCard c: patternDeck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }

}



