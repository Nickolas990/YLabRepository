package org.ylab.melnikov.lesson3.datedmap;

import java.util.Date;
import java.util.Set;

public interface DatedMap <K, V> {
    void put(K key, V value);

    K get(K key);

    boolean containsKey(K key);

    void remove(K key);

    Set<K> keySet();

    Date getKeyLastInsertionDate(K key);
}
