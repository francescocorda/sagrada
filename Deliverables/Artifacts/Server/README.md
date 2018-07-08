Please read these instructions to start server.

To start server application you have to fill four different blanks.  
In the first gap you have to insert the port on which you want to listen socket connections; by leaving it blank
the socket server port will be our default port 3001.  
In the second gap you have to insert the port on which you want to listen rmi connections; by leaving it blank
the rmi server port will be our default port 1099.  
Then you have to insert the timer (seconds) after which a game start; timer starts when two players are in lobby and
when it expires a game will start. Note that a game automatically starts before timer expiration if four players  
enter in lobby. By leaving this gap blank, timer is set to 120 seconds by default.  
Finally you have to insert  the timer of the turn. This timer starts at the begin of a player turn and if the timer  
expires before the end of the turn, automatically the player will be disconnected. By leaving it blank timer is set  
to 120 seconds by default.

We implemented the ADVANCED FUNCTIONALITY  that allows us to create new patternCard and to add it on the pool from which patternCards are drafted.  
This funcionality is very simple to use, you only have to go to "resources" folder and to add a new file in the folder "patterns".  
New file must have the same json format of the already existing ones; in order to fire any doubts here's an example.

We want to add a new patternCard named "blank" withoud any restrictions, giving it the ID 25(NOTE that in the forlder cannot exist two patternCard with the same id, since it is used as key to load the card in the deck).  
Here's the content of the file you have to add:  
{  
  "name": "blank",  
  "id": "25",  
  "difficulty": "1",  
  "patternCard": [  
    "WHITE", "WHITE", "WHITE", "WHITE", "WHITE",  
    "WHITE", "WHITE", "WHITE", "WHITE", "WHITE",  
    "WHITE", "WHITE", "WHITE", "WHITE", "WHITE",  
    "WHITE", "WHITE", "WHITE", "WHITE", "WHITE"  
  ]  
}
