package es.ulpgc.digester.box;

import es.ulpgc.digester.MicrobiologyDigester;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        DigesterBox digesterBox = new DigesterBox(args);
        MicrobiologyDigester.digest(digesterBox, Paths.get(digesterBox.configuration().csv()).normalize().toAbsolutePath().toString());
    }
}
