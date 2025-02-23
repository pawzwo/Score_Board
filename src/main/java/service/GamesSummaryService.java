package service;

import domain.Game;
import domain.GamesSummary;
import domain.ScoreBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class GamesSummaryService {

    public GamesSummary generateSummary(ScoreBoard scoreBoard) {
        List<Game> gamesList = new ArrayList<>(scoreBoard.getGames().values());
        gamesList.forEach(game -> game.setIndex(gamesList.indexOf(game)));
        TreeSet<Game> games = new TreeSet<>((g1, g2) -> {
            int totalScoreG1 = g1.getHomeTeam().getScore() + g1.getAwayTeam().getScore();
            int totalScoreG2 = g2.getHomeTeam().getScore() + g2.getAwayTeam().getScore();
            int diff = totalScoreG2 - totalScoreG1;
            return diff == 0 ? g2.getIndex() - g1.getIndex() : diff;
        });
        games.addAll(scoreBoard.getGames().values());
        return new GamesSummary(games);
    }
}
