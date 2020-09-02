/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

/**
 *
 * @author YTR
 */
import java.awt.event.*;
import javax.swing.JFrame;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

public class AntiAlias
        extends JFrame
        implements GLEventListener, KeyListener {

    private GLCapabilities caps;
    private GLCanvas canvas;
    private float rotAngle = 15f;
    private boolean rotate = false;

    public AntiAlias() {
        super("aargb");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        caps = new GLCapabilities(profile);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);

        getContentPane().add(canvas);
    }

    public void run() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(512, 512);
        setLocationRelativeTo(null);
        setVisible(true);
        canvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new AntiAlias().run();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        //
        float values[] = new float[2];
        gl.glGetFloatv(GL.GL_LINE_WIDTH, values, 0);
        System.out.println("GL.GL_LINE_WIDTH_GRANULARITY value is " + values[0]);
        gl.glGetFloatv(GL.GL_LINE_WIDTH, values, 0);
        System.out.println("GL.GL_LINE_WIDTH_RANGE values are " + values[0] + ", "
                + values[1]);
        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
        gl.glLineWidth(1.5f);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void display(GLAutoDrawable drawable) {
        //GL gl = drawable.getGL();
        rotAngle += 0.1f;
        GL2 gl = drawable.getGL().getGL2();

        //
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glPushMatrix();
        gl.glRotatef(-rotAngle, 0.0f, 0.0f, 0.1f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glPushMatrix();
        gl.glRotatef(rotAngle, 0.0f, 0.0f, 0.1f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glFlush();
        if (rotate) {
            rotAngle += 1f;
        }
        if (rotAngle >= 360f) {
            rotAngle = 0f;
        }
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        if (w <= h) //
        {
            glu.gluOrtho2D(-1.0, 1.0, -1.0 * (float) h / (float) w, //
                    1.0 * (float) h / (float) w);
        } else {
            glu.gluOrtho2D(-1.0 * (float) w / (float) h, //
                    1.0 * (float) w / (float) h, -1.0, 1.0);
        }
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_R:
                rotate = !rotate;
                canvas.display();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent key) {
    }

    public void dispose(GLAutoDrawable glad) {

    }
}
