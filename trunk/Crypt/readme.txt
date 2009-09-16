1) For command line mode, set "Mode = 0" in config.ini.
2) For graphics mode, set "Mode = 1" in config.ini.

The following description are for command line parameters.

/oper:Test, Exe, Digest, HMac		
Test for algorithm test, Exe for crypto execution(default), Digest for message digest and HMac for message HMac.

Mode "Test"
Supported algorithms:
"AES", "Blowfish", "Camellia", "CAST5", "CAST6", "DES", "GOST28147", "IDEA", "MISTY1", "Noekeon",
"Null", "RC2", "RC6", "Rijndael", "SEED", "Serpent", "Skipjack", "TDEA", "TEA", "Twofish", "XTEA",
"MD2Digest", "MD4Digest", "MD5Digest", "SHA1Digest", "SHA224Digest", "SHA256Digest", "SHA384Digest",
"SHA512Digest", "RIPEMD128Digest", "RIPEMD160Digest", "RIPEMD256Digest", "RIPEMD320Digest", "TigerDigest", "GOST3411Digest", "WhirlpoolDigest", "MD5HMac", "SHA1HMac", "SHA224HMac", "SHA256HMac", "SHA384HMac",
"SHA512HMac", "RIPEMD128HMac", "RIPEMD160HMac"
Parameters:
/in:xxx
/out:xxx
/key:xxx

Mode "Exe"
/algo:
"AES", "Blowfish", "Camellia", "CAST5", "CAST6", "DES", "GOST28147", "IDEA", "MISTY1", "Noekeon",
"Null", "RC2", "RC6", "Rijndael", "SEED", "Serpent", "Skipjack", "TDEA", "TEA", "Twofish", "XTEA", "RSA"
Parameters:
/in:xxx
/out:xxx
/key:xxx
/iv:xxx
/enc:0, 1		0 for encrypt(default), 1 for decrypt
/mode: a decimal number

case 0: ECBBlockCipher(_eng)
case 1: CBCBlockCipher(_eng)
case 2: CFBBlockCipher(_eng, 1)
case 3: CFBBlockCipher(_eng, 8)
case 4: CFBBlockCipher(_eng, 64)
case 5: OFBBlockCipher(_eng, 1)
case 6: OFBBlockCipher(_eng, 8)
case 7: OFBBlockCipher(_eng, 64)
case 8: SICBlockCipher(_eng)
default:new ECBBlockCipher(_eng)

Note:
For RSA only encrypt mode available and only ".cer" file allowed for encryption.

"Digest" mode
/algo:
"MD2Digest", "MD4Digest", "MD5Digest", "SHA1Digest", "SHA224Digest", "SHA256Digest", "SHA384Digest",
"SHA512Digest", "RIPEMD128Digest", "RIPEMD160Digest", "RIPEMD256Digest", "RIPEMD320Digest", "TigerDigest", "GOST3411Digest", "WhirlpoolDigest"
Parameters:
/in:xxx
/out:xxx

Mode "HMac"
/algo:
"MD5HMac", "SHA1HMac", "SHA224HMac", "SHA256HMac", "SHA384HMac", "SHA512HMac", "RIPEMD128HMac", "RIPEMD160HMac"
Parameters:
/in:xxx
/out:xxx
/key:xxx