import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class ImageContrastEnhancer {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final String INPUT_IMAGE = "image.jpg";
    private static final String OUTPUT_IMAGE = "output_image.jpg";

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedImage image = ImageIO.read(new File(INPUT_IMAGE));
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        //Создаём параллельные процессы относительно строк
        int rowsPerThread = image.getHeight() / NUM_THREADS;
        int startRow = 0;
        int endRow = rowsPerThread;
        for (int i = 0; i <= NUM_THREADS; i++) {
            executor.execute(new ContrastEnhancerTask(image, startRow, endRow));
            startRow = endRow;
            endRow = (i == NUM_THREADS - 1) ? image.getHeight() : endRow + rowsPerThread;
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        ImageIO.write(image, "png", new File(OUTPUT_IMAGE));

        System.out.println("Увеличение контраста прошло успешно");
    }
}