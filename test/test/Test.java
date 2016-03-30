/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.math.BigInteger;
import java.util.ArrayList;
import main.ECC;
import main.EllipticCurve;
import main.Point;

/**
 *
 * @author akhfa
 */
public class Test {
    public static void main(String[] args) {
            
//        Test.testEncodeDecode();
        Test.testEncryptDecrypt();
    }
    
    private static void testEncryptDecrypt()
    {
        EllipticCurve myCurve = new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        BigInteger x = new BigInteger("6");
        BigInteger y = new BigInteger("19");
        BigInteger nA = new BigInteger("10");
        
        ECC siA = new ECC(myCurve, x, y, nA);

        Point plain = new Point(new BigInteger("20"), new BigInteger("15"));
        System.out.println("encrypting " + plain);

        Point[] cipher = siA.encrypt(plain);
        
        Point recover = siA.decrypt(cipher);
        System.out.println("plain = " + recover);
    }
    
    private static void testEncodeDecode()
    {
        String apapun = "yasmcdfnrifcrf";
        byte [] data = apapun.getBytes();
        ArrayList<Point> plain = new ArrayList<>();
        Point [] plain1 = new Point[data.length];
        byte [] deplain = new byte[data.length];
        for (int i = 0; i < data.length; i++)
        {
            plain1 [i] = (new Point(data[i], new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"))));
            deplain[i] = plain1[i].convertToByte();
        }
        System.out.println(new String(deplain));
    }
}
