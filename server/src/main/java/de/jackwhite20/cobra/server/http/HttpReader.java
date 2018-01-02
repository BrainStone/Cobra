/*
 * Copyright (c) 2018 "JackWhite20"
 *
 * This file is part of Cobra.
 *
 * Cobra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jackwhite20.cobra.server.http;

import com.google.common.base.Preconditions;
import sun.nio.cs.StreamDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * Created by JackWhite20 on 25.11.2017.
 */
public class HttpReader extends Reader {

    private final StreamDecoder sd;

    private ByteBuffer byteBuffer;

    HttpReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
        super(in);

        this.sd = StreamDecoder.forInputStreamReader(in, this, Preconditions.checkNotNull(charsetName, "charsetName"));

        try {
            Field bb = sd.getClass().getDeclaredField("bb");
            bb.setAccessible(true);
            Object o = bb.get(sd);

            this.byteBuffer = (ByteBuffer) o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int read() throws IOException {
        return sd.read();
    }

    public int read(char cbuf[], int offset, int length) throws IOException {
        return sd.read(cbuf, offset, length);
    }

    public boolean ready() throws IOException {
        return sd.ready();
    }

    public void close() throws IOException {
        sd.close();
    }

    ByteBuffer buffer() {
        return byteBuffer;
    }
}
