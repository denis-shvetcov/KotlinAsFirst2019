@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.lang.IllegalArgumentException
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
        if (sqrt(sqr(center.x - other.center.x) + sqr(center.y - other.center.y)) > (radius + other.radius)) {
            sqrt(sqr(center.x - other.center.x) + sqr(center.y - other.center.y)) - (radius + other.radius)
        } else 0.0

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(center) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()

    fun center(): Point {

        var center = Point(0.0, 0.0)
        if (begin.x < end.x) center =
            Point(begin.x + (end.x - begin.x) / 2, 0.0)
        else center = Point(begin.x - (begin.x - end.x) / 2, 0.0)
        if (end.y > begin.y) center =
            Point(center.x, begin.y + (end.y - begin.y) / 2) else center =
            Point(center.x, begin.y - (begin.y - end.y) / 2)
        return center
    }
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    try {
        val list = mutableSetOf<Point>()
        list.addAll(points)
        var max1 = Point(0.0, 0.0)
        var max2 = Point(0.0, 0.0)
        for (p1 in points.indices) {
            for (p2 in p1 until points.size) {
                if (points[p2].distance(points[p1]) > max2.distance(max1)) {
                    max2 = points[p1]
                    max1 = points[p2]
                }
            }
        }

        return Segment(max1, max2)

    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException()
    }
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val center = diameter.center()

    val radius = diameter.begin.distance(diameter.end) / 2
    return Circle(center, radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val det = sin(angle) * cos(other.angle) - sin(other.angle) * cos(angle)
        val detX = -b * cos(other.angle) + other.b * cos(angle)
        val detY = sin(angle) * other.b - b * sin(other.angle)
        return Point(detX / det, detY / det)
//        val y = (b * sin(other.angle) - other.b * sin(angle)) / (cos(angle) * sin(other.angle) - cos(other.angle) * sin(
//            angle
//        ))
//        val x = (y * cos(angle) - b) / sin(angle)
//        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

fun angleCounter(a: Point, b: Point): Double {
    var angle = 0.0
    if (a.x == b.x) angle = PI / 2 else angle = atan((a.y - b.y) / (a.x - b.x))
    if (angle < 0) angle += PI
    return angle % PI
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */

fun lineBySegment(s: Segment): Line {
    val angle = angleCounter(s.begin, s.end)
    val b = s.begin.y * cos(angle) - sin(angle) * s.begin.x
    return Line(b, angle)
}


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val segment = Segment(a, b)
    return lineBySegment(segment)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val point = Segment(a, b).center()
    val angle = angleCounter(a, b)
    val perpendicularAngel = (angle + PI / 2) % PI

    return Line(point, perpendicularAngel)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    try {
        var max = Double.POSITIVE_INFINITY
        var pair = (Circle(Point(0.0, 0.0), 0.0) to Circle(Point(0.0, 0.0), 0.0))
        for (first in 0 until circles.size - 1) {
            for (second in first + 1 until circles.size) {
                if (circles[first].distance(circles[second]) < max) {
                    max = circles[first].distance(circles[second])
                    pair = (circles[first] to circles[second])
                }
            }

        }
        return pair
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException()
    }
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val perpAB = bisectorByPoints(a, b)
    val perpAC = bisectorByPoints(a, c)
    val cross = perpAB.crossPoint(perpAC)
    println(cross)
    return Circle(cross, a.distance(cross))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO() /*{
    try {

    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException()
    }
}*/


