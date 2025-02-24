package service;

import domain.EventType;
import domain.Game;
import domain.ScoreBoard;
import domain.Team;
import exception.CorruptedEventDataException;
import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

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

    public void readEvent(ScoreBoard scoreBoard, String event) throws CorruptedEventDataException {
        String[] eventArr = event.split(",");
        validateData(eventArr, scoreBoard);
        if (eventArr.length == 4) {
            processEvent(scoreBoard, eventArr[0], eventArr[1], Integer.parseInt(eventArr[2]), Integer.parseInt(eventArr[3]));
        } else if (eventArr.length == 5) {
            processEvent(scoreBoard, eventArr[0], eventArr[1], Integer.parseInt(eventArr[2]), Integer.parseInt(eventArr[3]), assignEventType(eventArr[4]));
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

    private void validateData(String[] eventDataArr, ScoreBoard scoreBoard) throws CorruptedEventDataException {
        if (eventDataArr.length < 4 || eventDataArr.length > 5) {
            throw new CorruptedEventDataException();
        }

        String homeTeamName = eventDataArr[0];
        String awayTeamName = eventDataArr[1];
        String scoreHomeTeam = eventDataArr[2];
        String scoreAwayTeam = eventDataArr[3];
        Pattern pattern = Pattern.compile("\\d{1,2}");

        if (homeTeamName.isBlank() || awayTeamName.isBlank()
                || !pattern.matcher(scoreHomeTeam).matches() || !pattern.matcher(scoreAwayTeam).matches()) {
            throw new CorruptedEventDataException();
        }

        if (eventDataArr.length == 5) {
            String eventType = eventDataArr[4];
            if (!(eventType.equals("START") || eventType.equals("UPDATE") || eventType.equals("END"))) {
                throw new CorruptedEventDataException();
            } else if ((!scoreAwayTeam.equals("0") || !scoreHomeTeam.equals("0")) && eventType.equals("START")
                    || (scoreAwayTeam.equals("0") && scoreHomeTeam.equals("0") && eventType.equals("UPDATE"))) {
                throw new CorruptedEventDataException();
            }
        }
        String gameKey = homeTeamName + awayTeamName;
        Game game = scoreBoard.getGames().get(gameKey);

        if (game != null) {
            int scoreHomeTeamGame = game.getHomeTeam().getScore();
            int scoreAwayTeamGame = game.getAwayTeam().getScore();
            if (scoreHomeTeamGame > Integer.parseInt(scoreHomeTeam) || scoreAwayTeamGame > Integer.parseInt(scoreAwayTeam)) {
                throw new CorruptedEventDataException();
            }
        }
    }
}
