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

public class Generator {

    private static final List<String> firstNames = Arrays.asList("Andrea", "Juan", "Sophia", "Lucas", "Olivia");
    private static final List<String> lastNames = Arrays.asList("Garcia", "Smith", "Brown", "Suzuki", "Wang");
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHARACTERS_WITH_SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;'<>,.?/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип генерации (1 - только адреса электронной почты, 2 - только пароли, 3 - адреса электронной почты и пароли):");
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            generateEmails(scanner);
        } else if (option == 2) {
            generatePasswords(scanner);
        } else if (option == 3) {
            generateEmailsAndPasswords(scanner);
        } else {
            System.out.println("Неверный выбор. Завершение программы.");
        }
    }

    private static void generateEmails(Scanner scanner) {
        System.out.println("Введите количество генерируемых адресов электронной почты:");
        int emailCount = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите домен электронной почты:");
        String emailService = scanner.nextLine();

        List<String> generatedEmails = createEmails(emailCount, emailService);
        saveToFile(generatedEmails, emailService + "_emails.txt");
    }

    private static List<String> createEmails(int count, String emailService) {
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

    private static void generatePasswords(Scanner scanner) {
        System.out.println("Введите количество генерируемых паролей:");
        int passwordCount = scanner.nextInt();
        System.out.println("Введите длину паролей:");
        int passwordLength = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Хотите начать пароли с двоеточия? (да/нет):");
        String startWithColon = scanner.nextLine();
        boolean useColon = startWithColon.equalsIgnoreCase("да");

        System.out.println("Хотите использовать специальные символы? (да/нет):");
        String useSpecialCharacters = scanner.nextLine();
        boolean useSpecial = useSpecialCharacters.equalsIgnoreCase("да");

        List<String> generatedPasswords = createPasswords(passwordCount, passwordLength, useColon, useSpecial);
        saveToFile(generatedPasswords, "generated_passwords.txt");
    }

    private static List<String> createPasswords(int count, int length, boolean useColon, boolean useSpecial) {
        List<String> passwords = new ArrayList<>();
        Random random = new Random();
        String characterSet = useSpecial ? CHARACTERS_WITH_SPECIAL : CHARACTERS;

        for (int i = 0; i < count; i++) {
            StringBuilder password = new StringBuilder(useColon ? ":" : "");
            for (int j = useColon ? 1 : 0; j < length; j++) {
                char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
                password.append(randomChar);
            }
            passwords.add(password.toString());
        }

        return passwords;
    }

    private static void generateEmailsAndPasswords(Scanner scanner) {
        System.out.println("Введите количество генерируемых пар email:password:");
        int count = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите домен электронной почты:");
        String emailService = scanner.nextLine();

        System.out.println("Введите длину паролей:");
        int passwordLength = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Хотите начать пароли с двоеточия? (да/нет):");
        String startWithColon = scanner.nextLine();
        boolean useColon = startWithColon.equalsIgnoreCase("да");

        System.out.println("Хотите использовать специальные символы? (да/нет):");
        String useSpecialCharacters = scanner.nextLine();
        boolean useSpecial = useSpecialCharacters.equalsIgnoreCase("да");

        List<String> generatedPairs = new ArrayList<>();
        List<String> generatedEmails = createEmails(count, emailService);
        List<String> generatedPasswords = createPasswords(count, passwordLength, useColon, useSpecial);

        for (int i = 0; i < count; i++) {
            String email = generatedEmails.get(i);
            String password = generatedPasswords.get(i);
            generatedPairs.add(email + ":" + password);
        }

        saveToFile(generatedPairs, emailService + "_email_password_pairs.txt");
    }

    private static void saveToFile(List<String> lines, String fileName) {
        String userHomeDirectory = System.getProperty("user.home");
        String outputDirectory = userHomeDirectory + "/Desktop/SavedFiles/";

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(outputDirectory, fileName).toString()))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

