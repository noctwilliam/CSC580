import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelSearch {
	static boolean found = false;
	static long mula = 0, habeh = 0;
	static int index = -1;
	static ExecutorService executor;
	
	public static void parallelSearch(int[] data, int target, int threadNum) {
		try {
			executor = Executors.newFixedThreadPool(threadNum);
			
			// Perform the parallel search
			for (int i = 0; i < threadNum; i++) {
				int chunkSize = data.length / threadNum;
				int startIndex = i * chunkSize;
				int endIndex = (i == threadNum - 1) ? data.length : (i + 1) * chunkSize;
				// System.out.println("Start: " + startIndex + "\nEnd: " + endIndex);
				executor.execute(new LinearSearch(data, startIndex, endIndex, target));
			}
			executor.shutdown();
		} catch (Exception e){
			e.printStackTrace();
		}
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void calculateTime (long startTime, long endTime){
		System.out.println("Time taken: " + (endTime - startTime) + " ns\nTime taken: " + (endTime - startTime) / 1000000 + " ms");
	}
	
	public static void parallelTimeStore(long nano_start_time, long nano_end_time) {
		try {
			// Create a new file
			File file = new File("parallel-times.txt");
			
			// Check if the file already exists
			if (file.exists()) {
				// Append the data to the file
				try (FileWriter writer = new FileWriter(file, true)) {
					long mili = (nano_end_time - nano_start_time) / 1000000;
					writer.write(mili + "\n");
				}
			} else {
				// Create a new file and write the data to it
				try (FileWriter writer = new FileWriter(file)) {
					long mili = (nano_end_time - nano_start_time) / 1000000;
					writer.write(mili + "\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}
	
	
	public static void serialTimeStore(long nano_start_time, long nano_end_time) {
		try {
			// Create a new file
			File file = new File("serial-times.txt");
			
			// Check if the file already exists
			if (file.exists()) {
				// Append the data to the file
				try (FileWriter writer = new FileWriter(file, true)) {
					long mili = (nano_end_time - nano_start_time) / 1000000;
					writer.write(mili + "\n");

				}
			} else {
				// Create a new file and write the data to it
				try (FileWriter writer = new FileWriter(file)) {
					long mili = (nano_end_time - nano_start_time) / 1000000;
					writer.write(mili + "\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		int target;
		long nano_start_time = 0, nano_end_time = 0;
		int[] data = new int[100000000]; //100M
		Scanner in = new Scanner(System.in);
		
		// initialize the data array
		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}
		
		// initialize the target
		target = 98760000; // 98.76M
		int limit = 10000;
		
		for (int threadNum = 1; threadNum <= limit; threadNum++){
			
			// Perform the parallel search
			System.out.println("\nThread: " + threadNum);
			
			nano_start_time = System.nanoTime();
			parallelSearch(data, target, threadNum);
			nano_end_time = System.nanoTime();
			parallelTimeStore(nano_start_time, nano_end_time);
			calculateTime(nano_start_time, nano_end_time);
			
			// Perform the serial search
			nano_start_time = System.nanoTime();
			LinearSearch search = new LinearSearch(data, 0, data.length, target);
			search.run();
			nano_end_time = System.nanoTime();
			serialTimeStore(nano_start_time, nano_end_time);
			calculateTime(nano_start_time, nano_end_time);
		}
		in.close();
	}
	
	static class LinearSearch implements Runnable {
		int[] data;
		int start;
		int end;
		int target;
		
		public LinearSearch(int[] data, int start, int end, int target) {
			this.data = data;
			this.start = start;
			this.end = end;
			this.target = target;
		}
		
		public void run() {
			for (int i = start; i < end; i++) {
				if (data[i] == target) {
					found = true;
					index = i;
					if (found){
						System.out.println("Found at index: " + index);
						break;
					} else{
						System.out.println("Not found");
					}
					break;
				}
			}
		}
	}
}