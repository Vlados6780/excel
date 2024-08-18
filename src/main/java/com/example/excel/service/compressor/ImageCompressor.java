package com.example.excel.service.compressor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageCompressor {

    // logic compressing image
    public static byte[] compressImage(byte[] imageBytes, int targetWidth, int targetHeight, float quality) throws IOException {
        // convert byte array to BufferedImage
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // calculate aspect ratio to maintain the quality and proportions
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        float aspectRatio = (float) width / height;

        if (width > targetWidth) {
            width = targetWidth;
            height = (int) (targetWidth / aspectRatio);
        }

        if (height > targetHeight) {
            height = targetHeight;
            width = (int) (targetHeight * aspectRatio);
        }

        // resize the image
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // compress the image to a JPEG format to reduce file size
        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", compressedImageStream);

        // return the compressed image bytes
        return compressedImageStream.toByteArray();
    }
}
