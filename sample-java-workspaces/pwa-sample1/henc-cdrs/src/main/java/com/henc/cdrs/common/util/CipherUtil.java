package com.henc.cdrs.common.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author SGCHO
 * 		CipherUtil cu = new CipherUtil();

		String plainStr = "M110566345";
		String algorithm = cu.AES_ALGORITHM;

		String encryptStr = cu.encrytion(plainStr, algorithm);

		System.out.println(encryptStr);

		System.out.println(cu.decrytion(encryptStr, algorithm));
 */
public class CipherUtil {


	public static final String DES_ALGORITHM = "DES";
	public static final String DESede_ALGORITHM = "DESede";
	public static final String AES_ALGORITHM = "AES";


	public Key getKey(String algorithm ) {

		String keyStr = "";

		if ("DES".equalsIgnoreCase(algorithm)) {
			keyStr =  "68616e6765656e61";

		} else if ("DESede".equalsIgnoreCase(algorithm)
				|| "TripleDES".equalsIgnoreCase(algorithm)) {
			keyStr = "696d697373796f7568616e6765656e61696d697373796f75";
		}
		else{
			keyStr = "696d697373796f7568616e6765656e61";
		}

		Key key = null;

		try {

			key = generateKey(algorithm, ByteUtils.toBytes(keyStr, 16));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return key;
	}

	/**
	 *
	 * <p>해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.</p>
	 *
	 * @return 비밀키(SecretKey)
	 */

	public Key generateKey(String algorithm) throws NoSuchAlgorithmException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

		SecretKey key = keyGenerator.generateKey();

		return key;

	}

	/**
	 *
	 * <p>주어진 데이터로, 해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.</p>
	 *
	 * @param algorithm DES/DESede/TripleDES/AES
	 * @param keyData
	 * @return
	 */

	public Key generateKey(String algorithm, byte[] keyData)
			throws NoSuchAlgorithmException, InvalidKeyException,
			InvalidKeySpecException {

		// String upper = String.toUpperCase(algorithm);

		if ("DES".equalsIgnoreCase(algorithm)) {

			KeySpec keySpec = new DESKeySpec(keyData);

			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance(algorithm);

			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

			return secretKey;

		} else if ("DESede".equalsIgnoreCase(algorithm)
				|| "TripleDES".equalsIgnoreCase(algorithm)) {

			KeySpec keySpec = new DESedeKeySpec(keyData);

			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance(algorithm);

			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

			return secretKey;

		} else {

			SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);

			return keySpec;

		}

	}

	/**
	 * Algorithm : DES , DESede, AES  Encryption
	 * @param plainStr
	 * @return
	 * @throws Exception
	 */
	public String encrytion(String plainStr, String algorithm)  {

		String encryptHex = "";

		try {

			Key key = getKey(algorithm);

			String transformation = algorithm+"/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plain = plainStr.getBytes();
			byte[] encrypt = cipher.doFinal(plain);
			encryptHex = ByteUtils.toHexString(encrypt);

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptHex;

	}

	/**
	 * Algorithm : DES , DESede, AES Decryption
	 * @param encryptHex
	 * @return
	 * @throws Exception
	 */
	public String decrytion(String encryptHex, String algorithm) {

		String transformation = algorithm+"/ECB/PKCS5Padding";

		Cipher cipher;
		String decStr = "";

		try {

			cipher = Cipher.getInstance(transformation);

			byte[] decryptHex = ByteUtils.toBytesFromHexString(encryptHex);

			cipher.init(Cipher.DECRYPT_MODE, getKey(algorithm));

			byte[] decrypt = cipher.doFinal(decryptHex);

			decStr =  new String(decrypt);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return decStr;
	}



}
