/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
/**
*
* @author kudin
*/
public class ParallelSearchTask2 {
	
	/**
	* @param args the command line arguments
	*/
	public static class ParallelSearch extends Thread {
		//Array of data
		private int[] array;
		//Start value
		private int start;
		//End value
		private int end;
		//Our target to find
		private int target;
		//True if found, False if not found
		private boolean found;
		
		//Normal Constructor
		public ParallelSearch(int[] array, int start, int end, int target) {
			this.array = array;
			this.start = start;
			this.end = end;
			this.target = target;
			this.found = false;
		}
		
		//Getter for found
		public boolean isFound() {
			return found;
		}
		
		@Override
		public void run() {
			//If true, found = true and break the loop
			for (int i = start; i < end; i++) {
				if (array[i] == target) {
					found = true;
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// Generate an array with 10,000 values
		int[] array = generateArray(100000000);
		// The value to search for
		int target = 97000000;
		boolean found = false;
		long parallelStartTime = 0, parallelEndTime = 0;
		
		// Number of available processor cores
		int numThreads = 25;
		for (int j = 1; j <= numThreads; j++) {
			int chunkSize = array.length / j;
			System.out.println("Chunk size: " + chunkSize);
			parallelStartTime = System.nanoTime();
			ParallelSearch[] threads = new ParallelSearch[j];
			
			// Create and start the threads
			for (int i = 0; i < j; i++) {
				int start = i * chunkSize;
				System.out.println("Start: " + start);
				//If i = thread, return end array.length, else i * chunkSize
				int end = (i == j - 1) ? array.length : (i + 1) * chunkSize;
				System.out.println("End: " + end);
				
				threads[i] = new ParallelSearch(array, start, end, target);
				//Start or creating and invocating a the thread object
				threads[i].start();
			}
			
			// Wait for all threads to finish
			for (ParallelSearch thread : threads) {
				try {
					// joining the thread
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// Check if the target value was found
			for (ParallelSearch thread : threads) {
				//Need only 1 of the processes to be true to find determine the target found or not
				if (thread.isFound()) {
					found = true;
					break;
				}
			}
			parallelEndTime = System.nanoTime();
			long serialStartTime = System.nanoTime();
			boolean serial = findTarget(array.length,target);
			long serialEndTime = System.nanoTime();
			
			System.out.println("\nThread: " + j);
			System.out.println("Parallel Result: " + found);
			System.out.println("Parallel time: " + (parallelEndTime - parallelStartTime) + " ns");
			System.out.println("Serial Result: " + serial);
			System.out.println("Serial time: " + (serialEndTime - serialStartTime) + " ns");
		}
	}
	
	//Generate Array based on size given
	private static int[] generateArray(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		return array;
	}
	
	//Serial search function
	private static boolean findTarget(int size, int target) {
		int[] array = new int[size];
		boolean found = false;
		for (int i = 0; i < size; i++) {
			if(target == i)
			{
				found = true;
				return found;
			}
		}
		return found;
	}
}