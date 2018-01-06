package com.example.leo.mygame

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_game_module.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameModule.GameControlListener] interface
 * to handle interaction events.
 * Use the [GameModule.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameModule : Fragment() {

    private var ground: ByteArray? = null
    private var mListener: GameControlListener? = null
    private var mTimerThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            ground = arguments.getByteArray(ARG_GROUND)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_module, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val plusOne = Handler {
            textView.text = (textView.text.toString().toLong() + 1).toString()
            true
        }
        btn_start.setOnClickListener {
            if (mTimerThread == null) {
                mTimerThread = PulseMaker.newPulse(1000,plusOne)
                btn_start.text = "pause"
            }
            else {
                mTimerThread!!.interrupt()
                mTimerThread = null
                btn_start.text = "start"
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameControlListener) {
            mListener = context
        } else {
            mListener = null
            throw RuntimeException(context.toString() + " must implement GameControlListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface GameControlListener {
        /**游戏开始时调用 */
        fun onGameOverListener()
        /**游戏结束时调用 */
        fun onGameStartListener()
    }

    companion object {
        private val ARG_GROUND = "param1"

        /**
         * 依次创建新的[Fragment]实例
         *
         * @param gnd 就是场地.
         * @return A new instance of fragment GameModule.
         */
        fun newInstance(gnd: ByteArray): GameModule {
            val fragment = GameModule()
            val args = Bundle()
            args.putByteArray(ARG_GROUND, gnd)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
