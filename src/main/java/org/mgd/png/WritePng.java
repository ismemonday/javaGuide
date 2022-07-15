package org.mgd.png;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

/**
 * @author mgd [maoguidong@standard-robots.com]
 * @data 2020/7/15 下午4:25
 */
public class WritePng {
    public static void main(String[] args) throws IOException {
        writePng();
    }

    private static void writePng() throws IOException {
        File file = new File("a.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //写png头部
        fileOutputStream.write(new byte[]{(byte) 0x89,0x50,0x4e,0x47,0x0d,0x1a,0x0a});
        //png的IHAD数据块
        fileOutputStream.write(pngChunkData(new byte[]{0x49,0x48,0x44,0x52},new byte[]{00,00,00,02,00,00,00,02,0x08,02,00,00,00}));
        //png的IDAT数据部分
        byte[] rgbData=new byte[]{00, (byte) 0xff,00,00,00,00, (byte) 0xff,00,00,00, (byte) 0xff, (byte) 0xff,00,00};
        fileOutputStream.write(pngChunkData(new byte[]{0x49,0x44,0x41,0x54},deflateZip(rgbData)));
        //写png尾部
        fileOutputStream.write(pngChunkData(new byte[]{0x49,0x45,0x4e, (byte) 0x44},null));
        fileOutputStream.close();
        System.out.println("操作成功");
    }

    /**
     * deflateZip压缩
     * @param source
     * @return
     */
    private static byte[] deflateZip(byte[] source) {
        Deflater deflater = new Deflater();
        deflater.setInput(source);
        deflater.finish();
        byte[] tem=new byte[64];
        byte[] dataByte=new byte[0];
        int deflate=0;
        while (!deflater.finished()){
            deflate = deflater.deflate(tem);
            byte[] array = ByteBuffer.allocate(deflate).put(tem, 0, deflate).array();
            byte[] temByte=new byte[dataByte.length+array.length];
            System.arraycopy(dataByte,0 , temByte, 0,dataByte.length );
            System.arraycopy(array, 0, temByte, dataByte.length, array.length);
            dataByte=temByte;
        }
        deflater.end();
        return dataByte;
    }

    /**
     * 数据块数据
     * @return
     */
    private static byte[] pngChunkData(byte[] chunkType,byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int dataLength=0;
        byte[] crcCheckSource= Arrays.copyOf(chunkType,chunkType.length);
        if(data!=null){
            dataLength=data.length;
            baos.write(crcCheckSource);
            baos.write(data);
            crcCheckSource=baos.toByteArray();
            baos.reset();
        }
        byte[] crcCheck = crc32CheckValue(crcCheckSource);
        baos.write(ByteBuffer.allocate(4).putInt(dataLength).array());
        baos.write(chunkType);
        if(data!=null){
            baos.write(data);
        }
       baos.write(crcCheck);
        return baos.toByteArray();
    }

    /**
     * 计算CRC32校验值
     * 参考：http://www.libpng.org/pub/png/spec/1.0/PNG-Structure.html#CRC-algorithm
     * crc32的二项目市为（w3c规定）：CRC32=X32+X26+X23+X22+X16+X12+X11+X10+X8+X7+X5+X4+X2+X1+X0
     * ng中的crc32原理： 与标准crc32有所不同，对数据处理前后各做了一些处理（初始余数寄存器为全一，最终余数取反（据w3c标准规定））
     * 原二项式：1 00000100 11000001 00011101 10110111
     * 去除最高位：100110000010001110110110111 =0X04C1BDD7L
     * 首位倒置：  111011011011100010000011001＝0xEDB88320L;
     * @return
     */
    private static byte[] crc32CheckValue(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        int value = (int) crc32.getValue();
        return ByteBuffer.allocate(4).putInt(value).array();
    }


}
