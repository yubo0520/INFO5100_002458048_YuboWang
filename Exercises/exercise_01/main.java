public class main {
  public static void main(String[] args) {
      // create a session
      Session session = new Session();

      // display the output as required by the task
      session.printAvgQuizScore();
      System.out.println("---------------------------------------");

      session.printScoresInAscendingOrder();
      System.out.println("---------------------------------------");

      session.printPartTimeStudents();
      System.out.println("---------------------------------------");

      session.printExamScores();
  }
}
