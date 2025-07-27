import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Main <ruta_archivo.atsp>");
            return;
        }

        String filePath = args[0];

        try {
            TSPInstance instance = new TSPInstance(filePath);

            List<City> cities = City.citiesReader(filePath);

            for (City city : cities) {
                System.out.println(city);
            }

            

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}