package latihan.c14220166.latfragment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import latihan.c14220166.latfragment.R.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        loadFragment(Page1())

        findViewById<FrameLayout>(id.btnPage1).setOnClickListener {
            loadFragment(Page1())
        }

        findViewById<FrameLayout>(id.btnPage2).setOnClickListener {
            loadFragment(Page2())
        }

        findViewById<FrameLayout>(id.btnPage3).setOnClickListener {
            loadFragment(Page3())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

