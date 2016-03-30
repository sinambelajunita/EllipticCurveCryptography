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
//        generateGroup();
    }
    public BigInteger getP(){
        return this.p;
    }
    public BigInteger getA()
    {
        return this.a;
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

    // gradien m = a/b
    public Point add(Point p, Point q){
        BigInteger xR, yR;
        //if(!isInEllipticCurve(p) && !isInEllipticCurve(q)) return null;
        if(p.equals(q)){
            BigInteger TWO = new BigInteger("2");
            BigInteger THREE = new BigInteger("3");
            BigInteger lambda = (THREE.multiply(p.getX().multiply(p.getX())).add(a).divide(TWO.multiply(p.getY()))).mod(this.p);
            xR = ((lambda.multiply(lambda)).subtract(TWO.multiply(p.getX()))).mod(this.p);
            yR = ((lambda.multiply(p.getX().subtract(xR))).subtract(p.getY())).mod(this.p);
        }
//        else if (mirror(p,q)) {
//            return new Point(INFINITE_POINT);
//        }
        else{
            BigInteger lambda = ((p.getY().subtract(q.getY())).divide(p.getX().subtract(q.getX()))).mod(this.p);
            xR = ((lambda.multiply(lambda)).subtract(p.getX()).subtract(q.getX())).mod(this.p);
            yR = ((lambda.multiply(p.getX().subtract(xR))).subtract(p.getY())).mod(this.p);
        }
        return new Point(xR, yR);
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
//    private void generateGroup(){
//        BigInteger x = new BigInteger("0");
//        while(!x.equals(p)){
//            BigInteger squareYModP = ((x.multiply(x.multiply(x))).add(a.multiply(x)).add(b)).mod(p);
//            BigInteger squareY1 = squareYModP.modInverse(p);
//            BigInteger squareY2 = squareYModP.modInverse(p);
//            while(squareY2.equals(squareY1)){
//                squareY2 = squareYModP.modInverse(p);
//            }
//        }
//    }
}
