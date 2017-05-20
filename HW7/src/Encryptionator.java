import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encryptionator {

	public static void main(String[] args) {
		try {

			System.out.println("Encryptionator initiating...");
			DummyEncDecModule dummyEncDec = new DummyEncDecModule();
			ShiftByOneEncDecModule shiftByOneEncDec = new ShiftByOneEncDecModule();
			ShiftByXEncDecModule shiftByXEncDec = new ShiftByXEncDecModule(180);
			PrevSumEncDecModule prevSumEncDec = new PrevSumEncDecModule();
			KeyFileEncDecModule keyFileEncDec = new KeyFileEncDecModule(
					"keyFile1.dat");

			ByteEncryptor[] subEncryptors = { shiftByOneEncDec, shiftByXEncDec,
					keyFileEncDec };
			ByteDecryptor[] subDecryptors = { shiftByOneEncDec, shiftByXEncDec,
					keyFileEncDec };
			ComplexEncDecModule complexEncDec = new ComplexEncDecModule(
					subEncryptors, subDecryptors);

			System.out.println("Encrypting files...");
			encryptFile("in1.txt", "in1_dummy.xxx", dummyEncDec);
			encryptFile("in1.txt", "in1_shiftByOne.xxx", shiftByOneEncDec);
			encryptFile("in2.txt", "in2_shiftByX.xxx", shiftByXEncDec);
			encryptFile("in2.txt", "in2_prevSum.xxx", prevSumEncDec);
			encryptFile("in2.txt", "in2_keyFile.xxx", keyFileEncDec);
			encryptFile("in2.txt", "in2_complex.xxx", complexEncDec);

			System.out.println("Decrypting files...");
			decryptFile("in1_dummy.xxx", "in1_dummy.txt", dummyEncDec);
			decryptFile("in1_shiftByOne.xxx", "in1_shiftByOne.txt",
					shiftByOneEncDec);
			decryptFile("in2_shiftByX.xxx", "in2_shiftByX.txt", shiftByXEncDec);
			decryptFile("in2_prevSum.xxx", "in2_prevSum.txt", prevSumEncDec);
			decryptFile("in2_keyFile.xxx", "in2_keyFile.txt", keyFileEncDec);
			decryptFile("in2_complex.xxx", "in2_complex.txt", complexEncDec);

			System.out.println("Done !");

		} catch (IOException e) {
			System.out.println("IO error encountered while processing files.");
			e.printStackTrace();
		}
	}

	public static void encryptFile(String inputFilename, String outputFilename,
			ByteEncryptor encryptor) throws IOException {

		BufferedInputStream reader = null;
		BufferedOutputStream writer = null;

		try {

			File fromFile = new File(inputFilename);
			reader = new BufferedInputStream(new FileInputStream(fromFile));
			File toFile = new File(outputFilename);
			writer = new BufferedOutputStream(new FileOutputStream(toFile));
			int readByte;

			while ((readByte = reader.read()) != -1) {
				writer.write(encryptor.encryptByte(readByte));
			}

		} catch (FileNotFoundException e) {
			System.out.println("File '" + inputFilename + "' not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Encryption of the file '" + inputFilename
					+ "' failed due to IO error");
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
			if (writer != null)
				writer.close();
		}

	}

	public static void decryptFile(String inputFilename, String outputFilename,
			ByteDecryptor decryptor) throws IOException {

		BufferedInputStream reader = null;
		BufferedOutputStream writer = null;

		try {

			File fromFile = new File(inputFilename);
			reader = new BufferedInputStream(new FileInputStream(fromFile));
			File toFile = new File(outputFilename);
			writer = new BufferedOutputStream(new FileOutputStream(toFile));
			int readByte;

			while ((readByte = reader.read()) != -1) {
				writer.write(decryptor.decryptByte(readByte));
			}

		} catch (FileNotFoundException e) {
			System.out.println("File '" + inputFilename + "' not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Decryption of the file '" + inputFilename
					+ "' failed due to IO error");
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
			if (writer != null)
				writer.close();

		}
	}

}
