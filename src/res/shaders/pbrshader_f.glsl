#version 330

in vec3 normal;
in vec3 fragmentPosition;
in vec2 uv;
in vec3 lightpos;
out vec3 pixelFarbe;
uniform sampler2D s;

#define PI 3.1415926535897932384626433832795;

float normalDistribution(float ndoth, float roughness, float pi){
    return (roughness * roughness) / max((pi * pow(pow(ndoth,2) * (roughness * roughness -1) + 1, 2)), 0.00000001); // cant be zero
}
float geometry(float ndotv, float ndotl, float roughness){
    float k = pow(roughness+1, 2) / 8;
    return (ndotv / (ndotv * (1-k) + k)) * (ndotl / (ndotl * (1-k) + k));
}
vec3 fresnel(float hdotv, vec3 reflectivity){
    return reflectivity + (1 - reflectivity) * pow(1- hdotv,5);
}

void main(){
    float pi = PI;
    //TODO: get from input;
    float metallic = 0.1;
    float roughness = 0.5;
    vec3 lightPosition = lightpos;
    vec3 lightColor = vec3(1,1,1);
    vec3 meshColor = texture(s,uv).rgb;

    //vec3 cameraPosition = vec3(0,0,0);
    vec3 N = normalize(normal);
    vec3 V = normalize(-fragmentPosition);
    vec3 baseReflectivity = mix(vec3(0.04), meshColor, metallic);
    //vec3 r = normalize(reflect(-lightPosition,N));

    vec3 L = normalize(lightPosition-fragmentPosition);
    vec3 H = normalize(V+L);
    float distance = length(lightPosition-fragmentPosition);
    float attenuation = 1.0/(distance*distance);
    vec3 radiance = lightColor * attenuation;

    //Cook-Torrance
    float NDotV = max(dot(N,V), 0.00000001);
    float NDotL = max(dot(N,L), 0.00000001);
    float HDotV = max(dot(H,V), 0.0);
    float NDotH = max(dot(N,H), 0.0);

    float D = normalDistribution(NDotH, roughness, pi);
    float G = geometry(NDotV, NDotL, roughness);
    vec3 F = fresnel(HDotV, baseReflectivity);

    vec3 specular = D * G * F;
    specular /= 4.0 * NDotV * NDotL;

    vec3 kD = vec3(1.0) - F;
    kD *= 1.0 - metallic;

    vec3 ambient = vec3(0.03) * radiance;
    vec3 color = ambient + (kD * meshColor / pi + specular) * radiance * NDotL;

    //color = color / (color + vec3(1.0));
    //color = pow(color, vec3(1.0/2.2));

    pixelFarbe =  vec3(color);
}

