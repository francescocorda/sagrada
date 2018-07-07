In this document are listed all the tests we made to be sure the model's behavior was right.
Our tests are divided into two sections: tests of the cards and tests of the game.
The goal of our test is to make sure that, after every modifications in the model's code, the behaviour of all model's
items remained the expected one.
We launched all tests after every little modifications, before committing it on GitHub.
One by one, following there's the description of all our tests.

 _________________
|                 |
|  cards section  |
|_________________|
* test that the setId method and setName method of card interface works properly

-PatternCard- the following tests have been created to be sure patternCard behavior is always right.

PatternCard:
* test that the the difficulty is the one set by setDifficulty
* test that the difficulty is not negative
* test that the difficulty is between 3 and 6
* test that the restriction (Color or Face) is the one set by setRestriction
* test that you can't set restrictions or exceptions outside of the matrix borders

PatternDeck:
* test that the patternDeck initial dimension is 24
* test that you can't access a position given an index bigger than the dimension
* test that you can't access a negative index
* test that the removePatternCard actually removes a patternCard from the deck  @ensures(size = \old(size-1))

-Private Objective Card- the following tests have been created to be sure PrivateObjectiveCard behavior is always right.

PrivateObjectiveCard:
* test that the points relative to the private objective are the sum of the faces of the card's color

PrivateObjectiveDeck:
* test that the patternDeck initial dimension is 5
* test that you can't access a position given an index bigger than the dimension
* test that you can't access a position given a negative index
* test that the removePrivateObjectiveCard actually removes a patternCard from the deck  @ensures(size = \old(size-1))
* test that the getPrivateObjectiveCard returns the selected card

-Public Objective Card- the following tests have been created to be sure PublicObjectiveCard behavior is always right.

PublicObjectiveDeck:
* test that the the initial size of the deck is 5 and cannot access to position smaller than 0 and bigger than 4
* test that the ID of the card in position of the deck is 5
* test that by removing one card by the deck, the initial size decreases by one

-ToolCard- the following tests have been created to be sure ToolCard behavior is always right.

* test that toolCard ONE properly transforms the face of the selected dice(increasing or decreasing by one)
* test that using toolCard TWO is possible to place a dice in a cell of the WindowFrame ignoring color's restrictions
* test that using toolCard THREE is possible to place a dice in a cell of the WindowFrame ignoring numbers' restrictions
    (shadows' restriction)
* test that using toolCard FOUR is possible to move exactly two dices, respecting all the restrictions
* test that using toolCard FIVE is possible to exchange a dice from the draftPool with a dice of the roundTrack
* test that using toolCard SIX the selected dice from the draftPool is correctly rerolled and placed in the windowFrame
    respecting all the restrictions
* test that using toolCard SEVEN is possible to reroll all the draftPool's dices ONLY if this tool is used during
    the second turn, before drafting the second dice
* test that using toolCard EIGHT is possible to draft two dices during the first turn; is checked that is consequently
    forbidden drafting a dice during the second turn of the same round
* test that using toolCard NINE is possible to place a drafting dice ignoring neighbour's restrictions; It's checked that
    all the other restrictions are respected
* test that toolCard TEN allows the player to exchange the face of a drafting dice with the value of its opposite face;
    is checked that this dice is correctly placed in the draftPool
* test that using toolCard ELEVEN is possible to exchange a dice from the draftPool with a dice from the diceBag;
    this toolCard allows the player to choose the face of the new dice and is checked that it's properly placed in the
    windowFrame respecting all the restrictions.
* test that using toolCard TWELVE is possible to move up to dice of the same color of a dice of the roundTrack,
    respecting all the placement restrictions.

 ________________
|                |
|  game section  |
|________________|
Dice:
* test that a dice has the color setted in the constructor method
* test that a dice has a face value between 1 and 6
* test that can't set a dice to a face value negative or bigger than 7
* test that the method oppositeFace returns the dice's opposite face

DiceBag:  
* test that a diceBag is initialized to 90 dice
* test that when a dice is drawn from the diceBag the dimension is reduced by 1
* test that a diceBag is empty after 90 extractions
* test that a diceBag is divided in groups of 18 dices with the same color
* test that when a number of dices is drawn from the diceBag the dimension is reduced by the same number
* test that you can't draw more dices than there are in the diceBag at the moment
* test that an extracted dice isn't in the diceBag anymore

Player:  
* test that the numOfTokens are the same set by setNumOfTokens
* test that you can't set a negative numOfTokens
* test that the constructor works as expected (all the attributes have the expected value)
* test that the attribute "name" is handled properly
* test that the attribute "numOfTokens" is handled properly

Round:  
* test that the method twoTurnsInARow correctly shifts the selected player turns without changing the remaining part
   of the sequence
* test that calling the method to the last player, after it's been called to the remaining players, doesn't have any
   effect

WindowFrame:  
* test setDice's functionality in the basic case
* test that you can't insert a dice outside of the matrix
* test that you can't call setDice with negative parameters
* test the correct management of null parameters of setDice
* test that you can't insert a dice in a position in which there is already a dice
* test that getDice returns the selected dice
* test that you can't call getDice with negative parameters
* test that you can't get a dice given parameters outside of the matrix
* test that you get null when you call getDice in a position in which there is not a dice
* test the correct management of null parameters of getDice
* test that a dice is actually removed from the window when you call removeDice
* test that an exception is called when you try to remove a dice in a position in which there isn't one
* test that you can't remove a dice given parameters outside of the matrix
* test the correct management of null parameters of removeDice
* test hasNeighbours parameters
* test hasNeighbours functionality in the normal case
* test hasNeighbours in the case with neighbours
* test hasNeighbours in the case with no neighbours
* test checkNeighboursRestriction parameters
* test checkNeighboursRestriction functionality in the normal case
* test checkNeighboursRestriction in case: valid neighbours
* test checkNeighboursRestriction in case: neighbours of the same color
* test checkNeighboursRestriction in case: neighbours of the same face
* test checkNeighboursRestriction in case: no neighbours
* test that you can't insert a dice outside of the matrix's borders in the first move(color and face restriction
    still considered)
* test complete filling of an example patternCard (Sun Catcher)
* test the correct behaviour of an insertion with exceptions on the position/restriction enabled by a
    toolCard (limit cases)





   

