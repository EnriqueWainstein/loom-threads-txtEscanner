

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

	public static void main(String[] args) throws Exception {
		
     Path root = Paths.get("./documents");
     
     
     List<Path> files; 
     
     
     try (Stream<Path> stream = Files.walk(root)) {
    	 
    	 files = stream
    			 .filter(Files::isRegularFile)
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
		 }
		 
	 }	

	}
	
	private static Reporte process(Path path) throws Exception{
		
		 String content = Files.readString(path);

	        Thread.sleep(50);

	        long words = Arrays.stream(content.split("\\s+"))
	                .count();

	        return new Reporte(
	                path,
	                words
	        );
		
	}

}
