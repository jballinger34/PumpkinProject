package com.rit.sucy.util;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TreeMultiMap<T> extends TreeMap<Integer, List<T>> {
    public TreeMultiMap() {
    }

    public TreeMultiMap(Comparator<? super Integer> comparator) {
        super(comparator);
    }

    public void put(int key, T value) {
        List<T> rewardList = super.getOrDefault(key, null);
        if (rewardList == null) {
            rewardList = new ArrayList();
            super.put(key, rewardList);
        }

        ((List)rewardList).add(value);
    }

    public Collection<T> getAll() {
        Deque<T> allValues = new ArrayDeque();
        Iterator var2 = this.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<Integer, List<T>> entry = (Map.Entry)var2.next();
            allValues.addAll((Collection)entry.getValue());
        }

        return allValues;
    }
}