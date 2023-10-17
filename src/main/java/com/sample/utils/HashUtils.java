/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public class HashUtils {
    private HashUtils() {}

    /**
     * Random alphanumric string with 32 character
     *
     * @return the string
     */
    public static String randomAlphanumric() {
        return randomAlphanumric(32);
    }

    /**
     * Random alphanumric string
     *
     * @param targetStringLength the random string length
     * @return the string
     */
    public static String randomAlphanumric(Integer targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString =
                random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(
                                StringBuilder::new,
                                StringBuilder::appendCodePoint,
                                StringBuilder::append)
                        .toString();

        return generatedString + new Date().getTime();
    }


    /**
     * Generate MD5 from a string.
     *
     * @param str The string you would like to hash
     * @return generated MD5 hash or if there is any problem return output
     */
    public static String generateMD5(String str)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Throwable e) {
            return str;
        }
    }

    /**
     * Generate short MD5 Hash random characters, based on length given
     *
     * @param str    The string you would like to hash
     * @param length length of output
     * @return Short random MD5 hash
     */
    public static String generateShortMD5(String str,int length)
    {
        String md5 = generateMD5(str);
        return Arrays.stream(md5.split(""))
                .limit(length)
                .collect(Collectors.joining());
    }
}
