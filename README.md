# VIH-automata-celular
Objetivo: Crear un programa en C++ que implemente un autómata celular para simular la infección por el VIH en el cuerpo humano.

Descripción del Autómata:
+ El autómata se representa en una malla cuadrada de tamaño L x L, donde cada sitio representa una célula en los tejidos linfáticos.
+ Cada célula está representada por un autómata de 4 estados: saludable, infectada tipo A, infectada tipo B o muerta.
+ Las células saludables representan las células CD4+ o macrófagos, que son el principal objetivo del VIH.
+ Las células infectadas tipo A representan las células recién infectadas que aún no han sido reconocidas por el sistema inmunológico.
+ Las células infectadas tipo B representan las células que han sido reconocidas por el sistema inmunológico y tienen una capacidad reducida de propagar la infección.
+ Las células muertas son aquellas que han sido destruidas por el sistema inmunológico.
+ El sistema inmunológico tarda t time steps en desarrollar una respuesta específica a un antígeno, y cada nueva célula infectada tiene una nueva cepa del virus debido a su alta tasa de mutación y replicación.

Reglas de Actualización:
+ Una célula saludable se vuelve infectada tipo A si tiene al menos RA células infectadas tipo A o RB células infectadas tipo B como vecinos.
+ Una célula infectada tipo A se propaga durante t time steps y luego se vuelve infectada tipo B.
+ Una célula infectada tipo B se vuelve muerta en el siguiente time step.
+ Una célula muerta tiene una probabilidad Prepl de ser reemplazada por una nueva célula, para simular la entrada de nuevas células debido a la circulación de sangre y linfa. La nueva célula tiene una probabilidad Pinfec de ser infectada tipo A.

Entrada:
+ L: Tamaño de la malla cuadrada (L x L)
+ RA: Número de células infectadas tipo A que una célula saludable necesita como vecinos para ser infectada.
+ RB: Número de células infectadas tipo B que una célula saludable necesita como vecinos para ser infectada.
+ t: Número de time steps que tarda el sistema inmunológico en desarrollar una respuesta específica a un antígeno.
+ Prepl: Probabilidad de que una célula muerta sea reemplazada por una nueva célula.
+ Pinfec: Probabilidad de que una nueva célula sea infectada tipo A.

Salida:
La simulación del autómata celular que muestra la evolución de las células en la malla cuadrada a lo largo del tiempo, similar a una animación del juego de la vida de Conway y una grafica que compare cell densities vs semanas y años
