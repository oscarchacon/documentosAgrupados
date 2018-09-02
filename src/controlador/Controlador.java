package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.io.OutputStreamWriter;

import negocios.Documento;

public class Controlador {

	private Documento doc = new Documento();  
	
	public void crearDocumento(String ruta)
	{
		
		File rutaArchivoSalida = new File(ruta);
		if(rutaArchivoSalida.isDirectory())
		{
			String nombreArchivoSalida = rutaArchivoSalida.getPath()+"/DocumentosPS.txt"; //Documento PlagScan
			File archivoSalida = new File(nombreArchivoSalida);
			if(archivoSalida.exists())
			{
				archivoSalida.delete();
			}
			BufferedWriter bw;
			try {
				//bw = new BufferedWriter(new FileWriter(nombreArchivo));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nombreArchivoSalida), "UTF8"));
				//bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nombreArchivo)));
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.doc.verListaCarpetas(ruta, archivoSalida);
			//System.out.println(nombreArchivo);	
		}
		//System.out.println(rutaArchivo.getPath());
		//ArrayList<Documento> documentos = new ArrayList<Documento>();
	}
}
