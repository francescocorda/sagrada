CLIENT -> SERVER  
  
Communication:
 * pong
  
Login:  
 * login/"username"/"password"

Lobby:  
 * lobby/last_access/"time"
  
Game:
 * move
 * toolcard
 * skip
 * logout
 * join
 * "message"

SERVER -> CLIENT  
  
Communication:
 * ping  
  
Login:  
 * login/insert_credentials
 * login/success
 * login/failed
 * login/invalid_command
  
Lobby:
 * lobby/invalid_command
 * lobby/welcome
 * lobby/last_access/invalid_time
 * lobby/last_access/insert_last_access
 * lobby/timer_reset
 * lobby/timer_started
 * lobby/list_of_players/"username"…/"username"
 * lobby/player_joined/"username"
 * lobby/player_left/"username"
 * lobby/start_game/list_of_players/"username"…/"username"
  
Game:  
 * game/private_objective_card/"private_objective_json"
 * game/pattern_card/"pattern_card_json"
 * game/update/"table_json"
 * game/active_table_element/"activeElement"
 * game/displayGame/"table_json"
 * "username"'s turn: "actionPerformed"
 * Choose [play] to play again, [logout] to go back to login
 * You Won!
 * New Turn.
 * Wait your turn.
 * You left the game. Choose join to get back.
 * It's your turn! Choose Action: move, toolcard, skip.
 * Choose the tool card to use (1-2-3).
 * Play game.
 * Pattern card assigned.
 * Choose the pattern card to use (1-2-3-4).
 * Invalid move by player 1: \n"errorMessage".
 * "username" joined the game.
 * back_to_game
 * Game joined.

JSON LEGEND
  
private_objective_json: (example) {"color":"YELLOW","description":"Sum values of allyellow dices","name":"Yellow shades","ID":3}
pattern_card_json: (example) {"difficulty":5,"patternCard":[[{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"PURPLE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"TWO","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"PURPLE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"SIX","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"PURPLE","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"ONE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FOUR","exceptionRestriction":false,"exceptionPosition":false}]],"name":"Aurorae Magnificus","ID":3}
table_json: (example) {"players":[{"name":"1","numOfTokens":5,"score":0,"privateObjectiveCard":{"color":"YELLOW","description":"Sum values of allyellow dices","name":"Yellow shades","ID":3},"windowFrame":{"dices":[[null,{"color":"PURPLE","face":"⚃"},null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null]],"patternCard":{"difficulty":5,"patternCard":[[{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"TWO","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"SIX","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FOUR","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"ONE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"TWO","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"THREE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false}]],"name":"Comitas","ID":22}}},{"name":"2","numOfTokens":3,"score":0,"privateObjectiveCard":{"color":"BLUE","description":"Sum values of allblue dices","name":"Blue shades","ID":4},"windowFrame":{"dices":[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null]],"patternCard":{"difficulty":3,"patternCard":[[{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"SIX","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"YELLOW","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"THREE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"SIX","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"TWO","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FOUR","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"ONE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false}]],"name":"Bellesguard","ID":6}}},{"name":"3","numOfTokens":6,"score":0,"privateObjectiveCard":{"color":"PURPLE","description":"Sum values of allpurple dices","name":"Purple shades","ID":5},"windowFrame":{"dices":[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null]],"patternCard":{"difficulty":6,"patternCard":[[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"ONE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"ONE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"THREE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"TWO","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FOUR","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"SIX","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false}],[{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"BLUE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"FIVE","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"GREEN","exceptionRestriction":false,"exceptionPosition":false},{"restriction":"WHITE","exceptionRestriction":false,"exceptionPosition":false}]],"name":"Lux Mundi","ID":21}}}],"roundTrack":{"roundTrack":[[],[],[],[],[],[],[],[],[],[]]},"diceBag":{"dices":[{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"BLUE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"BLUE"},{"color":"PURPLE"},{"color":"RED"},{"color":"GREEN"},{"color":"YELLOW"},{"color":"PURPLE"}]},"draftPool":[{"color":"BLUE","face":"⚃"},{"color":"BLUE","face":"⚁"},{"color":"YELLOW","face":"⚃"},{"color":"PURPLE","face":"⚂"},{"color":"RED","face":"⚀"},{"color":"PURPLE","face":"⚂"}],"gamePOC":[{"points":4,"description":"Columns with no repeated values","name":"Column Shade Variety","ID":4},{"points":5,"description":"Sets of one of each value anywhere","name":"Shade Variety","ID":8},{"points":2,"description":"Sets of 5 \u0026 6 values anywhere","name":"Deep Shades","ID":7}],"gameToolCards":[{"ID":5,"name":"Lens Cutter","description":"After drafting,\nswap the drafted\ndie with a die from the\nRound Track","numOfTokens":0,"price":1},{"ID":11,"name":"Flux Remover","description":"After drafting, return the die to the\nDice Bag and pull 1 die from the bag.\nChoose a value and place the new die,\nobeying all placement restrictions.","numOfTokens":0,"price":1},{"ID":6,"name":"Flux Brush","description":"After drafting,\nre-roll the drafted die.\nIf it cannot be placed,\nreturn it to the Draft Pool","numOfTokens":0,"price":1}],"scoreTrack":{"scores":[]}}
