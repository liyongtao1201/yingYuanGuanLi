import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reception {
    private String name;
    private String password;
    private Scanner in = new Scanner(System.in);
    User recSeat = new User();

    private boolean validateLogin() {
        // 执行登录验证逻辑，根据用户名和密码进行验证
        String adminUsername = "admin";
        String adminPassword = "123456";
        if (name.equals(adminUsername) && password.equals(adminPassword)) {/
            return true;
        } else {
            return false;
        }
    }

    void showMenu() throws FileNotFoundException {
        boolean flag = true;
        if (!login()) {
            flag = false;
        }
        while (flag) {
            System.out.println("===== 影院接待员功能菜单 =====");
            System.out.println("1. 列出所有正在上映影片的信息");
            System.out.println("2. 列出所有影片的场次信息");
            System.out.println("3. 列出指定电影和场次的座位信息");
            System.out.println("4. 售票");
            System.out.println("5. 退出登录");
            System.out.print("请选择操作：\n");
            double choice = in.nextDouble();
            in.nextLine(); // 清空输入缓冲区
            while (choice < 1 || choice > 5 || !(choice % (int) choice == 0)) {
                System.out.println("选择输入的格式有误，请重新输入：");
                choice = in.nextDouble();
            }
            switch ((int) choice) {
                //列出正在上映的影片信息
                case 1:
                    listMovies();
                    break;
                //列出所有影片的场次信息
                case 2:
                    listShowtimes();
                    break;
                //列出指定电影和场次的座位信息
                case 3:
                    showSeatAvailability();
                    break;
                //售票
                case 4:
                    sellTicket();
                    break;
                case 5:
                    System.out.println("再见！\n\n");
                    flag = false; // 退出循环，结束程序
                    break;
                default:
                    System.out.println("请输入有效选项！");
                    break;
            }
        }
    }

    //登录
    public boolean login() {
        System.out.println("=== 登录 ===");
        System.out.print("请输入用户名：");
        name = in.nextLine();
        System.out.print("请输入密码：");
        password = in.nextLine();
        if (validateLogin()) {
            System.out.println("登录成功！");
            return true;
        } else {
            System.out.println("用户名或密码错误！");
            return false;
        }
    }

    //列出正在上映的影片信息
    public void listMovies() {
        System.out.println("=== 正在上映的影片 ===");
        // 获取正在上映的影片列表并输出信息
        recSeat.manager.setFilm(5);
        recSeat.manager.queryFilmInformation(5);
    }

    //列出所有影片的场次信息
    public void listShowtimes() {
        changFilmInformation(5);
    }

    void changFilmInformation(int t) {
        recSeat.manager.setFilm(t);
        for (int i = 0; i < t; i++) {
            System.out.println("电影" + (i + 1) + ":");
            recSeat.manager.film[i].putInformation(recSeat.manager.film[i].cami_information);
        }
    }

    //列出指定电影和场次的座位信息
    public void showSeatAvailability() throws FileNotFoundException {
        int t = 0, k = 0;
        recSeat.manager.setFilm(5);
        System.out.println("=== 座位信息 ===");
        System.out.print("请输入片名：");
        String movieTitle = in.nextLine();
        System.out.print("请输入场次：");
        String movieTimi = in.nextLine();
        for (int i = 0; i < 5; i++) {
            t = i;
            Pattern p1 = Pattern.compile(movieTitle);
            Matcher m1 = p1.matcher(recSeat.manager.film[i].cami_information[1]);
            Pattern p2 = Pattern.compile(movieTimi);
            Matcher m2 = p1.matcher(recSeat.manager.film[i].cami_information[0]);
            if (m1.find() || m2.find()) {
                k = 1;
                System.out.println("影片信息为：");
                recSeat.manager.film[i].putInformation(recSeat.manager.film[i].cami_information);
                System.out.println("座位信息：");
                int seatNum = get_user_seat();
                int col;
                if (seatNum == -1) {
                    recSeat.manager.film[i].changSeat();
                } else {
                    if (seatNum % 12 == 0)
                        col = 11;
                    else col = seatNum % 12;
                    recSeat.manager.film[i].seat[seatNum / 12 - 1][col] = 'X';
                    recSeat.manager.film[i].changSeat();
                    break;
                }
            }
        }
        if (t == 3 && k == 0)
            System.out.println("未查找到该电影");
        // 根据输入的片名和场次，获取座位信息并输出
        // 输出座位信息表格
    }

    int get_user_seat() throws FileNotFoundException {
        int t = 0;
        boolean fag = false;
        try (Scanner sc = new Scanner(new FileReader("D:\\LYT\\Documents\\seat.txt"))) {
            sc.useDelimiter("\\ ");  //分隔符
            while (sc.hasNext()) {   //按分隔符读取字符串
                String str = sc.next();
                t++;
                if (str.equals("X")) {
                    fag = true;
                    break;
                }
            }
        }
        if (fag)
            return t;
        else return -1;
    }

    //售票
    public void sellTicket() {
        System.out.println("=== 售票 ===");
        listShowtimes();
        System.out.print("请输入片名：");
        String movieTitle = in.nextLine();
        for (int i = 0; i < 4; i++) {
            Pattern fo = Pattern.compile(movieTitle);//格式检验
            Matcher ma = fo.matcher(recSeat.manager.film[i].cami_information[1]);
            if (ma.find()) {
                System.out.println("影片信息为：");
                recSeat.manager.film[i].putInformation(recSeat.manager.film[i].cami_information);
                recSeat.manager.film[i].changSeat();
                System.out.println("请输入购买票数:");
                int t = in.nextInt();
                recSeat.selectSeat(i, t);
                recSeat.manager.film[i].changSeat();
                System.out.print("请输入用户名/手机号：");
                Scanner in = new Scanner(System.in);
                String usernameOrPhoneNumber = in.nextLine();
                Pattern formation1 = Pattern.compile("[0-9]");//格式检验
                Matcher mata1 = formation1.matcher(usernameOrPhoneNumber);
                double paymentAmount;
                if (mata1.find())
                    paymentAmount = 100;
                else paymentAmount = 200;
                System.out.print("请输入支付金额：");
                double paymentAmount1 = in.nextDouble();
                while (true) {
                    if (paymentAmount1 >= paymentAmount ) {
                        break;
                    } else {
                        System.out.println("金额不够，请重新支付");
                        break;
                    }
                }
                // 根据用户的级别计算折扣，生成电影票信息并输出
                String ticketID = generateTicketID();
                System.out.println("电影票信息如下：");
                System.out.println("电子ID编号：" + ticketID);
                System.out.println("用户：" + usernameOrPhoneNumber);
                System.out.println("支付金额：" + paymentAmount1 + "元");
                break;
            }
        }
    }

    //输出电影票信息
    private String generateTicketID() {
        int p = 0001;
        Date date = new Date();
        long now = System.currentTimeMillis();
        date.setTime(now);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return "您的票号是：" + format.format(date) + "-" + p;
    }
}