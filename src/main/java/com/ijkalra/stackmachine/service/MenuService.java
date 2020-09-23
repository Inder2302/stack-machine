package com.ijkalra.stackmachine.service;

import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;
import com.ijkalra.stackmachine.model.CustomStack;

import java.util.Arrays;
import java.util.Scanner;

import static com.ijkalra.stackmachine.config.Constants.ERROR_MSG;
import static com.ijkalra.stackmachine.config.Constants.INFO_MSG;

public class MenuService {

    // kept as final because CustomStack itself wont change.
    // its elements will change in value. but customStack itself will keep pointing to the what is set in constructor.
    private final CustomStack customStack;
    
    /* Constructor to set the custom stack */
    public MenuService(CustomStack customStack) {
        this.customStack = customStack;
    }

    public void startStackMachine() {
        // print the menu items from Inst
        printMenu();
        // Create Scanner to get user input
        Scanner scanner = new Scanner (System.in);
        // flag to exit the function when user enters "quit"
        boolean exitFunction = false;

        do {
            // prompt user
            System.out.print("\nEnter instructions: ");
            // get user input
            String inputLine = scanner.nextLine();
            // split the input line. The first part is our command. (in case of push command we have 2 parts)
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
                System.out.printf(ERROR_MSG, "Command not supported. Please try again...");
                continue;
            }

            // Now Since we have validated the input
            // we are sure the Enum will not raise IllegalArgumentException exception.
            Instructions instruction = Instructions.valueOf(inputInstruction.toUpperCase());

            // Perform action as per instruction.
            switch (instruction) {
                case PRINT:
                    try {
                        customStack.show();
                    } catch (EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case CLEAR:
                    customStack.clearStack();
                    System.out.printf(INFO_MSG, "Done. Stack Emptied.");
                    break;
                case UNDO:
                    customStack.undoLastOperation();
                    System.out.printf(INFO_MSG, "Done. Last operation reverted.");
                    break;
                case POP:
                    try {
                        System.out.println(customStack.pop());
                        System.out.printf(INFO_MSG, "Element popped out of stack");
                    } catch (EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case PEEK:
                    try {
                        System.out.println(customStack.peek());
                        System.out.printf(INFO_MSG, "Element is not removed from the stack");
                    } catch (EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case ADD:
                    try {
                        customStack.addTopTwoElements();
                        System.out.printf(INFO_MSG, "Done. Addition performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case NEG:
                    try {
                        customStack.negateTopElement();
                        System.out.printf(INFO_MSG, "Done. Negation performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case INV:
                    try {
                        customStack.invertTopElement();
                        System.out.printf(INFO_MSG, "Done. Inversion performed");
                    } catch (EmptyStackException | DivideByZeroException | NotEnoughElementsException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case MUL:
                    try {
                        customStack.multiplyTopTwoElements();
                        System.out.printf(INFO_MSG, "Done. Multiplication performed");
                    } catch (NotEnoughElementsException | EmptyStackException e) {
                        System.out.printf(ERROR_MSG, e.getMessage());
                    }
                    break;
                case QUIT:
                    System.out.println("INFO: Exiting application. GoodBye...");
                    exitFunction = true;
                    break;
                default:
                    // Default is push operation
                    try {
                        // take the argument of push opearation
                        String arg = inputLine.split("\\s+")[1];
                        customStack.push(Double.parseDouble(arg));
                        System.out.println("INFO: Element pushed to top of stack");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // if the input instruction does not have any argument for push command
                        System.out.printf(ERROR_MSG, "Element not provided to push");
                    } catch (NumberFormatException e){
                        // if unable to parse Double from the input string
                        System.out.printf(ERROR_MSG, "Expected numeric element to push");
                    }
            }
        } while (!exitFunction);

    }

    private void printMenu() {
        System.out.println("\t===== Welcome to Stack Machine =====");
        System.out.println("\t====== Valid Instructions =====");
        Arrays.stream(Instructions.values()).forEach(i -> System.out.printf("\t%s:-\t\t%s\n", i.name(), i.getInstructionDesc()));
        System.out.print("\n");
    }
}
