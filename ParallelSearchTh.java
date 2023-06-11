import java.util.Scanner;

public class ParallelSearchTh extends Thread {
    private int[] data;
    private int target;
    private int startIndex;
    private int endIndex;
    private boolean found = false;
    private int index = -1;
    
    public ParallelSearchTh(int[] data, int target, int startIndex, int endIndex) {
        this.data = data;
        this.target = target;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (data[i] == target) {
                found = true;
                index = i;
                break;
            }
        }
    }
    
    public static void calculateTime (long startTime, long endTime){
        System.out.println("Time taken: " + (endTime - startTime) + " ns\nTime taken: " + (endTime - startTime) / 1000000 + " ms");
    }
    
    public static void parallelOperation(int[] data, int target, int threadNum){
        ParallelSearchTh[] threads = new ParallelSearchTh[threadNum];
        
        for (int i = 0; i < threadNum; i++) {
            int startIndex = i * data.length / threadNum;
            int endIndex = (i + 1) * data.length / threadNum;
            threads[i] = new ParallelSearchTh(data, target, startIndex, endIndex);
            threads[i].start();
            System.out.println(threads[i] + " started...");
            
            
        }
        
        // Wait for all threads to finish
        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        int firstIndex = -1;
        for (int j = 0; j < threadNum; j++) {
            if (threads[j].found) {
                firstIndex = threads[j].index;
                break;
            }
        }
        
        // Print the result
        if (firstIndex != -1) {
            System.out.println("Found at index: " + firstIndex);
        } else {
            System.out.println("Not found");
        }
        
        // Find the first occurrence of the target
        
    }
    
    public static void main(String[] args) {
        int[] data = new int[10000];
        Scanner in = new Scanner(System.in);
        
        // Initialize the data array
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        
        // Initialize the target
        // System.out.print("Enter the target (0-9999): ");
        int target = 9876;
        
        
        // Create and start multiple threads for parallel search
        int threadNum = 4; //Runtime.getRuntime().availableProcessors(); // Use available processors as thread count
        // call the parallelOperation function and measure the time execution
        long startTime = System.nanoTime();
        parallelOperation(data, target, threadNum);
        long endTime = System.nanoTime();
        calculateTime(startTime, endTime);
        System.out.println(threadNum);
        
        
        in.close();
    }
}
