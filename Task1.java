import java.util.Scanner;

class first extends Thread {
	
	public int D;
	public int E;
	public double tf;
	
	public first(int D, int E) {
		this.D = D;
		this.E = E;
		tf = (D / Math.pow(E, 2));
	}

	public void run() {
		System.out.println("Exit from first: " + this.tf);
	}
}

class second extends Thread {
	
	public int A;
	public int B;
	public double ts;
	
	public second(int A, int B) {
		this.A = A;
		this.B = B;
		ts = (A * B);
	}
	
	public void run() {
		System.out.println("Exit from second: " + this.ts);
	}
}

class third extends Thread {
	
	public int Y;
	public int Z;
	public double tt;
	
	public third(int Y, int Z) {
		this.Y = Y;
		this.Z = Z;
		tt = (Y % Z);
	}
	
	public void run() {
		System.out.println("Exit from third: " + this.tt);
	}
}

public class Task1 {
	
	public static int serialCompute(int A, int B, int C, int D, int E, int Y, int Z){
		return A * B + C * (D / E * E) + Y % Z; //malas nak guna mathpow
	}

	public static int parallelCompute(int A, int B, int C, int D, int E, int Y, int Z){
		double totalFinal = 0.0;
		first f = new first(D, E);
		second s = new second(A, B);
		third t = new third(Y, Z);
		f.start();
		s.start();
		t.start();

		try {
			f.join();
			s.join();
			t.join();

			totalFinal = s.ts + f.tf + t.tt;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return (int) totalFinal;

		// return (int) (f.tf + s.ts + t.tt);
	}
	public static void main (String [] args){
		//A * B + C * (D / E ^ 2) + Y % Z
		Scanner scan = new Scanner (System.in);
		System.out.print("A: ");
		int A = scan.nextInt();
		System.out.print("B: ");
		int B = scan.nextInt();
		System.out.print("C: ");
		int C = scan.nextInt();
		System.out.print("D: ");
		int D = scan.nextInt();
		System.out.print("E: ");
		int E = scan.nextInt();
		System.out.print("Y: ");
		int Y = scan.nextInt();
		System.out.print("Z: ");
		int Z = scan.nextInt();

		// add a timer to measure time performance  
		long serialStartTime = System.nanoTime();
		int resultSerial = serialCompute(A, B, C, D, E, Y, Z);
		long serialEndTime = System.nanoTime();
		long serialMsTime = (long) ((serialEndTime - serialStartTime) / 1000000);

		long parallelStartTime = System.nanoTime();
		int resultParallel = parallelCompute(A, B, C, D, E, Y, Z);
		long parallelEndTime = System.nanoTime();
		long parallelMsTime = (long) ((parallelEndTime - parallelStartTime / 1000000));

		System.out.println("Serial result: " + resultSerial);
		System.out.println("Serial time: " + (serialEndTime - serialStartTime) + " ns\t\t" + serialMsTime + " ms");
		// convert to ms
		System.out.println("Parallel result: " + resultParallel);
		System.out.println("Parallel time: " + (parallelEndTime - parallelStartTime) + " ns\t\t" + parallelMsTime + " ms");

		scan.close();
	}
}
