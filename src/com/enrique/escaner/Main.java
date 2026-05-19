package com.enrique.escaner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import com.enrique.escaner.report.Reporte;

public class Main {
	static int contador=0;

	public static void main(String[] args) throws Exception {
		
		long start = System.currentTimeMillis();
		
		Path root = Paths.get(System.getProperty("user.home"), "Documents");
     
     
     List<Path> files; 
     
     
     try (Stream<Path> stream = Files.walk(root)) {
    	 
    	 files = stream
    			 .filter(Files::isRegularFile)
    			 .filter(path -> path.toString().endsWith(".txt"))
    			 .toList();
    	 
     }
     
	 try (ExecutorService executor = 
			 Executors.newVirtualThreadPerTaskExecutor()){
		 List<Future<Reporte>> futures = new ArrayList<>(); 
		 
		 for (Path file : files) {
			 futures.add(executor.submit(() -> process(file)));
		 } 
		 
		 for(Future<Reporte> future : futures) {
			
			 System.out.println(future.get());
			 
			 contador=contador +1;
		 }
		
		 System.out.println("reportes "+ contador);
		 
	 }	catch(Exception e) {
		 System.out.println("error aca 35 a 45 "+ e.getMessage());
	 }
	 
	 long end = System.currentTimeMillis();
	 
	  System.out.println(
	            "Tiempo total: " + (end - start));

	}
	
	private static Reporte process(Path path) throws Exception{
		
		 String content = Files.readString(path);

	     Thread.sleep(50);
	        
            // TODO: corregir contador da mal.
	        long words = Arrays.stream(content.split("\\s+"))
	                .count();
	        System.out.println(
	        	    Thread.currentThread()
	        	);

	        return new Reporte(
	                path,
	                words
	        );
		
	}

}
