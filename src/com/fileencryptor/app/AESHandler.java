package com.fileencryptor.app;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AESHandler {

	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 128;

	public static String encryptData(String dataToEncrypt, String userKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException {

		byte[] saltBytes = generateSalt();
		SecretKeySpec mSecretKeySpec = generateSecretKey(saltBytes, userKey);

		Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);

		byte[] ivBytes = mCipher.getIV();
		byte[] encryptedTextBytes = mCipher.doFinal(dataToEncrypt.getBytes());
		byte[] combined = combineByteArrys(ivBytes, saltBytes,
				encryptedTextBytes);

		return new Base64().encodeAsString(combined);

	}

	public static String decryptData(String dataToDecrypt, String userKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		byte[] encryptedCombinedBytes = Base64.decodeBase64(dataToDecrypt);
		byte[] ivbytes = Arrays.copyOfRange(encryptedCombinedBytes, 0, 16);
		byte[] saltBytes = Arrays.copyOfRange(encryptedCombinedBytes, 16, 48);
		byte[] encryptedTextBytes = Arrays.copyOfRange(encryptedCombinedBytes,
				48, encryptedCombinedBytes.length);

		SecretKeySpec mSecretKeySpec = generateSecretKey(saltBytes, userKey);

		Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, new IvParameterSpec(
				ivbytes));

		byte[] decryptedTextBytes = mCipher.doFinal(encryptedTextBytes);

		return StringUtils.newStringUtf8(decryptedTextBytes);

	}

	public static byte[] generateSalt() {

		SecureRandom random = new SecureRandom();
		byte saltBytes[] = new byte[32];
		random.nextBytes(saltBytes);
		return saltBytes;
	}

	private static SecretKeySpec generateSecretKey(byte[] saltBytes,
			String userKey) throws NoSuchAlgorithmException,
			InvalidKeySpecException {

		KeySpec mKeySpec = new PBEKeySpec(userKey.toCharArray(), saltBytes,
				ITERATIONS, KEY_LENGTH);
		SecretKeyFactory mSecretKeyFactory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA256");
		SecretKey mSecretKey = mSecretKeyFactory.generateSecret(mKeySpec);
		return new SecretKeySpec(mSecretKey.getEncoded(), "AES");
	}

	private static byte[] combineByteArrys(byte[]... byteArrays) {

		int combinedByteArraySize = 0;
		for (byte[] array : byteArrays) {
			combinedByteArraySize = combinedByteArraySize + array.length;
		}

		byte[] combined = new byte[combinedByteArraySize];
		int indexPoint = 0;

		for (byte[] array : byteArrays) {
			System.arraycopy(array, 0, combined, indexPoint, array.length);
			indexPoint = indexPoint + array.length;
		}

		return combined;

	}

}
