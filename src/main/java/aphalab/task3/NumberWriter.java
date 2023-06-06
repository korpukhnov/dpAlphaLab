package aphalab.task3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

public class NumberWriter implements Runnable {

    public static final int LIMIT = 1000_000;
    public static final String FOLDER_PATH = "/tmp/primes";
    public static final String MAIN_FILE_NAME = "result.txt";
    private final Path resultFile;
    private final Path threadFile;
    private static volatile BufferedWriter resultWriter;
    private static volatile BufferedWriter threadWriter;
    private static final AtomicInteger currentInteger = new AtomicInteger(3);

    public NumberWriter(String threadFileName, int i) {
        Path resultPath = Paths.get(FOLDER_PATH);
        this.threadFile = resultPath.resolve(threadFileName);
        resultFile = resultPath.resolve(MAIN_FILE_NAME);
    }

    @Override
    public void run() {
        boolean finished = false;
        while (!finished) {
            int next = currentInteger.updateAndGet(NumberWriter::nextPrime);
            if (next < LIMIT) {
                writeNumber(next);
            } else {
                try {
                    resultWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                finished = true;
            }
        }
    }

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;

        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    static int nextPrime(int n) {
        if (n <= 1)
            return 2;

        int prime = n;
        boolean found = false;
        while (!found) {
            prime++;
            if (isPrime(prime))
                found = true;
        }
        return prime;
    }

    public void initializeFiles(int i) {
        try {
            if (i == 2) {
                Files.writeString(resultFile, String.valueOf(i), ISO_8859_1, CREATE, TRUNCATE_EXISTING);
                Files.writeString(threadFile, String.valueOf(i), ISO_8859_1, CREATE, TRUNCATE_EXISTING);
                resultWriter = Files.newBufferedWriter(resultFile, UTF_8, SYNC, APPEND);
            } else {
                resultWriter.write(" " + i);
                resultWriter.flush();
                Files.writeString(threadFile, String.valueOf(i), ISO_8859_1, CREATE, TRUNCATE_EXISTING);
                threadWriter = Files.newBufferedWriter(threadFile, UTF_8, SYNC, APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeNumber(int i) {
        try {
            resultWriter.write(" " + i);
            resultWriter.flush();
            threadWriter.write(" " + i);
            threadWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}