package com.example.madcamppj1

import android.content.Intent
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.w1.Fragment_Gallery1
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat

class GalleryActivity : AppCompatActivity() {

    private val CAMERA = arrayOf(Manifest.permission.CAMERA)
    private val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    private lateinit var editTextNewName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        tmp1()

        val camera = findViewById<Button>(R.id.camera)
        val gallery = findViewById<Button>(R.id.picture)
        val imageView = findViewById<ImageView>(R.id.avatars)
        var currentUri: Uri? = null

        // 1.카메라 촬영후 저장
        val cameraPicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                bitmap: Bitmap? -> val name = randomFileName()
            val uri = saveFile(bitmap, setFileData(name,"image/jpeg"))
            imageView.setImageURI(uri)
            currentUri = uri
        }

        camera.setOnClickListener {
            cameraPicture.launch(null)
        }// 1.

        // 2.갤러리에서 이미지 데이터 가져와 출력하기
        val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
                uri: Uri? -> imageView.setImageURI(uri)
            currentUri = uri
        }

        gallery.setOnClickListener {
            getImageFromGallery.launch("image/*")
        }// 2.


        //4. 갤러리 프래그먼트 출력
        val btnGallery1 = findViewById<Button>(R.id.Gallery1)
        btnGallery1.setOnClickListener {
            val frag1 = supportFragmentManager.beginTransaction()
            frag1.replace(R.id.frameLayout,Fragment_Gallery1())
            frag1.commit()
        }

        //5.
        val imageViews = findViewById<ImageView>(R.id.avatars)
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                // 이미지를 앱 내부 저장소로 복사
                val internalStoragePath = copyImageToInternalStorage(this, uri, "copied_image.jpg")

                // 내부 저장소에 복사된 이미지를 ImageView에 표시
                if (internalStoragePath != null) {
                    imageViews.setImageURI(Uri.fromFile(internalStoragePath))
                }
            }
        }
    }

    fun copyImageToInternalStorage(context: Context, sourceUri: Uri, destinationFileName: String): File? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(sourceUri)
            val internalStoragePath = File(context.filesDir, destinationFileName)

            inputStream?.let {
                FileOutputStream(internalStoragePath).use { outputStream ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    while (it.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                }

                // 이미지가 복사된 내부 저장소의 파일 경로를 반환
                return internalStoragePath
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to copy image to internal storage.", Toast.LENGTH_SHORT).show()
        }

        return null
    }

    private fun renameImage(activity_gallery: GalleryActivity, currentUri: Uri, newName: String, s: String): Uri? {
        val contentResolver = activity_gallery.contentResolver
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentUri)

        val imagesDir = activity_gallery.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, "$newName.$s")
        val fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        //

        val newUri = FileProvider.getUriForFile(
            activity_gallery,
            activity_gallery.applicationContext.packageName + ".provider",
            imageFile
        )
        contentResolver.delete(currentUri, null, null)
        return newUri
    }

    // 파일명을 날짜 저장
    private fun randomFileName() : String{
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        return fileName
    }

    // 파일이름 지정.
    fun setFileData(fileName:String, mimeType:String): ContentValues {
        var CV = ContentValues()

        // MediaStore 에 파일명, mimeType 을 지정
        CV.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // 안정성 검사
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        return CV
    }

    // 사진 저장
    fun saveFile(bitmap: Bitmap?, CV:ContentValues):Uri?{
        // MediaStore 에 파일을 저장
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri, "w")

            val fos = FileOutputStream(scriptor?.fileDescriptor)

            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                CV.clear()
                // IS_PENDING 을 초기화
                CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, CV, null, null)
            }
        }
        return uri
    }

    fun tmp1(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_image
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_person -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_image -> return@setOnItemSelectedListener true
                R.id.bottom_send -> {
                    startActivity(Intent(applicationContext, SendActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}