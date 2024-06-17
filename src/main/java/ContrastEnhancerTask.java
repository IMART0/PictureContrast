import java.awt.image.BufferedImage;

public class ContrastEnhancerTask implements Runnable {
    private final BufferedImage image;
    private final int startRow;
    private final int endRow;

    public ContrastEnhancerTask(BufferedImage image, int startRow, int endRow) {
        this.image = image;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        for (int row = startRow; row < endRow; row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                int rgb = image.getRGB(col, row);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int contrastCoefficient = 70;
                red = increaseContrast(red, contrastCoefficient);
                blue = increaseContrast(blue, contrastCoefficient);
                green = increaseContrast(green, contrastCoefficient);

                image.setRGB(col, row, (red << 16) | (green << 8) | blue);
            }
        }
    }

    private int increaseContrast(int colorValue, int contrastCoefficient) {
        int newColorValue = (colorValue * 100 - 128 * contrastCoefficient) / (100 - contrastCoefficient);
        if (newColorValue > 255)
            return 255;
        else return Math.max(newColorValue, 0);
    }
}