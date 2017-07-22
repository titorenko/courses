package week9;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import common.BiIntConsumer;

class IntToIntTreeMap {
    final TreeMap<Integer, Integer> map = new TreeMap<>();

    void add(int key, int value) {
        map.put(key, value);
    }

    public int lastValue() {
        return map.lastEntry().getValue();
    }

    public void removeTailsWithSmallerValues(int key, int value) {
        List<Integer> toRemove = new ArrayList<>();
        SortedMap<Integer, Integer> tailSet = map.tailMap(key);
        for (Entry<Integer, Integer> step : tailSet.entrySet()) {
            if (step.getValue() < value)
                toRemove.add(step.getKey());
            else
                break;
        }
        for (Integer i : toRemove) {
            map.remove(i);
        }
    }

    List<Entry<Integer, Integer>> descendingEntries() {
        return new ArrayList<>(map.descendingMap().entrySet());
    }

    public int floorValue(int key) {
        return map.floorEntry(key).getValue();
    }
    
    void forEachDescending(BiIntConsumer consumer) {
        List<Entry<Integer, Integer>> items = new ArrayList<>(map.descendingMap().entrySet());
        items.forEach(e -> consumer.accept(e.getKey(), e.getValue()));
    }
}