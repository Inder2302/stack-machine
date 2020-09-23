package com.ijkalra.stackmachine.model;

import com.ijkalra.stackmachine.exception.DivideByZeroException;
import com.ijkalra.stackmachine.exception.EmptyStackException;
import com.ijkalra.stackmachine.exception.NotEnoughElementsException;

import static com.ijkalra.stackmachine.config.Constants.*;

public class CustomStack {

    private int primaryStackTop; // pointer to the top of primary stack
    private int secondaryStackTop; // pointer to the top of secondary(backup) stack
    private final int initialCapacity; // initial capacity of the stack. Kept as final (immutable)
    private double[] primaryStack; // array of double to work as primary stack
    private double[] secondaryStack; // array of double to work as secondary stack

    public CustomStack(int initialCapacity) {
        this.primaryStackTop = -1;
        this.secondaryStackTop = -1;
        this.initialCapacity = initialCapacity;
        this.primaryStack = new double[initialCapacity];
        this.secondaryStack = new double[initialCapacity];
    }

    // Regular push method with backup
    public void push(double num){
        push(num, true);
    }

    /*
    Method: Overloaded Push method
    args: double, boolean
    Desc:
    This method is called with backupRequired = false internally by other methods which
    perform multiple push and pop operations, like adding, multiplying etc.

    This is to avoid taking backup multiple times in same transaction by pop and push.
    When calling with backupRequired = false, we need to make sure to explicitly call backup method before pushing something to stack.
     */
    private void push(double num, boolean backupRequired){
        // backup is not required when
        if (backupRequired) {
            backUpPrimaryStack();
        }

        // pre-increment Top pointer when pushing to stack
        primaryStack[++primaryStackTop] = num;

        // when the stack is 80% full,
        // create a new array with bigger size and copy over the elements from current stacks.
        // this gives the effect of expanding the stack when its nearing capacity.
        if(isEightyPercentFull()) {
            double[] primaryStackWithMoreCap = new double[primaryStack.length + initialCapacity];
            double[] secondaryStackWithMoreCap = new double[primaryStack.length + initialCapacity];
            System.arraycopy(primaryStack,0,primaryStackWithMoreCap,0,primaryStackTop + 1);
            System.arraycopy(secondaryStack,0,secondaryStackWithMoreCap,0,secondaryStackTop + 1);
            primaryStack = primaryStackWithMoreCap;
            secondaryStack = secondaryStackWithMoreCap;
        }
    }

    // Regular pop method with backup
    public double pop() throws EmptyStackException {
        return pop(true);
    }

    /*
    Method: Overloaded Push method
    Args: boolean
    Desc:
    This method is called with backupRequired = false internally by other methods which
    perform multiple push and pop operations, like adding, multiplying etc.

    This is to avoid taking backup multiple times in same transaction by multiple pops and push.
    When calling with backupRequired = false, we need to make sure to explicitly call backup method before popping something out of stack.
     */
    private double pop(boolean backUpRequired) throws EmptyStackException{
        if (isEmpty()) {
            throw new EmptyStackException(ERR_MSG_STACK_IS_EMPTY);
        }
        if (backUpRequired) {
            backUpPrimaryStack();
        }
        // if required we can shrink the stack size just like we are expanding in push operation
        // post removing from stack, decrement Top pointer.
        // The value is not actually removed from the array, but as top is pointing to new (lower) number, thats all we need.
        return primaryStack[primaryStackTop--];
    }

    /*
    Method: peek
    Args: None
    Returns whatever is on top of stack without modifying the top pointer
     */
    public double peek() throws EmptyStackException{
        if (isEmpty()) {
            throw new EmptyStackException(ERR_MSG_STACK_IS_EMPTY);
        }
        return primaryStack[primaryStackTop];
    }


    /*
    Method: show
    Args: None
    Desc:
    Prints out the stack values.
    (It prints the array in inverse order to give the effect of stack)
     */
    public void show() throws EmptyStackException{
        if (!isEmpty()) {
            System.out.println("--- Top of stack ---");
            for (int i = primaryStackTop; i >= 0; i--) {
                System.out.printf("\t%.3f\n", primaryStack[i]);
            }
            System.out.println("--- Bottom of stack ---");
        }
        else {
            throw new EmptyStackException(ERR_MSG_STACK_IS_EMPTY);
        }
    }

    // points the top pointer to "-1" to give the effect of clear stack
    public void clearStack() {
        primaryStackTop = -1;
    }

    /*
    This method is used to check if the stack has enough elements for an operation
     used by add and multiply functions
     */
    private boolean hasEnoughElements(int reqNumber) {
        return primaryStackTop + 1 >= reqNumber;
    }

    /*
    Method: isEmpty
    Args: None
    Checks if primary stack is empty by checking Top pointer.
     */
    private boolean isEmpty() {
        return primaryStackTop == -1 ;
    }


    /*
    Method: multiplyTopTwoElements
    Desc:
    Take out the top 2 elements from the stack and push back their multiplication result onto stack.
     */
    public void multiplyTopTwoElements() throws NotEnoughElementsException, EmptyStackException {
        // Check if we have at least 2 elements in the stack to perform the multiply operation
        if (hasEnoughElements(2)){

            // take backup once before the operation
            backUpPrimaryStack();

            // pop top 2 elements and push it back to stack.
            // Use boolean flag to avoid taking backup multiple times.
            push(pop(false) * pop(false), false);
        }
        else {
            throw new NotEnoughElementsException(ERR_MSG_NOT_ENOUGH_ELEMENTS);
        }
    }

    /*
    Method: addTopTwoElements
    Desc:
    Take out the top 2 elements from the stack and push back their addition onto stack.
     */
    public void addTopTwoElements() throws NotEnoughElementsException, EmptyStackException{
        // Check if we have at least 2 elements in the stack to perform the addition operation
        if (hasEnoughElements(2)) {
            // take backup once before the operation
            backUpPrimaryStack();

            // pop top 2 elements and push it back to stack.
            // Use boolean flag to avoid taking backup multiple times.
            push(pop(false) + pop(false), false);
        }
        else {
            throw new NotEnoughElementsException(ERR_MSG_NOT_ENOUGH_ELEMENTS);
        }
    }

    /*
    Undo last operation by copying over the secondary array to primary array
     */
    public void undoLastOperation() {
        // do not copy if secondary array is empty
        if (secondaryStackTop != -1 ) {
            System.arraycopy(secondaryStack, 0, primaryStack, 0, secondaryStackTop + 1);
        }
        // move the top of primary array to secondary array
        primaryStackTop = secondaryStackTop;

        // lower the top pointer of secondary array
        if(secondaryStackTop >=0){
            secondaryStackTop--;
        }
    }

    /*
    Take backup of primary stack to secondary by copying the array elements.
     */
    private void backUpPrimaryStack() {
        System.arraycopy(primaryStack,0,secondaryStack,0,primaryStackTop + 1);
        secondaryStackTop = primaryStackTop;
    }

    /*
    Negate the top element of stack and push the result back to stack
     */
    public void negateTopElement() throws NotEnoughElementsException, EmptyStackException {
        if (!isEmpty()) {
            // does not make sense to negate zero
            if (peek() != 0) {
                // take back up before pushing
                // call push and pop with backup flag = false.
                backUpPrimaryStack();
                // multiply the top element with -1 to get negation
                push(-1 * pop(false), false);
            }
        } else {
            throw new NotEnoughElementsException(ERR_MSG_NOT_ENOUGH_ELEMENTS);
        }
    }

    /*
    invert the top element of stack and push the result back to stack
     */
    public void invertTopElement() throws EmptyStackException, DivideByZeroException, NotEnoughElementsException {
        if (!isEmpty()) {
            // Do not attempt to invert 0
            if (peek() == 0) {
                throw new DivideByZeroException(ERR_MSG_DIVIDE_BY_ZERO);
            }
            else {
                // take back up before calling push and pop
                // call push and pop with backup flag = false.
                backUpPrimaryStack();
                push(1 / pop(false), false);
            }
        } else {
            throw new NotEnoughElementsException(ERR_MSG_NOT_ENOUGH_ELEMENTS);
        }
    }

    // Check if the stack is more than 80% full
    private boolean isEightyPercentFull() {
        return  (((double)primaryStackTop + 1)/primaryStack.length) >= 0.8;
    }

}
