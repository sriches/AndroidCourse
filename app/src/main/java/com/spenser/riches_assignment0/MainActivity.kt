/* AUTHOR:      Spenser Riches, U1072910
 * DATE:        Sept 1, 2017
 * Course:      CS 4530
 * Assignment:  0
 */

/*  ASSIGNMENT 0 REQUIREMENTS

    You are to create a Kotlin program that uses various features of the language to parse a long String and present
    output to the user. The String is found in the following text file:

    Assignment 0 Input.txt

    I recommend copying and pasting it into a String value within your code, but if you'd like to figure out how to
    include the file in your Android Studio project and read from it, feel free. The specific objectives and expected
    output are:

    All of the code you write for this assignment must be in Kotlin.
    You must use (execute code that operates on) at least one of each of the following things in your program:
        A String
        A Lambda
        A Function (Other than the onCreate function in your MainActivity)
        A Class
        An Object
        An Enum Class
        A higher-order function such as map, filter, reduce, or fold.

    You must find the longest word (a word is defined as sequence of non-whitespace characters) in the input which
    meets ALL of the following criteria and log that word to the console:
        The word contains an uppercase letter.
        The word contains only letters (no punctuation or numbers).
        The word contains an even number of characters.
        The word appears more than once in the input.
        If there are multiple words that meet the conditions above, you should choose the one whose first character
         appears earliest in the alphabet.
        If there are still multiple distinct words that meet all the above conditions, you should choose the one which
         appeared earliest in the input String.

    You can get 5 extra points on this assignment if, in addition to ALL of the requirements above, you also update the
    "Hello World!" text in the MainActivity to display your answer. Keep in mind that we may change the input file (or
    your input String value) for grading purposes, so make sure you handle all these requirements even if some are
    redundant for this input.

    Please hand in your ENTIRE project directory, compressed into a .zip, .rar, .tar.gz, or .7z archive.
 */

package com.spenser.riches_assignment0

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // String given in assignment
        val testString: String = "Using Kotlin for Android Development Kotlin is a great fit for developing Android applications, " +
                "bringing all of the advantages of a modern language to the Android platform without introducing any new restrictions: " +
                "Compatibility: Kotlin is fully compatible with JDK 6, ensuring that Kotlin applications can run on older Android devices " +
                "with no issues. The Kotlin tooling is fully supported in Android Studio and compatible with the Android build system. " +
                "Performance: A Kotlin application runs as fast as an equivalent Java one, thanks to very similar bytecode structure. With " +
                "Kotlin's support for inline functions, code using lambdas often runs even faster than the same code written in Java. " +
                "Interoperability: Kotlin is 100% interoperable with Java, allowing to use all existing Android libraries in a Kotlin " +
                "application. This includes annotation processing, so databinding and Dagger work too. Footprint: Kotlin has a very compact " +
                "runtime library, which can be further reduced through the use of ProGuard. In a real application, the Kotlin runtime adds only " +
                "a few hundred methods and less than 100K to the size of the .apk file. Compilation Time: Kotlin supports efficient incremental " +
                "compilation, so while there's some additional overhead for clean builds, incremental builds are usually as fast or faster than " +
                "with Java. Learning Curve: For a Java developer, getting started with Kotlin is very easy. The automated Java to Kotlin converter " +
                "included in the Kotlin plugin helps with the first steps. Kotlin Koans offer a guide through the key features of the language with " +
                "a series of interactive exercises. Kotlin for Android Case Studies Kotlin has been successfully adopted by major companies, and " +
                "a few of them have shared their experiences: Pinterest has successfully introduced Kotlin into their application, used by 150M " +
                "people every month. Basecamp's Android app is 100% Kotlin code, and they report a huge difference in programmer happiness and " +
                "great improvements in work quality and speed. Keepsafe's App Lock app has also been converted to 100% Kotlin, leading to a 30% " +
                "decrease in source line count and 10% decrease in method count. Tools for Android Development The Kotlin team offers a set " +
                "of tools for Android development that goes beyond the standard language features: Kotlin Android Extensions is a compiler " +
                "extension that allows you to get rid of findViewById() calls in your code and to replace them with synthetic compiler-generated " +
                "properties. Anko is a library providing a set of Kotlin-friendly wrappers around the Android APIs, as well as a DSL that lets " +
                "your replace your layout .xml files with Kotlin code. Next Steps Download an install Android Studio 3.0 Preview, which includes " +
                "Kotlin support out of the box. Follow the Getting Started with Android and Kotlin tutorial to create your first Kotlin " +
                "application. For a more in-depth introduction, check out the reference documentation on this site and Kotlin Koans. Another " +
                "great resource is Kotlin for Android Developers, a book that guides you step by step through the process of creating a real " +
                "Android application in Kotlin. Check out Google's sample projects written in Kotlin."


        // Create an object to filter and parse the string
        val parser = StringParser(testString)

        // Get the desired word from the input string as defined by the assignment requirements
        val parsedAndSortedWord = parser.longestInterestingCharacterizedWord()

        // Write the desired word from the input string to the console via a log
        Write(parsedAndSortedWord)

        // Change the main app text
        main_app_text.text = parsedAndSortedWord

        // Use object to update count of parsing the string
        StringParser.stringsParsed.count++
    }
}

/* The StringParser class is instantiated with a given string. It can parse and sort
 * the string into individual words to determine the word as specified in
 * Assignment 0 requirements
 */
class StringParser constructor(var stringData: String){

    // Keep a status in case anyone wants to see what the progress of the string is
    var status : Status = Status.RECEIVED_STRING

    // Create object to store how many strings the StringParser parses
    public object stringsParsed{
        var count = 0
    }

    /* Returns the word that fits Assignment 0 requirements.
     *
     */
    public fun longestInterestingCharacterizedWord() : String {
        // Variables we may use later
        var wordsBeginningWithSameLetter = mutableListOf<String>()
        var longestWordsUsedMoreThanOnce_InOrderOfOriginalString = mutableListOf<String>()

        // Narrow down the string data
        var noPunctOrNums = removePunctuationAndNumbers({ -> Regex("[0|1|2|3|4|5|6|7|8|9|.|!|?|:|%|$|*|(|)|,|\\-|']").replace(stringData, " ")})
        var noPunctOrNumsOrOddLengthWords_List = removeOddLengthWords(noPunctOrNums)
        var noPunctOrNumsOrOddLength_WithUpperCase_List = removeLowerCaseWords(noPunctOrNumsOrOddLengthWords_List)
        var wordsUsedMoreThanOnce_List = removeWordsOnlyUsedOnce(noPunctOrNumsOrOddLength_WithUpperCase_List)

        // Words have been narrowed down. If no words are in the list, return an empty string
        if (wordsUsedMoreThanOnce_List.size == 0) return ""
        // If there's only one word in the list, return the word
        else if(wordsUsedMoreThanOnce_List.size == 1) return wordsUsedMoreThanOnce_List[0]

        // More than one word. Choose the longest word to return
        // Find the longest word
        var longestWordSize = 0
        wordsUsedMoreThanOnce_List.forEach({if(it.length > longestWordSize) longestWordSize = it.length})
        var longestWords = wordsUsedMoreThanOnce_List.filter { it.length == longestWordSize }.toMutableList()

        // If the longestWords list only has one word, return it
        if(longestWords.size == 1) return longestWords[0]

        // More than one word of the same length, now choose the word whose first character appears earliest in the alphabet.
        // Copy the list to use later
        for(w in longestWords){
            longestWordsUsedMoreThanOnce_InOrderOfOriginalString.add(w)
        }

        // Sort the list
        longestWords.sort()

        // Find which letter to search for
        var letterToCompare = wordsUsedMoreThanOnce_List[0][0].toUpperCase()

        for(s in wordsUsedMoreThanOnce_List){
            // Check if the word has the same letter as the first word
            if(s[0].toUpperCase().equals(letterToCompare)){
                // Add the word to the new list
                wordsBeginningWithSameLetter.add(s)
            }
        }

        // Now just working with the new list created above (list of word(s) beginning with the same letter)
        // Check if there's only one word in the list
        if(wordsBeginningWithSameLetter.size == 1){
            return wordsBeginningWithSameLetter[0]
        }
        else{
            // Choose the word which appears earliest in the input string
            for(s in longestWordsUsedMoreThanOnce_InOrderOfOriginalString){
                // If the word is contained in our new list of words which begin with the same letter, return that word
                if(wordsBeginningWithSameLetter.contains(s)){
                    return s
                }
            }
        }

        // If execution gets here, return an empty string
        return ""
    }

    /* This function takes a function as a parameter which should return a string.
     * The function parameter should remove desired punctuation and numbers.
     */
    private fun removePunctuationAndNumbers(function: () -> String) : String{
        status = Status.PARSING
        return function()
    }

    /* Takes a string, splits it into words, and returns a list of all the even-length words
     *
     */
    private fun removeOddLengthWords(input: String) : MutableList<String> {
        status = Status.PARSING

        // Break up all words into an List<String>
        var words = input.split("\\s+".toRegex())

        status = Status.SORTING

        // Create new MutableList<String> to hold words that have an even length
        var evenLengthWords = words.filter { it.length % 2 == 0 }.toMutableList()

        return evenLengthWords
    }


    /* Takes a list of words, then removes all words that do not have a capital letter in them.
     * Returns the list of words where each word has at least one capital letter.
     */
    private fun removeLowerCaseWords(input: MutableList<String>): MutableList<String>{
        status = Status.SORTING

        // Create new MutableList<String> to hold words that contain an uppercase letter
        var wordsWithUpperCase = mutableListOf<String>()
        for (s in input){
            if(s.contains("[A-Z]".toRegex()))
                wordsWithUpperCase.add(s)
        }

        return wordsWithUpperCase
    }

    /* Takes a list of words, then removes all words that are only used once in the original
     * StringParser parameter string. Returns the list of words where each word is used
     * at least twice in the original string.
     */
    private fun removeWordsOnlyUsedOnce(inputToFilter: MutableList<String>) : MutableList<String>{
        status = Status.SORTING

        // Make a new list with words used only once
        var distinctWords = inputToFilter.distinct()

        // List to keep words to return - words that are used more than once from the input list
        var wordsUsedMoreThanOnce = mutableListOf<String>()

        // Check how often each word is used
        for(s in distinctWords){
            var count = 0

            for(w in inputToFilter){
                if(s.equals(w)){
                    // Increase count
                    count++

                    // If count is 2+, add the word to the list to return
                    if(count > 1){
                        wordsUsedMoreThanOnce.add(w)
                        break // break out of inner for loop
                    }
                }
            }
        }

        // Return list of words that are used more than once
        return wordsUsedMoreThanOnce
    }
}

/* Enum class Status is used to determine the status of a StringParser object
 *
 */
enum class Status {RECEIVED_STRING, PARSING, SORTING}

/* Used to write log messages
 */
fun Write(message: String) = Log.i("Assignment 0", message)
