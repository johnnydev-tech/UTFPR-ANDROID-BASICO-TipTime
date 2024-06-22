package tech.johnnydev.tiptime

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tech.johnnydev.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate( layoutInflater )

        enableEdgeToEdge()
        setContentView( binding.root )
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.btCalculate.setOnClickListener{
            calculateButtonOnClick()
        }

        if(savedInstanceState !=null){
            val tip = savedInstanceState.getString("tip")
            binding.tipResut.text = tip
        }else {
            binding.tipResut.text = getString(R.string.tip_amount_s,"-")
        }

    }

    private fun calculateButtonOnClick() {
        val textField = binding.inputServiceCost.text.toString()

        if(textField.isEmpty() ){
            Toast.makeText(this,(R.string.error_text), Toast.LENGTH_SHORT).show()
            return
        }
        val cost = textField.toDouble()

        val selectedPercent = binding.radioTipGroup.checkedRadioButtonId

        val tipPercentage = when(selectedPercent) {
            R.id.option_eighteen_percent ->0.18
            R.id.option_fifteen_percent->0.15
            else->0.20
        }

        var tip = cost *tipPercentage

        val roundUp = binding.roundSwitch.isChecked

        if(roundUp){
            tip = kotlin.math.ceil(tip)
        }

        val formattedCurrency = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResut.text = getString(R.string.tip_amount_s,formattedCurrency)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tip",binding.tipResut.text.toString())
    }
}