#version 330

in vec3 color;
in vec3 normal;
in vec3 pos;
in vec2 uv;
out vec3 pixelFarbe;
uniform sampler2D s;

void main(){
    vec3 n = normalize(normal);
    vec3 l = vec3(0,30,50) - pos;
    l = normalize(l);
    vec3 r = reflect(-l,n);
    r = normalize(r);
    vec3 v = -pos;
    v = normalize(v);

    float intensity = 0.1 * 1 + 1 * (max(dot(l, n),0) * 0.7 + pow(max(dot(r, v),0),20) * 1.3);  //ambient, diffus, specular
    //float intensity = (max(dot(l, n),0) * 1 + pow(max(dot(r, v),0),10) * 1);

    if(intensity > 1){
        intensity = 1;
    }
    if(intensity < 0){
        intensity = 0;
    }

    pixelFarbe = texture(s,uv).rgb * intensity;
}