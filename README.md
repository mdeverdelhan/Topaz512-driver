
# Topaz512-driver ![Maven build](https://github.com/mdeverdelhan/Topaz512-driver/workflows/Maven%20build/badge.svg?branch=master)

Here is a simple Java library/driver for building commands to communicate with Innovision/Broadcom Topaz512 NFC tags.

### Features

  * Fully compliant with Broadcom BCM20203T512 specification
  * All Topaz512 commands implemented
  * Integrity checking with optional CRC-16 computation
  * JDK 11 or later
  * MIT Licensed

### Build

```bash
~$ mvn clean package
```

### Usage

```java
Topaz512 t512 = new Topaz512();

// Getting tag UID
byte[] ridCommand = t512.buildRidCommand();
byte[] tagUid = ...; // Send the RID command and get the tag UID

// Reading 8 bytes at block 0x1A
byte add8 = Topaz512.buildAdd8((byte) 0x1A);
byte[] read8Command = t512.buildRead8Command(add8);
byte[] read8Response = ...; // Send the READ8 command and get the 8 bytes
```

## About the Topaz512 tags

The BCM20203T512 (Topaz512) tags are [NFC](http://en.wikipedia.org/wiki/Near_field_communication) tags designed by [Broadcom Inc.](https://en.wikipedia.org/wiki/Broadcom_Inc.). They are [NFC Forum](https://nfc-forum.org/) Type 1 compliant tags.

### Tag features

  * Compliant with the NFC Forum Type 1 tag format
  * Compatible with the [ISO/IEC 14443](https://en.wikipedia.org/wiki/ISO/IEC_14443):2001, Parts 2 and 3; ISO/IEC 14443 Type A modulation scheme
  * Contactless operating Frequency: 13.56 MHz
  * Read/write operation (8-byte read/write commands)
  * Configurable for One Time Programmable (OTP) and Write Once Read Many (WORM) operation
  * Data communication rate: 106 kbps
  * Communication robustness: collision detection, 16-bit CRC integrity checking
  * EEPROM based user read/write memory area organized as blocks of 8 bytes (7 bytes of Unique Identification (UID) number for use in data authentication/anticloning, 480 bytes of user read/write memory, 64 bits (8 bytes) of OTP memory)
  * All memory areas are individually one time lockable by RF command (to produce read-only functionality)

## Donations

Bitcoin address: 13BMqpqbzJ62LjMWcPGWrTrdocvGqifdJ3
