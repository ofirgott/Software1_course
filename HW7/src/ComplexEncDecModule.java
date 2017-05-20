public class ComplexEncDecModule implements ByteEncryptor, ByteDecryptor {

	ByteEncryptor[] byteEncryptors;
	ByteDecryptor[] byteDecryptors;
	// arrays must be in same length and != null
	private int decrypts_counter=0;
	private int encrypts_counter=0;

	ComplexEncDecModule(ByteEncryptor[] byteEncryptors,
			ByteDecryptor[] byteDecryptors) throws IllegalArgumentException {

		if (byteDecryptors == null || byteEncryptors == null
				|| byteDecryptors.length != byteEncryptors.length)
			throw new IllegalArgumentException(
					"invalid byteEncryptors or byteDecryptors arrays!");

		
		this.byteEncryptors = byteEncryptors.clone();
		this.byteDecryptors = byteDecryptors.clone();

	}

	@Override
	public int decryptByte(int b) {
		int temp = decrypts_counter;
		int output = this.byteDecryptors[temp].decryptByte(b);
		this.decrypts_counter++;
		if (this.decrypts_counter == this.byteDecryptors.length)
			this.decrypts_counter = 0;
		return output;
	}

	@Override
	public int encryptByte(int b) {
		int temp = this.encrypts_counter;
		int output = this.byteEncryptors[temp].encryptByte(b);
		this.encrypts_counter++;
		if (this.encrypts_counter == this.byteEncryptors.length)
			this.encrypts_counter = 0;
		return output;
	}

}
