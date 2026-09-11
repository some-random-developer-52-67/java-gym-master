package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, Set<TrainingSession>>> timetable = new HashMap<>();

    private final Map<Coach, Integer> countByCoach = new HashMap<>();

    public Timetable() {
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        Set<TrainingSession> sessions = timetable
                .computeIfAbsent(
                        trainingSession.getDayOfWeek(),
                        k -> new TreeMap<>()
                )
                .computeIfAbsent(
                        trainingSession.getTimeOfDay(),
                        k -> new HashSet<>()
                );

        if (sessions.add(trainingSession)) {
            countByCoach.merge(
                    trainingSession.getCoach(),
                    1,
                    Integer::sum
            );
        }
    }

    public TreeMap<TimeOfDay, Set<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public Set<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        return timetable.getOrDefault(dayOfWeek, new TreeMap<>())
                .getOrDefault(timeOfDay, new HashSet<>());
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        List<Map.Entry<Coach, Integer>> entries = new ArrayList<>(countByCoach.entrySet());

        entries.sort(
                Map.Entry.<Coach, Integer>comparingByValue().reversed()
        );

        var result = new LinkedHashMap<Coach, Integer>();

        for (Map.Entry<Coach, Integer> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
