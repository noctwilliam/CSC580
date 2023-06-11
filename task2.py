import time
import threading

def search(data, start, end, target):
	for i in range(start, end):
		if data[i] == target:
			return i

def parallelSearch(data, target, threadNum):
	threads = []
	for i in range(threadNum):
		# hover over 'Thread' and click 'View source' to read the params needed
		thread = threading.Thread(target=search, args=(data, i * len(data) // threadNum, (i + 1) * len(data) // threadNum, target))
		threads.append(thread)

	# Start all threads
	for thread in threads:
		thread.start()

	# Wait for all threads to finish
	for thread in threads:
		thread.join()


def main():
	data = list(range(100000000)) #100M
	# print(data)
	target = 50000000

	# Create a thread pool with user input as threads
	# threadNum = int(input("Enter the number of threads: "))
	for threadAmt in range (1,17):
		# Perform the parallel search
		print(f"Thread: {threadAmt}")
		start_time = time.time()
		parallelSearch(data, target, threadAmt)
		end_time = time.time()
		parallel_time = end_time - start_time

		# Print the parallel search time
		print("Parallel time: ", parallel_time)

		# Perform the serial search
		serial_start_time = time.time()
		search(data, 0, len(data), target)
		serial_end_time = time.time()

		serial_time = serial_end_time - serial_start_time
		print("Serial time: ", serial_time)

if __name__ == "__main__":
	main()