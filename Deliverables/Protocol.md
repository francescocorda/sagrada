CLIENT -> SERVER  
  
communication:  
 * change_communication_mode  
 * pong  
 * quit  
  
login:  
 * login<"username"><"password">  
  
lobby:  
 * lobby<last_access><"time">  
  
game:  
 * game<start><choose_pattern_card><"pattern_id">  
 * game<move><set_dice_window><"draftpool_index"><"row"><"column">  
 * game<move><remove_dice_window><"row"><"column">  
 * game<move><roll_draftpool>  
 * game<move><use_toolcard><"toolcard_id">  
 * game<move><toolcard><insert_dice_in_dicebag><"draftpool_index">  
 * game<move><toolcard><extract_dice_from_dicebag>  
 * game<move><toolcard><swap_dice_draftpool_roundtrack><"draftpool_index"><"roundtrack_index"><"dice_index_round">  
 * game<move><toolcard><increase_dice><"draftpool_index">  
 * game<move><toolcard><decrease_dice><"draftpool_index">  
 * game<move><toolcard><roll_dice><"draftpool_index">  
 * game<move><toolcard><two_moves_in_a_row>  
 * game<move><toolcard><opposite_face_dice><"draftpool_index">  
 * game<end_turn>  


SERVER -> CLIENT  
  
communication:  
 * communication<socket>  
 * communication<rmi>  
 * ping  
  
login:  
 * login<invalid_command>  
 * login<insert_credentials>  
 * login<success>  
 * login<failed>  
  
lobby:
 * lobby<invalid_command>  
 * lobby<last_access><welcome_back>  
 * lobby<last_access><invalid_time>  
 * lobby<last_access><insert_last_access>  
 * lobby<timer><timer_started>  
 * lobby<timer><timer_restarted>  
 * lobby<list_of_players><"username">…<"username">  
 * lobby<player_joined><"username">  
 * lobby<player_left><"username">  
 * lobby<start_game>  
  
game:  
 * game<invalid_command>  
 * game<start><private_objective><"private_objective_json">  
 * game<start><public_objectives><"public_objectives_json">  
 * game<start><choose_pattern_card><"pattern_list_json">  
 * game<start><other_player_pattern><"other_player_pattern_json">  
 * game<start><tool_cards><"tool_cards_json">  
 * game<round_track><"roundtrack_json">  
 * game<draft_pool><"dice_list_json">  
 * game<player_tokens_matrix><"player_tokens_window_json">  
 * game<player_disconnection><"username">  
 * game<player_reconnection><"username">  
 * game<player_turn><"username">  
 * game<invalid_move>  
 * game<move><selected_dice_draftpool><"draftpool_index">  
 * game<move><selected_toolcard><"toolcard_id">
 * game<move><selected_dice_roundtrack><"draftpool_index">  
 * game<move><selected_dice_window><"row"><"column">  
 * game<end_turn><"username">  
 * game<endgame><winner><"username">  
 * game<endgame><scores><"usernames_and_scores_json">  


JSON LEGEND  
  
private_objective_json: { "name": " ", "description": " ", "color": " " }  
public_objectives_json: { "name": " ", "description": " ", "points": " "}  
pattern_list_json: [ { "name": " ", "difficulty": " ", "patternCard": [ " ", ... , " "] }, … , { ... }]  
other_player_pattern_json: [ { "name": " ", "difficulty": " ", "patternCard": [ " ", ... , " "] }, … , { ... }]  
tool_cards_json: [ { "name": " ", "description": " " }, … , { … }]	  
roundtrack_json: { "roundTrack": [ [ {"color": " ", "face": " "}, … ] , … , [ … ] ] }  
dice_list_json: { "draftPool": [ {"color": " ", "face": " "}, … , { … } ] }  
player_tokens_window_json: { "numOfTokens": " ",  "window" : [ { "color": " ", "face": " "}, … , { … } ] }	  
usernames_and_scores_json: { "players_scores": [ { "username": " ", "score": " "}, … , { … } ] }  
