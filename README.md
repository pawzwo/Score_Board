Name: Score Board
Author: Paweł Zwoliński

The implentation of Score Board was built with and assumption that game data will be fed as events containing name of home team, name of away team, score of home team, score of away team and optionally event typ (START, UPDATE, FINISH). 
If event type is not provided (as it was not stated in the coding exercise description), than data can be processed but with assumption that first event of the game with speciffic home team and away team names will mark start of the game, 
next events with the same names of the teams and score different from previous event will mark update of the score, while event with those names and the same score will mean that the game has ended. 

Event data is fed to the Score Board in a class Driver as a List of Strings mimicking lines of csv file. If required the application could be easily developed by adding interface with implementations (EventService) to support other formats like json.
Events are processed by a EventService (validation of data, building of Game objects and selecting of proper action), ScoreBoard operations (start, update and finish) are handled by ScoreBoardService. In order to keep things simple and efficient
the ScoreBoard object holds the game data in LinkedHashMap<Game>. The summary report is generated via GameSummaryService as a GameSummary object with a TreeSet to allow for ordering by total score and time of adding to the system. 

If any questions arise feel free to give me a call or send an email with contact details provided in my resume. 
