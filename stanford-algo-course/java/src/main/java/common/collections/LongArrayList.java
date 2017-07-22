package common.collections;

import java.util.Arrays;
import java.util.stream.LongStream;

import com.carrotsearch.hppc.procedures.LongProcedure;

public class LongArrayList extends com.carrotsearch.hppc.LongArrayList {

    public LongArrayList(long[] values) {
		super(values.length);
		add(values);
	}

	public LongArrayList() {
		super();
	}

	
	public LongStream stream() {
		return Arrays.stream(buffer, 0, size());
	}
	
	public void forAll(final LongProcedure procedure) {
        forEach(procedure);
    }

}

/*public class LongArrayList extends gnu.trove.list.array.TLongArrayList {
    public LongArrayList(long[] values) {
        super(values.length);
        add(values);
    }

    public LongArrayList() {
        super();
    }

    
    public LongStream stream() {
        return Arrays.stream(_data, 0, size());
    }

    public void forAll(final LongProcedure procedure) {
        final int size = _pos;
        final long[] data = _data;
        for (int i = 0; i < size; i++) {
            procedure.apply(data[i]);
        }
    }
}*/