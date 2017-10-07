package com;

import java.io.*;

public class SavecObject implements Serializable {

    public static void writeToFile(Piece piece) throws IOException {


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Piece.bin"));
        objectOutputStream.writeObject(piece);    }

    public static void readFile(){{}};
}
