package com.example.leo.mygame

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_game_module.*


/**
 * A simple [Fragment] subclass.
 * Use the [GameModule.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameModule : Fragment(), CardGroup.GameControlListener {

    private var mTimerThread: Thread? = null
    private var time=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_module, container, false)
    }

    override fun onStart() {
        cardGroup.gameControlModule = this
        super.onStart()
        /** 只是简单做个计时器测试一下前面写的[PulseMaker] */
        val plusOne = Handler { msg ->
            /**这里的[Handler]将[Handler.Callback]直接将[textTime]设置为[msg]所指定的调用次数对应的时间*/
            time = msg.arg1
            textTime.text = time.toTime()
            true
        }
        btn_start.setOnClickListener {
            if (mTimerThread == null) {
                mTimerThread = PulseMaker.newPulse(1000, plusOne)
                btn_start.visibility = View.INVISIBLE
                textTime.visibility = View.VISIBLE
                cardGroup.startGame()
            }
        }
    }

    override fun onGameOver(score: Int) {
        mTimerThread!!.interrupt()
        mTimerThread = null
        btn_start.visibility = View.VISIBLE
        textTime.visibility = View.INVISIBLE
        Toast.makeText(context, textTime.text, Toast.LENGTH_LONG).show()
        /**文件操作 && 更新列表*/
        DataSaver.saveScore(context!!, score, time)
    }

    override fun onScoreChangeListener(score: Int) {
        textView.text = score.toScore()
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

}
