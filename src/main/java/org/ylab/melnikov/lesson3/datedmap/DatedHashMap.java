package org.ylab.melnikov.lesson3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Nikolay Melnikov
 */
public class DatedHashMap implements DatedMap<String, String> {

    private final Map<String, String> map = new HashMap<>();
    private final Map<String, Date> dateMap = new HashMap<>();
    @Override
    public void put(String key, String value) {
        map.put(key, value);
        dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return dateMap.get(key);
    }
}
