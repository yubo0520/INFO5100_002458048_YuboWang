import java.util.ArrayList;
import java.util.Random;

class Session {
  private ArrayList<Student> students;

  public Session() {
      this.students = new ArrayList<>();
      populateStudents();  
  }

  private void populateStudents() {
      Random random = new Random();
      for (int i = 0; i < 20; i++) {
          String studentName = "Student" + (i + 1);
          if (random.nextInt(2) == 0) {  // randomly add part time/full time students
              students.add(new PartTimeStudent(studentName));
          } else {
              students.add(new FullTimeStudent(studentName));
          }
      }
  }

  //print the average quiz score 
  public void printAvgQuizScore() {
      for (Student student : students) {
          double avgScore = student.calculateAverageQuizScore();
          System.out.println(student.getName() + " average quiz score: " + String.format("%.1f", avgScore));
      }
  }

  public void printScoresInAscendingOrder() {
      for (Student student : students) {
          student.printSortedQuizScores();
      }
  }

  public void printPartTimeStudents() {
      System.out.println("part-time students: ");
      for (Student student : students) {
          if (student instanceof PartTimeStudent) {
              System.out.println(student.getName());
          }
      }
  }

  public void printExamScores() {
      System.out.println("exam scores of full time students: ");
      for (Student student : students) {
          if (student instanceof FullTimeStudent) {
              ((FullTimeStudent) student).printExamScores(); 
          }
      }
  }
}

