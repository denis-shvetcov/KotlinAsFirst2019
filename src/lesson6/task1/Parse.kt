@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.Exception

val months = listOf(
    "нулевой",
    "января",
    "февраля",
    "марта",
    "апреля",
    "мая",
    "июня",
    "июля",
    "августа",
    "сентября",
    "октября",
    "ноября",
    "декабря"
)

val findDigital = """[\d]""".toRegex()
/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    try {
        val date = str.split(" ")
        val day = date[0].toInt()
        var month = date[1]
        val year = date[2].toInt()
        if (month in months) {
            if (day == 29 && months.indexOf(month) == 2 && daysInMonth(months.indexOf(month), year) != day ||
                (day == 31 && day != daysInMonth(months.indexOf(month), year)) || day > 31 ||
                months.indexOf(month) == 2 && day > 29 ) return ""
            else month = twoDigitStr(months.indexOf(month))
        } else return ""
        return "${twoDigitStr(day)}.$month.$year"
    } catch (e1: NumberFormatException) {
        return ""
    } catch (e2: IndexOutOfBoundsException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    var correctmonth = ""
    if (Regex("""(\d\d\.\d\d\.\d+)""").find(digital) == null) return ""
    val date = digital.split(".")
    val day = date[0].toIntOrNull()
    val month = date[1].toIntOrNull()
    val year = date[2].toIntOrNull()
    if (month in 1..12 && day ?: 0 < 32 && day != null && month != null && year != null) correctmonth =
        months[month] else return ""
    if ((month == 2 && day > 29) || (day == 31 && day != daysInMonth(month, year))) return ""
    if (day == 29 && month == 2 && daysInMonth(month, year) != day || date.size != 3) return ""
    return "$day $correctmonth $year"
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    if (Regex("""[\+\-\s\d \( \)]""").replace(phone, "") != "" ||
        Regex("""(\(\))""").find(phone) != null || (Regex("""[)(]""").find(phone) != null &&
                Regex("""(\(.*\))""").find(phone) == null) ||
        (Regex("""[\+\-\s]""").find(phone) != null && Regex("""[\d]""").find(phone) == null
                )
    ) return "" // проверка наличия лишних символов и пустых скобок
    return Regex("""[-\s()]""").replace(phone, "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (Regex("""[\%\-\d\s]""").replace(jumps, "") != "") return -1 //проверка наличия лишних символов
    var list =
        Regex("""[\%\-]""").replace(jumps, "").split(" ") // создаем лист, в котором могут оказаться пробелы
    var max = -1
    list = list.map { it.trim() }  //чистим пробелы
    for (i in list.indices) {
        if (list[i] != "" && list[i].toInt() > max) max = list[i].toInt()
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (Regex("""[\%\-\+\d\s]""").replace(jumps, "") != "") return -1 //проверка наличия лишних символов
    var max = -1
    val list = jumps.split(" ")
    try {
        for (i in 0 until list.size - 1 step 2) {
            if (list[i].toInt() > max && list[i + 1].contains(Regex("""[\+]""")) &&
                Regex("""[\%\-\+\s]""").find(list[i]) == null
            ) max = list[i].toInt()
        }
        return max
    } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val list = expression.split(" ")
    if (findDigital.replace(list[0], "") != "" || expression.isEmpty()
        || list.contains("") || Regex("""[\d\s\+\-]""").replace(expression, "") != ""
    ) throw IllegalArgumentException()
    var sum = list[0].toInt()
    for (i in 1 until list.size - 1 step 2) {
        if ((list[i] != "-" && list[i] != "+") || findDigital.replace(list[i + 1], "") != "")
            throw IllegalArgumentException()
        if (list[i] == "+") sum += list[i + 1].toInt() else sum -= list[i + 1].toInt()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val setofIndices = mutableSetOf<Int>()// индексы совпадений
    val newStr = str.map { it.toLowerCase() }.joinToString(separator = "")
    var word = ""
    for (i in newStr.indices) {
        val twoWordsRegex = """(\Q$word\E(?=\s\Q$word\E))""".toRegex()
        if (str[i] == ' ') {//отслеживаем пробел
            if (twoWordsRegex.find(newStr) != null)
                setofIndices += twoWordsRegex.find(newStr)!!.range.first else word = ""
        } else word += newStr[i]//создает слово
    }
    return if (setofIndices.isNotEmpty()) setofIndices.sorted().first() else -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isEmpty()) return ""
    val list = description.split("; ")
    var max = Double.NEGATIVE_INFINITY
    var namemax = ""
    val findDotPrice = """(\s\d+\.\d+)""".toRegex()
    val findPrice = """(\s\d+)""".toRegex()
    for (i in list.indices) {
        val resultFindDotPrice = findDotPrice.find(list[i])
        if (resultFindDotPrice != null && resultFindDotPrice.value.toDouble() >= max) {
            max = resultFindDotPrice.value.toDouble()
            namemax = Regex("""(\s.*)""").replace(list[i], "")
        }
        val resultFindPrice = findPrice.find(list[i])
        if (resultFindPrice != null && resultFindPrice.value.toDouble() >= max) {
            max = resultFindPrice.value.toDouble()
            namemax = Regex("""(\s.*)""").replace(list[i], "")
        }
    }
    return namemax
}


/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val numbers = mapOf(
    (900 to "CM"),
    (1000 to "M"),
    (400 to "CD"),
    (500 to "D"),
    (90 to "XC"),
    (100 to "C"),
    (40 to "XL"),
    (50 to "L"),
    (9 to "IX"),
    (10 to "X"),
    (4 to "IV"),
    (5 to "V"),
    (1 to "I")
)

fun fromRoman(roman: String): Int {
    var newroman = roman
    var sum = 0
    if ((Regex("""[IVXLCDM]""").replace(roman, "") != "") || roman.isEmpty()) return -1
    for ((decimalNumber, romanNumber) in numbers) {
        val romanregex = "($romanNumber+)".toRegex()
        val findResult = romanregex.find(newroman)
        if (findResult != null) {
            sum += decimalNumber * findResult.value.length / romanNumber.length
            newroman = romanregex.replaceFirst(newroman, "")
        }
    }
    return sum
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */


fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
