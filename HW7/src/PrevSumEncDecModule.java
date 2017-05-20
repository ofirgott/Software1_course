public class PrevSumEncDecModule implements ByteEncryptor, ByteDecryptor {

	private int prev_encrypted; // previous encrypted value
	private int prev_decrypted; // previous decrypted value

	PrevSumEncDecModule() {
		this.prev_decrypted = -1; // Default value
		this.prev_encrypted = -1;
	}

	@Override
	public int decryptByte(int b) {
		int output;
		if (prev_decrypted == -1) { // first byte
			prev_decrypted = b;
			return b;
		} else { // non first byte
			output = (b - prev_decrypted) % 256;
			prev_decrypted = output;
			return output;

		}
	}

	@Override
	public int encryptByte(int b) {
		int output;
		if (prev_encrypted == -1) { // first byte
			prev_encrypted = b;
			return b;
		} else { // non first byte
			output = (b + prev_encrypted) % 256;
			prev_encrypted = b;
			return output;
		}
	}

}
