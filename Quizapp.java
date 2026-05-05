import java.util.*;
import java.io.*;

// Question class to store each question
class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 🔹 Username input
        System.out.print("Enter your name: ");
        String username = sc.nextLine();

        boolean playAgain = true;

        while (playAgain) {

            ArrayList<Question> quiz = new ArrayList<>();

            // 🔹 Adding questions
            quiz.add(new Question(
                    "What is the capital of India?",
                    new String[]{"Mumbai", "Delhi", "Kolkata", "Chennai"},
                    1
            ));

            quiz.add(new Question(
                    "Which language is used for Android development?",
                    new String[]{"Python", "Java", "C++", "Swift"},
                    1
            ));

            quiz.add(new Question(
                    "What is 5 + 3?",
                    new String[]{"6", "7", "8", "9"},
                    2
            ));

            // 🔹 Shuffle questions randomly
            Collections.shuffle(quiz);

            int score = 0;
            int correctCount = 0;
            int wrongCount = 0;

            ArrayList<String> correctQs = new ArrayList<>();
            ArrayList<String> wrongQs = new ArrayList<>();

            // 🔹 Quiz loop
            for (int i = 0; i < quiz.size(); i++) {
                Question q = quiz.get(i);

                System.out.println("\nQ" + (i + 1) + ": " + q.question);

                // 🔹 Shuffle options while tracking correct answer
                List<String> optionsList = new ArrayList<>(Arrays.asList(q.options));
                String correctOption = q.options[q.correctAnswer];

                Collections.shuffle(optionsList);

                int newCorrectIndex = optionsList.indexOf(correctOption);

                for (int j = 0; j < optionsList.size(); j++) {
                    System.out.println((j + 1) + ". " + optionsList.get(j));
                }

                // 🔹 Timer start
                long startTime = System.currentTimeMillis();

                System.out.print("Enter your answer (1-4): ");
                int answer = sc.nextInt() - 1;

                long endTime = System.currentTimeMillis();
                long timeTaken = (endTime - startTime) / 1000;

                // 🔹 Time limit check (10 seconds)
                if (timeTaken > 10) {
                    System.out.println("⏱ Time up! Question marked wrong.");
                    wrongCount++;
                    wrongQs.add(q.question);
                    continue;
                }

                // 🔹 Answer checking
                if (answer == newCorrectIndex) {
                    System.out.println("Correct!");
                    score++;
                    correctCount++;
                    correctQs.add(q.question);
                } else {
                    System.out.println("Wrong!");
                    wrongCount++;
                    wrongQs.add(q.question);
                }
            }

            // 🔹 Final results
            System.out.println("\n===== RESULT =====");
            System.out.println("Player: " + username);
            System.out.println("Score: " + score + "/" + quiz.size());

            // 🔹 Accuracy calculation
            double accuracy = (score * 100.0) / quiz.size();
            System.out.println("Accuracy: " + accuracy + "%");

            System.out.println("Correct Answers: " + correctCount);
            System.out.println("Wrong Answers: " + wrongCount);

            // 🔹 Show correct questions
            System.out.println("\nQuestions answered correctly:");
            for (String q : correctQs) {
                System.out.println("- " + q);
            }

            // 🔹 Show wrong questions
            System.out.println("\nQuestions answered wrong:");
            for (String q : wrongQs) {
                System.out.println("- " + q);
            }

            // 🔹 Save result to file
            try {
                FileWriter fw = new FileWriter("scores.txt", true);
                fw.write("User: " + username +
                        " | Score: " + score + "/" + quiz.size() +
                        " | Accuracy: " + accuracy + "%\n");
                fw.close();
            } catch (IOException e) {
                System.out.println("Error saving score.");
            }

            // 🔹 Retry option
            System.out.print("\nDo you want to play again? (yes/no): ");
            sc.nextLine(); // clear buffer
            String choice = sc.nextLine();

            if (!choice.equalsIgnoreCase("yes")) {
                playAgain = false;
            }
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
