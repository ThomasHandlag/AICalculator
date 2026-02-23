package com.example.calculator.exprcal

import android.os.Build
import android.util.Log
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

enum class Keyword(val symbol: String) {
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    CO_TAN("co_tan"),
    LOG("log"),
    LN("ln"),
    SQRT("sqrt"),
    PI("π"),
    EULER(
        "e"
    ),
    PHI("Φ"),
    FACTORIAL("!"),
    CUBER("cuber")
}

val MATH_PHI: Double = (1 + sqrt(5.0)) / 2

enum class TokenType {
    NUMBER, OPERATOR, KEYWORD, LEFT_PAREN, RIGHT_PAREN, COMMA, END_OF_EXPRESSION
}

class Token(
    val value: Double? = null,
    val lexeme: String? = null,
    val type: TokenType,
    val keyword: Keyword? = null,
) {
    override fun toString(): String {
        val buffer = StringBuffer("Token(type: $type")
        if (lexeme != null) buffer.append(", lexeme: \"$lexeme\"")
        if (value != null) buffer.append(", value: \$value")
        if (keyword != null) buffer.append(", keyword: \$keyword")
        buffer.append(')')
        return buffer.toString()
    }
}

class Lexical(private val expression: String) {
    var position: Int = 0

    companion object {
        const val DIGITS = "0123456789."
        const val OPERATORS = "+-*/^"
        const val LEFT_PAREN = "("
        const val RIGHT_PAREN = ")"
    }

    fun tokenize(): MutableList<Token> {
        val tokens: MutableList<Token> = mutableListOf()

        while (position < expression.length) {
            val currentChar: Char = expression[position]

            if (currentChar.toString() in DIGITS) {
                tokens.add(readNumber())
                continue
            }

            if (currentChar.toString() in OPERATORS) {
                tokens.add(
                    Token(
                        lexeme = currentChar.toString(),
                        type = TokenType.OPERATOR,
                    )
                )
                position++
                continue
            }

            if (currentChar.toString() == LEFT_PAREN) {
                tokens.add(Token(type = TokenType.LEFT_PAREN))
                position++
                continue
            }

            if (currentChar.toString() == RIGHT_PAREN) {
                tokens.add(Token(type = TokenType.RIGHT_PAREN))
                position++
                continue
            }

            var keywordFound = false
            for (kw in Keyword.entries) {
                if (expression.substring(position).startsWith(kw.symbol)) {
                    // Ensure it's not part of a longer identifier
                    val nextCharIndex = position + kw.symbol.length
                    if (nextCharIndex == expression.length || !Regex("[a-zA-Z0-9_]").matches(
                            expression[nextCharIndex].toString()
                        )
                    ) {
                        tokens.add(
                            Token(
                                type = TokenType.KEYWORD, lexeme = kw.symbol, keyword = kw
                            )
                        )
                        position += kw.symbol.length
                        keywordFound = true
                        break
                    }
                }
            }
            if (keywordFound) {
                continue
            }

            throw Exception("Unexpected character: $currentChar at position $position")
        }
        tokens.add(Token(type = TokenType.END_OF_EXPRESSION, lexeme = "EOF"))
        return tokens
    }

    fun readNumber(): Token {
        val start = position
        while (position < expression.length && expression[position].toString() in DIGITS) {
            position++
        }
        val numberStr = expression.substring(start, position)
        try {
            return Token(
                type = TokenType.NUMBER, lexeme = numberStr, value = numberStr.toDouble()
            )
        } catch (_: Exception) {
            throw Exception("Invalid number format: $numberStr")
        }
    }
}

class Parser(private val tokens: List<Token>) {
    var currentTokenIndex = 0

    val operatorPrecedence: Map<String, Int> = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3,
    )

    val operatorRightAssociative: Map<String, Boolean> = mapOf(
        "^" to true, // Exponentiation is right-associative
    )

    val epsilon: Double = 1e-10

    /// Returns the current token without advancing the position.
    fun peek(): Token = tokens[currentTokenIndex]

    /// Returns the current token and advances the position.
    fun next() = tokens[currentTokenIndex++]

    /// Parses and evaluates the expression, returning the result.
    fun parse(): Double {
        val operandStack = mutableListOf<Double>()
        val operatorStack = mutableListOf<Token>()

        while (peek().type != TokenType.END_OF_EXPRESSION) {
            val token = next()

            when (token.type) {
                TokenType.NUMBER -> {
                    operandStack.add(token.value!!)
                }

                TokenType.OPERATOR -> {
                    // Handle unary minus by pushing a 0 onto the operand stack
                    if (token.lexeme == "-") {
                        val prevTokenType =
                            if (currentTokenIndex > 1) tokens[currentTokenIndex - 2].type else null
                        val isUnaryMinus =
                            operandStack.isEmpty() || prevTokenType == TokenType.LEFT_PAREN || prevTokenType == TokenType.OPERATOR || prevTokenType == TokenType.KEYWORD
                        if (isUnaryMinus) {
                            operandStack.add(0.0)
                        }
                    }

                    while (operatorStack.isNotEmpty() && isOperatorOnStack(operatorStack.last()) && shouldPopOperator(
                            token,
                            operatorStack.last()
                        )
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                            applyOperation(operandStack, operatorStack.removeLast())
                        } else {
                            applyOperation(
                                operandStack, operatorStack.removeAt(operatorStack.size - 1)
                            )
                        }
                    }
                    operatorStack.add(token)
                }

                TokenType.KEYWORD -> {
                    operatorStack.add(token)
                }

                TokenType.LEFT_PAREN -> {
                    operatorStack.add(token)
                }

                TokenType.RIGHT_PAREN -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last().type != TokenType.LEFT_PAREN) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                            applyOperation(operandStack, operatorStack.removeLast())
                        } else {
                            applyOperation(
                                operandStack, operatorStack.removeAt(operatorStack.size - 1)
                            )
                        }
                    }

                    if (operatorStack.isEmpty() || operatorStack.last().type != TokenType.LEFT_PAREN) {
                        throw Exception(
                            "Mismatched parentheses or missing opening parenthesis"
                        )
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                        operatorStack.removeLast() // Pop the '('
                    } else {
                        operatorStack.removeAt(operatorStack.size - 1) // Pop the '('
                    }

                    // If the token before the '(' was a function, apply it
                    if (operatorStack.isNotEmpty() && operatorStack.last().type == TokenType.KEYWORD) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                            applyFunction(operandStack, operatorStack.removeLast())
                        } else {
                            applyOperation(
                                operandStack, operatorStack.removeAt(operatorStack.size - 1)
                            )
                        }
                    }
                }

                TokenType.COMMA -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last().type != TokenType.LEFT_PAREN) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                            applyOperation(operandStack, operatorStack.removeLast())
                        } else {
                            applyOperation(
                                operandStack, operatorStack.removeAt(operatorStack.size - 1)
                            )
                        }
                    }
                }

                TokenType.END_OF_EXPRESSION -> {
                }
            }
        }

        // After parsing all tokens, apply any remaining operators
        while (operatorStack.isNotEmpty()) {
            if (operatorStack.last().type == TokenType.LEFT_PAREN) {
                throw Exception("Mismatched parentheses or missing closing parenthesis")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                applyOperation(operandStack, operatorStack.removeLast())
            } else {
                applyOperation(operandStack, operatorStack.removeAt(operatorStack.size - 1))
            }
        }

        if (operandStack.size != 1) {
            throw Exception("Invalid expression format or unapplied operations")
        }

        return operandStack.single()
    }

    fun isOperatorOnStack(token: Token): Boolean {
        return token.type == TokenType.OPERATOR || token.type == TokenType.KEYWORD
    }

    /// Determines if the operator on top of the stack ([op2]) should be popped and applied
    /// before pushing the current operator ([op1]).
    fun shouldPopOperator(op1: Token, op2: Token): Boolean {
        if (op2.type == TokenType.KEYWORD) return true // Functions always get applied before other operators
        if (op1.type != TokenType.OPERATOR || op2.type != TokenType.OPERATOR) return false

        val p1 = operatorPrecedence[op1.lexeme]!!
        val p2 = operatorPrecedence[op2.lexeme]!!
        val isRightAssociative = operatorRightAssociative[op1.lexeme] == true

        return (p1 < p2) || (p1 == p2 && !isRightAssociative)
    }

    /// Applies a binary operator to the top two operands on the stack.
    fun applyOperation(operandStack: MutableList<Double>, operatorToken: Token) {
        if (operatorToken.type == TokenType.KEYWORD) {
            applyFunction(operandStack, operatorToken)
            return
        }

        if (operandStack.size < 2) {
            throw Exception("Insufficient operands for operator \"${operatorToken.lexeme}\"")
        }
        val b = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            operandStack.removeLast()
        } else {
            operandStack.removeAt(operandStack.size - 1)
        }
        val a = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            operandStack.removeLast()
        } else {
            operandStack.removeAt(operandStack.size - 1)
        }

        when (operatorToken.lexeme) {
            "+" -> operandStack.add(a + b)

            "-" -> operandStack.add(a - b)

            "*" -> operandStack.add(a * b)

            "/" -> {
                if (abs(b) < epsilon) throw Throwable("Division by zero")
                operandStack.add(a / b)
            }

            "^" -> operandStack.add(a.pow(b))

            else -> throw Throwable("Unknown operator: \"${operatorToken.lexeme}\"")
        }
    }

    /// Applies a function (keyword) to the top operand on the stack.
    fun applyFunction(operandStack: MutableList<Double>, functionToken: Token) {
        if (operandStack.isEmpty() && functionToken.keyword !in listOf(
                Keyword.PHI,
                Keyword.PI,
                Keyword.EULER
            )
        ) {
            throw Exception("Insufficient operands for function \"${functionToken.lexeme}\"")
        }
        val operand =
            if (operandStack.isEmpty()) 1.0 else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM)
                operandStack.removeLast()
            else
                operandStack.removeAt(operandStack.size - 1)


        when (functionToken.keyword) {
            Keyword.SIN -> operandStack.add(sin(operand))

            Keyword.COS -> operandStack.add(cos(operand))

            Keyword.TAN -> operandStack.add(tan(operand))

            Keyword.CO_TAN -> {
                // co_tan(x) = 1/tan(x)
                val tanVal = tan(operand)
                if (abs(tanVal) < epsilon) {
                    throw Throwable("co_tan(x) is undefined for tan(x) ≈ 0 (at multiples of π)")
                }
                operandStack.add(1 / tanVal)
            }

            Keyword.SQRT -> {
                if (operand < 0) throw Throwable("Cannot take square root of a negative number")
                operandStack.add(sqrt(operand))
            }

            Keyword.CUBER -> operandStack.add(operand.pow(1.0 / 3))

            Keyword.LOG -> {
                if (operand <= 0) throw Throwable("Logarithm undefined for non-positive numbers")
                operandStack.add(log10(operand))
            }

            Keyword.LN -> {
                if (operand <= 0) throw Throwable("Natural logarithm undefined for non-positive numbers")
                operandStack.add(ln(operand))
            }


            Keyword.FACTORIAL -> {
                if (operand < 0 || operand % 1 != 0.0) {
                    throw Throwable("Factorial is only defined for non-negative integers")
                }
                var result = 1.0
                for (i in 1..operand.toInt()) {
                    result *= i
                }
                operandStack.add(result)
            }

            Keyword.PHI -> operandStack.add(MATH_PHI * operand)
            Keyword.PI -> operandStack.add(Math.PI * operand)
            Keyword.EULER -> operandStack.add(Math.E * operand)

            else -> throw Throwable("Unknown function: \"${functionToken.lexeme}\"")
        }
    }
}

fun evaluate(input: String): Double {
    if (input.trim().isEmpty()) {
        return 0.0 // Return 0 for empty expressions, or throw an error based on requirements.
    }
    try {
        val lexer = Lexical(normalizeExpr(input))
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        return parser.parse()
    } catch (e: Exception) {
        Log.d("Parsing error: ", "${e.message}")
    }
    return Double.NaN
}

fun normalizeExpr(expr: String): String {
    return expr.trim().replace('x', '*')
        .replace("÷", "/")
        .replace(" ", "")
        .replace("∛", Keyword.CUBER.symbol)
        .replace("√", Keyword.SQRT.symbol)
}


