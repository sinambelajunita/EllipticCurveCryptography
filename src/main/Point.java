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
    private boolean infinite;
    public Point(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
        this.infinite = false;
    }
    public Point(BigInteger x, BigInteger y, boolean infinite){
        this.x = x;
        this.y = y;
        this.infinite = infinite;
    }
    public Point(Point p){
        this.x = p.getX();
        this.y = p.getY();
        this.infinite = p.infinite;
    }
    public BigInteger getX(){
        return this.x;
    }
    public BigInteger getY(){
        return this.y;
    }
    public boolean isInfinite(){
        return this.infinite;
    }
    public boolean equals(Point p){
        return p.x.equals(this.x) && p.y.equals(this.y);
    }
    @Override
    public String toString(){
        return "(" + this.x +", "+ this.y + ")";
    }
}
