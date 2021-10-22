package uz.mobiler.kontakt

import android.app.AlertDialog
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import uz.mobiler.kontakt.databinding.FragmentSmsSendBinding
import uz.mobiler.kontakt.models.Contact

class SmsSendFragment : Fragment() {
    lateinit var binding: FragmentSmsSendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSmsSendBinding.inflate(inflater,container,false)

        val contact = arguments?.getSerializable("contact") as Contact

        if (contact!=null){

            binding.nameNumberEt.setText("${contact.name}  ${contact.number}")

            binding.sendBtn.setOnClickListener {

                val message = binding.descriptions.text.toString().trim()

                askPermission(android.Manifest.permission.SEND_SMS) {

                    if (contact.number!!.isNotEmpty()){
                        if (message.isNotEmpty()){
                            var sms=SmsManager.getDefault()
                            sms.sendTextMessage(contact.number,null,message,null,null)
                            findNavController().popBackStack()
                            Toast.makeText(binding.root.context, "Sms jo'natildi!!!", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(binding.root.context, "Iltimos bo'sh xabar jo'natmang", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(binding.root.context, "Iltimos +998 raqam kiriting boshida", Toast.LENGTH_SHORT).show()
                    }
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
                        Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
                        e.goToSettings()
                    }
                }
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}