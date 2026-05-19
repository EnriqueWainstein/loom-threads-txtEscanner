package com.enrique.escaner.report;

import java.nio.file.Path;

public record Reporte(
    Path path,
    long words
) {
}