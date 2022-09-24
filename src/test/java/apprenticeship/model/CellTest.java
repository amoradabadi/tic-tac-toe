package apprenticeship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CellTest {
    private final Cell cell1 = new Cell("1", "2");
    private final Cell cell2 = new Cell("1", "2");
    private final Cell cell3 = new Cell("2", "2");

    @Test
    void shouldReturnXandY() {
        assertEquals(1, cell1.x());
        assertEquals(2, cell1.y());
    }

    @Test
    void whenUsingNumbersshouldReturnXandY() {
        Cell cell = new Cell(1, 2);

        assertEquals(1, cell.x());
        assertEquals(2, cell.y());
    }

    @Test
    void whenObjectsAreEqual_returnTrue() {
        assertEquals(cell1, cell2);
    }

    @Test
    void whenObjectsAreNotEqual_returnFalse() {
        assertNotEquals(cell1, cell3);
    }

    @Test
    void whenHashAreEqual_returnTrue() {
        assertEquals(cell1.hashCode(), cell2.hashCode());
    }

    @Test
    void whenHashAreNotEqual_returnFalse() {
        assertNotEquals(cell1.hashCode(), cell3.hashCode());
    }

}