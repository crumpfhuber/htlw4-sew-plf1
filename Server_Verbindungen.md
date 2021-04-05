# mehrere, gleichzeitige Verbindungen

## Executors

`Executors.newCachedThreadPool()` ... Thread-Pool, der bei Bedarf neue Threads erstellt, wenn keine Threads wiederverwendbar sind
`Executors.newFixedThreadPool(int nThreads)` ... Thread-Pool mit einer fixen Anzahl an Threads

## Beispiel

```java
public static void main(String[] args) {
	ExecutorService executorService = Executors.newCachedThreadPool();

	try (ServerSocket serverSocket = new ServerSocket(properties.getServerPort())) {
		while (true)
			executorService.execute(new ClientHandler(serverSocket.accept(), properties));
	} catch (IOException ioException) {
		ioException.printStackTrace();
	}
}
```
