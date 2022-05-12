package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import util.Time;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    public float r,g,b,a;
    private boolean fadeToBlack = false;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.height = 1080;
        this.width = 1920;
        this.title = "Mario";
        r=1;
        b=1;
        g=1;
        a=1;
    }
    public static void changeScene(int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "unknown scene "+newScene +"";
                break;
        }
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error back
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        //System.err.println("We have an error.");
        // GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be full screen

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context curent
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        Window.changeScene(0);
    }

    private void loop() {
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            // Set the clear color
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            if(dt >= 0){
                currentScene.update(dt);
            }
            glfwSwapBuffers(glfwWindow); // swap the color buffers

            endTime = Time.getTime();
            dt = endTime - beginTime; // calculate Delta time
            beginTime = endTime;
        }
    }

}
