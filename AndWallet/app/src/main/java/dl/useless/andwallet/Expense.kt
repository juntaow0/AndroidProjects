package dl.useless.andwallet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.android.synthetic.main.item_row.view.*

class Expense : AppCompatActivity() {

    companion object {
        const val INCOME = "INCOME"
        const val EXPENSE = "EXPENSE"
        const val KEY_INCOME = "KEY_INCOME"
        const val KEY_EXPENSE = "KEY_EXPENSE"
        const val KEY_BALANCE = "KEY_BALANCE"
    }

    private var toggleState = INCOME
    private var income:Long = 0
    private var expense:Long = 0
    private var balance:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        setSupportActionBar(toolbar)
        btnToggle.setOnClickListener {
            if (toggleState==INCOME){
                btnToggle.text = EXPENSE
                toggleState = EXPENSE
            } else{
                btnToggle.text = INCOME
                toggleState = INCOME
            }
        }
        btnSave.setOnClickListener {
            if (etName.text.isNotEmpty() && etNumber.text.isNotEmpty()){
                updateBalance()
                createRow()
            } else{
                if (etName.text.isEmpty()){
                    etName.error = getString(R.string.empty_field)
                }
                if (etNumber.text.isEmpty()) {
                    etNumber.error = getString(R.string.empty_field)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_expense, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sum) {
            showSummary()
        } else if (item.itemId == R.id.action_delete) {
            layoutContent.removeAllViews()
            income = 0
            expense = 0
            balance = 0
            tvBalance.text = getString(R.string.current_balance, balance.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSummary(){
        val summaryIntent = Intent(this, Summary::class.java)
        summaryIntent.putExtra(KEY_INCOME, income)
        summaryIntent.putExtra(KEY_EXPENSE, expense)
        summaryIntent.putExtra(KEY_BALANCE, balance)
        startActivity(summaryIntent)
    }

    private fun updateBalance(){
        var amount = etNumber.text.toString().toLong()
        if (toggleState== INCOME){
            income+=amount
            balance+=amount
        }else{
            expense+=amount
            balance-=amount
        }
        tvBalance.text = getString(R.string.current_balance, balance.toString())
    }

    private fun createRow() {
        var itemRow = layoutInflater.inflate(R.layout.item_row,null,false)
        if (toggleState== EXPENSE){
            itemRow = layoutInflater.inflate(R.layout.item_row_2,null,false)
        }

        itemRow.tvName.text =  etName.text.toString()
        itemRow.tvAmount.text = getString(R.string.display_price, etNumber.text.toString())
        itemRow.btnDelete.setOnClickListener{
            var number = itemRow.tvAmount.text.toString().substring(1).toLong()
            if (itemRow.id==R.id.incomeView) {
                income -= number
            } else{
                expense -= number
            }
            balance = income-expense
            tvBalance.text = getString(R.string.current_balance, balance.toString())
            layoutContent.removeView(itemRow)
        }
        layoutContent.addView(itemRow,0)
        etName.setText("")
        etNumber.setText("")
    }
}
