package Esteganografia;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

public class Estega {

	private String path1, path2, msj = "";
	
	private Key key,key2;
	private String instancia = "AES/ECB/PKCS5Padding";
	byte[] msjE;
	byte[] msjD;
	String r = "", g = "", b = "";
	byte [] rE;
	byte [] gE;
	byte [] bE;
	byte [] rD;
	byte [] gD;
	byte [] bD;
	
	public Estega(String path1, String path2) {

		this.path1 = path1;
		this.path2 = path2;
	}

	public void CifradoAES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
								    IllegalBlockSizeException, BadPaddingException {
		String metodo = "AES";
		int longitud = 128;
		if (path2.endsWith(".txt")) {
			cifraTexto(metodo, longitud);
		} else {
			cifraImagen(metodo, longitud);
		}
		
	}
	
	private void cifraTexto(String metodo, int longitud)throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
                                    IllegalBlockSizeException, BadPaddingException{
		
		crea_mensaje();
		genera_clave(metodo, longitud);
		encripta();
		System.out.println("mensaje "+msj);
		System.out.println("mensaje encriptado: ");
		for (byte b : msjE) {
	         System.out.print(Integer.toHexString(0xFF & b));
	      }
	    System.out.println();
	    System.out.println("mensaje desencriptado");
		desencripta();
	    System.out.println(new String(msjD));
	}
	
	private void cifraImagen(String metodo, int longitud) {
		llena_rgb();
		System.out.println("r: "+r);
		System.out.println("g: "+g);
		System.out.println("b: "+b);
	}

	private void crea_mensaje() {
		FileReader f;
		int c;
		try {
			f = new FileReader(path2);
			c = f.read();
			while (c != -1) {
				msj = msj + (char) c;
				c = f.read();
				
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void genera_clave(String metodo, int longitud) {
		// Generamos una clave de 128 bits adecuada para AES
		if (path2.endsWith(".txt")) {
			try {
				KeyGenerator keyGenerator = KeyGenerator.getInstance(metodo);
				keyGenerator.init(longitud);
				key = keyGenerator.generateKey();
				// key = new SecretKeySpec("una clave de 16 bytes".getBytes(), 0, 16, metodo);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				KeyGenerator keyGenerator = KeyGenerator.getInstance(metodo);
				keyGenerator.init(longitud);
				key2 = keyGenerator.generateKey();
				// key = new SecretKeySpec("una clave de 16 bytes".getBytes(), 0, 16, metodo);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void encripta() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
								   IllegalBlockSizeException, BadPaddingException {
		Cipher aes = Cipher.getInstance(instancia);
		aes.init(Cipher.ENCRYPT_MODE, key);
		msjE = aes.doFinal(msj.getBytes());
	}

	private void desencripta() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
									  IllegalBlockSizeException, BadPaddingException {
		Cipher aes = Cipher.getInstance(instancia);
		aes.init(Cipher.DECRYPT_MODE, key);
		msjD = aes.doFinal(msjE);
	}
	
	private void llena_rgb() {
		try {
			System.out.println(path2);
			BufferedImage img = ImageIO.read(new File(path2));
			for (int i = 0; i < img.getWidth(); i++) {
				for (int j = 0; j < img.getHeight(); j++) {
					int rgb = img.getRGB(i, j);
					Color color = new Color(rgb); 
					r = r + Integer.toString(color.getRed());
					g = g + color.getGreen();
					b = b + color.getBlue();
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
