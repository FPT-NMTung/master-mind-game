package org.example;

import org.example.Answers;

import java.util.*;
public class MasterMind {
    private static int defaultmaxAllowTime = 8;
    private static int QUIZZ_LENGTH = 4;
    private static char allCorrect='*';
    private static char numberCorrect='!';
    public enum State {PROGRESS, LOST, WIN };
    private String hiddenNumber;
    private State gameState;
    private int maxAllowTime;
    private List<Answers> attemptList;
    private String correctAnswer = new String();

    public MasterMind() {
        gameState = State.PROGRESS;
        hiddenNumber = generateHiddenNumber();
        maxAllowTime = defaultmaxAllowTime;

        for(int i=1;i<= QUIZZ_LENGTH;i++){
            correctAnswer = correctAnswer + allCorrect;
        }
        attemptList = new ArrayList<Answers>();
    }

    public String getHiddenNumber(){
        return hiddenNumber;
    }

    public boolean isProgress(){
        return gameState==State.PROGRESS;
    }

    public boolean isGameOver(){
        return gameState==State.LOST;
    }

    public boolean isWon(){
        return gameState==State.WIN;
    }
    private String generateHiddenNumber(){
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));
    }

    public String getResult(){
        boolean isEmpty = this.attemptList.isEmpty();
        if (isEmpty) {
            return "";
        }
        return this.attemptList.get(this.attemptList.size() - 1).getResult();
    }

    public void evaluateResult(Answers Ans){
        if (Ans.getAnswer().length() == 4) {
            Ans.setResult(matchResult(Ans.getAnswer()));
            this.attemptList.add(Ans);
        }
        changeGameStatus(Ans);
    }

    private void changeGameStatus(Answers paraAttmpt){
        if (attemptList.size() < maxAllowTime){
            if (paraAttmpt.getResult().equals("****")) {
                gameState=State.WIN;
            }
        } else {
            gameState = State.LOST;
        }
    }

    public String matchResult(String inputNumber){
        char[] inChar;
        char[] hidChar;
        String rtnValue = new String();
        inChar = inputNumber.toCharArray();
        hidChar = hiddenNumber.toCharArray();

        for (int i=0;i < hiddenNumber.length();i++){
            boolean isMatch = inChar[i] == hidChar[i];
            if (isMatch){
                rtnValue= rtnValue + this.allCorrect;
            } else {
                for (int j=0;j < hiddenNumber.length();j++){
                    boolean isContain = inChar[i] == hidChar[j];
                    if (isContain) {
                        rtnValue=rtnValue + this.numberCorrect;
                        break;
                    }
                }
            }
        }
        return rtnValue;
    }
}

