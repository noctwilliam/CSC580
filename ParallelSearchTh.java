import java.util.Scanner;
/**
 * @author harith
 */

public class ParallelSearchTh extends Thread {
	private long [] data;
	private int target;
	private int startIndex;
	private int endIndex;
	private boolean found = false;
	private int index = -1;
	
	public ParallelSearchTh(long [] data, int target, int startIndex, int endIndex) {
		this.data = data;
		this.target = target;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public void run() {
		// linear search implementation
		for (int i = startIndex; i < endIndex; i++) {
			if (data[i] == target) {
				found = true;
				index = i;
				break;
			}
		}
	}
	
	/**
	 * Calculate and display the time taken to execute the program
	 * @param startTime
	 * @param endTime
	 */
	public static void calculateTime (long startTime, long endTime){
		System.out.println("Time taken: " + (endTime - startTime) + " ns\nTime taken: " + (endTime - startTime) / 1000000 + " ms");
	}

	/**
	 * Serial search implementation
	 * @param data
	 * @param target
	 * @return Display the time taken and whether the target is found or not
	 */
	public static void serialOperations(long [] data, int target){
		System.out.println("SERIAL OPERATION\n------------------------");
		long startTime = System.nanoTime();
		// linear search implementation
		for (int i = 0; i < data.length; i++) {
			if (data[i] == target) {
				System.out.println("Found at index: " + i);
				break;
			} else {
				if (i == data.length - 1) {
					System.out.println("Not found");
				}
			}
		}
		long endTime = System.nanoTime();
		calculateTime(startTime, endTime);
	}
	
	/**
	 * Parallel search implementation
	 * @param data - the array to be searched
	 * @param target - the target to be searched for
	 * @param threadNum - the number of threads to be used
	 * 
	 * @return Display results of the search and the time taken
	 */
	public static void parallelOperation(long [] data, int target, int threadNum){
		System.out.println("PARALLEL OPERATION\n------------------------\nThread amount: " + threadNum);

		long start_time = System.nanoTime();
		// Create multiple threads according to threadNum
		ParallelSearchTh[] threads = new ParallelSearchTh[threadNum];
		
		for (int i = 0; i < threadNum; i++) {
			// Create a thread for each section of the array
			int startIndex = i * data.length / threadNum;
			int endIndex = (i + 1) * data.length / threadNum;
			// Initialize the value of the objects inside the array by calling the normal constructor
			threads[i] = new ParallelSearchTh(data, target, startIndex, endIndex);
			// Start the thread
			// threads[i].start();
			// System.out.println(threads[i] + " started...");
		}
		// Start all threads after their initialization
		for (int i = 0; i < threadNum; i++) {
			threads[i].start();
		}
		
		// Wait for all threads to finish
		for (int i = 0; i < threadNum; i++) {
			try {
				// joining the thread
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int firstIndex = -1;
		for (int j = 0; j < threadNum; j++) {
			if (threads[j].found) {
				// Get the index of the first thread that found the target
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
		
		long end_time = System.nanoTime();
		calculateTime(start_time, end_time);
	}
	
	public static void main(String[] args) {
		long [] data = new long [100000000]; //100M
		Scanner in = new Scanner(System.in);
		
		// Initialize the data array
		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}
		
		// Initialize the target
		int target = 99760000; //99,760,000
		
		// Initialize and start multiple threads for parallel search
		System.out.print("Enter thread amount (<=16): ");
		int threadNum = in.nextInt(); //Runtime.getRuntime().availableProcessors(); -> Use available processors as thread count
		for (int i = 1; i <= threadNum; i++) {
			System.out.println("EXECUTION " + i + "\n========================");
			parallelOperation(data, target, i);
			System.out.println(); //to add space
			serialOperations(data, target);
			System.out.println();
		}
		in.close();
	}
}
