package service;

import domain.Game;
import domain.ScoreBoard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreBoardServiceTest {

    private final ScoreBoardService scoreBoardService = new ScoreBoardService();


    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void startGame_startTwoGames_GamesStartedSuccessfully(Game game1, Game game2) {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGame(scoreBoard, game1);
        scoreBoardService.startGame(scoreBoard, game2);

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void finishGame_startTwoGamesThenFinishOne_GameFinishedSuccessfully(Game game1, Game game2) {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGame(scoreBoard, game1);
        scoreBoardService.startGame(scoreBoard, game2);
        scoreBoardService.finishGame(scoreBoard, game1);

        assertEquals(1, scoreBoard.getGames().size());
        assertTrue(!scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }
}
