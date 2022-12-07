#version 330
out vec3 color;
out vec3 n;
out vec3 p;

layout(location=0) in vec4 vertices;
layout(location=1) in vec3 newColor;
layout(location=2) in vec3 normals;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(){
    color = newColor;

    mat3 normalMatrix = transpose(inverse(mat3(modelMatrix)));
    n = normalMatrix * normals;
    n = normalize(n);
    p = (modelMatrix * vertices).xyz;
    p = normalize(p);

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vertices;
}