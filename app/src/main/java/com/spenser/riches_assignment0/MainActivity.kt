/* AUTHOR:      Spenser Riches, U1072910
 * DATE:        Sept 1, 2017
 * Course:      CS 4530
 * Assignment:  0
 */

package com.spenser.riches_assignment0

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // String given in assignment
        val testString: String = "Using Kotlin for Android Development Kotlin is a great fit for developing Android applications, bringing all of the advantages of a modern language to the Android platform without introducing any new restrictions: Compatibility: Kotlin is fully compatible with JDK 6, ensuring that Kotlin applications can run on older Android devices with no issues. The Kotlin tooling is fully supported in Android Studio and compatible with the Android build system. Performance: A Kotlin application runs as fast as an equivalent Java one, thanks to very similar bytecode structure. With Kotlin's support for inline functions, code using lambdas often runs even faster than the same code written in Java. Interoperability: Kotlin is 100% interoperable with Java, allowing to use all existing Android libraries in a Kotlin application. This includes annotation processing, so databinding and Dagger work too. Footprint: Kotlin has a very compact runtime library, which can be further reduced through the use of ProGuard. In a real application, the Kotlin runtime adds only a few hundred methods and less than 100K to the size of the .apk file. Compilation Time: Kotlin supports efficient incremental compilation, so while there's some additional overhead for clean builds, incremental builds are usually as fast or faster than with Java. Learning Curve: For a Java developer, getting started with Kotlin is very easy. The automated Java to Kotlin converter included in the Kotlin plugin helps with the first steps. Kotlin Koans offer a guide through the key features of the language with a series of interactive exercises. Kotlin for Android Case Studies Kotlin has been successfully adopted by major companies, and a few of them have shared their experiences: Pinterest has successfully introduced Kotlin into their application, used by 150M people every month. Basecamp's Android app is 100% Kotlin code, and they report a huge difference in programmer happiness and great improvements in work quality and speed. Keepsafe's App Lock app has also been converted to 100% Kotlin, leading to a 30% decrease in source line count and 10% decrease in method count. Tools for Android Development The Kotlin team offers a set of tools for Android development that goes beyond the standard language features: Kotlin Android Extensions is a compiler extension that allows you to get rid of findViewById() calls in your code and to replace them with synthetic compiler-generated properties. Anko is a library providing a set of Kotlin-friendly wrappers around the Android APIs, as well as a DSL that lets your replace your layout .xml files with Kotlin code. Next Steps Download an install Android Studio 3.0 Preview, which includes Kotlin support out of the box. Follow the Getting Started with Android and Kotlin tutorial to create your first Kotlin application. For a more in-depth introduction, check out the reference documentation on this site and Kotlin Koans. Another great resource is Kotlin for Android Developers, a book that guides you step by step through the process of creating a real Android application in Kotlin. Check out Google's sample projects written in Kotlin."

        Write("HIIIIIIIIIIIIIIIIII")

        val parser = StringParser(testString)

        Write(parser.longestInterestingCharacterizedWord())

    }
}

class StringParser constructor(var stringData: String){


    fun longestInterestingCharacterizedWord() : String {
        // Narrow down the string data
        var noPunctAndNums = removePunctuationAndNumbers(stringData)
        var noPunctAndNumsAndEvenLength_List = removeOddLengthWords(noPunctAndNums)
        var noPunctAndNumsAndEvenLengthWithUpperCase_List = removeLowerCaseWords(noPunctAndNumsAndEvenLength_List)
        var WordsUsedMoreThanOnce_List = removeWordsOnlyUsedOnce(noPunctAndNumsAndEvenLengthWithUpperCase_List, noPunctAndNumsAndEvenLength_List)

        


        return stringData
    }

    private fun removePunctuationAndNumbers(input: String) : String{
        var noPunctAndNums = Regex("[0|1|2|3|4|5|6|7|8|9|.|!|?|:|%|$|*|(|)|,|\\-|']").replace(input, " ")

        Write("\n\n noPunctAndNums: " + noPunctAndNums)

        return noPunctAndNums
    }

    private fun removeOddLengthWords(input: String) : MutableList<String> {
        // Break up all words into an List<String>
        var words = input.split("\\s+".toRegex())
        Write("words length: " + words.size)

        // Create new MutableList<String> to hold words that have an even length
        var evenLengthWords = mutableListOf<String>()
        for (s in words){
            if(s.length%2 == 0)
                evenLengthWords.add(s)
        }
        Write("\nEven Length Words size: " + evenLengthWords.size)

        return evenLengthWords
    }


    private fun removeLowerCaseWords(input: MutableList<String>): MutableList<String>{
        // Create new MutableList<String> to hold words that contain an uppercase letter
        var wordsWithUpperCase = mutableListOf<String>()
        for (s in input){
            if(s.contains("[A-Z]".toRegex()))
                wordsWithUpperCase.add(s)
        }
        Write("\n\nupperCaseList size: " + wordsWithUpperCase.size)

        for(s in wordsWithUpperCase) Write(s)

        return wordsWithUpperCase
    }

    /*
     * TODO: This method is pretty inefficient but gets the job done. Optimize this better later.
     */
    private fun removeWordsOnlyUsedOnce(inputToFilter: MutableList<String>, inputToCompare: MutableList<String>) : MutableList<String>{
        // Create a list to return
        var wordsUsedMoreThanOnce = mutableListOf<String>()

        // Go through all words that are candidates for the longest word with all restrictions placed so far
        for(s in inputToFilter) {
            // Count how many times the word displays in the original string
            var count = 0;
            for(str in inputToCompare){
                // If the words are equal, increase the count
                if (s.toLowerCase().equals(str.toLowerCase())){
                    count++
                }
                // Add word to return list if count is greater than 1
                if (count > 1){
                    wordsUsedMoreThanOnce.add(s)
                    break
                }
            }
        }


        Write("---- Words Used More Than Once: " + wordsUsedMoreThanOnce.size)
        for(s in wordsUsedMoreThanOnce) Write(s)

        // Filter the list so a word does not show up more than once
        var distinctWordsUsedMoreThanOnce = wordsUsedMoreThanOnce.distinct().toMutableList()


        Write("--- Distinct Words Used More than Once: " + distinctWordsUsedMoreThanOnce.size)
        for(s in distinctWordsUsedMoreThanOnce) Write(s)

        return distinctWordsUsedMoreThanOnce
    }




}
// Used to write log messages
fun Write(message: String) = Log.e("General", message)
