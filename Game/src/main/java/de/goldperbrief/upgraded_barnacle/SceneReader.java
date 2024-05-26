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
        String[] decodedFile = new String[fileContents.length];
        int index = 0;
        String s;
        for (int i = 0; i < fileContents.length; i++) {
            s = 
            if (s.startsWith("//")) {
                // 'tis a comment; do not put this line into the array
                continue; // Skip this line and move on to the next one
            } else if (s.startsWith("\"Scene Name:\"")) {
                decodedFile[index++] = "Scene Name";
                decodedFile[index++] = s;
                i++;
            } else if (s.startsWith("\"Height:\"")) {
                decodedFile[index++] = "Height";
                decodedFile[index++] = s;
                i++;
            } else if (s.startsWith("\"Width:\"")) {
                decodedFile[index++] = "Width";
                decodedFile[index++] = s;
                i++;
            } else if (s.startsWith)
        }
        return Arrays.copyOf(decodedFile, index);
    }

    /*
     * Read a Scene-data file (filename.data)
     * Takes as argument the relative path of the scene file from the /data/scenes
     * directory as argument.
     * The file must be placed in the src/main/resources/data/scenes directory
     */
    public String[] readData(String pFile) {
        String[] fileContents = read(pFile).split(System.getProperty("line.separator"));
        String[] decodedFile = new String[fileContents.length];
        int index = 0;

        for (String s : fileContents) {
            if (s.startsWith("//")) {
                // 'tis a comment; do not put this line into the array
                continue; // Skip this line and move on to the next one
            } else {
                decodedFile[index++] = s;
            }
        }
        return Arrays.copyOf(decodedFile, index);
    }

    private String read(String pFileName) {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        String fileToBeRead = "/data/scenes/" + pFileName;
        try {
            InputStream inputStream = getClass().getResourceAsStream(fileToBeRead);
            if (inputStream == null) {
                System.err.println("File not found: " + fileToBeRead);
            } else {
                Scanner scanner = new Scanner(inputStream, "UTF-8");
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append(NL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text.toString();

    }

}