package it.polimi.ingsw.model.effects;

public class EffectFactory {

    /**
     * create a new {@link Effect} accordingly to the paramaters
     * @param nameEffect is the effect' name
     * @param parameter is the parameter need to create the effect (it's usually an enum that define the
     *                  behaviour of the effect)
     * @return the {@link Effect} created
     */
    public Effect createEffect(String nameEffect, String parameter) {
        if(nameEffect.equals("RemoveFrom")) {
            return new RemoveFrom(Element.valueOf(parameter));
        } else if(nameEffect.equals("ChangeDiceFace")) {
            return new ChangeDiceFace(ChangeFace.valueOf(parameter));
        } else if(nameEffect.equals("EnableException")) {
            return new EnableException(RestrictionException.valueOf(parameter));
        } else if(nameEffect.equals("PlaceIn")) {
            return new PlaceIn(Element.valueOf(parameter));
        } else if(nameEffect.equals("SkipNextTurn")) {
            return new SkipNextTurn();
        } else if(nameEffect.equals("SwapWith")) {
            return new SwapWith(Element.valueOf(parameter));
        } else if(nameEffect.equals("RollDraftPool")) {
            return new RollDraftPool();
        } else if(nameEffect.equals("SearchRoundTrack")) {
            return new SearchRoundTrack();
        } else {
            return null;
        }
    }

}