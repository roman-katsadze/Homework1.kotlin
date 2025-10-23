package com.example.myapplication1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                systemBars.bottom)
            insets
        }
    }
}

open class Account(
    val accountNumber: String,
    var ownerName: String
) {
    private var balance: Double = 0.0

    fun getBalance(): Double {
        return balance
    }

    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
            println("Deposited $amount to account $accountNumber. New balance: $balance")
        } else {
            println("Deposit failed: amount must be positive.")
        }
    }

    open fun withdraw(amount: Double) {
        if (amount > 0 && balance >= amount) {
            balance -= amount
            println("Withdrawn $amount from account $accountNumber. Remaining balance: $balance")
        } else {
            println("Withdrawal failed: insufficient funds or invalid amount.")
        }
    }

    fun printInfo() {
        println("Account Info → Number: $accountNumber | Owner: $ownerName | Balance: $balance")
    }
}



class SavingsAccount(
    accountNumber: String,
    ownerName: String
) : Account(accountNumber, ownerName) {

    override fun withdraw(amount: Double) {
        if (amount > 500) {
            println("Withdrawal denied: Savings account limit is 500 per transaction.")
        } else {
            super.withdraw(amount)
        }
    }
}



class VIPAccount(
    accountNumber: String,
    ownerName: String,
    private val transactionFee: Double = 2.0
) : Account(accountNumber, ownerName) {

    override fun withdraw(amount: Double) {
        val totalAmount = amount + transactionFee
        if (getBalance() >= totalAmount) {

            super.withdraw(totalAmount)
            println("Transaction fee of $transactionFee applied. Total deducted: $totalAmount")
        } else {
            println("Withdrawal failed: insufficient funds (need $totalAmount including fee).")
        }
    }
}



fun main() {

    val acc1 = SavingsAccount("S101", "გიორგი გ.")
    val acc2 = VIPAccount("V202", "მარიამი ა.")

    println("\n--- Testing SavingsAccount ---")
    acc1.deposit(1000.0)
    acc1.withdraw(300.0)
    acc1.withdraw(600.0)

    println("\n--- Testing VIPAccount ---")
    acc2.deposit(1000.0)
    acc2.withdraw(50.0)
    acc2.printInfo()

    println("\n--- Polymorphism Demo ---")
    val accounts: List<Account> = listOf(acc1, acc2)
    for (account in accounts) {
        account.deposit(50.0)
        account.printInfo()
    }
}


