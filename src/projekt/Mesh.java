package projekt;

import java.util.*;


public class Mesh {
    public VectorF vertices[];
    public VectorF normals[];
    public float uv[];
    public int indicesVertices[];
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

        uv = new float[vertices.length * 2];
        for (int i = 0; i < uv.length; i++) {
            uv[i] = (float)Math.random();
        }

        calcNormals();
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

        uv = new float[vertices.length * 2];
        for (int i = 0; i < uv.length; i++) {
            uv[i] = (float)Math.random();
        }

        calcNormals();
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

        uv = new float[]{
                0,0,
                0,1,
                1,0,
                1,1
        };

        calcNormals();
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

        uv = new float[vertices.length * 2];
        for (int i = 0; i < uv.length; i++) {
            uv[i] = (float)Math.random();
        }

        calcNormals();
    }

    public Mesh(String path, VectorF color){
        OBJFileReader reader = new OBJFileReader();
        reader.readFile(this, path);
        this.color = new VectorF[this.vertices.length];
        for (int i = 0; i < this.color.length; i++) {
            this.color[i] = color;
        }
        //Arrays.fill(this.color, color);
        uv = new float[vertices.length * 2];
        for (int i = 0; i < uv.length; i++) {
            uv[i] = (float)Math.random();
        }
        calcNormals();
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

    public void calcNormals(){
        this.normals = new VectorF[vertices.length];
        Set<VectorF>[] normalSet = new Set[vertices.length];
        for (int i = 0; i < normalSet.length; i++) {
            normalSet[i] = new HashSet<VectorF>();
        }
        Arrays.fill(normals,new VectorF(0,0,0));
        for (int i = 0; i <= indicesVertices.length - 3; i += 3) {
            VectorF vec1 = vertices[this.indicesVertices[i+1]].add(vertices[this.indicesVertices[i]].negate());
            VectorF vec2 = vertices[this.indicesVertices[i+2]].add(vertices[this.indicesVertices[i]].negate());
            VectorF normal = vec1.cross(vec2);
            normalSet[this.indicesVertices[i]].add(normal);
            normalSet[this.indicesVertices[i+1]].add(normal);
            normalSet[this.indicesVertices[i+2]].add(normal);
        }

        for (int i = 0; i < normalSet.length; i++) {
            VectorF sum = new VectorF(0,0,0);
            for (VectorF e: normalSet[i]) {
                sum = sum.add(e);
            }
            normals[i] = sum;
        }
    }

    public static void main(String[] args) {
        Mesh test = new Mesh('1');


        test.calcNormals();
        System.out.println(test.normals[0].x + " " + test.normals[0].y + " " + test.normals[0].z);
    }

}




/*
            0,0.4f,0,     0,0.4f,0,     0.5f,0.5f,0.25f,
            0,0.4f,0,     0.5f,0.5f,0.25f,     0.5f,0.5f,0.25f
 */