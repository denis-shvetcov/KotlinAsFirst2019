@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.digitNumber
import lesson3.task1.isPrime
import lesson3.task1.pow10
import kotlin.math.pow
import kotlin.math.sqrt


fun powint(x: Int, i: Int): Int {
    var pow = 1
    for (k in 1..i) {
        pow *= x
    }
    return pow
}

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание  при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (element in v) {
        sum += sqr(element)
    }
    return sqrt(sum)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var sum = 0.0
    var count = 0.0
    for (element in list) {
        sum += element
        count++
    }
    return if (count == 0.0) 0.0 else (sum / count)
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    val z = mean(list)
    for (i in 0 until list.size) {
        list[i] -= z
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in a.indices) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var sum = 0
    for (i in p.indices) {
        sum += p[i] * powint(x, i)
    }
    return sum
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isEmpty() || list.size == 1) return list
    for (i in 1 until list.size) {
        list[i] = list[i] + list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n >1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var number = n
    val v = mutableListOf<Int>()
    for (i in 2..n) {
        if (n % i == 0 && isPrime(i)) {
            while (number % i == 0) {
                number /= i
                v.add(i)
            }
        }
    }
    return v
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val s = mutableListOf<Int>()
    var x = n
    var temp = 0
    if (n == 0) return listOf(0)
    while (x > 0) {
        s.add(x % base)
        x /= base
    }
    for (i in 0 until ((s.size) / 2)) {
        temp = s[i]
        s[i] = s[s.size - i - 1]
        s[s.size - i - 1] = temp
    }
    return s
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var ost = 0
    var x = n
    var perem = ""
    var temp = ""
    if (n == 1 || n == 0) return n.toString()
    while (x > 0) {
        ost = x % base
        if (ost >= 10) temp += (ost + 'a'.toInt() - 10).toChar().toString() else temp += "$ost"
        x /= base
    }
    return temp.reversed()
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var sum = 0
    for (i in digits.indices) {
        sum += digits[i] * powint(base, digits.size - i - 1)
    }
    return sum
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var last = 's'
    var sum = 0
    var number = str.reversed()
    while (number.isNotEmpty()) {
        last = number.last()
        if (last >= 'a') sum += (last - 'a' + 10) * powint(
            base,
            number.length - 1
        ) //код 'a'=97, если отнять от кода последней буквы 87, то получим число >9
        else sum += (last.toInt() - '0'.toInt()) * powint(
            base,
            number.length - 1
        )//код нуля в ASCII-48, а 48- разница м-ду кодами начала букв a,b,c... и началом кодов десятичных цифр
        number = number.substring(0, number.length - 1)
    }
    return sum
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII,  44 = XLIV, 100 = C
 */


fun roman(n: Int): String {
    var result = ""
    var input = n
    val numbers = listOf<Pair<Int, String>>(
        (1000 to "M"),
        (900 to "CM"),
        (500 to "D"),
        (400 to "CD"),
        (100 to "C"),
        (90 to "XC"),
        (50 to "L"),
        (40 to "XL"),
        (10 to "X"),
        (9 to "IX"),
        (5 to "V"),
        (4 to "IV"),
        (1 to "I")
    )
    for (i in numbers.indices) {
        while (input >= numbers[i].first) {
            input -= numbers[i].first
            result += numbers[i].second
        }
    }
    return result
}


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999  прописью по-русски.
 * Например, 375 ="триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 *
 */
val units = listOf(
    "нулевой",
    "один",
    "два",
    "три",
    "четыре",
    "пять",
    "шесть",
    "семь",
    "восемь",
    "девять"
)
val tentonineteen = listOf(
    "десять",
    "одиннадцать",
    "двенадцать",
    "тринадцать",
    "четырнадцать",
    "пятнадцать",
    "шестнадцать",
    "семнадцать",
    "восемнадцать",
    "девятнадцать"
)
val dozens = listOf(
    "нулевой",
    "десять",
    "двадцать",
    "тридцать",
    "сорок",
    "пятьдесят",
    "шестьдесят",
    "семьдесят",
    "восемьдесят",
    "девяносто"
)
val hundreds = listOf(
    "нулевой",
    "сто",
    "двести",
    "триста",
    "четыреста",
    "пятьсот",
    "шестьсот",
    "семьсот",
    "восемьсот",
    "девятьсот"
)

fun russian(n: Int): String {
    var sp = ""
    var x = n
    if (x >= 1000) {
        x /= 1000
        if ((x / 100) > 0) {
            sp += hundreds[x / pow10(digitNumber(x) - 1)]
            x %= pow10(digitNumber(x) - 1)
            if (x > 0) sp += " " else sp += " тысяч"
        }
        if ((x / 10) >= 2) {
            sp += dozens[x / pow10(digitNumber(x) - 1)]
            x %= pow10(digitNumber(x) - 1)
            if (x > 0) sp += " " else sp += " тысяч"
        }
        if ((x / 10) == 1) {
            sp += tentonineteen[x % pow10(digitNumber(x) - 1)]
            x /= 100
            sp += " тысяч"
        }
        if (x > 0) {
            if (x == 1) sp += "одна тысяча"
            if (x == 2) sp += "две тысячи"
            if ((x == 3) || (x % 10 == 4)) sp = sp + units[x] + " тысячи"
            if ((x >= 5) && (x % 10 <= 9)) sp = sp + units[x] + " тысяч"

        }
    }
    if ((n % 1000 != 0) && (n > 999)) sp += " "
    x = n % 1000
    if ((x / 100) > 0) {
        sp += hundreds[x / pow10(digitNumber(x) - 1)]
        x %= pow10(digitNumber(x) - 1)
        if (x > 0) sp += " "
    }
    if ((x / 10) >= 2) {
        sp += dozens[x / pow10(digitNumber(x) - 1)]
        x %= pow10(digitNumber(x) - 1)
        if (x > 0) sp += " "
    }
    if ((x / 10) == 1) {
        sp += tentonineteen[x % pow10(digitNumber(x) - 1)]
        x /= 100
    }
    if (x > 0) sp += units[x]
    return sp
}