import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelSearch {
	static boolean found = false;
	static int index = -1;

	public static void parallelSearch(int[] data, int target, int threadNum) {
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		
		// Perform the parallel search
		for (int i = 0; i < threadNum; i++) {
			executor.execute(new LinearSearch(data, i * data.length / threadNum, (i + 1) * data.length / threadNum, target));
		}
		executor.shutdown();
		
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int target;
		long parallel_time = 0, serial_time = 0, nano_parallel_time = 0, nano_serial_time = 0, start_time = 0, end_time = 0, nano_start_time = 0, nano_end_time = 0;
		int[] data = new int[10000];
		Scanner in = new Scanner(System.in);
		
		// initialize the data array
		for (int i = 0; i < data.length; i++) {
			data[i] = i;
		}

		// initialize the target
		target = 9876;

		// // get the threads limit
		// System.out.print("Enter the thread limit: ");
		// int threadLimit = in.nextInt();

		for (int threadNum = 1; threadNum <= 4; threadNum++){
			
			// Perform the parallel search
			nano_start_time = System.nanoTime();
			start_time = System.currentTimeMillis();
			parallelSearch(data, target, threadNum);
			nano_end_time = System.nanoTime();
			end_time = System.currentTimeMillis();
			parallel_time = end_time - start_time;
			nano_parallel_time = nano_end_time - nano_start_time;
			
			System.out.println("\nThread: " + threadNum);
			System.out.println("Parallel time: " + parallel_time + " ms\nParallel time: " + nano_parallel_time + " ns");
			
			// Perform the serial search
			nano_start_time = System.nanoTime();
			start_time = System.currentTimeMillis();
			LinearSearch search = new LinearSearch(data, 0, data.length, target);
			search.run();
			end_time = System.currentTimeMillis();
			nano_end_time = System.nanoTime();
			serial_time = end_time - start_time;
			nano_serial_time = nano_end_time - nano_start_time;
			
			System.out.println("\nSerial time: " + serial_time + " ms\nSerial time: " + nano_serial_time + " ns");
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