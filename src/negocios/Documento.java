package negocios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Documento {
	private String contenidoDocumento;
	
	/***VARIABLE DE TIPO LOCAL***/
	private final String delimitadores = "[0123456789%.,;:–•·○►+—*#&$€~|`ºª@{}=_\\-()«»<>?!¡¿´/\'\"\\[\\]]+";

	public String getContenidoDocumento() {
		return contenidoDocumento;
	}

	public void setContenidoDocumento(String contenidoDocumento) {
		this.contenidoDocumento = contenidoDocumento;
	}
	int n = 0;
	int x = 0;
	public void verListaCarpetas(String Ruta, File ArchivoSalida)
	{
		System.out.println(ArchivoSalida.getAbsolutePath());
		File directorio = new File(Ruta+"/Source-documents/");
		if(directorio.exists())
		{
			this.verArchivosListaCarpetasNIO(directorio, ArchivoSalida);
			//this.verArchivosListaCarpetas(carpeta, ArchivoSalida);
			//this.verArchivosListaCarpetasBinario(carpeta, ArchivoSalida);
		}
	}
	
	public void verArchivosListaCarpetas(File carpeta,File Archivo)
	{
		List<Documento> docs = new ArrayList<Documento>();
		if(carpeta.exists())
		{
			//String[] listaArchivos = carpeta.list();
			String[] listaArchivos = carpeta.list(new FilenameFilter(){
														public boolean accept(File carpeta, String nombre) {
															return nombre.endsWith(".txt");
														}
													});
			//for(int i = 0; i < listaArchivos.length; i++)
			for(String nombreArchivo : listaArchivos)
			{	
				FileReader fr = null;
				BufferedReader br = null;
				File archivo = new File(carpeta.getPath()+"/"+nombreArchivo);
				if(archivo.exists())
				{
					Documento docaux = new Documento();
					try {
						fr = new FileReader(archivo);
						br = new BufferedReader(fr);
						String textoDocumento = new String("");
						String Linea;
						while((Linea = br.readLine()) != null)
						{
							textoDocumento += Linea;
						}
						docaux.setContenidoDocumento(textoDocumento);
						docs.add(docaux);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{

						// En el finally cerramos el fichero, para asegurarnos
			            // que se cierra tanto si todo va bien como si salta 
			            // una excepcion.
			            try{
			            	if( null != fr ){
			            		fr.close();
			            	}
			            }catch (Exception e2){
			            	e2.printStackTrace();
			            }
				    }
					if(n == 0)
					{
						System.out.println(docs.get(0).getContenidoDocumento());
					}
				}
				n++;
			}
		}
	}
	
	public void verArchivosListaCarpetasBinario(File carpeta,File Archivo)
	{
		List<Documento> docs = new ArrayList<Documento>();
		if(carpeta.exists())
		{
			//String[] listaArchivos = carpeta.list();
			
			String[] listaArchivos = carpeta.list(new FilenameFilter(){
														public boolean accept(File carpeta, String nombre) {
															return nombre.endsWith(".txt");
														}
													});
			for(String nombreArchivo : listaArchivos)
			{	
				File archivo = new File(carpeta.getPath()+"/"+nombreArchivo);
				if(archivo.exists())
				{
					Documento docaux = new Documento();
					FileInputStream fis = null;
					try{
						fis = new FileInputStream(archivo);
						InputStreamReader isr = new InputStreamReader ( fis ) ;
			            BufferedReader buffreader = new BufferedReader ( isr ) ;
						String textoDocumento = new String("");
						String Linea;
						while((Linea = buffreader.readLine()) != null)
						{
							textoDocumento += Linea;
						}
						docaux.setContenidoDocumento(textoDocumento);
						docs.add(docaux);
						buffreader.close();
						isr.close() ;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{

						// En el finally cerramos el fichero, para asegurarnos
			            // que se cierra tanto si todo va bien como si salta 
			            // una excepcion.			         
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
					if(n == 0)
					{						
						System.out.println(docs.get(0).getContenidoDocumento());
					}
				}
				n++;
			}
		}
	}
	
	public void verArchivosListaCarpetasNIO(File carpeta,File Archivo)
	{
		List<Documento> docs = new ArrayList<Documento>();
		if(carpeta.exists() && Archivo.exists())
		{
			//String[] listaArchivos = carpeta.list();
			
			Path dir = FileSystems.getDefault().getPath(carpeta.getPath());
			DirectoryStream<Path> stream;
			try {
				stream = Files.newDirectoryStream(dir, "*.txt");
				//final String charEncoding = System.getProperty("file.encoding");
				String charEncoding = "UTF-8";
				
				for(Path nombreArchivo : stream)  
				{	
					String contenido = new String("");
					CharBuffer charBuffer = null;
					Documento docaux = new Documento();
					FileInputStream f = new FileInputStream(nombreArchivo.toString());
					//InputStreamReader r = new InputStreamReader(f);
					//String filecharset = r.getEncoding();
					FileChannel ch = f.getChannel();
					//MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());
					MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());
					if(mbb != null)
					{
						charBuffer = Charset.forName(charEncoding).decode(mbb);
						//charBuffer = Charset.forName(filecharset).decode(mbb);
						//contenido = this.quitaEspacios(charBuffer.toString().toLowerCase().replaceAll("[\n\r]"," ").replaceAll(delimitadores, " ").trim());
						//contenido = this.quitaEspacios(charBuffer.toString().toLowerCase().replaceAll("[\n\r]"," ").replaceAll("[^\\p{L}\\p{N} ]+", " ").trim());
						//contenido = charBuffer.toString().toLowerCase().replaceAll("[\n\r]"," ").replaceAll("[^\\p{L}0-9 ]+", " ").replaceAll("[^\\p{InBasic_Latin}0-9 ]+", " ").replaceAll("\\s+", " ").trim();
						contenido = charBuffer.toString().toLowerCase().replaceAll("[\n\r]"," ");
						//contenido = contenido.replaceAll("ñ", "n");
						//contenido = contenido.replaceAll("á", "a");
						//contenido = contenido.replaceAll("é", "e");
						//contenido = contenido.replaceAll("í", "i");
						//contenido = contenido.replaceAll("ó", "o");
						//contenido = contenido.replaceAll("ú", "u");
						//contenido = contenido.replaceAll("ü", "u");
						contenido = contenido.replaceAll("ç", "c");
						contenido = contenido.replaceAll("[^\\p{L}\\p{N} ]+", " ").trim();
						contenido = contenido.replaceAll("\\s+", " ").trim();
						//contenido = contenido.replaceAll("[\\W+|_+]", " ").replaceAll("\\s+", " ").trim();						
						//contenido = this.quitaEspacios(charBuffer.toString().toLowerCase().replaceAll("[\\W+|_+]", " ").trim());
						//contenido = contenido.replaceAll("[\\s+]", " ").trim();
						//contenido = this.quitaEspacios(contenido);
						docaux.setContenidoDocumento(nombreArchivo.getFileName()+";"+contenido);
						docs.add(docaux);
					}
					f.close();
					
					if(x == 0)
					{
						//System.out.println(nombreArchivo.getFileName());
						System.out.println("nombre archivo:"+nombreArchivo.toString());
					}
					if(n==0)
					{
						System.out.println(System.getProperty("file.encoding"));
						System.out.println(docs.get(0).getContenidoDocumento());
					}
					n++;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(docs.size() > 0)
			{		
				for(Documento d1 : docs)
				{
					BufferedWriter out = null;
					try {   
					    //out = new BufferedWriter(new FileWriter(Archivo, true));   
						out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Archivo, true), "UTF8"));   
						//out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Archivo, true)));   
					    out.write(d1.getContenidoDocumento());
					    out.newLine();
					    out.flush();					 
					    out.close();					 
					} catch (IOException e) {   
					    // error processing code   
					}
				}
			}			
		}
	}
	
	public String quitaEspacios(String texto) 
	{
        StringTokenizer tokens = new StringTokenizer(texto);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        //String linea = buff.toString().replaceAll("[\\s+|\\u00A0+|\\ufeff+]", " ").trim();
        return buff.toString().trim();
        //return linea;
    }
}
