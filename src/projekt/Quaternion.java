package projekt;



public class Quaternion {
    public float angle;
    public VectorF vec;


    public Quaternion(float angleDeg, VectorF vector){
        this.angle = (float)Math.toRadians(angleDeg);
        this.vec = new VectorF(vector.x *(float)Math.sin(angle/2), vector.y *(float)Math.sin(angle/2), vector.z *(float)Math.sin(angle/2));
        this.angle = (float)Math.cos(angle / 2);
    }

    public Quaternion(VectorF vector){
        this.angle = 0;
        this.vec = vector.normalize();
    }

    public Quaternion conjugate(){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));
        out.angle = this.angle;
        out.vec = new VectorF(-vec.x, -vec.y,-vec.z);
        return out;
    }

    public Quaternion multiply(Quaternion other){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));

        if(this.vec.x == -0){
            this.vec.x = 0;
        }
        if(this.vec.y == -0){
            this.vec.y = 0;
        }
        if(this.vec.z == -0){
            this.vec.z = 0;
        }

        if(other.vec.x == -0){
            other.vec.x = 0;
        }
        if(other.vec.y == -0){
            other.vec.y = 0;
        }
        if(other.vec.z == -0){
            other.vec.z = 0;
        }

        out.angle = (this.angle * other.angle) - (this.vec.x * other.vec.x) - (this.vec.y * other.vec.y) - (this.vec.z * other.vec.z);
        out.vec.x = (this.angle * other.vec.x) + (this.vec.x * other.angle) + (this.vec.y * other.vec.z) - (this.vec.z * other.vec.y);
        out.vec.y = (this.angle * other.vec.y) - (this.vec.x * other.vec.z) + (this.vec.y * other.angle) + (this.vec.z * other.vec.x);
        out.vec.z = (this.angle * other.vec.z) + (this.vec.x * other.vec.y) - (this.vec.y * other.vec.x) + (this.vec.z * other.angle);



/*
        out.angle = (this.angle * other.angle) - (this.vec.x * other.vec.x) - (this.vec.y * other.vec.y) - (this.vec.z * other.vec.z);
        out.vec.x = (this.angle * other.vec.x) + (this.vec.x * other.angle) + (this.vec.y * other.vec.z) - (this.vec.z * other.vec.y);
        out.vec.y = (this.angle * other.vec.y) + (this.vec.z * other.angle) + (this.vec.z * other.vec.x) - (this.vec.x * other.vec.z);
        out.vec.z = (this.angle * other.vec.z) + (this.vec.z * other.angle) + (this.vec.x * other.vec.y) - (this.vec.y * other.vec.x);
*/
        return out;
    }

    public Quaternion multiplyVec(VectorF other){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));
        out.angle = -(this.vec.x * other.x) - (this.vec.y * other.y) - (this.vec.z * other.z);
        out.vec.x = (this.angle * other.x) + (this.vec.y * other.z) - (this.vec.z * other.y);
        out.vec.y = (this.angle * other.y) + (this.vec.z * other.x) - (this.vec.x * other.z);
        out.vec.z = (this.angle * other.z) + (this.vec.x * other.y) - (this.vec.y * other.x);

        return out;
    }

    public Quaternion normalize(){
        Quaternion out = new Quaternion(0,new VectorF(0,0,0));
        float denominator = (float)Math.sqrt(Math.pow(this.angle*this.angle,2) + Math.pow(this.vec.x*this.vec.x,2) + Math.pow(this.vec.y*this.vec.y,2) + Math.pow(this.vec.z*this.vec.z,2));
        out.angle = this.angle / denominator;
        out.vec = this.vec.multiplyScalar(1/denominator);

        return out;
    }
}
