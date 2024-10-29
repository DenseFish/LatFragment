package latihan.c14220166.latfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Page1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Page1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var score = 50
    private var randomStart = 1
    private var randomRange = 5
    private lateinit var tvScore: TextView
    private var selectedCard: Int? = null
    private var lastSelectedCardIndex: Int? = null
    private lateinit var btnGiveUp: Button
    private lateinit var isMatched: MutableList<Boolean>

    private lateinit var randomNumbers: MutableList<Int>
    private lateinit var textViews: List<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGiveUp = view.findViewById(R.id.btnGiveUp)
        tvScore = view.findViewById(R.id.tvScore)

        arguments?.let {
            randomStart = it.getInt("randomLimit", 1)
        }

        textViews = listOf(
            view.findViewById(R.id.tvCard1),
            view.findViewById(R.id.tvCard2),
            view.findViewById(R.id.tvCard3),
            view.findViewById(R.id.tvCard4),
            view.findViewById(R.id.tvCard5),
            view.findViewById(R.id.tvCard6),
            view.findViewById(R.id.tvCard7),
            view.findViewById(R.id.tvCard8),
            view.findViewById(R.id.tvCard9),
            view.findViewById(R.id.tvCard10)
        )

        isMatched = MutableList(10) { false }

        randomNumbers = mutableListOf()
        for (i in randomStart until (randomStart + randomRange)) {
            randomNumbers.add(i)
            randomNumbers.add(i)
        }
        randomNumbers.shuffle()

        for (textView in textViews) {
            textView.text = "?"
        }

        textViews.forEachIndexed { index, textView ->
            textView.setOnClickListener { onCardClicked(index) }
        }

        btnGiveUp.setOnClickListener {
            val fragment = Page2()
            val bundle = Bundle()
            bundle.putInt("finalScore", score)
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
    }

    private fun onCardClicked(index: Int) {
        val selectedNumber = randomNumbers[index]

        if (!isMatched[index]) {
            textViews[index].text = selectedNumber.toString()
        }

        if (selectedCard == null) {
            selectedCard = selectedNumber
            lastSelectedCardIndex = index
        } else {
            if (selectedCard == selectedNumber && lastSelectedCardIndex != null && lastSelectedCardIndex != index) {
                Toast.makeText(context, "10 Score!!", Toast.LENGTH_SHORT).show()
                score += 10
                isMatched[index] = true
                isMatched[lastSelectedCardIndex!!] = true
            } else {
                Toast.makeText(context, "-5 Score!!", Toast.LENGTH_SHORT).show()
                score -= 5
                textViews.forEachIndexed { i, textView ->
                    if (!isMatched[i]) {
                        textView.postDelayed({
                            textView.text = "?"
                        }, 1000)
                    }
                }
            }
            selectedCard = null
            lastSelectedCardIndex = null
        }

        view?.findViewById<TextView>(R.id.tvScore)?.text = "Score: $score"

        if (isMatched.all { it }) {
            Toast.makeText(context, "Selamat Anda Berhasil Menyelesaikan Gamenya!!", Toast.LENGTH_SHORT).show()
            val fragment = Page2()
            val bundle = Bundle()
            bundle.putInt("finalScore", score)
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }

        if (score <= 0) {
            Toast.makeText(context, "Yahh Score Anda Habis, Silahkan Coba Lagi!!", Toast.LENGTH_SHORT).show()
            val fragment = Page2()
            val bundle = Bundle()
            bundle.putInt("finalScore", score)
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fHal1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Page1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}