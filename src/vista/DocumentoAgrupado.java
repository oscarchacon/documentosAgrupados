package vista;

import controlador.Controlador;

public class DocumentoAgrupado {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long totalTiempo;
        long tiempoInicio;
 
        // Inicializa con el tiempo actual
        tiempoInicio = System.currentTimeMillis();  
		
		Controlador controlador = new Controlador();
		String ruta = new String("/home/marcusfenix/Escritorio/corpus/"); // RUTA LINUX
		//String ruta = new String("C:/Users/oschacon/pan-plagiarism-corpus-2010/source-documents/"); //RUTA WINDOWS TRABAJO
		//String ruta = new String("/mnt/c/Users/oschacon/pan-plagiarism-corpus-2010/source-documents/"); //RUTA LINUX VIRTUAL TRABAJO
		//String ruta = new String("/Users/marcusfenix/Documents/Corpus/corpus pan10/source-documents/");  //RUTA MAC
		//String ruta = new String("/home/ochacon/pan-plagiarism-corpus-2010/source-documents/"); //RUTA SERVIDOR
		//String ruta = new String("C:/Users/Oscar/Desktop/pan-plagiarism-corpus-2010/source-documents/"); //RUTA WINDOWS SSD
		
		controlador.crearDocumento(ruta);
		
		totalTiempo = System.currentTimeMillis() - tiempoInicio;
		
		System.out.println("Tiempo demorado: " + (float) totalTiempo/1000 + " Segundos.");
	}

}
