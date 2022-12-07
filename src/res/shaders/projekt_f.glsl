#version 330

in vec3 color;
in vec3 n;
in vec3 p;
out vec3 pixelFarbe;

void main(){
    vec3 l = vec3(0,100,50) - p;
    l = normalize(l);
    vec3 r = reflect(-l,n);
    r = normalize(r);
    vec3 v = -p;
    v = normalize(v);

    float intensity = 0.1 * 1 + 1 * (max(dot(l, n),0) * 1 + pow(max(dot(r, v),0),10) * 1);
    //float intensity = (max(dot(l, n),0) * 1 + pow(max(dot(r, v),0),10) * 1);


    pixelFarbe = color * intensity;
}