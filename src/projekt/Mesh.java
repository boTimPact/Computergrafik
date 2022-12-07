package projekt;

import java.util.Arrays;

public class Mesh {
    public VectorF vertices[];
    public VectorF normals[];
    public VectorF textures[];
    public int indicesVertices[];
    public int indicesNormals[];
    public int indicesTextures[];
    public VectorF color[];

    public Mesh(){
        vertices = new VectorF[]{new VectorF(1, 0, 0), new VectorF(0, 1, 0),new VectorF(0, 0, 0)};
        color = new VectorF[]{new VectorF(1,0,0),new VectorF(0,1,0), new VectorF(0,0,1)};
    }

    //Pyramide
    public Mesh(int a) {
        vertices = new VectorF[]{
                new VectorF(0, 1, 0),
                new VectorF(-1, 0, 1),
                new VectorF(1, 0, 1),
                new VectorF(1, 0, -1),
                new VectorF(-1, 0, -1)
        };
        indicesVertices = new int[]{
                0, 1, 2,
                0, 3, 4,
                0, 2, 3,
                0, 4, 1,
                2, 1, 3,
                4, 3, 1
        };

        color = new VectorF[]{
                new VectorF(0,0,1),
                new VectorF(0,1,0),
                new VectorF(1,1,1),
                new VectorF(1,0,0),
                new VectorF(0,0,0)
        };
    }

    //Tetraeder
    public Mesh(float a){
        float h = (float)(Math.sqrt(3) / 2);
        float b = (float)(2 * Math.pow(h,2));
        vertices = new VectorF[]{
                new VectorF(0, b,0),
                new VectorF(1,0,-h),
                new VectorF(-1,0,-h),
                new VectorF(0,0,h),
        };

        indicesVertices = new int[]{
                0,1,2,
                0,3,1,
                0,2,3,
                3,2,1
        };

        color = new VectorF[]{
                new VectorF(1,1,1),
                new VectorF(0,1,0),
                new VectorF(0,0,1),
                new VectorF(1,0,0)
        };
    }

    //Plane
    public Mesh(double a){
        vertices = new VectorF[]{
                new VectorF(-1,-1,0),
                new VectorF(1,-1,0),
                new VectorF(-1,1,0),
                new VectorF(1,1,0),
        };

        indicesVertices = new int[]{
                0,1,2,
                1,3,2
        };

        normals = new VectorF[]{
                new VectorF(0,0,1),
                new VectorF(0,0,1),
                new VectorF(0,0,1),
                new VectorF(0,0,1),
        };

        color = new VectorF[]{
                new VectorF(0.2f,1f,0.23f),
                new VectorF(0.2f,1f,0.23f),
                new VectorF(0.2f,0.4f,0.23f),
                new VectorF(0.2f,0.4f,0.23f)
        };
    }

    //Cube
    public Mesh(char a){
        vertices = new VectorF[]{
                new VectorF(-1,-1,-1),     //A 0
                new VectorF(1,-1,-1),      //B 1
                new VectorF(-1,-1,1),      //C 2
                new VectorF(1,-1,1),       //D 3
                new VectorF(-1,1,-1),      //E 4
                new VectorF(1,1,-1),       //F 5
                new VectorF(-1,1,1),       //G 6
                new VectorF(1,1,1),        //H 7
        };

        indicesVertices = new int[]{
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
                1,3,2
        };

        color = new VectorF[]{
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


                new VectorF(1,0,0),
                new VectorF(1,0,0),
                new VectorF(0,0,1),
                new VectorF(0,0,1),
                new VectorF(0,1,0),
                new VectorF(0,1,0),
                new VectorF(1,1,1),
                new VectorF(1,1,1)

        };

    }

    public Mesh(int vLength, int iVLength, int nLength, int iNLength, int tLength, int iTLength, VectorF col){
        this.vertices = new VectorF[vLength];
        this.normals = new VectorF[nLength];
        this.textures = new VectorF[tLength];
        this.indicesVertices = new int[iVLength];
        this.indicesNormals = new int[iNLength];
        this.indicesTextures = new int[iTLength];
        this.color = new VectorF[vertices.length];
        Arrays.fill(color, col);        //for static coloring
        /*
        //for Color for Cube Testing
        color = new VectorF[]{
                new VectorF(1, 0, 0),
                new VectorF(0, 1, 0),
                new VectorF(0, 0, 1),
                new VectorF(1, 1, 1),
                new VectorF(1, 1, 0),
                new VectorF(1, 0, 1),
                new VectorF(0, 1, 1),
                new VectorF(0.5f, 0.5f, 0.5f)
        };
        */
    }

    public float[] vArrToArr(VectorF arr[]){
        float out[] = new float[arr.length * 3];
        int index = 0;
        for (VectorF vertex : arr) {
            out[index] = vertex.x;
            index++;
            out[index] = vertex.y;
            index++;
            out[index] = vertex.z;
            index++;
        }
        return out;
    }

    public float[] ctoArray(){
        float out[] = new float[this.color.length * 3];
        int index = 0;
        for (VectorF vector3f : color) {
            out[index] = vector3f.x;
            index++;
            out[index] = vector3f.y;
            index++;
            out[index] = vector3f.z;
            index++;
        }
        return out;
    }
}




/*
            0,0.4f,0,     0,0.4f,0,     0.5f,0.5f,0.25f,
            0,0.4f,0,     0.5f,0.5f,0.25f,     0.5f,0.5f,0.25f
 */