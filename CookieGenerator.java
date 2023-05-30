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

        System.out.println("Введите логин: ");
        String login = scanner.nextLine();

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        System.out.println("Введите количество файлов с куками для генерации: ");
        int numFiles = scanner.nextInt();

        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";
        String mainFolder = desktopPath + File.separator + "CookieGenerator";
        String jsonFolder = mainFolder + File.separator + "JSON";
        String netscapeFolder = mainFolder + File.separator + "Netscape";

        for (int i = 0; i < numFiles; i++) {
            generateCookies(sites, i, jsonFolder, netscapeFolder, login, password);
        }
    }

    private static void generateCookies(List<String> sites, int fileNum, String jsonFolder, String netscapeFolder, String login, String password) {
        new File(jsonFolder).mkdirs();
        new File(netscapeFolder).mkdirs();

        String jsonFileName = jsonFolder + File.separator + fileNum + ".txt";
        String netscapeFileName = netscapeFolder + File.separator + fileNum + ".txt";

        for (String site : sites) {
            try (PrintWriter jsonWriter = new PrintWriter(new BufferedWriter(new FileWriter(jsonFileName, true)));
                 FileWriter netscapeWriter = new FileWriter(netscapeFileName, true)) {

                jsonWriter.println("[");
                for (int i = 0; i < 5; i++) {
                    String cookieName = "cookie" + i;
                    String cookieValue = UUID.randomUUID().toString();
                    long currentTime = System.currentTimeMillis();

                    jsonWriter.println("  {");
                    jsonWriter.println("    \"domain\": \"" + site + "\",");
                    jsonWriter.println("    \"name\": \"" + cookieName + "\",");
                    jsonWriter.println("    \"value\": \"" + cookieValue + "\",");
                    jsonWriter.println("    \"expirationDate\": " + (currentTime + 86400000));
                    jsonWriter.println(i < 4 ? "  }," : "  }");

                    netscapeWriter.write(site + "\tTRUE\t/\tFALSE\t" + (currentTime + 86400000) + "\t" + cookieName + "\t" + cookieValue + "\n");
                }

                // Добавляем куки для логина и пароля
                String cookieLogin = "login";
                String cookiePassword = "password";

                                jsonWriter.println("  {");
                jsonWriter.println("    \"domain\": \"" + site + "\",");
                jsonWriter.println("    \"name\": \"" + cookiePassword + "\",");
                jsonWriter.println("    \"value\": \"" + password + "\",");
                jsonWriter.println("    \"expirationDate\": " + (System.currentTimeMillis() + 86400000));
                jsonWriter.println("  }");

                jsonWriter.println("]");

                netscapeWriter.write(site + "\tTRUE\t/\tFALSE\t" + (System.currentTimeMillis() + 86400000) + "\t" + cookieLogin + "\t" + login + "\n");
                netscapeWriter.write(site + "\tTRUE\t/\tFALSE\t" + (System.currentTimeMillis() + 86400000) + "\t" + cookiePassword + "\t" + password + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

