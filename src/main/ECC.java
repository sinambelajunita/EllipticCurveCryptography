/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author akhfa
 *
**/

import java.math.*;

public class ECC {
	
    private EllipticCurve curve;
    private Point basePoint;
    private Point publicKey;
    private BigInteger privateKey;
    
    public ECC(EllipticCurve c, BigInteger x, BigInteger y, BigInteger nA) {
            curve = c;
            basePoint = new Point(x, y);
            privateKey = nA;
            publicKey = c.multiply(privateKey, basePoint);
    }
    
    public Point getPublicKey()
    {
        return publicKey;
    }
    
    // Enkripsi.
    public Point[] encrypt(Point plain) {
        int bits = curve.getP().bitLength();

        // Enkripsi yang menghasilkan 2 pasang point, (k * base_point, plain + k*publickey)
        Point[] ans = new Point[2];
        ans[0] = this.curve.multiply(basePoint.getK(), this.basePoint);
        ans[1] = this.curve.add(plain, this.curve.multiply(plain.getK(), publicKey));
        return ans;
    }

    public Point decrypt(Point[] cipher) {
        Point sub = curve.multiply(privateKey,cipher[0]);
        return curve.subtract(cipher[1],sub);
    }

    public String toString() {

            return "Gen: "+basePoint+"\n"+
                       "pri: "+privateKey+"\n"+
                       "pub: "+publicKey;
    }
}
