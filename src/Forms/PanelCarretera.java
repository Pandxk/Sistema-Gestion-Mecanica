/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelCarretera extends JPanel implements ActionListener {

    private Timer timer;

    // --- VARIABLES DE ESTADO ---
    private int lineaX = 0;
    private int garageX = 1500;
    private int autoReboteY = 0;
    private boolean subiendo = true;
    private boolean autoVisible = true;

    // Estados de la "Película"
    private boolean conHumoNegro = false;
    private boolean llantaSalida = false;
    private boolean modoDerrape = false;
    private boolean efectoBrillo = false;

    // Variables físicas
    private int llantaSueltaX = 0;
    private int llantaSueltaY = 0;
    private double anguloLlanta = 0;
    private double anguloAuto = 0;

    // Configuración
    private final int VELOCIDAD = 15;
    private final int ALTO_CARRETERA = 240;
    private final int AUTO_POS_X_FIJA = 120;

    public PanelCarretera() {
        // Color base de respaldo (por si acaso)
        this.setBackground(new Color(80, 75, 70));
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // ACTIVAR ANTIALIASING (Para que se vea HD)
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        int ancho = getWidth();
        int alto = getHeight();
        int centroY = alto / 2;
        int inicioCarreteraY = centroY - (ALTO_CARRETERA / 2);
        int autoPosY = centroY - 20 + autoReboteY;

        // --- 1. FONDO PROCEDURAL (EFECTO TIERRA/ROCA CON CÓDIGO) ---
        // A. Fondo base sólido
        g2.setColor(new Color(80, 75, 70)); // Gris tierra oscuro
        g2.fillRect(0, 0, ancho, alto);

        // B. Generar "Ruido" (Piedritas)
        // Dibujamos manchitas aleatorias para dar textura sin usar imagen
        // Piedritas oscuras
        g2.setColor(new Color(60, 55, 50));
        for (int i = 0; i < 400; i++) { // 400 piedritas oscuras
            int rx = (int) (Math.random() * ancho);
            int ry = (int) (Math.random() * alto);
            // Dibujar cuadradito
            g2.fillRect(rx, ry, 4, 4);
        }

        // Piedritas claras
        g2.setColor(new Color(110, 100, 95));
        for (int i = 0; i < 400; i++) { // 400 piedritas claras
            int rx = (int) (Math.random() * ancho);
            int ry = (int) (Math.random() * alto);
            // Dibujar cuadradito pequeño
            g2.fillRect(rx, ry, 2, 2);
        }

        // --- 2. DIBUJAR CARRETERA (ENCIMA DE LA TIERRA) ---
        dibujarCarreteraAsfalto(g2, ancho, inicioCarreteraY);

        // --- 3. OBJETOS DETRÁS DEL AUTO ---
        if (llantaSalida) {
            dibujarLlantaRealista(g2);
        }
        if (conHumoNegro && autoVisible) {
            dibujarHumoNegro(g2, AUTO_POS_X_FIJA, autoPosY);
        }

        // --- 4. AUTO (CON ROTACIÓN SI DERRAPA) ---
        if (autoVisible) {
            AffineTransform old = g2.getTransform();
            if (modoDerrape) {
                // Rotar el auto sobre su eje
                g2.rotate(Math.toRadians(anguloAuto), AUTO_POS_X_FIJA + 35, autoPosY + 20);
            }
            dibujarAuto(g2, AUTO_POS_X_FIJA, autoPosY);
            g2.setTransform(old); // Restaurar rotación
        }

        // --- 5. GARAGE GIGANTE (FRENTE) ---
        dibujarGarageGigante(g2, garageX, inicioCarreteraY - 20, ALTO_CARRETERA + 40);

        // --- 6. EFECTO BRILLO (EXITO) ---
        if (efectoBrillo && autoVisible) {
            dibujarEstrellitas(g2, AUTO_POS_X_FIJA, autoPosY);
        }
    }

    // --- MÉTODOS DE DIBUJO ---
    private void dibujarCarreteraAsfalto(Graphics2D g2, int ancho, int inicioY) {
        // Asfalto
        g2.setColor(new Color(50, 50, 50));
        g2.fillRect(0, inicioY, ancho, ALTO_CARRETERA);

        // Bordes Blancos
        g2.setColor(new Color(220, 220, 220));
        g2.fillRect(0, inicioY + 5, ancho, 5);
        g2.fillRect(0, inicioY + ALTO_CARRETERA - 10, ancho, 5);

        // Líneas Centrales
        int linea1Y = inicioY + (ALTO_CARRETERA / 3);
        int linea2Y = inicioY + (2 * ALTO_CARRETERA / 3);
        g2.setColor(Color.WHITE);
        for (int i = -150; i < ancho + 150; i += 150) {
            g2.fillRect(i + lineaX, linea1Y - 2, 60, 4);
            g2.fillRect(i + lineaX, linea2Y - 2, 60, 4);
        }
    }

    private void dibujarAuto(Graphics2D g2, int x, int y) {
        // Sombra
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(x + 5, y + 5, 70, 40, 15, 15);

        // Ruedas
        g2.setColor(new Color(20, 20, 20));
        if (!llantaSalida) {
            g2.fillRect(x + 10, y - 4, 18, 6); // Trasera Izq
        }
        g2.fillRect(x + 45, y - 4, 18, 6);
        g2.fillRect(x + 10, y + 38, 18, 6);
        g2.fillRect(x + 45, y + 38, 18, 6);

        // Carrocería
        g2.setColor(new Color(245, 245, 245)); // Blanco
        g2.fillRoundRect(x, y, 70, 40, 15, 15);

        // Ventanas
        g2.setColor(new Color(50, 50, 60)); // Gris oscuro azulado
        g2.fillRect(x + 45, y + 5, 15, 30);
        g2.fillRect(x + 10, y + 5, 25, 30);
    }

    private void dibujarLlantaRealista(Graphics2D g2) {
        AffineTransform old = g2.getTransform();
        g2.translate(llantaSueltaX, llantaSueltaY);
        g2.rotate(anguloLlanta);

        // Goma
        g2.setColor(new Color(20, 20, 20));
        g2.fillRoundRect(-10, -5, 20, 10, 5, 5);
        // Rin Plateado
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(-4, -5, 8, 10);
        // Detalle
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(0, -5, 0, 5);

        g2.setTransform(old);
    }

    private void dibujarEstrellitas(Graphics2D g2, int x, int y) {
        g2.setColor(Color.YELLOW);
        if (System.currentTimeMillis() % 500 < 250) {
            dibujarEstrella(g2, x + 80, y - 10);
        }
        if (System.currentTimeMillis() % 400 < 200) {
            dibujarEstrella(g2, x + 60, y + 50);
        }
        if (System.currentTimeMillis() % 600 < 300) {
            dibujarEstrella(g2, x - 10, y + 20);
        }
    }

    private void dibujarEstrella(Graphics2D g2, int cx, int cy) {
        g2.drawLine(cx - 5, cy, cx + 5, cy);
        g2.drawLine(cx, cy - 5, cx, cy + 5);
        g2.setColor(Color.WHITE);
        g2.fillRect(cx - 1, cy - 1, 2, 2);
        g2.setColor(Color.YELLOW);
    }

    private void dibujarHumoNegro(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(20, 20, 20, 180));
        g2.fillOval(x - 15 + (int) (Math.random() * 5), y + 8, 20, 20);
        g2.fillOval(x - 30 + (int) (Math.random() * 8), y + 2, 25, 25);
    }

    private void dibujarGarageGigante(Graphics2D g2, int x, int y, int altoTotal) {
        // Estructura
        g2.setColor(new Color(80, 80, 90));
        g2.fillRect(x, y, 200, altoTotal);
        // Entrada
        g2.setColor(new Color(20, 20, 20));
        g2.fillRect(x, y + 20, 180, altoTotal - 40);
        // Techo
        g2.setColor(new Color(180, 40, 40));
        g2.fillRect(x - 20, y - 30, 240, 30);
        // Letrero
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial Black", Font.BOLD, 24));
        g2.drawString("TALLER MECÁNICO", x + 10, y - 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- MOTOR DE ANIMACIÓN ---

        // Mover carretera
        lineaX -= VELOCIDAD;
        if (lineaX <= -150) {
            lineaX = 0;
        }

        // Mover garage
        garageX -= VELOCIDAD;

        // ETAPA 1: Humo
        if (garageX < 1000 && garageX > 400) {
            conHumoNegro = true;
        }

        // ETAPA 2: Llanta y Derrape
        if (garageX <= 400 && garageX > 350 && !llantaSalida) {
            llantaSalida = true;
            modoDerrape = true;
            llantaSueltaX = AUTO_POS_X_FIJA + 10;
            llantaSueltaY = (getHeight() / 2) - 24;
        }

        // Derrape
        if (modoDerrape && anguloAuto < 25) {
            anguloAuto += 2;
        }

        // ETAPA 3: Entrada (Ocultar)
        if (garageX < AUTO_POS_X_FIJA + 50 && garageX > AUTO_POS_X_FIJA - 200) {
            autoVisible = false;
            conHumoNegro = false;
            modoDerrape = false;
            anguloAuto = 0;
        }

        // ETAPA 4: Salida (Brillo)
        if (garageX <= AUTO_POS_X_FIJA - 200) {
            autoVisible = true;
            llantaSalida = false;
            efectoBrillo = true;
        }

        // Apagar brillos
        if (garageX < -500) {
            efectoBrillo = false;
        }

        // ETAPA 5: Reinicio
        if (garageX < -800) {
            garageX = 2000;
            conHumoNegro = false;
            llantaSalida = false;
            autoVisible = true;
            efectoBrillo = false;
        }

        // Física Llanta
        if (llantaSalida) {
            llantaSueltaX -= (VELOCIDAD - 5);
            llantaSueltaY += 3;
            anguloLlanta -= 0.5;
        }

        // Vibración auto
        if (subiendo) {
            autoReboteY--;
            if (autoReboteY < -2) {
                subiendo = false;
            }
        } else {
            autoReboteY++;
            if (autoReboteY > 2) {
                subiendo = true;
            }
        }

        repaint();
    }
}
