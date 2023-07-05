#version 330
out vec3 color;


layout(location=0) in vec4 vertices;
layout(location=1) in vec3 newColor;
layout(location=2) in vec3 normals;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(){
    color = newColor;

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vertices;
}