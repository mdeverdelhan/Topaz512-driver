package eu.verdelhan.topaz512;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Topaz512Test {

    private byte[] uid = new byte[] {0x13, 0x37, (byte) 0xFF, 0x42};

    @Test
    void constuctorsAndSetters() {
        Topaz512 t512 = new Topaz512();
        Assertions.assertArrayEquals(new byte[4], t512.getUid());
        t512.setUid(uid);
        Assertions.assertArrayEquals(uid, t512.getUid());
    }

    @Test
    void reqaAndWupaCommands() {
        Topaz512 topaz512 = new Topaz512();
        Assertions.assertArrayEquals(new byte[] {Topaz512.REQA}, topaz512.buildReqaCommand());
        Assertions.assertArrayEquals(new byte[] {Topaz512.WUPA}, topaz512.buildWupaCommand());
    }

    @Test
    void buildRidCommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RID, 0, 0, 0, 0, 0, 0},
                new Topaz512(false).buildRidCommand()
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RID, 0, 0, 0, 0, 0, 0, (byte) 0xD0, 0x43},
                new Topaz512(true).buildRidCommand()
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RID, 0, 0, 0, 0, 0, 0, (byte) 0xD0, 0x43},
                new Topaz512(uid, true).buildRidCommand()
        );
    }

    @Test
    void buildRallCommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RALL, 0, 0, 0x13, 0x37, (byte) 0xFF, 0x42},
                new Topaz512(uid, false).buildRallCommand()
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RALL, 0, 0, 0, 0, 0, 0, 0x70, (byte) 0x8C},
                new Topaz512(true).buildRallCommand()
        );
    }

    @Test
    void buildReadCommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.READ, (byte) 0xFF, 0, 0, 0, 0, 0},
                new Topaz512(false).buildReadCommand((byte) 0xFF)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.READ, 0x42, 0, 0x13, 0x37, (byte) 0xFF, 0x42, 0x33, (byte) 0x6B},
                new Topaz512(uid, true).buildReadCommand((byte) 0x42)
        );
    }

    @Test
    void buildWriteECommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_E, (byte) 0x3F, 0x01, 0, 0, 0, 0},
                new Topaz512(false).buildWriteECommand((byte) 0x3F, (byte) 0x01)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_E, (byte) 0x3F, 0x01, 0x13, 0x37, (byte) 0xFF, 0x42, (byte) 0xDD, (byte) 0xD5},
                new Topaz512(uid, true).buildWriteECommand((byte) 0x3F, (byte) 0x01)
        );
    }

    @Test
    void buildWriteNECommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_NE, (byte) 0x3F, 0x01, 0, 0, 0, 0},
                new Topaz512(false).buildWriteNECommand((byte) 0x3F, (byte) 0x01)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_NE, (byte) 0x3F, 0x01, 0x13, 0x37, (byte) 0xFF, 0x42, (byte) 0xE2, 0x53},
                new Topaz512(uid, true).buildWriteNECommand((byte) 0x3F, (byte) 0x01)
        );
    }

    @Test
    void buildRsegCommand() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RSEG, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Topaz512(false).buildRsegCommand((byte) 0x00)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.RSEG, (byte) 0x02, 0, 0, 0, 0, 0, 0, 0, 0, 0x13, 0x37, (byte) 0xFF, 0x42, (byte) 0xF6, 0x06},
                new Topaz512(uid, true).buildRsegCommand((byte) 0x02)
        );
    }

    @Test
    void buildRead8Command() {
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.READ8, (byte) 0x07, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Topaz512(false).buildRead8Command((byte) 0x07)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.READ8, 0x33, 0, 0, 0, 0, 0, 0, 0, 0, 0x13, 0x37, (byte) 0xFF, 0x42, 0x28, (byte) 0xC7},
                new Topaz512(uid, true).buildRead8Command((byte) 0x33)
        );
    }

    @Test
    void buildWriteE8Command() {
        byte[] data8 = new byte[] {0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34};
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_E8, 0x20, 0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34, 0, 0, 0, 0},
                new Topaz512(false).buildWriteE8Command((byte) 0x20, data8)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_E8, 0x20, 0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34, 0x13, 0x37, (byte) 0xFF, 0x42, (byte) 0x4D, 0x68},
                new Topaz512(uid, true).buildWriteE8Command((byte) 0x20, data8)
        );
    }

    @Test
    void buildWriteNE8Command() {
        byte[] data8 = new byte[] {0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34};
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_NE8, 0x20, 0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34, 0, 0, 0, 0},
                new Topaz512(false).buildWriteNE8Command((byte) 0x20, data8)
        );
        Assertions.assertArrayEquals(
                new byte[] {Topaz512.WRITE_NE8, 0x20, 0x01, 0x02, 0x03, 0x05, 0x08, 0x13, 0x21, 0x34, 0x13, 0x37, (byte) 0xFF, 0x42, (byte) 0xC6, (byte) 0xB4},
                new Topaz512(uid, true).buildWriteNE8Command((byte) 0x20, data8)
        );
    }

    @Test
    void addCrcPostfixIfNeeded() {
        Assertions.assertArrayEquals(
                new byte[] { 0x01, 0x02 },
                new Topaz512(false).addCrcPostfixIfNeeded(new byte[] { 0x01, 0x02 })
        );
        Assertions.assertArrayEquals(
                new byte[] { 0x01, 0x02, (byte) 0x8D, 0x35 },
                new Topaz512(true).addCrcPostfixIfNeeded(new byte[] { 0x01, 0x02 })
        );
    }

    @Test
    void checkData8() {
        IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.checkData8(new byte[] { 0x01, 0x02 }));
        Assertions.assertEquals("Data array must be 8-bytes long", iae.getMessage());
        iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.checkData8(new byte[10]));
        Assertions.assertEquals("Data array must be 8-bytes long", iae.getMessage());
        iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.checkData8(null));
        Assertions.assertEquals("Data array must be 8-bytes long", iae.getMessage());

        Assertions.assertDoesNotThrow(() -> Topaz512.checkData8(new byte[8]));
    }

    @Test
    void addCrcBPostfix() {
        Assertions.assertArrayEquals(
                new byte[] { 0x01, 0x02, (byte) 0x8D, 0x35 },
                Topaz512.addCrcBPostfix(new byte[] { 0x01, 0x02 })
        );
    }

    @Test
    void buildAdd() {
        Assertions.assertEquals(0x0, Topaz512.buildAdd('0', (byte) 0));
        Assertions.assertEquals(0x08, Topaz512.buildAdd('1', (byte) 0));
        Assertions.assertEquals(0x53, Topaz512.buildAdd('A', (byte) 3));
        Assertions.assertEquals(0x7F, Topaz512.buildAdd('F', (byte) 7));
    }

    @Test
    void buildAdds() {
        IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.buildAdds((byte) -1));
        Assertions.assertEquals("Segment index must be between 0 and 3", iae.getMessage());
        iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.buildAdds((byte) 0x04));
        Assertions.assertEquals("Segment index must be between 0 and 3", iae.getMessage());

        Assertions.assertEquals(0x02, Topaz512.buildAdd8((byte) 2));
    }

    @Test
    void buildAdd8() {
        IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.buildAdd8((byte) -1));
        Assertions.assertEquals("Global block index must be between 0x00 and 0x3F", iae.getMessage());
        iae = Assertions.assertThrows(IllegalArgumentException.class, () -> Topaz512.buildAdd8((byte) 0x40));
        Assertions.assertEquals("Global block index must be between 0x00 and 0x3F", iae.getMessage());

        Assertions.assertEquals(0x20, Topaz512.buildAdd8((byte) 32));
    }
}
