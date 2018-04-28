

/* --------------------------Usercode Section------------------------ */
package src;
import java_cup.runtime.*;
import java.util.*;

%%

/* -----------------Options and Declarations Section----------------- */

/*
    The name of the class JFlex will create will be Lexer.
    Will write the code to the file Lexer.java.
*/
%class Lexer

/*
    The current line number can be accessed with the variable yyline
    and the current column number with the variable yycolumn.
*/
%line
%column

/*
    Will switch to a CUP compatibility mode to interface with a CUP
    generated parser.
*/
%cup

/*
    Declarations

    Code between %{ and %}, both of which must be at the beginning of a
    line, will be copied letter to letter into the lexer class source.
    Here you declare member variables and functions that are used inside
    scanner actions.
*/
%{
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


/*
  Macro Declarations

  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.
*/

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
int = 0 | [1-9][0-9]*
float = [1-9][0-9]*\.[0-9]+
char = \'[a-zA-Z0-9 :;\"_,.-]*\'|\"[a-zA-Z0-9 :;\'_,.-]*\"

/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
id = [A-Za-z_][A-Za-z_0-9]*

/*arg_list = ([A-Za-z_][A-Za-z_0-9]*,)+*/

comment = #.*#

%%
/* ------------------------Lexical Rules Section---------------------- */

/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

   /* YYINITIAL is the state at which the lexer begins scanning.  So
   these regular expressions will only be matched if the scanner is in
   the start state YYINITIAL. */

<YYINITIAL> {

    /* Return the token SEMI declared in the class sym that was found. */
    ";"                { return symbol(sym.SEMI); }

    /* Print the token found that was declared in the class sym and then
       return it. */
    /*arithmetic operations*/
    "+"                { return symbol(sym.PLUS); }
    "-"                { return symbol(sym.MINUS); }
    "*"                { return symbol(sym.TIMES); }
    "/"                { return symbol(sym.DIVIDE); }
    "="                { return symbol(sym.ASS); }

    "->"               { return symbol(sym.ARROW); }

    /*brackets, parentheses*/
    "("                { return symbol(sym.LPAREN); }
    ")"                { return symbol(sym.RPAREN); }
    "["                { return symbol(sym.LBRACKET); }
    "]"                { return symbol(sym.RBRACKET); }
    ","                { return symbol(sym.COMMA); }
    ":"                { return symbol(sym.COLON); }
    "{"                { return symbol(sym.LBRACE); }
    "}"                { return symbol(sym.RBRACE); }

    /*comparison operations*/
    "=="               { return symbol(sym.EQ); }
    "!="               { return symbol(sym.NOTEQ); }
    "<"                { return symbol(sym.LESSER); }
    "<="               { return symbol(sym.LESSEREQ); }
    ">"                { return symbol(sym.GREATER); }
    ">="               { return symbol(sym.GREATEREQ);}

    /*boolean operations*/
    "and"              { return symbol(sym.AND); }
    "or"               { return symbol(sym.OR); }
    "not"              { return symbol(sym.NOT); }

    /*conditional and flow keywords*/
    "if"               { return symbol(sym.IF); }
    "then"             { return symbol(sym.THEN); }
    "else"             { return symbol(sym.ELSE); }
    "print"            { return symbol(sym.PRINT); }
    "while"            { return symbol(sym.WHILE); }
    "do"               { return symbol(sym.DO); }
    "begin"            { return symbol(sym.BEGIN); }
    "end"              { return symbol(sym.END); }
    "return"           { return symbol(sym.RETURN);}
    
    /*variable declaration stuff*/
    "int"              { return symbol(sym.INTDEF, sym.INTDEF); }
    "float"            { return symbol(sym.FLOATDEF, sym.FLOATDEF); }
    "boolean"          { return symbol(sym.BOOLEANDEF, sym.BOOLEANDEF); }
    "char"             { return symbol(sym.CHARDEF, sym.CHARDEF); }
    "array"            { return symbol(sym.ARRAYDEF, sym.ARRAYDEF); }
    "struct"           { return symbol(sym.STRUCTDEF, sym.STRUCTDEF); }
    "def"              { return symbol(sym.FUNCDEF, sym.FUNCDEF); }
    
    /*other keywords*/
    "true"             { return symbol(sym.BOOLEAN, new Boolean(true)); }
    "false"            { return symbol(sym.BOOLEAN, new Boolean(false)); }

    /*literals*/
    {int}      { return symbol(sym.INT, new Integer(yytext())); }
    {float}    { return symbol(sym.FLOAT, new Float(yytext())); }
    {char}     { 
                    // Remove quotes from the string.
                    String str = yytext();
                    return symbol(sym.CHAR, new String(str.substring(1, str.length()-1))); 
                }
    {id}       { return symbol(sym.ID, yytext());}

    /* Don't do anything if whitespace is found */
    {WhiteSpace}       { /* just skip what was found, do nothing */ }
    {comment}          {/* do nothing */}
}


/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }
