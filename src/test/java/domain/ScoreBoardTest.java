package domain;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardTest {

    @Test
    void createScoreBoard_scoreBoardCreatedSuccessfully() {
        Team homeTeam = new Team(0, "Mexico");
        Team awayTeam = new Team(0, "Canada");
        Game game = new Game(homeTeam, awayTeam);
        LinkedHashMap<String, Game> games = new LinkedHashMap<String, Game>();
        games.put(game.getHomeTeam().getName() + game.getAwayTeam().getName(), game);
        ScoreBoard scoreBoard = new ScoreBoard(games);

        assertEquals("ScoreBoard", scoreBoard.getClass().getSimpleName());
        assertTrue(scoreBoard.getGames().contains(game));
        assertEquals(1, scoreBoard.getGames().size());
    }
}