/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteArkanoid extends JFrame {

    private JTextField txtIP;
    private JTextField txtPuerto;
    private JButton btnConectar;
    private JLabel lblEstado;

    public ClienteArkanoid() {
        super("Arkanoid Multijugador - Cliente");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 230);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lbl1 = new JLabel("IP del servidor:");
        lbl1.setBounds(20, 20, 120, 25);
        panel.add(lbl1);

        txtIP = new JTextField("127.0.0.1");
        txtIP.setBounds(150, 20, 150, 25);
        panel.add(txtIP);

        JLabel lbl2 = new JLabel("Puerto:");
        lbl2.setBounds(20, 60, 120, 25);
        panel.add(lbl2);

        txtPuerto = new JTextField("5050");
        txtPuerto.setBounds(150, 60, 150, 25);
        panel.add(txtPuerto);

        btnConectar = new JButton("Conectar");
        btnConectar.setBounds(100, 100, 130, 30);
        panel.add(btnConectar);

        lblEstado = new JLabel("Esperando conexión...");
        lblEstado.setBounds(20, 140, 300, 25);
        lblEstado.setForeground(Color.BLUE);
        panel.add(lblEstado);

        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectarServidor();
            }
        });

        add(panel);
    }

    private void conectarServidor() {
        String ip = txtIP.getText().trim();
        int puerto;

        try {
            puerto = Integer.parseInt(txtPuerto.getText().trim());
        } catch (NumberFormatException ex) {
            lblEstado.setText("Puerto inválido");
            lblEstado.setForeground(Color.RED);
            return;
        }

        lblEstado.setText("Conectando...");
        lblEstado.setForeground(Color.BLUE);

        // Abrir la ventana del juego
        VistaJuegoCliente vista = new VistaJuegoCliente(ip, puerto);
        vista.setVisible(true);

        // Cerrar ventana de conexión
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteArkanoid().setVisible(true));
    }
}