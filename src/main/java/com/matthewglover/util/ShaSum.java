package com.matthewglover.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class ShaSum {
    public String generate(InputStream stream) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
            MessageDigest messageDigest = getMessageDigest(bufferedInputStream);
            Formatter formatter = getFormatter(messageDigest);
            return formatter.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private MessageDigest getMessageDigest(BufferedInputStream stream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        byte[] buffer = new byte[1024];
        int read;

        while ((read = stream.read(buffer)) != -1) {
            digest.update(buffer, 0, read);
        }

        return digest;
    }

    private Formatter getFormatter(MessageDigest messageDigest) {
        Formatter formatter = new Formatter();

        for (byte aByte :  messageDigest.digest()) {
            formatter.format("%02x", aByte);
        }

        return formatter;
    }
}
