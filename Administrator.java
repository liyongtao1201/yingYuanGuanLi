import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//类Administrator实现接口function
public class Administrator implements function {
    private Scanner in = new Scanner(System.in);
    // 预设管理员账户名和密码
    private String adminName ;//将用户名储存在文本文件D:\LYT\Documents\javaAdministrator.txt中
    private String adminPassword;//将密码储存在文本文件D:\LYT\Documents\javaAdministrator.txt中
    filmInformation filAdmin = new filmInformation();//接口类
    filmInformation[] filmAdmin = new filmInformation[10];//接口类数组
    //private HashMap<String, String> userPasswords;

    void AdministratorMenue() throws FileNotFoundException {
        boolean flag = true;
        //登录操作
        if (!loginAdmin())
            flag = false;
        //登录成功
        while (flag) {
            //输出功能选择菜单
            printMenu();
            double input = in.nextDouble();
            //对操作选择的输入进行一定的容错处理，当输入的数字不是整数以及不在1~5之间时进入循环
            while (input < 1 || input > 5 || !(input % (int) input == 0)) {
                System.out.println("输入有误，请重新输入：");
                input = in.nextDouble();
            }
            switch ((int) input) {
                //修改密码
                case 1:
                    changePassword();
                    break;
                //列出影城方所有用户信息
                case 2:
                    listAllInformation();
                    break;
                //删除用户信息
                case 3:
                    deleteUser();
                    break;
                //查询用户信息
                case 4:
                    queryUser();
                    break;
                //退出登录,返回主菜单界面
                case 5:
                    System.out.println("再见！");
                    flag = false;
                    break;
            }
        }
    }

    // 登录管理员账户
    boolean loginAdmin() throws FileNotFoundException {
        get_admin_usename();//读取文件上的管理员密码
        System.out.print("请输入用户名：");
        String inputName = in.nextLine();
        System.out.print("请输入密码：");
        String inputPassword = in.nextLine();
        //管理员用户名及密码进行匹对
        if (inputName.equals(adminName) && inputPassword.equals(adminPassword)) {
            System.out.println("登录成功！");
            return true;
        } else {
            System.out.println("用户名或密码错误！");
            return false;
        }
    }

    //读取地址D:\LYT\Documents\javaAdministrator.txt上储存的管理员用户名和密码
    void get_admin_usename() throws FileNotFoundException {
        int t = 0;
        try (Scanner sc = new Scanner(new FileReader("D:\\LYT\\Documents\\javaAdministrator.txt"))) {
            while (sc.hasNextLine()) {   //按分隔符读取字符串
                adminPassword = sc.nextLine();
                t++;
                if (t == 3)
                    break;
            }
        }
        int r = 0;
        try (Scanner res = new Scanner(new FileReader("D:\\LYT\\Documents\\javaAdministrator.txt"))) {
            while (res.hasNextLine()) {   //按分隔符读取字符串
                adminName = res.nextLine();
                r++;
                if (r == 2)
                    break;
            }
        }
    }

    // 管理员功能选择菜单
    private void printMenu() {
        System.out.println("===== 管理员功能菜单 =====");
        System.out.println("1. 修改管理员密码");
        System.out.println("2. 列出所有影城方用户信息");
        System.out.println("3. 删除影城方用户信息");
        System.out.println("4. 查询影城方用户信息");
        System.out.println("5. 退出登录");
        System.out.print("请选择将要进行的操作：\n");
    }

    // 修改管理员密码
    private void changePassword() {
        System.out.print("请输入管理员当前的密码：");
        Scanner in = new Scanner(System.in);
        String currentPassword = in.nextLine();
        //确认身份信息
        if (currentPassword.equals(adminPassword)) {
            System.out.print("请输入新密码：");
            String newPassword = in.nextLine();
            //用新密码替换掉原来的密码
            filAdmin.autoReplace("D:\\LYT\\Documents\\javaAdministrator.txt", adminPassword, newPassword);
            System.out.println("密码修改成功！");
        } else {
            System.out.println("管理员当前的密码输入错误，暂无法修改密码！");
        }
    }

    //列出所有用户信息
    void listAllInformation() {
        System.out.println("影城方用户信息列表：");
        for (int i = 0; i < 6; i++) {
            System.out.println("用户" + (i + 1) + "的信息如下：");
            filmAdmin[i] = new filmInformation();
            filmAdmin[i].set_cinema_square_Information(i + 1);
            filmAdmin[i].putInformation(filmAdmin[i].cinemaSquare);
        }
    }

    //删除影城方的用户信息
    private void deleteUser() {
        //先列出现有的所有用户的信息
        listAllInformation();
        System.out.print("请输入要删除的影城方用户ID或用户名：\n");// 根据输入的ID或用户名找到相应用户并删除
        Scanner in = new Scanner(System.in);
        //输入要删除的用户的ID或用户名
        String userIDorUsername = in.nextLine();
        //遍历寻找所寻找的用户信息
        for (int i = 0; i < 6; i++) {
            filmAdmin[i] = new filmInformation();
            //从文本文件D:\LYT\Documents\javaUserInformation.txt上读取用户信息
            filmAdmin[i].set_cinema_square_Information(i + 1);
            Pattern P = Pattern.compile(userIDorUsername);
            Matcher m = P.matcher(filmAdmin[i].cinemaSquare[1]);//对用户ID进行匹配
            Matcher m1 = P.matcher(filmAdmin[i].cinemaSquare[2]);//对用户名进行匹配
            if (m.find() || m1.find()) {
                System.out.println("即将删除该用户信息\n回车确定删除");
                in.nextLine();
                for (int j = 0; j < 7; j++)
                    filAdmin.autoReplace("D:\\LYT\\Documents\\javaUserInformation.txt", filmAdmin[i].cinemaSquare[j], "");
                System.out.println("影城方用户删除成功！");
                break;
            }
        }
    }

    //查询影院用户信息
    void queryUser() {
        System.out.println("请选择查询方式：");
        System.out.println("1. 根据用户ID查询");
        System.out.println("2. 根据用户名查询");
        System.out.println("3. 退出");
        System.out.print("请选择操作：");
        double choice = in.nextDouble();
        in.nextLine(); //清空输入缓冲区
        while (choice < 1 || choice > 3 || !(choice % (int) choice == 0)) {
            System.out.println("输入的选择格式有误，请重新输入：");
            choice = in.nextDouble();
        }
        // 根据选择的方式进行查询，并输出结果
        switch ((int) choice) {
            // 根据用户ID查询用户信息并输出
            case 1:
                System.out.print("请输入用户ID：");
                String userID = in.nextLine();
                for (int i = 0; i < 10; i++) {
                    filmAdmin[i] = new filmInformation();
                    filmAdmin[i].set_cinema_square_Information(i + 1);
                    Pattern p = Pattern.compile(userID);
                    Matcher m = p.matcher(filmAdmin[i].cinemaSquare[1]);
                    if (m.find()) {
                        filmAdmin[i].putInformation(filmAdmin[i].cinemaSquare);//输出查找到的信息
                        break;
                    } else {
                        System.out.println("您输入的用户不存在");
                        break;
                    }
                }
                break;
            //根据用户名查询用户信息并输出
            case 2:
                System.out.print("请输入用户名：");
                String username = in.nextLine();
                // 根据用户名查询用户信息并输出
                for (int i = 0; i < 10; i++) {
                    filmAdmin[i] = new filmInformation();
                    filmAdmin[i].set_cinema_square_Information(i + 1);
                    Pattern p = Pattern.compile(username);
                    Matcher m = p.matcher(filmAdmin[i].cinemaSquare[2]);
                    if (m.find()) {
                        filmAdmin[i].putInformation(filAdmin.cinemaSquare);//输出查找到的信息
                        break;
                    } else {
                        System.out.println("您输入的用户不存在");
                        break;
                    }
                }
                break;
            case 3:
                break;
            default:
                System.out.println("请输入有效选项！");
                break;
        }
    }

    @Override
    public boolean regis(String n) {
        return false;
    }

    @Override
    public boolean regis_pass(String p) {
        return false;
    }

    @Override
    public void set() {
    }
}