package projekt;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float[] addToArr(float arr[]){
        arr[0] = this.x;
        arr[1] = this.y;
        arr[2] = this.z;

        return arr;
    }
}
