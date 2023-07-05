package projekt.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorInput extends GLFWCursorPosCallback {
    public static double xPos;
    public static double yPos;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        //System.out.println("X:" + xpos + " Y:" + ypos);
        this.xPos = xpos;
        this.yPos = ypos;
    }
}
