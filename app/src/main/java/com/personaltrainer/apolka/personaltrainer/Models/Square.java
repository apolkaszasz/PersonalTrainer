package com.personaltrainer.apolka.personaltrainer.Models;

/**
 * Created by Api on 4/12/2018.
 */

public class Square {
    private double x_begin;
    private double y_begin;
    private double x_end;
    private double y_end;

    public Square(double x_begin,double y_begin, double x_end, double y_end){
        this.x_begin = x_begin;
        this.y_begin = y_begin;
        this.x_end = x_end;
        this.y_end = y_end;
    }

    public double getX_begin(){return this.x_begin;}
    public double getY_begin(){return this.y_begin;}
    public double getX_end(){return this.x_end;}
    public double getY_end(){return this.y_end;}

    public void setX_begin(double x_begin){this.x_begin = x_begin;}
    public void setY_begin(double y_begin){this.y_begin = y_begin;}
    public void setX_end(double x_end){this.x_end = x_end;}
    public void setY_end(double y_end){this.y_end = y_end;}
}
