
public class DummyEncDecModule implements ByteEncryptor, ByteDecryptor {

	private int b;
	
	DummyEncDecModule(){
		this.b = -1 ;
	}

	@Override
	public int decryptByte(int b) {
		this.b = b;
		return this.b;
	}

	@Override
	public int encryptByte(int b) {
		this.b = b;
		return this.b;
	}
	
}
