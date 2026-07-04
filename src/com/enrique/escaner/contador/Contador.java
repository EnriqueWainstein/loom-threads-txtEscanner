package com.enrique.escaner.contador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import com.enrique.escaner.report.Reporte;

public class Contador {

	
	public Reporte contarPalabras(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            long words = lines
                    .filter(line -> !line.isBlank())
                    .mapToLong(line -> Arrays.stream(line.split("\\s+")).count())
                    .sum();
            return new Reporte(path, words);
        } catch (IOException e) {
            System.err.println("Error leyendo: " + path + " - " + e.getMessage());
            return new Reporte(path, 0);
        }
    }
}
