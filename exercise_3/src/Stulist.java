import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Stulist {
    //开始使用的字节文件转接失败。
	/*public static void CombineFile(String argv[])throws IOException{
	File file = new File("scorelist.txt");//打开第一个文件，即成绩表
	byte[] b = new byte[(int)file.length()];
	FileInputStream in = new FileInputStream(file);
	in.read(b);

	File file3 = new File("list.txt");//把成绩表输入到一个文本中
	FileOutputStream out=new FileOutputStream(file3);
	out.write(b);
	in.close();

	file = new File("student.txt");//再打开第二个文件，即学生名单，输入到上面的那个文本中
	b = new byte[(int)file.length()];
	in = new FileInputStream(file);
	in.read(b);
	out.write(b);
	out.close();
	in.close();
}*/
    public static void writeTxt(String string) {
        try {//使用了网络上的两个文本衔接，在衔接过程中两个文本会粘在一起，所以把Studentlist多输出一行
            FileReader fr = new FileReader("Studentlist.txt");//读取学生表
            FileWriter fw = new FileWriter("Score.txt", true);//把学生表衔接在成绩表后
            int read;
            read = fr.read();
            while (read != -1) {
                fw.write(read);
                read = fr.read();
            }
            fr.close();
            fw.close();//读取和输出之后需要关闭
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //用来读取已经产生的成绩单，便于后面创建数组时的Matrix的大小
    public static ArrayList<String> readTxtFile(String filePath) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            String encoding = "UTF-8";
            File file = new File("Score.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (!lineTxt.startsWith("#"))
                        list.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("文件路径错误，不能找到");
            }
        } catch (Exception e) {
            System.out.println("发生了错误");
            e.printStackTrace();
        }
        return list;
    }

    //创建二维数组，用来存取后面的数据
    public static String[][] createArray(String filePath) throws Exception {
        ArrayList<String> list = readTxtFile(filePath);
        String array[][] = new String[list.size()][8];
        for (int i = 0; i < list.size(); i++) {
            String linetxt = list.get(i);
            String[] myArray = linetxt.replaceAll("\\s+", "@").split("@");
            for (int j = 0; j < myArray.length; j++) {
                array[i][j] = myArray[j];
            }
        }
        return array;
    }
    //用来生成随机成绩，并且计算每个学生的总成绩
    public static void total(String array[][]) throws IOException {
        Random rand = new Random();
        for (int i = 1; i < array.length; i++) {
            for (int j = 2; j < 8; j++) {
                if (2 <= j && j <= 6) {
                    String str = "" + (int) ((50.0 / 6) * rand.nextGaussian() + 75);
                    array[i][j] = str;
                }
                {
                    if (j == 7) {
                        int m = 0;
                        for (int k = 2; k < 7; k++) {
                            m += Integer.parseInt(array[i][k]);//强制转换为int
                        }
                        String str1 = "" + m;//简便强制转换为String型
                        array[i][j] = str1;
                    }

                }
            }
        }
    }
    //计算科目平均成绩
    public static void avg(String array[][]) {
        array[array.length-1][0] = "平均成绩";//最后一行加入平均成绩
        array[array.length-1][1] = "\t";//第二格需要空开，使后面的成绩数据对齐
        for (int j = 2; j < 8; j++) {
            int t= 0;
            for (int h = 1; h < array.length; h++) {//在第2行到倒数第2行成绩的累加
                t += Integer.parseInt(array[h][j]);

            }
            String str2 = "" + t / array.length;
            array[array.length - 1][j] = str2;//最后一行输出平均数
        }

    }

    //归并算法就是类似于二分法，每一部分的比较，通过begin,middle,end来一点点的比较。
    public static String[][] mergeArray(String array[][], String temparray[][], int beginIndex, int middleIndex,
                                        int endIndex) {
        int i = beginIndex, j = middleIndex + 1, k = beginIndex;
        while (i != middleIndex + 1 && j != endIndex + 1) {
            if (Integer.parseInt(array[i][7]) <= Integer.parseInt(array[j][7])) {
                temparray[k++][7] = array[j++][7];
                for (int h = 0; h < 7; h++)
                    temparray[k - 1][h] = array[j - 1][h];
            } else {
                temparray[k++][7] = array[i++][7];
                for (int h = 0; h < 7; h++)
                    temparray[k - 1][h] = array[i - 1][h];
            }
        }
        while (i != middleIndex + 1) {
            temparray[k++][7] = array[i++][7];
            for (int h = 0; h < 7; h++)
                temparray[k - 1][h] = array[i - 1][h];
        }
        while (j != endIndex + 1) {
            temparray[k++][7] = array[j++][7];
            for (int h = 0; h < 7; h++)
                temparray[k - 1][h] = array[j - 1][h];
        }
        for (i = beginIndex; i <= endIndex; i++) {
            array[i][7] = temparray[i][7];
            for (int h = 0; h < 7; h++)
                array[i][h] = temparray[i][h];
        }
        return array;
    }
    //将上面的排序递推到所有的成绩数据，使其排序完毕。
    public static String[][] mergeSort(String array[][], String temparray[][], int beginIndex, int endIndex) {
        int midIndex;
        if (beginIndex < endIndex) {
            midIndex = (beginIndex + endIndex) / 2;
            mergeSort(array, temparray, beginIndex, midIndex);
            mergeSort(array, temparray, midIndex + 1, endIndex);
            mergeArray(array, temparray, beginIndex, midIndex, endIndex);
        }
        return array;
    }
    //输出已经生成的排好序的成绩表
    public static void print(String array[][]) throws IOException {
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (j != array[i].length - 1) {
                    System.out.print(array[i][j] + " ");
                } else {

                    System.out.print(array[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    //生成一个新的txt成绩表，为Stulist
    public static void write(String array[][]) throws IOException {
        File file = new File("Stulist.txt");
        BufferedWriter printArray = null;
        try {
            printArray = new BufferedWriter(new FileWriter(file, true));
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (j != array[i].length - 1) {
                        printArray.write(array[i][j] + "  ");
                    } else {
                        printArray.write(array[i][j] + "  ");
                    }
                    if (j == 7)
                        printArray.write("\r\n");
                }
                System.out.println();
            }
            printArray.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //最后在Main函数里实现上面的函数
    public static void main(String args[]) throws Exception {
        writeTxt(null);
        String array[][] = createArray("Studentlist.txt");
        total(array);
        ArrayList<String> list = readTxtFile("Score.txt");
        String[][] temparray = new String[list.size()][8];
        mergeSort(array, temparray, 1, list.size() - 1);
        avg(array);
        write(array);
        print(array);
    }
}
