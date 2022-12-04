package projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OBJFileReader{
    private List<Vector3f> vertices = new LinkedList<>();
    private List<Integer> indices = new LinkedList<>();
    private float out[];
    private Vector3f color;     //for static coloring


    public OBJFileReader(Vector3f col){     //for static coloring
        this.color = col;
    }



    public Mesh readFile(String path){

        File file = new File(path);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                String parts[] = line.split(" ");
                if(parts[0].equals("v")){                   // v = vertex, vt = Texturkoordinaten, vn = Normale, f = Fläche (f v/vt/vn),
                    vertices.add(new Vector3f(Float.valueOf(parts[1]), Float.valueOf(parts[2]), Float.valueOf(parts[3])));
                    }
                if(parts[0].equals("f")){
                    for (int i = 1; i < parts.length; i++) {
                        if(parts[1].contains("/")){
                            String split[] = parts[i].split("/");
                            indices.add(Integer.valueOf(split[0]) - 1);
                        }else {
                            indices.add(Integer.valueOf(parts[i]) - 1);
                        }
                    }
                }
                // read next line
                line = reader.readLine();
            }
/*
            for (int i = 0; i < vertices.size(); i++) {
                System.out.println(vertices.get(i).x + " " + vertices.get(i).y + " " + vertices.get(i).z);
            }
            for (int i = 0; i + 3 < indices.size(); i+=3) {
                System.out.println(indices.get(i) + " " + indices.get(i+1) + " " + indices.get(i+2));
            }
*/
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mesh out = new Mesh(vertices.size(), indices.size(), color);        //color only for static coloring

        for (int i = 0; i < out.vertices.length; i++) {
            out.vertices[i] = this.vertices.get(i);
        }
        for (int i = 0; i < out.indices.length; i++) {
            out.indices[i] = this.indices.get(i);
        }

        return out;
    }

    /*
    public static void main(String[] args) {
        OBJFileReader test = new OBJFileReader();
        test.readFile("src/res/Cube2.obj");
    }
    */
}