package uz.mobiler.kontakt

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import uz.mobiler.kontakt.adapters.RvAdapter
import uz.mobiler.kontakt.databinding.FragmentHomeBinding
import uz.mobiler.kontakt.models.Contact

class HomeFragment : Fragment() {

    lateinit var rvAdapter: RvAdapter
    lateinit var list: ArrayList<Contact>
    lateinit var binding: FragmentHomeBinding
    var setting=false
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        askPermission(Manifest.permission.READ_CONTACTS) {
                 list = ArrayList()
                val contacts = requireContext().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
                while (contacts != null && contacts.moveToNext()) {
                    val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    var contact = Contact(name, number)
                    list.add(contact)
                }
                contacts!!.close()

                rvAdapter = RvAdapter(list, object : RvAdapter.MyCallItemOnClickListener {
                    override fun callItemOnClick(contact: Contact, position: Int) {
                        askPermission(Manifest.permission.CALL_PHONE) {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:" + contact.number)
                            startActivity(callIntent)
                        }.onDeclined { e ->
                            if (e.hasDenied()) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Please allow me to read your contacts")
                                    .setPositiveButton("yes") { dialog, which ->
                                        e.askAgain()
                                    }
                                    .setNegativeButton("no") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                            if (e.hasForeverDenied()) {
                                Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
                                e.goToSettings()
                                setting=true
                            }
                        }
                    }

                    override fun sendSmsItemOnClick(contact: Contact, position: Int) {
                        var bundle = Bundle()
                        bundle.putSerializable("contact", contact)
                        bundle.putInt("position", position)
                        findNavController().navigate(R.id.smsSendFragment, bundle)
                    }

                    override fun itemOnClick(contact: Contact, position: Int) {
                        Toast.makeText(binding.root.context, "${contact.name}", Toast.LENGTH_SHORT).show()
                    }

                    override fun imgItemOnClick(contact: Contact, position: Int) {
                        Toast.makeText(binding.root.context, "${contact.name}", Toast.LENGTH_SHORT).show()
                    }

                })
                binding.rv.adapter = rvAdapter
                rvAdapter.notifyItemInserted(list.size)
                rvAdapter.notifyItemChanged(list.size)

            }.onDeclined { e ->
                if (e.hasDenied()) {
                    Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(requireContext())
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain()
                        }
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
                if (e.hasForeverDenied()) {
                    Toast.makeText(binding.root.context, "Dened", Toast.LENGTH_SHORT).show()
                    e.goToSettings()
                    setting=true
                }
            }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (setting){
            askPermission(Manifest.permission.READ_CONTACTS) {
                list = ArrayList()
                val contacts = requireContext().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
                while (contacts != null && contacts.moveToNext()) {
                    val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    var contact = Contact(name, number)
                    list.add(contact)
                }
                contacts!!.close()

                rvAdapter = RvAdapter(list, object : RvAdapter.MyCallItemOnClickListener {
                    override fun callItemOnClick(contact: Contact, position: Int) {
                        askPermission(Manifest.permission.CALL_PHONE) {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:" + contact.number)
                            startActivity(callIntent)
                        }.onDeclined { e ->
                            if (e.hasDenied()) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Please allow me to read your contacts")
                                    .setPositiveButton("yes") { dialog, which ->
                                        e.askAgain()
                                    }
                                    .setNegativeButton("no") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                            if (e.hasForeverDenied()) {
                                Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
                                e.goToSettings()
                            }
                        }
                    }

                    override fun sendSmsItemOnClick(contact: Contact, position: Int) {
                        var bundle = Bundle()
                        bundle.putSerializable("contact", contact)
                        bundle.putInt("position", position)
                        findNavController().navigate(R.id.smsSendFragment, bundle)
                    }

                    override fun itemOnClick(contact: Contact, position: Int) {
                        Toast.makeText(binding.root.context, "${contact.name}", Toast.LENGTH_SHORT).show()
                    }

                    override fun imgItemOnClick(contact: Contact, position: Int) {
                        Toast.makeText(binding.root.context, "${contact.name}", Toast.LENGTH_SHORT).show()
                    }

                })
                binding.rv.adapter = rvAdapter
                rvAdapter.notifyItemInserted(list.size)
                rvAdapter.notifyItemChanged(list.size)

            }.onDeclined { e ->
                if (e.hasDenied()) {
                    Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(requireContext())
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain()
                        }
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
                if (e.hasForeverDenied()) {
                    Toast.makeText(binding.root.context, "Dened", Toast.LENGTH_SHORT).show()
                    e.goToSettings()
                }
            }

        }
    }
}