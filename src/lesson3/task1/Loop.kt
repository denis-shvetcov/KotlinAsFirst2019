@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var x = n
    var count = 0
    while (x != 0) {
        count++
        x /= 10
    }
    return if (count == 0) 1 else count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 1
    var b = 1
    var c = 0
    for (i in 1..n - 2) {
        c = a + b
        b = a
        a = c
    }
    return if ((n == 1) || (n == 2)) 1 else c
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var x = m
    var y = n
    while (x != y) {
        if (x > y) x -= y else y -= x
    }
    val nod = x
    var nok = 0
    nok = m * n / nod
    return nok
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (n % 2 == 0) return 2
    var min = n
    for (i in 3..(n / 2)) {
        if (n % i == 0) {
            min = i
            break
        }
    }
    return min
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var x = n
    for (i in (n / 2) downTo 1) {
        if ((n % i) == 0) {
            x = i
            break
        }
    }
    return x
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    if ((m % 2 == 0) && (n % 2 == 0)) return false
    if ((m == n) && (m != 1) && (n != 1)) return false
    if ((m == 1) && (n == 1)) return true
    if (m > n) {
        for (i in 3..n) {
            if (((m % i) == 0) && ((n % i) == 0)) return false
        }

    } else for (i in 3..m) {
        if (((m % i) == 0) && ((n % i) == 0)) return false
    }
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val a = m
    val b = n
    for (i in a..b) {
        if (sqrt(i.toDouble()) > sqrt(i.toDouble()).toInt()) continue else return true
    }
    return false
}


/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var k = x
    var count = 0
    while (k > 1) {
        if (k % 2 == 0) {
            k /= 2
            count++
        } else {
            k = 3 * k + 1
            count++
        }
    }
    return count
}


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    val l = x % (2 * PI)
    var sin = 0.0
    var a = 1

    while (abs(l.pow(a) / factorial(a)) >= eps) {
        if ((a - 1) % 4 == 0) sin += l.pow(a) / factorial(a) else sin -= l.pow(a) / factorial(a)
        a += 2

    }
    return sin
}


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    val l = x % (2 * PI)
    var cos = 1.0
    var a = 2
    var b = 2
    while (l.pow(a) / factorial(b) >= eps) {
        if (a % 4 == 0) cos += l.pow(a) / factorial(b) else cos -= l.pow(a) / factorial(b)
        a += 2
        b += 2
    }
    return cos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var a = 0
    var x = n
    var new = 0
    var count = 0
    while (x > 0) {
        x /= 10
        count++
    }
    x = n
    for (i in 1..count) {
        a = (x % 10) * 10.0.pow(count - i).toInt()
        new += a
        x /= 10
    }

    return new
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var count = 0
    var x = n
    while (x > 0) {
        x /= 10
        count++
    }
    val s = n
    var first = 0
    if ((n >= 0) && (n <= 9)) return true
    for (i in 1..(count / 2)) {
        first = ((s / (10.0.pow(count - i))).toInt() % 10)
        if (((s / (10.0.pow(i - 1))) % 10).toInt() != first) return false
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var x = n
    var first = 0

    if ((n >= 0) && (n <= 9)) return false
    while (x > 9) {
        x /= 10
        first = x
    }
    x = n
    while (x > 10) {
        if ((x % 10) != first) {
            return true
        } else x /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    if (n == 1) return 1
    var i = 1
    var sqr = 0
    var sumcount = 1 //кол-во цифр в ряду квадратов
    var last = 0 //n-ная цифра
    var count = 0
    var temp = 0
    var counttemp = 0//колво цифр в числе, при котором sumcount>=n
    while (sumcount < n) {
        i += 1
        sqr = i * i // квадрат i
        temp = i * i // запоминаем квадрат
        count = 0
        while (sqr > 0) {
            sqr /= 10
            sumcount++
            count++ //кол-во сокращенных цифр с конца
            if (sumcount == n) break
        }
        counttemp = count //кол-во сокращенных цифр с конца
    }
    count = digitNumber(temp) //кол-во цифр в числе, на котором обрывается цикл
    last = temp / (10.0.pow(count - counttemp).toInt()) % 10
    return last
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if ((n == 1) || (n == 2)) return 1
    var i = 2
    var fib = 0
    var sumcount = 2
    var last = 0
    var count = 0
    var temp = 0
    var counttemp = 0
    while (sumcount < n) {
        i += 1
        fib = fib(i)
        temp = fib(i)
        count = 0
        while (fib > 0) {
            fib /= 10
            sumcount++
            count++
            if (sumcount == n) break
        }
        counttemp = count
    }
    count = digitNumber(temp)
    last = temp / (10.0.pow(count - counttemp).toInt()) % 10
    return last
}

