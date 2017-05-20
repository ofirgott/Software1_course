package sw1.polynomials;

import java.util.Arrays;

/**
 * Represents a multiplication of variables in a-z with an integral coefficient
 */
public class Monomial {

	private int coefficient;
	private int[] degrees;

	/**
	 * @post this.getCoefficient() == coefficient
	 * @post for every v, 'a'<=v<='z', isVariable(v) == false
	 */
	public Monomial(int coefficient) {

		this.coefficient = coefficient;
		this.degrees = new int[26];
	}

	/**
	 * @return the coefficient of this monomial
	 */
	public int getCoefficient() {
		return this.coefficient;
	}

	/**
	 * @post getCoefficient() == coefficient
	 */
	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	/**
	 * @return true iff the input is a variable of this monomial (and appears in
	 *         toString).
	 */
	public boolean isVariable(char variable) {
		if (this.degrees[variable - 97] == 0)
			return false;
		else
			return true;
	}

	/**
	 * @pre isVariable(variable)
	 * @return the degree of variable in this monomial
	 */
	public int getDegree(char variable) {
		return this.degrees[variable - 97];
	}

	/**
	 * @pre degree >= 0
	 * @pre 'a'<=variable<='z'
	 * @post getDegree(variable) = degree
	 */
	public void setDegree(char variable, int degree) {
		this.degrees[variable - 97] = degree;
	}

	/**
	 * @pre other!= null
	 * @return true iff the set of variables and the degree of each variable is
	 *         the same for this and other.
	 */
	public boolean hasSameDegrees(Monomial other) {
		if (Arrays.equals(this.degrees, other.degrees))
			return true;
		else
			return false;
	}

	/**
	 * Returns a string representation of this monomial by the mathematical
	 * convention. I.e., the coefficient is first (if not 1), then every
	 * variable in an alphabetic order followed by ^ and its degree (if > 1).
	 * For example, 13b^2x^3z
	 */
	public String toString() {

		String output = "";

		// check if coefficient is 0
		if (this.coefficient == 0)
			return new String("0");
		// check if coefficient is 1
		if (this.coefficient != 1)
			output = String.valueOf(this.coefficient);

		for (int i = 0; i < this.degrees.length; i++) {
			if (this.degrees[i] != 0) {
				if (this.degrees[i] != 1)
					output += String.valueOf(Character.toChars(i + 97)) + "^"
							+ String.valueOf(this.degrees[i]);
				else
					// degree is 1
					output += String.valueOf(Character.toChars(i + 97));

			}
		}
		return output;
	}

	/**
	 * Returns a "safe" copy of this monomial, i.e., if the copy is changed,
	 * this will not change and vice versa
	 */
	public Monomial getCopy() {

		Monomial output = new Monomial(this.coefficient);
		output.degrees = Arrays.copyOf(this.degrees, this.degrees.length);
		return output;
	}

	/**
	 * @pre this.degrees.length == 26
	 * @return true if all degrees in monomials == 0, else return false
	 */
	public boolean mon_All_degres_zeros() {
		for (int i = 0; i < 26; i++) {
			if (this.getDegree(((char) (i + 97))) != 0)
				return false;
		}
		return true;
	}
}
