package service;

import domain.Game;
import domain.ScoreBoard;
import domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreBoardServiceTest {

    private Game game1;
    private Game game2;
    private final ScoreBoardService scoreBoardService = new ScoreBoardService();


    @BeforeEach
    void setUp() {
        Team homeTeam1 = new Team(0, "Mexico");
        Team awayTeam1 = new Team(0, "Canada");
        game1 = new Game(homeTeam1, awayTeam1);

        Team homeTeam2 = new Team(0, "France");
        Team awayTeam2 = new Team(0, "Italy");
        game2 = new Game(homeTeam2, awayTeam2);
    }

    @Test
    void startGame_startTwoGames_GamesStartedSuccessfully() {
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());
        scoreBoardService.startGame(scoreBoard, game1);
        scoreBoardService.startGame(scoreBoard, game2);

        assertEquals(2, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsValue(game1) && scoreBoard.getGames().containsValue(game2));
    }
}
