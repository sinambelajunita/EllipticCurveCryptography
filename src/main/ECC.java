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
import java.util.*;

public class ECC {
	
	// Parts of one ECC system.
	private EllipticCurve curve;
	private Point generator;
	private Point publicKey;
	private BigInteger privateKey;
	
	// We need a curve, a generator point (x,y) and a private key, nA, that will
	// be used to generate the public key.
	public ECC(EllipticCurve c, BigInteger x, BigInteger y, BigInteger nA) {
		
		curve = c;
		generator = new Point(x, y);
		privateKey = nA;
		publicKey = c.multiply(privateKey, generator);
	}
	
        //Encode pesan
//        public ArrayList<Point> getEncodedMsg(byte[] toBeEncoded) {
//            ArrayList<Point> arrPoint = new ArrayList<>();
//            byte b;
//            Point temp;
//            int len = toBeEncoded.length;
//            System.out.println("EncodedMsg");
//            for (int i=0; i < len; i++){
//                b = toBeEncoded[i];
//                temp = encodeChar((long)b,kForKoblitz); //bisakah cast byte ke long?
//                if (temp != Point.O){
//                    arrPoint.add(temp);
//                }
//
//            }
//            return arrPoint;
//        }
    
//	// Encryption.
	public Point[] encrypt(Point plain) {
            int bits = curve.getP().bitLength();
            BigInteger k = new BigInteger(bits, new Random());
            System.out.println("Picked "+k+" as k for encrypting.");

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
	
	public static void main(String[] args) {
		
		
            // Just use the book's curve and test.
            EllipticCurve myCurve = new EllipticCurve(new BigInteger("23"), new BigInteger("1"), new BigInteger("1"));
            BigInteger x = new BigInteger("6");
            BigInteger y = new BigInteger("19");
            BigInteger nA = new BigInteger("10");
            ECC Alice = new ECC(myCurve, x, y, nA);

            // I have hard-coded my plaintext point.
            Point plain = new Point(new BigInteger("3"), new BigInteger("13"));
            System.out.println("encrypting "+plain);

            // Encrypt and print.
            Point[] cipher = Alice.encrypt(plain);
            System.out.println("cipher first part "+cipher[0]);
            System.out.println("cipher second part "+cipher[1]);

            // Decrypt and verify.
            Point recover = Alice.decrypt(cipher);
            System.out.println("recovered "+recover);

	}
}
