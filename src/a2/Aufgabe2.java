package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

import java.util.LinkedList;
import java.util.List;

public class Aufgabe2 extends AbstractOpenGLBase {

	private List<Integer> vaoID = new LinkedList<>();

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		vaoID.add(createVAO());
		float vertices[] = {
				0,0,
				0,1,
				1,0
		};
		float color[] = {
				0,0,0,
				1,0,0,
				0,0,1
		};
		createVBO(vertices,true,0);
		createVBO(color, false, 1);

		vaoID.add(createVAO());
		float vertices2[] = {
				-0.75f,-0.2f,
				0,-0.75f,
				0,0,
				-0.75f,0,
				0,0.75f,
				0,0,
		};

		float color2[] = {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1
		};
		createVBO(vertices2,true,0);
		createVBO(color2, false, 1);
	}

	@Override
	public void update() {

	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren

		// hier vorher erzeugte VAOs zeichnen

		glBindVertexArray(vaoID.get(0));
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glBindVertexArray(vaoID.get(1));
		glDrawArrays(GL_TRIANGLES,0,6);
	}

	@Override
	public void destroy() {
	}

	public int createVAO(){
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		return vao;
	}

	public void createVBO(float[] input, boolean isVertex, int index){
		int size = 0;
		if(isVertex){
			size = 2;
		}else {
			size = 3;
		}

		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, input,GL_STATIC_DRAW);
		glVertexAttribPointer(index,size,GL_FLOAT, false, 0,0);
		glEnableVertexAttribArray(index);
	}
}
