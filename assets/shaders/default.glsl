#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;

out vec4 vertexColor;

void main()
{
    FColor = aColor;
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core
out vec4 Color;
in vec4 fColor;// the input variable from the vertex shader (same name and same type)

void main()
{
    fColor = Color;
}