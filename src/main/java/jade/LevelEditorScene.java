package jade;


public class LevelEditorScene extends Scene{
    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 vertexColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    FColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "out vec4 Color;\n" +
            "in vec4 fColor;// the input variable from the vertex shader (same name and same type)\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = Color;\n" +
            "}";
    public LevelEditorScene(){

    }
    @Override
    public void update(float dt) {

    }
}
