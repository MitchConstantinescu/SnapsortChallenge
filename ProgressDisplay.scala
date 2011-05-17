package com.snapsort.codingchallenge

object ProgressDisplay {

    var counter = 1
    
    def tick()
    {
        counter = counter + 1
        if (counter % 100 == 0)
            print(".")
    }
}