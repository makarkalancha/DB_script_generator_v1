package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mcalancea on 2015-08-03.
 */
public class FileUtils {
    public static boolean createDirectory(String path) {
        boolean result = false;
        File directory = new File(path);
        if(!directory.exists()) {
            result = directory.mkdir();
        }

        return result;
    }

    public static void writeToFile(String fileName, String text) {
        try (
                PrintWriter pwStat = new PrintWriter(fileName);
        ) {
            pwStat.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileFromJar(InputStream targetPath, String destinationPath) {
        int bytesRead;
        try (
                PrintWriter pw = new PrintWriter(new File(destinationPath));
        ){
//            Files.copy(Paths.get(targetPath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
//            Files.copy(new File(targetPath.toURI()).toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            while ((bytesRead = targetPath.read()) != -1) {
                pw.append((char)bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readOrderFile(String fileWithScriptOrder, List<String> listOrder){
        try (
                BufferedReader br = new BufferedReader(new FileReader(fileWithScriptOrder));
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if(line.length() > 0 ) {
                    listOrder.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
