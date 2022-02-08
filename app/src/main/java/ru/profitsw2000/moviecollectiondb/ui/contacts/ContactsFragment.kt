package ru.profitsw2000.moviecollectiondb.ui.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
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
import ru.profitsw2000.moviecollectiondb.ui.adapters.ContactsAdapter
import ru.profitsw2000.moviecollectiondb.ui.adapters.FavoriteAdapter

const val REQUEST_CODE = 42

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter: ContactsAdapter by lazy { ContactsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactsFragmentRecyclerview.adapter = adapter
        checkPermission()
        //adapter.setData()
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                    //Доступ к контактам на телефоне есть
                    Toast.makeText(context, "Доступ есть", Toast.LENGTH_LONG).show()
                    adapter.setData(getContacts())//getContacts()
                    with(binding) {
                        progressBar.hide()
                    }
                }

                //Опционально: если нужно пояснение перед запросом разрешений
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Жизненно необходим доступ к контактам.")
                        .setPositiveButton("Предоставить доступ") { _, _ ->requestPermission()}
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss()}
                        .create()
                        .show()
                    //Toast.makeText(context, "Доступ есть", Toast.LENGTH_LONG).show()
                }

                else -> {
                    //Запрашиваем разрешение
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    // Обратный вызов после получения разрешений от пользователя
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    adapter.setData(getContacts())//getContacts()
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
    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}