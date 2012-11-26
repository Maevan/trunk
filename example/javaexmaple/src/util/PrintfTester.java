package util;

public class PrintfTester {
    public static void main(String[] args) {
        System.out.printf("%1$5.1f%n", 1.2);
        System.out.printf("%2$5.1f%n", 1.22, 1.333);
        System.out.printf("%-5.1f%n", 1.2);
    }
}
