package com.example.elivia

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elivia.Services.ChatService
import com.example.elivia.Components.Adapter.MessageAdapter
import com.example.elivia.Models.Messages
import com.example.elivia.Services.BrainService
import com.example.elivia.Services.ContactsService
import com.example.elivia.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

private const val TAG = "ChatActivity"

class MainActivity : AppCompatActivity(), RecognitionListener {
    private lateinit var adapter: MessageAdapter;
    private lateinit var messageList: List<Messages>;
    private lateinit var binding: ActivityMainBinding


    private val STATE_START = 0
    private val STATE_READY = 1
    private val STATE_DONE = 2
    private val STATE_FILE = 3
    private val STATE_MIC = 4

    /* Used to handle permission request */
    private val PERMISSIONS_REQUEST_RECORD_AUDIO = 1

    private var model: Model? = null
    private var speechService: SpeechService? = null
    private var speechStreamService: SpeechStreamService? = null
    private var resultView: TextView? = null
    private var lastStatMic: Boolean = false;

    private lateinit var brainService: BrainService;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.messageList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this);
        recyclerView.adapter = adapter;

        resultView = findViewById(R.id.txtMessage);

        brainService = BrainService(this);


        // Check if user has given permission to record audio, init the model after permission is granted
        val permissionCheck = arrayOf(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO), ContextCompat.checkSelfPermission( applicationContext, Manifest.permission.CALL_PHONE ), ContextCompat.checkSelfPermission( applicationContext, Manifest.permission.READ_CONTACTS ), ContextCompat.checkSelfPermission( applicationContext, Manifest.permission.WRITE_CONTACTS ));
        println("permission $permissionCheck");
        if (!permissionCheck.all { x -> x == PackageManager.PERMISSION_GRANTED}) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_CONTACTS), PERMISSIONS_REQUEST_RECORD_AUDIO)
        }
        println("init model");
        initModel()

        var tmp = ContactsService(this);
        println("thiiiiis " + tmp.readContacts("Elodie"));


        findViewById<AppCompatImageButton>(R.id.btnSend).setOnClickListener { view: View ->
            onSendMessage(view);
        }
        findViewById<AppCompatImageButton>(R.id.speak).setOnClickListener { view: View ->
            recognizeMicrophone();
        }
    }

    private fun initModel() {
        StorageService.unpack(this, "model-fr-fr", "model",
                { model: Model? ->
                    this.model = model
                    setUiState(STATE_READY)
                }
        ) { exception: IOException -> setErrorState("Failed to unpack the model" + exception.message) }

    }

    private fun recognizeMicrophone() {
        println("click to speak");
        if (speechService != null) {
            speechService!!.setPause(lastStatMic);
            lastStatMic = !lastStatMic;
        } else {
            setUiState(STATE_MIC)
            try {
                println(model);
                val rec = Recognizer(model, 16000.0f)
                speechService = SpeechService(rec, 16000.0f)
                println("sec");
                speechService!!.startListening(this)
                println("third");
                lastStatMic = true;
            } catch (e: IOException) {
                println("exeption")
                setErrorState(e.message!!)
            }
        }
    }

    private fun setUiState(state: Int) {
        println(state);
    }
    private fun setErrorState(state: String) {
        println("error" + state);
    }
    private fun resetInput(messageBox: TextView) {
        messageBox.text = "";
    }

    private fun sendMessage(mess: String): Messages? {
        println("thiiiiiiiiiiiiiiiiis");
        if (mess.isNotEmpty()) {
            val message = Messages(
                    "test",
                    mess,
                    Calendar.getInstance().timeInMillis,
                    UUID.randomUUID().toString()
            )
            println(message);
            adapter.addMessage(message);
            brainService.takeAction(message, adapter);
            return message;
        }
        return null;
    }

    private fun onSendMessage(view: View)
    {
        val messageBox = findViewById<TextView>(R.id.txtMessage);
        if(messageBox.text.isNotEmpty()) {
            val message = sendMessage(messageBox.text.toString()) ?: return;
            resetInput(messageBox)
        } else {
            Toast.makeText(applicationContext, "Message should not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Recognizer initialization is a time-consuming and it involves IO,
                // so we execute it in async task
                initModel()
            } else {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (speechService != null) {
            speechService!!.stop()
            speechService!!.shutdown()
        }
        speechStreamService?.stop()
    }

    override fun onResult(hypothesis: String?) {
        println("finalllll")
        if (hypothesis == null)
            return;
        val obj = JSONObject(hypothesis);
        try {
            resultView!!.text = obj.getString("text");
            println(obj.getString("text"));
            sendMessage(obj.getString("text"));
        } catch (e : JSONException) {
            println(e);
        }
        setUiState(STATE_DONE)
        if (speechStreamService != null) {
            speechStreamService = null
        }
    }

    override fun onFinalResult(hypothesis: String?) {
        println("finalllll")
        if (hypothesis == null)
            return;
        val obj = JSONObject(hypothesis);
        try {
            resultView!!.text = obj.getString("text");
            println(obj.getString("text"));
            sendMessage(obj.getString("text"));
        } catch (e : JSONException) {
            println(e);
        }
        setUiState(STATE_DONE)
        if (speechStreamService != null) {
            speechStreamService = null
        }
    }

    override fun onPartialResult(hypothesis: String?) {
        if (hypothesis == null)
            return;
        val obj = JSONObject(hypothesis);
        try {
            resultView!!.text = obj.getString("partial");
        } catch (e : JSONException) {
            println(e);
        }
    }

    override fun onError(e: java.lang.Exception) {
        setErrorState(e.message!!)
    }

    override fun onTimeout() {
        setUiState(STATE_DONE)
    }
}
