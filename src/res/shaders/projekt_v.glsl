#version 330
out vec3 color;

layout(location=0) in vec4 vertices;
layout(location=1) in vec3 newColor;
uniform mat4 modelMatrix1;
uniform mat4 modelmatrix2;
uniform mat4 projectionMatrix;

void main(){
    color = newColor;
    gl_Position = projectionMatrix * modelMatrix1 * vertices;
}