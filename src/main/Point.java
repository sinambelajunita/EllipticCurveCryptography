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
    private BigInteger k = new BigInteger("20");
    
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
    
    public Point(byte data, EllipticCurve curve)
    {
        int dataCode = data & 0xFF;
        boolean found = false;
        BigInteger m = new BigInteger("" + dataCode);
        BigInteger y2, x, y;
        
        BigInteger index = new BigInteger("1");
        while(index.compareTo(k) == -1 && !found)
        {
            x = k.multiply(m).add(index);
            y2 = x.pow(3).add(curve.getA().multiply(x)).add(curve.getB()).mod(curve.getP());
            if(y2.modPow(curve.getP().subtract(new BigInteger("1")).divide(new BigInteger("2")), curve.getP()).compareTo(new BigInteger("1")) == 0)
            {
                y = y2.modPow(curve.getP().add(new BigInteger("1")).divide(new BigInteger("4")), curve.getP());
                found = true;
                this.x = x;
                this.y = y;
            }
            else
            {
                index = index.add(new BigInteger("1"));
            }
        }
    }
    
    public byte convertToByte()
    {
        return (this.x.subtract(new BigInteger("1"))).divide(this.k).byteValue();
    }
    public BigInteger getX(){
        return this.x;
    }
    public BigInteger getY(){
        return this.y;
    }
    public BigInteger getK(){
        return this.k;
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
