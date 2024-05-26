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

public class run {
    public static void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        Display window1 = new Display(640, 480);
        window1.init();

        while(window1.isRunning()) {
            /*
            glBegin(GL_QUADS); 
            glVertex2f(-0.5f, 0.5f);
            glVertex2f(0.5f, 0.5f);
            glVertex2f(0.5f, -0.5f);
            glVertex2f(-0.5f, -0.5f);
            glEnd();
             */

            window1.DrawQuad(10, 10, 50, 50);

            window1.update(); 
        }

        window1.terminate();
    }


    public static void main(String[] args) {
        run();
    }
}