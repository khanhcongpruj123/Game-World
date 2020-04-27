package com.icongkhanh.gameworld.data.utils


object StringUtils {

    private fun generateCharMap(str: String): Map<Char, Int> {
        val map: MutableMap<Char, Int> = HashMap()
        var currentChar: Int?
        for (c in str.toCharArray()) {
            currentChar = map[c]
            if (currentChar == null) {
                map[c] = 1
            } else {
                map[c] = currentChar + 1
            }
        }
        return map
    }

    fun isSimilar(str: String, compareStr: String): Boolean {
        var strMap = generateCharMap(str)
        val compareStrMap =
            generateCharMap(compareStr)
        val charSet = compareStrMap.keys
        var similarChars = 0
        var totalStrChars: Int = str.length
        val thisThreshold: Float
        if (totalStrChars < compareStrMap.size) {
            totalStrChars = compareStr.length
        }
        val it: Iterator<*> = charSet.iterator()
        var currentChar: Char
        var currentCountStrMap: Int
        var currentCountCompareStrMap: Int?
        while (it.hasNext()) {
            currentChar = it.next() as Char
            currentCountStrMap = strMap.get(currentChar) ?: 0
            if (currentCountStrMap != null) {
                currentCountCompareStrMap = compareStrMap[currentChar]
                similarChars += if (currentCountCompareStrMap!! >= currentCountStrMap) {
                    currentCountStrMap
                } else {
                    currentCountCompareStrMap
                }
            }
        }
        thisThreshold = similarChars.toFloat() / totalStrChars.toFloat()
        return thisThreshold > 0.75
    }

    fun similarity(s1: String, s2: String): Double {
        var longer = s1
        var shorter = s2
        if (s1.length < s2.length) { // longer should always have greater length
            longer = s2
            shorter = s1
        }
        val longerLength = longer.length
        return if (longerLength == 0) {
            1.0 /* both strings are zero length */
        } else (longerLength - editDistance(
            longer,
            shorter
        )) / longerLength.toDouble()
        /* // If you have Apache Commons Text, you can use it to calculate the edit distance:
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength; */
    }

    fun editDistance(s1: String, s2: String): Int {
        var s1 = s1
        var s2 = s2
        s1 = s1.toLowerCase()
        s2 = s2.toLowerCase()
        val costs = IntArray(s2.length + 1)
        for (i in 0..s1.length) {
            var lastValue = i
            for (j in 0..s2.length) {
                if (i == 0) costs[j] = j else {
                    if (j > 0) {
                        var newValue = costs[j - 1]
                        if (s1[i - 1] != s2[j - 1]) newValue = Math.min(
                            Math.min(newValue, lastValue),
                            costs[j]
                        ) + 1
                        costs[j - 1] = lastValue
                        lastValue = newValue
                    }
                }
            }
            if (i > 0) costs[s2.length] = lastValue
        }
        return costs[s2.length]
    }
}