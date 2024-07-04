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
        if (!pFile.endsWith(".scene")) {
            pFile = pFile + ".scene";
        }
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
                    // System.out.println("Comment while parsing file: " + s);
                    continue; // Skip this line and move on to the next one
                } else if (s.startsWith("Scene Name:")) {
                    decodedFile[index++] = "sceneName";
                    decodedFile[index++] = n;
                    i++;
                    // System.out.println(s + "\n" + n);
                } else if (s.startsWith("Height:")) {
                    boolean isValidInt = true;
                    try {
                        Integer.parseInt(n);
                    } catch (NumberFormatException e) {
                        isValidInt = false;
                        continue; // Not a valid Integer - Skip.
                    }
                    if (isValidInt) {
                        decodedFile[index++] = "height";
                        decodedFile[index++] = n;
                        i++;
                    } else {
                        continue; // Skip this line and move on
                    }
                    // System.out.println(s + "\n" + n);
                } else if (s.startsWith("Width:")) {
                    boolean isValidInt = true;
                    try {
                        Integer.parseInt(n);
                    } catch (NumberFormatException e) {
                        isValidInt = false;
                        continue; // Not a valid Integer - Skip.
                    }
                    if (isValidInt) {
                        decodedFile[index++] = "width";
                        decodedFile[index++] = n;
                        i++;
                    } else {
                        continue; // Skip this line and move on
                    }
                    // System.out.println(s + "\n" + n);
                } else {
                    // Something else, that the decoder can't decode
                    continue; // Thus, skip it
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
        if (!pFile.endsWith(".data")) {
            pFile = pFile + ".data";
        }
        String[] fileContents = read(pFile).split(System.getProperty("line.separator"));
        Object[] decodedFile = new Object[fileContents.length];
        de.goldperbrief.upgraded_barnacle.Renderer.numShapes = 0;
        int index = 0;

        for (String s : fileContents) {
            if (s.startsWith("//")) {
                // 'tis a comment; do not put this line into the array
                // Print this comment to the console
                // System.out.println("Comment while parsing file: " + s);
                continue; // Skip this line and move on to the next one
            } else if (s.startsWith("!")) {
                s = s.substring(1);
                if (s.startsWith("GLStrt ")) {
                    decodedFile[index++] = new Object[]{"glBegin", s.substring(7,s.length()-1)};
                    de.goldperbrief.upgraded_barnacle.Renderer.numShapes++;
                    System.out.println("There are now " + de.goldperbrief.upgraded_barnacle.Renderer.numShapes + " shapes to be drawn!");
                } else if (s.startsWith("GL-END")) {
                    decodedFile[index++] = "glEnd";
                }


            } else if (s.startsWith("!GL-END")) {
                decodedFile[index++] = s;
            } else if (s.startsWith("#")) {
                decodedFile[index++] = decodeArray(s);
            } else {
                // cant decode -> ignore line
                continue;
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

    private Object[] decodeArray(String strToDecode) {
        int counter1, counter2, counter3;

        Object[] array = new Object[2];
        Object[] arr1 = getArrayType(strToDecode.substring(6,7),Integer.parseInt(strToDecode.substring(5,6)));

        array[0] = "gl" + getDataType(strToDecode.substring(1,4)) + strToDecode.substring(5,7);

        counter1 = 8;
        counter2 = 11;
        counter3 = 12;

        for (int i = 0; i < arr1.length; i++) {
            if (true) {}
        }

        array[1] = arr1;
        return array;
    }

    private Object[] getArrayType(String s, int length) {
        switch (s) {
        case "f":
            return new Float[length];
        case "d":
            return new Double[length];
        case "b":
            return new Boolean[length];
        case "S":
            return new String[length];
        case "o":
            return new Object[length];
        }
        return new Object[length];
    }

    private String getDataType(String s) {
        switch (s) {
        case "Col":
            return "Color";
        case "Vrt":
            return "Vertex";
        }
        return "";
    }

}