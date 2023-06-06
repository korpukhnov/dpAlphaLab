package aphalab.task3;

public class Main {

    public static void main(String[] args) {

        NumberWriter writer1 = new NumberWriter("thread1.txt", 2);
        NumberWriter writer2 = new NumberWriter("thread2.txt", 3);

        writer1.initializeFiles(2);
        writer2.initializeFiles(3);

        new Thread(writer1).start();
        new Thread(writer2).start();
//        try {
//            Path path = Paths.get("/Users/akorpukhnov/workspace/tpAlphaLab/result.txt");
//            Files.writeString(path, "--", StandardCharsets.ISO_8859_1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
