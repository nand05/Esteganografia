package Esteganografia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Estega {

	private String path1,path2;
	static FileReader f;
	
	public Estega(String path1, String path2) {
		
		this.path1 = path1;
		this.path2 = path2;
	}
	
	public void CifradoAES() {
		try {
			f = new FileReader(path2);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void RondaInicial() {
		
	}
	
	private void RondaEstandar() {
		
	}
	
	private void RondaFinal() {
		
	}

}
