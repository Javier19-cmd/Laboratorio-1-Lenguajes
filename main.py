"""
Archivo principal del programa. Aquí se harán todas las llamadas a las funciones de los otros archivos.
"""


from shunting import evaluar # Importando la función evaluar de shunting.py.


# Definiendo la expresión regular.
regex = "(a|b)*abb"

# Imprimiendo la expresión regular.
print("Expresión regular: " + regex)

# Convirtiendo la expresión regular a su forma posfija.
post = evaluar(regex)

# Imprimiendo la expresión regular en su forma posfija.
print("Expresión regular en su forma posfija: " + post)