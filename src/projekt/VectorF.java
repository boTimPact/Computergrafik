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


    public VectorF negate(){
        VectorF out = new VectorF(this.x,this.y,this.z);
        out.x = -this.x;
        out.y = -this.y;
        out.z = -this.z;

        return out;
    }


    public VectorF add(VectorF other){
        VectorF out = new VectorF(this.x,this.y,this.z);
        out.x += other.x;
        out.y += other.y;
        out.z += other.z;

        return out;
    }

    //Skalarprodukt
    public VectorF dot(float scalar){
        VectorF out = new VectorF(this.x,this.y,this.z);
        out.x *= scalar;
        out.y *= scalar;
        out.z *= scalar;

        return out;
    }

    public VectorF multiplyMatrix(Matrix4f matrix){
        VectorF out = new VectorF(this.x,this.y,this.z);
        out.x = matrix.matrix[0][0] * this.x + matrix.matrix[0][1] * this.y + matrix.matrix[0][2] * this.z + matrix.matrix[0][3] * w;
        out.y = matrix.matrix[1][0] * this.x + matrix.matrix[1][1] * this.y + matrix.matrix[1][2] * this.z + matrix.matrix[1][3] * w;
        out.z = matrix.matrix[2][0] * this.x + matrix.matrix[2][1] * this.y + matrix.matrix[2][2] * this.z + matrix.matrix[2][3] * w;

        return out;
    }

    //Kreuzprodukt
    public VectorF cross(VectorF other){
        VectorF out = new VectorF(1,1,1);
        out.x = this.y * other.z - this.z * other.y;
        out.y = this.z * other.x - this.x * other.z;
        out.z = this.x * other.y - this.y * other.x;

        return out;
    }


    public VectorF normalize(){
        VectorF out = new VectorF(this.x,this.y,this.z);
        double amount = Math.sqrt(out.x * out.x + out.y * out.y + out.z * out.z);
        out.x = (float) (this.x / amount);
        out.y = (float) (this.y / amount);
        out.z = (float) (this.z / amount);

        return out;
    }
}
