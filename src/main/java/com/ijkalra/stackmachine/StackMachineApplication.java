package com.ijkalra.stackmachine;

import com.ijkalra.stackmachine.model.CustomStack;
import com.ijkalra.stackmachine.service.MenuService;

public class StackMachineApplication {
    public static void main(String[] args) {

        int initialCapacity = 10; //default capacity
        int inputCapacity = 0;

        try {
            inputCapacity = Integer.parseInt(args[0]);
        }
        catch(Exception e){
            System.out.println("INFO: Machine capacity is not configured");
        }
        finally {
            initialCapacity = inputCapacity > 2 ? inputCapacity: initialCapacity;
            System.out.printf("INFO: Starting machine with initial capacity: %s\n", initialCapacity);

            MenuService menuService = new MenuService(new CustomStack(initialCapacity));
            menuService.startStackMachine();
        }

    }
}
