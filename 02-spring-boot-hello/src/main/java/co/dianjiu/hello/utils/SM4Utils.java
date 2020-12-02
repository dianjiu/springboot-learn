package co.dianjiu.hello.utils;


import co.dianjiu.hello.custom.BusinessException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SM4加解密工具类
 * @author yyf
 *
 */
public class SM4Utils {
	private static final String iv = "UISwD9fW6cFh9SNS";
	private static final boolean hexString = false;

	public SM4Utils() {
	}

	public static String encryptData_ECB(String secretKey, String plainText) throws BusinessException {
		try {
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;

			byte[] keyBytes;
			keyBytes = secretKey.getBytes();
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));
			String cipherText = new String(Base64.encodeBase64(encrypted), java.nio.charset.StandardCharsets.UTF_8);
			if (cipherText != null && cipherText.trim().length() > 0) {
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} catch (Exception e) {
			throw new BusinessException("100","SM4加密失败：plainText: " + plainText);

		}
	}

	public static String decryptData_ECB(String secretKey, String cipherText) throws BusinessException {
		try {
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;

			byte[] keyBytes;
			keyBytes = secretKey.getBytes();
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			Base64.decodeBase64(cipherText.getBytes());
			byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64.decodeBase64(cipherText.getBytes()));
			return new String(decrypted, java.nio.charset.StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new BusinessException("100","SM4解密失败，请检查解密秘钥或解密类型,解密字段是否配置正确：cipherText: " + cipherText);

		}
	}

	public static String encryptData_CBC(String secretKey, String plainText) {
		try {
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;

			byte[] keyBytes;
			byte[] ivBytes;

			keyBytes = secretKey.getBytes();
			ivBytes = iv.getBytes();

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));
			String cipherText = new String(Base64.encodeBase64(encrypted), java.nio.charset.StandardCharsets.UTF_8);

			if (cipherText != null && cipherText.trim().length() > 0) {
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptData_CBC(String secretKey, String cipherText) {
		try {
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;

			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString) {
				keyBytes = Util.hexStringToBytes(secretKey);
				ivBytes = Util.hexStringToBytes(iv);
			} else {
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64.decodeBase64(cipherText.getBytes()));
			return new String(decrypted, java.nio.charset.StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param secretKey  秘钥
	 * @param plainText  明文
	 * @param cipherText 密文
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyEcb(String secretKey, String plainText, String cipherText) {
		boolean flag = false;
		try {

			if (StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(plainText) || StringUtils.isEmpty(cipherText)) {
				return flag;
			}

			String encryptData_ECB = encryptData_ECB(secretKey, plainText);

			flag = cipherText.equals(encryptData_ECB);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) throws Exception {
		String plainText = "ssda报文ii&$$^##)*&6223';.,[]";
		String secretKey = "11HDESaAhiHHugD1";
		System.out.println("ECB模式");
		String cipherText = SM4Utils.encryptData_ECB(secretKey, plainText);
		System.out.println("密文: " + cipherText);

		System.out.println();

		plainText = SM4Utils.decryptData_ECB("11HDESaAhiHHugD1", cipherText);
		System.out.println("明文: " + plainText);
		System.out.println();

		boolean verifyEcb = SM4Utils.verifyEcb(secretKey, plainText, cipherText);
		System.out.println("校验结果：" + verifyEcb);

		System.out.println("CBC模式");
		cipherText = SM4Utils.encryptData_CBC(secretKey, plainText);
		System.out.println("密文: " + cipherText);
		System.out.println();

		plainText = SM4Utils.decryptData_CBC(secretKey, cipherText);
		System.out.println("明文: " + plainText);
		// PI4ke7HMoUMD/LOSHWX5/g==

	}
}
