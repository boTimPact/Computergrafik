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
	Camera camera = new Camera();
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Matrix4f modelMatrix;
	private int locMatrices[] = new int[3];

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 1920, 1080);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("projekt");
		glUseProgram(shaderProgram.getId());

		Mesh cube = new Mesh('A');
		this.vaos.add(new VAO(cube, new Matrix4f()));

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		Mesh pyramide = new Mesh(1);
		this.vaos.add(new VAO(pyramide, new Matrix4f()));


		Mesh tetraeder = new Mesh(1f);
		this.vaos.add(new VAO(tetraeder, new Matrix4f()));



		Mesh plane = new Mesh(0.);
		this.vaos.add(new VAO(plane, new Matrix4f()));


		OBJFileReader reader = new OBJFileReader(new Vector3f(1,1,1));
		Mesh readFromFile = reader.readFile("src/res/MiniBike.obj");
		this.vaos.add(new VAO(readFromFile, new Matrix4f()));


		reader = new OBJFileReader(new Vector3f(0,0,1));
		readFromFile = reader.readFile("src/res/Ball.obj");
		this.vaos.add(new VAO(readFromFile, new Matrix4f()));

		reader = new OBJFileReader(new Vector3f(1,1,0));
		readFromFile = reader.readFile("src/res/Ball.obj");
		this.vaos.add(new VAO(readFromFile, new Matrix4f()));



		viewMatrix = new Matrix4f(camera.pos, camera.u, camera.v, camera.n);
		projectionMatrix = new Matrix4f(1, 100, 1.777f,1);

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

		//Cube
		modelMatrix = new Matrix4f().rotateX(angle).rotateY(angle).rotateZ(0).translate(offset,offset,-15);
		vaos.get(0).updateModel(modelMatrix);

		//Pyramide
		modelMatrix = new Matrix4f().rotateX(angle / 2).rotateY(angle * 2).rotateZ(-angle).translate(4,2,-10);
		vaos.get(1).updateModel(modelMatrix);

		//Tetraeder
		modelMatrix= new Matrix4f().translate(0,8f,0).rotateZ(angle/2).translate(0,-8f,0).rotateX(angle/4).rotateY(-angle).scale((float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2)),(float)(2*(Math.sin(angle)+2))).translate(-3,0,-25);
		vaos.get(2).updateModel(modelMatrix);

		//Plane
		modelMatrix = new Matrix4f().scale(1000).rotateX((float)Math.toRadians(-90)).translate(0,-20,-10);
		vaos.get(3).updateModel(modelMatrix);

		//File 1
		modelMatrix = new Matrix4f().rotateX((float)Math.toRadians(0)).rotateY(angle).rotateZ(0).scale(1).translate(30, -10,-60);
		vaos.get(4).updateModel(modelMatrix);

		//File 2
		modelMatrix = new Matrix4f().scale(4).translate(-10, 5, -50);
		vaos.get(5).updateModel(modelMatrix);

		//File 3
		modelMatrix = new Matrix4f().translate(6,0,0).rotateY(angle).multiply(modelMatrix);
		vaos.get(6).updateModel(modelMatrix);

		//camera.pos.multiplyMatrix(new Matrix4f().translate(0,offset/100,0));
		viewMatrix = new Matrix4f(camera.pos, camera.u, camera.v, camera.n);

		angle += 0.01;
		offset += delta;
		if(offset > 6.5 || offset < -6.5){
			delta *= -1;
		}
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Matrix an Shader übertragen
		// VAOs zeichnen
		glUniformMatrix4fv(locMatrices[1], false, viewMatrix.getValuesAsArray());
		glUniformMatrix4fv(locMatrices[2], false, projectionMatrix.getValuesAsArray());

		for (VAO tmp : this.vaos) {
			glUniformMatrix4fv(locMatrices[0], false, tmp.modelMatrix.getValuesAsArray());
			glBindVertexArray(tmp.location);
			glDrawElements(GL_TRIANGLES, tmp.mesh.indices.length, GL_UNSIGNED_INT, 0);
		}
	}

	@Override
	public void destroy() {
	}
}