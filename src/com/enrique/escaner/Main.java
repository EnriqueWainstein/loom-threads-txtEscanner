package com.enrique.escaner;


import java.nio.file.Path;
import java.nio.file.Paths;
import com.enrique.escaner.contador.Contador;
import com.enrique.escaner.scanner.FileScanner;

public class Main {
	

	public static void main(String[] args) throws Exception {
		
		
		
		Path root = Paths.get(args[0]);
		Contador c = new Contador();
        FileScanner f = new FileScanner(c, root);
     
       f.escanear(); 
     
	 
	 
	
	
	}

}
