package projekt;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {
	public float matrix[][] = new float[4][4];


	public Matrix4() {
		// TODO mit der Identitätsmatrix initialisieren
		for (int y = 0; y < 4; y++) {
			this.matrix[y][y] = 1;
		}
	}

	public Matrix4(int a){
		for (int i = 0; i < 4; i++) {				//{  0, 1, 2, 3,
			for (int j = 0; j < 4; j++) {			//	 4, 5, 6, 7,
				this.matrix[i][j] = i * 4 + j;		//	 8, 9,10,11
			}										//	12,13,14,15}
		}
	}

	public Matrix4(Matrix4 copy) {
		// TODO neues Objekt mit den Werten von "copy" initialisieren
		for (int i = 0; i < 4; i++) {
			this.matrix[i] = copy.matrix[i].clone();
		}
	}

	public Matrix4(float near, float far, float b, float h) {
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzufügen
		this.matrix[0][0] = (2 * near) / b;
		this.matrix[1][1] = (2 * near) / h;
		this.matrix[2][2] = (-far - near) / (far - near);
		this.matrix[2][3] = (-2 * near * far) / (far - near);
		this.matrix[3][2] = -1;
	}

	public  Matrix4(Vector3f pos, Vector3f u, Vector3f v, Vector3f n){
		this.matrix[0] = new float[]{u.x, u.y, u.z, -pos.x};
		this.matrix[1] = new float[]{v.x, v.y, v.z, -pos.y};
		this.matrix[2] = new float[]{n.x, n.y, n.z, -pos.z};
		this.matrix[3] = new float[]{  0,	0,	 0,		 1};
	}

	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfügen
		float newMatrix[][] = new float[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				float sum = 0;
				for (int k = 0; k < 4; k++) {
					sum += other.matrix[i][k] * this.matrix[k][j];
				}
				newMatrix[i][j] = sum;
			}
		}

		this.matrix = newMatrix;
		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		// TODO Verschiebung um x,y,z zu this hinzufügen
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{1,0,0,x},{0,1,0,y},{0,0,1,z},{0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichmäßige Skalierung um Faktor "uniformFactor" zu this hinzufügen
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{uniformFactor,0,0,0},{0,uniformFactor,0,0},{0,0,uniformFactor,0},{0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichförmige Skalierung zu this hinzufügen
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{sx,0,0,0},{0,sy,0,0},{0,0,sz,0},{0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public Matrix4 rotateX(float angle) {
		// TODO Rotation um X-Achse zu this hinzufügen
		//angle = (float)Math.toRadians((double)angle);
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{1,0,0,0},{0,(float)Math.cos(angle),(float)-Math.sin(angle),0},{0,(float)Math.sin(angle),(float)Math.cos(angle),0},{0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public Matrix4 rotateY(float angle) {
		// TODO Rotation um Y-Achse zu this hinzufügen
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{(float)Math.cos(angle),0,(float)-Math.sin(angle),0},{0,1,0,0},{(float)Math.sin(angle),0,(float)Math.cos(angle),0},{0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public Matrix4 rotateZ(float angle) {
		// TODO Rotation um Z-Achse zu this hinzufügen
		Matrix4 newMatrix = new Matrix4();
		newMatrix.matrix = new float[][]{{(float)Math.cos(angle),(float)-Math.sin(angle),0,0}, {(float)Math.sin(angle),(float)Math.cos(angle),0,0}, {0,0,1,0}, {0,0,0,1}};

		this.multiply(newMatrix);

		return this;
	}

	public float[] getValuesAsArray() {
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefüllt) herausgeben
		float arr[] = new float[16];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int pos = i * 4 + j;
				arr[pos] = this.matrix[j][i];
			}
		}
		return arr;
	}


	public static void main(String[] args) {
		Matrix4 test = new Matrix4(1);
		System.out.println("test");
	}
}
