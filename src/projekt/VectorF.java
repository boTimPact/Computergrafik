package projekt;

public class VectorF {
    public float x;
    public float y;
    public float z;
    public float w;

    public VectorF(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VectorF(float x, float y){
        this.x = x;
        this.y = y;
    }


    public VectorF(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    public void negate(){
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }


    public void add(VectorF other){
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    //Skalarprodukt
    public void dot(float scalar){
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
    }

    public void multiplyMatrix(Matrix4f matrix){
        this.x = matrix.matrix[0][0] * this.x + matrix.matrix[0][1] * this.y + matrix.matrix[0][2] * this.z + matrix.matrix[0][3] * w;
        this.y = matrix.matrix[1][0] * this.x + matrix.matrix[1][1] * this.y + matrix.matrix[1][2] * this.z + matrix.matrix[1][3] * w;
        this.z = matrix.matrix[2][0] * this.x + matrix.matrix[2][1] * this.y + matrix.matrix[2][2] * this.z + matrix.matrix[2][3] * w;
    }

    //Kreuzprodukt
    public VectorF cross(VectorF other){
        VectorF out = new VectorF(1,1,1);
        out.x = this.y * other.z - this.z * other.y;
        out.y = this.z * other.x - this.x * other.z;
        out.z = this.x * other.y - this.y * other.z;

        return out;
    }


    public VectorF normalize(){
        float amount = (float)Math.sqrt(this.x*this.x+this.y*this.y+this.y*this.z);
        this.x /=amount;
        this.y /=amount;
        this.z /=amount;

        return this;
    }
}
