Passed Tests

Dice:   
test that a dice has the color setted in the constructor method  
test that a dice has a face value between 1 and 6  
test that can't set a dice to a face value negative or bigger than 7  
test that the method oppositeFace returns the dice's opposite face  

DiceBag:  
test that a dicebag is initialized to 90 dice  
test that when a dice is drawn from the dicebag the dimension is reduced by 1  
test that a dicebag is empty after 90 extractions  
test that a dicebag is divided in groups of 18 dices with the same color  
test that when a number of dices is drawn from the dicebag the dimension is reduced by the same number  
test that you can't draw more dices than there are in the dicebag at the moment  
test that an extracted dice isn't in the dicebag anymore  

PatternCard:  
test that the the difficulty is the one set by setDifficulty  
test that the difficulty is not negative  
test that the difficulty is between 3 and 6  
test that the restricion (Color or Face) is the one set by setRestriction  
test that you can't set restrictions or exceptions outside of the matrix borders  

PatternDeck:  
test that the patterndeck initial dimension is 24  
test that you can't access a position given an index bigger than the dimension  
test that you can't access a negative index  
test that the removePatternCard actually removes a patterncard from the deck(size = \old(size)-1)  

Player:  
test that the numOfTokens are the same set by setNumOfTokens  
test that you can't set a negative numOfTOkens
test that the constructor work as expected (all the attributes have the expected value)
test that the attribute "name" is handled properly
test that the attribute "numOfTokens" is handled properly

PrivateObjectiveCard:  
test that the points relative to the private objective are the sum of the faces of the card's color  

PrivateObjectiveDeck:  
test that the patternDeck initial dimension is 5  
test that you can't access a position given an index bigger than the dimension  
test that you can't access a position given a negative index  
test that the removePrivateObjectiveCard actually removes a patterncard from the deck(size = \old(size)-1)  
test that the getPrivateObjectiveCard returns the selected card  

Round:  
test that the method twoTurnsInARow correctly shift the selected player turns without changing the rest of the sequence  
test that calling the method to the last player, after it's been called to the remaining players, doesn't have any effect  

WindowFrame:  
test setDice's functionality in the basic case  
test that you can't insert a dice outside of the matrix  
test that you can't call setDice with negative parameters  
test the correct management of null parameters of setDice  
test that you can't insert a dice in a position in which there is already a dice  
test that getDice returns the selected dice  
test that you can't call getDice with negative parameters  
test that you can't get a dice given parameters outside of the matrix  
test that you get null when you call getDice in a position in which there is not a dice   
test the correct management of null parameters of getDice  
test that a dice is actually removed from the window when you call removeDice  
test that an exception is called when you try to remove a dice in a position in which there isn't one  
test that you can't remove a dice given parameters outside of the matrix  
test the correct management of null parameters of removeDice  
test hasNeighbours parameters  
test hasNeighbours functionality in the normal case  
test hasNeighbours in the case with neighbours  
test hasNeighbours in the case with no neighbours  
test checkNeighboursRestriction parameters  
test checkNeighboursRestriction functionality in the normal case  
test checkNeighboursRestriction in case: valid neighbours  
test checkNeighboursRestriction in case: neighbours of the same color  
test checkNeighboursRestriction in case: neighbours of the same face  
test checkNeighboursRestriction in case: no neighbours  
test that you can't insert a dice outside of the matrix's borders in the first move(color and face restriction still considered)  
test complete filling of an example patterncard (Sun Catcher)  
test the correct behaviour of an insertion with exceptions on the position/restriction actived by a toolcard (limit cases)  





   

