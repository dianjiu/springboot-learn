package co.dianjiu.hello.demo;
public class LongTest {
    public static void main(String[] args) {
        Long YW_InsuredAmt = 0L; //意外保险金额 0600001
        Long ZJ_InsuredAmt = 100000L; //重疾保险金额 1200009
        Long SG_InsuredAmt = 0L; //身故保险金额 1200011
        //提示语二
        if (1000L != ZJ_InsuredAmt) {
            System.out.println("重大疾病保额必须是1000");
        }
    }
}
