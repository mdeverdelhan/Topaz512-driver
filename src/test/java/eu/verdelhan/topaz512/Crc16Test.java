package eu.verdelhan.topaz512;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Crc16Test {

    @Test
    void computeCrcA() {
        byte[] inBuffer = new byte[] { 0x12, 0x34 };
        byte[] expected = new byte[] { 0x26, (byte) 0xCF };
        byte[] crc = Crc16.computeCrcA(inBuffer);
        Assertions.assertArrayEquals(expected, crc);
    }

    @Test
    void computeCrcB() {
        byte[] inBuffer = new byte[] { 0x0A, 0x12, 0x34, 0x56 };
        byte[] expected = new byte[] { (byte) 0x2C, (byte) 0xF6 };
        byte[] crc = Crc16.computeCrcB(inBuffer);
        Assertions.assertArrayEquals(expected, crc);
    }

    @Test
    void computeCrcWithNullArguments() {
        NullPointerException npe = Assertions.assertThrows(NullPointerException.class, () -> Crc16.computeCrc(null, new byte[10]));
        Assertions.assertEquals("CRC type cannot be null", npe.getMessage());

        npe = Assertions.assertThrows(NullPointerException.class, () -> Crc16.computeCrc(Crc16.CrcType.CRC_A, null));
        Assertions.assertEquals("Byte array cannot be null", npe.getMessage());
    }
}
