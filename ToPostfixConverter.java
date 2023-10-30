package cse214hw2;
import java.util.Stack;
public class ToPostfixConverter implements Converter {
    public String convert(ArithmeticExpression expression) {
        if(expression.getExpression().isEmpty()) return "";
        Stack<Character> stack = new Stack<>();
        String token;
        String postfix = "";
        String e = expression.getExpression();
        e = e.replaceAll("\\s", "");
        int s;
        if(Operator.isOperator(e.charAt(0))){
            postfix += e.charAt(0);
            s = 1;
        }else{
            s = 0;
        }
        for(int i = s; i < e.length(); i++){
            if(e.charAt(i) == Operator.LEFT_PARENTHESIS.getSymbol()) stack.push(e.charAt(i));
            else if(e.charAt(i) == Operator.RIGHT_PARENTHESIS.getSymbol()){
                while(stack.peek() != Operator.LEFT_PARENTHESIS.getSymbol()){
                    postfix += (stack.pop() + " ");
                }
                stack.pop();
            }
            else if(Operator.isOperator(e.charAt(i)) && !Operator.isOperator(e.charAt(i-1))){
                if(stack.isEmpty() || stack.peek() == Operator.LEFT_PARENTHESIS.getSymbol() || Operator.of(e.charAt(i)).getRank() < Operator.of(stack.peek()).getRank()) stack.push(e.charAt(i));
                else if(Operator.of(e.charAt(i)).getRank() == Operator.of(stack.peek()).getRank()){
                    postfix += (stack.pop() + " ");
                    stack.push(e.charAt(i));
                }
                else if(Operator.of(e.charAt(i)).getRank() > Operator.of(stack.peek()).getRank()){
                    while(!stack.isEmpty() && Operator.of(e.charAt(i)).getRank() > Operator.of(stack.peek()).getRank()){
                        postfix += (stack.pop() + " ");
                    }
                    if(stack.isEmpty() || stack.peek() == Operator.LEFT_PARENTHESIS.getSymbol() || Operator.of(e.charAt(i)).getRank() < Operator.of(stack.peek()).getRank()) stack.push(e.charAt(i));
                    else if(Operator.of(e.charAt(i)).getRank() == Operator.of(stack.peek()).getRank()){
                        postfix += (stack.pop() + " ");
                        stack.push(e.charAt(i));
                    }
                }
            }
            else if(Operator.isOperator(e.charAt(i))){
                token = e.charAt(i) + nextToken(e,i+1);
                if(token.length() > 1){
                    i += (token.length() - 1);
                }
                TokenBuilder tokenBuilder = new TokenBuilder();
                for(int c = 0; c < token.length(); c++){
                    tokenBuilder.append(token.charAt(c));
                    if(token.charAt(i) == '.'){
                        tokenBuilder.append(token.charAt(c+1));
                        tokenBuilder.append(token.charAt(c+2));
                        break;
                    }
                }
                postfix += tokenBuilder.build() + " ";
            }
            else{
                token = nextToken(e, i);
                if(token.length() > 1){
                    i += (token.length() - 1);
                }
                TokenBuilder tokenBuilder = new TokenBuilder();
                for(int c = 0; c < token.length(); c++){
                    tokenBuilder.append(token.charAt(c));
                    if(token.charAt(c) == '.'){
                        tokenBuilder.append(token.charAt(c+1));
                        tokenBuilder.append(token.charAt(c+2));
                        break;
                    }
                }
                postfix += tokenBuilder.build() + " ";
            }
            token = "";
        }
        while(!stack.isEmpty()) postfix += (stack.pop() + " ");
        return postfix;
    }

    public String nextToken(String s, int start) {
        int end = start;
        while(!Operator.isOperator(s.charAt(end)) && s.charAt(end) != Operator.LEFT_PARENTHESIS.getSymbol() && s.charAt(end) != Operator.RIGHT_PARENTHESIS.getSymbol()){
            if(s.length() == end + 1)return s.substring(start);
            end++;
        }
        return s.substring(start, end);
    }

    public boolean isOperand(String s) {
        return (s.length() != 1 || !Operator.isOperator(s)) && !s.isEmpty();
    }
}
