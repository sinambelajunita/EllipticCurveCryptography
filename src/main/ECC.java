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
import java.util.ArrayList;

public class ECC {
	
    private EllipticCurve curve;
    private Point generator;
    private Point publicKey;
    private BigInteger privateKey;

    public ECC(EllipticCurve c, BigInteger x, BigInteger y, BigInteger nA) {
            curve = c;
            generator = new Point(x, y);
            privateKey = nA;
            publicKey = c.multiply(privateKey, generator);
            System.out.println("Public key: " + publicKey.toString());
    }

    // Encryption.
    public Point[] encrypt(Point plain) {
        int bits = curve.getP().bitLength();
        BigInteger k = new BigInteger("1800001113776135289708488930858597154146763766152901314982");//bits, new Random());

        Point[] ans = new Point[2];
        ans[0] = curve.multiply(k,generator);
        ans[1] = curve.add(curve.multiply(k, publicKey),plain);
        return ans;
    }

    public Point decrypt(Point[] cipher) {

            Point sub = curve.multiply(privateKey,cipher[0]);

            return curve.subtract(cipher[1],sub);
    }

    public String toString() {

            return "Gen: "+generator+"\n"+
                       "pri: "+privateKey+"\n"+
                       "pub: "+publicKey;
    }
}
