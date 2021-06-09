import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RockPaperScissors {

    private static final SecureRandom random = new SecureRandom();
    private static final byte[] key = random.generateSeed(16);

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
        if (args.length < 3 || unique(args) || args.length % 2 == 0) {
            System.out.println("Error input");
            System.out.println("Correct input: Rock Paper Scissors Lizard Spock");
            System.exit(0);
        }
        List<String> moves = Arrays.asList(args);
        int comp = random.nextInt(moves.size() - 1);
        System.out.println("HMAC: " + bytesToHex(hmac(moves.get(comp))));
        Scanner scanner = new Scanner(System.in);
        printMenu(moves);
        int choose;
        while (true) {
            try {
                choose = Integer.parseInt(scanner.nextLine());
                if (choose == 0) {
                    System.exit(0);
                }
                if (choose < 1 || choose > moves.size()) {
                    System.out.println("Enter a correct number!!");
                    printMenu(moves);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!!!");
                printMenu(moves);
            }
        }
        choose = choose - 1;
        System.out.println("Cpu choose: " + moves.get(comp));
        System.out.println("You choose: " + moves.get(choose));
        int result = (moves.size() + comp - choose) % moves.size();
        if (result == 0) {
            System.out.println("Draw");
        } else if (result % 2 == 0) {
            System.out.println("You win C:");
        } else {
            System.out.println("Cpu win :C");
        }
        System.out.println("HMAC Key: " + bytesToHex(key));
    }

    private static boolean unique(String[] a) {
        List<String> arr = new ArrayList<>();
        for (String b : a) {
            if (arr.contains(b)) return true;
            arr.add(b);
        }
        return false;
    }

    public static byte[] hmac(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(message.getBytes());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (byte b : bytes) {
            s.append(String.format("%02X", b));
        }
        return s.toString();
    }

    private static void printMenu(List<String> moves) {
        System.out.println("Available moves: ");
        for (int i = 0; i < moves.size(); i++) {
            System.out.println(i + 1 + " - " + moves.get(i));
        }
        System.out.println("0 - Exit");
    }
}
