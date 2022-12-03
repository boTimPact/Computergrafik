package projekt;

import java.util.Arrays;

public class Mesh {
    public Vector3f vertices[];
    public int indices[];
    public float color[];

    public Mesh(){
        vertices = new Vector3f[]{new Vector3f(1, 0, 0), new Vector3f(0, 1, 0),new Vector3f(0, 0, 0)};
        color = new float[]{1,0,0, 0,1,0, 0,0,1};
    }

    //Pyramide
    public Mesh(int a) {
        vertices = new Vector3f[]{
                new Vector3f(0, 1, 0),
                new Vector3f(-1, 0, 1),
                new Vector3f(1, 0, 1),
                new Vector3f(1, 0, -1),
                new Vector3f(-1, 0, -1)
        };
        indices = new int[]{
                1, 2, 3,
                1, 4, 5,
                1, 3, 4,
                1, 5, 2,
                3, 2, 4,
                5, 4, 2
        };

        color = new float[]{
                0,0,1,
                0,1,0,
                1,1,1,
                1,0,0,
                0,0,0,
        };
    }

    //Tetraeder
    public Mesh(float a){
        float h = (float)(Math.sqrt(3) / 2);
        float b = (float)(2 * Math.pow(h,2));
        vertices = new Vector3f[]{
                new Vector3f(0, b,0),
                new Vector3f(1,0,-h),
                new Vector3f(-1,0,-h),
                new Vector3f(0,0,h),
        };

        indices = new int[]{
                1,2,3,
                1,4,2,
                1,3,4,
                4,3,2
        };

        color = new float[]{
                1,1,1,
                0,1,0,
                0,0,1,
                1,0,0,
        };
    }

    //Plane
    public Mesh(double a){
        vertices = new Vector3f[]{
                new Vector3f(-1,-1,1),
                new Vector3f(1,-1,1),
                new Vector3f(-1,-1,-1),
                new Vector3f(1,-1,-1),
        };

        indices = new int[]{
                1,2,3,
                2,4,3
        };

        color = new float[]{
                0.2f,0.2f,0.23f,
                0.2f,0.2f,0.23f,
                0.2f,0.2f,0.23f,
                0.2f,0.2f,0.23f
        };
    }

    public Mesh(int vLength, int iLength){
        this.vertices = new Vector3f[vLength];
        this.indices = new int[iLength];
        this.color = new float[vLength];
        Arrays.fill(color,1);
    }

    public float[] vtoArray(){
        float out[] = new float[this.vertices.length * 3];
        int index = 0;
        for (int i = 0; i < vertices.length; i++) {
            out[index] = vertices[i].x;
            index++;
            out[index] = vertices[i].y;
            index++;
            out[index] = vertices[i].z;
            index++;
        }
        return out;
    }
}




/*
            0,0.4f,0,     0,0.4f,0,     0.5f,0.5f,0.25f,
            0,0.4f,0,     0.5f,0.5f,0.25f,     0.5f,0.5f,0.25f
 */