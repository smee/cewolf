
package de.laures.cewolf.util;

import java.util.HashMap;
import java.util.Map;

public class Expr {

/*  Simple recursive descent expression evaluator.
		written in C by Guy Laden

		Changes by Ulf Dittmer:
		May 1997: ported to Oberon and small enhancements
		November 1997: ported to Java
		May 1999: now accepts an arbitrary number of variables
		May 1999: added cond() and % functions, PI and E constants
		June 2001: added min, max, tan, asin and acos functions
		March 2002: enhanced min and max to accept an arbitrary number of arguments
					added AND, OR and NOT operators; AND and OR accept an arbitrary number of arguments -
					a number whose absolute value is below 1.0e-6 is considered to be logically false, otherwise true

	Usage:
		from the command line:
			java Expr "(x+1) * (y%4) + cond(x<>y, 10, 0)" x 2 y 10
				prints 16.0

		from a program:
			Map vars = new HashMap();
			vars.put("x", new Double(2));
			vars.put("y", new Double(10.0));
			double result = Expr.eval("(x+1) * (y%4) + cond(x<>y, 10, 0)", vars);

	Known functions/operators:
		+  -  *  /  ^  %  sin()  cos()  tan()  asin()  acos()  atan()
		exp()  ln()  sqrt()  cond(,,)  min()  max()

		cond works somewhat like an if/then/else, or rather like the "a ? b : c"
		construct in C and Java. The 1st argument should evaluate to true or
		false (the numerical comparison operators can be used); if it's true,
		cond evaluates to the 2nd argument, otherwise to the 3rd.

	Variables can be upper- or lowercase; they override functions of the same name.
	"PI" and "E" are predefined to be the corresponding mathematical constants.

	Here's an EBNF of the expressions this class understands:

	expr:	 ['-' | '+' ] term {'+' term | '-' term}.
	term:	 factor {'*' factor | '/' factor | '%' factor}.
	factor:	 primary {'^' factor}.
	compExpr:	 ['-' | '+' ] term  compOper term
	compOper:	'=' | '<' | '>' | '<>' | '<=' | '>='
	primary: number | '(' expr ')' | 'sin' '(' expr ')' | 'cos' '(' expr ')' | 'tan' '(' expr ')' |
			'exp' '(' expr ')' | 'ln' '(' expr ')' | 'atan' '(' expr ')'| 'acos' '(' expr ')' |
			'sqrt' '(' expr ')' | 'cond' '(' compExpr ',' expr ',' expr ')' | 'asin' '(' expr ')'
			'min' '(' expr (',' expr) * ')' | 'max' '(' expr (',' expr) * ')' |
			'and' '(' expr (',' expr) * ')' | 'or' '(' expr (',' expr) * ')' | 'not' '(' expr ')' | variable 
	variable:	[a-zA-Z]+
	number:	intnumber | realnumber.
	realnumber:	[ intnumber ] . [ intnumber ].
	intnumber:	digit {digit}.
	digit:	0 | 1 | 2 | 3 | ... | 9
*/

	final static int
			MULT = 1,		DIVIDED = 2,	PLUS = 3,		MINUS = 4,
			LBRAK = 5,		RBRAK = 6,		POW = 7,		NUMBER = 8,
			SIN = 9,		COS = 10,		EXP = 11,		LN = 12,
			ATAN = 13,		SQRT = 14,		LAST = 15,		COND = 16,
			COMMA = 17,		LT = 18,		GT = 19,		EQ = 20,
			NE = 21,		LE = 22,		GE = 23,		MOD = 24,
			MIN = 25,		MAX = 26,		ASIN = 27,		ACOS = 28,
			TAN = 29,		AND = 30,		OR = 31,		NOT = 32;

	char c;		/* last character read from input */
	String str,
			ident;	/* scanned identifiers are stored here */
	Map vars;	/* the values of the variables */
	double num;	/* scanned numbers are stored here */
	int i,
		token;		/* invariant throughout: token is the last token scanned */

/* Scanner */

	void getIdent () {
		ident = "";
		do {
			ident += String.valueOf(c);
			if (++i < str.length()) c = str.charAt(i);
		} while ((i < str.length()) && Character.isLetter(c));
	}

	int getCompOp () {
		char c1 = c, c2 = ' ';
		if (++i < str.length()) c = str.charAt(i);
		c2 = c;
		if ((c=='=') || (c=='<') || (c=='>'))
			if (++i < str.length()) c = str.charAt(i);

		if ((c1 == '<') && (c2 == '=')) return LE;
		else if ((c1 == '>') && (c2 == '=')) return GE;
		else if (c1 == '=') return EQ;
		else if ((c1 == '<') && (c2 == '>')) return NE;
		else if (c1 == '<') return LT;
		else if (c1 == '>') return GT;
		else throw new RuntimeException("unknown operator: " + c1 + c2);
	}

	double getNum () throws NumberFormatException {
		StringBuffer s = new StringBuffer("");

		do {
			s.append(String.valueOf(c));
			if (++i < str.length()) c = str.charAt(i);
		} while ((i< str.length()) && (Character.isDigit(c) || (c == '.')));
		return Double.valueOf(s.toString()).doubleValue();
	}

	int getTok () {
		/* pre: c is the last character read */
		int t;

		while ((i < str.length()) && (c == ' ')) {
			if (++i < str.length()) c = str.charAt(i);
		}
		if (i == str.length())
			t = LAST;
		else if (Character.isDigit(c) || (c == '.')) {
			num = getNum();	t = NUMBER;
		} else if ((c == '<') || (c == '=') || (c == '>')) {
			t = getCompOp();
		} else if (Character.isLetter(Character.toUpperCase(c))) {
			getIdent();
				// variable names override functions of same name
			Double obj = (Double) vars.get(ident.toLowerCase());
			if (obj != null) {
				num = obj.doubleValue();
				t = NUMBER;
			} else if (ident.equalsIgnoreCase("PI")) {
				num = Math.PI;	t = NUMBER;
			} else if (ident.equalsIgnoreCase("E")) {
				num = Math.E;	t = NUMBER;
			} else if (ident.equalsIgnoreCase("SIN"))
				t = SIN;
			else if (ident.equalsIgnoreCase("COS"))
				t = COS;
			else if (ident.equalsIgnoreCase("TAN"))
				t = TAN;
			else if (ident.equalsIgnoreCase("EXP"))
				t = EXP;
			else if (ident.equalsIgnoreCase("LN"))
				t = LN;
			else if (ident.equalsIgnoreCase("ASIN"))
				t = ASIN;
			else if (ident.equalsIgnoreCase("ACOS"))
				t = ACOS;
			else if (ident.equalsIgnoreCase("ATAN"))
				t = ATAN;
			else if (ident.equalsIgnoreCase("SQRT"))
				t = SQRT;
			else if (ident.equalsIgnoreCase("COND"))
				t = COND;
			else if (ident.equalsIgnoreCase("MIN"))
				t = MIN;
			else if (ident.equalsIgnoreCase("MAX"))
				t = MAX;
			else if (ident.equalsIgnoreCase("AND"))
				t = AND;
			else if (ident.equalsIgnoreCase("OR"))
				t = OR;
			else if (ident.equalsIgnoreCase("NOT"))
				t = NOT;
			else
				throw new RuntimeException("unknown ident: " + ident);
			/* this exception can be ignored; simply replace it by the following lines
				if (++i < str.length()) c = str.charAt(i);
				t = getTok();
			*/
		} else
			switch (c) {
				case '*': t = MULT;		if (++i < str.length()) c = str.charAt(i); break;
				case '/': t = DIVIDED;	if (++i < str.length()) c = str.charAt(i); break;
				case '%': t = MOD;		if (++i < str.length()) c = str.charAt(i); break;
				case '+': t = PLUS;		if (++i < str.length()) c = str.charAt(i); break;
				case '-': t = MINUS;	if (++i < str.length()) c = str.charAt(i); break;
				case '(': t = LBRAK;	if (++i < str.length()) c = str.charAt(i); break;
				case ')': t = RBRAK;	if (++i < str.length()) c = str.charAt(i); break;
				case ',': t = COMMA;	if (++i < str.length()) c = str.charAt(i); break;
				case '^': t = POW;		if (++i < str.length()) c = str.charAt(i); break;
				default:
					throw new RuntimeException("unknown char: " + c);
			/* this exception can be ignored; simply replace it by the following line
					if (++i < str.length()) c = str.charAt(i); t = getTok(); break;
			*/
			}
		return t;
	}

	/* Parser */

	void eat (int expectedToken) {
		if (token == expectedToken)
			token = getTok();
		else
			throw new RuntimeException("expected: " + expectedToken + " but got: " + token);
	}

	double primary () {
		double p=0.0;
		double first=0.0, second=0.0, cond=0.0;

		switch (token) {
			case NUMBER:	p = num; token = getTok(); break;
			case SIN:		token = getTok();	eat(LBRAK);		p = Math.sin(expr());	eat(RBRAK); break;
			case COS:		token = getTok();	eat(LBRAK);		p = Math.cos(expr());	eat(RBRAK); break;
			case TAN:		token = getTok();	eat(LBRAK);		p = Math.tan(expr());	eat(RBRAK); break;
			case EXP:		token = getTok();	eat(LBRAK);		p = Math.exp(expr());	eat(RBRAK); break;
			case LN:		token = getTok();	eat(LBRAK);		p = Math.log(expr());	eat(RBRAK); break;
			case ASIN:		token = getTok();	eat(LBRAK);		p = Math.asin(expr());	eat(RBRAK); break;
			case ACOS:		token = getTok();	eat(LBRAK);		p = Math.acos(expr());	eat(RBRAK); break;
			case ATAN:		token = getTok();	eat(LBRAK);		p = Math.atan(expr());	eat(RBRAK); break;
			case SQRT:		token = getTok();	eat(LBRAK);		p = Math.sqrt(expr());	eat(RBRAK); break;
			case NOT:		token = getTok();	eat(LBRAK);		p = (notFalse(compExpr()) ? 0.0 : 1.0);
							eat(RBRAK);			break;
			case AND:		token = getTok();	eat(LBRAK);		first = (notFalse(compExpr()) ? 1.0 : 0.0);
							while (token == COMMA) {
								eat(COMMA);		if (! notFalse(compExpr())) first = 0.0;
							}
							eat(RBRAK);			p = (notFalse(first) ? 1.0 : 0.0);		break;
			case OR:		token = getTok();	eat(LBRAK);		first = (notFalse(compExpr()) ? 1.0 : 0.0);
							while (token == COMMA) {
								eat(COMMA);		if (notFalse(compExpr())) first = 1.0;
							}
							eat(RBRAK);			p = (notFalse(first) ? 1.0 : 0.0);		break;
			case COND:		token = getTok();	eat(LBRAK);		cond = compExpr();		eat(COMMA);
							first = expr();		eat(COMMA);		second = expr();		eat(RBRAK);
							p = (notFalse(cond) ? first : second);						break;
			case MIN:		token = getTok();	eat(LBRAK);		first = second = expr();
							while (token == COMMA) {
								eat(COMMA); second = expr();	if (second < first) first = second;
							}
							eat(RBRAK);			p = (first < second ? first : second);	break;
			case MAX:		token = getTok();	eat(LBRAK);		first = second = expr();
							while (token == COMMA) {
								eat(COMMA); second = expr();	if (second > first) first = second;
							}
							eat(RBRAK);			p = (first > second ? first : second);	break;
			case LBRAK:		token = getTok();	p = expr();		eat(RBRAK); break;
		default:
			throw new RuntimeException("unexpected token: " + token);
		}
		return p;
	}

	boolean notFalse (double arg) {
		return Math.abs(arg) >= 1.0e-6;
	}

	double factor () {
		double f = primary();
		while (token == POW) {
			token = getTok();
			f = Math.exp(factor() * Math.log(f));
		}
		return f;
	}

	double term () {
		double f = factor();
		while ((token == MULT) || (token == DIVIDED) || (token == MOD)) {
			if (token == MULT) {
				token = getTok();
				f *= factor();
			} else if (token == DIVIDED) {
				token = getTok();
				f /= factor();
			} else {
				token = getTok();
				f %= factor();
			}
		}
		return f;
	}

	double expr () {
		double t;

		if (token == PLUS) {
			token = getTok();
			t = term();
		} else if (token == MINUS) {
			token = getTok();
			t = -term();
		} else
			t = term();

		while ((token == PLUS) || (token == MINUS))
			if (token == PLUS) {
				token = getTok();
				t += term();
			} else {
				token = getTok();
				t -= term();
			}
		return t;
	}

	double compExpr () {
		double t1 = expr();
		int compToken = token;	// remember comparison operator

		switch (compToken) {
			case LE: case LT: case EQ: case NE: case GE: case GT:
				break;
			default:
				return t1;
		}

		token = getTok();
		double t2 = expr();
		switch (compToken) {
			case LE: return ((t1 <= t2) ? 1.0 : 0.0);
			case LT: return ((t1 < t2) ? 1.0 : 0.0);
			case EQ: return ((t1 == t2) ? 1.0 : 0.0);
			case NE: return ((t1 != t2) ? 1.0 : 0.0);
			case GE: return ((t1 >= t2) ? 1.0 : 0.0);
			case GT: return ((t1 > t2) ? 1.0 : 0.0);
			default: return t1;
				// default should not happen
		}
	}

	public static double eval (String s, Map vars) {
		Expr self = new Expr();
		self.str = s;
		self.ident = "";
		self.i = -1;
		self.c = ' ';
		if (vars == null)
			self.vars = new HashMap();
		else
			self.vars = vars;
		self.token = self.getTok();
		if (self.token != LAST)
			return self.expr();
		else
			return 0.0;
	}

	static String[] tests = {
		"not(0.0)",					"1.0",
		"not(1.0)",					"0.0",
		"and(0.0,0.0)",				"0.0",
		"and(0.0,1.0)",				"0.0",
		"and(1.0,0.0)",				"0.0",
		"and(1.0,0.0,1.0)",			"0.0",
		"and(1.0,1.0)",				"1.0",
		"and(1.0,1.0,1.0)",			"1.0",
		"or(0.0,0.0)",				"0.0",
		"or(0.0,0.0,0.0)",			"0.0",
		"or(0.0,1.0)",				"1.0",
		"or(1.0,0.0)",				"1.0",
		"or(1.0,1.0)",				"1.0",
		"or(1.0,1.0,0.0)",			"1.0",
		"cond(1.0<0.0,37,85)",		"85.0",
		"cond(1.0>=0.0,37,85)",		"37.0",
		"and(5>3,6>2)",				"1.0",
		"and(5>3,6<2)",				"0.0"
	};

	public static void main (String args[]) {
		int narg = args.length;
		if (narg == 0) {
				// do some self-testing
			for (int i=0; i < tests.length; i+=2) {
				if (! tests[i+1].equals(new Double(eval(tests[i],null)).toString()))
					System.out.println(tests[i] + " should be " + tests[i+1] + " but is " + new Double(eval(tests[i],null)).toString());
				else
					System.out.println(tests[i] + " : OK");
			}
			System.out.println("Usage: java Expr expression var-name var-value ...");
		} else {
			Map vars = new HashMap();
			try {
				for (int i=1; i<narg; i+=2)
					vars.put(args[i].toLowerCase(), Double.valueOf(args[i+1]));
			} catch (Exception ex) {
				System.out.println("Problem reading the input parameters");
			}
			System.out.print(args[0] + " (" + vars.toString() + ") = " + eval(args[0], vars));
		}
	}
}

