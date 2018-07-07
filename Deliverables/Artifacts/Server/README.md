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
