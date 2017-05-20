import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class KeyFileEncDecModule implements ByteEncryptor, ByteDecryptor {

	int[] key;
	int current_Key_index_Encrypt;
	int current_Key_index_Decrypt;
	int key_length;
	public static final int KEY_MAX_LENGHT = 2000;

	KeyFileEncDecModule(String keyFilename) throws IOException {
		current_Key_index_Encrypt = 0;
		current_Key_index_Decrypt = 0;
		key_length = 0; // bytes counter in key file

		// build key array
		key = new int[KEY_MAX_LENGHT];
		BufferedInputStream reader = null;
		try {

			File fromFile = new File(keyFilename);
			reader = new BufferedInputStream(new FileInputStream(fromFile));

			int readByte;

			while ((readByte = reader.read()) != -1) {
				key[key_length] = readByte;
				key_length++;
			}
			if (key_length < KEY_MAX_LENGHT)
				key = Arrays.copyOf(key, key_length); // adjust key array size;

		} catch (FileNotFoundException e) {
			System.out.println("key File '" + keyFilename + "' not found");

		} catch (IOException e) {
			System.out.println("open key file '" + keyFilename
					+ "' failed due to IO error");
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}

	}

	@Override
	public int decryptByte(int b) {
		int output;

		output = (b - key[current_Key_index_Decrypt % key_length]) % 256;

		current_Key_index_Decrypt++;

		if (current_Key_index_Decrypt >= KEY_MAX_LENGHT)
			current_Key_index_Encrypt = 0;

		return output;
	}

	@Override
	public int encryptByte(int b) {
		int output;

		output = (b + key[current_Key_index_Encrypt % key_length]) % 256;

		current_Key_index_Encrypt++;

		if (current_Key_index_Encrypt >= KEY_MAX_LENGHT)
			current_Key_index_Encrypt = 0;

		return output;
	}

}
