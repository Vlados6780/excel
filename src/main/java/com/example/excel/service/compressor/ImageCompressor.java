package com.example.excel.service.compressor;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCompressor {

    // logic with compressing image and rotation

    // logic compressing image
    public static byte[] compressImage(byte[] imageBytes, int targetWidth, int targetHeight, float quality) throws IOException {
        // convert byte array to BufferedImage
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // detect orientation and rotate the image if needed
        int orientation = getOrientation(imageBytes); // method for getting image orientation
        if (orientation == 6) { // if orientation is 6 then a 90-degree clockwise rotation is required
            originalImage = rotateImage(originalImage, 90);
        }

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

    private static BufferedImage rotateImage(BufferedImage originalImage, double angle) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setBackground(Color.BLUE);
        g2d.clearRect(0, 0, rotatedImage.getWidth(), rotatedImage.getHeight());
        g2d.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

    // the method is needed to extract the image orientation from EXIF metadata
    private static int getOrientation(byte[] imageBytes) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
            Metadata metadata = ImageMetadataReader.readMetadata(bais);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                return directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }
        } catch (ImageProcessingException | MetadataException e) {
            e.printStackTrace();
        }
        return 1; // by default, we assume that orientation is 1 (no rotation)
    }
}

