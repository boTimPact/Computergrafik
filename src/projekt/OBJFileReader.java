package projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OBJFileReader{
    private List<VectorF> vertices = new LinkedList<>();
    private List<VectorF> normals = new LinkedList<>();
    private List<VectorF> textures = new LinkedList<>();

    private List<Integer> indicesVertices = new LinkedList<>();
    private List<Integer> indicesNormals = new LinkedList<>();
    private List<Integer> indicesTextures = new LinkedList<>();
    private float out[];
    private VectorF color;     //for static coloring


    public OBJFileReader(VectorF col){     //for static coloring
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
                    vertices.add(new VectorF(Float.valueOf(parts[1]), Float.valueOf(parts[2]), Float.valueOf(parts[3])));
                    }
                if(parts[0].equals("vn")){
                    normals.add(new VectorF(Float.valueOf(parts[1]), Float.valueOf(parts[2]), Float.valueOf(parts[3])));
                }
                if(parts[0].equals("vt")){
                    textures.add(new VectorF(Float.valueOf(parts[1]), Float.valueOf(parts[2])));
                }
                if(parts[0].equals("f")){
                    for (int i = 1; i < parts.length; i++) {
                        if(parts[1].contains("/")){
                            String split[] = parts[i].split("/");
                            indicesVertices.add(Integer.valueOf(split[0]) - 1);
                            if(normals.size()!= 0 ) {
                                indicesNormals.add(Integer.valueOf(split[1]) - 1);
                                indicesTextures.add(Integer.valueOf(split[2]) - 1);
                            }else {
                                indicesTextures.add(Integer.valueOf(split[1]) - 1);
                            }

                        }
                        //if only vertices are saved in .obj file
                        else {
                            indicesVertices.add(Integer.valueOf(parts[i]) - 1);
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

        Mesh out = new Mesh(vertices.size(), indicesVertices.size(), normals.size(), indicesNormals.size(), textures.size(), indicesTextures.size(), color);        //color only for static coloring

        for (int i = 0; i < out.vertices.length; i++) {
            out.vertices[i] = this.vertices.get(i);
        }
        for (int i = 0; i < out.normals.length; i++) {
            out.normals[i] = this.normals.get(i);
        }
        for (int i = 0; i < out.textures.length; i++) {
            out.textures[i] = this.textures.get(i);
        }
        for (int i = 0; i < out.indicesVertices.length; i++) {
            out.indicesVertices[i] = this.indicesVertices.get(i);
            if(i < out.indicesNormals.length) {
                out.indicesNormals[i] = this.indicesNormals.get(i);
            }
            if(i < out.indicesTextures.length) {
                out.indicesTextures[i] = this.indicesTextures.get(i);
            }
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