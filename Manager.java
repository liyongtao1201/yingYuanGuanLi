import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager implements function {
    filmInformation[] film;
    int num = 6;//密码次数限制
    int filmNumb = 5;//电影数量
    int roomNumb = 5;// 影城放映厅的数量
    String filDress = "D:\\LYT\\Documents\\javaMovie.txt";
    String manageName, managePassword;
    Scanner in = new Scanner(System.in);
    //预设经理的账户名和密码
    String[] managerInfor = new String[]{"manager", "manager123", "true"};

    boolean manage_operation() {
        boolean fag = true;
        //经理的登录操作
        if (!manage_lo())
            fag = false;
        //登录成功
        while (fag) {
            System.out.println("请选择要进行的操作：");
            System.out.println("1.影片管理,2.排片管理,3.退出");
            double tb = in.nextDouble();
            while (tb < 1 || tb > 4 || !(tb % (int) tb == 0)) {
                System.out.println("选择输入的格式有误，请重新输入：");
                tb = in.nextDouble();
            }
            switch ((int) tb) {
                //影片管理
                case 1:
                    setFilm(filmNumb);
                    boolean flag = true;
                    while (flag) {
                        System.out.println("1.查询影片的信息，2.添加影片的信息，3.修改、删除影片的信息，4.返回上一级");
                        double res = in.nextDouble();
                        while (res < 1 || res > 4 || !(res % (int) res == 0)) {
                            System.out.println("选择输入的格式有误，请重新输入：");
                            res = in.nextDouble();
                        }
                        switch ((int) res) {
                            //列出所有影片信息
                            case 1:
                                queryFilmInformation(filmNumb);
                                break;
                            //添加影片信息，依次储存到文件D:\LYT\Documents\javaMovie.txt中
                            case 2:
                                String addNewFilm;
                                filmNumb++;
                                setFilm(filmNumb);
                                film[filmNumb - 1].add_new_information(filDress, "\nmovie " + filmNumb + ":\n");
                                System.out.println("请输入电影名称:");
                                Scanner in=new Scanner(System.in);
                                addNewFilm = in.nextLine();
                                film[filmNumb - 1].add_new_information(filDress, "Title: " + addNewFilm + "\n");
                                System.out.println("请输入电影导演:");
                                addNewFilm = in.nextLine();
                                film[filmNumb - 1].add_new_information(filDress, "Director:" + addNewFilm + "\n");
                                System.out.println("请输入电影主演:");
                                addNewFilm = in.nextLine();
                                film[filmNumb - 1].add_new_information(filDress, "Starring: " + addNewFilm + "\n");
                                System.out.println("请输入电影简介:");
                                addNewFilm = in.nextLine();
                                film[filmNumb - 1].add_new_information(filDress, "Plot Summary: " + addNewFilm + "\n");
                                System.out.println("请输入电影时长:");
                                addNewFilm = in.nextLine();
                                film[filmNumb - 1].add_new_information(filDress, "Duration: " + addNewFilm + "\n");
                                break;
                            //修改、删除影片的信息
                            case 3:
                                int movieNumb;//记录当前影片的数量
                                int movieInformation;
                                String newInformation;
                                setFilm(filmNumb);
                                queryFilmInformation(filmNumb);//读取文本内容并打印输出
                                System.out.println("选择需要修改的电影编号：");
                                Scanner In=new Scanner(System.in);
                                movieNumb = In.nextInt();
                                if (movieNumb >= 1 && movieNumb <= filmNumb) {//需要修改的电影是在文件中储存着的
                                    film[movieNumb - 1].putInformation(film[movieNumb - 1].cami_information);
                                    System.out.println("请选择:1.修改电影信息,2.删除电影：\n");
                                    double op = In.nextDouble();
                                    while (op < 1 || op > 2 || !(op % (int) op == 0)) {
                                        System.out.println("选择输入的格式有误，请重新输入：");
                                        op = In.nextDouble();
                                    }
                                    if (op == 1) {
                                        System.out.println("选择需要修改信息的电影的编号：");
                                        movieInformation = In.nextInt();
                                        if (movieInformation >= 1 && movieInformation <= 6) {
                                            System.out.println("请输入新的信息：");
                                            newInformation = In.nextLine();
                                            if (newInformation.length() == 0)
                                                newInformation = In.nextLine();
                                            film[movieNumb - 1].autoReplace(filDress, film[movieNumb - 1].cami_information[movieInformation - 1], newInformation);
                                        } else {
                                            System.out.println("目前还没有安排这场电影");
                                            continue;
                                        }
                                    } else if (op == 2) {
                                        if (movieNumb > 0 && movieNumb <= filmNumb) {
                                            filmNumb--;
                                            delete_film_information(movieNumb, filmNumb);
                                            System.out.println("已经删除该电影.");
                                        } else {
                                            System.out.println("目前还没有安排这场电影");
                                            continue;
                                        }
                                    } else {
                                        System.out.println("目前还没有安排这场电影");
                                        continue;
                                    }
                                } else {
                                    System.out.println("目前还没有安排这场电影");
                                    continue;
                                }
                                continue;
                            //退出返回上一级
                            case 4:
                                flag = false;
                                break;
                        }
                    }
                    break;
                //排片管理
                case 2:
                    //设置影院厅
                    setFilm(roomNumb);
                    boolean gif = true;
                    while (gif) {
                        System.out.println("1.增加场次，2.修改场次，3.删除场次，4.列出所有场次信息，5.返回上一级");
                        double res = in.nextDouble();
                        while (res < 1 || res > 5 || !(res % (int) res == 0)) {
                            System.out.println("选择输入的格式有误，请重新输入：");
                            res = in.nextDouble();
                        }
                        switch ((int) res) {
                            //增加场次
                            case 1:
                                int pl = 0;
                                for (int i = 0; i < roomNumb; i++) {
                                    if (film[i].cami_information[1].equals("Title:")) {
                                        pl = i;
                                        System.out.println("请输入增加场次的信息:");
                                        String titleName = in.nextLine();
                                        film[i].cami_information[1] = titleName;
                                        break;
                                    } else {
                                        pl = roomNumb - 1;
                                    }
                                }
                                if (pl == roomNumb - 1) {
                                    System.out.println("放映厅已满，暂时不能增加场次");
                                }
                                break;
                            //修改场次
                            case 2:
                                int count = 0;
                                for (int i = 0; i < roomNumb; i++) {
                                    if (film[i].cami_information[1].equals("Title:")) {
                                        count = i;
                                        break;
                                    } else {
                                        count = roomNumb;
                                    }
                                }
                                System.out.println("您要修改哪一场次的信息：");
                                double a = in.nextDouble();
                                while (a < 1 || a > count || !(a % (int) a == 0)) {
                                    System.out.println("选择输入的格式有误，请重新输入：");
                                    a = in.nextDouble();
                                }
                                System.out.println("这一场次本来安排好的影片是：");
                                System.out.println(film[(int) a - 1].cami_information[1]);
                                System.out.println("请输入修改内容：");
                                Scanner In = new Scanner(System.in);
                                String Title = In.nextLine();
                                film[(int) a - 1].autoReplace(filDress, film[(int) a - 1].cami_information[1], "Title:" + Title);
                                System.out.println("场次修改成功！");
                                break;
                            //删除场次
                            case 3:
                                int counts = 0;
                                for (int i = 0; i < roomNumb; i++) {
                                    if (film[i].cami_information[1].equals("Title:")) {
                                        counts = i;
                                        break;
                                    } else {
                                        counts = roomNumb - 1;
                                    }
                                }
                                System.out.println("您要删除哪一场影片：");
                                double m = in.nextDouble();
                                while (m < 1 || m > counts || !(m % (int) m == 0)) {
                                    System.out.println("选择输入的格式有误，请重新输入：");
                                    m = in.nextDouble();
                                }
                                delete_film_information((int) m, (int) m);
                                System.out.println("删除场次成功！");
                                break;
                            //列出所有场次信息
                            case 4:
                                for (int i = 0; i < roomNumb; i++) {
                                    System.out.println(film[i].cami_information[0]);
                                    System.out.println(film[i].cami_information[1]);
                                }
                                System.out.println("\n");
                                continue;
                            //返回上一级
                            case 5:
                                gif = false;
                                break;
                        }
                    }
                    break;
                case 3:
                    //退出
                    fag = false;
                    break;
            }
        }
        return true;
    }

    //影院管理员进行登录操作
    boolean manage_lo() {
        int step = 1;
        boolean[] fag = new boolean[]{false, false};
        while (true) {
            switch (step) {
                case 1:
                    System.out.println("请输入用户名：");
                    manageName = in.nextLine();
                    //经理的用户名匹配
                    if (manageName.equals(managerInfor[0])) {
                        if (regis(manageName))
                            fag[0] = true;
                        else {
                            step = 1;
                            continue;
                        }
                    } else {
                        System.out.println("用户名尚未注册！\n请先注册");
                        managerLogins();
                        step = 1;
                        continue;
                    }
                case 2:
                    if (num == 0 || managerInfor[2].equals("false")) {
                        managerInfor[2] = "false";
                        System.out.println("账号已锁定");
                        break;
                    }
                    System.out.println("输入密码：");
                    managePassword = in.nextLine();
                    //密码检验
                    if (regis_pass(managePassword))
                        fag[1] = true;
                    else {
                        System.out.println("还有" + (num - 1) + "次机会");
                        num--;
                        step = 2;
                        continue;
                    }
            }
            if (fag[0] && fag[1]) {
                System.out.println("登录成功");
                return true;
            } else if (managerInfor[2].equals("false")) {
                return false;
            } else System.out.println("错误，请重新输入");
        }
    }

    //注册
    void managerLogins() {
        int n = 1;
        boolean[] fag = new boolean[]{false, false};
        while (true) {
            switch (n) {
                case 1:
                    System.out.println("请设置用户名(仅为大小写字母组合长度小于5)：");
                    managerInfor[0] = in.nextLine();
                    Pattern formation1 = Pattern.compile("[^A-z]");//格式检验
                    Matcher mata1 = formation1.matcher(managerInfor[0]);
                    //匹配用户名
                    if (mata1.find()) {
                        System.out.println("用户名格式错误，重新输入");
                        n = 1;
                        continue;
                    } else {
                        fag[0] = true;
                    }
                    break;

                case 2:
                    System.out.println("请设置密码(仅为大小写字母与数字组合且长度大于8)：");
                    managerInfor[1] = in.nextLine();
                    Pattern formation2 = Pattern.compile("[^A-z,^1-9]");//格式检验
                    Matcher mata2 = formation2.matcher(managerInfor[1]);
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
                managerInfor[2] = "true";
                break;
            }
        }
    }

    //显示电影信息
    void setFilm(int filmNumb) {
        film = new filmInformation[filmNumb];
        for (int i = 0; i < filmNumb; i++) {
            film[i] = new filmInformation();
            film[i].initSeat();
            film[i].setMovieInformation(i + 1);
        }
    }

    //删除电影信息
    void delete_film_information(int star, int end) {
        for (int i = star - 1; i < end; i++) {
            for (int j = 0; j < 6; j++)
                film[i].autoReplace(filDress, film[i].cami_information[j], film[i + 1].cami_information[j]);
        }
    }

    //列出影片的所有信息
    void queryFilmInformation(int t) {
        for (int i = 0; i < t; i++) {
            System.out.println("电影" + (i + 1) + ":");
            film[i].putInformation(film[i].cami_information);
        }
    }

    @Override
    public boolean regis(String n) {
        Pattern p1 = Pattern.compile("[A-z]");//格式检验
        Matcher mata = p1.matcher(n);
        Pattern p2 = Pattern.compile(managerInfor[0]);
        Matcher mate1 = p2.matcher(n);
        //匹配用户名
        if (managerInfor[2].equals("true")) {
            if (mata.find() && mate1.find()) {
                return true;
            } else
                return false;
        } else {
            if (mata.find()) {
                return true;
            } else {
                System.out.println("用户名格式错误，重新输入");
                return false;
            }
        }
    }

    @Override
    public boolean regis_pass(String p) {
        Pattern p1 = Pattern.compile("[A-z,1-9]");//格式检验
        Matcher mata = p1.matcher(p);
        Pattern p2 = Pattern.compile(managerInfor[1]);
        Matcher mate2 = p2.matcher(p);
        //匹配密码
        if (managerInfor[2].equals("true")) {
            if (mata.find() && mate2.find()) {
                return true;
            } else {
                System.out.println("密码格式错误，重新输入");
                return false;
            }
        } else {
            if (mata.find()) {
                return true;
            } else {
                System.out.println("密码格式错误，重新输入");
                return false;
            }
        }
    }

    @Override
    public void set() {
    }
}