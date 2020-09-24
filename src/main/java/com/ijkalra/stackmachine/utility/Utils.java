package com.ijkalra.stackmachine.utility;

import com.ijkalra.stackmachine.service.Instructions;

import java.util.Arrays;

public class Utils {
    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static void printMenu() {
        System.out.println("\t===== Welcome to Stack Machine =====");
        System.out.println("\t====== Valid Instructions =====");
        Arrays.stream(Instructions.values()).forEach(i -> System.out.printf("\t%s:-\t\t%s\n", i.name(), i.getInstructionDesc()));
        System.out.print("\n");
    }

}
