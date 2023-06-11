import java.util.Scanner;

public class T2_HSMD {
    public static void main(String args[]){
        
        Scanner sc = new Scanner(System.in);

        // System.out.print("Enter an integer: ");
        int key = 9876;//sc.nextInt();

        // Creating ExecutionTimer object
        ExecutionTimer timer = new ExecutionTimer();
        // Start the timer
        timer.start();

        // Create an array with 10,000 values from 1 to 10,000
        int[] array = new int[10000];
        for (int i = 0; i < 10000; i++) {
            array[i] = i + 1;
        }

        // Create three threads for parallel search
        Thread1 thread1 = new Thread1(array, key);
        Thread2 thread2 = new Thread2(array, key);
        Thread3 thread3 = new Thread3(array, key);

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // Wait for all threads to finish
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException IntExp) {
            // Handle the exception if interrupted while waiting
        }

        // Stop the timer
        timer.stop();

        // Display the execution time of the program
        System.out.println("total time: " + timer.getDuration() + " milliseconds");

		sc.close();
    }
}

class Thread1 extends Thread{
    public int searchKey;
    public int index = -1;
    public int[] array1;
    private boolean keyFound = false;

    // Constructor to initialize the array and search key
    public Thread1(int[] array, int searchKey) { 
        array1 = array;
        this.searchKey = searchKey;
    }

    // Run method to start the thread
    public void run() {
        System.out.println("Thread 1 is running........");
        // Loop through the first portion of the array
        for (int i = 0; i < 3999; i++) {
            // Check if the current element is equal to the search key
            if (array1[i] == searchKey) {
                // If it is, update the index and print a message
                index = i;
                System.out.println("Exit from thread 1. Found key in Thread 1 at index : "+index);
            }
        }
        // If the index was not updated, print a message indicating that the key was not found
        if (index == -1)
            System.out.println("There is no key found in Thread 1");

    }

    // Method to check if the key was found
    public boolean isKeyFound() {
        return keyFound;
    }

}

// Thread2 class for searching a key in the second portion of the array
class Thread2 extends Thread{
    public int searchKey;
    public int index = -1;
    public int[] array1;
    private boolean keyFound = false;

    // Constructor to initialize the array and search key
    public Thread2(int[] array, int searchKey) { 
        array1 = array;
        this.searchKey = searchKey;
    }

    // Run method to start the thread
    public void run() {
        System.out.println("Thread 2 is running........");
        // Loop through the second portion of the array
        for (int i = 4000; i < 7999; i++) {
            // Check if the current element is equal to the search key
            if (array1[i] == searchKey) {
                // If it is, update the index and print a message
                index = i;
                System.out.println("Exit from thread 2. Found key in Thread 2 at index : "+index);
            }
        }
        // If the index was not updated, print a message indicating that the key was not found
        if (index == -1)
            System.out.println("There is no key found in Thread 2");



    }

    // Method to check if the key was found
    public boolean isKeyFound() {
        return keyFound;
    }

}

// Thread3 class for searching a key in the third portion of the array
class Thread3 extends Thread{
    public int searchKey;
    public int index = -1;
    public int[] array1;
    private boolean keyFound = false;

    // Constructor to initialize the array and search key
    public Thread3(int[] array, int searchKey) { 
        array1 = array;
        this.searchKey = searchKey;
    }

    // Run method to start the thread
    public void run() {
        System.out.println("Thread 3 is running........");
        // Loop through the third portion of the array
        for (int i = 8000; i < 9999; i++) {
            // Check if the current element is equal to the search key
            if (array1[i] == searchKey) {
                // If it is, update the index and print a message
                index = i;
                System.out.println("Exit from thread 3. Found key in Thread 3 at index : "+index);
            }
        }
        // If the index was not updated, print a message indicating that the key was not found
        if (index == -1)
            System.out.println("There is no key found in Thread 3");

    }

    // Method to check if the key was found
    public boolean isKeyFound() {
        return keyFound;
    }

}


// ExecutionTimer class to measure execution time of code in milliseconds
class ExecutionTimer {
    private long startTime; // start time of code execution in nanoseconds
    private long endTime; // end time of code execution in nanoseconds

    public void start() {
        startTime = System.nanoTime(); // record start time in nanoseconds
    }

    public void stop() {
        endTime = System.nanoTime(); // record end time in nanoseconds
    }

    public long getDuration() {
        return (endTime - startTime) / 1000000; // calculate duration in milliseconds
    }
}