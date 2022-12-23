package projekt;

import projekt.input.CursorInput;
import projekt.input.KeyboardInput;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class Camera {
    public VectorF pos = new VectorF(0,0,-1,1);
    public VectorF u = new VectorF(1,0,0);
    public VectorF v = new VectorF(0,1,0);
    public VectorF n = new VectorF(0,0,1);
    private float camPosX = 0;
    private float camPosY = 0;

    public Matrix4f view = new Matrix4f(pos, u, v, n);

    public Camera move() {
        if(KeyboardInput.keys[GLFW_KEY_SPACE]){
            this.view.translate(0,-0.35f,0);
        }
        if(KeyboardInput.keys[GLFW_KEY_LEFT_SHIFT]){
            this.view.translate(0,0.35f,0);
        }
        if(KeyboardInput.keys[GLFW_KEY_A]){
            this.view.translate(0.35f,0,0);
        }
        if(KeyboardInput.keys[GLFW_KEY_D]){
            this.view.translate(-0.35f,0,0);
        }
        if(KeyboardInput.keys[GLFW_KEY_W]){
            this.view.translate(0,0,0.35f);
        }
        if(KeyboardInput.keys[GLFW_KEY_S]){
            this.view.translate(0,0,-0.35f);
        }
        if(KeyboardInput.keys[GLFW_KEY_DELETE]){
            this.pos = new VectorF(0,0,-1,1);
            u = new VectorF(1,0,0);
            v = new VectorF(0,1,0);
            n = new VectorF(0,0,1);
            this.view = new Matrix4f(pos, u,v,n);
        }
        return this;
    }



    public Camera rotate(float posX, float posY){
        float deltaX = (posX - camPosX) * 0.1f;
        float deltaY = (posY - camPosY) * 0.1f;
        camPosX = posX;
        camPosY = posY;

        view.rotateVector(u.normalize(), deltaY).rotateVector(new VectorF(0,1,0), deltaX);
        if(deltaX != 0 && deltaY != 0) {
            System.out.println("X: " + deltaX + " Y: " + deltaY);
        }
        return this;
    }

    public Matrix4f toMatrix(){
        return this.view;
    }
}




/*
        Escape = Show Mouse
        P = Pause
        W = Move Forward
        S = Move Backward
        A = Move Left
        D = Move Right
        Space = Move Up
        Shift = Move Down
        Delete = Set Camera to Origin
            Press F to Start
 */