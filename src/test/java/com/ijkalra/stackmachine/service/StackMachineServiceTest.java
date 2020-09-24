package com.ijkalra.stackmachine.service;

import com.ijkalra.stackmachine.exception.ActionNotSupportedException;
import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;
import com.ijkalra.stackmachine.model.CustomStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackMachineServiceTest {
    StackMachineService stackMachineService;

    @BeforeEach
    void init() {
        stackMachineService = new StackMachineService(new CustomStack((5)));
    }

    @Nested
    @DisplayName("Testing Basic Operations")
    class BasicOperationTest{
            @Test
            @DisplayName("Testing Push operation")
            public void testElementIsPushed () throws
            EmptyStackException, NotEnoughElementsException, ActionNotSupportedException, DivideByZeroException {
            System.out.println("Running test: testElementIsPushed");
            stackMachineService.performAction(Instructions.PUSH, "10");
            assertEquals(stackMachineService.peekTopElement(), 10);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
        }

            @Test
            @DisplayName("Testing Pop operation")
            public void testElementIsPopped () throws
            NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testElementIsPopped");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            stackMachineService.performAction(Instructions.POP, null);
            assertEquals(stackMachineService.peekTopElement(), 10);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
        }

        @Test
        @DisplayName("Testing Clear operation")
        public void testStackIsCleared() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testStackIsCleared");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            stackMachineService.performAction(Instructions.CLEAR, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 0);
        }

        @Test
        @DisplayName("Testing Add operation")
        public void testAdd() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testAdd");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),20);

            stackMachineService.performAction(Instructions.ADD, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
            assertEquals(stackMachineService.peekTopElement(),30);
        }


        @Test
        @DisplayName("Testing Multiply operation")
        public void testMulutiply() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testMulutiply");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),20);

            stackMachineService.performAction(Instructions.MUL, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
            assertEquals(stackMachineService.peekTopElement(),200);
        }


        @Test
        @DisplayName("Testing Negation operation")
        public void testNegation() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testNegation");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),20);

            stackMachineService.performAction(Instructions.NEG, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),-20);
        }

        @Test
        @DisplayName("Testing Inversion operation")
        public void testInversion() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testInversion");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),20);

            stackMachineService.performAction(Instructions.INV, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(),0.05);
        }

    }

    // ======= test Undo Operations ====

    @Nested
    @DisplayName("Testing Undo Operation")
    class UndoOperationTest {
        @Test
        @DisplayName("Testing Undo Operation after Push operation")
        public void testUndoOperationAfterPush() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoOperationAfterPush");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.peekTopElement(), 20);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 10);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
        }

        @Test
        @DisplayName("Testing Undo Operation after Pop operation")
        public void testUndoOperationAfterPop() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoOperationAfterPop");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 20);
            stackMachineService.performAction(Instructions.POP, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
            assertEquals(stackMachineService.peekTopElement(), 10);
            stackMachineService.performAction(Instructions.UNDO, null);
            // should still be 2 elements when pop is undone
            assertEquals(stackMachineService.peekTopElement(), 20);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
        }

        @Test
        @DisplayName("Testing Undo Operation after Negation operation")
        public void testUndoAfterNegation() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoAfterNegation");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 20);

            stackMachineService.performAction(Instructions.NEG, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), -20);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 20);
        }

        @Test
        @DisplayName("Testing Undo Operation after Inversion operation")
        public void testUndoAfterInversion() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoAfterInversion");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 20);

            stackMachineService.performAction(Instructions.INV, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 0.05);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 20);
        }

        @Test
        @DisplayName("Testing Undo Operation after Add operation")
        public void testUndoAfterAdd() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoAfterAdd");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 20);

            stackMachineService.performAction(Instructions.ADD, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
            assertEquals(stackMachineService.peekTopElement(), 30);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 20);
        }


        @Test
        @DisplayName("Testing Undo Operation after Multiply operation")
        public void testUndoAfterMulutiply() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoAfterMulutiply");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
            assertEquals(stackMachineService.peekTopElement(), 20);

            stackMachineService.performAction(Instructions.MUL, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 1);
            assertEquals(stackMachineService.peekTopElement(), 200);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 20);
        }

        @Test
        @DisplayName("Testing Undo Operation after Clear operation")
        public void testUndoAfterStackClear() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testUndoAfterStackClear");
            stackMachineService.performAction(Instructions.PUSH, "10");
            stackMachineService.performAction(Instructions.PUSH, "20");
            stackMachineService.performAction(Instructions.CLEAR, null);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 0);
            stackMachineService.performAction(Instructions.UNDO, null);
            assertEquals(stackMachineService.peekTopElement(), 20);
            assertEquals(stackMachineService.getNumberOfElementsInStack(), 2);
        }

    }
    // ======= test Exception scenario ====

    @Nested
    @DisplayName("Testing Exception Scenarios")
    class ExceptionScenarioTest {
        @DisplayName("Testing Exception Scenario for Multiply operation")
        @Test
        public void testThrowsException_MultiplyWithLessThan2Elements() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testThrowsException_MultiplyWithLessThan2Elements");
            stackMachineService.performAction(Instructions.PUSH, "10");
            assertThrows(NotEnoughElementsException.class, () -> stackMachineService.performAction(Instructions.MUL, null));
        }

        @Test
        @DisplayName("Testing Exception Scenario for Add operation")
        public void testThrowsException_AddWithLessThan2Elements() throws NotEnoughElementsException, DivideByZeroException, EmptyStackException, ActionNotSupportedException {
            System.out.println("Running test: testThrowsException_AddWithLessThan2Elements");
            stackMachineService.performAction(Instructions.PUSH, "10");
            assertThrows(NotEnoughElementsException.class, () -> stackMachineService.performAction(Instructions.ADD, null));
        }

        @Test
        @DisplayName("Testing Exception Scenario for Negation operation")
        public void testThrowsException_NegateOnEmptyStack() {
            System.out.println("Running test: testThrowsException_NegateOnEmptyStack");
            assertThrows(NotEnoughElementsException.class, () -> stackMachineService.performAction(Instructions.NEG, null));
        }

        @Test
        @DisplayName("Testing Exception Scenario for Inversion operation")
        public void testThrowsException_InvertOnEmptyStack() {
            System.out.println("Running test: testThrowsException_InvertOnEmptyStack");
            assertThrows(NotEnoughElementsException.class, () -> stackMachineService.performAction(Instructions.NEG, null));
        }

        @Test
        @DisplayName("Testing Exception Scenario for Pop operation")
        public void testThrowsException_PopOnEmptyStack() {
            System.out.println("Running test: testThrowsException_PopOnEmptyStack");
            assertThrows(EmptyStackException.class, () -> stackMachineService.performAction(Instructions.POP, null));
        }

        @Test
        @DisplayName("Testing Exception Scenario for Print operation")
        public void testThrowsException_PrintEmptyStack() {
            System.out.println("Running test: testThrowsException_PrintEmptyStack");
            assertThrows(EmptyStackException.class, () -> stackMachineService.performAction(Instructions.PRINT, null));
        }
    }
}
