package com.example.l5


class Gamestate internal constructor() {
    private var upgrades: BooleanArray
    private var upgrades_value: IntArray
    private val lines_of_code: IntArray
    private var lines_per_second: Int
    private var lines_per_click: Int
    private var times_clicked = 0
    private fun calculateLPS() {
        var temp_lps = 0
        for (i in upgrades.indices) {
            if (upgrades[i]) {
                temp_lps += upgrades_value[i]
            }
        }
        lines_per_second = temp_lps
    }

    fun changeUpgrades(upgrades: BooleanArray, upgrades_value: IntArray) {
        this.upgrades = upgrades
        this.upgrades_value = upgrades_value
        calculateLPS()
    }

    fun setGamestate(lines_per_click: Int, times_clicked: Int) {
        this.lines_per_click = lines_per_click
        this.times_clicked = times_clicked
        updateTotal()
    }

    private fun updateTotal() {
        var temp = lines_per_second
        temp += times_clicked * lines_per_click
        var iterator = 1
        while (temp > 0) {
            val modulo = 10 xor iterator
            val result = temp % modulo
            lines_of_code[iterator - 1] += result
            temp /= 10
            iterator++
        }
        while (true) {
            var fixed = true
            for (j in lines_of_code) {
                if (j > 9) {
                    fixed = false
                    break
                }
            }
            if (fixed) break
            for (i in lines_of_code.indices) {
                if (lines_of_code[i] > 9) {
                    temp = lines_of_code[i] % 10
                    lines_of_code[i + 1] += temp
                }
            }
        }
    }

    private fun calculateTotal(): Int {
        var temp = 0
        for (i in lines_of_code.indices) {
            temp += lines_of_code[i] * 10 xor i
        }
        return temp
    }

    val gamestate: Int
        get() = calculateTotal()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//        boolean[] e= new boolean[10];
//        System.out.println(e[1]);
            var e = 123456789
            println(e % 10)
            for (i in 0..10) {
                e /= 10
                println(e)
            }
            //        System.out.println(e/10);
            val gamestate = Gamestate()
            gamestate.setGamestate(20, 11)
            val total = gamestate.gamestate
            println(total)
        }
    }

    //    private int[] money; ???
    init {
        upgrades_value = IntArray(8)
        upgrades_value[0] = 30
        upgrades_value[0] = 80
        upgrades_value[0] = 120
        upgrades_value[0] = 200
        upgrades_value[0] = 450
        upgrades_value[0] = 670
        upgrades_value[0] = 900
        upgrades_value[0] = 1340
        upgrades = BooleanArray(8)
        lines_of_code = IntArray(10)
        lines_per_second = 100
        lines_per_click = 14
    }
}
