import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class HazardInRooms {

  public static void main(String[] args) {
    int[] rooms = {0,1,2,3};

    String BAT = "Bat";
    String PIT = "Pit";
    String WUMPUS = "Wumpus";
    String NOTHING = "nothing";

    String[] harzards = {NOTHING,BAT,PIT,WUMPUS};

    HashMap<String, String> harzardMessages = new HashMap<>();

    harzardMessages.put(WUMPUS, "\"이디선가 끔찍한 소리가 들린다. \"");
    harzardMessages.put(BAT, "\"어디선가 부스럭 거리는 소리가 들린다\"");
    harzardMessages.put(PIT, "\"바람이 부는 소리가 들리는 거 같다\"");
    harzardMessages.put(NOTHING, "\"저 방에는 아무것도 없는 거 같다\"");

    int [][] links = {{1,2,3},{2,3,0},{3,0,1},{0,1,2}};
    int currentRoom = 0;

    Scanner scanner = new Scanner(System.in);

    while(true){
      System.out.println("지금" + currentRoom + "번 방에 있습니다");
      int[] nextRooms = links[currentRoom];

      for (int nextRoom : nextRooms){
        String hazard = harzards[nextRoom];
        String message = harzardMessages.get(hazard);
        System.out.println(message);
      }

      System.out.println("다음 번호 중에서 이동할 방 번호를 입력해주세요");
      System.out.println(Arrays.toString(nextRooms));
      int roomNumber = scanner.nextInt();

      currentRoom = roomNumber;
    }

  }
}
