import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
/**
 * Write a description of class Display here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Display
{
    public long window;
    private int length;
    private int heigth;

    public Display(int length, int heigth) {
        this.length = length;
        this.heigth = heigth;
    }

    public void init() {
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initalize GLFW!");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(length, heigth, "test", 0, 0); 
        //glfwGetPrimaryMonitor() Replace for full screen^
        glfwMakeContextCurrent(window);
        if(window == 0) {
            throw new IllegalStateException("Failed to initalize Window!");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - length) / 2, (videoMode.height() - heigth) / 2);

        glfwShowWindow(window); 

    }
    public boolean isRunning() {
        return(!glfwWindowShouldClose(this.window));
    }

    public void update() {
        glfwSwapBuffers(window); 
        glfwPollEvents();
    }

    public void terminate() {
        glfwTerminate();
    }

    public void DrawQuad(int x, int y, float width, float height) {
        GL11.glColor3f(0, 255, 255);

        glBegin(GL_QUADS); 

        glVertex2f(-x, y);
        glVertex2f(width, y);
        glVertex2f(width, -height);
        glVertex2f(-x, -height);

        glEnd();
        //glLoadIdentity(); 
    }
}
