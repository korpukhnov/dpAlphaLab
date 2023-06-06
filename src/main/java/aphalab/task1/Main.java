package aphalab.task1;

import aphalab.task1.entity.Document;
import aphalab.task1.repo.DocumentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static final String DOCUMENT_NUMBER = "777";

    private DocumentRepo documentRepo;

    @Autowired
    public void setDocumentRepo(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("EXECUTING : command line runner");
        List<Document> result = documentRepo.findByNumberAndActive(DOCUMENT_NUMBER, true);
        LOGGER.info(result.toString());
    }
}