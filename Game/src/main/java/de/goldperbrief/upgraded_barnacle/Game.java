package de.goldperbrief.upgraded_barnacle;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import de.goldperbrief.upgraded_barnacle.Renderer;
import de.goldperbrief.upgraded_barnacle.SceneReader;

public class Game {

    // The window handle
    private long window;
    private Renderer renderer;
    private SceneReader sceneReader;
    private String currentScene;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        // glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        sceneReader = new SceneReader();
        currentScene = "__selection";

        // Set up an error callback. The default implementation will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(Integer.valueOf(getPropertyFromSceneFile(currentScene,"width")), Integer.valueOf(getPropertyFromSceneFile(currentScene,"height")), getPropertyFromSceneFile(currentScene,"sceneName"), NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        
        GL.createCapabilities();

        renderer = new Renderer();
    }

    private String getPropertyFromSceneFile(String sceneName, String propertyName) {
        String property = "";
        if (!sceneName.endsWith(".scene")) {
            sceneName = sceneName + ".scene";
        }
        String[] sceneInfo = sceneReader.readScene(sceneName);
        for (int i = 0; i < sceneInfo.length; i++) {
            // System.out.println(i + ": " + sceneInfo[i]);
            if (sceneInfo[i] == propertyName && 
                i+1 < sceneInfo.length) {
                property = sceneInfo[i+1];
                break;
            }
        }

        // System.out.println(propertyName + ": " + property);
        return property;
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            renderer.clear();
            renderer.render();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Game().run();
    }
}
