
6 Stone Kalah  
  
#####Build  
    To Build - gradle build         
    To Action - gradle bootRun       
    To Run Tests - gradle test      
      
####REST
#####Entry:
    http://localhost:8080  
#####Create a game:\ 
    input: POST	/games
    response:
        Status: 201
	    {"id":815,"url":"http://localhost:8080/games/815"}   
#####Play a move:
    input: PUT  /games/{gameId}/{startingPosition}
    variables:
        gameId - ID of the game that was returned from creation
        startingPosition - Pit ID (2-13) to start your move from
    response:
        /games/815/2 was used
        {"1":0,"2":0,"3":7,"4":7,"5":7,"6":7,"7":7,"8":7,"9":6,"10":6,"11":6,"12":6,"13":6,"14":0}
#####Game status:
    input: GET  /games/{gameId}
    variables:
        gameId - ID of the game that you are requesting the status for
    response:
        /games/815 was used
        {"1":0,"2":0,"3":7,"4":7,"5":7,"6":7,"7":7,"8":7,"9":6,"10":6,"11":6,"12":6,"13":6,"14":0}