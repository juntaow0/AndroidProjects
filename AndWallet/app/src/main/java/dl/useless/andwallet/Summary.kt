package dl.useless.andwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_summary.*

class Summary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        var income = intent.getLongExtra(Expense.KEY_INCOME,0)
        var expense = intent.getLongExtra(Expense.KEY_EXPENSE,0)
        var balance = intent.getLongExtra(Expense.KEY_BALANCE,0)
        sumIncome.text = getString(R.string.display_price, income.toString())
        sumExpense.text = getString(R.string.display_price, expense.toString())
        sumBalance.text = getString(R.string.display_price, balance.toString())
    }
}
