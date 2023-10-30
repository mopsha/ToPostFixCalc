package cse214hw2;

import java.util.Stack;

public class BalancedWord {
    private final String word;

    public BalancedWord(String word){
        if(isBalanced(word)) this.word = word;
        else throw new IllegalArgumentException(String.format("%s is not a balanced word.", word));
    }

    private static boolean isBalanced(String word){
        if(word.isEmpty()) return true;
        char[] exp = word.toCharArray();
        Stack<Character> par = new Stack<>();
        for(char c: exp){
            if(c == '(') par.push(c);
            if(c == ')' && par.isEmpty()) return false;
            if(c == ')') par.pop();
        }
        return par.isEmpty();
    }

    public String getWord(){ return word; }


}
