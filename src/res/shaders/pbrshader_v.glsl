#version 330

out vec3 normal;
out vec3 fragmentPosition;
out vec2 uv;
out vec3 lightpos;

layout(location=0) in vec4 vertices;
layout(location=1) in vec3 newColor;
layout(location=2) in vec3 normals;
layout(location=3) in vec2 texPos;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 lightMatrix;


void main(){
    // no lightmatrix yet!!
    lightpos = (projectionMatrix * viewMatrix * lightMatrix * vec4(1,1,0,0)).rgb;
    mat3 normalMatrix = transpose(inverse(mat3(modelMatrix)));
    normal = normalMatrix * normals;
    normal = normalize(normals);
    fragmentPosition = (modelMatrix * vertices).xyz;
    fragmentPosition = normalize(fragmentPosition);

    uv = texPos;

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vertices;
}