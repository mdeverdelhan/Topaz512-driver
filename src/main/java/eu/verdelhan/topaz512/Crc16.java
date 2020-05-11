package eu.verdelhan.topaz512;

import java.util.Objects;

/**
 * CRC-16 reference (ISO/IEC 14443-3)
 * Crc-16 G(x) = x^16 + x^12 + x^5 + 1
 */
public class Crc16 {

    /**
     * CRC type
     */
    public enum CrcType {
        CRC_A((short) 0x6363), // ITU-V.41
        CRC_B((short) 0xFFFF); // a.k.a. CRC-16/X-25 - ISO/IEC 13239 (formerly ISO/IEC 3309)
        short seed;
        CrcType(short seed) {
            this.seed = seed;
        }
    }

    /**
     * @param data a byte array
     * @return the CRC-A, computed on the provided byte array
     */
    public static byte[] computeCrcA(byte[] data) {
        return computeCrc(CrcType.CRC_A, data);
    }

    /**
     * @param data a byte array
     * @return the CRC-B, computed on the provided byte array
     */
    public static byte[] computeCrcB(byte[] data) {
        return computeCrc(CrcType.CRC_B, data);
    }

    /**
     * Compute the CRC of type {@link CrcType} on the provided byte array.
     * @param crcType a CRC type
     * @param data a byte array
     * @return the CRC, computed on the provided byte array
     */
    public static byte[] computeCrc(CrcType crcType, byte[] data) {
        Objects.requireNonNull(crcType, "CRC type cannot be null");
        Objects.requireNonNull(data, "Byte array cannot be null");
        short wCrcU = crcType.seed;
        for (int i = 0; i < data.length; i++) {
            wCrcU = updateCrcU(data[i], wCrcU);
        }
        if (CrcType.CRC_B.equals(crcType)) {
            wCrcU = (short) ~wCrcU; // ISO/IEC 13239 (formerly ISO/IEC 3309)
        }
        return new byte[] { (byte) wCrcU, (byte) (wCrcU >> 8) };
    }

    private static short updateCrcU(byte charU, short lpwCrcU) {
        charU = (byte) (charU ^ lpwCrcU);
        charU = (byte) (charU ^ charU << 4);
        return (short) (Short.toUnsignedInt(lpwCrcU) >> 8 ^ Byte.toUnsignedInt(charU) << 8 ^ Byte.toUnsignedInt(charU) << 3 ^ Byte.toUnsignedInt(charU) >> 4);
    }

    private Crc16() {}
}
