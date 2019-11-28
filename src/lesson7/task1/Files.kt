@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import lesson3.task1.pow10
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val newsubstrings = substrings.toSet()
    val map = mutableMapOf<String, Int>()
    for (element in newsubstrings) map[element] = 0
    for (line in File(inputName).readLines()) {
        for (string in substrings) {
            for (word in line.split(" ")) {
                val templist = mutableListOf<String>()
                if (word.length >= string.length) {
                    for (i in 0..(word.length - string.length)) {
                        templist.add(word.substring(i until i + string.length))
                    }
                    for (element in templist) {
                        if (element.toLowerCase() == string.toLowerCase()) map[string] =
                            map.getOrDefault(string, 0) + 1
                    }
                }
            }
        }
    }
    return map
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
val compareMap = mapOf('ю' to 'у', 'я' to 'а', 'ы' to 'и') //эквиваленты

fun sibilants(inputName: String, outputName: String) {
    val newText = File(outputName).bufferedWriter() //файл для текста
    for (line in File(inputName).readLines()) {
        val wordList = mutableListOf<String>()
        for (word in line.split(" ")) {
            var newword = word
            val regex = """([чщЧЩжЖшШ][юЮяЯыЫ])""".toRegex()
            while (regex.find(newword) != null) {
                val neednotToChangeMatchResult = regex.find(newword)//согласная, ее менять не надо (MatchResult)
                val needToChangeMatchResult = regex.find(newword) //буква, которую нужно изменить (MatchResult)
                var neednotToChange = ' ' //согласная, ее менять не надо
                var needToChange = ' ' //буква, которую нужно изменить
                if (needToChangeMatchResult != null && neednotToChangeMatchResult != null) {
                    neednotToChange = neednotToChangeMatchResult.value[0]
                    needToChange = needToChangeMatchResult.value[1]
                }
                if (needToChange.isUpperCase()) newword = newword.replace(
                    regex.find(newword)!!.value,
                    (neednotToChange.toString() + compareMap[needToChange.toLowerCase()]?.toUpperCase())
                )
                else
                    newword = newword.replace(
                        regex.find(newword)!!.value,
                        (neednotToChange.toString() + compareMap.getValue(needToChange))
                    )
            }
            wordList.add(newword)
        }
        newText.write(wordList.joinToString(separator = " "))
        newText.newLine()
    }
    newText.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val newText = File(outputName).bufferedWriter()
    var maxlength = 0
    var newline = ""
    try {
        for (line in File(inputName).readLines()) {
            if (line.trim().trimIndent().length > maxlength) maxlength = line.trim().trimIndent().length
        }
        for (line in File(inputName).readLines()) {
            newline = ""
            newline += " ".repeat((maxlength - line.trim().length) / 2 - (line.trim().length - line.trim().trimIndent().length))
            newline += line.trim().trimIndent()
            newText.write(newline)
            newText.newLine()
        }
        newText.close()
    } finally {
        newText.close()
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val newText = File(outputName).bufferedWriter()
    var maxlength = 0
    var newLine = ""
    var wordList = mutableListOf<String>()
    val findSpace = Regex("""(\s+)""")
    for (line in File(inputName).readLines()) {
        if (findSpace.replace(line, " ").length > maxlength) {
            maxlength = findSpace.replace(line.trim(), " ").length //самая длинная строка
        }
    }
    for (line in File(inputName).readLines()) {
        if (findSpace.replace(line, "") == "") newText.newLine() else { //проверка пустой строки
            if (findSpace.find(line.trim()) == null) newText.write(line.trim()) else { //проверка, что в строке одно слово
                newLine = findSpace.replace(line.trim(), " ")
                wordList = newLine.split(" ").toMutableList()
                for (i in 0 until maxlength * maxlength) {
                    if (newLine.length != maxlength) {
                        wordList[i % wordList.size] += " " //с помощью % обеспечиваем добавление пробелов "по кругу"
                        newLine = wordList.joinToString(separator = " ").trim()
                    }
                }
                newText.write(wordList.joinToString(separator = " ").trim())
            }
            newText.newLine()
        }
    }
    newText.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    var words = mutableMapOf<String, Int>()
    var tempList = listOf<String>()
    val excessKey = mutableListOf<String>() //лишние ключи
    var counter = 0
    for (line in File(inputName).readLines()) {
        tempList = emptyList()
        if (Regex("""([a-zA-Zа-яА-ЯёЁ]+)""").find(line) != null)
            tempList = Regex("""([a-zA-Zа-яА-ЯёЁ]+)""").findAll(line)
                .toList().map { it.value.toLowerCase() }
        for (element in tempList) {
            words[element] = words.getOrDefault(element, 0) + 1

        }
    }
    words = words.toList().sortedBy { (_, value) -> value }.toMap().toMutableMap()
    for ((name, _) in words) excessKey.add(name)
    return if (words.size < 20) words else words - excessKey.subList(0, excessKey.size - 20)
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val newText = File(outputName).bufferedWriter()
    val newDictionary = dictionary.map { (char, string) -> char.toLowerCase() to string.toLowerCase() }.toMap()
    for (char in File(inputName).readText()) {
        if (char.isUpperCase()) {
            newText.write(newDictionary.getOrDefault(char.toLowerCase(), char.toString()).capitalize())
        } else {
            newText.write(newDictionary.getOrDefault(char.toLowerCase(), char.toString()))
        }
    }
    newText.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    var longList = mutableListOf<String>()
    val file = File(outputName).bufferedWriter()
    val setOfLetters = mutableSetOf<Char>()
    var maxlength = 0
    for (word in File(inputName).readLines()) {
        setOfLetters.clear()
        word.toLowerCase().map { setOfLetters.add(it) }
        if (setOfLetters.size == word.length && word.length == maxlength) {
            longList.add(word)
        } else {
            if (setOfLetters.size == word.length && word.length > maxlength) {
                maxlength = word.length
                longList = mutableListOf(word)
            }
        }
    }
    file.write(longList.joinToString(separator = ", "))
    file.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    var newLine = ""
    val file = File(outputName).bufferedWriter()
    file.write("<html>\n<body>\n<p>")
    for (line in File(inputName).readLines()) {
        newLine = line
        if (line.replace(" ", "") == "") file.write("</p>\n<p>")
        while (Regex("""(\*\*)""").find(newLine) != null) {
            newLine = newLine.replaceFirst(Regex("""(\*\*)""").find(newLine)!!.value, "<b>")
            newLine = newLine.replaceFirst(Regex("""(\*\*)""").find(newLine)!!.value, "</b>")
        }
        while (Regex("""((?<!\*)\*(?!\*))""").find(newLine) != null) {
            newLine = newLine.replaceFirst(Regex("""((?<!\*)\*(?!\*))""").find(newLine)!!.value, "<i>")
            newLine = newLine.replaceFirst(Regex("""((?<!\*)\*(?!\*))""").find(newLine)!!.value, "</i>")

        }
        while (Regex("""(~~)""").find(newLine) != null) {
            newLine = newLine.replaceFirst(Regex("""(~~)""").find(newLine)!!.value, "<s>")
            newLine = newLine.replaceFirst(Regex("""(~~)""").find(newLine)!!.value, "</s>")
        }
        file.write(newLine)
        file.newLine()
    }
    file.write("</p>\n</body>\n</html>")
    file.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {} /*{
    var currentListType = ""
    var currentspace = 0
    val newList = File(outputName).bufferedWriter()
    newList.write("<html>\n<body>\n")
    for (line in File(inputName).readLines()) {
        if (Regex("""(\s+(?=\*|\.))""").find(line)?.value?.length ?: 0 > currentspace ||
            Regex("""(\s+(?=\*|\.))""").find(line)?.value?.length ?: 0 == 0
        ) {
            if (Regex("""[\*\.]""").find(line)!!.value == "*") {
                newList.write("<ul>\n")
                if (Regex("""(\s+(?=\*|\.))""").find(line)?.value?.length ?: 0 != 0) currentspace += 4
                currentListType = "*"
            } else {
                currentspace += 4
                newList.write("<ol>\n")
                currentListType = "."
            }
        } else {
            if (Regex("""(\s+(?=\*|\.))""").find(line)?.value?.length!! ?: 0 < currentspace ||
                Regex("""(\s+(?=\*|\.))""").find(line)?.value?.length ?: 0 == 0) {
                if (currentListType == "*") {
                    newList.write("</ul>\n<li>\n")
                    currentspace -= 4
                    currentListType = Regex("""[\*\.]""").find(line)!!.value
                } else {
                    currentspace -= 4
                    newList.write("</ol>\n<li>\n")
                    currentListType = Regex("""[\*\.]""").find(line)!!.value
                }
            }
        }
        if (currentListType == ".") {
            newList.write("<li>\n${Regex("""[\.\d]""").replace(line, "")}")
            newList.newLine()
        } else {
            newList.write("<li>\n${Regex("""[\*]""").replace(line, "")}")
            newList.newLine()
        }
    }

    newList.write("</body>\n</html>")
    newList.close()
}*/

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    var newRhv = rhv
    val stringResult = (lhv * rhv).toString()
    result.write(" ".repeat(stringResult.length + 1 - digitNumber(lhv)) + lhv + "\n")
    result.write("*" + " ".repeat(stringResult.length - digitNumber(rhv)) + rhv + "\n")
    result.write("-".repeat(stringResult.length + 1) + "\n")
    for (i in 0 until digitNumber(rhv)) {
        val localMultiplication = lhv * (newRhv % 10)
        if (i > 0) result.write(
            "+" + " ".repeat(stringResult.length - digitNumber(localMultiplication) - i) +
                    localMultiplication + "\n"
        ) else
            result.write(" ".repeat(stringResult.length + 1 - digitNumber(localMultiplication)) + localMultiplication + "\n")
        newRhv /= 10
    }
    result.write("-".repeat(stringResult.length + 1) + "\n")
    result.write(" $stringResult")
    result.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    var index = 0
    var divinded = 0
    var localQuotient  = 0
    var divisionCounter = 0 //нужен для частного случая первого деления
    var nextDigit = 0
    var underDottedResult = 0
    var subtrahendLine = ""
    var dottedLine = ""
    var underDottedLine = ""
    result.write(" " + lhv + " | " + rhv)
    //поиск индекса первого возможного делимого
    if (lhv > rhv) {
        for (i in 0..digitNumber(lhv)) {
            if ((lhv.toString().substring(0..i).toInt()) / rhv != 0) {
                index = i
                break
            }
        }
    }
    while (index != digitNumber(lhv)) {
        divisionCounter++ //увеличиваем счетчик делений
        //поиск делимого
        if (divisionCounter == 1) divinded = lhv.toString().substring(0..index).toInt() else divinded = underDottedResult
        localQuotient = (divinded / rhv) * rhv
        //определение разряда, следующего за делимым, если последнее деление, то разряд = 123, то есть не существует
        if (index >= digitNumber(lhv) - 1) nextDigit = 123 else nextDigit =
            (lhv.toString()).get(index + 1).toString().toInt()
        //определение результата под пунктирной линией, если последнее деление, то не нужно умножать на 10
        if (index >= digitNumber(lhv) - 1) underDottedResult = (divinded - localQuotient) else underDottedResult =
            (divinded - localQuotient) * 10 + nextDigit
        result.newLine()
        subtrahendLine = "-$localQuotient"
        // цикл обрабатывает три строки: вычитаемое, пунктирная, результат вычитания, если третья строка пустая, то мы не добавляем пробелы
        if (underDottedLine.isNotEmpty()) subtrahendLine =
            (subtrahendLine.reversed() + (" ").repeat(underDottedLine.length - subtrahendLine.length)).reversed()
        //создается пунктирная строка и к ней добавляются пробелы
        dottedLine = "-".repeat(Regex("""[\s]""").replace(subtrahendLine, "").length)
        dottedLine = (dottedLine.reversed() + " ".repeat(subtrahendLine.length - dottedLine.length)).reversed()
        //создается строка результата вычитания, в частных случаях нужно прописывать незначащий ноль
        // также в последней строке исключается случай записи двух нулей
        if (divinded - localQuotient == 0 && nextDigit != 123) underDottedLine = "0$underDottedResult" else underDottedLine = "$underDottedResult"
        underDottedLine = (underDottedLine.reversed() + (" ").repeat(subtrahendLine.length - underDottedLine.length + 1)).reversed()
        //в последней строке пробелы не добавляются, поэтому нужно удалить лишний пробел, чтобы выровнять по правому краю
        if (index >= digitNumber(lhv) - 1) underDottedLine = underDottedLine.removeRange(0..0)
        //если делим первый раз, то второй строке нужно добавить результат частного
        if (divisionCounter == 1) {
            result.write(subtrahendLine + " ".repeat(4 + digitNumber(lhv) - subtrahendLine.length) + lhv / rhv + "\n")
        } else result.write(subtrahendLine + "\n")
        result.write(dottedLine + "\n")
        result.write(underDottedLine)
        index++
    }
    result.close()
}


