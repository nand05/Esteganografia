package vistasEsteganografia;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Esteganografia.Estega;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class vistaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField path1;
	private JTextField path2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vistaPrincipal frame = new vistaPrincipal();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public vistaPrincipal() {
		setBackground(new Color(192, 192, 192));
		setResizable(false);
		setTitle("Esteganografia");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 780, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProyectoTsisEsteganografia = new JLabel("Proyecto TSIS Esteganografia");
		lblProyectoTsisEsteganografia.setHorizontalAlignment(SwingConstants.CENTER);
		lblProyectoTsisEsteganografia.setFont(new Font("Baskerville Old Face", Font.BOLD | Font.ITALIC, 20));
		lblProyectoTsisEsteganografia.setBounds(107, 37, 531, 32);
		contentPane.add(lblProyectoTsisEsteganografia);
		
		JLabel lblImagenOriginal = new JLabel("Imagen Original");
		lblImagenOriginal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblImagenOriginal.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagenOriginal.setBounds(53, 118, 153, 25);
		contentPane.add(lblImagenOriginal);
		
		path1 = new JTextField();
		path1.setEnabled(false);
		path1.setBounds(216, 121, 335, 20);
		contentPane.add(path1);
		path1.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		JButton btnBuscar_1 = new JButton("Buscar");
		JButton btnOcultar = new JButton("Ocultar");
		
		
		/* Propiedades y listener del boton buscar */
		
		btnBuscar_1.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser explorador = new JFileChooser();
				explorador.setDialogTitle("Abrir documento...");
				// Muestro un dialogo sin pasarle parent con el boton de abrir
				int seleccion = explorador.showDialog(null, "Aceptar");

				// analizamos la respuesta
				switch (seleccion) {
				case JFileChooser.APPROVE_OPTION:
					// seleccionó abrir
					path1.setText(explorador.getSelectedFile().getAbsolutePath());
					if(path1.getText().endsWith(".bmp")) {
						btnBuscar_1.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null,
								"Error el archivo que quiere abrir en "+lblImagenOriginal.getText()+" no es correcto \n"
								+ "La primer direccion solo puede contener archivos con extencion bmp \n");
						path1.setText("");
					}
					
					break;
				case JFileChooser.CANCEL_OPTION:
					// dio click en cancelar o cerro la ventana
					break;

				case JFileChooser.ERROR_OPTION:
					// llega aqui si sucede un error
					break;
				}
			}
		});
		btnBuscar.setBounds(561, 120, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblImagenOTexto = new JLabel("Imagen o Texto a ocultar");
		lblImagenOTexto.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagenOTexto.setFont(new Font("Arial", Font.PLAIN, 14));
		lblImagenOTexto.setBounds(53, 283, 174, 43);
		contentPane.add(lblImagenOTexto);
		
		path2 = new JTextField();
		path2.setEnabled(false);
		path2.setBounds(237, 295, 335, 20);
		contentPane.add(path2);
		path2.setColumns(10);
		
		/* Propiedades y listener del boton Buscar_1 */
		
		btnBuscar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser explorador = new JFileChooser();
				explorador.setDialogTitle("Abrir documento...");
				// Muestro un dialogo sin pasarle parent con el boton de abrir
				int seleccion = explorador.showDialog(null, "Aceptar");

				// analizamos la respuesta
				switch (seleccion) {
				case JFileChooser.APPROVE_OPTION:
					// seleccionó abrir
					path2.setText(explorador.getSelectedFile().getAbsolutePath());
					if(path2.getText().endsWith(".bmp") || path2.getText().endsWith(".txt")) {
						btnOcultar.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null,
								"Error el archivo que quiere abrir "+lblImagenOTexto.getText()+" no es correcto \n"
								+ "La segunda direccion solo puede contener archivo con extencion bmp o txt");
						path2.setText("");
					}
					break;
				case JFileChooser.CANCEL_OPTION:
					// dio click en cancelar o cerro la ventana
					break;

				case JFileChooser.ERROR_OPTION:
					// llega aqui si sucede un error
					break;
				}
			}
		});
		btnBuscar_1.setBounds(582, 294, 89, 23);
		contentPane.add(btnBuscar_1);
		
		/* Propiedades y listener del boton Ocultar */
		
		btnOcultar.setEnabled(false);
		btnOcultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Aqui se va a llamar a AES para cifrar la imagen o texto
				//Una vez cifrado se llamra a otro metodo el cual va ocultar la imgen o texto cifrado
				Estega estega = new Estega(path1.getText(),path2.getText());
				try {
					estega.CifradoAES();
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				path1.setText("");
				path2.setText("");
				btnBuscar_1.setEnabled(false);
				btnOcultar.setEnabled(false);
			}
		});
		btnOcultar.setFont(new Font("Arial", Font.PLAIN, 20));
		btnOcultar.setBounds(278, 396, 192, 43);
		contentPane.add(btnOcultar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(329, 470, 89, 23);
		contentPane.add(btnSalir);
	}
}
