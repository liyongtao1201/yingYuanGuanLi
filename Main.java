import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, AWTException {
        while (true) {
            System.out.println("-----------------请选择您的身份----------------");
            System.out.println("------------若是要结束进程退出，请输入5----------");
            System.out.println("1.影院管理员，2.影院经理，3.影院前台，4.用户，5.退出");
            Scanner scanner = new Scanner(System.in);
            double input = scanner.nextDouble();
            //对身份选择的输入进行一定的容错处理，当输入的数字不是整数以及不在1~5之间时进入循环重复选择
            while (input < 1 || input > 5 || !(input % (int) input == 0)) {
                System.out.println("身份选择操作输入有误，请重新输入您的选择：");
                input = scanner.nextDouble();
            }
            switch ((int) input) {
                //影院管理员
                case 1 -> {
                    Administrator administrator = new Administrator();
                    administrator.AdministratorMenue();
                }
                //影院经理
                case 2 -> {
                    Manager manager = new Manager();
                    manager.manage_operation();
                }
                //影院前台
                case 3 -> {
                    Reception reception = new Reception();
                    reception.showMenu();
                }
                //用户
                case 4 -> {
                    User user = new User();
                    user.user_operation();
                }
                //退出
                case 5 -> System.exit(0);
            }
        }
    }
}