package projekt;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

import java.util.LinkedList;
import java.util.List;


public class Projekt extends AbstractOpenGLBase {
	private ShaderProgram shaderProgram;
	private List<VAO> vaos = new LinkedList<>();
	private Matrix4 projectionMatrix;
	private Matrix4 viewMatrix;
	private Matrix4 modelMatrix;
	private int locMatrices[] = new int[3];

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 1920, 1080);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("projekt");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		Mesh pyramide = new Mesh(1);
		this.vaos.add(new VAO(pyramide, new Matrix4()));


		Mesh tetraeder = new Mesh(1f);
		this.vaos.add(new VAO(tetraeder, new Matrix4()));



		Mesh plane = new Mesh(0.);
		this.vaos.add(new VAO(plane, new Matrix4()));


		OBJFileReader reader = new OBJFileReader();
		Mesh readFromFile = reader.readFile("src/res/House.obj");
		this.vaos.add(new VAO(readFromFile, new Matrix4()));

		Camera camera = new Camera();
		viewMatrix = new Matrix4(camera.pos, camera.u, camera.v, camera.n);
		projectionMatrix = new Matrix4(1, 150, 1.777f,1);

		locMatrices[0] = glGetUniformLocation(shaderProgram.getId(), "modelMatrix");
		locMatrices[1] = glGetUniformLocation(shaderProgram.getId(), "viewMatrix");
		locMatrices[2] = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
		//glFrontFace(GL_CW);	// check when clockwise vertex configuration
		//glCullFace(GL_BACK);
	}

	float angle = 0;
	float offset = 0;
	float delta = 0.05f;
	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		modelMatrix = new Matrix4();
		//modelMatrix.rotateZ(-angle);
		//modelMatrix.translate(0,0,-4);
		//modelMatrix.rotateX(angle / 2);
		//modelMatrix.rotateY(angle * 2);
		modelMatrix.translate(0,0,-5);
		vaos.get(0).updateModel(modelMatrix);


		modelMatrix= new Matrix4();
		//modelMatrix.scale((float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2)));
		//modelMatrix.translate(0,8f,0);
		//modelMatrix.rotateZ(angle/2);
		//modelMatrix.translate(0,-8f,0);
		//modelMatrix.rotateX(angle/4);
		//modelMatrix.rotateY(-angle);
		modelMatrix.translate(0,0,-10);
		vaos.get(1).updateModel(modelMatrix);


		modelMatrix = new Matrix4();
		modelMatrix.scale(200,0,100);
		//modelMatrix.rotateX((float)Math.toRadians(90));
		modelMatrix.translate(0,-15,-20);
		vaos.get(2).updateModel(modelMatrix);


		modelMatrix = new Matrix4();
		//modelMatrix.rotateY(angle/5);
		//modelMatrix.rotateZ(angle/10);
		//modelMatrix.rotateX(angle);
		modelMatrix.scale(2);
		modelMatrix.translate(0, 0,-2);
		vaos.get(3).updateModel(modelMatrix);


		angle += 0.01;
		offset += delta;
		if(offset > 10 || offset < -10){
			delta *= -1;
		}
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Matrix an Shader übertragen
		// VAOs zeichnen
		// TODO: eigene Klasse für VAOs sodass ich die länge automatisch lesen kann und nicht per hand eingeben muss -> glDrawArrays()
		glUniformMatrix4fv(locMatrices[1], false, viewMatrix.getValuesAsArray());
		glUniformMatrix4fv(locMatrices[2], false, projectionMatrix.getValuesAsArray());

		for (int i = 0; i < vaos.size(); i++) {
			VAO tmp = this.vaos.get(i);
			glUniformMatrix4fv(locMatrices[0],false, tmp.modelMatrix.getValuesAsArray());
			glBindVertexArray(tmp.location);
			glDrawElements(GL_TRIANGLES, tmp.mesh.indices.length * 3, GL_UNSIGNED_INT, tmp.vboIndices);
		}
	}

	@Override
	public void destroy() {
	}

	public void createVBO(float[] input, int size, int index){
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		glBufferData(GL_ARRAY_BUFFER, input,GL_STATIC_DRAW);
		glVertexAttribPointer(index,size,GL_FLOAT, false, 0,0);
		glEnableVertexAttribArray(index);
	}
}