/*
 * Copyright (c) 2016 "JackWhite20"
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

package de.jackwhite20.cobra.shared.http;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public class Body {

    private byte[] bytes;

    public Body(byte[] bytes) {

        this.bytes = bytes;
    }

    public Body(String body) {

        this.bytes = body.getBytes();
    }

    public byte[] bytes() {

        return bytes;
    }

    public String content() {

        return new String(bytes);
    }

    public static Builder form(String key, String value) {

        return new Builder(key, value);
    }

    public static class Builder {

        private StringBuilder formBody = new StringBuilder();

        public Builder(String key, String value) {

            formBody.append(key).append("=").append(value);
        }

        public Builder form(String key, String value) {

            formBody.append("&").append(key).append("=").append(value);

            return this;
        }

        public Body build() {

            return new Body(formBody.toString());
        }
    }
}
