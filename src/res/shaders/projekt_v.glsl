#version 330

out vec3 color;
out vec3 normal;
out vec3 pos;
out vec2 uv;

layout(location=0) in vec4 vertices;
layout(location=1) in vec3 newColor;
layout(location=2) in vec3 normals;
layout(location=3) in vec2 texPos;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;


void main(){
    color = newColor;

    mat3 normalMatrix = transpose(inverse(mat3(modelMatrix)));
    normal = normalMatrix * normals;
    normal = normalize(normal);
    pos = (modelMatrix * vertices).xyz;
    pos = normalize(pos);

     uv = texPos;

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vertices;
}