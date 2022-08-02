package util;

import data.City;

import java.time.LocalDateTime;
import java.util.TreeSet;

public class CollectionManager {
    private TreeSet<City> collection = new TreeSet<>();
    private final LocalDateTime collectionInitTime = LocalDateTime.now();
    public TreeSet<City> getCollection() {
        return collection;
    }
    public LocalDateTime getCollectionInitTime() {
        return collectionInitTime;
    }
    public void setCollection(TreeSet<City> collection) {
        this.collection = collection;
    }
}