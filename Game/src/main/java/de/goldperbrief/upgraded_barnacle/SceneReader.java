package de.goldperbrief.upgraded_barnacle;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class SceneReader {

    
    /*
     * Read a Scene-information file (filename.scene)
     * Takes as argument the relative path of the scene file from the /data/scenes
     * directory as argument.
     * The file must be placed in the src/main/resources/data/scenes directory
     */
    public String[] readScene(String pFile) {
        String[] fileContents = read(pFile).split(System.getProperty("line.separator"));
        String[] decodedFile = new String[fileContents.length];
        int index = 0;
        String s;
        String n;
        for (int i = 0; i < fileContents.length; i++) {
            try {
                s = fileContents[i];
                n = fileContents[i+1];
                if (s.startsWith("//")) {
                    // 'tis a comment; do not put this line into the array
                    continue; // Skip this line and move on to the next one
                } else if (s.startsWith("Scene Name:")) {
                    decodedFile[index++] = "sceneName";
                    decodedFile[index++] = n;
                    i++;
                    // System.out.println(s + "\n" + n);
                } else if (s.startsWith("Height:")) {
                    decodedFile[index++] = "height";
                    decodedFile[index++] = n;
                    i++;
                    // System.out.println(s + "\n" + n);
                } else if (s.startsWith("Width:")) {
                    decodedFile[index++] = "width";
                    decodedFile[index++] = n;
                    i++;
                    // System.out.println(s + "\n" + n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Arrays.copyOf(decodedFile, index);
    }

    /*
     * Read a Scene-data file (filename.data)
     * Takes as argument the relative path of the scene file from the /data/scenes
     * directory as argument.
     * The file must be placed in the src/main/resources/data/scenes directory
     */
    public Object[] readData(String pFile) {
        String[] fileContents = read(pFile).split(System.getProperty("line.separator"));
        Object[] decodedFile = new Object[fileContents.length];
        int index = 0;

        for (String s : fileContents) {
            if (s.startsWith("//")) {
                // 'tis a comment; do not put this line into the array
                continue; // Skip this line and move on to the next one
            } else if (s.startsWith("#")) {
                if (s.startsWith("#GLStrt ")) {
                    if (s.substring(8) == "Triangle;") { // A Triangle
                        decodedFile[index++] = new String[]{"GL_START","GL_TRIANGLES"}; // Add the good thing for the triangle into the array
                    }
                } else if (s.startsWith("#Col ")) {
                    s = s.substring(4, s.length()-1);
                    if (s.substring(1,2) == "f") {
                        decodedFile[index] = getArrayType(s.substring(1,2),Integer.parseInt(s.substring(0,1)));
                    } 
                }
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

    private Object[] getArrayType(String s, int length) {
        if (s == "f") {
            return new Float[length];
        } else if (s == "d") {
            return new Double[length];
        } else if (s == "b") {
            return new Boolean[length];
        } else if (s == "S") {
            return new String[length];
        } else {
            return new Object[length];
        }
    }

}