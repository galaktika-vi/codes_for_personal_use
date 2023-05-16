import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Введите название города (на кириллице или латинице) или введите 'exit' для выхода: ");
                String city = reader.readLine();

                if (city.equalsIgnoreCase("exit")) {
                    break;
                }

                // Формирование URL-адреса API запроса погоды для выбранного города
                String urlString = "https://wttr.in/" + city + "?format=%C+%t";
                URL url = new URL(urlString);

                // Создание HTTP-соединения
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Получение ответа от сервера
                int responseCode = connection.getResponseCode();

                // Чтение ответа
                BufferedReader apiReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = apiReader.readLine()) != null) {
                    line = line.replace("Clear", "Ясно").replace("Partly cloudy", "Переменная облачность").replace("Sunny", "Солнечно");
                    System.out.println(line);
                }
                apiReader.close();

                // Обработка ответа
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    System.out.println("Не удалось получить данные о погоде для города " + city + ". Код ответа: " + responseCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
