package com.example.leo.mygame

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.leo.mygame.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_game_module.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameControlListener] interface
 * to handle interaction events.
 * Use the [GameModule.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameModule : Fragment(), GameControlListener {

    private var mListener: GameControlListener? = null
    private var mTimerThread: Thread? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_module, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        cardGroup.onGameOverListener = this
        super.onStart()
        /**这里的[Handler]将[Handler.Callback]直接设置为给[textView]加一
         * 这样的话[PulseMaker]就只需要调用[Handler.sendEmptyMessage]就能做到加一*/
        val plusOne = Handler { msg ->
            textTime.text = "Time : ${msg.arg1 / 3600}:${msg.arg1 / 60 % 60}:${msg.arg1 % 60}"
            true
        }
        /** 只是简单做个计时器测试一下前面写的[PulseMaker] */
        btn_start.setOnClickListener {
            if (mTimerThread == null) {
                mTimerThread = PulseMaker.newPulse(1000, plusOne)
                btn_start.visibility = View.INVISIBLE
                textTime.visibility = View.VISIBLE
                cardGroup.startGame()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = this
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onGameOver(score: Int) {
        mTimerThread!!.interrupt()
        mTimerThread = null
        btn_start.visibility = View.VISIBLE
        textTime.visibility = View.INVISIBLE
        Toast.makeText(context, textTime.text, Toast.LENGTH_LONG).show()
        //TODO ("文件操作")
        DataSaver.saveScore(context, score, textTime.text.toString())
        DummyContent.ITEMS.add(DummyContent.DummyItem(textTime.text.toString(),score.toString()))
    }

    @SuppressLint("SetTextI18n")
    override fun onScoreChangeListener(score: Int) {
        textView.text = "Score:$score"
    }

    companion object {

        /**
         * 依次创建新的[Fragment]实例
         *
         * @return 一个新的[GameModule]实例
         */
        fun newInstance(): GameModule {
            val fragment = GameModule()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
