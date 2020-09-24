package com.ijkalra.stackmachine;

import com.ijkalra.stackmachine.controller.StackMachineController;
import com.ijkalra.stackmachine.model.CustomStack;
import com.ijkalra.stackmachine.service.StackMachineService;
import com.ijkalra.stackmachine.utility.Utils;

import java.util.Scanner;

import static com.ijkalra.stackmachine.config.Constants.INFO_MSG;

public class StackMachineApplication {
    public static void main(String[] args) {

        int initialCapacity = 10; //default capacity
        int inputCapacity = 0; // for user input as program argument

        try {
            inputCapacity = Integer.parseInt(args[0]); // read from program argument
        } catch(Exception e){
            System.out.println("INFO: Machine capacity is not configured");
        } finally {
            initialCapacity = inputCapacity > 2 ? inputCapacity: initialCapacity;
        }

        // Initialize Service, Scanner and Controller
        Scanner scanner = new Scanner (System.in);
        StackMachineService stackMachineService = new StackMachineService(new CustomStack(initialCapacity));
        StackMachineController stackMachineController = new StackMachineController(stackMachineService, scanner);

        System.out.printf(INFO_MSG, String.format("Starting machine controller with initial capacity: %s ", initialCapacity));
        Utils.printMenu(); // print menu and start stack machine
        stackMachineController.runStackMachine();

        System.out.println("INFO: Exiting application. GoodBye...");
    }
}
