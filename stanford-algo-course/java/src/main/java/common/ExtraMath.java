package common;

import com.google.common.base.Preconditions;

public class ExtraMath {

	public static int log2(int i) {
		Preconditions.checkArgument(i > 0);
		int result = 0;
		i--;
		while(i > 0) {
			i = i >> 1;
			result++;
		}
		return result;
	}

}
