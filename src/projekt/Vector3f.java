package projekt;

public class Vector3f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void multiplyMatrix(Matrix4f matrix){
        this.x = matrix.matrix[0][0] * this.x + matrix.matrix[0][1] * this.y + matrix.matrix[0][2] * this.z + matrix.matrix[0][3] * w;
        this.y = matrix.matrix[1][0] * this.x + matrix.matrix[1][1] * this.y + matrix.matrix[1][2] * this.z + matrix.matrix[1][3] * w;
        this.z = matrix.matrix[2][0] * this.x + matrix.matrix[2][1] * this.y + matrix.matrix[2][2] * this.z + matrix.matrix[2][3] * w;
    }
}
