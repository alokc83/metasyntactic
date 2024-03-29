package org.metasyntactic.automata.compiler.java.parser;

import org.metasyntactic.automata.compiler.framework.parsers.packrat.PackratGrammar;
import org.metasyntactic.automata.compiler.framework.parsers.packrat.Rule;
import org.metasyntactic.automata.compiler.framework.parsers.packrat.expressions.Expression;
import static org.metasyntactic.automata.compiler.framework.parsers.packrat.expressions.Expression.*;
import org.metasyntactic.automata.compiler.java.scanner.IdentifierToken;
import org.metasyntactic.automata.compiler.java.scanner.JavaToken;
import org.metasyntactic.automata.compiler.java.scanner.keywords.*;
import org.metasyntactic.automata.compiler.java.scanner.literals.LiteralToken;
import org.metasyntactic.automata.compiler.java.scanner.operators.*;
import org.metasyntactic.automata.compiler.java.scanner.separators.*;
import static org.metasyntactic.utilities.ReflectionUtilities.getSimpleName;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** @author cyrusn@google.com (Cyrus Najmabadi) */
public class JavaGrammar extends PackratGrammar<JavaToken.Type> {
  private final static Rule javaStartRule;
  private final static Set<Rule> javaRules;

  static {
    SeparatorToken.getSeparators();
    OperatorToken.getOperators();
    KeywordToken.getKeywords();

    javaStartRule = new Rule("CompilationUnit",
                             sequence(optional(variable("PackageDeclaration")),
                                      repetition(variable("ImportDeclaration")),
                                      repetition(variable("TypeDeclaration")), endOfTokens()));

    javaRules = new LinkedHashSet<Rule>();
    javaRules.add(javaStartRule);

    Set<Rule> rules = javaRules;

    addCompilationUnit(rules);

    addClassDeclaration(rules);
    addInterfaceDeclaration(rules);
    addEnumDeclaration(rules);
    addAnnotationDeclaration(rules);
    addMemberDeclarations(rules);

    addTypes(rules);
    addAnnotation(rules);
    addStatements(rules);
    addExpressions(rules);
    addArrays(rules);
  }

  private static void addArrays(Set<Rule> rules) {
    rules.add(new Rule("ArrayInitializer",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                optionalDelimitedList(variable("VariableInitializer"),
                                                      token(CommaSeparatorToken.instance),
                                                      true),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("VariableInitializer", choice(variable("ArrayInitializer"), variable("Expression"))));
  }

  private static void addTypes(Set<Rule> rules) {
    rules.add(new Rule("TypeParameters",
                       sequence(token(LessThanOperatorToken.instance),
                                delimitedList(variable("TypeParameter"), token(CommaSeparatorToken.instance)),
                                token(GreaterThanOperatorToken.instance))));

    rules.add(new Rule("TypeParameter",
                       sequence(identifier(),
                                optional(variable("TypeBound")))));

    rules.add(new Rule("TypeBound",
                       sequence(token(ExtendsKeywordToken.instance),
                                delimitedList(variable("ClassOrInterfaceType"),
                                              token(BitwiseAndOperatorToken.instance)))));

    rules.add(new Rule("Type",
                       choice(variable("ReferenceType"),
                              variable("PrimitiveType"))));

    rules.add(new Rule("ReferenceType",
                       choice(variable("PrimitiveArrayReferenceType"),
                              variable("ClassOrInterfaceReferenceType"))));

    rules.add(new Rule("PrimitiveArrayReferenceType",
                       sequence(variable("PrimitiveType"),
                                oneOrMore(variable("BracketPair")))));

    rules.add(new Rule("ClassOrInterfaceReferenceType",
                       sequence(variable("ClassOrInterfaceType"),
                                repetition(variable("BracketPair")))));

    rules.add(new Rule("ClassOrInterfaceType",
                       // Identifier  [TypeArguments] { . Identifier [TypeArguments]}
                       delimitedList(variable("SingleClassOrInterfaceType"),
                                     token(DotSeparatorToken.instance))));

    rules.add(new Rule("SingleClassOrInterfaceType",
                       sequence(identifier(),
                                optional(variable("TypeArguments")))));

    rules.add(new Rule("TypeArguments",
                       sequence(token(LessThanOperatorToken.instance),
                                delimitedList(variable("TypeArgument"), token(CommaSeparatorToken.instance)),
                                token(GreaterThanOperatorToken.instance))));

    rules.add(new Rule("TypeArgument",
                       choice(variable("ReferenceType"),
                              variable("WildcardTypeArgument"))));

    rules.add(new Rule("WildcardTypeArgument",
                       choice(
                           variable("ExtendsWildcardTypeArgument"),
                           variable("SuperWildcardTypeArgument"),
                           variable("OpenWildcardTypeArgument"))));

    rules.add(new Rule("ExtendsWildcardTypeArgument",
                       sequence(token(QuestionMarkOperatorToken.instance),
                                token(ExtendsKeywordToken.instance),
                                variable("ReferenceType"))));

    rules.add(new Rule("SuperWildcardTypeArgument",
                       sequence(token(QuestionMarkOperatorToken.instance),
                                token(SuperKeywordToken.instance),
                                variable("ReferenceType"))));

    rules.add(new Rule("OpenWildcardTypeArgument",
                       token(QuestionMarkOperatorToken.instance)));

    rules.add(new Rule("NonWildcardTypeArguments",
                       sequence(token(LessThanOperatorToken.instance),
                                delimitedList(variable("ReferenceType"), token(CommaSeparatorToken.instance)),
                                token(GreaterThanOperatorToken.instance))));

    rules.add(new Rule("PrimitiveType",
                       choice(token(ByteKeywordToken.instance), token(ShortKeywordToken.instance),
                              token(CharKeywordToken.instance), token(IntKeywordToken.instance),
                              token(LongKeywordToken.instance), token(FloatKeywordToken.instance),
                              token(DoubleKeywordToken.instance), token(BooleanKeywordToken.instance),
                              token(VoidKeywordToken.instance))));
  }

  private static void addEnumDeclaration(Set<Rule> rules) {
    rules.add(new Rule("EnumDeclaration",
                       sequence(variable("Modifiers"),
                                token(EnumKeywordToken.instance),
                                identifier(),
                                optional(variable("Interfaces")),
                                variable("EnumBody"))));

    rules.add(new Rule("EnumBody",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                optionalDelimitedList(variable("EnumConstant"), token(CommaSeparatorToken.instance), true),
                                optional(token(SemicolonSeparatorToken.instance)),
                                repetition(variable("ClassBodyDeclaration")),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("EnumConstant",
                       sequence(repetition(variable("Annotation")),
                                identifier(),
                                optional(variable("Arguments")),
                                optional(variable("ClassOrInterfaceBody")))));

    rules.add(new Rule("Arguments",
                       sequence(token(LeftParenthesisSeparatorToken.instance),
                                optional(variable("DelimitedExpressionList")),
                                token(RightParenthesisSeparatorToken.instance))));
  }

  private static void addAnnotationDeclaration(Set<Rule> rules) {
    rules.add(new Rule("AnnotationDeclaration",
                       sequence(variable("Modifiers"),
                                token(AtSeparatorToken.instance),
                                token(InterfaceKeywordToken.instance),
                                identifier(),
                                variable("AnnotationBody"))));

    rules.add(new Rule("AnnotationBody",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                repetition(variable("AnnotationElementDeclaration")),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("AnnotationElementDeclaration",
                       choice(variable("AnnotationDefaultDeclaration"),
                              variable("ClassOrInterfaceMemberDeclaration"))));

    rules.add(new Rule("AnnotationDefaultDeclaration",
                       sequence(variable("Modifiers"),
                                variable("Type"),
                                identifier(),
                                token(LeftParenthesisSeparatorToken.instance),
                                token(RightParenthesisSeparatorToken.instance),
                                token(DefaultKeywordToken.instance),
                                variable("ElementValue"))));
  }

  private static void addMemberDeclarations(Set<Rule> rules) {
    rules.add(new Rule("ClassOrInterfaceMemberDeclaration",
                       choice(variable("FieldDeclaration"),
                              variable("MethodDeclaration"),
                              variable("TypeDeclaration"))));

    rules.add(new Rule("ConstructorDeclaration",
                       sequence(variable("Modifiers"),
                                optional(variable("TypeParameters")),
                                identifier(),
                                token(LeftParenthesisSeparatorToken.instance),
                                optionalDelimitedList(variable("FormalParameter"), token(CommaSeparatorToken.instance)),
                                token(RightParenthesisSeparatorToken.instance),
                                optional(variable("Throws")),
                                variable("Block"))));

    rules.add(new Rule("FieldDeclaration",
                       sequence(variable("Modifiers"),
                                variable("Type"),
                                delimitedList(variable("VariableDeclarator"), token(CommaSeparatorToken.instance)),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("VariableDeclarator",
                       choice(variable("VariableDeclaratorIdAndAssignment"),
                              variable("VariableDeclaratorId"))));

    rules.add(new Rule("VariableDeclaratorIdAndAssignment",
                       sequence(variable("VariableDeclaratorId"),
                                token(EqualsOperatorToken.instance),
                                variable("VariableDeclaratorAssignment"))));

    rules.add(new Rule("VariableDeclaratorAssignment",
                       choice(variable("Expression"),
                              variable("ArrayInitializer"))));

    rules.add(new Rule("VariableDeclaratorId",
                       sequence(identifier(),
                                repetition(variable("BracketPair")))));

    rules.add(new Rule("BracketPair",
                       sequence(token(LeftBracketSeparatorToken.instance),
                                token(RightBracketSeparatorToken.instance))));

    rules.add(new Rule("MethodDeclaration",
                       sequence(variable("Modifiers"),
                                optional(variable("TypeParameters")),
                                variable("Type"),
                                identifier(),
                                token(LeftParenthesisSeparatorToken.instance),
                                optionalDelimitedList(variable("FormalParameter"), token(CommaSeparatorToken.instance)),
                                token(RightParenthesisSeparatorToken.instance),
                                repetition(variable("BracketPair")),
                                optional(variable("Throws")),
                                variable("MethodBody"))));

    rules.add(new Rule("MethodBody",
                       choice(variable("Block"),
                              token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("FormalParameter",
                       sequence(variable("Modifiers"),
                                variable("Type"),
                                optional(token(EllipsisSeparatorToken.instance)),
                                variable("VariableDeclaratorId"))));

    rules.add(new Rule("Throws",
                       sequence(token(ThrowsKeywordToken.instance),
                                delimitedList(variable("ClassOrInterfaceType"), token(CommaSeparatorToken.instance)))));
  }

  private static Expression literal() {
    return type(getSimpleName(LiteralToken.class), LiteralToken.getLiteralValues());
  }

  private static void addExpressions(Set<Rule> rules) {
    rules.add(new Rule("Expression",
                       delimitedList(variable("Expression1"), variable("AssignmentOperator"))));
    /*
        choice(
            variable("AssignmentExpression"),
            variable("Expression1"))
    ));

    rules.add(new Rule("AssignmentExpression",
        sequence(
            variable("Expression1"),
            variable("AssignmentOperator"),
            variable("Expression1")
        )));
        */

    rules.add(new Rule("AssignmentOperator",
                       choice(token(EqualsOperatorToken.instance),
                              token(PlusEqualsOperatorToken.instance),
                              token(MinusEqualsOperatorToken.instance),
                              token(TimesEqualsOperatorToken.instance),
                              token(DivideEqualsOperatorToken.instance),
                              token(AndEqualsOperatorToken.instance),
                              token(OrEqualsOperatorToken.instance),
                              token(ExclusiveOrEqualsOperatorToken.instance),
                              token(ModulusEqualsOperatorToken.instance),
                              token(LeftShiftEqualsOperatorToken.instance),
                              token(RightShiftEqualsOperatorToken.instance),
                              token(BitwiseRightShiftEqualsOperatorToken.instance))));

    rules.add(new Rule("Expression1",
                       choice(variable("TernaryExpression"),
                              variable("Expression2"))));

    rules.add(new Rule("TernaryExpression",
                       sequence(variable("Expression2"),
                                token(QuestionMarkOperatorToken.instance),
                                variable("Expression"),
                                token(ColonOperatorToken.instance),
                                variable("Expression1"))));

    rules.add(new Rule("Expression2",
                       choice(variable("BinaryExpression"),
                              variable("Expression3"))));

    rules.add(new Rule("BinaryExpression",
                       sequence(variable("Expression3"),
                                oneOrMore(variable("BinaryExpressionRest")))));

    rules.add(new Rule("BinaryExpressionRest",
                       choice(variable("InfixOperatorBinaryExpressionRest"),
                              variable("InstanceofOperatorBinaryExpressionRest"))));

    rules.add(new Rule("InfixOperatorBinaryExpressionRest",
                       sequence(variable("InfixOperator"),
                                variable("Expression3"))));

    rules.add(new Rule("InstanceofOperatorBinaryExpressionRest",
                       sequence(token(InstanceofKeywordToken.instance),
                                variable("Type"))));

    rules.add(new Rule("InfixOperator",
                       choice(token(LogicalOrOperatorToken.instance),
                              token(LogicalAndOperatorToken.instance),
                              token(BitwiseOrOperatorToken.instance),
                              token(BitwiseExclusiveOrOperatorToken.instance),
                              token(BitwiseAndOperatorToken.instance),
                              token(EqualsEqualsOperatorToken.instance),
                              token(NotEqualsOperatorToken.instance),
                              token(LessThanOperatorToken.instance),
                              token(LessThanOrEqualsOperatorToken.instance),
                              token(GreaterThanOrEqualsOperatorToken.instance),
                              token(LeftShiftOperatorToken.instance),
                              variable("UnsignedRightShift"),
                              variable("SignedRightShift"),
                              token(GreaterThanOperatorToken.instance),
                              token(PlusOperatorToken.instance),
                              token(MinusOperatorToken.instance),
                              token(TimesOperatorToken.instance),
                              token(DivideOperatorToken.instance),
                              token(ModulusOperatorToken.instance))));

    rules.add(new Rule("UnsignedRightShift",
                       sequence(token(GreaterThanOperatorToken.instance),
                                token(GreaterThanOperatorToken.instance),
                                token(GreaterThanOperatorToken.instance))));

    rules.add(new Rule("SignedRightShift",
                       sequence(token(GreaterThanOperatorToken.instance),
                                token(GreaterThanOperatorToken.instance))));

    rules.add(new Rule("Expression3",
                       choice(variable("PrefixExpression"),
                              variable("PossibleCastExpression"),
                              variable("PrimaryExpression"))));

    rules.add(new Rule("PrefixExpression",
                       sequence(variable("PrefixOperator"),
                                variable("Expression3"))));

    rules.add(new Rule("PrefixOperator",
                       choice(token(IncrementOperatorToken.instance),
                              token(DecrementOperatorToken.instance),
                              token(LogicalNotOperatorToken.instance),
                              token(BitwiseNotOperatorToken.instance),
                              token(PlusOperatorToken.instance),
                              token(MinusOperatorToken.instance))));

    rules.add(new Rule("PossibleCastExpression",
                       choice(variable("PossibleCastExpression_Type"),
                              variable("PossibleCastExpression_Expression"))));

    rules.add(new Rule("PossibleCastExpression_Type",
                       sequence(token(LeftParenthesisSeparatorToken.instance),
                                variable("Type"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Expression3"))));

    rules.add(new Rule("PossibleCastExpression_Expression",
                       sequence(token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Expression3"))));

    rules.add(new Rule("PrimaryExpression",
                       sequence(variable("ValueExpression"),
                                repetition(variable("Selector")),
                                optional(variable("PostfixOperator")))));

    rules.add(new Rule("PostfixOperator",
                       choice(token(IncrementOperatorToken.instance),
                              token(DecrementOperatorToken.instance))));

    rules.add(new Rule("ValueExpression",
                       choice(variable("ParenthesizedExpression"),
                              variable("MethodInvocation"),
                              variable("ThisConstructorInvocation"),
                              variable("SuperConstructorInvocation"),
                              token(ThisKeywordToken.instance),
                              token(SuperKeywordToken.instance),
                              variable("ClassAccess"),
                              literal(),
                              identifier(),
                              variable("CreationExpression"))));

    rules.add(new Rule("ClassAccess",
                       sequence(variable("Type"),
                                token(DotSeparatorToken.instance),
                                token(ClassKeywordToken.instance))));

    rules.add(new Rule("Selector",
                       choice(variable("DotSelector"),
                              variable("ArraySelector"))));

    rules.add(new Rule("DotSelector",
                       sequence(token(DotSeparatorToken.instance),
                                variable("ValueExpression"))));

    rules.add(new Rule("ArraySelector",
                       sequence(token(LeftBracketSeparatorToken.instance),
                                variable("Expression"),
                                token(RightBracketSeparatorToken.instance))));

    rules.add(new Rule("ParenthesizedExpression",
                       sequence(token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance))));

    rules.add(new Rule("MethodInvocation",
                       sequence(optional(variable("NonWildcardTypeArguments")),
                                identifier(),
                                variable("Arguments"))));

    rules.add(new Rule("ThisConstructorInvocation",
                       sequence(token(ThisKeywordToken.instance),
                                variable("Arguments"))));

    rules.add(new Rule("SuperConstructorInvocation",
                       sequence(token(SuperKeywordToken.instance),
                                variable("Arguments"))));

    rules.add(new Rule("CreationExpression",
                       choice(variable("ObjectCreationExpression"),
                              variable("ArrayCreationExpression"))));

    rules.add(new Rule("ObjectCreationExpression",
                       sequence(token(NewKeywordToken.instance),
                                optional(variable("NonWildcardTypeArguments")),
                                variable("ClassOrInterfaceType"),
                                variable("Arguments"),
                                optional(variable("ClassBody")))));

    rules.add(new Rule("ArrayCreationExpression",
                       sequence(token(NewKeywordToken.instance),
                                variable("ArrayCreationType"),
                                oneOrMore(variable("DimensionExpression")),
                                optional(variable("ArrayInitializer")))));

    rules.add(new Rule("ArrayCreationType",
                       choice(variable("ClassOrInterfaceType"),
                              variable("PrimitiveType"))));

    rules.add(new Rule("DimensionExpression",
                       sequence(token(LeftBracketSeparatorToken.instance),
                                optional(variable("Expression")),
                                token(RightBracketSeparatorToken.instance))));
  }

  private static void addStatements(Set<Rule> rules) {
    rules.add(new Rule("Block",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                repetition(variable("BlockStatement")),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("BlockStatement",
                       choice(variable("LocalVariableDeclarationStatement"),
                              variable("ClassDeclaration"),
                              variable("Statement"))));

    rules.add(new Rule("LocalVariableDeclarationStatement",
                       sequence(variable("LocalVariableDeclaration"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("LocalVariableDeclaration",
                       sequence(variable("Modifiers"),
                                variable("Type"),
                                delimitedList(variable("VariableDeclarator"), token(CommaSeparatorToken.instance)))));

    rules.add(new Rule("Statement",
                       choice(variable("Block"), variable("EmptyStatement"),
                              variable("ExpressionStatement"), variable("AssertStatement"),
                              variable("SwitchStatement"), variable("DoStatement"),
                              variable("BreakStatement"), variable("ContinueStatement"),
                              variable("ReturnStatement"), variable("SynchronizedStatement"),
                              variable("ThrowStatement"), variable("TryStatement"),
                              variable("LabeledStatement"), variable("IfStatement"),
                              variable("WhileStatement"), variable("ForStatement"))));

    rules.add(new Rule("EmptyStatement",
                       token(SemicolonSeparatorToken.instance)));

    rules.add(new Rule("LabeledStatement",
                       sequence(identifier(),
                                token(ColonOperatorToken.instance),
                                variable("Statement"))));

    rules.add(new Rule("ExpressionStatement",
                       sequence(variable("Expression"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("IfStatement",
                       sequence(token(IfKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Statement"),
                                optional(variable("ElseStatement")))));

    rules.add(new Rule("ElseStatement",
                       sequence(token(ElseKeywordToken.instance),
                                variable("Statement"))));

    rules.add(new Rule("AssertStatement",
                       choice(variable("MessageAssertStatement"),
                              variable("SimpleAssertStatement"))));

    rules.add(new Rule("MessageAssertStatement",
                       sequence(token(AssertKeywordToken.instance),
                                variable("Expression"),
                                token(ColonOperatorToken.instance),
                                variable("Expression"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("SimpleAssertStatement",
                       sequence(token(AssertKeywordToken.instance),
                                variable("Expression"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("SwitchStatement",
                       sequence(token(SwitchKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                token(LeftCurlySeparatorToken.instance),
                                repetition(variable("SwitchBlockStatementGroup")),
                                repetition(variable("SwitchLabel")),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("SwitchBlockStatementGroup",
                       sequence(oneOrMore(variable("SwitchLabel")),
                                oneOrMore(variable("BlockStatement")))));

    rules.add(new Rule("SwitchLabel",
                       choice(variable("CaseSwitchLabel"),
                              variable("DefaultSwitchLabel"))));

    rules.add(new Rule("CaseSwitchLabel",
                       sequence(token(CaseKeywordToken.instance),
                                variable("Expression"),
                                token(ColonOperatorToken.instance))));

    rules.add(new Rule("DefaultSwitchLabel",
                       sequence(token(DefaultKeywordToken.instance),
                                token(ColonOperatorToken.instance))));

    rules.add(new Rule("WhileStatement",
                       sequence(token(WhileKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Statement"))));

    rules.add(new Rule("DoStatement",
                       sequence(token(DoKeywordToken.instance),
                                variable("Statement"),
                                token(WhileKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("ForStatement",
                       choice(variable("BasicForStatement"),
                              variable("EnhancedForStatement"))));

    rules.add(new Rule("BasicForStatement",
                       sequence(token(ForKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                optional(variable("ForInitializer")),
                                token(SemicolonSeparatorToken.instance),
                                optional(variable("Expression")),
                                token(SemicolonSeparatorToken.instance),
                                optional(variable("DelimitedExpressionList")),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Statement"))));

    rules.add(new Rule("ForInitializer",
                       choice(variable("LocalVariableDeclaration"),
                              variable("DelimitedExpressionList"))));

    rules.add(new Rule("DelimitedExpressionList",
                       delimitedList(variable("Expression"),
                                     token(CommaSeparatorToken.instance))));


    rules.add(new Rule("EnhancedForStatement",
                       sequence(token(ForKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Modifiers"),
                                variable("Type"),
                                identifier(),
                                token(ColonOperatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Statement"))));

    rules.add(new Rule("BreakStatement",
                       sequence(token(BreakKeywordToken.instance),
                                optional(identifier()),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("ContinueStatement",
                       sequence(token(ContinueKeywordToken.instance),
                                optional(identifier()),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("ReturnStatement",
                       sequence(token(ReturnKeywordToken.instance),
                                optional(variable("Expression")),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("ThrowStatement",
                       sequence(token(ThrowKeywordToken.instance),
                                optional(variable("Expression")),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("SynchronizedStatement",
                       sequence(token(SynchronizedKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("Expression"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Block"))));

    rules.add(new Rule("TryStatement",
                       choice(variable("TryStatementWithFinally"),
                              variable("TryStatementWithoutFinally"))));

    rules.add(new Rule("TryStatementWithFinally",
                       sequence(token(TryKeywordToken.instance),
                                variable("Block"),
                                repetition(variable("CatchClause")),
                                token(FinallyKeywordToken.instance),
                                variable("Block"))));

    rules.add(new Rule("TryStatementWithoutFinally",
                       sequence(token(TryKeywordToken.instance),
                                variable("Block"),
                                oneOrMore(variable("CatchClause")))));

    rules.add(new Rule("CatchClause",
                       sequence(token(CatchKeywordToken.instance),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("FormalParameter"),
                                token(RightParenthesisSeparatorToken.instance),
                                variable("Block"))));
  }

  private static Expression identifier() {
    return type(getSimpleName(IdentifierToken.class), IdentifierToken.getTypeValue());
  }

  private static void addInterfaceDeclaration(Set<Rule> rules) {
    rules.add(new Rule("InterfaceDeclaration",
                       choice(variable("NormalInterfaceDeclaration"),
                              variable("AnnotationDeclaration"))));

    rules.add(new Rule("NormalInterfaceDeclaration",
                       sequence(variable("Modifiers"),
                                token(InterfaceKeywordToken.instance),
                                identifier(),
                                optional(variable("TypeParameters")),
                                optional(variable("ExtendsInterfaces")),
                                variable("ClassOrInterfaceBody"))));

    rules.add(new Rule("ExtendsInterfaces",
                       sequence(token(ExtendsKeywordToken.instance),
                                delimitedList(variable("ClassOrInterfaceType"), token(CommaSeparatorToken.instance)))));

    rules.add(new Rule("ClassOrInterfaceBody",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                repetition(variable("ClassOrInterfaceMemberDeclaration")),
                                token(RightCurlySeparatorToken.instance))));
  }

  private static void addClassDeclaration(Set<Rule> rules) {
    rules.add(new Rule("ClassDeclaration",
                       choice(variable("NormalClassDeclaration"),
                              variable("EnumDeclaration"))));

    rules.add(new Rule("NormalClassDeclaration",
                       sequence(variable("Modifiers"),
                                token(ClassKeywordToken.instance),
                                identifier(),
                                optional(variable("TypeParameters")),
                                optional(variable("Super")),
                                optional(variable("Interfaces")),
                                variable("ClassBody"))));

    rules.add(new Rule("Modifiers",
                       repetition(variable("Modifier"))));

    rules.add(new Rule("Modifier",
                       choice(variable("Annotation"), token(PublicKeywordToken.instance),
                              token(ProtectedKeywordToken.instance), token(PrivateKeywordToken.instance),
                              token(StaticKeywordToken.instance), token(AbstractKeywordToken.instance),
                              token(FinalKeywordToken.instance), token(NativeKeywordToken.instance),
                              token(SynchronizedKeywordToken.instance),
                              token(TransientKeywordToken.instance), token(VolatileKeywordToken.instance),
                              token(StrictfpKeywordToken.instance))));

    rules.add(new Rule("Super",
                       sequence(token(ExtendsKeywordToken.instance),
                                variable("ClassOrInterfaceType"))));

    rules.add(new Rule("Interfaces",
                       sequence(token(ImplementsKeywordToken.instance),
                                delimitedList(variable("ClassOrInterfaceType"), token(CommaSeparatorToken.instance)))));

    rules.add(new Rule("ClassBody",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                repetition(variable("ClassBodyDeclaration")),
                                token(RightCurlySeparatorToken.instance))));

    rules.add(new Rule("ClassBodyDeclaration",
                       choice(variable("ClassOrInterfaceMemberDeclaration"),
                              variable("Block"),
                              variable("StaticInitializer"),
                              variable("ConstructorDeclaration"))));

    rules.add(new Rule("StaticInitializer",
                       sequence(token(StaticKeywordToken.instance),
                                variable("Block"))));
  }

  private static Expression optionalDelimitedList(Expression element, Expression delimiter) {
    return optional(delimitedList(element, delimiter));
  }

  private static Expression optionalDelimitedList(Expression element, Expression delimiter, boolean allowsTrailingDelimiter) {
    return optional(delimitedList(element, delimiter, allowsTrailingDelimiter));
  }

  private static void addCompilationUnit(Set<Rule> rules) {
    rules.add(new Rule("PackageDeclaration",
                       sequence(repetition(variable("Annotation")),
                                token(PackageKeywordToken.instance),
                                variable("QualifiedIdentifier"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("QualifiedIdentifier",
                       delimitedList(identifier(), token(DotSeparatorToken.instance))));

    rules.add(new Rule("ImportDeclaration",
                       choice(variable("SingleTypeImportDeclaration"),
                              variable("TypeImportOnDemandDeclaration"),
                              variable("SingleStaticImportDeclaration"),
                              variable("StaticImportOnDemandDeclaration"))));

    rules.add(new Rule("SingleTypeImportDeclaration",
                       sequence(token(ImportKeywordToken.instance),
                                variable("QualifiedIdentifier"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("TypeImportOnDemandDeclaration",
                       sequence(token(ImportKeywordToken.instance),
                                variable("QualifiedIdentifier"),
                                token(DotSeparatorToken.instance),
                                token(TimesOperatorToken.instance),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("SingleStaticImportDeclaration",
                       sequence(token(ImportKeywordToken.instance),
                                token(StaticKeywordToken.instance),
                                variable("QualifiedIdentifier"),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("StaticImportOnDemandDeclaration",
                       sequence(token(ImportKeywordToken.instance),
                                token(StaticKeywordToken.instance),
                                variable("QualifiedIdentifier"),
                                token(DotSeparatorToken.instance),
                                token(TimesOperatorToken.instance),
                                token(SemicolonSeparatorToken.instance))));

    rules.add(new Rule("TypeDeclaration",
                       choice(variable("ClassDeclaration"),
                              variable("InterfaceDeclaration"),
                              token(SemicolonSeparatorToken.instance))));
  }

  private static void addAnnotation(Set<Rule> rules) {
    rules.add(new Rule("Annotation",
                       choice(variable("NormalAnnotation"),
                              variable("SingleElementAnnotation"),
                              variable("MarkerAnnotation"))));

    rules.add(new Rule("NormalAnnotation",
                       sequence(token(AtSeparatorToken.instance),
                                variable("QualifiedIdentifier"),
                                token(LeftParenthesisSeparatorToken.instance),
                                optionalDelimitedList(variable("ElementValuePair"),
                                                      token(CommaSeparatorToken.instance)),
                                token(RightParenthesisSeparatorToken.instance))));

    rules.add(new Rule("ElementValuePair",
                       sequence(identifier(),
                                token(EqualsOperatorToken.instance),
                                variable("ElementValue"))));

    rules.add(new Rule("SingleElementAnnotation",
                       sequence(token(AtSeparatorToken.instance),
                                variable("QualifiedIdentifier"),
                                token(LeftParenthesisSeparatorToken.instance),
                                variable("ElementValue"),
                                token(RightParenthesisSeparatorToken.instance))));

    rules.add(
        new Rule("MarkerAnnotation",
                 sequence(token(AtSeparatorToken.instance),
                          variable("QualifiedIdentifier"))));

    rules.add(new Rule("ElementValue",
                       choice(variable("Annotation"),
                              variable("Expression"),
                              variable("ElementValueArrayInitializer"))));

    rules.add(new Rule("ElementValueArrayInitializer",
                       sequence(token(LeftCurlySeparatorToken.instance),
                                optionalDelimitedList(variable("ElementValue"),
                                                      token(CommaSeparatorToken.instance),
                                                      true),
                                optional(token(CommaSeparatorToken.instance)),
                                token(RightCurlySeparatorToken.instance))));
  }

  public static final JavaGrammar instance = new JavaGrammar();

  private JavaGrammar() {
    super(javaStartRule, javaRules);
  }

  public JavaToken.Type getTokenFromTerminal(int type) {
    return JavaToken.Type.values()[type];
  }

  protected Set<Integer> getTerminalsWorker() {
    return JavaToken.getValues();
  }

  protected double prefixCost(List<Integer> tokens) {
    if (tokens == null) {
      return Float.MAX_VALUE;
    }

    double cost = 0;

    for (int terminal : tokens) {
      JavaToken.Type token = getTokenFromTerminal(terminal);

      // @ means annotation, and we never really want to insert that.
      if (token == JavaToken.Type.AtSeparator) {
        cost += 100;
      } else if (token == JavaToken.Type.Identifier) {
        cost += 0.75;
      } else {
        cost++;
      }
    }

    return cost;
  }
}