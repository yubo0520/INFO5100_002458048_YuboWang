import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Student {
    protected String name;
    protected ArrayList<Integer> quizScores;

    public Student(String name) {
        this.name = name;
        this.quizScores = new ArrayList<>(); 
        generateQuizScores(); 
    }

    // randomly generate quiz scores
    private void generateQuizScores() {
        Random random = new Random(); 
        for (int i = 0; i < 15; i++) {
            quizScores.add(random.nextInt(11)); 
        }
    }

    // calculate average quiz score
    public double calculateAverageQuizScore() {  
        int sum = 0;
        for (int score : quizScores) {
            sum += score;
        }
        return (double) sum / quizScores.size(); 
    }

    // quiz scores in an ascending order
    public void printSortedQuizScores() {
        Collections.sort(quizScores); 
        System.out.println("students: " + name + ", quiz scores: " + quizScores); 
    }

    public String getName() {
        return name;
    }
}

//  PartTimeStudent child class
class PartTimeStudent extends Student {
    public PartTimeStudent(String name) {
        super(name);  
    }
}

// FullTimeStudent child class 
class FullTimeStudent extends Student {
    private int examScore1; //add extra attributes 
    private int examScore2;

    public FullTimeStudent(String name) {
        super(name); // call constructor from parent class
        generateExamScores(); 
    }

    // randomly generate exam scores
    private void generateExamScores() { 
        Random random = new Random();
        examScore1 = random.nextInt(101); 
        examScore2 = random.nextInt(101);
    }
    public void printExamScores() {
        System.out.println("student: " + name + ", exam scores: " + examScore1 + ", " + examScore2);
    }
}
