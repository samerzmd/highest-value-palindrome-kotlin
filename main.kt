import java.util.*

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nk = scan.nextLine().split(" ")

    val n = nk[0].trim().toInt()

    val k = nk[1].trim().toInt()

    val s = scan.nextLine()

    val result = highestValuePalindrome(s, n, k)

    println(result)
}

internal fun highestValuePalindrome(inputString: String, numberOfDigits: Int, maximumDigitsToBeAltered: Int): String {
    var maxDigiToAlter = maximumDigitsToBeAltered
    val palindrome: String
    val allDigitsWithoutNine = inputString.replace("9".toRegex(), "").length

    if (allDigitsWithoutNine <= maxDigiToAlter) {
        return palindromeToNines(inputString)
    }

    val strBuilder = StringBuilder(inputString)
    var notMatching = 0

    for (i in 0 until numberOfDigits / 2) {
        val mirrorIndex = numberOfDigits - 1 - i
        if (strBuilder[i] != strBuilder[mirrorIndex]) {
            notMatching++
        }
    }

    var transition = 0
    if (notMatching < maxDigiToAlter) {

        for (i in 0 until numberOfDigits / 2) {
            val mirrorIndex = numberOfDigits - 1 - i
            if (strBuilder[i] != '9' && strBuilder[mirrorIndex] != '9') {
                if (strBuilder[i] != strBuilder[mirrorIndex]) {
                    notMatching--
                }
                if (notMatching > maxDigiToAlter - 2) {
                    transition = i
                    break
                }

                strBuilder.setCharAt(i, '9')
                strBuilder.setCharAt(mirrorIndex, '9')
                maxDigiToAlter -= 2

                if (notMatching == maxDigiToAlter) {
                    transition = i + 1
                    break
                }

            } else if (strBuilder[i] != strBuilder[mirrorIndex]) {

                if (Character.getNumericValue(strBuilder[i]) > Character
                                .getNumericValue(strBuilder[mirrorIndex])) {
                    strBuilder.setCharAt(mirrorIndex, strBuilder[i])
                    maxDigiToAlter--
                    notMatching--
                } else {
                    strBuilder.setCharAt(i, strBuilder[mirrorIndex])
                    maxDigiToAlter--
                    notMatching--
                }
            }
        }
    }

    for (i in transition until numberOfDigits / 2) {
        val mirrorIndex = numberOfDigits - 1 - i
        if (strBuilder[i] != strBuilder[mirrorIndex]) {
            if (Character.getNumericValue(strBuilder[i]) > Character
                            .getNumericValue(strBuilder[mirrorIndex])) {
                strBuilder.setCharAt(mirrorIndex, strBuilder[i])
                maxDigiToAlter--
                if (maxDigiToAlter == 0) {
                    break
                }
            } else {
                strBuilder.setCharAt(i, strBuilder[mirrorIndex])
                maxDigiToAlter--
                if (maxDigiToAlter == 0) {
                    break
                }
            }
        }
    }

    if (!isPalindrome(strBuilder.toString())) {
        return "-1"
    }

    if (strBuilder.toString().length % 2 != 0 && maxDigiToAlter > 0) {
        val midIndex = strBuilder.toString().length / 2
        if (strBuilder[midIndex] != '9') {
            strBuilder.setCharAt(midIndex, '9')
        }
    }

    palindrome = strBuilder.toString()
    return palindrome
}

fun isPalindrome(inputString: String): Boolean {
    val strBuilder = StringBuilder(inputString)
    return strBuilder.reverse().toString() == inputString
}

private fun palindromeToNines( inputString: String): String {
    val regex = "[0-8]"
    return inputString.replace(regex.toRegex(), "9")
}
