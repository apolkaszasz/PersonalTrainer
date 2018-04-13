package com.personaltrainer.apolka.personaltrainer.Models;

/**
 * Created by Api on 4/12/2018.
 */

public class Square {
    private float x_begin;
    private float y_begin;
    private float x_end;
    private float y_end;

    public Square(float x_begin,float y_begin, float x_end, float y_end){
        this.x_begin = x_begin;
        this.y_begin = y_begin;
        this.x_end = x_end;
        this.y_end = y_end;
    }

    public float getX_begin(){return this.x_begin;}
    public float getY_begin(){return this.y_begin;}
    public float getX_end(){return this.x_end;}
    public float getY_end(){return this.y_end;}

    public void setX_begin(float x_begin){this.x_begin = x_begin;}
    public void setY_begin(float y_begin){this.y_begin = y_begin;}
    public void setX_end(float x_end){this.x_end = x_end;}
    public void setY_end(float y_end){this.y_end = y_end;}
}
