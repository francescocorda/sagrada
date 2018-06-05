package it.polimi.ingsw.Model.effects;

public class EffectFactory {

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