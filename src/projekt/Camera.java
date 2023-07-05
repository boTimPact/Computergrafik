package projekt;

import projekt.input.KeyboardInput;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class Camera {
    public VectorF pos;
    public VectorF u;
    public VectorF v;
    public VectorF n;
    private float camPosX;
    private float camPosY;


    public Camera(){
        this.pos = new VectorF(0,0,-1,1);
        this.u = new VectorF(1,0,0);
        this.v = new VectorF(0,1,0);
        this.n = new VectorF(0,0,1);

        this.camPosX = 0;
        this.camPosY = 0;

        System.out.println("N:   X: " + n.x + " Y: " + n.y + " Z: " + n.z);
        System.out.println("V:   X: " + v.x + " Y: " + v.y + " Z: " + v.z);
        System.out.println("U:   X: " + u.x + " Y: " + u.y + " Z: " + u.z);
    }



    private float translateX = 0;
    private float translateY = 0;
    private float translateZ = 0;

    public Camera move(List vaos) {
        /*
        //Move Camera
        if(KeyboardInput.keys[GLFW_KEY_SPACE]){
            this.pos = this.pos.add(new VectorF(0,-0.35f,0).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_LEFT_SHIFT]){
            this.pos = this.pos.add(new VectorF(0,0.35f,0).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_A]){
            this.pos = this.pos.add(new VectorF(0.35f,0,0).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_D]){
            this.pos = this.pos.add(new VectorF(-0.35f,0,0).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_W]){
            this.pos = this.pos.add(new VectorF(0,0,0.35f).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_S]){
            this.pos = this.pos.add(new VectorF(0,0,-0.35f).negate());
        }
        if(KeyboardInput.keys[GLFW_KEY_0] || KeyboardInput.keys[GLFW_KEY_DELETE]){
            this.pos = new VectorF(0,0,-1,1);
            u = new VectorF(1,0,0);
            v = new VectorF(0,1,0);
            n = new VectorF(0,0,1);
        }
        */

        //Move Objects
        if(KeyboardInput.keys[GLFW_KEY_SPACE]){
            translateX += -0.35f * this.v.x;
            translateY += -0.35f * this.v.y;
            translateZ += -0.35f * this.v.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_LEFT_SHIFT]){
            translateX += 0.35f * this.v.x;
            translateY += 0.35f * this.v.y;
            translateZ += 0.35f * this.v.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_A]){
            translateX += -0.35f * this.u.x;
            translateY += -0.35f * this.u.y;
            translateZ += -0.35f * this.u.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_D]){
            translateX += 0.35f * this.u.x;
            translateY += 0.35f * this.u.y;
            translateZ += 0.35f* this.u.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_W]){
            translateX += 0.35f * this.n.x;
            translateY += 0.35f * this.n.y;
            translateZ += 0.35f * this.n.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_S]){
            translateX += -0.35f * this.n.x;
            translateY += -0.35f * this.n.y;
            translateZ += -0.35f * this.n.z;
        }
        if(KeyboardInput.keys[GLFW_KEY_0] || KeyboardInput.keys[GLFW_KEY_DELETE]){
            translateX = 0;
            translateY = 0;
            translateZ = 0;
            this.u = new VectorF(1,0,0);
            this.v = new VectorF(0,1,0);
            this.n = new VectorF(0,0,1);
        }
        Matrix4f transMat = new Matrix4f();
        transMat.translate(translateX, translateY, translateZ);
        //System.out.println("X: " + pos.x + " Y: " + pos.y + " Z: " + pos.z);

        for (int i = 0; i < vaos.size(); i++ ) {
            VAO e = (VAO)vaos.get(i);
            e.modelMatrix.multiply(transMat);
        }


        return this;
    }



    public Camera rotate(float posX, float posY){
        float deltaX = (posX - camPosX) * 0.1f;
        float deltaY = (posY - camPosY) * 0.1f;
        camPosX = posX;
        camPosY = posY;

        VectorF vUp = new VectorF(0,1,0);
        Quaternion rotateV = new Quaternion(-deltaX, v.normalize());
        Quaternion conjugateRotateV = rotateV.conjugate();

        Quaternion newN = rotateV.multiply(new Quaternion(this.n.normalize())).multiply(conjugateRotateV);
        this.n = newN.vec;
        this.n = this.n.normalize();
        this.v = vUp.add(n.multiplyScalar(vUp.dot(n)).negate());
        this.v = this.v.normalize();

        this.u = this.n.cross(this.v);

        Quaternion rotateU = new Quaternion(deltaY, this.u.normalize());
        Quaternion conjugateRotateU = rotateU.conjugate();

        newN = rotateU.multiply(new Quaternion(this.n.normalize())).multiply(conjugateRotateU);
        this.n = newN.vec;
        this.n = this.n.normalize();
        Quaternion newV = rotateV.multiply(new Quaternion(this.v)).multiply(conjugateRotateV);
        this.v = newV.vec;
        this.v = this.v.normalize();

        return this;
    }

    public Matrix4f toMatrix(){
        Matrix4f view = new Matrix4f(pos, u.negate(), v, n);
        return view;
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