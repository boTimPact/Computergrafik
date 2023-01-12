package projekt;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.Texture;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import projekt.input.CursorInput;
import projekt.input.KeyboardInput;
import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.LinkedList;
import java.util.List;


public class Projekt extends AbstractOpenGLBase {
	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgram2;
	private List<VAO> vaos = new LinkedList<>();
	Camera camera = new Camera();
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Matrix4f modelMatrix;
	private int locMatrices[] = new int[3];

	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorPos;

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", (int)(1920/1.5), (int)(1200/1.5));
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("projekt");
		shaderProgram2 = new ShaderProgram("projectNoLighting");
		glUseProgram(shaderProgram2.getId());

		glfwSetKeyCallback(this.getWindow(), keyCallback = new KeyboardInput());
		glfwSetCursorPosCallback(this.getWindow(), cursorPos = new CursorInput());
		glfwSetInputMode(this.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);		//https://stackoverflow.com/questions/36951925/java-lwjgl-how-to-make-the-mouse-invisible


		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		Mesh cube = new Mesh('A');
		this.vaos.add(new VAO(cube, new Matrix4f(), "red.png", 0));

		Mesh pyramide = new Mesh(1);
		this.vaos.add(new VAO(pyramide, new Matrix4f(), "texture2.png", 2));


		Mesh tetraeder = new Mesh(1f);
		this.vaos.add(new VAO(tetraeder, new Matrix4f(), "texture2.png", 2));


		Mesh plane = new Mesh(0.);
		this.vaos.add(new VAO(plane, new Matrix4f(), "Background.jpg", 5));


		Mesh readFromFile = new Mesh("src/res/MiniBike.obj", new VectorF(1,1,1));
		this.vaos.add(new VAO(readFromFile, new Matrix4f(), "textureWood.png", 5));


		readFromFile = new Mesh("src/res/Ball.obj", new VectorF(0,0,1));
		this.vaos.add(new VAO(readFromFile, new Matrix4f(), "bluePlanet.jpg", 5));


		readFromFile = new Mesh("src/res/Ball.obj", new VectorF(1,1,0));
		this.vaos.add(new VAO(readFromFile, new Matrix4f(), "orangePlanet.jpg", 5));


		readFromFile = new Mesh("src/res/MenuWriting.obj", new VectorF(0.7f, 0.524f, 0.083f));
		this.vaos.add(new VAO(readFromFile, new Matrix4f(), "texture2.png", 0));


/*
		//Test for Lighting
		Mesh cube = new Mesh('A');
		this.vaos.add(new VAO(cube, new Matrix4f(), "texture2.png"));
*/


		viewMatrix = new Matrix4f(camera.pos, camera.u, camera.v, camera.n);
		projectionMatrix = new Matrix4f(1, 1000, 1.777f,1);

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

	boolean isPressedBeforeESC = false;
	boolean isPressedBeforeP = false;
	boolean isInMenu = false;
	boolean isPaused = false;
	boolean isStarted = false;
	@Override
	public void update() {

		//Switch to show mouse
		if (KeyboardInput.keys[GLFW_KEY_ESCAPE] && !isPressedBeforeESC) {
			//System.out.println("Escape pressed");
			if (isInMenu) {
				glfwSetInputMode(this.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
				isInMenu = false;
			}else{
				glfwSetInputMode(this.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
				isInMenu = true;
			}
			isPressedBeforeESC = true;
		}else if(KeyboardInput.keys[GLFW_KEY_ESCAPE] && isPressedBeforeESC){
			//System.out.println("ESC was already pressed!");
		}else {
			isPressedBeforeESC = false;
		}

		//Switch to pause animation
		if(KeyboardInput.keys[GLFW_KEY_P] && !isPressedBeforeP){
			if(isPaused){
				isPaused = false;
			}else {
				isPaused = true;
			}
			isPressedBeforeP = true;
		}else if(KeyboardInput.keys[GLFW_KEY_P] && isPressedBeforeP){
			//System.out.println("P was already pressed!");
		}else {
			isPressedBeforeP = false;
		}

		if(KeyboardInput.keys[GLFW_KEY_F] && !isStarted){
			isStarted = true;
		}

		if (!isPaused) {
			// Transformation durchführen (Matrix anpassen)

			//Cube
			modelMatrix = new Matrix4f().rotateX(angle).rotateY(angle).rotateZ(angle).translate(0, -5, -15);
			vaos.get(0).updateModel(modelMatrix);

			//Pyramide
			modelMatrix = new Matrix4f().rotateX(angle / 2).rotateY(angle * 2).rotateZ(-angle).translate(4, 2, -10);
			vaos.get(1).updateModel(modelMatrix);

			//Tetraeder
			modelMatrix = new Matrix4f().translate(0, 8f, 0).rotateZ(angle / 2).translate(0, -8f, 0).rotateX(angle / 4).rotateY(-angle).scale((float) (2 * (Math.sin(angle) + 2)), (float) (2 * (Math.sin(angle) + 2)), (float) (2 * (Math.sin(angle) + 2))).translate(-3, 0, -25);
			vaos.get(2).updateModel(modelMatrix);

			//Plane
			modelMatrix = new Matrix4f().scale(500).rotateX((float) Math.toRadians(-90)).translate(0, -50, 0);
			vaos.get(3).updateModel(modelMatrix);

			//File 1
			modelMatrix = new Matrix4f().rotateX((float) Math.toRadians(0)).rotateY(-angle).scale(2).translate(30, -10, -60);
			vaos.get(4).updateModel(modelMatrix);

			//File 2 Ball 1
			modelMatrix = new Matrix4f().translate(0, 0, -30).rotateY(angle/2).translate(-10,10,-100).scale(2);
			vaos.get(5).updateModel(modelMatrix);

			//File 3
			modelMatrix = new Matrix4f().rotateZ(angle).translate(0, 0, 10).rotateY(angle).multiply(modelMatrix);
			vaos.get(6).updateModel(modelMatrix);

			modelMatrix = new Matrix4f().scale(5).rotateX(angle).rotateZ(angle).multiply(vaos.get(5).modelMatrix);
			vaos.get(5).updateModel(modelMatrix);

			//File 4
			modelMatrix = new Matrix4f().rotateX((float) Math.toRadians(80)).translate(-5, 5, -15);
			vaos.get(7).updateModel(modelMatrix);


/*
			//Matrix for Testing
			modelMatrix = new Matrix4f().rotateX(angle/2).rotateY(angle).rotateZ(angle/4).scale(2).translate(0, 0, -8);
			vaos.get(0).updateModel(modelMatrix);
*/

			angle += 0.01;
			offset += delta;
			if (offset > 6.5 || offset < -6.5) {
				delta *= -1;
			}
		}

		if(isStarted) {
			if (!isInMenu) {
				viewMatrix = camera.move(this.vaos).rotate((float) CursorInput.xPos, (float) CursorInput.yPos).toMatrix();
			} else {
				//viewMatrix = camera.move().toMatrix();
			}
		}
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Matrix an Shader übertragen
		// VAOs zeichnen
		glUniformMatrix4fv(locMatrices[1], false, viewMatrix.getValuesAsArray());
		glUniformMatrix4fv(locMatrices[2], false, projectionMatrix.getValuesAsArray());

		if(isStarted && !isInMenu) {
			glUseProgram(shaderProgram.getId());
			for (int i = 0; i < vaos.size() - 1; i++) {
				VAO tmp = vaos.get(i);
				glUniformMatrix4fv(locMatrices[0], false, tmp.modelMatrix.getValuesAsArray());
				glBindTexture(GL_TEXTURE_2D, tmp.texture.getId());
				glBindVertexArray(tmp.location);
				glDrawElements(GL_TRIANGLES, tmp.mesh.indicesVertices.length, GL_UNSIGNED_INT, 0);
			}
		}else {
			glUseProgram(shaderProgram2.getId());
			VAO tmp = vaos.get(vaos.size()-1);
			glUniformMatrix4fv(locMatrices[0], false, tmp.modelMatrix.getValuesAsArray());
			glBindTexture(GL_TEXTURE_2D, tmp.texture.getId());
			glBindVertexArray(tmp.location);
			glDrawElements(GL_TRIANGLES, tmp.mesh.indicesVertices.length, GL_UNSIGNED_INT, 0);
		}

/*
		//for Cube testing
		for (int i = 0; i < vaos.size(); i++) {
			glUseProgram(shaderProgram.getId());
			VAO tmp = vaos.get(i);
			glBindTexture(GL_TEXTURE_2D, tmp.texture.getId());
			glUniformMatrix4fv(locMatrices[0], false, tmp.modelMatrix.getValuesAsArray());
			glBindVertexArray(tmp.location);
			glDrawElements(GL_TRIANGLES, tmp.mesh.indicesVertices.length, GL_UNSIGNED_INT, 0);
		}
*/
	}

	@Override
	public void destroy() {
	}
}