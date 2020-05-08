package eu.verdelhan.topaz512;

/**
 * Driver for Innovision/Broadcom Topaz 512 NFC tags (BCM20203T512).
 */
public class Topaz512 {

    /** Commands */
    public static final byte REQA = 0x26; // Request Command, type A
    public static final byte WUPA = 0x52; // Wake-up, type A
    public static final byte RID = 0x78; // Read ID: Use to read the metal-mask ROM and UID0-3 from block 0
    public static final byte RALL = 0x00; // Read all (only blocks 0-E covered)
    public static final byte READ = 0x01; // Read (a single byte)
    public static final byte WRITE_E = 0x53; // Write-with-erase (a single byte)
    public static final byte WRITE_NE = 0x1A; // Write-no-erase (a single byte)
    public static final byte RSEG = 0x10; // Read segment
    public static final byte READ8 = 0x02; // Read (8 bytes)
    public static final byte WRITE_E8 = 0x54; // Write-with-erase (8 bytes)
    public static final byte WRITE_NE8 = 0x1B; // Write-no-erase (8 bytes)

    /** Responses */
    public static final byte[] ATQA = new byte[] { 0x0C, 0x00 };

    /** Tag UID */
    private byte[] uid;

    /** True to add the CRC-B postfix (to commands) when it is needed, false otherwise */
    private boolean addCrcPostfix;

    public Topaz512() {
        this(new byte[4]);
    }

    /**
     * @param uid the tag UID
     */
    public Topaz512(byte[] uid) {
        this(uid, true);
    }

    /**
     * @param addCrcPostfix true to add the CRC-B postfix when it is needed, false otherwise
     */
    public Topaz512(boolean addCrcPostfix) {
        this(new byte[4], addCrcPostfix);
    }

    /**
     * @param uid the tag UID (4 bytes)
     * @param addCrcPostfix true to add the CRC-B postfix when it is needed, false otherwise
     */
    public Topaz512(byte[] uid, boolean addCrcPostfix) {
        if (uid == null || uid.length != 4) {
            throw new IllegalArgumentException("Tag UID must be 4-bytes long");
        }
        this.uid = uid;
        this.addCrcPostfix = addCrcPostfix;
    }

    /**
     * @param uid the tag UID
     */
    public void setUid(byte[] uid) {
        this.uid = uid;
    }

    /**
     * @return the tag UID
     */
    public byte[] getUid() {
        return uid;
    }

    /**
     * @return the REQA (Request) command
     */
    public byte[] buildReqaCommand() {
        return new byte[] {REQA};
    }

    /**
     * @return the WUPA (Wake-up) command
     */
    public byte[] buildWupaCommand() {
        return new byte[] {WUPA};
    }

    /**
     * @return the RID (Read ID) command
     */
    public byte[] buildRidCommand() {
        byte[] rawCmd = new byte[7];
        rawCmd[0] = RID;
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @return the RALL (Read all) command (only blocks 0-E covered)
     */
    public byte[] buildRallCommand() {
        byte[] rawCmd = new byte[7];
        rawCmd[0] = RALL;
        System.arraycopy(uid, 0, rawCmd, 3, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add the ADD address byte
     * @return the READ (a single byte) command
     */
    public byte[] buildReadCommand(byte add) {
        byte[] rawCmd = new byte[7];
        rawCmd[0] = READ;
        rawCmd[1] = add;
        System.arraycopy(uid, 0, rawCmd, 3, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add the ADD address byte
     * @param data the byte to be written on the tag
     * @return the WRITE-E (Write-with-erase, a single byte) command
     */
    public byte[] buildWriteECommand(byte add, byte data) {
        byte[] rawCmd = new byte[7];
        rawCmd[0] = WRITE_E;
        rawCmd[1] = add;
        rawCmd[2] = data;
        System.arraycopy(uid, 0, rawCmd, 3, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add the ADD address byte
     * @param data the byte to be written on the tag
     * @return the WRITE-NE (Write-no-erase, a single byte) command
     */
    public byte[] buildWriteNECommand(byte add, byte data) {
        byte[] rawCmd = new byte[7];
        rawCmd[0] = WRITE_NE;
        rawCmd[1] = add;
        rawCmd[2] = data;
        System.arraycopy(uid, 0, rawCmd, 3, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param adds the ADDS segment address byte
     * @return the RSEG (Read segment) command
     */
    public byte[] buildRsegCommand(byte adds) {
        byte[] rawCmd = new byte[14];
        rawCmd[0] = RSEG;
        rawCmd[1] = adds;
        System.arraycopy(uid, 0, rawCmd, 10, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add8 the ADD8 block address byte
     * @return the READ8 (Read, 8 bytes) command
     */
    public byte[] buildRead8Command(byte add8) {
        byte[] rawCmd = new byte[14];
        rawCmd[0] = READ8;
        rawCmd[1] = add8;
        System.arraycopy(uid, 0, rawCmd, 10, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add8 the ADD8 block address byte
     * @param data8 the 8 bytes to be written on the tag
     * @return the WRITE-E8 (Write-with-erase, 8 bytes) command
     */
    public byte[] buildWriteE8Command(byte add8, byte[] data8) {
        checkData8(data8);
        byte[] rawCmd = new byte[14];
        rawCmd[0] = WRITE_E8;
        rawCmd[1] = add8;
        System.arraycopy(data8, 0, rawCmd, 2, data8.length);
        System.arraycopy(uid, 0, rawCmd, 10, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param add8 the ADD8 block address byte
     * @param data8 the 8 bytes to be written on the tag
     * @return the WRITE-NE8 (Write-no-erase, 8 bytes) command
     */
    public byte[] buildWriteNE8Command(byte add8, byte[] data8) {
        checkData8(data8);
        byte[] rawCmd = new byte[14];
        rawCmd[0] = WRITE_NE8;
        rawCmd[1] = add8;
        System.arraycopy(data8, 0, rawCmd, 2, data8.length);
        System.arraycopy(uid, 0, rawCmd, 10, uid.length);
        return addCrcPostfixIfNeeded(rawCmd);
    }

    /**
     * @param rawCmd a raw command
     * @return the command + its CRC-B postfix if it has been requested, the raw command itself otherwise
     */
    protected byte[] addCrcPostfixIfNeeded(byte[] rawCmd) {
        return addCrcPostfix ? addCrcBPostfix(rawCmd) : rawCmd;
    }

    /**
     * Check the validity of a data8 byte array.
     * @param data8 the data byte array to check
     */
    protected static void checkData8(byte[] data8) {
        if (data8 == null || data8.length != 8) {
            throw new IllegalArgumentException("Data array must be 8-bytes long");
        }
    }

    /**
     * Add a CRC-B postfix to a raw command.
     * @param rawCmd a raw command
     * @return the command + its CRC-B postfix (2 bytes)
     */
    protected static byte[] addCrcBPostfix(byte[] rawCmd) {
        byte[] crc = Crc16.computeCrc(Crc16.CrcType.CRC_B, rawCmd);
        byte[] cmd = new byte[rawCmd.length + crc.length];
        System.arraycopy(rawCmd, 0, cmd, 0, rawCmd.length);
        System.arraycopy(crc, 0, cmd, rawCmd.length, crc.length);
        return cmd;
    }

    /**
     * @param blockIdx the block index (from 0 to F)
     * @param byteIdx the byte index (from 0 to 7)
     * @return an ADD address byte (for READ, WRITE-E and WRITE-NE)
     */
    public static byte buildAdd(char blockIdx, byte byteIdx) {
        return (byte) ((Character.digit(blockIdx, 16) << 3) + byteIdx);
    }

    /**
     * @param segmentIdx the segment index (from 0 to 3)
     * @return an ADDS segment index byte (for RSEG)
     */
    public static byte buildAdds(byte segmentIdx) {
        if (segmentIdx < 0 || segmentIdx > 3) {
            throw new IllegalArgumentException("Segment index must be between 0 and 3");
        }
        return (byte) (segmentIdx << 5);
    }

    /**
     * @param globalBlockIdx the global block index (from 0x00 to 0x3F)
     * @return an ADD8 block address byte (for READ8, WRITE-E8 and WRITE-NE8)
     */
    public static byte buildAdd8(byte globalBlockIdx) {
        if (globalBlockIdx < 0x00 || globalBlockIdx > 0x3F) {
            throw new IllegalArgumentException("Global block index must be between 0x00 and 0x3F");
        }
        return globalBlockIdx;
    }
}
