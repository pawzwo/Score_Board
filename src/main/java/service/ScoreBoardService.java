package service;

import domain.Game;
import domain.ScoreBoard;

public class ScoreBoardService {

    public void startGameUpdateScore(ScoreBoard scoreBoard, Game game) {
        String key = game.getHomeTeam().getName() + game.getAwayTeam().getName();
        scoreBoard.getGames().put(key, game);
    }

    public void finishGame(ScoreBoard scoreBoard, Game game) {
        String key = game.getHomeTeam().getName() + game.getAwayTeam().getName();
        scoreBoard.getGames().remove(key);
    }
}
