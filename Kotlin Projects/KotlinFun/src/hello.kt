fun main(args: Array<String>) {
    println("Hello Kotlin!")

    // variables
    var age = 20
    age = 30 // a var can be assigned again
//    age = "hello" // Kotlin automatically save age as an int, so we cannot assign a string to it
    val abc = 15
//    abc = 20 // val cannot be assigned again
    var age2: Int = 28 // explicitly specify the type of age2 to be an Int
    var favCandy = "Snickers"
    println("I am $age years old and I love $favCandy") // use variables in a string

    // maths and comments
    var weight = 188.6 // weight is a double
    var radius = 6
    var pi = 3.14
    var c1 = pi * radius * 2
    println(c1) // 37.68
    var c2 = pi.toInt() * radius * 2
    println(c2) // 36

    // if statements and booleans
    var isTheDogAlive : Boolean = true
    if (isTheDogAlive) {
        println("The dog is alive")
    } else {
        println("The dog is dead")
    }
    var name = "Nick"
    if (name == "Nick") {
        println("Nick is baws!")
    }

    // lists and arrays
    var luckyNumbers = listOf(1, 6, 78, 3)
    var lucky2 = arrayOf(1, 6, 78, 3)
    // empty array
    var lucky3 = arrayOf<Int>()
    // mutable array
    var lucky4 = mutableListOf(1, 3, 6)
    lucky4.add(3, 0)
    lucky4.add(10)
    println(lucky4)
    println(lucky4.size)

    // for loops
    for (x in 1..10) { // loop from 1 to 10 (important: both inclusive)
        println(x) // 1 2 3 4 5 6 7 8 9 10
    }
    var favCandies = listOf("Snickers", "100 grand bar", "Fun Dip")
    for (candy in favCandies) {
        println(candy) // Snickers   100 grand bar   Fun DIp
    }
    // loop through the number 1 to 200 and print out all odd numbers
    for (x in 1 .. 200) {
        if (x % 2 == 1) {
            println(x)
        }
    }

    // maps
    var dogs = mutableMapOf("Fido" to 8, "Sarah" to 17, "Sean" to 6)
    println(dogs["Fido"]) // 8
    dogs["Jeremy"] = 52 // add a new entry
    var nicksSlang = mapOf<String, String>("Programming Cheese" to "Coding and eating macncheese", "grub" to "eat food", "Rick" to "Summer of Nick")

    // functions
//    fun hello(): String { // this function returns a String
//        println("Hello World")
//        return "Hello World"
//    }
//    println(hello())
    fun hello(name: String = "Rick"): String { // there is a parameter of type String. and the default value is "Rick"
        return "Hello $name"
    }
    println(hello("Nick")) // Hello Nick
    println(hello()) // Hello Rick
    fun addNumbers(num1: Int, num2: Int) : Int {
        return num1 + num2
    }
    fun addNumbers2(num1: Int, num2: Int) = num1 + num2 // a simplified method

    // classes
    class Dog(var name: String, var age: Int) {
    }
    var myDog = Dog("Fido", 5)
    println(myDog.name)
    class Cat {
        var catName: String
        var catAge: Int
        var furColor: String
        constructor(name: String, age: Int, color: String) { // this is a constructor of this class
            this.catName = name
            this.catAge = age
            this.furColor = color
        }
        constructor() { // another constructor
            this.catName = ""
            this.catAge = 0
            this.furColor = ""
        }
        fun catInfo() {
            println("$catName is $catAge years old, and it is $furColor")
        }
    }
    var myCat = Cat("Sarah", 10, "Orange")
    myCat.catInfo()

    // Nullable
    var anotherAge: Int? = 28 // Int? makes this variable nullable
    anotherAge = null
    anotherAge
    var newAge = age!! // turn this variable from nullable to non-nullable
    if (anotherAge != null) {
        age!!
    }
    var dogsMap = mapOf("Fido" to 8)
    println(dogsMap["abc"]) // null
    var dogAge = dogsMap["abc"] // dogAge is null
    // String nullable
    var stringNullable: String? = "Snickers"
    stringNullable = null
}