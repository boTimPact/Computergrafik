#version 330

out vec3 pixelFarbe;
struct Circle{
    float x;
    float y;
    float rad;
};

struct Line{
    vec2 origin;
    vec2 direction;
};

bool isInCircle(float x, float y, Circle c){
    return (x - c.x) * (x - c.x) + (y - c.y) * (y - c.y) < c.rad * c.rad;
}

vec2 rotate(float x, float y, float rad){
    mat2 rotMat = mat2(cos(rad), -sin(rad), sin(rad), cos(rad));
    vec2 vector = vec2(x, y);
    return rotMat * vector;
}

bool isOnLine(float x, float y, Line test){
    float lampdaX = 0;
    float lampdaY = 0;

    if(test.direction.x > test.direction.y){
        lampdaX = (x - test.origin.x) / test.direction.x;
        float newY = test.origin.y + lampdaX * test.direction.y;

        if (int(y) == int(newY) && lampdaX <= 1 && lampdaX >= 0){
            return true;
        }
    }else{
        lampdaY = (y - test.origin.y) / test.direction.y;
        float newX = test.origin.x + lampdaY * test.direction.x;

        if (int(x) == int(newX) && lampdaY <= 1 && lampdaY >= 0){
            return true;
        }
    }
    return false;
}

void main() {
    float x = gl_FragCoord.x;
    float y = gl_FragCoord.y;

    Circle test = Circle(350.0, 350.0, 150.0);
    Circle test2 = Circle(600, 600, 200);

    vec2 vec_rotate = rotate(x, y, 0.5);

    float x_rot = vec_rotate.x;
    float y_rot = vec_rotate.y;

    Line straight = Line(vec2(100, 100), vec2(200, 500));

    pixelFarbe = vec3(0, 0, 0);
    if(x > 100 && x < 600 && y > 175 && y < 525){
        pixelFarbe = vec3(0.3, 0.0, 0.7);
    }
    else{
        pixelFarbe = vec3(0.0, 0.0, 0.0);
    }
    if(isInCircle(x, y, test)){
        pixelFarbe += vec3(1.0, 1.0, 1.0);
    }
    if(isInCircle(x,y,test2)){
        pixelFarbe = vec3(0, 1, 0);
    }
    if(x_rot > 100 && x_rot < 600 && y_rot + 600 > 175 && y_rot + 600 < 525){
        pixelFarbe = vec3(1, 1, 0);
    }
    if(isOnLine(x, y, straight)){
        pixelFarbe = vec3(1, 0, 0);
    }
}


