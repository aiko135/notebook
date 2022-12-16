package ktepin.penzasoft.dairy.util

object Util {
    fun intToTimeStr(int: Int):String{
        var str = int.toString();
        if (str.length == 1){
            str = "0${str}"
        }
        return str
    }
}