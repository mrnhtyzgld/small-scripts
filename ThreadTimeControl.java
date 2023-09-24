import java.util.Random;

public class ThreadTimeControl {
    int threadNumber;
    int[][] storage = new int[10240][10240]; // 2^10*10
    int max;
    CountThread[] threads = new CountThread[threadNumber];

    long begin, end;

    class CountThread extends Thread {
        int turn;
        int max = -1;
        public CountThread(int turn) {
            this.turn = turn;
        }
        @Override
        public void run() {
            for (int y = turn * storage.length / threadNumber; y < (turn + 1) * storage.length / threadNumber; y++) {
                for (int x = 0; x < storage[y].length; x++) {
                    this.max = Math.max(this.max, storage[y][x]);
                }
            }
        }
    }

    public ThreadTimeControl() throws InterruptedException {

        Random r = new Random();

        for (int j = 1; j <= storage.length; j++) {
            if (storage.length % j == 0) {
                threadNumber = j;
                threads = new CountThread[threadNumber];


                for (int y = 0; y < storage.length; y++) {
                    for (int x = 0; x < storage[y].length; x++) {
                        storage[y][x] = Math.abs(r.nextInt());
                    }
                }


                max = -1;
                begin = System.nanoTime();

                for (int y = 0; y < storage.length; y++) {
                    for (int x = 0; x < storage[y].length; x++) {
                        max = Math.max(max, storage[y][x]);
                    }
                }

                end = System.nanoTime();
                long first = end - begin;
                System.out.println(max + " serial\t\t" + (end - begin));


                max = -1;
                begin = System.nanoTime();

                for (int a = 0; a < threadNumber; a++) {
                    threads[a] = new CountThread(a);
                }
                for (int a = 0; a < threadNumber; a++) {
                    threads[a].start();
                }
                for (int a = 0; a < threadNumber; a++) {
                    threads[a].join();
                }
                for (int a = 0; a < threadNumber; a++) {
                    max = Math.max(max, threads[a].max);
                }

                end = System.nanoTime();
                long second = end - begin;
                System.out.println(max + " " + threadNumber + " threads\t" + (end - begin));
                System.out.println((double)first/second);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadTimeControl();
    }
}