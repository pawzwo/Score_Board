package data;

import domain.Game;
import domain.Team;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class GamesData {

    public static Stream<Arguments> generateTwoGames() {
        Team homeTeam1 = new Team(0, "Mexico");
        Team awayTeam1 = new Team(0, "Canada");
        Game game1 = new Game(homeTeam1, awayTeam1);

        Team homeTeam2 = new Team(0, "France");
        Team awayTeam2 = new Team(0, "Italy");
        Game game2 = new Game(homeTeam2, awayTeam2);
        return Stream.of(Arguments.of(game1, game2));
    }

}
