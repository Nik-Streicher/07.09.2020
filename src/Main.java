import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        AOC aoc = new AOC();

        Prime prime = new Prime() {
            @Override
            public synchronized void prime() {

                Boolean[] booleans = new Boolean[100];

                Arrays.fill(booleans, true);

                for (int x = 2; x < Math.sqrt(booleans.length); x++) {

                    if (booleans[x]) {

                        for (int n = (x * x); n < booleans.length; n = n + x) {
                            booleans[n] = false;
                        }
                    }
                }
                System.out.print("The prime numbers is:");
                for (int x = 2; x < booleans.length; x++) {
                    if (booleans[x]) {
                        System.out.printf(" %d", x);
                    }
                }
            }
        };


        Divide divide = new Divide() {
            @Override
            public synchronized void divide(Scanner in) {
                try (in) {
                    System.out.print("Zadejte číslo : ");
                    int enteredNumber = in.nextInt();
                    if (enteredNumber % 7 == 0)
                        System.out.printf("číslo %d je dělitelné sedmi", enteredNumber);

                    else System.out.printf("číslo %d není dělitelé sedmi", enteredNumber);
                }
            }
        };

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                prime.prime();
            }
        });


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                divide.divide(new Scanner(System.in));
            }
        });


        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                aoc.calc();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class AOC {
    private final String dir = "D:\\Test";
    private final File file = new File(dir + "\\" + "One" + ".txt");
    private final List<Double> array = new ArrayList<>();

    private void fill(BufferedReader reader) throws IOException, IOException {
        String s;
        while ((s = reader.readLine()) != null) {
            array.add(Double.parseDouble(s));
        }
    }

    public synchronized void calc() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            fill(reader);
            System.out.println("\nResult of AOC is " + array.stream().map(value -> value / 3).map(Math::floor).mapToInt(value -> (int) (value - 2)).sum());
        } catch (Exception ignored) {
            System.err.println("\nRead ERROR,File didn't found");
        }
    }
}

interface Prime {
    void prime();
}


interface Divide {
    void divide(Scanner in);
}