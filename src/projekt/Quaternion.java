package projekt;

public class Quaternion {
    public float angle;
    public VectorF vec;


    public Quaternion(float angle, VectorF vector){
        this.angle = (float)Math.toRadians(angle);
        this.vec = new VectorF(vector.x *(float)Math.sin(Math.toRadians(angle)/2), vector.y *(float)Math.sin(Math.toRadians(angle)/2), vector.z *(float)Math.sin(Math.toRadians(angle)/2));
        this.angle = (float)Math.cos(angle / 2);
    }

    public Quaternion conjugate(){
        return new Quaternion(0, new VectorF(-vec.x, -vec.y,-vec.z));
    }

    public Quaternion multiply(Quaternion other){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));
        out.angle = (this.angle * other.angle) - (this.vec.x * other.vec.x) - (this.vec.y * other.vec.y) - (this.vec.z * other.vec.z);
        out.vec.x = (this.vec.x * other.angle) + (this.angle * other.vec.x) + (this.vec.y * other.vec.z) - (this.vec.z * other.vec.y);
        out.vec.y = (this.vec.y * other.vec.y) + (this.vec.z * other.vec.x) - (this.vec.x * other.vec.z);
        out.vec.z = (this.vec.z * other.vec.z) + (this.vec.x * other.vec.y) - (this.vec.y * other.vec.x);

        return new Quaternion(0, new VectorF(1,1,1));
    }

    public Quaternion multiplyVec(VectorF other){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));
        out.angle = -(this.vec.x * other.x) - (this.vec.y * other.y) - (this.vec.z * other.z);
        out.vec.x = (this.angle * other.x) + (this.vec.y * other.z) - (this.vec.z * other.y);
        out.vec.y = (this.angle * other.y) + (this.vec.z * other.x) - (this.vec.x * other.z);
        out.vec.z = (this.angle * other.z) + (this.vec.x * other.y) - (this.vec.y * other.x);

        return out;
    }
}
