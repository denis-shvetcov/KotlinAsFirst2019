@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import ru.spbstu.wheels.PositiveInfinity

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val s = mutableMapOf<Int, List<String>>()
    val f = mutableListOf<String>()
    var maxgrade = 0
    for ((name, grade) in grades) {
        if (grade > maxgrade) maxgrade = grade
    }
    for (i in 0..maxgrade) {
        for ((name, grade) in grades) {
            if (grade == i) {
                f.add(name)
            }
        }
        if (f.isNotEmpty()) s.put(i, f.toList())
        f.clear()
    }
    return s
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if (a[key] == b[key]) continue else return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): MutableMap<String, String> {
    for ((element1, element2) in b) {
        if (a[element1] == element2) a.remove(element1)
    }
    return a
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val set1 = a.toSet()
    val set2 = b.toSet()
    return set1.intersect(set2).toList()
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val all = mutableMapOf<String, MutableList<String>>()
    val finished = mutableMapOf<String, String>()
    for ((name, number) in mapA) {
        all.put(name, mutableListOf(number))
    }
    for ((name, number) in mapB) {
        if (!all.containsKey(name)) all.put(name, mutableListOf(number)) else {
            if (!all[name]!!.contains(number)) all[name]!!.add(number)
        }
    }
    for ((name, list) in all) {
        finished.put(name, list.joinToString(separator = ", "))
    }
    return finished
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val averagelist = mutableMapOf<String, Double>()
    val names = mutableListOf<String>()
    var a: String = ""
    var count = 0
    var sum = 0.0
    for ((name, price) in stockPrices) {
        sum = 0.0
        a = name
        count = 0
        if (!names.contains(name)) {
            names.add(name)
            for ((name, price) in stockPrices) {
                if (name == a) {
                    sum += price
                    count++
                }
            }
            averagelist.put(name, sum / count)
        }
    }
    return averagelist
}


/**
 * Средняя
 *
 * Входными  данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var need: String? = null
    var min = Double.POSITIVE_INFINITY
    for ((name, pair) in stuff) {
        if ((pair.first == kind) && (pair.second < min)) {
            min = pair.second
            need = name
        }
    }
    return need
}


/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    if (chars.isEmpty() && word.isEmpty()) return true
    if (chars.isEmpty() && word.isNotEmpty()) return false
    for (element in chars) {
        if (element !in word) return false
    }
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val repeats = mutableMapOf<String, Int>()
    for (element in list) {
        repeats[element] = (repeats[element] ?: 0) + 1
    }
    return repeats.filterValues { it > 1 }
}


/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    if (words.isEmpty()) return false
    val set = mutableSetOf(mutableMapOf<Char, Int>())
    var pair = mutableMapOf<Char, Int>()
    for (word in words) {
        pair = mutableMapOf<Char, Int>()
        for (letter in word) {
            pair[letter] = pair.getOrDefault(letter, 0) + 1
        }
        set.add(pair)
    }
    set.removeIf { it == emptyMap<Char, Int>() }
    return set.size < words.size
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val all = mutableMapOf<String, MutableSet<String>>()
    for ((name, set) in friends) {
        all.putIfAbsent(name, mutableSetOf())
        for (element in set) {
            all.putIfAbsent(element, mutableSetOf())
        }
    }//заполнили ALL пустыми значениями всех имен
    for ((name, set) in friends) {
        all[name]!!.addAll(set)
    }
    for ((name, set) in all) {
        for (element in set) {
            if (friends.containsKey(element)) all[name]!! += friends[element]!!
        }
    }//добавили все рукопожатия (включая лишние)
    for ((name, handshakes) in all) handshakes.removeIf { it == name }
    return all
}


/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    fun firstsecondindices(list: List<Int>, first: Int, second: Int): Pair<Int, Int> {
        var odin = 0
        var dva = 0
        for (i in list.indices) {
            if (list[i] == first) {
                odin = i
                break
            }
        }
        for (i in list.indices) {
            if (list[i] == second && odin != i) {
                dva = i
                break
            }
        }
        return Pair(odin, dva)
    }

    fun pairsort(pair: Pair<Int, Int>): Pair<Int, Int> {
        val first = pair.first
        val second = pair.second
        return if (first < second) Pair(first, second) else Pair(second, first)
    }

    val chisla = mutableListOf<Int>()
    for (element in list) {
        if (chisla.contains(element) && element * 2 == number) return firstsecondindices(
            list,
            number,
            number
        ) //если есть равные значения и они в 2 раза меньше number
        if (!chisla.contains(element)) chisla += element
    }
    var max = 0
    var index = 0
    while (chisla.isNotEmpty()) {
        max = chisla.max()!!
        index = chisla.indexOf(max)
        if (chisla.contains(number - max) && chisla.indexOf(number - max) != index)
            return pairsort(firstsecondindices(list, number - max, max))
        chisla.removeAt(index)
    }
    return Pair(-1, -1)
}


/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()