"""
Archivo contenedor del algoritmo de Shunting Yard para convertir una expresión regular a su forma posfija.
"""


import collections

precedenceMap = {
    '(': 1,
    '|': 2,
    '.': 3,
    '?': 4,
    '*': 4,
    '+': 4,
}

def getPrecedence(c):
    return precedenceMap.get(c, 6)

def formatRegEx(regex):
    res = ""
    allOperators = {'|', '?', '+', '*', '^'}
    binaryOperators = {'^', '|'}

    for i, c1 in enumerate(regex):
        if i + 1 < len(regex):
            c2 = regex[i+1]
            res += c1

            if c1 != '(' and c2 != ')' and c2 not in allOperators and c1 not in binaryOperators:
                res += '.'

    res += regex[-1]
    return res

def evaluar(regex):
    postfix = ""
    stack = collections.deque()
    formattedRegEx = formatRegEx(regex)

    for c in formattedRegEx:
        if c == '(':
            stack.append(c)
        elif c == ')':
            while stack[-1] != '(':
                postfix += stack.pop()
            stack.pop()
        else:
            while len(stack) > 0:
                peekedChar = stack[-1]
                peekedCharPrecedence = getPrecedence(peekedChar)
                currentCharPrecedence = getPrecedence(c)

                if peekedCharPrecedence >= currentCharPrecedence:
                    postfix += stack.pop()
                else:
                    break
            stack.append(c)

    while len(stack) > 0:
        postfix += stack.pop()

    return postfix