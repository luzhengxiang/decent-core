package com.dingsheng.decent.util.old;

public class Verify {
    public static void If(Boolean condiction, String message) throws CustomException {
        if (condiction) throw new CustomException(message);
    }

    public static void IfNull(Object obj, String message) throws CustomException {
        if ((obj instanceof String && obj.equals("")) || obj == null)
            throw new CustomException(message);
    }
}
