package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;
import model.Person;
import static db.DBMS.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AppUtils {
    public static String console;
    private static final String ALGORITHM = "AES";
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public static Scanner scanner = new Scanner(System.in);

    public static void clearConsole() {
        if (console.equalsIgnoreCase("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\033\143");
        }
    }

    public static void print(String value) {
        if (null == value) {
            System.out.println();
        } else {
            System.out.println(value);
        }
    }

    public AppUtils(String console) {
        AppUtils.console = console;
    }

    public static void printLogo() {
        int width = 200;
        int height = 600;
        // BufferedImage image = ImageIO.read(new
        // File("/Users/mkyong/Desktop/logo.jpg"));
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 15));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("College Management System", 10, 200);
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            }
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(sb);
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomMarks() {
        return (int) (Math.random() * (100));
    }

    public static boolean checkCreds(int id, String password) {
        List<Person> list = selectAllDataFromCMS();
        return list.stream().anyMatch(x -> x.getId() == (id)
                && x.getPassword().equalsIgnoreCase(encrypt(password, x.getType())));
    }

    public static boolean changePassword(int id) {

        System.out.print("Enter New Password :");
        String newPassword = scanner.nextLine();

        List<Person> list = selectAllDataFromCMS();
        Optional<Person> person = list.stream().filter(x -> x.getId() == (id)).findAny();
        if (person.isPresent()) {
            person.get().setPassword(encrypt(newPassword, person.get().getType()));
            updateDataIntoCMS(person.get());
            print("Password Changed Successfully !!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        print("Password Not Changed because of wrong id");
        return false;

    }
}
