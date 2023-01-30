package URLToFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  // method to copy the data from the URL to the file
  private static void copyStream(InputStream in, OutputStream out) throws IOException {
      int oneByte = in.read();
      while (oneByte >= 0) { // negative value indicates end-of-stream
          out.write(oneByte);
          oneByte = in.read();
      }
  }
  public static void main(String[] args) {
      InputStream input = null;
      OutputStream output = null;
      // declaring a URL and URL string
      URL url = null;
      String urlString;
      // declaring a File and file name string
      
      String fileName;
      try {
          // read the URL and file name as strings from the user
          System.out.println("Enter the URL: ");
          urlString = System.console().readLine();
          System.out.println("Enter the file name: ");
          fileName = System.console().readLine();

          // Create the URL object with the URL string provided by the user
          url = new URL(urlString);

          // Get the input stream from the URL
          input = url.openStream();
          // Create the "files" directory in the class file's directory if it doesn't already exist
          File fileDirectory = new File(System.getProperty("user.dir"), "./files");
          if (!fileDirectory.exists()) {
              fileDirectory.mkdir();
          }

          // Get the output stream from the file, saving it in the "files" directory
          Path filePath = Paths.get(System.getProperty("user.dir"), "/files", fileName+".html");
          // This gets all the data from a webpage and creates a file
          output = new FileOutputStream(filePath.toFile());

          // Copy the data from the URL to the file
          copyStream(input, output);
          
      } catch (MalformedURLException e) {
          System.out.println("Error: Invalid URL");
      } catch (IOException e) {
          System.out.println("Error: Failed to read data from URL or write data to file");
      } finally {
          // Close the streams if they were successfully opened
          if (input != null) {
              try {
                  input.close();
              } catch (IOException e) {
                  System.out.println("Error: Failed to close input stream");
              }
          }
          if (output != null) {
              try {
                  output.close();
              } catch (IOException e) {
                  System.out.println("Error: Failed to close output stream");
              }
          }
      }
  }
}
