package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {

    private Team homeTeam;
    private Team awayTeam;
}
