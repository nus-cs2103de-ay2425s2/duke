package io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Class that handles all outputs to the console
 */
class Output {
    private final BufferedWriter BUFFERED_WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * Constructor
     */
    public Output() {
    }

    /**
     * Writes outputStrings to console separated by sep
     *
     * @param outputStrings List of Strings to output to the console
     * @param sep           Separator used between each String in the List
     * @param end           End character used after the last string is written
     * @throws IOException IO fails
     */
    public void printOutput(List<String> outputStrings, String sep, String end) throws IOException {
        for (int i = 0; i < outputStrings.size(); i++) {
            this.BUFFERED_WRITER.write(outputStrings.get(i));
            if (i != outputStrings.size() - 1) {
                this.BUFFERED_WRITER.write(sep);
            }
        }
        this.BUFFERED_WRITER.write(end);
        this.BUFFERED_WRITER.flush();
    }
}
