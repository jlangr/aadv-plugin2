package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

class ImageLoader {
   private ImageLoader() {}

   public static ImageIcon loadImageIcon(String resourcePath) {
      var imageUrl = getResourceUrl(resourcePath);
      if (imageUrl == null) return null;
      try {
         return new ImageIcon(ImageIO.read(imageUrl));
      } catch (IOException e) {
         e.printStackTrace(); // Consider logging this in a real application
         return null;
      }
   }

   private static URL getResourceUrl(String resourcePath) {
      return ImageLoader.class.getResource(resourcePath);
   }
}