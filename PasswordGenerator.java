import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHARACTERS_WITH_SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;'<>,.?/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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

        List<String> generatedPasswords = generatePasswords(passwordCount, passwordLength, useColon, useSpecial);

        String userHomeDirectory = System.getProperty("user.home");
        String outputDirectory = userHomeDirectory + "/Desktop/SavedFiles/";

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(outputDirectory, "generated_passwords.txt").toString()))) {
            for (String password : generatedPasswords) {
                writer.write(password);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> generatePasswords(int count, int length, boolean useColon, boolean useSpecial) {
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
}
