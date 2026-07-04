package com.enrique.escaner.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import com.enrique.escaner.contador.Contador;
import com.enrique.escaner.report.Reporte;

public class FileScanner {

	private final Contador c;
	private final Path p;
	
	public FileScanner(Contador c, Path p) {
		this.c = c;
		this.p = p;
	}
	
	
	public void escanear() {
		try {
			List<Path> files =escaneo(p);
			procesamiento(files);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	private List<Path> escaneo(Path p) throws IOException{
		List<Path> files = new ArrayList<>(); 
	     
	     
	     try (Stream<Path> stream = Files.walk(p)) {
	    	 
	    	 files = stream
	    			 .filter(Files::isRegularFile)
	    			 .filter(path -> path.toString().endsWith(".txt"))
	    			 .toList();
	    	 
	     }catch(IOException e){
	    	 
	    	 System.out.println(e.getMessage());
	     }
	     
	     return files;
		
	}
	
	private void  procesamiento(List<Path> l) {
		try (ExecutorService executor = 
				 Executors.newVirtualThreadPerTaskExecutor()){
			 List<Future<Reporte>> futures = new ArrayList<>(); 
			 
			 for (Path file : l) {
				 futures.add(executor.submit(() -> c.contarPalabras(file)));
			 } 
			 
			 for(Future<Reporte> future : futures) {
				
				 System.out.println(future.get());
				 
				 
			 }
			
			 System.out.println("reportes ");
			 
		 }	catch(Exception e) {
			 System.out.println( e.getMessage());
		 }
	}
	

}
