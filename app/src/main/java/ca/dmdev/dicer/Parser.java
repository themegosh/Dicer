package ca.dmdev.dicer;

/**
 * Created by mathe_000 on 2016-01-09.
 */
/*
public class Parser {

    public void Parse(String sequence){
        sequence = sequence.toLowerCase();
        sequence = sequence.replace("+-", "-");

        Object parseValues = new ParseValues().Init();

        Object dice = new DiceExpression();

        for (int i = 0; i < sequence.Length; ++i)
        {
            char c = sequence.charAt(i);

            if (Character.isDigit(c))
            {
                parseValues.Constant += c;
            }
            else if (c == '*')
            {
                parseValues.Scalar *= int.Parse(parseValues.Constant);
                parseValues.Constant = "";
            }
            else if (c == 'd')
            {
                if (parseValues.Constant == "")
                    parseValues.Constant = "1";
                parseValues.Multiplicity = int.Parse(parseValues.Constant);
                parseValues.Constant = "";
            }
            else if (c == 'k')
            {
                string chooseAccum = "";
                while (i + 1 < cleanExpression.Length && char.IsDigit(cleanExpression[i + 1]))
                {
                    chooseAccum += cleanExpression[i + 1];
                    ++i;
                }
                parseValues.Choose = int.Parse(chooseAccum);
            }
            else if (c == '+')
            {
                Append(dice, parseValues);
                parseValues = new ParseValues().Init();
            }
            else if (c == '-')
            {
                Append(dice, parseValues);
                parseValues = new ParseValues().Init();
                parseValues.Scalar = -1;
            }
            else
            {
                throw new ArgumentException("Invalid character in dice expression", "expression");
            }
        }
        Append(dice, parseValues);

        return dice;
    }
}
*/