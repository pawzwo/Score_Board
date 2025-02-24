package service;

import domain.EventType;
import domain.Game;
import domain.ScoreBoard;
import domain.Team;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventService {

    private final ScoreBoardService scoreBoardService;

    private void selectActionOnEvent(ScoreBoard scoreBoard, Game game, EventType eventType) {
        switch (eventType) {
            case START_OR_UPDATE:
                scoreBoardService.startGameUpdateScore(scoreBoard, game);
                break;
            case FINISH:
                scoreBoardService.finishGame(scoreBoard, game);
                break;
        }
    }

    public void processEvent(ScoreBoard scoreBoard, String homeTeamName, String awayTeamName, int scoreHomeTeam, int scoreAwayTeam, EventType eventType) {
        Team homeTeam = new Team(scoreHomeTeam, homeTeamName);
        Team awayTeam = new Team(scoreAwayTeam, awayTeamName);
        Game game = new Game(homeTeam, awayTeam);
        selectActionOnEvent(scoreBoard, game, eventType);
    }

    public void processEvent(ScoreBoard scoreBoard, String homeTeamName, String awayTeamName, int scoreHomeTeam, int scoreAwayTeam) {
        Team homeTeam = new Team(scoreHomeTeam, homeTeamName);
        Team awayTeam = new Team(scoreAwayTeam, awayTeamName);
        Game game = new Game(homeTeam, awayTeam);
        String gameKey = homeTeamName + awayTeamName;

        boolean isGameOver = scoreBoard.getGames().containsKey(gameKey) && scoreBoard.getGames().get(gameKey).equals(game);
        if (isGameOver) {
            selectActionOnEvent(scoreBoard, game, EventType.FINISH);
        } else {
            selectActionOnEvent(scoreBoard, game, EventType.START_OR_UPDATE);
        }
    }

    public void readEvent(ScoreBoard scoreBoard, String event) {
        String[] eventChars = event.split(",");
        if (eventChars.length == 4) {
            processEvent(scoreBoard, eventChars[0], eventChars[1], Integer.parseInt(eventChars[2]), Integer.parseInt(eventChars[3]));
        } else if (eventChars.length == 5) {
            processEvent(scoreBoard, eventChars[0], eventChars[1], Integer.parseInt(eventChars[2]), Integer.parseInt(eventChars[3]), assignEventType(eventChars[4]));
        }
    }

    private EventType assignEventType(String eventType) {
        switch (eventType) {
            case "START":
            case "UPDATE":
                return EventType.START_OR_UPDATE;
            default:
                return EventType.FINISH;
        }
    }
}
