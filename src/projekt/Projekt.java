package projekt;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

public class Projekt extends AbstractOpenGLBase {
	private ShaderProgram shaderProgram;
	private List<Integer> vaos = new ArrayList<>();
	private Matrix4 modelMatrixPyramide;
	private Matrix4 modelMatrixTetraeder;
	private Matrix4 modelMatrixPlane;
	private Matrix4 modelMatrixReading;
	private Matrix4 projectionMatrix;
	private Matrix4 viewMatrix;
	private int loc[] = new int[3];

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 1920, 1080);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("projekt");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		Figure pyramide = new Figure(1);
		this.vaos.add(glGenVertexArrays());
		glBindVertexArray(vaos.get(vaos.size() - 1));
		createVBO(pyramide.vertices,3,0);
		createVBO(pyramide.color, 3,1);

		Figure tetraeder = new Figure(1f);
		this.vaos.add(glGenVertexArrays());
		glBindVertexArray(vaos.get(vaos.size() - 1));
		createVBO(tetraeder.vertices,3,0);
		createVBO(tetraeder.color, 3,1);


		Figure plane = new Figure(0.);
		this.vaos.add(glGenVertexArrays());
		glBindVertexArray(vaos.get(vaos.size() - 1));
		createVBO(plane.vertices,3,0);
		createVBO(plane.color, 3,1);

		Figure readFromFile = new Figure("src/res/WeirdHouse.obj");
		this.vaos.add(glGenVertexArrays());
		glBindVertexArray(vaos.get(vaos.size() - 1));
		createVBO(readFromFile.vertices,3,0);
		createVBO(readFromFile.color, 3,1);


		projectionMatrix = new Matrix4(1, 150, 1.777f,1);
		loc[0] = glGetUniformLocation(shaderProgram.getId(), "modelMatrix1");
		loc[1] = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	float angle = 0;
	float offset = 0;
	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		modelMatrixPyramide = new Matrix4();
		modelMatrixPyramide.rotateZ(-angle);
		//modelMatrix.translate(0,0,-4);
		modelMatrixPyramide.rotateX(angle / 2);
		modelMatrixPyramide.rotateY(angle * 2);
		modelMatrixPyramide.translate(2,1,-5);

		modelMatrixTetraeder = new Matrix4();
		//modelMatrixTetraeder.scale((float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2)));
		modelMatrixTetraeder.translate(0,8f,0);
		modelMatrixTetraeder.rotateZ(angle/2);
		modelMatrixTetraeder.translate(0,-8f,0);
		modelMatrixTetraeder.rotateX(angle/4);
		modelMatrixTetraeder.rotateY(-angle);
		modelMatrixTetraeder.translate(-5,0,-10);

		modelMatrixPlane = new Matrix4();
		modelMatrixPlane.scale(200,0,100);
		//modelMatrixPlane.rotateX((float)(Math.toRadians(90)));
		modelMatrixPlane.translate(0,-15,-20);

		modelMatrixReading = new Matrix4();
		modelMatrixReading.rotateY(angle/5);
		modelMatrixReading.rotateZ(angle/10);
		modelMatrixReading.scale(2);

		modelMatrixReading.translate(0, 0,-10);


		angle += 0.01;
		offset += 0.05;
		if(offset > 25){
			offset = -25;
		}
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Matrix an Shader übertragen
		// VAOs zeichnen
		glUniformMatrix4fv(loc[0],false, modelMatrixPyramide.getValuesAsArray());
		glUniformMatrix4fv(loc[1], false, projectionMatrix.getValuesAsArray());
		glBindVertexArray(this.vaos.get(0));
		glDrawArrays(GL_TRIANGLES, 0, 18);

		glUniformMatrix4fv(loc[0], false, modelMatrixTetraeder.getValuesAsArray());
		glBindVertexArray(this.vaos.get(1));
		glDrawArrays(GL_TRIANGLES, 0, 18);

		glUniformMatrix4fv(loc[0], false, modelMatrixPlane.getValuesAsArray());
		glBindVertexArray(this.vaos.get(2));
		glDrawArrays(GL_TRIANGLES, 0, 6);

		glUniformMatrix4fv(loc[0], false, modelMatrixReading.getValuesAsArray());
		glBindVertexArray(this.vaos.get(3));
		glDrawArrays(GL_TRIANGLES, 0, 42);
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