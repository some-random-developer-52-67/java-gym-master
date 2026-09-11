package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.gym.Age.ADULT;
import static ru.yandex.practicum.gym.Age.CHILD;
import static ru.yandex.practicum.gym.DayOfWeek.FRIDAY;
import static ru.yandex.practicum.gym.DayOfWeek.MONDAY;
import static ru.yandex.practicum.gym.DayOfWeek.SATURDAY;
import static ru.yandex.practicum.gym.DayOfWeek.SUNDAY;
import static ru.yandex.practicum.gym.DayOfWeek.THURSDAY;
import static ru.yandex.practicum.gym.DayOfWeek.TUESDAY;
import static ru.yandex.practicum.gym.DayOfWeek.WEDNESDAY;

public class TimetableTest {

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        assertEquals(0, timetable.getTrainingSessionsForDay(MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(TUESDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(WEDNESDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(THURSDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(FRIDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(SATURDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(SUNDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(
                group,
                coach,
                MONDAY,
                new TimeOfDay(13, 0)
        );


        timetable.addNewTrainingSession(singleTrainingSession);


        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(MONDAY).size());

        //Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coach,
                THURSDAY,
                new TimeOfDay(20, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                MONDAY,
                new TimeOfDay(13, 0)
        );
        TrainingSession thursdayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                THURSDAY,
                new TimeOfDay(13, 0)
        );
        TrainingSession saturdayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0)
        );


        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);


        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertEquals(2, timetable.getTrainingSessionsForDay(THURSDAY).size());
        assertAll(
                () -> assertEquals(
                        timetable.getTrainingSessionsForDay(THURSDAY)
                                .firstEntry()
                                .getValue(),

                        timetable.getTrainingSessionsForDayAndTime(
                                THURSDAY,
                                new TimeOfDay(13, 0)
                        )
                ),
                () -> assertEquals(
                        timetable.getTrainingSessionsForDay(THURSDAY)
                                .higherEntry(new TimeOfDay(13, 0))
                                .getValue(),

                        timetable.getTrainingSessionsForDayAndTime(
                                THURSDAY,
                                new TimeOfDay(20, 0)
                        )
                )
        );


        // Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group,
                coach,
                MONDAY,
                new TimeOfDay(13, 0)
        );


        timetable.addNewTrainingSession(singleTrainingSession);


        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(
                1,
                timetable.getTrainingSessionsForDay(MONDAY).size()
        );
        assertEquals(
                1,
                timetable.getTrainingSessionsForDayAndTime(
                        MONDAY,
                        new TimeOfDay(13, 0)
                ).size()
        );

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(
                0,
                timetable.getTrainingSessionsForDayAndTime(
                        MONDAY,
                        new TimeOfDay(14, 0)
                ).size()
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();

        TimeOfDay time = new TimeOfDay(13, 0);

        Coach coach1 = new Coach("Иванов", "Иван", "Иваныч");
        TrainingSession trainingSession1 = new TrainingSession(
                new Group("Физ-ра", CHILD, 60),
                coach1,
                MONDAY,
                time
        );

        Coach coach2 = new Coach("Ильин", "Илья", "Ильич");
        TrainingSession trainingSession2 = new TrainingSession(
                new Group("Футбол", ADULT, 60),
                coach2,
                MONDAY,
                time
        );


        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);


        assertEquals(1, timetable.getTrainingSessionsForDay(MONDAY).size());
        assertEquals(
                2,
                timetable.getTrainingSessionsForDayAndTime(MONDAY, time).size()
        );
    }

    @Test
    void testAddingSameTrainingSessionTwice() {
        Timetable timetable = new Timetable();

        TrainingSession trainingSession = new TrainingSession(
                new Group("Физ-ра", CHILD, 60),
                new Coach("Иванов", "Иван", "Иваныч"),
                MONDAY,
                new TimeOfDay(13, 0)
        );


        timetable.addNewTrainingSession(trainingSession);
        timetable.addNewTrainingSession(trainingSession);


        assertEquals(1, timetable.getTrainingSessionsForDay(MONDAY).size());
        assertEquals(
                1,
                timetable.getTrainingSessionsForDayAndTime(
                        MONDAY,
                        new TimeOfDay(13, 0)
                ).size()
        );
        assertEquals(
                trainingSession,
                timetable.getTrainingSessionsForDayAndTime(
                        MONDAY,
                        new TimeOfDay(13, 0)
                ).iterator().next()
        );
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Timetable timetable = new Timetable();

        assertEquals(0, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesForOneCoach() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Иван", "Иваныч");

        Group group1 = new Group("Физ-ра", CHILD, 60);
        TrainingSession firstTrainingSession = new TrainingSession(
                group1,
                coach,
                MONDAY,
                new TimeOfDay(10, 0)
        );
        Group group2 = new Group("Футбол", ADULT, 90);
        TrainingSession secondTrainingSession = new TrainingSession(
                group2,
                coach,
                TUESDAY,
                new TimeOfDay(11, 0)
        );
        Group group3 = new Group("Плавание", ADULT, 60);
        TrainingSession thirdTrainingSession = new TrainingSession(
                group3,
                coach,
                WEDNESDAY,
                new TimeOfDay(12, 0)
        );


        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);


        assertEquals(1, timetable.getCountByCoaches().size());
        assertEquals(3, timetable.getCountByCoaches().get(coach));
    }

    @Test
    void testGetCountByCoachesSortsByCountDescending() {
        Timetable timetable = new Timetable();

        Coach coachWithThreeSessions = new Coach("Иванов", "Иван", "Иваныч");
        Coach coachWithTwoSessions = new Coach("Петров", "Пётр", "Петрович");
        Coach coachWithOneSession = new Coach("Сидоров", "Сидор", "Сидорович");

        Group group1 = new Group("Группа 1", CHILD, 60);
        TrainingSession trainingSession1 = new TrainingSession(
                group1,
                coachWithThreeSessions,
                MONDAY,
                new TimeOfDay(10, 0)
        );
        Group group2 = new Group("Группа 2", CHILD, 60);
        TrainingSession trainingSession2 = new TrainingSession(
                group2,
                coachWithThreeSessions,
                TUESDAY,
                new TimeOfDay(10, 0)
        );
        Group group3 = new Group("Группа 3", ADULT, 90);
        TrainingSession trainingSession3 = new TrainingSession(
                group3,
                coachWithThreeSessions,
                WEDNESDAY,
                new TimeOfDay(10, 0)
        );
        Group group4 = new Group("Группа 4", CHILD, 60);
        TrainingSession trainingSession4 = new TrainingSession(
                group4,
                coachWithTwoSessions,
                THURSDAY,
                new TimeOfDay(10, 0)
        );
        Group group5 = new Group("Группа 5", ADULT, 90);
        TrainingSession trainingSession5 = new TrainingSession(
                group5,
                coachWithTwoSessions,
                FRIDAY,
                new TimeOfDay(10, 0)
        );
        Group group6 = new Group("Группа 6", CHILD, 60);
        TrainingSession trainingSession6 = new TrainingSession(
                group6,
                coachWithOneSession,
                SATURDAY,
                new TimeOfDay(10, 0)
        );


        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);


        var result = timetable.getCountByCoaches();
        List<Coach> coachesInOrder = new ArrayList<>(result.keySet());

        assertEquals(
                List.of(coachWithThreeSessions, coachWithTwoSessions, coachWithOneSession),
                coachesInOrder
        );
        assertEquals(List.of(3, 2, 1), new ArrayList<>(result.values()));
    }

}
