package co.dianjiu.hello.demo;

import co.dianjiu.hello.custom.BusinessException;
import co.dianjiu.hello.utils.AESUtils;
import co.dianjiu.hello.utils.SM4Utils;

public class EncryptTest {

    private static final String AES_ALG         = "AES";
    private static final String SM4_ALG         = "SM4";

    private static final String charSet = "UTF-8";
    private static final String encryptTypeA = "AES";
    private static final String encryptTypeS = "SM4";
    private static final String encryptKey = "1234567887654321";

    public static void main(String[] args) {
        //测试AES加密
        String encryptContentAES = encryptContent("Java是世界上最好的语言", encryptTypeA, encryptKey, charSet);
        System.out.println("AES加密后=="+encryptContentAES);
        //测试AES解密
        String decryptContentAES = decryptContent(encryptContentAES, encryptTypeA, encryptKey, charSet);
        System.out.println("AES解密后=="+decryptContentAES);
        //测试SM4加密
        String encryptContentSM4 = encryptContent("Java是世界上最好的语言", encryptTypeS, encryptKey, charSet);
        System.out.println("SM4加密后=="+encryptContentSM4);
        //测试SM4解密
        String decryptContentSM4 = decryptContent(encryptContentSM4, encryptTypeS, encryptKey, charSet);
        System.out.println("SM4解密后=="+decryptContentSM4);
    }
    /**
     *   加密
     *
     * @param content
     * @param encryptType   SM4加解密秘钥必须为16位
     * @param encryptKey
     * @param charset
     * @return
     * @throws BusinessException
     */
    public static String encryptContent(String content, String encryptType, String encryptKey,
                                        String charset) throws BusinessException {

        if (AES_ALG.equals(encryptType)) {

            return AESUtils.aesEncrypt(content, encryptKey, charset);

        } else if (SM4_ALG.equals(encryptType)){

            return SM4Utils.encryptData_ECB(encryptKey,content);

        }else {

            throw new BusinessException("100","当前不支持该算法类型：encrypeType=" + encryptType);
        }

    }

    /**
     *  解密
     *
     * @param content
     * @param encryptType
     * @param encryptKey
     * @param charset
     * @return
     * @throws BusinessException
     */
    public static String decryptContent(String content, String encryptType, String encryptKey,
                                        String charset) throws BusinessException {

        if (AES_ALG.equals(encryptType)) {

            return AESUtils.aesDecrypt(content, encryptKey, charset);

        }else if (SM4_ALG.equals(encryptType)){
            //SM4解密
            String plainText = SM4Utils.decryptData_ECB(encryptKey,content);
            //SM4解密校验
            boolean verifyEcb = SM4Utils.verifyEcb(encryptKey, plainText, content);
            if(verifyEcb){
                return plainText;
            }else{
                throw new BusinessException("100","SM4解密失败，请检查秘钥：encrypeType=" + encryptType +" 密文：content"+content);
            }

        } else {

            throw new BusinessException("100","当前不支持该算法类型：encrypeType=" + encryptType);
        }

    }

}
