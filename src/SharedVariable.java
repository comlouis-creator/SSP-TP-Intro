public class SharedVariable {
    private static long sharedVariable = 0;

    public static void main(String[] args) {
        // create 5 threads to increment sharedVariable
        for (int i = 0; i < 5; i++) {
            new IncrementThread().start();
        }

        // create 15 threads to read and output sharedVariable
        for (int i = 0; i < 15; i++) {
            new ReadThread().start();
        }
    }

    static class IncrementThread extends Thread {
        public void run() {
            for (int i = 0; i < 100000; i++) {
                sharedVariable++;
            }
        }
    }

    static class ReadThread extends Thread {
        public void run() {
            for (int i = 0; i < 100000; i++) {
                if (i % 20000 == 0) {
                    System.out.println(getName() + " read " + sharedVariable);
                }
            }
        }
    }
}
