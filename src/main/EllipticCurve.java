/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class EllipticCurve {
    private BigInteger a;
    private BigInteger b;
    private BigInteger p;
    private static Point INFINITE_POINT = new Point(BigInteger.ZERO, BigInteger.ZERO, true);
    private List<Point> groupPoint;
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p){
        this.a = a;
        this.b = b;
        this.p = p;
        groupPoint = new ArrayList<>();
    }
    public BigInteger getP(){
        return this.p;
    }
    public BigInteger getA()
    {
        return this.a;
    }
    public BigInteger getB()
    {
        return this.b;
    }
    
    // 
    public boolean mirror(Point p, Point q) {
        return isInEllipticCurve(p) 
                && isInEllipticCurve(q) 
                && p.getX().equals(q.getX()) 
                && p.getY().equals(this.p.subtract(q.getY()));
    }

    // Returns the negative of this point, which is its mirror.
    public Point negate(Point p) {
        BigInteger newY = this.p.subtract(p.getY());
        return new Point(p.getX(), newY);
    }
    
    public Point add(Point a, Point b) {
		
        if (a.equals(b)) {

            BigInteger three = new BigInteger("3");
            BigInteger two = new BigInteger("2");
            BigInteger temp = new BigInteger(a.getX().toString());

            BigInteger lambda = temp.modPow(two, this.p);
            lambda = three.multiply(lambda);
            lambda = lambda.add(this.a);
            BigInteger den = two.multiply(a.getY());
            lambda = lambda.multiply(den.modInverse(this.p));

            BigInteger newX = lambda.multiply(lambda).subtract(a.getX()).subtract(a.getX()).mod(this.p);
            BigInteger newY = (lambda.multiply(a.getX().subtract(newX))).subtract(a.getY()).mod(this.p);
            return new Point(newX, newY);

        }

        else if (this.mirror(a, b)) {
            return new Point(BigInteger.ZERO, BigInteger.ZERO);
        }

        else {
            BigInteger lambda = b.getY().subtract(a.getY());
            BigInteger den = b.getX().subtract(a.getX());
            lambda = lambda.multiply(den.modInverse(this.p));

            BigInteger newX = lambda.multiply(lambda).subtract(a.getX()).subtract(b.getX()).mod(this.p);
            BigInteger newY = (lambda.multiply(a.getX().subtract(newX))).subtract(a.getY()).mod(this.p);
            return new Point(newX, newY);
        }
    }
    
    public boolean equals(EllipticCurve other) {
        return p.equals(other.p) 
                && a.equals(other.a) 
                && b.equals(other.b);
    }
    
    public Point subtract(Point p, Point q) {
        q = negate(q);
        return this.add(p,q);
    }
    public Point doubling(Point p){
        Point result = null;
        if(p.getY().equals(new BigInteger("0"))){
            result = new Point(p);
            result = add(result,p);
        }
        else{
            result = new Point(INFINITE_POINT);
        }
        return result;
    }
    
    public Point multiply(BigInteger k, Point p){
        BigInteger two = new BigInteger("2");
        if (k.equals(BigInteger.ONE))
            return new Point(p);
        else if (k.equals(two))
            return this.add(p, p);
        else if(k.mod(two).equals(BigInteger.ZERO)){
            Point result = multiply(k.divide(two), p);
            return add(result,result);
        }
        else {
            k = k.subtract(BigInteger.ONE);
            return add(p, multiply(k, p));
        }
    }
    
    public boolean isInEllipticCurve(Point p){
        if(p.isInfinite()) return true;
        else{
            return ((p.getX().multiply(p.getX().multiply(p.getX())).add(a.multiply(p.getX())).add(b))
                    .mod(this.p))
                    .subtract((p.getY().multiply(p.getY())).mod(this.p)) == BigInteger.ZERO;
        }
    }
    
    public boolean isInverse(Point p, Point q){
        return false;
    }
}
