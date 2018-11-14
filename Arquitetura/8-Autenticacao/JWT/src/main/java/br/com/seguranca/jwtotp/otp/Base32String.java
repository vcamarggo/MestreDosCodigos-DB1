/*
 * Copyright 2009 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.seguranca.jwtotp.otp;

/**
 * Encodes arbitrary byte arrays as case-insensitive base-32 strings.
 * <p>
 * The implementation is slightly different than in RFC 4648. During encoding,
 * padding is not added, and during decoding the last incomplete chunk is not
 * taken into account. The result is that multiple strings decode to the same
 * byte array, for example, string of sixteen 7s ("7...7") and seventeen 7s both
 * decode to the same byte array.
 *
 * @author sweis@google.com (Steve Weis)
 * @author Neal Gafter
 */
class Base32String {
    // singleton

    private static final Base32String INSTANCE =
            new Base32String(); // RFC 4648/3548

    private static Base32String getInstance() {
        return INSTANCE;
    }

    private final char[] DIGITS;
    private final int MASK;
    private final int SHIFT;

    private Base32String() {
        // 32 alpha-numeric characters.
        DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
        MASK = DIGITS.length - 1;
        SHIFT = Integer.numberOfTrailingZeros(DIGITS.length);
    }


    static String encode(byte[] data) {
        return getInstance().encodeInternal(data);
    }

    private String encodeInternal(byte[] data) {
        if (data.length == 0) {
            return "";
        }

        // SHIFT is the number of bits per output character, so the length of the
        // output is the length of the input multiplied by 8/SHIFT, rounded up.
        if (data.length >= (1 << 28)) {
            // The computation below will fail, so don't do it.
            throw new IllegalArgumentException();
        }

        int outputLength = (data.length * 8 + SHIFT - 1) / SHIFT;
        StringBuilder result = new StringBuilder(outputLength);

        int buffer = data[0];
        int next = 1;
        int bitsLeft = 8;
        while (bitsLeft > 0 || next < data.length) {
            if (bitsLeft < SHIFT) {
                if (next < data.length) {
                    buffer <<= 8;
                    buffer |= (data[next++] & 0xff);
                    bitsLeft += 8;
                } else {
                    int pad = SHIFT - bitsLeft;
                    buffer <<= pad;
                    bitsLeft += pad;
                }
            }
            int index = MASK & (buffer >> (bitsLeft - SHIFT));
            bitsLeft -= SHIFT;
            result.append(DIGITS[index]);
        }
        return result.toString();
    }

    @Override
    // enforce that this class is a singleton
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
