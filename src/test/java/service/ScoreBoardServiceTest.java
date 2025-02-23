package service;

import domain.EventType;
import domain.Game;
import domain.ScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceTest {

    private final ScoreBoardService scoreBoardService = new ScoreBoardService();
    private final EventService eventService = new EventService(scoreBoardService);
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard(new LinkedHashMap<>());
    }


    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void startGame_startTwoGames_GamesStartedSuccessfully(Game game1, Game game2) {

        scoreBoardService.startGameUpdateScore(scoreBoard, game1);
        scoreBoardService.startGameUpdateScore(scoreBoard, game2);

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoGames")
    void finishGame_startTwoGamesThenFinishOne_GameFinishedSuccessfully(Game game1, Game game2) {
        scoreBoardService.startGameUpdateScore(scoreBoard, game1);
        scoreBoardService.startGameUpdateScore(scoreBoard, game2);
        scoreBoardService.finishGame(scoreBoard, game1);

        assertEquals(1, scoreBoard.getGames().size());
        assertTrue(!scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }

    @Test
    void updateScore_startTwoGamesUpdateScoreInBoth_ScoreUpdatedSuccessfully() {
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 1, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 1, EventType.START_OR_UPDATE);

        String game1key = "MexicoCanada";
        String game2key = "SpainBrazil";
        int game1homeTeamScoreScoreBoard = scoreBoard.getGames().get(game1key).getHomeTeam().getScore();
        int game1awayTeamScoreScoreBoard = scoreBoard.getGames().get(game1key).getAwayTeam().getScore();
        int game2homeTeamScoreScoreBoard = scoreBoard.getGames().get(game2key).getHomeTeam().getScore();
        int game2awayTeamScoreScoreBoard = scoreBoard.getGames().get(game2key).getAwayTeam().getScore();

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsKey(game1key) && scoreBoard.getGames().containsKey(game2key));
        assertEquals(0, game1homeTeamScoreScoreBoard);
        assertEquals(1, game1awayTeamScoreScoreBoard);
        assertEquals(0, game2homeTeamScoreScoreBoard);
        assertEquals(1, game2awayTeamScoreScoreBoard);
    }
}
