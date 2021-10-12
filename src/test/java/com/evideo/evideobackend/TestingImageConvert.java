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
        Rectangle rectangle = new Rectangle();
        rectangle.moveTo(5,8);
    }
}

abstract class GraphicObject {
    int x, y;
    void moveTo(int newX, int newY) {
    }
    abstract void draw();
    abstract void resize();
}

class A extends GraphicObject {

    @Override
    void draw() {

    }

    @Override
    void resize() {

    }
}
class Circle extends GraphicObject {
    @Override
    void draw() {

    }

    @Override
    void resize() {

    }
}

class Rectangle extends GraphicObject {

    @Override
    void draw() {

    }

    @Override
    void resize() {

    }
}

