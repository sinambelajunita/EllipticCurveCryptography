/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.math.BigInteger;

/**
 *
 * @author user
 */
public class Point {
    private BigInteger x;
    private BigInteger y;
    public Point(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
    }
    public Point(Point p){
        this.x = p.getX();
        this.y = p.getY();
    }
    public BigInteger getX(){
        return this.x;
    }
    public BigInteger getY(){
        return this.y;
    }
    public boolean isInfinite(){
        return null == x || y == null;
    }
    public boolean equals(Point p){
        return p.x.equals(this.x) && p.y.equals(this.y);
    }
    public boolean isInverse(Point p){
        return false;
    }
}
