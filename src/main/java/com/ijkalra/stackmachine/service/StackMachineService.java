package com.ijkalra.stackmachine.service;

import com.ijkalra.stackmachine.exception.ActionNotSupportedException;
import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;
import com.ijkalra.stackmachine.model.CustomStack;

import static com.ijkalra.stackmachine.config.Constants.INFO_MSG;

public class StackMachineService {

    // kept as final because CustomStack itself wont change.
    // its elements will change in value. but customStack itself will keep pointing to the what is set in constructor.
    private final CustomStack customStack;
    
    /* Constructor to set the custom stack */
    public StackMachineService(CustomStack customStack) {
        this.customStack = customStack;
    }

    public void performAction(Instructions instruction, String arg) throws EmptyStackException, NotEnoughElementsException, DivideByZeroException, ActionNotSupportedException {
        // Perform action as per instruction.
        switch (instruction) {
            case PRINT:
                customStack.show();
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
                System.out.println(customStack.pop());
                System.out.printf(INFO_MSG, "Element popped out of stack");
                break;
            case PEEK:
                System.out.println(customStack.peek());
                System.out.printf(INFO_MSG, "Element is not removed from the stack");
                break;
            case ADD:
                customStack.addTopTwoElements();
                System.out.printf(INFO_MSG, "Done. Addition performed");
                break;
            case NEG:
                customStack.negateTopElement();
                System.out.printf(INFO_MSG, "Done. Negation performed");
                break;
            case INV:
                customStack.invertTopElement();
                System.out.printf(INFO_MSG, "Done. Inversion performed");
                break;
            case MUL:
                customStack.multiplyTopTwoElements();
                System.out.printf(INFO_MSG, "Done. Multiplication performed");
                break;
            case PUSH:
                // Default is push operation
                try {
                    double elementForPushOperation = Double.parseDouble(arg); // throws number format exception
                    customStack.push(elementForPushOperation);
                    System.out.printf(INFO_MSG, "Element pushed to top of stack");
                } catch (NullPointerException | NumberFormatException e) {
                    throw new ActionNotSupportedException("Expected Numeric input");
                }
                break;
            default:
                // Ideally code flow will not come here as we are validating the action
                // in controller class
                throw new ActionNotSupportedException("Command not supported. Please try again...");
        }
    }

    // helpful in testing
    protected int getNumberOfElementsInStack() {
        return customStack.getNumberOfElementsInStack();
    }

    // helpful in testing
    protected double peekTopElement() throws EmptyStackException {
        return customStack.peek();
    }
}

