public class ShiftByXEncDecModule implements ByteEncryptor, ByteDecryptor {

	private int b;
	private int x;

	ShiftByXEncDecModule(int x) {
		this.b = -1;
		this.x = x;
	}

	@Override
	public int decryptByte(int b) {
		this.b = b;
		return (this.b - this.x) % 256;
	}

	@Override
	public int encryptByte(int b) {
		this.b = b;
		return (this.b + this.x) % 256;
	}

}
