# zentrale Verwaltung von Ressourcen (Daten, Collections, Dateien, ...)

## Lock

`Server.java`
```java
Lock lock = new ReentrantLock();
```

`ClientHandler.java`
```java

lock.lock();

lock.unlock();

```

## Collections

- `List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());`
- `Map<Integer, String> syncMap = Collections.synchronizedMap(new HashMap<>());`
- `Set<Integer> syncSet = Collections.synchronizedSet(new HashSet<>());`
- `SortedSet<Integer> syncSortedSet = Collections.synchronizedSortedSet(new TreeSet<>());`
