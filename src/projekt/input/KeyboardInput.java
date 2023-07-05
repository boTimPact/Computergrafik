package projekt.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

//https://www.youtube.com/watch?v=_6b73ZxlQOg
public class KeyboardInput extends GLFWKeyCallback{
    public static boolean keys[] = new boolean[65535];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

}
