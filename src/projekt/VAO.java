package projekt;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VAO {
    public int location;
    public Mesh mesh;
    public Matrix4f modelMatrix;


    public VAO(Mesh mesh, Matrix4f model){
        this.mesh = mesh;
        this.modelMatrix = model;
        location = glGenVertexArrays();
        glBindVertexArray(this.location);
        this.bindVBOs();
    }

    public void updateModel(Matrix4f model){
        this.modelMatrix = new Matrix4f(model);
    }


    public void bindVBOs(){
        int vboVertex = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertex);
        glBufferData(GL_ARRAY_BUFFER, mesh.vArrToArr(this.mesh.vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        int vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER, mesh.ctoArray(), GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        int vboNormals = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormals);
        glBufferData(GL_ARRAY_BUFFER, mesh.vArrToArr(this.mesh.vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(2);

        int vboIndices = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIndices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.mesh.indicesVertices, GL_STATIC_DRAW);
    }
}