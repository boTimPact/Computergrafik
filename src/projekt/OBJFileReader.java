package projekt;

import java.io.File;
import java.io.FileNotFoundException;;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OBJFileReader{
    private List<Vector3f> vertices = new LinkedList<>();
    private List<Integer> indices = new LinkedList<>();
    private float out[];

    public Mesh readFile(String path){

        File file = new File(path);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                String parts[] = line.split(" ");
                if(parts[0].equals("v")){                   // v = vertex, vt = Texturkoordinaten, vn = Normale, f = Fläche,
                    vertices.add(new Vector3f(Float.valueOf(parts[1]), Float.valueOf(parts[2]), Float.valueOf(parts[3])));
                    }
                if(parts[0].equals("f")){
                    indices.add(Integer.valueOf(parts[1].charAt(0)) - 48);
                    indices.add(Integer.valueOf(parts[2].charAt(0)) - 48);
                    indices.add(Integer.valueOf(parts[3].charAt(0)) - 48);
                }
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mesh out = new Mesh(vertices.size(), indices.size());

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