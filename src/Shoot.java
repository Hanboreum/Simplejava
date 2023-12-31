import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Shoot {
  public static int[] rooms = {0,1,2,3};
  public static int [][] links = {{1,2,3,},{2,3,0},{3,0,1},{0,1,2}};

  public static String BAT = "Bat";
  public static String PIT = "Pit";
  public static String WUMPUS = "Wumpus";
  public static String NOTHING = "Nothing";
  public static String[] hazards = {NOTHING,BAT,PIT,WUMPUS};
  public static int currentRoom = 0;
  public static int wumpusRoom = 3; //움퍼스의 현 위치
  public static int arrowCount = 5;//화살
  public static Random random = new Random();
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while(true) {
      System.out.println("지금" + currentRoom + "번 방에 있습니다");
      int[] nextRooms = links[currentRoom];

      System.out.println("어느 방에 화살을 쏘시겠습니까?");
      System.out.println(Arrays.toString(nextRooms));

      int roomNumber = scanner.nextInt();

      shoot(roomNumber);
    }

  }
  public static void shoot(int room){
    if(hazards[room].equals(WUMPUS)){
      System.out.println("움퍼스를 죽였습니다");
    }else {
      arrowCount = arrowCount -1;

      if(arrowCount ==0){
        System.out.println("움퍼스 사냥에 실패했습니다");
      }else if (random.nextInt(4) !=0){
        System.out.println("움퍼스가 깨어났습니다");

        int[]nextRooms = links[wumpusRoom];
        int nextRoom = nextRooms[random.nextInt(3)];

        if (hazards[nextRoom].equals(NOTHING)){
          hazards[wumpusRoom] = NOTHING;
          hazards[nextRoom] = WUMPUS;
          wumpusRoom = nextRoom;
        }
        if(wumpusRoom == currentRoom){
          System.out.println("움퍼스에게 잡아먹혔습니다");
          System.out.println("움퍼스 사냥에 실패했습니다");
          //TODO.game over
        }else if (wumpusRoom == nextRoom){
          System.out.println(" 움퍼스가 도망갔습니다");
        }
      }
    }
  }

}
