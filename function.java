import java.io.*;
import java.util.Scanner;

public interface function {
    boolean regis(String n);
    boolean regis_pass(String p);
    void set();
}

class filmInformation {
    private Scanner in = new Scanner(System.in);
    String[] cami_information = new String[6];
    char[][] seat = new char[7][12];//影院厅的规模、大小
    String[] session = new String[2];
    String[] cinemaSquare = new String[7];//储存单个用户信息
    String[] userInformation = new String[8];

    //初始化座位信息，0表示当前座位为空，先为行，再为列
    void initSeat() {
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 12; j++)
                seat[i][j] = 'O';
    }

    //座位布局（对齐）
    void changSeat() {
        for (int i = 0; i < 9; i++) {
            System.out.print(i +1+"   ");
        }
        System.out.print("10  ");
        System.out.print("11  ");
        System.out.print("12  ");
        System.out.println("\t");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 12; j++)
                System.out.print(seat[i][j] + "   ");
            System.out.println((char) (65 + i));
        }
    }

    //读取文件D:\LYT\Documents\javaUserInformation.txt上的影城方用户信息
    void set_cinema_square_Information(int k) {
        int t = 0, m = 0;
        String fileName = "D:\\LYT\\Documents\\javaUserInformation.txt";
        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            while (sc.hasNextLine()) {
                cinemaSquare[t] = sc.nextLine();
                t++;
                if (t == 7) {
                    t = 0;//一个人的信息存7行，满7行后归0，继续储存下一个用户的信息
                    m++;
                    if (m == k)
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //读取文件D:\LYT\Documents\javaMovie.txt上的影片相关信息
    void setMovieInformation(int k) {
        int t = 0, m = 0;
        String fileName = "D:\\LYT\\Documents\\javaMovie.txt";
        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            while (sc.hasNextLine()) {
                cami_information[t] = sc.nextLine();
                t++;
                if (t == 6) {
                    t = 0;
                    m++;
                    if (m == k)
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //读取文件D:\LYT\Documents\javauser.txt上的用户信息
    void getUserInformation(int k) {
        int t = 0, m = 0;
        String fileName = "D:\\LYT\\Documents\\javauser.txt";
        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            while (sc.hasNextLine()) {
                userInformation[t] = sc.nextLine();
                t++;
                if (t == 8) {
                    t = 0;
                    m++;
                    if (m == k)
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //输出信息
    void putInformation(String[] st) {
        for (int i = 0; i < st.length; i++) {
            if (i == 0)
                continue;
            System.out.println((i) + "、 " + st[i]);
        }
    }

    //实现代码与.txt文本文件的交互，进行信息的改动
    void autoReplace(String filePath, String oldStr, String newStr) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] fileContext = new byte[fileLength.intValue()];
        FileInputStream in = null;
        PrintWriter out = null;
        try {
            in = new FileInputStream(filePath);
            in.read(fileContext);
            String str = new String(fileContext, "UTF-8");
            str = str.replace(oldStr, newStr);
            out = new PrintWriter(filePath);
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //实现信息的增加
    void add_new_information(String filmDress, String newInformation) {
        try {
            BufferedWriter in = new BufferedWriter(new FileWriter(filmDress, true));
            in.write(newInformation);
            in.close();
            BufferedReader out = new BufferedReader(new FileReader(filmDress));
            out.close();
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }
}