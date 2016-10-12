package devigenere;

/*
 * Try reading or running this code, which cracks the Vigenere cipher
 * 
 * The Vigenere Cipher rotates each letter of the input message by an amount
 * dictated by the password. For example, 'A' might be treated as zero, and thus
 * a message containing 'HELLO' and a password of 'AB' would encode to 'HFLMO'
 * 'H' + A -> F, 'E' + B -> F, 'L' + A -> L, etc
 *
 * To reverse the vignere cipher for a given password length, we must know
 * the expected frequency distribution of characters in the original language.
 * Then, we split a message, such as HELLO by the length of the password.
 * In the case of length=2, the message becomes HE LL O.
 *
 * Each first character of the split sections becomes the "first interleave", or
 * HE LL O
 * ^  ^  ^
 * First interleave here is HLO
 * Each second character becomes the second interleave, which is "EL" here.
 *
 * We can crack each interleave by decrypting it with each of the possible
 * pass letters, from A-Z. Then, we look at the frequency distribution of the
 * resulting plaintext. We choose the distribution that is closest to the target
 * language. This example code minimizes the square differences between 
 * frequencies in the decoding of each interleave and english frequencies.
 *
 * The previos process can be called "deceasar", since it reverses what is known
 * as a "Caesar Cipher" for each interleave. Finally, we recombine the decrypted
 * interleaves into the original text.
 */
public class DeVigenere {
	public static void main(String[] args) {
		String input = "When in the course of human events it becomes necessary for one people to dissolve the political bands which have connected them with another and to assume among the powers of the earth the separate and equal station to which the laws of Nature and of Nature's God entitle them, a decent respect to the opinions of mankind requires that they declare the causes of their separation.";
		String encrypted = vignere(input, "ciccionee", 'a', 'z');
		//String encrypted="";
                System.out.println("ORIGINAL: " + input);
		System.out.println("ENCRYPTED: " + encrypted);
                encrypted = "Inserisci testo da decriptare";
		String decrypted = devignere(encrypted, 20, 'a', 'z');
		System.out.println("DECRYPTED: " + decrypted);
	}
    
	private static double[] frequencies = { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228,
			2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507,
			1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
			1.974, 0.074 }; //frequencies of a-z in english
			
	private static double score(String data) { // compute sum squares of deviations from expected frequency
		int[] letters = new int[26];   //compute histogram of a-z frequency:
		for(char c : data.toLowerCase().replaceAll("[^a-z]", "").toCharArray()) letters[c - 'a']++;

		double sumDSquared = 0.0;
		for(int j = 0; j < frequencies.length; j++) sumDSquared += Math.pow((100.0 * letters[j] / data.length() - frequencies[j]), 2);
		return sumDSquared;
	}

	public static String min(String a, String b) { //Return which of 2 strings has lower score
		return a == null || (b != null && score(a) > score(b)) ? b : a;
	}

	static String decaesar(String msg, char minChar, char maxChar) { //Returns caesar decryption with lowest score
		String best = null;
		for(int i = minChar; i <= maxChar; i++) best = min(best, vignere(msg, "" + (char) (i), minChar, maxChar));
		return best;
	}

	static String vignere(String data, String password, char minChar, char maxChar) { //vigenere cipher
		StringBuilder out = new StringBuilder();
		for(int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			int pass = password.charAt(i % password.length()) - minChar, range = maxChar - minChar + 1;
			out.append((c >= minChar && c <= maxChar) ? (char) (minChar + ((c - minChar + pass) % range + range) % range) : c);
		}
		return out.toString();
	}
	
	static StringBuilder[] splitInterleaves(String input, int length) { //converts "123456",3 to array "14","25","36"
		StringBuilder[] inter = new StringBuilder[length];
		for(int i = 0; i < inter.length; i++) inter[i] = new StringBuilder();
		for(int j = 0; j < input.length(); j++) inter[j % length].append(input.charAt(j)); 
		return inter;
	}

	static String devignere(String input, int maxLen, char minChar, char maxChar) {// Guessing passwords from 1 to maxLen.
		String best = null;
		for(int pwlen = 1; pwlen <= maxLen; pwlen++) {
			StringBuilder[] inter = splitInterleaves(input, pwlen);
			String[] decryptedInterleaves = new String[pwlen];
			// Decrypt each
			for(int j = 0; j < pwlen; j++) decryptedInterleaves[j] = decaesar(inter[j].toString(), minChar, maxChar);

			StringBuilder combined = new StringBuilder();
			for(int x = 0; x < input.length(); x++) combined.append(decryptedInterleaves[x % pwlen].charAt(x / pwlen));// Combine interleaves

			best = min(best, combined.toString()); //minimize between password lengths
		}
		return best;
	}
}