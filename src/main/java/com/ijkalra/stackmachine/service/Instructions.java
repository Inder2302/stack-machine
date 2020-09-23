package com.ijkalra.stackmachine.service;

import java.util.Arrays;

public enum Instructions {
    PUSH ("Pushes an element to the Stack (expects numeric value as argument. E.g. PUSH 10 )"),
    POP("Pops the top element out of stack"),
    PEEK ("Prints the top element from stack (element not removed from stack unlike POP)"),
    CLEAR ("Clears the whole stack"),
    ADD ("Pops the top 2 elements of the stack and push their sum on top of stack"),
    MUL ("Pops the top 2 elements of the stack and push their multiplication on top of stack"),
    NEG ("Negates the top element of the stack"),
    INV ("Inverts the top element of the stack"),
    UNDO ("Undo the last operation and return stack to previous state."),
    PRINT ("Prints all elements of the stack"),
    QUIT ("Quits the application")
    ;

    private String instructionDesc;

    Instructions(String instructionDesc) {
        this.instructionDesc = instructionDesc;
    }

    /*
    Checks if a strings is a valid name for this enum
     */
    public static boolean isValidInstruction(String val) {
        return Arrays.stream(values()).map(Enum::name).anyMatch(v -> v.equalsIgnoreCase(val));
    }

    public String getInstructionDesc() {
        return instructionDesc;
    }
}
