import javax.lang.model.type.NoType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.lang.reflect.Array;
import java.util.*;

public class HuntTheWumpus {

  public  static  Integer[] rooms ={
      0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20  };

  public static Integer[][]links ={
      {1,7,4},{0,9,2},{1,11,3},{2,13,4},{2,13,4}, {3,0,5},
      {4,14,6},{5,16,7},{6,0,8},{7,17,9},{0,1,10}, {9,18,11},
      {10,2,12},{11,19,13},{12,3,14},{13,5,15},{14,19,16},
      {15,6,17},{16,8,18},{17,10,19},{18,12,15}
  };

  public static String WUMPUS = "Wumpus";
  public static String BAT = "Bat";
  public static String PIT = "Pit";
  public static String NOTHING = "Nothing";

  public static ArrayList<String> hazards = new ArrayList<>();
  public static HashMap<String,String> hazardMessages = new HashMap<>();

  public static Random random =new Random();
  public static Scanner scanner = new Scanner(System.in);

  public static boolean gameOver = true;

  public static int currentRoom;
  public static int arrowCount;
  public static int wumpusRoom;

  public static void main(String[] args) {
    showIntro();

    intitalizeStaticValues();

    while(true) {
      intitalPlayVariables();

      setupHazards();

      delay(1000L);
      System.out.println("\n...");
      delay(1000L);
      System.out.println("\n...");
      delay(1000L);
      System.out.println("동굴에 들어왔습니다... \n ");
      delay(1000L);
      System.out.println("\"섬뜩한 곳이군.\"");
      delay(1000L);

      game();

      selectReplay();
    }
  }

  private static void showIntro() {
    try {
      FileInputStream inputStream = new FileInputStream("src/intro.txt");
      Scanner scanner = new Scanner(inputStream);
      while (scanner.hasNextLine()){
        System.out.println(scanner.nextLine());
        delay(500L);
      }
    }catch (FileNotFoundException e){
      System.out.println("트로를 읽을 수 없어 생략합니다. ");
    }
  }
  private static void intitalPlayVariables(){
    gameOver =false;
    currentRoom = random.nextInt(rooms.length);
    arrowCount = 5;
  }
  public static void intitalizeStaticValues(){
    hazardMessages.put(WUMPUS,"\"어디선가 끔찍한 냄새가 난다. \"");
    hazardMessages.put(BAT,"\"어디선가 부스럭 거리는 소리가 난다. \"");
    hazardMessages.put(PIT,"\"바림이 부는 소리가 들리는 거 같다. \"");
    hazardMessages.put(NOTHING,"\"저 방에는 아무 것도 없는 거 같다. \"");
  }


  public static void setupHazards(){
    if(hazards.size() == 0){ //프로그램이 막 실행됨
      for(int i=0; i< rooms.length; i= i+ 1){
        hazards.add(NOTHING);
      }
    }
    for (int i=0; i <rooms.length; i =i +1){ //게임이 재시작되었을때 모든 위험요소를 제거
      hazards.set(i,NOTHING);
    }
    String[]ordinals = {WUMPUS,BAT,BAT,BAT,PIT,PIT};

    for(String hazard : ordinals){
      int room;

      while(true){
        room = random.nextInt(rooms.length);

        if(isTooCloseWithPlayer(room)){
          continue;
        }
        if(hazards.get(room).equals(NOTHING)){
          break;
        }
      }
      hazards.set(room, hazard);

      if(hazard.equals(WUMPUS)){
        wumpusRoom = room;
      }
    }
  }
  public static boolean isTooCloseWithPlayer(int room){
    if(currentRoom == room){
      return true;
    }
    List<Integer> linkedRooms = Arrays.asList(links[currentRoom]);
    if(linkedRooms.contains(room)){
      return true;
    }
    return false;
  }
  public static void move(int room){
    currentRoom = room;
    System.out.println(currentRoom +"번 방으로 이동했습니다");

    String hazard = hazards.get(currentRoom);

    delay(1000L);

    if(hazard.equals(WUMPUS)){
      System.out.println("\"으악\"");
      delay(300L);
      System.out.println("움퍼스에게 잡아먹혔습니다");
      gameOver = true;
    }

    else if(hazard.equals(PIT)){
      System.out.println("\"으악\"");
      delay(1000L);
      System.out.println("쿵!");
      delay(300L);
      System.out.println("당신은 구멍이에 빠졌습니다 .");
      delay(300L);
      System.out.println("더 이상 움퍼스를 사냥할 수 없습니다");
      gameOver = true;

    } else if (hazard.equals((BAT))) {
      System.out.println("쿵!");
      delay(300L);
      System.out.println("박쥐가 당신을 다른 방으로 이동시켰습니다");

      do{
        currentRoom = random.nextInt(rooms.length);
      }while(hazards.get(currentRoom).equals(BAT));

      hazards.set(room,NOTHING);

      while(true){
        int newBatRoom = random.nextInt(rooms.length);

        if(newBatRoom == currentRoom){
          continue;
        }
        if(hazards.get(newBatRoom).equals(NOTHING)) {
          hazards.set(newBatRoom,BAT);
          break;
        }
      }
      move(currentRoom);

    }
    else{
      List<Integer> nextRooms = Arrays.asList(links[currentRoom]);

      System.out.println("\n 연결되어 있는 통로를 살핀다");
      for(int nextRoom : nextRooms){
        delay(700L);
        String hazardAround = hazards.get(nextRoom);
        System.out.println(hazardMessages.get(hazardAround));
      }
      Collections.shuffle(nextRooms);

    }
  }
  public static void shoot(int room){
    arrowCount = arrowCount -1;

    delay(1000L);
    System.out.println("화살 발사");
    delay(300L);

    if (hazards.get(room).equals(WUMPUS)){
      System.out.println("슝");
      delay(100L);
      System.out.println("\"푹!\"");
      delay(1000L);
      System.out.println("축하합니다. 움퍼스가 죽었습니다");
      gameOver =true;
    }
    else{
      System.out.println("(. . . )");
      delay(1000L);
      System.out.println("\"화살만 낭비 했군요\"");

      if(arrowCount == 0){
        delay(300L);
        System.out.println("\"이런 화살이 얼마 남지 않았네요\"");
        delay(300L);
        System.out.println("움퍼스 사냥에 실패했습니다");
        gameOver =true;

      }
      else if(random.nextInt(4) !=0){
        System.out.println("움퍼스가 눈을 떴습니다");
        delay(1000L);

        Integer[] nextRooms = links[wumpusRoom];

        int nextRoom = nextRooms[random.nextInt(3)];

        if(hazards.get(nextRoom).equals(NOTHING)){
          hazards.set(wumpusRoom, NOTHING);
          hazards.set(nextRoom, WUMPUS);
          wumpusRoom = nextRoom;
        }

        if(wumpusRoom == currentRoom) {
          System.out.println("\"으악\"");
          delay(500L);
          System.out.println("움퍼스에게 잡아먹혔습니다");
          gameOver = true;
        }
        else if(wumpusRoom == nextRoom){
          System.out.println("움퍼스가 도망갔습니다");
        }
      }
    }
  }
  private static void selectReplay(){
    System.out.println("게임이 끝났습니다. 다시 플레이 하시겠습니까?");

    while (true){
      System.out.println("(0: 종료, 1: 다시 플레이)");
      String action = scanner.nextLine();

      if(action.equals("0")){
        System.out.println("게임을 종료합니다");
        System.exit(0);
      }

      else if(action.equals("1")){
        System.out.println("게임을 다시 플레이합니다");
        break;
      }
      else{
        System.out.println("잘못된 입력입니다");
      }
    }
  }
  private static void game(){
    while(gameOver == false){
      System.out.println("\n 당신은 "+currentRoom +"번 방에 있습니다");
      System.out.println("행동을 선택하세요");
      System.out.println("1 이동 ");
      System.out.println("2 화살쏘기");
      System.out.println("3 통로 목록");
      System.out.println("0 플레이 종료");

      String action = scanner.nextLine();

      if (action.equals(("1"))) {
        Integer[] nextRooms = links[currentRoom];

        System.out.println("\n 번 방으로 이동하겠습니까?");
        System.out.println(Arrays.toString(nextRooms));

        String nextRoomInput = scanner.nextLine();
        int nextRoom = parseIntegerOrNegative(nextRoomInput);

        if(Arrays.asList(nextRooms).contains(nextRoom)){
          move(nextRoom);
        }else {
          System.out.println("선택한 방으로는 이동할 수 없습니다");
        }
      }
      else if(action. equals("2")){
        Integer[] nextRooms = links[currentRoom];

        System.out.println("\n번 방에 화살을 쏘시겠습니까?");
        System.out.println(Arrays.toString(nextRooms));

        String targetRoomInput = scanner.nextLine();
        int targetRoom = parseIntegerOrNegative(targetRoomInput);

        if(Arrays.asList(nextRooms).contains(targetRoom)){
          shoot(targetRoom);
        }else {
          System.out.println("선택한 방에는 화살을 쏠 수가 없습니다");
        }

      } else if (action.equals(("3"))) {
        System.out.println("현재 방에 연결된 통로는 다음과 같습니다");
        System.out.println(Arrays.asList(links[currentRoom]));
      }
      else if (action.equals("0")){
        System.out.println("게임 플레이를 종료합니다");
        gameOver = true;
      }
      else {
        System.out.println("잘못된 입력입니다");
      }
    }
  }
  public static int parseIntegerOrNegative(String input){
    try{
      return Integer.parseInt(input);
    }catch (NumberFormatException e){
      return -1;
    }
  }
  public static void delay(Long ms){
    try{
      Thread.sleep(ms);
    }catch(InterruptedException ignored){
    }
  }
}


