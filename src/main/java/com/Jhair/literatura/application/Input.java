package com.Jhair.literatura.application;

import java.util.Scanner;

public final class Input {
    private Input() {}

    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n < min || n > max) {
                    System.out.println("⚠ Ingresa un número entre " + min + " y " + max + ".\n");
                    continue;
                }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Valor no numérico. Intenta de nuevo.\n");
            }
        }
    }

    public static String readNonEmptyLine(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("⚠ No puede estar vacío.\n");
        }
    }

    public static Integer readOptionalYear(Scanner sc, String prompt) {
        // Devuelve null si se deja en blanco
        System.out.print(prompt);
        String s = sc.nextLine().trim();
        if (s.isBlank()) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("⚠ Año inválido. Dejando vacío.\n");
            return null;
        }
    }

    public static void pause(Scanner sc) {
        System.out.print("Presiona ENTER para continuar...");
        sc.nextLine();
    }
}
