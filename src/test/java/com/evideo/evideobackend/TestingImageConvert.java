package com.evideo.evideobackend;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.DatatypeConverter;

public class TestingImageConvert {

    public static void main(String[] args) {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordEncoder  = bCryptPasswordEncoder.encode("password");
            System.out.println(passwordEncoder);

//            String fileName = "G:\\my-vd\\uploads\\video\\My-t\\3e42f591-f709-43d2-b2d9-f6173c907473-FlutterUIAppIntroWithIndicatorsApplicationUIUXDesignday.mp4";
//            try {
//                Files.delete(Paths.get(fileName));
//            } catch (IOException e) {
//                e.printStackTrace();

//            String filePath = "G:\\\\my-vd\\\\uploads\\\\video\\\\Flutter\\\\e5af3dd3-8c-90b0-269bf9009acd-Flutter-Animations-RadialMenuwithAnimatedSwitcher.mp4";
//            File f = new File(fileName);
//            System.out.println(f.exists());
//            if (f.exists()) {
//                f.delete();
//            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        //            data:video/mp4;base64
//            data:image/png;base64
//        String string = "data:video/mp4;base64";
//        String arr[] = string.split("/");
//        String arr1[] = arr[1].split(";");
//        System.out.println(arr[0] + "...."+arr[1]);
//        System.out.println(arr1[0] + "...."+arr1[1]);
//        Rectangle rectangle = new Rectangle();
//        rectangle.moveTo(5,8);
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

