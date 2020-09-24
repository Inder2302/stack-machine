package com.ijkalra.stackmachine.controller;

import com.ijkalra.stackmachine.exception.ActionNotSupportedException;
import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;
import com.ijkalra.stackmachine.service.Instructions;
import com.ijkalra.stackmachine.service.StackMachineService;

import java.util.Scanner;

import static com.ijkalra.stackmachine.config.Constants.ERROR_MSG;

public class StackMachineController {

    private final StackMachineService stackMachineService;
    private final Scanner scanner;

    public StackMachineController(StackMachineService stackMachineService, Scanner scanner) {
        this.stackMachineService = stackMachineService;
        this.scanner = scanner;
    }

    public void runStackMachine() {
        String arg = null;

        // flag to exit the function when user enters "quit"
        boolean exitRequest = false;
        do {
            // prompt user
            System.out.print("\nEnter instructions: ");
            // get user input
            String inputLine = scanner.nextLine().trim();
            // split the input line. The first part is our command. (in case of push command we have 2 parts)
            String inputInstruction = inputLine.split("\\s+")[0];

            if (! Instructions.isValidInstruction(inputInstruction.toUpperCase())) {
                System.out.printf(ERROR_MSG, "Command not supported. Please try again...");
                continue;
            }
            /*
            Other way to validate an enum value can be a try catch block
                try { Instructions.valueOf(inputInstruction) }
                catch (IllegalArgumentException e) {
                    System.out.println("Invalid Instruction");
                    continue;
                };
            */

            // Now Since we have validated the input
            // we are sure the Enum will not raise IllegalArgumentException exception.
            Instructions instruction = Instructions.valueOf(inputInstruction.toUpperCase());
            if (instruction == Instructions.QUIT) {
                exitRequest = true;
                continue;
            }

            //take the argument if its a push opearation
            if (instruction == Instructions.PUSH) {
                try {
                     arg = inputLine.split("\\s+")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    // if the input instruction does not have any argument for push command
                    System.out.printf(ERROR_MSG, "Element not provided to push");
                    continue;
                }
            }

            try {
                stackMachineService.performAction(instruction, arg);
            } catch (EmptyStackException | NotEnoughElementsException | DivideByZeroException | ActionNotSupportedException | NumberFormatException e) {
                System.out.printf(ERROR_MSG, e.getMessage());
                continue;
            }
        }while(!exitRequest);
    }

}
