package service;

import domain.EventType;
import domain.Game;
import domain.ScoreBoard;
import exception.CorruptedEventDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    private ScoreBoard scoreBoard;

    @Mock
    private ScoreBoardService scoreBoardService;
    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard(new LinkedHashMap<>());
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoStartEventsWithEventTypeProvided")
    void processEvent_twoStartEventsWithEventTypesStartProvided_EventProcessedGamesStartedSuccessfully(String homeTeamName, String awayTeamName, EventType eventType) {
        eventService.processEvent(scoreBoard, homeTeamName, awayTeamName, 0, 0, eventType);

        verify(scoreBoardService).startGameUpdateScore(any(ScoreBoard.class), any(Game.class));
    }

    @ParameterizedTest
    @MethodSource("data.GamesData#generateTwoStartEventsWithoutEventTypeProvided")
    void processEvent_twoStartEventsWithoutEventTypeProvided_EventProcessedGamesStartedSuccessfully(String homeTeamName, String awayTeamName) {
        eventService.processEvent(scoreBoard, homeTeamName, awayTeamName, 0, 0);

        verify(scoreBoardService).startGameUpdateScore(any(ScoreBoard.class), any(Game.class));
    }

    @Test
    void processEvent_twoStartEventsOneFinishEventWithEventTypeProvided_EventProcessedGamesStartedAndFinishedSuccessfully() {
        eventService.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventService.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.FINISH);

        verify(scoreBoardService, times(2)).startGameUpdateScore(any(ScoreBoard.class), any(Game.class));
        verify(scoreBoardService, times(1)).finishGame(any(ScoreBoard.class), any(Game.class));
    }

    @Test
    void processEvent_twoStartEventsOneUpdateWithoutEventTypeProvided_EventProcessedGamesStartedAndFinishedSuccessfully() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);
        eventServiceInst.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventServiceInst.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventServiceInst.processEvent(scoreBoard, "Spain", "Brazil", 0, 1);

        assertEquals(2, scoreBoard.getGames().size());
        assertEquals(1, scoreBoard.getGames().get("SpainBrazil").getAwayTeam().getScore());
    }

    @Test
    void processEvent_twoStartEventsOneUpdateOneFinishEventWithoutEventTypeProvided_EventProcessedGamesStartedAndFinishedSuccessfully() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);
        eventServiceInst.processEvent(scoreBoard, "Mexico", "Canada", 0, 0, EventType.START_OR_UPDATE);
        eventServiceInst.processEvent(scoreBoard, "Spain", "Brazil", 0, 0, EventType.START_OR_UPDATE);
        eventServiceInst.processEvent(scoreBoard, "Spain", "Brazil", 0, 1);
        eventServiceInst.processEvent(scoreBoard, "Spain", "Brazil", 0, 1);

        assertEquals(1, scoreBoard.getGames().size());
    }

    @Test
    void readEvent_twoStartEventsOneUpdateOneFinishEventWithoutEventTypeProvided_EventProcessedGamesStartedAndFinishedSuccessfully() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);
        eventServiceInst.readEvent(scoreBoard, "Mexico,Canada,0,0,START");
        eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,START");
        eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,1");
        eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,1");

        assertEquals(1, scoreBoard.getGames().size());
        assertTrue(scoreBoard.getGames().containsKey("MexicoCanada"));
        assertEquals(0, scoreBoard.getGames().get("MexicoCanada").getHomeTeam().getScore());
        assertEquals(0, scoreBoard.getGames().get("MexicoCanada").getAwayTeam().getScore());
    }

    @Test
    void readEventValidateData_eventDataCorrupted_ExceptionThrown() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);

        Exception exception = assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Mexico,Canada,0"));
        assertEquals("CorruptedEventDataException: The event data is corrupted, event skipped", exception.getMessage());
        assertDoesNotThrow(() -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,START"));
        assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,a,1"));
        assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, " ,Brazil,0,0,START"));
        assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,b"));
        assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,BLA"));
    }

    @Test
    void validateEvent_eventStartedOneCorrectUpdateEventOneUpdateEventWithCorruptedScore_ExceptionThrown() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);

        assertDoesNotThrow(() -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,START"));
        assertDoesNotThrow(() -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,1,UPDATE"));
        Exception exception = assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,UPDATE"));
        assertEquals("CorruptedEventDataException: The event data is corrupted, event skipped", exception.getMessage());
    }

    @Test
    void validateEvent_eventStartedOneCorrectUpdateEventOneUpdateEventWithCorruptedEventType_ExceptionThrown() {
        ScoreBoardService scoreBoardServiceInst = new ScoreBoardService();
        EventService eventServiceInst = new EventService(scoreBoardServiceInst);

        assertDoesNotThrow(() -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,START"));
        assertDoesNotThrow(() -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,1,UPDATE"));
        Exception exception = assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,1,START"));
        assertEquals("CorruptedEventDataException: The event data is corrupted, event skipped", exception.getMessage());
        assertThrows(CorruptedEventDataException.class, () -> eventServiceInst.readEvent(scoreBoard, "Spain,Brazil,0,0,UPDATE"));
    }
}
