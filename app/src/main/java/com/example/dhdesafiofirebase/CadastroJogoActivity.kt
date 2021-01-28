package com.example.dhdesafiofirebase

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.dhdesafiofirebase.databinding.ActivityCadastroJogoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CadastroJogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroJogoBinding
    private val RC_PHOTO = 190
    private var mSelectedUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCadastroJogo.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.ibCapa.setOnClickListener {
            selectPhoto()
        }

        binding.btnSave.setOnClickListener {
            saveInFirebase()
        }
    }

    private fun selectPhoto() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                0
            )
        } else {
            callImageSelector()
        }
    }

    private fun callImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = "image/*"
        startActivityForResult(intent, RC_PHOTO)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            callImageSelector()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_PHOTO) {
            mSelectedUri = data?.data

            mSelectedUri?.let {
                Log.i("Teste", mSelectedUri.toString())

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)

                binding.imgCapa.setImageBitmap(bitmap)
                binding.ibCapa.visibility = View.GONE
                binding.imgCapa.visibility = View.VISIBLE
            }
        }
    }

    private fun saveInFirebase() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/${filename}")

        mSelectedUri?.let {
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { it ->
                        Log.i("Teste", it.toString())

                        val url = it.toString()
                        val titulo = binding.edtTitulo.text.toString().toUpperCase(Locale.ROOT)
                        val descricao = binding.edtDescricao.text.toString().toUpperCase(Locale.ROOT)
                        val anoLancamento = binding.edtAnoLancamento.text.toString().toInt()

                        val jogo = Jogo(titulo, anoLancamento, descricao, url)

                        FirebaseFirestore.getInstance().collection("jogos")
                            .add(jogo)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Jogo Adicionado com sucesso!",
                                    Toast.LENGTH_LONG
                                ).show()
                                onBackPressed()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "Erro: " + it.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("Teste", it.message.toString())

                            }
                    }
                }
        }
    }
}