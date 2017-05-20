public class ShiftByOneEncDecModule implements ByteEncryptor, ByteDecryptor {

	private int b;

	ShiftByOneEncDecModule() {
		this.b = -1;
	}

	@Override
	public int decryptByte(int b) {
		this.b = b;
		return (this.b - 1) % 256;
	}

	@Override
	public int encryptByte(int b) {
		this.b = b;
		return (this.b + 1) % 256;
	}

}
