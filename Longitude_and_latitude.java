import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

public class Longitude_and_latitude {
    public static void main(String[] args) throws Exception {
        var client = HttpClient.newHttpClient();

        var scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Введите название города (или 'выход' для завершения): ");
            var cityName = scanner.nextLine();
            if (cityName.equalsIgnoreCase("выход")) {
                break;
            }

            var request = HttpRequest.newBuilder()
                    .uri(new URI("https://nominatim.openstreetmap.org/search?city=" + cityName + "&format=json"))
                    .GET()
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                var jsonArray = new JSONArray(response.body());
                if (jsonArray.length() > 0) {
                    var jsonObject = jsonArray.getJSONObject(0);
                    var lat = jsonObject.getDouble("lat");
                    var lon = jsonObject.getDouble("lon");
                    System.out.printf("Координаты города %s: широта %s, долгота %s\n", cityName, lat, lon);
                } else {
                    System.out.println("Город не найден.");
                }
            } else {
                System.out.println("Ошибка при выполнении запроса: " + response.statusCode());
            }
        }
        scanner.close();
    }
}
