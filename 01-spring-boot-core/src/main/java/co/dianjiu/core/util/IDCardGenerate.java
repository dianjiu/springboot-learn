package co.dianjiu.core.util;

import java.util.Random;

public class IDCardGenerate {

    /**
     *
     * @param areaCode 区域代码
     * @param birthday  出生日期
     * @param gender    性别 1-男 0-女
     * @param bX 是否带x
     * @return
     */
    public static String generateIDCard(String areaCode, String birthday, String gender, boolean bX) {
        String sb = areaCode + birthday;

        int sex = new Random().nextInt(10);
        if ("1".equals(gender)) {
            //男性
            sex = (sex * 2 + 1) % 10;
        } else if ("0".equals(gender)) {
            //女性
            sex = sex * 2 % 10;
        } else {
            //不限
        }

        if (bX) {
            //必须带X
            StringBuffer cardId = null;
            for (int i = 0; i < 100; i++) {
                cardId = new StringBuffer(sb);
                if (i < 10) {
                    cardId.append("0");
                }
                cardId.append(i);
                cardId.append(sex);
                String verifyCode = getVerify(cardId.toString());
                if ("x".equalsIgnoreCase(verifyCode)) {
                    return cardId.append(verifyCode).toString();
                }
            }
        } else {
            //随意(三位数为偶数代表女性 所以最后一位由性别控制)
            String cardId = sb.toString();
            int i = new Random().nextInt(100);
            if (i < 10) {
                cardId = cardId + "0" + i;
            } else {
                cardId = cardId + i;
            }
            cardId = cardId + sex;
            String verifyCode = getVerify(cardId);
            return cardId + verifyCode;
        }
        return "";
    }

    /**
     * 生成最后一位校验码
     * @param cardId
     * @return
     */
    private static String getVerify(String cardId) {
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(cardId.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];

        return strVerifyCode;
    }
    //单元测试
    public static void main(String[] args) {
        String idCard = generateIDCard("150102", "19880730", "0", false);
        System.out.println(idCard);
    }
}
