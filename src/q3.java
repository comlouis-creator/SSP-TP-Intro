import java.util.concurrent.locks.ReentrantReadWriteLock;

public class q3 {
	
    private static long sharedVariable = 0;
    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
    	
    	long startTime = System.nanoTime();
    	
        // create 5 threads to increment sharedVariable
        for (int i = 0; i < 5; i++) {
            new IncrementThread().start();
        }

        // create 15 threads to read and output sharedVariable
        for (int i = 0; i < 15; i++) {
            new ReadThread().start();
        }

        // wait for all threads to finish
        Thread.sleep(5000);

        System.out.println("Final value of sharedVariable: " + sharedVariable);
        
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Execution time: " + elapsedTime + " nanoseconds");
    }

    static class IncrementThread extends Thread {
        public void run() {
            for (int i = 0; i < 100000; i++) {
                rwLock.writeLock().lock();
                try {
                    sharedVariable++;
                } finally {
                    rwLock.writeLock().unlock();
                }
            }
        }
    }

    static class ReadThread extends Thread {
        public void run() {
            for (int i = 0; i < 100000; i++) {
                if (i % 20000 == 0) {
                    rwLock.readLock().lock();
                    try {
                        System.out.println(getName() + " read " + sharedVariable);
                    } finally {
                        rwLock.readLock().unlock();
                    }
                }
            }
        }
    }
}
