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
public class EllipticCurve {
    private BigInteger a;
    private BigInteger b;
    private BigInteger p;
    
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p){
        this.a = a;
        this.b = b;
        this.p = p;
    }
    
    private boolean isPointInCurve(Point p){
        return  (p.getY().multiply(p.getY())).equals((p.getX().multiply(p.getX().multiply(p.getX()))).add(p.getX().multiply(a)).add(b));
    }
    
    // gradien m = a/b
    public Point add(Point p, Point q){
        BigInteger lambdaA = p.getY().add(q.getY().negate());
        BigInteger lambdaB = p.getX().add(q.getX().negate());
//        BigInteger betaA = ((lambdaA.multiply(q.getX())).negate()).add(lambdaB.multiply(q.getY()));
//        BigInteger betaB = lambdaB;
        BigInteger xR = (lambdaA.multiply(lambdaA)).add(p.getX().negate()).add(q.getX().negate());
        BigInteger yR = (lambdaA.multiply(p.getX().add(xR.negate()))).add(p.getY().negate());
        return new Point(xR, yR);
    }
    
    // assumption : k > 2
    public Point multiply(BigInteger k, Point p){
        BigInteger i = new BigInteger("1");
        Point result = new Point(p);
        while(!i.equals(k)){
            result = add(result,p);
            i.add(new BigInteger("1"));
        }
        return result;
    }
    
}
