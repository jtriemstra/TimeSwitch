package com.example.jtriemstra.timeswitch.models;

public class AppInfo
{
    /**
     * The login parameters should be specified in the following manner:
     * 
     * public static final String SpeechKitServer = "ndev.server.name";
     * 
     * public static final int SpeechKitPort = 1000;
     * 
     * public static final String SpeechKitAppId = "ExampleSpeechKitSampleID";
     * 
     * public static final byte[] SpeechKitApplicationKey =
     * {
     *     (byte)0x38, (byte)0x32, (byte)0x0e, (byte)0x46, (byte)0x4e, (byte)0x46, (byte)0x12, (byte)0x5c, (byte)0x50, (byte)0x1d,
     *     (byte)0x4a, (byte)0x39, (byte)0x4f, (byte)0x12, (byte)0x48, (byte)0x53, (byte)0x3e, (byte)0x5b, (byte)0x31, (byte)0x22,
     *     (byte)0x5d, (byte)0x4b, (byte)0x22, (byte)0x09, (byte)0x13, (byte)0x46, (byte)0x61, (byte)0x19, (byte)0x1f, (byte)0x2d,
     *     (byte)0x13, (byte)0x47, (byte)0x3d, (byte)0x58, (byte)0x30, (byte)0x29, (byte)0x56, (byte)0x04, (byte)0x20, (byte)0x33,
     *     (byte)0x27, (byte)0x0f, (byte)0x57, (byte)0x45, (byte)0x61, (byte)0x5f, (byte)0x25, (byte)0x0d, (byte)0x48, (byte)0x21,
     *     (byte)0x2a, (byte)0x62, (byte)0x46, (byte)0x64, (byte)0x54, (byte)0x4a, (byte)0x10, (byte)0x36, (byte)0x4f, (byte)0x64
     * };
     * 
     * Please note that all the specified values are non-functional
     * and are provided solely as an illustrative example.
     * 
     */

    /* Please contact Nuance to receive the necessary connection and login parameters */
    public static final String SpeechKitServer = "sandbox.nmdp.nuancemobility.net";/* Enter your server here */;

    public static final int SpeechKitPort = 443/* Enter your port here */;
    
    public static final boolean SpeechKitSsl = false;

    public static final String SpeechKitAppId = "NMDPTRIAL_joeltriemstra_sbcglobal_net20160116163401"/* Enter your ID here */;

    public static final byte[] SpeechKitApplicationKey = {
    	(byte)0x25, (byte)0x22, (byte)0x59, (byte)0xb8, (byte)0x31, (byte)0x21, (byte)0x54, (byte)0xf7, (byte)0x48, (byte)0xf1, (byte)0xd0, (byte)0x86, (byte)0x5a, (byte)0x65, (byte)0xef, (byte)0xd9, (byte)0x57, (byte)0xf9, (byte)0x21, (byte)0x3b, (byte)0x97, (byte)0xe3, (byte)0x8f, (byte)0xb2, (byte)0x0b, (byte)0x56, (byte)0x0b, (byte)0x8b, (byte)0x19, (byte)0xf1, (byte)0x84, (byte)0x3e, (byte)0x13, (byte)0xde, (byte)0xf7, (byte)0x45, (byte)0xff, (byte)0x4b, (byte)0x3c, (byte)0x47, (byte)0x3d, (byte)0x0c, (byte)0xcd, (byte)0x97, (byte)0x71, (byte)0xbd, (byte)0x71, (byte)0xc8, (byte)0xb6, (byte)0x92, (byte)0x04, (byte)0x4e, (byte)0x76, (byte)0x93, (byte)0x00, (byte)0x81, (byte)0xe9, (byte)0x0b, (byte)0x0c, (byte)0x03, (byte)0xfd, (byte)0x91, (byte)0x41, (byte)0x10
    };
}
