/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static void main(String[] args) throws IOException {
            
//        Test.testEncodeDecode();
//        Test.testEncryptDecrypt();
//        Test.testString();
        Test.encdec2();
    }
    
    /**
     * Test enkrip dekrip 1 point doank, lancar mau diganti2 apa juga inputnya
     */
    private static void testEncryptDecrypt()
    {
        EllipticCurve myCurve = new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        BigInteger x = new BigInteger("6");
        BigInteger y = new BigInteger("19");
        BigInteger nA = new BigInteger("10");
        
        ECC siA = new ECC(myCurve, x, y, nA);

        Point plain = new Point(new BigInteger("200000"), new BigInteger("150000"));
        System.out.println("encrypting " + plain);
        Point[] cipher = siA.encrypt(plain);
        
        Point recover = siA.decrypt(cipher);
        System.out.println("plain = " + recover);
    }
    
    /**
     * Test dengan input string.getBytes
     */
    private static void testString ()
    {
        EllipticCurve myCurve = new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        BigInteger x = new BigInteger("6");
        BigInteger y = new BigInteger("19");
        BigInteger nA = new BigInteger("10");
        
        ECC siA = new ECC(myCurve, x, y, nA);
        
        String apapun = "yasmcdfnrifcrf";
        byte [] data = apapun.getBytes();
        
        Point [] plain1 = new Point[data.length];
        Point [] chiper = new Point[data.length * 2];
        byte [] deplain = new byte[data.length];
        Point [] plain2 = new Point[data.length];
        for (int i = 0; i < data.length; i++)
        {
            plain1 [i] = new Point(data[i], myCurve);
            Point [] enkrip = siA.encrypt(plain1[i]);
            chiper [2 * i] = enkrip[0];
            chiper [2 * i + 1] = enkrip[1];
            plain2[i] = siA.decrypt(enkrip);
            System.err.println(plain2[i]);
            deplain[i] = plain2[i].convertToByte();
        }
        System.out.println(new String(deplain));
    }
    
    /**
     * Decrypt only, masih gagal
     */
    private static void decryptOnly()
    {
        EllipticCurve myCurve = new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        BigInteger x = new BigInteger("6");
        BigInteger y = new BigInteger("19");
        BigInteger nA = new BigInteger("10");
        
        ECC siA = new ECC(myCurve, x, y, nA);

        Point []chiper = new Point[2]; 
        chiper [0] = new Point(new BigInteger("36671358758"), new BigInteger("98764321260"));
        chiper [1] = new Point(new BigInteger("56333424803"), new BigInteger("1"));
        
        Point recover = siA.decrypt(chiper);
        System.out.println("plain = " + recover);
    }
    
    /**
     * Test pake file, dicoba terakhir kalo yang lain udah lancar
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private static void encdec2 () throws FileNotFoundException, IOException
    {
        EllipticCurve curve = new EllipticCurve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        Point base = new Point(new BigInteger("127"), new BigInteger("113"));
        BigInteger privateKey = new BigInteger("123");
        
        ECC ecc = new ECC(curve, base.getX(), base.getY(), privateKey);
        
        File file = new File("./.gitignore");
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileData);
        in.close();
        byte[] encryptedFile = new byte[2*fileData.length];
        byte[] decodedFile = new byte[fileData.length];
        for(int i=0; i<fileData.length; i++) {

            Point plain = new Point(fileData[i], curve);

            Point[] enkrip = ecc.encrypt(plain);
            encryptedFile[i*2] = enkrip[0].convertToByte();
            encryptedFile[i*2+1] = enkrip[1].convertToByte();
            
            Point dekrip = ecc.decrypt(enkrip);
            decodedFile[i] = dekrip.convertToByte();

        }
        FileOutputStream fw = new FileOutputStream(new File("./decoded"));
        fw.write(decodedFile);
        fw.close();
        
        fw = new FileOutputStream(new File("./encrypted"));
        fw.write(encryptedFile);
        fw.close();
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
        System.err.println("asal = " +apapun );
        System.out.println("decode = " + new String(deplain));
    }
}
