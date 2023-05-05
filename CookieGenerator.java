import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CookieGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите список сайтов по одному на каждой строке (введите 'end' для завершения): ");
        List<String> sites = new ArrayList<>();
        String input;
        while (!(input = scanner.nextLine()).equals("end")) {
            sites.add(input);
        }

        System.out.println("Введите количество файлов с куками для генерации: ");
        int numFiles = scanner.nextInt();

        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";
        String mainFolder = desktopPath + File.separator + "CookieGenerator";
        String jsonFolder = mainFolder + File.separator + "JSON";
        String netscapeFolder = mainFolder + File.separator + "Netscape";

        for (int i = 0; i < numFiles; i++) {
            generateCookies(sites, i, jsonFolder, netscapeFolder);
        }
    }

    private static void generateCookies(List<String> sites, int fileNum, String jsonFolder, String netscapeFolder) {
        new File(jsonFolder).mkdirs();
        new File(netscapeFolder).mkdirs();

        String jsonFileName = jsonFolder + File.separator + fileNum + ".json";
        String netscapeFileName = netscapeFolder + File.separator + fileNum + ".txt";

        for (String site : sites) {
            try (FileWriter jsonWriter = new FileWriter(jsonFileName, true);
                 FileWriter netscapeWriter = new FileWriter(netscapeFileName, true)) {

                jsonWriter.write("[\n");
                for (int i = 0; i < 5; i++) {
                    String cookieName = "cookie" + i;
                    String cookieValue = UUID.randomUUID().toString();
                    long currentTime = System.currentTimeMillis();

                    jsonWriter.write("  {\n");
                    jsonWriter.write("    \"domain\": \"" + site + "\",\n");
                    jsonWriter.write("    \"name\": \"" + cookieName + "\",\n");
                    jsonWriter.write("    \"value\": \"" + cookieValue + "\",\n");
                    jsonWriter.write("    \"expirationDate\": " + (currentTime + 86400000) + "\n");
                    jsonWriter.write(i < 4 ? "  },\n" : "  }\n");

                    netscapeWriter.write(site + "\tFALSE\t/\tFALSE\t" + (currentTime + 86400000) + "\t" + cookieName + "\t" + cookieValue + "\n");
                }
                jsonWriter.write("]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
