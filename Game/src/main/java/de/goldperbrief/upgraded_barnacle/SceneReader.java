package de.goldperbrief.upgraded_barnacle;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

public class SceneReader {

    /*
     * Read a Scene-information file (filename.scene)
     * Takes as argument the relative path of the scene file from the /data/scenes
     * directory as argument.
     * The file must be placed in the src/main/resources/data/scenes directory
     */
    public String[] readScene(String pFile) {
        String fileToBeRead = "/data/scenes/" + pFile;
        try {
            String[] decodedFile = read(fileToBeRead, "UTF-8").split(System.getProperty("line.separator"));
            return decodedFile;
        } catch (java.io.FileNotFoundException e) {
            System.err.println("File not found: " + fileToBeRead);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{null};
    }

    private String read(InputStream inputStream, String pEncoding) {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        try (Scanner scanner = new Scanner(inputStream, pEncoding)) {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine() + NL);
            }
        }
        return text.toString();
    }

    

}