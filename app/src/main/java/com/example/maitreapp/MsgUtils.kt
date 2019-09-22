package com.example.maitreapp

class MsgUtils constructor() {
    companion object{
        internal lateinit var lastFoodCodes:ArrayList<Int>
        internal lateinit var lastFoodQtys:ArrayList<Int>
    }


    fun tableItems(msg: String): ArrayList<String> {
        var content = msg.substringAfter("[").substringBefore("]")
        var listNoNumbers = content.split("[1-9]".toRegex())

        var i = 0
        var foodList = ArrayList<String>()
        for (food in listNoNumbers) {
            if (!listNoNumbers[i].contains("table")) continue
            foodList.add(listNoNumbers[i].substringAfter("table(").substringBefore(","))
            i++
        }
        return foodList
    }

    fun fridgeItems(msg:String) : ArrayList<String>{
        var content = msg.substringAfter("[").substringBefore("]")
        var listNoNumbers = content.split("[1-9]".toRegex())
        var i=0
        var foodList = ArrayList<String>()
        for(food in listNoNumbers){
            if(!food.contains("content")) continue
            foodList.add(food.substringAfter("content(").substringBefore(","))
            i++
        }
        return foodList
    }

    fun dishwasherItems(msg: String): ArrayList<String> {
        var content = msg.substringAfter("[").substringBefore("]")
        var listNoNumbers = content.split("[1-9]".toRegex())
        var i = 0
        var foodList = ArrayList<String>()
        for (food in listNoNumbers) {
            if (!listNoNumbers[i].contains("dishwasher")) continue
            foodList.add(listNoNumbers[i].substringAfter("dishwasher(").substringBefore(","))
            i++
        }
        return foodList
    }

    fun pantryItems(msg: String): ArrayList<String> {
        var content = msg.substringAfter("[").substringBefore("]")
        var listNoNumbers = content.split("[1-9]".toRegex())

        var i = 0
        var foodList = ArrayList<String>()
        for (food in listNoNumbers) {
            if (!listNoNumbers[i].contains("pantry")) continue
            foodList.add(listNoNumbers[i].substringAfter("pantry(").substringBefore(","))
            i++
        }
        return foodList
    }

    fun getNumbers(msg: String): ArrayList<Int> {
        var content = msg.substringAfter("[").substringBefore("]")
        val re = Regex("[^a-z0-9]")
        var filter1 = re.replace(content, ",")
        var filter2 = filter1.split("[^0-9]".toRegex())
        var numberList = ArrayList<Int>()
        for (element in filter2) {
            if (element.isEmpty()) continue
            else {
                numberList.add(element.toInt())
            }
        }
        //var numbers = answer.split("[a-z]".toRegex())
        //println(numbers)
        return numberList
    }

    //for the fridge answer
    fun getFoodCodes(numbers:ArrayList<Int>) : ArrayList<Int> {
        var i=0
        var codes = ArrayList<Int>()
        for(num in numbers){
            if(i%2==0){
                codes.add(num)
            }
            i++
        }
        lastFoodCodes=codes
        return codes
    }

    //for the fridge answer
    fun getFoodQuantities(numbers:ArrayList<Int>) : ArrayList<Int> {
        var i=0
        var qtys = ArrayList<Int>()
        for(num in numbers){
            if(i%2!=0){
                qtys.add(num)
            }
            i++
        }
        lastFoodQtys=qtys
        return qtys
    }
}