
# Topaz512-driver ![Maven build](https://github.com/mdeverdelhan/Topaz512-driver/workflows/Maven%20build/badge.svg?branch=master)

Here is a simple Java library/driver for building commands to communicate with Innovision/Broadcom Topaz512 NFC tags.

### Features

  * Fully compliant with Broadcom BCM20203T512 specification
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

TODO

## Donations

Bitcoin address: 13BMqpqbzJ62LjMWcPGWrTrdocvGqifdJ3
