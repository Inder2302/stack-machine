package com.ijkalra.stackmachine.service;

import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;
import com.ijkalra.stackmachine.model.CustomStack;

import java.util.Arrays;
import java.util.Scanner;

public class MenuService {

    private CustomStack customStack;

    public MenuService(CustomStack customStack) {
        this.customStack = customStack;
    }

    public void startStackMachine() {
        final String errorMessage = "Invalid Command. Reason: %s. \n";

        // print the menu items
        printMenu();
        // Create Scanner to get user input
        Scanner scanner = new Scanner (System.in);
        // flag to exit the function when user enters "quit"
        boolean exitFunction = false;

        do {
            // prompt user
            System.out.print("Enter instructions: ");
            // get user input
            String inputLine = scanner.nextLine();
            // split the input line to
            String inputInstruction = inputLine.split("\\s+")[0];
            /*
            Other way to validate an enum value can be a try catch block
                try { Instructions.valueOf(command) }
                catch (IllegalArgumentException e) {
                    System.out.println("Invalid Instruction");
                    continue;
                };
                I chose a function to make it look cleaner ...
                prefer this over suppressing exceptions.
            */

            if (! Instructions.isValidInstruction(inputInstruction.toUpperCase())) {
                System.out.println("Invalid instruction. Please try again...");
                continue;
            }

            // Now Since we have validated the input
            // we are sure the Enum will not raise IllegalArgumentException exception.
            Instructions instruction = Instructions.valueOf(inputInstruction.toUpperCase());

            switch (instruction) {
                case PRINT:
                    try {
                        customStack.show();
                    } catch (EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case CLEAR:
                    customStack.clearStack();
                    System.out.println("INFO: Stack emptied.");
                    break;
                case UNDO:
                    customStack.undoLastOperation();
                    System.out.println("INFO: Last operation reverted.");
                    break;
                case POP:
                    try {
                        System.out.printf("INFO: Element popped out of stack %s\n",customStack.pop());
                    } catch (EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case PEEK:
                    try {
                        System.out.printf("INFO: Top Element on stack is: %s\n",customStack.peek());
                    } catch (EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case ADD:
                    try {
                        customStack.addTopTwoElements();
                        System.out.println("INFO: Addition performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case NEG:
                    try {
                        customStack.negateTopElement();
                        System.out.println("INFO: Negation performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case INV:
                    try {
                        customStack.invertTopElement();
                        System.out.println("INFO: Inversion performed");
                    } catch (EmptyStackException | DivideByZeroException | NotEnoughElementsException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case MUL:
                    try {
                        customStack.multiplyTopTwoElements();
                        System.out.println("INFO: Multiplication performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(errorMessage, e.getMessage());
                    }
                    break;
                case QUIT:
                    System.out.println("INFO: Exiting application. GoodBye...");
                    exitFunction = true;
                    break;
                default:
                    // Push
                    try {
                        String arg = inputLine.split("\\s+")[1];
                        customStack.push(Integer.parseInt(arg));
                        System.out.println("INFO: Element pushed to top of stack");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.printf(errorMessage, "Element not provided to push");
                    } catch (NumberFormatException e){
                        System.out.printf(errorMessage, "Expected numeric element to push");
                    }
            }

        } while (!exitFunction);

    }

    private void printMenu() {
        System.out.println("\t===== Welcome to Stack Machine =====");
        System.out.println("\t====== Valid Instructions =====");
        Arrays.stream(Instructions.values()).forEach(i -> System.out.printf("\t%s:-\t\t%s\n", i.name(), i.getInstructionDesc()));
        System.out.println("\n");
    }
}
