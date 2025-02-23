package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void createGame_gameCreatedSuccessfully() {
        Team homeTeam = new Team(0, "Mexico");
        Team awayTeam = new Team(0, "Canada");
        Game game = new Game(homeTeam, awayTeam);

        assertEquals("Game", game.getClass().getSimpleName());
        assertEquals("Mexico", game.getHomeTeam().getName());
        assertEquals("Canada", game.getAwayTeam().getName());
    }
}