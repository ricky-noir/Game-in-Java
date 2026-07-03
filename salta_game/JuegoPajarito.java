import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// Proyecto para la clase de programación - juego estilo Flappy Bird
// Nota: intenté organizarlo con clases internas como vimos en el curso
public class JuegoPajarito extends JPanel implements ActionListener, KeyListener {

    // tamaño de la ventana del juego
    int anchoTablero = 360;
    int altoTablero = 640;

    // imagenes que voy a usar
    Image imgFondo;
    Image imgPajaro;
    Image imgTuboArriba;
    Image imgTuboAbajo;

    // datos iniciales del pajarito
    int pajaroX = anchoTablero / 8;
    int pajaroY = altoTablero / 2;
    int pajaroAncho = 34;
    int pajaroAlto = 24;

    // clase para representar al pajarito (el personaje que controla el usuario)
    class Pajaro {
        int x = pajaroX;
        int y = pajaroY;
        int ancho = pajaroAncho;
        int alto = pajaroAlto;
        Image img;

        Pajaro(Image img) {
            this.img = img;
        }
    }

    // datos iniciales de los tubos (obstaculos)
    int tuboX = anchoTablero;
    int tuboY = 0;
    int tuboAncho = 64;   // la imagen original es mas grande, la reduje como 1/6
    int tuboAlto = 512;

    // clase para los tubos que van apareciendo
    class Tubo {
        int x = tuboX;
        int y = tuboY;
        int ancho = tuboAncho;
        int alto = tuboAlto;
        Image img;
        boolean yaPaso = false; // para saber si el pajaro ya pasó este tubo y sumar punto

        Tubo(Image img) {
            this.img = img;
        }
    }

    // ---- variables del juego ----
    Pajaro pajaro;
    int velocidadX = -4;   // que tan rapido se mueven los tubos hacia la izquierda
    int velocidadY = 0;    // velocidad vertical del pajaro (sube/baja)
    int gravedad = 1;

    ArrayList<Tubo> listaTubos;
    Random rand = new Random();

    Timer cicloJuego;
    Timer temporizadorTubos;
    boolean juegoTerminado = false;
    double puntaje = 0;

    // constructor, aca se configura todo antes de que arranque el juego
    JuegoPajarito() {
        setPreferredSize(new Dimension(anchoTablero, altoTablero));
        setFocusable(true);
        addKeyListener(this);

        // cargamos las imagenes desde la carpeta del proyecto
        imgFondo = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        imgPajaro = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        imgTuboArriba = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        imgTuboAbajo = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        pajaro = new Pajaro(imgPajaro);
        listaTubos = new ArrayList<Tubo>();

        // este timer se encarga de ir poniendo tubos nuevos cada 1.5 segundos
        temporizadorTubos = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ponerTubos();
            }
        });
        temporizadorTubos.start();

        // este es el timer principal del juego, se ejecuta 60 veces por segundo (60 fps)
        cicloJuego = new Timer(1000 / 60, this);
        cicloJuego.start();
    }

    // metodo que crea un par de tubos (uno arriba y otro abajo) con un espacio para pasar
    void ponerTubos() {
        // se calcula una posicion aleatoria para que el juego no sea siempre igual
        int tuboYAleatorio = (int) (tuboY - tuboAlto / 4 - Math.random() * (tuboAlto / 2));
        int espacioLibre = altoTablero / 4; // el hueco por donde pasa el pajaro

        Tubo tuboArriba = new Tubo(imgTuboArriba);
        tuboArriba.y = tuboYAleatorio;
        listaTubos.add(tuboArriba);

        Tubo tuboAbajo = new Tubo(imgTuboAbajo);
        tuboAbajo.y = tuboArriba.y + tuboAlto + espacioLibre;
        listaTubos.add(tuboAbajo);
    }

}