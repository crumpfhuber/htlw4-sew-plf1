# Properties

## Beispiel

`Main.java`
```java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("values.properties"));

            System.out.println("User: " + properties.getProperty("username"));
            System.out.println("Password: " + properties.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

`values.properties`
```
username=admin
password=cisco123
```
