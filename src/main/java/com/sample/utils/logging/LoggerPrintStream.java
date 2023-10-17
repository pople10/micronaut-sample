/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.logging;

import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class LoggerPrintStream extends OutputStream {
    private final Logger logger;
    private String mem;

    @Override
    public void write(int b) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (b & 0xff);
        mem = mem + new String(bytes);

        if (mem.endsWith("\n")) {
            mem = mem.substring(0, mem.length() - 1);
            flush();
        }
    }

    public void flush() {
        logger.info(mem);
        mem = "";
    }
}
