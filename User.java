import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements function{
    int changeMovie, userNum = 5;
    String name, password;
    Scanner in = new Scanner(System.in);
    String[] information = new String[3];
    Manager manager = new Manager();
    filmInformation[] userInformations = new filmInformation[userNum];

    //功能实现
    void user_operation() throws AWTException {
        getUserMation();//获取用户信息
        boolean fag = true;
        int s = 0;//全局变量
        int p = 0001;
        //登录操作
        if (!user_lo()) {
            fag = false;
        }
        //登录成功
        while (fag) {
            System.out.println("请选择要进行的操作：");
            System.out.println("1、购票\n2、密码管理(修改密码)\n3、退出\n4、取票");
            double tb = in.nextDouble();
            while (tb < 1 || tb > 4 || !(tb % (int) tb == 0)) {
                System.out.println("选择输入的格式有误，请重新输入：");
                tb = in.nextDouble();
            }
            switch ((int) tb) {
                //购票
                case 1:
                    look_movie();
                    System.out.println("选择电影序号\n“0”返回");
                    changeMovie = in.nextInt();
                    System.out.println("输入购买票数\n“0”返回");
                    s = in.nextInt();
                    if (s >= 1) {
                        //支付操作
                        System.out.println("购票成功！\n\n按回车键进入选座,其他键退出返回");
                        in.nextLine();
                        if (in.nextLine().length() == 0) {
                            Robot r = new Robot();
                            r.keyRelease(KeyEvent.VK_ENTER);
                            if (selectSeat(changeMovie - 1, s))//选座
                                manager.film[changeMovie - 1].changSeat();
                            else continue;
                        }
                    } else {
                        System.out.println("数量输入错误");
                        continue;
                    }
                    break;
                //修改密码
                case 2:
                    password_manager();
                    break;
                //退出
                case 3:
                    System.out.println("再见！\n");
                    fag = false;
                    break;
                //取票
                case 4:
                    Date date = new Date();
                    long now = System.currentTimeMillis();
                    date.setTime(now);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    for (int i = 0; i < s; i++) {
                        System.out.println("您的票号是：" + format.format(date) + "-" + p);
                        p++;
                    }
                    System.out.println("共" + s + "张票");
                    break;
            }
        }
    }

    //获取用户信息
    void getUserMation() {
        for (int i = 0; i < userNum; i++) {
            userInformations[i] = new filmInformation();
            userInformations[i].getUserInformation(i + 1);
        }
    }

    //用户登录
    boolean user_lo() {
        int step = 1;
        int num = 5;//次数限制
        boolean[] fag = new boolean[]{false, false};
        while (true) {
            System.out.println("输入用户名：");
            name = in.nextLine();
            Pattern p1 = Pattern.compile("[^A-z]");//格式检验
            Matcher mata = p1.matcher(name);
            if (mata.find())
                continue;
            System.out.println("输入密码：");
            password = in.nextLine();
            if (regis(name) && regis_pass(password)) {
                System.out.println("登录成功！");
                fag[0] = true;
                return true;
            } else if (regis(name)) {
                System.out.println("用户名尚未注册！请选择\n1、注册\n2、退出");
                int sc = in.nextInt();
                if (sc == 1)
                    login();
                else if (sc == 2) {
                    return false;
                } else {
                    continue;
                }
            } else if (regis(name) && !regis_pass(password)) {
                System.out.println("密码错误，还有" + num + "次机会");
                num--;
                if (num == 0) {
                    information[2] = "false";
                    System.out.println("账号已锁定");
                    return false;
                }
                continue;
            } else {
                System.out.println("用户名或密码错误！请选择\\n1、注册\\n2、退出");
                int sc = in.nextInt();
                if (sc == 1)
                    login();
                else if (sc == 2) {
                    return false;
                } else {
                    continue;
                }
            }
        }
    }

    //查看当前影片信息
    void look_movie() {
        manager.setFilm(manager.filmNumb);
        for (int i = 0; i < 5; i++) {
            System.out.println("movie" + (i + 1));
            manager.film[i].putInformation(manager.film[i].cami_information);
        }
    }

    //修改密码
    void password_manager() {
        String newName, oldName;
        System.out.println("请输入原密码：");
        oldName = in.nextLine();
        if (oldName.length() == 0)
            oldName = in.nextLine();
        while (true) {
            if (regis_pass(oldName)) {
                System.out.println("请输入新密码：");
                information[1] = in.nextLine();
                System.out.println("请再次确认密码：");
                newName = in.nextLine();
                if (regis_pass(newName)) {
                    System.out.println("重设密码成功");
                    break;
                } else {
                    System.out.println("两次输入的密码不相同,请重新设置");
                    continue;
                }
            }
        }
    }

    // 需要完成的任务
    void zSeat(int r, int c) {
        char[][] seats = new char[7][12];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 12; j++)
                seats[i][j] = 'O';
        for (int i = 0; i < 9; i++)
            System.out.print(i + 1 + "   ");
        System.out.print("10  ");
        System.out.print("11  ");
        System.out.print("12  ");
        System.out.println();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 12; j++) {
                if (i == r && j == c)
                    seats[i][j] = 'X';
                System.out.print(seats[i][j] + "   ");
            }
            System.out.println((char) (65 + i));
        }
    }

    boolean performTaskWithinTimeLimit(int timeLimit) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(() -> {
            // 执行需要计时的任务
            String sc = in.nextLine();
            // 假设这里是一个耗时的任务，我们让它睡眠6秒来模拟超时情况
            // 返回任务结果
            return true;
        });
        executorService.shutdown();
        try {
            return future.get(timeLimit, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return false;
        }
    }

    double sumMoney = 0;

    //选择座位，并计算应该要付多少钱
    boolean selectSeat(int changeMovie, int k) {
        char[][] ch = new char[k][2];
        String se;
        int i = 0;
        zSeat(-1, -1);
        while (true) {
            if (i == k)
                break;
            System.out.println("输入座位编号(先输列号再输行号且不用空格隔开):");
            se = in.nextLine();
            ch[i][0] = se.charAt(0);
            ch[i][1] = se.charAt(1);
            if (manager.film[changeMovie].seat[((int) ch[i][0]) % 65][((int) ch[i][1]) % 48 - 1] == 'X') {
                System.out.println("该座位已被选择，请重新选择：");
            } else {
                zSeat(((int) ch[i][0]) % 65, ((int) ch[i][1]) % 48 - 1);
                i++;
            }
        }
        System.out.println("座位已选择，请在两分钟内完成支付");
        switch (changeMovie) {
            case 0:
                sumMoney = k * 24.5;
                break;
            case 1:
                sumMoney = k * 35.5;
                break;
            case 2:
                sumMoney = k * 26;
                break;
            case 3:
                sumMoney = k * 45;
                break;
            default:
                sumMoney = k * 12;
        }
        System.out.println("你需要支付" + sumMoney + "元");
        boolean result = performTaskWithinTimeLimit(5);
        if (result) {
            System.out.println("支付成功！");
        } else {
            System.out.println("支付超时，已返回！");
            return false;
        }
        for (int b = 0; b < k; b++) {
            manager.film[changeMovie].seat[((int) ch[b][0]) % 65][((int) ch[b][1]) % 48 - 1] = 'X';
            int sx = (((int) ch[b][0]) % 65) * 12 + ((int) ch[b][1]) % 48;
            switch (changeMovie) {
                case 0:
                    manager.film[changeMovie].add_new_information("\"D:\\LYT\\Documents\\seat.txt\"", ch[b][0] + ch[b][1] + " ");
                    break;
                case 1:
                    manager.film[changeMovie].add_new_information("\"D:\\LYT\\Documents\\seat.txt\"", "\n" + ch[b][0] + ch[b][1] + " ");
                    break;
                case 2:
                    manager.film[changeMovie].add_new_information("\"D:\\LYT\\Documents\\seat.txt\"", "\n\n" + ch[b][0] + ch[b][1] + " ");
                    break;
                case 3:
                    manager.film[changeMovie].add_new_information("\"D:\\LYT\\Documents\\seat.txt\"", "\n\n\n" + ch[b][0] + ch[b][1] + " ");
                    break;
            }
        }
        return true;
    }

    //用户注册
    void login() {
        int n = 1;
        boolean[] fag = new boolean[]{false, false};
        while (true) {
            switch (n) {
                case 1:
                    System.out.println("请设置用户名(仅为大小写字母组合长度小于5)：");
                    information[0] = in.nextLine();
                    if (information[0].length() == 0)
                        information[0] = in.nextLine();
                    Pattern formation1 = Pattern.compile("[^A-z]");//格式检验
                    Matcher mata1 = formation1.matcher(information[0]);
                    //匹配用户名
                    if (mata1.find()) {
                        System.out.println("用户名格式错误，重新输入");
                        n = 1;
                        continue;
                    } else {
                        fag[0] = true;
                    }
                case 2:
                    System.out.println("请设置密码(仅为大小写字母与数字组合且长度大于8)：");
                    information[1] = in.nextLine();
                    if (information[1].length() == 0)
                        information[1] = in.nextLine();
                    Pattern formation2 = Pattern.compile("[^A-z,^1-9]");//格式检验
                    Matcher mata2 = formation2.matcher(information[1]);
                    if (mata2.find()) {
                        System.out.println("密码格式错误，重新输入");
                        n = 2;
                        continue;
                    } else {
                        fag[1] = true;
                    }
            }
            if (fag[0] && fag[1]) {
                System.out.println("注册成功");
                userNum++;
                information[2] = "true";
                Date date = new Date();
                long now = System.currentTimeMillis();
                date.setTime(now);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "User ID:" + userNum + "\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Username:" + information[0] + "\\" + information[1] + "\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "User Level: Bronze\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Registration Time:" + format.format(date) + "\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Total Amount Spent:0\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Total Number of Purchases: 0\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Phone Number: 567-890-2134\n");
                userInformations[0].add_new_information("D:\\LYT\\Documents\\javauser.txt", "Email: " + information[0] + "@example.com\n");
                break;
            }
        }
    }

    @Override
    public boolean regis(String n) {
        getUserMation();
        Pattern p1 = Pattern.compile("[^A-z]");//格式检验
        Matcher mata = p1.matcher(n);
        //匹配用户名
        if (!mata.find()) {
            for (int i = 0; i < userNum; i++) {
                Pattern p2 = Pattern.compile(userInformations[i].userInformation[1]);
                Matcher mate1 = p2.matcher(n);
                if (!mate1.find()) {
                    information[0] = n;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    public boolean regis_pass(String p) {
        getUserMation();
        Pattern p1 = Pattern.compile("[^A-z][^1-9]");//格式检验
        Matcher mata = p1.matcher(p);
        //匹配密码
        if (!mata.find()) {
            for (int i = 0; i < userNum; i++) {
                Pattern p2 = Pattern.compile(userInformations[i].userInformation[1]);
                Matcher mate2 = p2.matcher(p);
                if (!mate2.find()) {
                    information[1] = p;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    public void set(){
    }
}