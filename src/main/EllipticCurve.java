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
    private static Point INFINITE_POINT = new Point(null, null);
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
    private boolean isPointInCurve(Point p){
        return  (p.getY().multiply(p.getY()))
                .equals
                    ((p.getX().multiply(p.getX().multiply(p.getX())))
                            .add(p.getX().multiply(a))
                            .add(b));
    }
    
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
        if(p.equals(q)){
            BigInteger TWO = new BigInteger("2");
            BigInteger THREE = new BigInteger("3");
            BigInteger lambdaA = THREE.multiply(p.getX().multiply(p.getX())).add(a);
            BigInteger lambdaB = TWO.multiply(p.getY());
            xR = (lambdaA.multiply(lambdaA)).divide(lambdaB.multiply(lambdaB)).subtract(TWO.multiply(p.getX()));
            yR = (lambdaA.multiply(p.getX().subtract(xR))).divide(lambdaB).subtract(p.getY());
        }
        else{
            BigInteger lambdaA = p.getY().add(q.getY().negate());
            BigInteger lambdaB = p.getX().add(q.getX().negate());
            xR = ((lambdaA.divide(lambdaB)).multiply(lambdaA.divide(lambdaB))).subtract(p.getX()).subtract(q.getX());
            yR = ((lambdaA.divide(lambdaB)).multiply(p.getX().subtract(xR))).subtract(p.getY());
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
    // assumption : k > 2
    public Point multiply(BigInteger k, Point p){
        Point result = null;
        if(p.getY().equals(new BigInteger("0"))){
            BigInteger i = new BigInteger("1");
            result = new Point(p);
            while(!i.equals(k)){
                result = add(result,p);
                i.add(new BigInteger("1"));
            }
        }
        else {
            result = new Point(INFINITE_POINT);
        }
        return result;
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
