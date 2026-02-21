// package src;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            // Your logic: Append "Expected " to the input?
            // Or if the input is "Output 1", print "Expected Output 1"

            System.out.println("Expected " + input);
        }
        scanner.close();
    }
}
