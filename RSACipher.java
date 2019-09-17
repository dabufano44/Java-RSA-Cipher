// Danielle Bufano
// 3/6/19
// Java Implementation of RSA Cipher

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;

public class RSACipher {
    public static void main(String[] args) {

        Random rnd = new Random();
        BigInteger p = new BigInteger(2048, 100000, rnd);
        BigInteger q = new BigInteger(2048, 100000, rnd);
        BigInteger n = p.multiply(q);
        BigInteger p_1 = p.subtract(BigInteger.ONE);
        BigInteger q_1 = q.subtract(BigInteger.ONE);
        BigInteger n0 = p_1.multiply(q_1);
        BigInteger e = BigInteger.TWO;
        BigInteger gcd = n0.gcd(e);
        while (!gcd.equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
            gcd = n0.gcd(e);
        }
        BigInteger d = e.modInverse(n0);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter a message to be encrypted: ");
        String message = input.nextLine();

        menu();
        Scanner numInput = new Scanner(System.in);
        int userNum = numInput.nextInt();
        while (userNum != 0) {
            switch (userNum) {
                case 1:
                    System.out.println("Public Key: (" + e + ", " + n + ")");
                    menu();
                    userNum = numInput.nextInt();
                    break;
                case 2:
                    BigInteger ciphertext = encrypt(message, e, n);
                    System.out.println("Ciphertext: " + ciphertext);
                    menu();
                    userNum = numInput.nextInt();
                    break;
                case 3:
                    BigInteger c = encrypt(message, e, n);
                    decrypt(c, d, n);
                    menu();
                    userNum = numInput.nextInt();
                    break;
                case 4:
                    message = input();
                    encrypt(message, e, n);
                    menu();
                    userNum = numInput.nextInt();
                    break;
                default:
                    System.out.println("That is not a valid input, try again");
                    menu();
                    userNum = numInput.nextInt();
            }
        }
    }

    public static void menu()
    {
        System.out.println("\nPress 1 to show the public key." +
                "\nPress 2 to display the encrypted ciphertext " + "\nPress 3 to decrypt the message"
                + "\nPress 4 to enter a new message to be encrypted" + "\nPress 0 to exit\n");
    }

    public static String input()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a message to be encrypted: ");
        String message = input.nextLine();
        return message;
    }

    public static BigInteger encrypt(String message, BigInteger e, BigInteger n)
    {
        byte messageArr[] = message.getBytes();
        BigInteger m = new BigInteger(messageArr);
        BigInteger c = m.modPow(e, n);
        return c;
    }

    public static void decrypt(BigInteger c, BigInteger d, BigInteger n)
    {
        BigInteger answer = c.modPow(d, n);
        byte newarr[] = answer.toByteArray();
        for (int i = 0; i < newarr.length; i++)
            System.out.print((char)newarr[i]);
        System.out.print("\n");
    }
}
