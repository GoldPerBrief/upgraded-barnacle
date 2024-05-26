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

public class Renderer {

	public static int numShapes = 0;
    public Renderer() {
        // Set the clear color (this can be customized as needed)
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clear() {
        // Clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render() {
        float[] vertex1 = {-0.5f, -0.5f};
        float[] vertex2 = {0.5f, -0.5f};
        float[] vertex3 = {0.0f, 0.5f};
        float[] color1 = {1.0f, 0.0f, 0.0f};
        float[] color2 = {0.0f, 1.0f, 0.0f};
        float[] color3 = {0.0f, 0.0f, 1.0f};

        drawTriangle(vertex1, vertex2, vertex3, color1, color2, color3);
    }

    private void drawTriangle(float[] vertex1, float[] vertex2, float[] vertex3, float[] color1, float[] color2, float[] color3) {
        glBegin(GL_TRIANGLES);
        glColor3f(color1[0], color1[1], color1[2]);
        glVertex2f(vertex1[0], vertex1[1]);
        glColor3f(color2[0], color2[1], color2[2]);
        glVertex2f(vertex2[0], vertex2[1]);
        glColor3f(color3[0], color3[1], color3[2]);
        glVertex2f(vertex3[0], vertex3[1]);
        glEnd();
    }

}
