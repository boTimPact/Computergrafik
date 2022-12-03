package projekt;

import java.util.Arrays;

public class Mesh {
    public Vector3f vertices[];
    public int indices[];
    public Vector3f color[];

    public Mesh(){
        vertices = new Vector3f[]{new Vector3f(1, 0, 0), new Vector3f(0, 1, 0),new Vector3f(0, 0, 0)};
        color = new Vector3f[]{new Vector3f(1,0,0),new Vector3f(0,1,0), new Vector3f(0,0,1)};
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
                5, 4, 2,
                0
        };

        color = new Vector3f[]{
                new Vector3f(0,0,1),
                new Vector3f(0,1,0),
                new Vector3f(1,1,1),
                new Vector3f(1,0,0),
                new Vector3f(0,0,0)
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
                4,3,2,
                0
        };

        color = new Vector3f[]{
                new Vector3f(1,1,1),
                new Vector3f(0,1,0),
                new Vector3f(0,0,1),
                new Vector3f(1,0,0)
        };
    }

    //Plane
    public Mesh(double a){
        vertices = new Vector3f[]{
                new Vector3f(-1,-1,0),
                new Vector3f(1,-1,0),
                new Vector3f(-1,1,0),
                new Vector3f(1,1,0),
        };

        indices = new int[]{
                1,2,0,
                1,2,3,
                0
        };

        color = new Vector3f[]{
                new Vector3f(0.2f,0.2f,0.23f),
                new Vector3f(0.2f,0.2f,0.23f),
                new Vector3f(0.2f,0.2f,0.23f),
                new Vector3f(0.2f,0.2f,0.23f)
        };
    }

    //Cube
    public Mesh(char a){
        vertices = new Vector3f[]{
                new Vector3f(-1,-1,-1),     //A 0
                new Vector3f(1,-1,-1),      //B 1
                new Vector3f(-1,-1,1),      //C 2
                new Vector3f(1,-1,1),       //D 3
                new Vector3f(-1,1,-1),      //E 4
                new Vector3f(1,1,-1),       //F 5
                new Vector3f(-1,1,1),       //G 6
                new Vector3f(1,1,1),        //H 7
        };

        indices = new int[]{
                6,2,7,  //vorne
                2,3,7,

                7,3,5,  //rechts
                3,1,5,

                5,1,4,  //hinten
                1,0,4,

                4,0,6,  //links
                0,2,6,

                4,6,5,  //oben
                6,7,5,

                1,2,0,  //unten
                1,3,2,

                0       //TODO: Prof fragen warum dies geht aber +1 in glDrawElements nicht
        };

        color = new Vector3f[]{
                /*
                new Vector3f(0,0,1),    //A
                new Vector3f(1,1,0),    //B
                new Vector3f(1,0,0),    //C
                new Vector3f(0,1,0),    //D
                new Vector3f(1,1,1),    //E
                new Vector3f(1,1,1),    //F
                new Vector3f(1,1,1),    //G
                new Vector3f(1,1,1),    //H
                 */


                new Vector3f(1,0,0),
                new Vector3f(0,1,0),
                new Vector3f(0,0,1),
                new Vector3f(1,1,1),
                new Vector3f(1,1,0),
                new Vector3f(1,0,1),
                new Vector3f(0,1,1),
                new Vector3f(0.5f,0.5f,0.5f)

        };

    }

    public Mesh(int vLength, int iLength){
        this.vertices = new Vector3f[vLength];
        this.indices = new int[iLength];
        this.color = new Vector3f[vLength];
        Arrays.fill(color,new Vector3f(1,1,1));
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

    public float[] ctoArray(){
        float out[] = new float[this.color.length * 3];
        int index = 0;
        for (int i = 0; i < color.length; i++) {
            out[index] = color[i].x;
            index++;
            out[index] = color[i].y;
            index++;
            out[index] = color[i].z;
            index++;
        }
        return out;
    }
}




/*
            0,0.4f,0,     0,0.4f,0,     0.5f,0.5f,0.25f,
            0,0.4f,0,     0.5f,0.5f,0.25f,     0.5f,0.5f,0.25f
 */