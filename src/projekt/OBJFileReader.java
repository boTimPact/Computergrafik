package projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class OBJFileReader{

    public void readFile(Mesh mesh, String path){
         List<VectorF> vertices = new LinkedList<>();
         List<Integer> indices = new LinkedList<>();


        File file = new File(path);

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String parts[] = line.split(" ");
                if(parts[0].equals("v")){                   // v = vertex, vt = Texturkoordinaten, vn = Normale, f = Fläche (f v/vt/vn),
                    vertices.add(new VectorF(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
                    }
                if(parts[0].equals("f")){
                    for (int i = 1; i < parts.length; i++) {
                        if(parts[1].contains("/")){
                            String split[] = parts[i].split("/");
                            indices.add(Integer.parseInt(split[0]) - 1);
                        }
                        //if only vertices are saved in .obj file
                        else {
                            indices.add(Integer.parseInt(parts[i]) - 1);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeToMesh(mesh, vertices, indices);
    }

    private Mesh writeToMesh(Mesh mesh, List vertices, List indices){
        mesh.vertices = new VectorF[vertices.size()];
        mesh.indicesVertices = new int[indices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            mesh.vertices[i] = (VectorF) vertices.get(i);
        }
        for (int i = 0; i < indices.size(); i++) {
            mesh.indicesVertices[i] = (int)indices.get(i);
        }
        return mesh;
    }

    /*
    public static void main(String[] args) {
        OBJFileReader test = new OBJFileReader();
        test.readFile("src/res/Cube.obj");
    }
    */
}