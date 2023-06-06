package aphalab.task3;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;

public class FileWatcher {

    private final Path resultPath = Paths.get("/Users/akorpukhnov/workspace/tpAlphaLab");
    private final Path resultFile = resultPath.resolve("result.txt");

    public void watch() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            resultPath.register(watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);


            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
    }


    protected boolean pollEvents(WatchService watchService) throws InterruptedException, IOException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for (WatchEvent<?> event : key.pollEvents()) {
            System.out.println(event.context());
            int ln = readLastNumber();

//            writeNumber(nextPrime(ln));
        }
        return key.reset();
    }

    private int readLastNumber() {
        int lines = 0;
        StringBuilder builder = new StringBuilder();
        RandomAccessFile randomAccessFile = null;
        try {
            File file = resultFile.toFile();
            randomAccessFile = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            randomAccessFile.seek(fileLength);

            boolean skip = true;
            for (long pointer = fileLength; pointer >= 0; pointer--) {
                randomAccessFile.seek(pointer);
                char c;
                c = (char) randomAccessFile.read();
                if (c == ' ') break;
                builder.append(c);
            }
            builder.reverse();
            String string = builder.toString();
            System.out.println(Thread.currentThread().getName() + " " + string);
            return Integer.parseInt(string);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
