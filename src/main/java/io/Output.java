package io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Class that handles all outputs to the console
 */
class Output {
    private BufferedWriter bufferedWriter;

    /**
     * Constructor
     */
    public Output() {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    /**
     * Writes outputStrings to console separated by sep
     * @param outputStrings List of Strings to output to the console
     * @param sep Separator used between each String in the List
     * @throws IOException IO fails
     */
    public void printOutput(List<String> outputStrings, String sep) throws IOException {
        for (String s: outputStrings) {
            this.bufferedWriter.write(s);
            this.bufferedWriter.write(sep);
        }
        this.bufferedWriter.flush();
    }
}
