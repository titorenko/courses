import java.lang.reflect.Method;

import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {

    static class TestablePercolation extends Percolation {

        private int n;

        public TestablePercolation(int n) {
            super(n);
            this.n = n;
        }

        public int _translate(int i, int j) throws Exception {
            Method method = Percolation.class.getDeclaredMethod("translate",
                    int.class, int.class);
            method.setAccessible(true);
            return (int) method.invoke(this, i, j);
        }

        public String draw() {
            StringBuffer result = new StringBuffer();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (isFull(i, j)) {
                        result.append('f');
                    } else if (isOpen(i, j)) {
                        result.append('o');
                    }
                    if (!isOpen(i, j)) {
                        result.append("x");
                    }
                }
                result.append("\n");
            }
            return result.toString();
        }
    }

    @Test
    public void testPercolationConstruction() {
        TestablePercolation p = new TestablePercolation(10);
        assertFalse(p.percolates());
        assertFalse(p.isOpen(1, 1));
    }

    @Test
    public void testTranslation() throws Exception {
        TestablePercolation p = new TestablePercolation(2);
        assertEquals(1, p._translate(1, 1));
        assertEquals(2, p._translate(1, 2));
        assertEquals(3, p._translate(2, 1));
        assertEquals(4, p._translate(2, 2));
    }

    @Test
    public void test2by2Percolation() {
        TestablePercolation p = new TestablePercolation(2);
        p.open(1, 1);
        assertFalse(p.percolates());
        p.open(2, 1);
        assertTrue(p.percolates());
    }

    @Test
    public void test4by4PercolationBackwards() {
        TestablePercolation p = new TestablePercolation(4);
        p.open(1, 1);
        p.open(3, 1);
        p.open(4, 1);
        assertFalse(p.percolates());
        p.open(2, 1);
        assertTrue(p.percolates());
    }

    @Test
    public void testIfNotFullViaBottomSink() {
        TestablePercolation p = new TestablePercolation(3);
        p.open(2, 1);
        p.open(1, 1);
        assertFalse(p.percolates());
        p.open(3, 3);
        assertFalse(p.percolates());
        p.open(3, 1);
        assertFalse(p.isFull(3, 3));
        assertTrue(p.percolates());
        p.open(3, 2);
        assertTrue(p.isFull(3, 3));
        assertTrue(p.percolates());
    }
}
