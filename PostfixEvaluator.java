package cse214hw2;
import java.text.DecimalFormat;
import java.util.Stack;
public class PostfixEvaluator implements Evaluator {
    public double evaluate(String expressionString) {
        Stack<Double> stack = new Stack<>();
        String token = "";
        double sOp, fOp;
        DecimalFormat df = new DecimalFormat("#.##");
        for(int i = 0; i < expressionString.length(); i++){
            if(expressionString.charAt(i) != ' '){
                while(expressionString.charAt(i) != ' '){
                    token += expressionString.charAt(i);
                    i++;
                }
                if(Operator.isOperator(token) && token.length() == 1){
                    sOp = stack.pop();
                    fOp = stack.pop();
                    if(Operator.of(token) == Operator.ADDITION) stack.push(Double.parseDouble(df.format(fOp + sOp)));
                    else if(Operator.of(token) == Operator.SUBTRACTION) stack.push(Double.parseDouble(df.format(fOp - sOp)));
                    else if(Operator.of(token) == Operator.MULTIPLICATION) stack.push(Double.parseDouble(df.format(fOp * sOp)));
                    else if(Operator.of(token) == Operator.DIVISION) stack.push(Double.parseDouble(df.format(fOp / sOp)));
                }
                else if(token.length() > 1 || !Operator.isOperator(token.charAt(0))) stack.push(Double.parseDouble(token));
            }
            token = "";
        }
        double answer = stack.pop();
        answer = Double.parseDouble(df.format(answer));
        return answer;
    }
}