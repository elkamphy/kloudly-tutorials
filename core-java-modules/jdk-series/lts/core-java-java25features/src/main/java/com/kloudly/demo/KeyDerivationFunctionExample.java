package com.kloudly.demo;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class KeyDerivationFunctionExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		// Create a KDF object for the HKDF algorithm with SHA-256
		KDF hkdf = KDF.getInstance("HKDF-SHA256");

		// Build the parameters for an extract-then-expand operation
		byte[] initialKeyMaterial = "shared-secret".getBytes();
		byte[] salt = "random-salt".getBytes();
		byte[] info = "context".getBytes();
		AlgorithmParameterSpec params =
				HKDFParameterSpec.ofExtract()    // Start the extraction phase
						.addIKM(initialKeyMaterial)
						.addSalt(salt)
						.thenExpand(info, 32); // Expand to 32 bytes for an AES key

		// Derive a new AES key
		SecretKey aesKey = hkdf.deriveKey("AES", params);

		// Show result
		System.out.println("Derived key: " + Arrays.toString(aesKey.getEncoded()).substring(0, 30) + "...");
    }
}