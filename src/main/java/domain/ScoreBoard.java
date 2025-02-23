package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;

@AllArgsConstructor
@Getter
public class ScoreBoard {

    private LinkedHashMap<String, Game> games;
}
