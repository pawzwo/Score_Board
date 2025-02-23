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

        Team homeTeam2 = new Team(0, "Spain");
        Team awayTeam2 = new Team(0, "Brazil");
        Game game2 = new Game(homeTeam2, awayTeam2);
        return Stream.of(Arguments.of(game1, game2));
    }

    public static Stream<Arguments> generateAndUpdateScoreTwoGames() {
        Team homeTeam1 = new Team(0, "Mexico");
        Team awayTeam1 = new Team(0, "Canada");
        Game game1 = new Game(homeTeam1, awayTeam1);

        Team homeTeam2 = new Team(0, "Spain");
        Team awayTeam2 = new Team(0, "Brazil");
        Game game2 = new Game(homeTeam2, awayTeam2);

        Team homeTeam3 = new Team(0, "Mexico");
        Team awayTeam3 = new Team(1, "Canada");
        Game game3 = new Game(homeTeam3, awayTeam3);

        Team homeTeam4 = new Team(0, "Spain");
        Team awayTeam4 = new Team(1, "Brazil");
        Game game4 = new Game(homeTeam4, awayTeam4);
        return Stream.of(Arguments.of(game1, game2), Arguments.of(game3, game4));
    }


}
