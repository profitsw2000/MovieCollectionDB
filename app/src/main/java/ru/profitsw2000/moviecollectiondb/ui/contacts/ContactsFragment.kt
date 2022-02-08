package ru.profitsw2000.moviecollectiondb.ui.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentContactsBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentFavoriteBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Contact
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.ui.adapters.ContactsAdapter
import ru.profitsw2000.moviecollectiondb.ui.adapters.FavoriteAdapter
import ru.profitsw2000.moviecollectiondb.ui.adapters.ParentAdapter
import ru.profitsw2000.moviecollectiondb.ui.description.DescriptionFragment
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

const val REQUEST_CODE_CONTACTS = 42
const val REQUEST_CODE_CALL = 99

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var adapter: ContactsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactsAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(phoneNumber: String) {
                checkPermission(Manifest.permission.CALL_PHONE, REQUEST_CODE_CALL, phoneNumber)
            }
        })
        binding.contactsFragmentRecyclerview.adapter = adapter
        checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_CODE_CONTACTS,"")
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    private fun checkPermission(requestType: String, requestCode: Int, phoneNumber: String) {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, requestType) == PackageManager.PERMISSION_GRANTED -> {
                    //Доступ к контактам на телефоне есть
                    when (requestCode) {
                        REQUEST_CODE_CONTACTS -> {
                            adapter?.setData(getContacts())
                            with(binding) {
                                progressBar.hide()
                            }
                        }

                        REQUEST_CODE_CALL -> {
                            makePhoneCall(phoneNumber)
                        }
                        else -> {

                        }
                    }
                }

                //Опционально: если нужно пояснение перед запросом разрешений
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Жизненно необходим доступ к контактам.")
                        .setPositiveButton("Предоставить доступ") { _, _ ->requestPermission(requestType, requestCode)}
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss()}
                        .create()
                        .show()
                }

                //Опционально: если нужно пояснение перед запросом разрешений
                shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                    Toast.makeText(context,"Сообщение",Toast.LENGTH_LONG).show()
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к звонкам")
                        .setMessage("Жизненно необходим доступ к звонкам.")
                        .setPositiveButton("Предоставить доступ") { _, _ ->requestPermission(requestType, requestCode)}
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss()}
                        .create()
                        .show()
                }

                else -> {
                    //Запрашиваем разрешение
                    requestPermission(requestType, requestCode)
                }
            }
        }
    }

    private fun requestPermission(requestType: String, requestCode: Int) {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), requestCode)
    }

    // Обратный вызов после получения разрешений от пользователя
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CONTACTS -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    adapter?.setData(getContacts())
                    with(binding) {
                        progressBar.hide()
                    }
                    Toast.makeText(context, "Доступ предоставлен", Toast.LENGTH_LONG).show()
                } else {
                    // Поясните пользователю, что экран останется пустым, потому что доступ к контактам не предоставлен
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage("Доступ отклонён, приложение не будет работать.")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }

            REQUEST_CODE_CALL -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    adapter?.setData(getContacts())
                    with(binding) {
                        progressBar.hide()
                    }
                    Toast.makeText(context, "Доступ предоставлен. Повторите попытку.", Toast.LENGTH_LONG).show()
                } else {
                    // Поясните пользователю, что звонка не будет, потому что доступ к звонкам не предоставлен
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к звонкам")
                            .setMessage("Доступ отклонён, звонка не будет.")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }


    @SuppressLint("Range")
    private fun getContacts(): List<Contact> {
        var contacts: MutableList<Contact> = mutableListOf()

        context?.let {
            val resolver: ContentResolver = it.contentResolver;
            val cursor: Cursor? =
                resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

            cursor?.let {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber =
                        (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(id),
                            null
                        )
                        var phoneArray: MutableList<String> = mutableListOf()
                        cursorPhone?.let {
                            if (cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue =
                                        cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    phoneArray.add(phoneNumValue)
                                }
                            }
                            contacts.add(Contact(name, phoneArray))
                            cursorPhone.close()
                        }
                    }
                }
            } else {
                //   toast("No contacts available!")
            }
            cursor.close()
            }
        }
        return contacts
    }

    private fun View.show() : View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    private fun View.hide() : View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(phoneNumber: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}