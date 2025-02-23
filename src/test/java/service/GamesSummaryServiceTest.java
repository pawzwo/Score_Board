package service;

import domain.EventType;
import domain.Game;
import domain.ScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamesSummaryServiceTest {

    private final ScoreBoardService scoreBoardService = new ScoreBoardService();
    private final EventService eventService = new EventService(scoreBoardService);
    private final GamesSummaryService gamesSummaryService = new GamesSummaryService();
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard(new LinkedHashMap<>());
    }


    @Test
    void getSummary_startThreeGamesUpdateScoreInTwoDifferentTotalScoreGetSummary_SummaryDeliveredSuccessfully() {
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 1, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 1, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Germany", "France", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 1, 1, EventType.START_OR_UPDATE);

        GamesSummary gamesSummary = gamesSummaryService.generateSummary(scoreBoard);
        TreeSet<Game> gamesReport = gamesSummary.getGames();
        LinkedHashSet<Game> gamesBoard = new LinkedHashSet<>(scoreBoard.getGames().values());
        List<Integer> totalScores = gamesReport.stream()
                .map(game -> game.getHomeTeam().getScore() + game.getAwayTeam().getScore())
                .collect(Collectors.toList());

        assertEquals(gamesBoard.size(), gamesReport.size());
        assertTrue(gamesReport.containsAll(gamesBoard));
        assertEquals(List.of(2, 1, 0), totalScores);
    }

    @Test
    void getSummary_startThreeGamesUpdateScoreInAllTheSameTotalScoreGetSummary_SummaryDeliveredSuccessfully() {
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 1, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 1, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Germany", "France", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Germany", "France", 0, 1, EventType.START_OR_UPDATE);

        GamesSummary gamesSummary = gamesSummaryService.generateSummary(scoreBoard);
        TreeSet<Game> gamesReport = gamesSummary.getGames();
        LinkedHashSet<Game> gamesBoard = new LinkedHashSet<>(scoreBoard.getGames().values());
        List<Integer> totalScores = gamesReport.stream()
                .map(game -> game.getHomeTeam().getScore() + game.getAwayTeam().getScore())
                .collect(Collectors.toList());

        assertEquals(gamesBoard.size(), gamesReport.size());
        assertTrue(gamesReport.containsAll(gamesBoard));
        assertEquals(List.of(1, 1, 1), totalScores);
    }
}
