import java.util.Random;
import java.util.Scanner;

public class RandomGame {
  public  static int randomnum;
  public static Scanner scanner =new Scanner(System.in);

  public static void main(String[] args) {
    Random random = new Random();
    randomnum = random.nextInt(256);

    int attempt =0;
    while(attempt <8){
      boolean isCorrect = play();

      if(isCorrect){
        break;
      }
      attempt = attempt + 1;
      System.out.println(attempt +"회 시도 하였음");
    }
    System.out.println("게임 종료");
  }

  public  static boolean play(){
    System.out.println("숫자를 입력하세요");
    int input = scanner.nextInt();

    if(input == randomnum){
      System.out.println(" 정답");
      return true;
    } else if (input >randomnum) {
      System.out.println("더 작은 수를 입력하세요");

    }else{
      System.out.println("더 큰 수");
    }

    return  false;
  }
}

