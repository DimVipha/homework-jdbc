package Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class PropertiesLoader {
    public final static Properties properties = new Properties();
    public static void LoadPropertiesFile(){
        try(BufferedReader bufferedReader =
                    new BufferedReader(
                            new FileReader("application.properties")
                    );
        )
        {
            properties.load(bufferedReader);

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}
