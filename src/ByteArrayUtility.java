import java.util.Arrays;

public class ByteArrayUtility {
	
	/**
	 * @param a First ByteArray to be concatenated.
	 * @param b Second ByteArray to be concatenated.
	 * @return Concatenated of a & b ByteArray.
	 */
	public static byte[] concateByteArray(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	
	/**
	 * @param byteArray ByteArray to be operated on.
	 * @param number of first n byte to be returned.
	 * @return n number of byte
	 */
	public static byte[] getFirstNByte(byte[] byteArray, int n) {
		return Arrays.copyOfRange(byteArray, 0, n);
	}
}
