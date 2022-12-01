package projekt;

import java.util.Arrays;

public class Figure {
    public float vertices[];
    public int indices[];
    public float color[];

    public Figure(){
        vertices = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 0};
        color = new float[]{1,0,0, 0,1,0, 0,0,1};
    }

    public Figure(int a){
        vertices = new float[]{
                0,1,0,      -1,0,1,     1,0,1,
                0,1,0,      1,0,-1,     -1,0,-1,
                0,1,0,      1,0,1,      1,0,-1,
                0,1,0,      -1,0,-1,    -1,0,1,
                1,0,1,      -1,0,1,     1,0,-1,
                -1,0,-1,    1,0,-1,     -1,0,1
        };
        /*
        {
            0,1,0, //0,1,2
            1,0,1, //3,4,5
            1,0,-1, //6,7,8
            -1,0,1, //9,10,11
            -1,0,-1 //12,13,14
        }
        indices = new int[]{};
        */


        color = new float[]{
                0,0,1,      0,1,0,     1,1,1,
                0,0,1,      1,0,0,     0,0,0,
                0,0,1,      1,1,1,     1,0,0,
                0,0,1,      0,0,0,     0,1,0,
                1,1,1,      0,1,0,     1,0,0,
                0,0,0,      1,0,0,     0,1,0,
        };
    }

    public Figure(float a){
        float h = (float)(Math.sqrt(3) / 2);
        vertices = new float[]{
                0,1,0,      1,0,-h,         -1,0,-h,
                0,1,0,      0,0,h,          1,0,-h,
                0,1,0,      -1,0,-h,        0,0,h,
                0,0,h,      -1,0,-h,        1,0,-h
        };

        color = new float[]{
                1,1,1,      0,1,0,        0,0,1,
                1,1,1,      1,0,0,        0,1,0,
                1,1,1,      0,0,1,        1,0,0,
                1,0,0,      0,0,1,        0,1,0,

        };
    }

    public Figure(double a){
        vertices = new float[]{
                -1,-1,1,        1,-1,1,     -1,-1,-1,
                1,-1,1,         1,-1,-1,    -1,-1,-1
        };
        color = new float[]{
                0.2f,0.2f,0.23f,     0.2f,0.2f,0.23f,     0.2f,0.2f,0.23f,
                0.2f,0.2f,0.23f,     0.2f,0.2f,0.23f,     0.2f,0.2f,0.23f
        };
    }

    public Figure(String path){
        loadFromFile(path);
    }


    public void loadFromFile(String path){
        OBJFileReader reader = new OBJFileReader();
        this.vertices = reader.readFile(path).clone();
        this.color = new float[vertices.length];
        int index = 0;
        for (int i = 0; i < color.length; i++) {
            float col = 0;
            if(index < 3) {
                index++;
            }else {
                col = (float)Math.random();
                index = 0;
            }
            color[i] = col;
        }
    }
}




/*
            0,0.4f,0,     0,0.4f,0,     0.5f,0.5f,0.25f,
            0,0.4f,0,     0.5f,0.5f,0.25f,     0.5f,0.5f,0.25f
 */