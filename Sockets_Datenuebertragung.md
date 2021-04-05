# Datenübertragung: Text, Binär, Objekte

## Streams

| Typ     | Input             | Output             |
| ------- | ----------------- | ------------------ |
| Text    | BufferedReader    | PrintWriter        |
| Binär   | DataInputStream   | DataOutputStream   |
| Objekte | ObjectInputStream | ObjectOutputStream |

## Binär

```java
@Override
public Collection<Airplane> receive(Socket s) throws Exception {
	Collection<Airplane> airplanes = new ArrayList<>();
	try (DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()))) {
		int size = dis.readInt();
		while (size-- > 0) {
			Airplane airplane = new Airplane();
			airplane.setName(dis.readUTF());
			airplane.setSeats(dis.readInt());
			airplane.setvMax(dis.readFloat());
			airplane.setManufacturer(dis.readUTF());
			airplane.setFuelCapacity(dis.readFloat());

			airplanes.add(airplane);
		}
	}
	return airplanes;
}

@Override
public void send(Collection<Airplane> airplanes, Socket s) throws Exception {
	try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()))) {
		dos.writeInt(airplanes.size());
		for (Airplane airplane : airplanes) {
			dos.writeUTF(airplane.getName());
			dos.writeInt(airplane.getSeats());
			dos.writeFloat(airplane.getvMax());
			dos.writeUTF(airplane.getManufacturer());
			dos.writeFloat(airplane.getFuelCapacity());
		}
	}
}
```



## Objekte

```java
@Override
public Collection<Airplane> receive(Socket s) throws Exception {
	try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()))) {
		return (Collection<Airplane>) ois.readObject();
	}
}

@Override
public void send(Collection<Airplane> airplanes, Socket s) throws Exception {
	try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()))) {
		oos.writeObject(airplanes);
		oos.flush();
	}
}
```
