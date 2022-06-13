package zyx.and.dyj.autocheck

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

internal object DesCrypt {
    fun encrypt(password: String, input: String): ByteArray {

        val cipher = Cipher.getInstance("DES")

        val kf = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(password.toByteArray())

        val key: Key = kf.generateSecret(keySpec)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        //加密
        return cipher.doFinal(input.toByteArray())
        //通过Base64解决乱码问题，否则会出现异常（avax.crypto.IllegalBlockSizeException: last block incomplete in decryption）
    }

    fun dencrypt(password: String, input: ByteArray): ByteArray {

        val cipher = Cipher.getInstance("DES")

        val kf = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(password.toByteArray())

        val key: Key = kf.generateSecret(keySpec)
        cipher.init(Cipher.DECRYPT_MODE, key)

        return cipher.doFinal(input)
    }

}