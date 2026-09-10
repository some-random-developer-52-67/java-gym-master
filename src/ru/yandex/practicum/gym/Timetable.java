package ru.yandex.practicum.gym;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, Set<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), k -> new HashSet<>())
                .add(trainingSession);
    }

    public TreeMap<TimeOfDay, Set<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public Set<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new HashSet<>());
    }

    // TODO getCountByCoaches
}
