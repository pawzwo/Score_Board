package service;

import domain.Game;
import domain.ScoreBoard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceTest {

    private final ScoreBoardService scoreBoardService = new ScoreBoardService();


    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void startGame_startTwoGames_GamesStartedSuccessfully(Game game1, Game game2) {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGameUpdateScore(scoreBoard, game1);
        scoreBoardService.startGameUpdateScore(scoreBoard, game2);

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void finishGame_startTwoGamesThenFinishOne_GameFinishedSuccessfully(Game game1, Game game2) {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGameUpdateScore(scoreBoard, game1);
        scoreBoardService.startGameUpdateScore(scoreBoard, game2);
        scoreBoardService.finishGame(scoreBoard, game1);

        assertEquals(1, scoreBoard.getGames().size());
        assertTrue(!scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateAndUpdateScoreTwoGames")
    void updateScore_startTwoGamesUpdateScoreInBoth_ScoreUpdatedSuccessfully(Game game1, Game game2) {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGameUpdateScore(scoreBoard, game1);
        scoreBoardService.startGameUpdateScore(scoreBoard, game2);

        String game1key = game1.getHomeTeam().getName()+game1.getAwayTeam().getName();
        String game2key = game2.getHomeTeam().getName()+game2.getAwayTeam().getName();
        int game1homeTeamScoreData = game1.getHomeTeam().getScore();
        int game1awayTeamScoreData = game1.getAwayTeam().getScore();
        int game2homeTeamScoreData = game2.getHomeTeam().getScore();
        int game2awayTeamScoreData = game2.getAwayTeam().getScore();
        int game1homeTeamScoreScoreBoard = scoreBoard.getGames().get(game1key).getHomeTeam().getScore();
        int game1awayTeamScoreScoreBoard = scoreBoard.getGames().get(game1key).getAwayTeam().getScore();
        int game2homeTeamScoreScoreBoard = scoreBoard.getGames().get(game2key).getHomeTeam().getScore();
        int game2awayTeamScoreScoreBoard = scoreBoard.getGames().get(game2key).getAwayTeam().getScore();

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
        assertEquals(game1homeTeamScoreData, game1homeTeamScoreScoreBoard);
        assertEquals(game1awayTeamScoreData, game1awayTeamScoreScoreBoard);
        assertEquals(game2homeTeamScoreData, game2homeTeamScoreScoreBoard);
        assertEquals(game2awayTeamScoreData, game2awayTeamScoreScoreBoard);
    }
}
