package sw1.polynomials;

import java.util.Arrays;

public class Polynomial {

	private Monomial[] monomials;
	private int mon_counter = 0; // counter of monomials in polynomial

	/**
	 * Creates a polynomial with (safe copies of) the given monomials
	 * 
	 * @pre monomials != null
	 * @pre for all i, 0 <= i < monmials.length : monomials[i] != null
	 * @post for all i, 0 <= i < monmials.length : monomials[i].getCoefficient()
	 *       == getMonomial(i).getCoefficient()
	 * @post for all i,v, 0 <= i < monmials.length, 'a'<=v<='z' :
	 *       monomials[i].getDegree(v) == getMonomial(i).getDegree(v)
	 */
	public Polynomial(Monomial[] monomials) {

		this.monomials = new Monomial[monomials.length];
		for (int i = 0; i < monomials.length; i++)
			this.monomials[i] = monomials[i].getCopy();
		this.mon_counter += monomials.length;
	}

	/**
	 * @return the number of monomials in this polynomial
	 */
	public int getMonomialCount() {
		return this.mon_counter;
	}

	/**
	 * @pre 0<=index < getMonomialCount()
	 * @return a safe copy of the monomial at the given index
	 */
	public Monomial getMonomial(int index) {
		return this.monomials[index].getCopy();
	}

	/**
	 * @pre other != null
	 * @post Creates a new Polynomial which is the sum of this polynomial and
	 *       other. E.g., the sum of 13b^2x^3z+15 and -4b^2x^3z is
	 *       13b^2x^3z+15-4b^2x^3z
	 */
	public Polynomial add(Polynomial other) {

		Monomial[] new_monomials = new Monomial[this.mon_counter
				+ other.mon_counter];

		for (int i = 0; i < this.mon_counter; i++)
			// copying this monomials
			new_monomials[i] = this.monomials[i].getCopy();

		for (int j = 0; j < other.mon_counter; j++)
			// copying other monomials
			new_monomials[j + this.mon_counter] = other.monomials[j].getCopy();

		return new Polynomial(new_monomials);
	}

	/**
	 * @pre other != null
	 * @post Creates a new Polynomial which is the product of this polynomial
	 *       and other. E.g., the product of 13b^2x^3z+15 and -4b^2x^3z is
	 *       -52b^4x^6z^2-60b^2x^3z
	 */
	public Polynomial multiply(Polynomial other) {

		Monomial[] new_monomials = new Monomial[this.mon_counter
				* other.mon_counter];
		int counter = 0;
		Polynomial self_copy = this.getCopy();
		Polynomial other_copy = other.getCopy();
		Monomial self_tmp_mon;
		Monomial other_tmp_mon;

		for (int i = 0; i < self_copy.mon_counter; i++) {
			self_tmp_mon = self_copy.monomials[i];

			for (int j = 0; j < other_copy.mon_counter; j++) {
				other_tmp_mon = other_copy.monomials[j];
				new_monomials[counter] = monomial_mult(self_tmp_mon,
						other_tmp_mon);
				counter++;
			}
		}
		return new Polynomial(new_monomials);
	}

	// Multiply 2 monomials
	private Monomial monomial_mult(Monomial a, Monomial b) {
		Monomial output = new Monomial(a.getCoefficient() * b.getCoefficient());
		// degrees
		for (int i = 0; i < 26; i++) {
			if ((a.isVariable((char) (i + 97)))
					|| (b.isVariable((char) (i + 97)))) {
				output.setDegree(((char) (i + 97)), (a
						.getDegree((char) (i + 97)) + b
						.getDegree((char) (i + 97))));
			}
		}

		return output;
	}

	/**
	 * Returns a "safe" copy of this polynomial, i.e., if the copy is changed,
	 * this will not change and vice versa
	 */
	private Polynomial getCopy() {

		Monomial[] monomials = new Monomial[this.mon_counter];

		for (int i = 0; i < monomials.length; i++)
			monomials[i] = this.monomials[i].getCopy();

		return new Polynomial(monomials);

	}

	/**
	 * @pre assignment != null
	 * @pre assignment.length == 26
	 * @return the result of assigning assignment[0] to a, assignment[1] to b
	 *         etc., and computing the value of this Polynomial
	 */
	public int computeValue(int[] assignment) {
		int sum = 0;
		for (int i = 0; i < this.mon_counter; i++)
			sum += mon_computeValue(this.monomials[i].getCopy(), assignment);
		return sum;
	}

	// compute value for specific monomial
	private int mon_computeValue(Monomial monomial, int[] assignment) {
		int output = 1;
		for (int i = 0; i < 26; i++) {
			if (monomial.isVariable((char) (97 + i)))
				output *= Math.pow(assignment[i],
						monomial.getDegree((char) (97 + i)));
		}
		return output * monomial.getCoefficient();
	}

	/**
	 * @post Sums together all the monomials of the same degrees in this
	 *       polynomial e.g., 13b^2x^3z+15-4b^2x^3z --> 9b^2x^3z+15 and removes
	 *       monomials that are identical to 0
	 */
	public void normalize() {

		for (int i = 0; i < monomials.length; i++) {

			for (int j = i + 1; j < monomials.length; j++) {
				if (monomials[i].hasSameDegrees(monomials[j])) {
					monomials[i].setCoefficient(monomials[i].getCoefficient()
							+ monomials[j].getCoefficient());
					monomials[j].setCoefficient(0); // set monomials[j] to
													// Polynomial with
													// coefficient 0
				}
			}

			remove_Zero_Monomials();
		}
	}

	// remove from monomial array monomials with coefficent 0
	private void remove_Zero_Monomials() {

		int counter = 0;
		Monomial[] new_Monomial_Array = new Monomial[this.mon_counter];

		// copy to new monomials array without zeros

		for (int i = 0; i < monomials.length; i++) {
			if (this.monomials[i].getCoefficient() != 0) {
				new_Monomial_Array[counter] = this.monomials[i].getCopy();
				counter++;
			}
		}
		new_Monomial_Array = Arrays.copyOf(this.monomials, counter);
		this.monomials = new_Monomial_Array;
		this.setUpdated_mon_counter();

	}

	// Updates mon_conter with monomials array change (because of normalize)
	private void setUpdated_mon_counter() {
		this.mon_counter = this.monomials.length;
	}

	/**
	 * Returns a string representation of this polynomial by the mathematical
	 * convention, but without performing normalization (summing of monomials).
	 * I.e., each monomial is printed according to Monomial.toString(), for
	 * example 13b^2x^3z+15-4b^2x^3z
	 */
	public String toString() {

		if (monomials.length == 0) // empty Polynomial will printed as 0
			return "0";
		String output = monomials[0].toString();
		for (int i = 1; i < monomials.length; i++) {
			if (monomials[i].getCoefficient() < 0) // check sign (+ or -)
				output += monomials[i].toString();
			else
				output += "+" + monomials[i].toString();
		}

		return output;
	}
}
