/**
 * Created by Shinelon on 2016/9/22.
 */
import java.io.IOException;
import java.util.Scanner;
public class TestScanner
{
    public static void main(String[] args) {
        TestScanner a  = new TestScanner();
        a.test1();
        a.test2();
        a.test3();
        a.test4();
        a.Test5();
    }
    public void test1(){
        Scanner input = new Scanner(System.in);
        System.out.println("Input");
        if (input.hasNext()) {
            System.out.println("你输入的是：" + input.nextInt());
        }

    }
    public void test2(){
        Scanner input = new Scanner(System.in);
        String length = input.next();
        System.out.println("输入的字符串是：" + length);
    }

    public void test3(){
        String s = " 3 + 3.0 = 6 ";
        Scanner input = new Scanner(s);

        if (input.hasNextDouble()) {
            System.out.println("显示:" + input.nextDouble());
        }
    }
    public void test4(){
        Scanner input = new Scanner(System.in);
        System.out.println("输入浮点数：");
        if (input.hasNext()) {
            System.out.println("你输入的是：" + input.nextDouble());
        }
    }
    public void Test5() {

            Scanner input=new Scanner(System.in);
            System.out.println("请输入一个整数：");
            int length=input.nextInt();
            System.out.println("Input char or  string");
            String str=input.next();
            System.out.println("输入的字符串是："+str);
            try {
                System.in.close();
                input.nextDouble();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
}












