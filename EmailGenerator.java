import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EmailGenerator {

    private static final List<String> firstNames = Arrays.asList("Andrea", "Juan", "Sophia", "Lucas", "Olivia");
    private static final List<String> lastNames = Arrays.asList("Garcia", "Smith", "Brown", "Suzuki", "Wang");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество генерируемых адресов электронной почты:");
        int emailCount = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите домен электронной почты:");
        String emailService = scanner.nextLine();

        List<String> generatedEmails = generateEmails(emailCount, emailService);

        String userHomeDirectory = System.getProperty("user.home");
        String outputDirectory = userHomeDirectory + "/Desktop/SavedFiles/";

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(outputDirectory, emailService + "_emails.txt").toString()))) {
            for (String email : generatedEmails) {
                writer.write(email);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> generateEmails(int count, String emailService) {
        List<String> emails = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size())).toLowerCase();
            String lastName = lastNames.get(random.nextInt(lastNames.size())).toLowerCase();
            int birthYear = 1950 + random.nextInt(50);
            String email = String.format("%s%s%d@%s", firstName, lastName, birthYear, emailService);
            emails.add(email);
        }

        return emails;
    }
}
