package com.evideo.evideobackend;
import java.io.*;

import sun.misc.BASE64Decoder;

import javax.xml.bind.DatatypeConverter;

public class TestingImageConvert {

    public static void main(String[] args) {
        //            data:video/mp4;base64
//            data:image/png;base64
        String string = "data:video/mp4;base64";
        String arr[] = string.split("/");
        String arr1[] = arr[1].split(";");
        System.out.println(arr[0] + "...."+arr[1]);
        System.out.println(arr1[0] + "...."+arr1[1]);
    }
}
