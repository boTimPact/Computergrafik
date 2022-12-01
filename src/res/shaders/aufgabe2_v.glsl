#version 330

out vec3 color1;

layout(location=0) in vec2 vertices;
layout(location=1) in vec3 color;


vec2 rotate(vec2 vector, float rad){
    mat2 rotMat = mat2(cos(rad), -sin(rad), sin(rad), cos(rad));
    return rotMat * vector;
}

void main(){
    color1 = color;
    vec2 vector = vertices;//rotate(vertices, 0.5);
    gl_Position = vec4(vector, 0,1);
}